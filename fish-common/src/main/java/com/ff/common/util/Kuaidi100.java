package com.ff.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Kuaidi100
{

	public static String key = "29833628d495d7a5";//必填项，从官网申请的key

	public static void main(String[] agrs)
	{
		//huitongkuaidi 210414794266
		//zhongtong 761284432955
		//shunfeng 211001746537
		//System.out.println(getExpressInfo("shunfeng","211001746537"));
		String info=getExpressInfo("quanfengkuaidi","123456");
		System.out.println(getExpressInfo("shunfeng","211001746537"));
//		try
//		{
//			URL url= new URL("http://api.kuaidi100.com/api?id=c1441d4e82940df1&com=shunfeng&nu=211001746537&show=0&muti=1&order=desc");
//			URLConnection con=url.openConnection();
//			con.setAllowUserInteraction(false);
//			InputStream urlStream = url.openStream();
//			String type = con.guessContentTypeFromStream(urlStream);
//			String charSet=null;
//			if (type == null)
//				type = con.getContentType();
//
//			if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
//				return ;
//
//			if(type.indexOf("charset=") > 0)
//				charSet = type.substring(type.indexOf("charset=") + 8);
//
//			byte b[] = new byte[10000];
//			int numRead = urlStream.read(b);
//			String content = new String(b, 0, numRead);
//			while (numRead != -1) {
//				numRead = urlStream.read(b);
//				if (numRead != -1) {
//					//String newContent = new String(b, 0, numRead);
//					String newContent = new String(b, 0, numRead, charSet);
//					content += newContent;
//				}
//			}
//			System.out.println("content:" + content);
//			urlStream.close();
//		} catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}
	/**
	 * 查询快递信息
	 * @param com 快递公司代码
	 * @param nu 快递单号
	 * @return
	 */
	public static String getExpressInfo(String com ,String nu){
		String ret = "";
		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append("http://api.kuaidi100.com/api?id=");
			sb.append(Kuaidi100.key);
			sb.append("&com=").append(com);
			sb.append("&nu=").append(nu);
			sb.append("&show=0&muti=1&order=desc");
			URL url= new URL(sb.toString());
			URLConnection con=url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			String type = con.guessContentTypeFromStream(urlStream);
			String charSet=null;
			if (type == null)
				type = con.getContentType();

			if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/json") < 0)
				return "";

			if(type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);

			byte b[] = new byte[10000];
			int numRead = urlStream.read(b);
			String content = new String(b, 0, numRead);
			while (numRead != -1) {
				numRead = urlStream.read(b);
				if (numRead != -1) {
					//String newContent = new String(b, 0, numRead);
					String newContent = new String(b, 0, numRead, charSet);
					content += newContent;
				}
			}
			ret = content;
			urlStream.close();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 该接口使用与收费的快递公司接口查询
	 * @param com 快递公司代码
	 * @param nu 快递单号
	 * @return
	 */
	public static String searchkuaiDiInfo(String com, String nu)
	{
		String content = "";
		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append("http://www.kuaidi100.com/applyurl?key=").append(Kuaidi100.key);
			sb.append("&com=").append(com);
			sb.append("&nu=").append(nu);
			URL url = new URL(sb.toString());
			URLConnection con = url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			byte b[] = new byte[10000];
			int numRead = urlStream.read(b);
			content = new String(b, 0, numRead);
			while (numRead != -1)
			{
				numRead = urlStream.read(b);
				if (numRead != -1)
				{
					// String newContent = new String(b, 0, numRead);
					String newContent = new String(b, 0, numRead, "UTF-8");
					content += newContent;
				}
			}
			urlStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("快递查询错误");
		}
		return content;
	}

}

