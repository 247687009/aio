package com.santrong.http.server.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santrong.log.Log;
import com.santrong.system.Global;
import com.santrong.util.CommonTools;
import com.santrong.util.XmlReader;

/**
 * @author weinianjie
 * @date 2014年7月24日
 * @time 下午8:38:00
 */
@Controller
@RequestMapping("/http")
public class HttpServiceDispatcher {

	/*
	 * 分发服务
	 */
	private String dispatch(String xmlMsg) {
		// 第一种信令规范
		XmlReader xml = new XmlReader();
		xml.parse(xmlMsg);
		String msgCode = xml.find("/MsgHead/MsgCode").getText();
		try {
			
			// 分发任务
			AbstractHttpService service = (AbstractHttpService) Class.forName("com.santrong.http.server.BasicHttpService" + msgCode).newInstance();
			return service.excute(xml);
			
		} catch (Exception e) {
			Log.printStackTrace(e);
		}
		
		//第二种信令规范请实现AbstractHttpService接口构建XXXHttpService类
		
		return "";	
	}
	
	/**
	 * 解析http数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/basic", method=RequestMethod.POST)
	@ResponseBody
	public String basic(HttpServletRequest request, HttpServletResponse response) {
        try {
			request.setCharacterEncoding(Global.Default_Encoding);
			
			InputStream in = request.getInputStream();
			int length = request.getContentLength();
			byte[] requestData = readContent(in, length);
			String xmlMsg = new String(requestData, Global.Default_Encoding);
			
			String uuid = CommonTools.getGUID();
			
			Log.debug("HTTP-begin-" + uuid + ":getXmlMsg : " + xmlMsg);
			String retMsg = dispatch(xmlMsg);
			Log.debug("HTTP-end-" + uuid + ":sendXmlMsg : " + retMsg);
			
			OutputStream out = response.getOutputStream();
            if (retMsg != null) {
                byte[] respData = retMsg.getBytes(Global.Default_Encoding);
                response.setContentLength(respData.length);
                out.write(respData);
            }
            
            in.close();
            out.flush();
            out.close();
			
		} catch (Exception e) {
			Log.printStackTrace(e);
		}
        
		return null;
	}
	
    public byte[] readContent(final InputStream in, int length) throws IOException {
        byte dataBytes[] = new byte[length];
        int bytesRead = 0;
        int n = 0;
        int leftbytes = length;
        while (leftbytes > 0
                && (n = in.read(dataBytes, bytesRead, leftbytes)) != -1) {
            leftbytes = length - bytesRead;
            bytesRead = bytesRead + n;
        }
        return dataBytes;
    }	
}
