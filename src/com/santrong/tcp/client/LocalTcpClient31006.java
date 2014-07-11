package com.santrong.tcp.client;

import com.santrong.tcp.TcpDefine;

/**
 * @author weinianjie
 * @date 2014年7月11日
 * @time 下午5:37:06
 */
public class LocalTcpClient31006 {
	
	public String getMajorXml() {
		return TcpDefine.Xml_Header
			+ "<ReqMsg>"
			+ "<MsgHead>"
				+ "<MsgCode>" + TcpDefine.Basic_Client_RecordCtl + "</MsgCode>"
				+ "<ModId>" + TcpDefine.ModuleSign + "</ModId>"
				+ "<SessionId>1</SessionId>"
			+ "</MsgHead>"
			+ "<MsgBody>"
				+ "<RcdCtlReq>"
					+ "<ConfID type=\"string\">#{ConfID}</ConfID>"
					+ "<OperType type=\"int\">#{OperType}</OperType>"// <!-- 0：停止录制; 1：开始录制; 2：暂停录制; -->
				+ "</RcdCtlReq>"
			+ "</MsgBody>"
		+ "</ReqMsg>";
	}
}