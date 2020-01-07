package com.example.zyjulib.utile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

	public static byte[] encrypt(String paramString) {
		return encrypt(paramString.getBytes());
	}

	public static byte[] encrypt(byte[] paramArrayOfByte) {
		byte[] arrayOfByte1 = null;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte2 = localMessageDigest.digest();
			arrayOfByte1 = arrayOfByte2;
			return arrayOfByte1;
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			return null;

		}
	}

	public static String encrypt_bytes(byte[] paramArrayOfByte) {
		return toHexString(encrypt(paramArrayOfByte));
	}

	public static String encrypt_string(String paramString) {
		return toHexString(encrypt(paramString));
	}

	public static String toHexString(byte[] paramArrayOfByte) {
		StringBuilder localStringBuilder = new StringBuilder(3 * paramArrayOfByte.length);
		int i = paramArrayOfByte.length;
		for (int j = 0;; j++) {
			if (j >= i)
				return localStringBuilder.toString().toUpperCase();
			int k = 0xFF & paramArrayOfByte[j];
			localStringBuilder.append(HEXDIGITS[(k >> 4)]);
			localStringBuilder.append(HEXDIGITS[(k & 0xF)]);
		}
	}
}
