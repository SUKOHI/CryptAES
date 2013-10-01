package com.sukohi.lib;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptAES {
	
	private static SecretKeySpec sKey;
	
	public CryptAES(String secretkey, int bitLength) {
		
		byte[] bytes = new byte[bitLength/8];
		byte[] keys = null;
		
		try {
			
			keys = secretkey.getBytes("UTF-8");
			
			for(int i = 0; i < secretkey.length(); i++ ) {
			     if(i >= bytes.length) break;
			     bytes[i] = keys[i];
			}
			
		    sKey = new SecretKeySpec(bytes, "AES");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	private byte[] crypt(int mode, byte[] data) {
		
		int cipherMode;
		
		switch(mode) {
		
			case 1:
				cipherMode = Cipher.ENCRYPT_MODE;
				break;
			case 2:
				cipherMode = Cipher.DECRYPT_MODE;
				break;
			default:
				cipherMode = Cipher.ENCRYPT_MODE;
		
		}
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(cipherMode, sKey);
			return cipher.doFinal(data);
		
		} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
		} catch (NoSuchPaddingException e) {
		e.printStackTrace();
		} catch (InvalidKeyException e) {
		e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
		e.printStackTrace();
		} catch (BadPaddingException e) {
		e.printStackTrace();
		}
		return new byte[0];
	
}
	
	public byte[] encrypt(byte[] data) {

	     return crypt(1, data);
	     
	 }

	public byte[] decrypt(byte[] encData) {

		return crypt(2, encData);
		
	 }

}

/*** Sample source

	String key = "ThisIsTheKeyword";	// Note: Only alphabet and number
	int bitLength = 128;				// or 192, 256
	CryptAES aes = new CryptAES(key, bitLength);
	data = aes.encrypt(data);
	data = aes.decrypt(data);
	
***/