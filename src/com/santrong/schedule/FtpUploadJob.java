package com.santrong.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.santrong.file.dao.FileDao;
import com.santrong.file.entry.FileItem;
import com.santrong.ftp.DirectoryUploadEventListener;
import com.santrong.ftp.FtpConfig;
import com.santrong.ftp.FtpHandler;
import com.santrong.log.Log;
import com.santrong.meeting.entry.MeetingItem;
import com.santrong.opt.ThreadUtils;
import com.santrong.system.DirDefine;
import com.santrong.util.SantrongUtils;

/**
 * @author weinianjie
 * @date 2014年8月13日
 * @time 下午3:06:20
 */
public class FtpUploadJob extends JobImpl {
	
	public static boolean uploading;
	
	@Override
	public String getJobName() {
		return "FtpUpload";
	}

	@Override
	public String getTriggerName() {
		return "FtpUploadTrigger";
	}

	@Override
	public String getCronExp() {
		// 每30秒检测一次，既能检测是否需要启动ftp，又能防止时间范围内失败就不再上传
		return "*/30 * * * * ?";
	}
	
	@Override
	public Date getDateTime() {
		return null;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try{
		
			FtpConfig config = new FtpConfig();
			
			if(config.getFtpEnable().equals("1")) {
				SimpleDateFormat sdf = new SimpleDateFormat(SantrongUtils.DF_yyyy_MM_dd_HH_mm_ss);
				Calendar now = Calendar.getInstance();
				Calendar begin = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				
				try{
					String nowDateString = SantrongUtils.dateToString(new Date(), SantrongUtils.DF_yyyy_MM_dd);
					
					begin.setTime(sdf.parse(nowDateString + " " + config.getBeginTime() + ":00"));//config里面的时间应该是12:30这样的
					
					end.setTime(sdf.parse(nowDateString + " " + config.getEndTime() + ":00"));
					
				}catch(Exception e) {
					Log.printStackTrace(e);
					return;
				}
				
				if(now.compareTo(begin) > 0 && now.compareTo(end) < 0) {// 时间范围内
					
					if(!uploading) {
						
						try{
							DirectoryUploadEventListener listener = new DirectoryUploadEventListener();
							HashMap<String, Object> map = new HashMap<String, Object>();
							FileDao fileDao = new FileDao();
							
							// 获取所有应该上传的文件
							List<FileItem> fileList = fileDao.selectToFtp();
							if(fileList != null && fileList.size() > 0) {
								FtpHandler handler = FtpHandler.getInstance();
								handler.setIp(config.getFtpIp());
								handler.setPort(Integer.parseInt(config.getFtpPort()));
								handler.setUsername(config.getUsername());
								handler.setPassword(config.getPassword());
								
								uploading = true;
								for(FileItem file : fileList) {
									// 更新文件状态为正在上传
									file.setCts(new Date());
									file.setStatus(FileItem.File_Status_Uploading);
									if(fileDao.update(file) < 0) {
										Log.mark("---set file status to uploading fail with fileId " + file.getId());
									}								
									
									String confId = MeetingItem.ConfIdPreview + file.getChannel();
									map.put("fileId", file.getId());
									handler.setMapper(map);
									handler.uploadDirectory(DirDefine.VedioDir + "/" + confId, file.getFileName(), listener);// /data/CLSRM_*/filename/cmps以/data/CLSRM_*为根
	//								handler.uploadDirectory(DirDefine.VedioDir, confId + "/" + file.getFileName(), listener);// /data/CLSRM_*/filename/cmps以/data为根
									
									// 更新文件状态为上传完成
									file.setCts(new Date());
									file.setStatus(FileItem.File_Status_Uploaded);
									if(fileDao.update(file) < 0) {
										Log.mark("---set file status to uploaded fail with fileId " + file.getId());
									}
								}
							}
						}catch(Exception e) {
							Log.printStackTrace(e);
						}finally{
							uploading = false;
						}
					}
				}else{// 时间范围外
					if(uploading) {
						FtpHandler handler = FtpHandler.getInstance();
						handler.forceStop();
						uploading = false;
					}
				}
				
			}else {
				if(uploading) {
					FtpHandler handler = FtpHandler.getInstance();
					handler.forceStop();
					uploading = false;
				}
			}
		}catch(Exception e) {
			Log.printStackTrace(e);
		}finally{
			// 在quartz里面线程永远不会结束，需要手动关闭数据库连接
			ThreadUtils.closeAll();
		}
	}
}
