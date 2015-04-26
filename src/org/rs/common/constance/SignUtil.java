package org.rs.common.constance;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
	public static String SHA1(String origin){
		// SHA1加密
        MessageDigest messageDigest;
        String digest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(origin.getBytes());
	        byte resultData[] = messageDigest.digest();
	        digest = byteToStr(resultData);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}      
        return digest;
	}
	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
	    String strDigest = "";
	    for (int i = 0; i < byteArray.length; i++) {
	        strDigest += byteToHexStr(byteArray[i]);
	    }
	    return strDigest;
	}
	
	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
	    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	    char[] tempArr = new char[2];
	    tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
	    tempArr[1] = Digit[mByte & 0X0F];

	    String s = new String(tempArr);
	    return s;
	}
	
	public static void main(String args[]){
		String[] str = { Global.TOKEN, "7987489374893274", "6788997" };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = SignUtil.SHA1(bigStr);
        System.out.println("digest:" +digest);
	}
}
