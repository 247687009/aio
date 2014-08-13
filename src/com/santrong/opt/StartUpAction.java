package com.santrong.opt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.santrong.schedule.FtpUploadJob;
import com.santrong.schedule.ScheduleManager;
import com.santrong.schedule.StatusMonitJob;

/**
 * 系统启动的时候直接调用
 */
@SuppressWarnings("serial")
public class StartUpAction extends HttpServlet {
	

	@Override
	public void init() throws ServletException {
		
		ScheduleManager scheManager = new ScheduleManager();
		
		scheManager.startCron(new StatusMonitJob());// 启动会议室状态监听线程
		
		scheManager.startCron(new FtpUploadJob());// 启动ftp上传扫描线程
		
		// 启动TCP服务监听线程
//		new Thread(new TcpServer(), "---TcpServer").start();
	}
	
}
