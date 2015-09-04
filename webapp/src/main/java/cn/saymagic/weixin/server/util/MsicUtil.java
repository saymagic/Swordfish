package cn.saymagic.weixin.server.util;

import java.io.UnsupportedEncodingException;

public class MsicUtil {

	public static String formatString(String string){
		try {
			return new String(string.getBytes("UTF-8"),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}
}
