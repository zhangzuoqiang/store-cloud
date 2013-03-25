package com.graby.store.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * MD5加密
 * @author huabiao.mahb
 *
 */
public class EncryptUtil {

	private static MessageDigest digest = null;

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 */
	public synchronized static String md5(String data) {
		if (StringUtils.isEmpty(data)) {
			return null;
		}
		// SHA-1, MD5
		final String algorithm = "MD5";
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance(algorithm);
			} catch (NoSuchAlgorithmException nsae) {
				throw new RuntimeException("编码出错:" + algorithm);
			}
		}
		try {
			digest.update(data.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
		return encodeHex(digest.digest());
	}

	private static String encodeHex(byte[] bytes) {
		StringBuffer hex = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toString((int) bytes[i] & 0xff, 16));
		}
		return hex.toString();
	}

	/**
	 * 产生随机数
	 * 
	 * @param length
	 * @return
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	private static Random randGen = new Random();
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	public static void main(String[] args) {
		System.out.println(EncryptUtil.md5("亮晶晶善良一族"));
	}
}
