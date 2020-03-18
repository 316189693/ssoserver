package com.example.ssoserver.util;

import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class EncryptDecryptUtil {
    private static final String key = "HtjySso6jfdk45d6"; // length must be 16
    private static final String cipherKey = "HtjySSo2jfdk3876"; // length must be 16

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            IvParameterSpec iv = new IvParameterSpec(cipherKey.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            final String encryptedString = Base64.getUrlEncoder().encodeToString(cipher
                    .doFinal(strToEncrypt.getBytes("UTF-8")));
            return encryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String md5(String strToEncrypt, String salt) {
        try {
            return DigestUtils.md5DigestAsHex((strToEncrypt + salt).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            IvParameterSpec iv = new IvParameterSpec(cipherKey.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            final String decryptedString = new String(
                    cipher.doFinal(Base64.getUrlDecoder().decode(strToDecrypt)));
            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
		System.out.println(encrypt("ddccccddfd##$#$@DSDSDDXCCXccxcx"));
		System.out.println(md5("ddccccddfd##$#sdsddsdqw233233$@DSDSDDXCCXccxcx", "EHIPartnerPortal"));
		System.out.println(decrypt(encrypt("ddccccddfd##$#$@DSDSDDXCCXccxcx")));
	}
}
