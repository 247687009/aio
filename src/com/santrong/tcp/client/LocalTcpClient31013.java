package com.santrong.tcp.client;

import com.santrong.tcp.TcpDefine;

/**
 * @author weinianjie
 * @date 2014年7月11日
 * @time 下午5:37:06
 */
public class LocalTcpClient31013 {
	
	public String getMajorXml() {
		return TcpDefine.Xml_Header
			+ "<ReqMsg>"
				+ "<MsgHead>"
					+ "<MsgCode>" + TcpDefine.Basic_Client_DirectCtrl + "</MsgCode>"
					+ "<ModId>" + TcpDefine.ModuleSign + "</ModId>"
					+ "<SessionId>1</SessionId>"
				+ "</MsgHead>"
				+ "<MsgBody>"
					+ "<DirectCtrlReq>"
						+ "<ConfID type=\"string\">#{ConfID}</ConfID>"
						+ "<StrmId type=\"int\">#{StrmId}</StrmId>"
					+ "</DirectCtrlReq>"
				+ "</MsgBody>"
			+ "</ReqMsg>";
	}
}