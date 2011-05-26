package com.strutstool.hash;

import com.strutstool.string.StringUtil;

/**
 *
 * @author maycon
 */
public class Challenge {
    public static String generate() {
        String str = StringUtil.generateRandomString(40) + System.currentTimeMillis();
        return SHA256.calculate(str);
    }
}
