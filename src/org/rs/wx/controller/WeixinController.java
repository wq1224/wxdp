package org.rs.wx.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rs.common.constance.Global;
import org.rs.common.constance.SignUtil;
import org.rs.common.util.XMLUtil;
import org.rs.wx.domain.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

@Controller
public class WeixinController {
	private static Logger logger = Logger.getLogger(WeixinController.class);
	
	@RequestMapping(value="weixin_msg.do", method=RequestMethod.GET)	
	public @ResponseBody String auth(
			@RequestParam("signature")String signature, 
			@RequestParam("timestamp")String timestamp, 
			@RequestParam("nonce")String nonce, 
			@RequestParam("echostr")String echostr, 
			HttpServletRequest request) {

		logger.info("------------weixin_msg.do: signature="+ signature+", timestamp="+timestamp+", nonce="+nonce+", echostr="+echostr );
		String[] str = { Global.TOKEN, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = SignUtil.SHA1(bigStr);
        System.out.println("digest:" +digest);
        // 确认请求来至微信
        if (signature.equals(digest.toLowerCase())) {
            return echostr;
        }
		//return "loginSuccess";
        else return "error";
	}
	
	@RequestMapping(value="weixin_msg.do", method=RequestMethod.POST)
	public @ResponseBody String getMsg(HttpServletRequest request,@RequestBody String body) throws ParserConfigurationException, SAXException, IOException {
		// 将解析结果存储在HashMap中 
	    logger.info("------------weixin_msg.do: body="+ body );
	    
	    Message m = XMLUtil.parseXML2Message(body);
	    return XMLUtil.message2string(m);
	}
}