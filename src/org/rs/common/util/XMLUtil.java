package org.rs.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.rs.common.constance.Global;
import org.rs.wx.domain.Message;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLUtil {
	
	/*
	 * 	<xml>
			<ToUserName><![CDATA[toUser]]></ToUserName>
			<FromUserName><![CDATA[FromUser]]></FromUserName>
			<CreateTime>123456789</CreateTime>
			<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[CLICK]]></Event>
			<EventKey><![CDATA[EVENTKEY]]></EventKey>
		</xml>
	 */
	public static Message parseXML2Message(String message) throws ParserConfigurationException, SAXException, IOException{
		ByteArrayInputStream stream = new ByteArrayInputStream(message.getBytes());
	    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc = (Document) builder.parse(stream);
		Message m = new Message();
		m.setToUserName(doc.getElementsByTagName("ToUserName").item(0).getNodeValue());
		m.setFromUserName(doc.getElementsByTagName("FromUserName").item(0).getNodeValue());
		m.setCreateTime(doc.getElementsByTagName("CreateTime").item(0).getNodeValue());
		m.setMsgType(doc.getElementsByTagName("MsgType").item(0).getNodeValue());
		m.setContent(doc.getElementsByTagName("Content").item(0).getNodeValue());
		//m.setEvent(doc.getElementsByTagName("Event").item(0).getNodeValue());
		//m.setEventKey(doc.getElementsByTagName("EventKey").item(0).getNodeValue()); 
	    return m;
	}
	
	public static String message2string(Message m){
		return "<xml>" + 
		"<ToUserName><![CDATA[" + m.getFromUserName() + "]]></ToUserName>" +
		"<FromUserName><![CDATA[" + m.getToUserName()+ "]]></FromUserName>" +
		"<CreateTime>" + m.getCreateTime() + "</CreateTime>" + 
		"<MsgType><![CDATA[text]]></MsgType>" +
		"<Content><![CDATA[" + m.getContent() + "]]></Content>" +
		"</xml>";
	}
	
	public static void main(String[] args){
		
	}
}
