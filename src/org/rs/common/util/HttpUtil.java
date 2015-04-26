package org.rs.common.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	private static ThreadSafeClientConnManager cm = null;
	public static final int MAX_JOB_COUNT = 2000;
	public static final int MAX_HTTP_TOTALCONNECTION = 300;
	public static final int MAX_HTTP_PERROUTE = 200;
	public static final int MAX_HTTP_CONNECTIONTIMEOUT = 10000;
	public static final int MAX_HTTP_SOCKETTIMEOUT = 3600000;
	
	static {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));
		cm = new ThreadSafeClientConnManager(schemeRegistry);
		// 设置最大连接数
		cm.setMaxTotal(MAX_HTTP_TOTALCONNECTION);
		//cm.setMaxTotal(100);
		// 每个路由(route)最大连接数
		cm.setDefaultMaxPerRoute(MAX_HTTP_PERROUTE);
		//cm.setDefaultMaxPerRoute(20);
	}

	public static HttpClient getHttpClient() {
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		// 设置连接超时时间
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				MAX_HTTP_CONNECTIONTIMEOUT);
		//httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// 设置读取超时时间
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT,
				MAX_HTTP_SOCKETTIMEOUT);
		//httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 12000);
		return new DefaultHttpClient(cm, httpParams);
	}

	public static void main(String[] args) {
		HttpPut put = new HttpPut("http://180.153.148.24/SAP-V2/package/update");
		try {
			put.setEntity(new StringEntity(
					"<UpdatePackages><Package><packageId>5e06861b82844810b61459ada8497906</packageId><packageName>50元套餐</packageName><groupMax>15</groupMax><friendMax>15</friendMax><fileMax>15</fileMax><folderMax>15</folderMax><duration>10W</duration></Package></UpdatePackages>",
					"application/xml", "UTF-8"));
			HttpResponse httpResponse = getHttpClient().execute(put);
			for (Header header : httpResponse.getAllHeaders()) {
				System.out.println(header.getName() + "：" + header.getValue());
			}
			System.out.println(httpResponse.getStatusLine());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
