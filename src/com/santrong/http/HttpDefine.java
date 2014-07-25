package com.santrong.http;

/**
 * @author weinianjie
 * @date 2014年7月24日
 * @time 下午8:33:28
 */
public class HttpDefine {
	
	public static final String Xml_Header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	public static final String Basic_Server_GetResource 			= "30001";	// 获取资源状态（磁盘、型号、序列号）
	public static final String Basic_Server_GetModInfo 				= "30002";	// 获取模块版本和运行状态
	public static final String Basic_Server_GetNetwork 				= "30003";	// 获取网络配置
	public static final String Basic_Server_RecordCtl 				= "30004";	// 录制，暂停，恢复，停止
	public static final String Basic_Server_TiltCtrl 				= "30005";	// 摄像头控制
	public static final String Basic_Server_GetMtInfo 				= "30006";	// 获取会议配置
	 
	
}