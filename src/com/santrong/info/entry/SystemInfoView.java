package com.santrong.info.entry;

import java.util.ArrayList;
import java.util.List;

import com.santrong.tcp.client.MainTcp39004.ModuleStatus;

/**
 * @author weinianjie
 * @date 2014年7月14日
 * @time 下午4:34:09
 */
public class SystemInfoView {
	
	private String deviceNo;
	private String deviceType;
	private int uniVodMax;
	private int uniCur;
	private int vodCur;
	private int freePcent;
	private int freeSize;
	private List<ModuleStatus> moduleList = new ArrayList<ModuleStatus>();
	
	/*
	 * 根据百分比和空闲算出磁盘总容量
	 */
	public String getTotalSize() {
		if(this.freePcent != 0){
			return String.valueOf(this.freeSize * 100 / this.freePcent); 
		}
		return "unknown";
	}
	
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public int getUniVodMax() {
		return uniVodMax;
	}
	public void setUniVodMax(int uniVodMax) {
		this.uniVodMax = uniVodMax;
	}
	public int getUniCur() {
		return uniCur;
	}
	public void setUniCur(int uniCur) {
		this.uniCur = uniCur;
	}
	public int getVodCur() {
		return vodCur;
	}
	public void setVodCur(int vodCur) {
		this.vodCur = vodCur;
	}
	public int getFreePcent() {
		return freePcent;
	}
	public void setFreePcent(int freePcent) {
		this.freePcent = freePcent;
	}
	public int getFreeSize() {
		return freeSize;
	}
	public void setFreeSize(int freeSize) {
		this.freeSize = freeSize;
	}
	public List<ModuleStatus> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<ModuleStatus> moduleList) {
		this.moduleList = moduleList;
	}
}