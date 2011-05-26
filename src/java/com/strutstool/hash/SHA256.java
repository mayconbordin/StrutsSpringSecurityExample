package com.strutstool.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author maycon
 */
public class SHA256 {

    public static String calculate(String str) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
}
