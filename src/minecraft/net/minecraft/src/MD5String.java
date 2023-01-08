package net.minecraft.src;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5String {
	private String field_27370_a;

	public MD5String(String string1) {
		this.field_27370_a = string1;
	}

	public String func_27369_a(String string1) {
		try {
			String string2 = this.field_27370_a + string1;
			MessageDigest messageDigest3 = MessageDigest.getInstance("MD5");
			messageDigest3.update(string2.getBytes(), 0, string2.length());
			return (new BigInteger(1, messageDigest3.digest())).toString(16);
		} catch (NoSuchAlgorithmException noSuchAlgorithmException4) {
			throw new RuntimeException(noSuchAlgorithmException4);
		}
	}
}
