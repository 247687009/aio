package com.santrong.tcp.client;

import com.santrong.system.Global;
import com.santrong.tcp.TcpDefine;
import com.santrong.tcp.client.base.LocalTcpBase;
import com.santrong.util.XmlReader;

/**
 * @author weinianjie
 * @date 2014年7月11日
 * @time 下午5:37:06
 */
public class LocalTcp31012 extends LocalTcpBase {
	private String confId;
	private int strmId;
	private int tiltType;
	private int speed;
	private int camAddr;
	
	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setConfId(String confId) {
		this.confId = confId;
	}

	public void setStrmId(int strmId) {
		this.strmId = strmId;
	}

	public void setTiltType(int tiltType) {
		this.tiltType = tiltType;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setCamAddr(int camAddr) {
		this.camAddr = camAddr;
	}

	@Override
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append(TcpDefine.Xml_Header);
		sb.append("<ReqMsg>");
			sb.append("<MsgHead>");
				sb.append("<MsgCode>").append(TcpDefine.Basic_Client_TiltCtrl).append("</MsgCode>");
				sb.append("<ModId>").append(Global.Module_Sign).append("</ModId>");
				sb.append("<SessionId>1</SessionId>");
			sb.append("</MsgHead>");
			sb.append("<MsgBody>");
				sb.append("<TiltCtrlReq>");
					sb.append("<ConfID type=\"string\">").append(this.confId).append("</ConfID>");
					sb.append("<StrmId type=\"int\">").append(this.strmId).append("</StrmId>");
					sb.append("<TiltType type=\"int\">").append(this.tiltType).append("</TiltType>");
					sb.append("<Speed type=\"int\">").append(this.speed).append("</Speed>");
					sb.append("<CamAddr type=\"int\">").append(this.camAddr).append("</CamAddr>");
				sb.append("</TiltCtrlReq>");
			sb.append("</MsgBody>");
		sb.append("</ReqMsg>");
		return sb.toString();
	}

	@Override
	public void resolveXml(XmlReader xml) {
		this.resultCode = Integer.parseInt(xml.find("/MsgBody/TiltCtrlResp/ResultCode").getText());
		
	}
}
