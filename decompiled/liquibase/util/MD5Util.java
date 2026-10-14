/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;

public class MD5Util {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String computeMD5(String input) {
        MessageDigest digest;
        if (input == null) {
            return null;
        }
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
        byte[] digestBytes = digest.digest();
        String returnString = new String(MD5Util.encodeHex(digestBytes));
        String inputToLog = input;
        if (inputToLog.length() > 500) {
            inputToLog = inputToLog.substring(0, 500) + "... [truncated in log]";
        }
        Scope.getCurrentScope().getLog(MD5Util.class).fine("Computed checksum for " + inputToLog + " as " + returnString);
        return returnString;
    }

    public static String computeMD5(InputStream stream) {
        MessageDigest digest;
        if (stream == null) {
            return null;
        }
        try {
            digest = MessageDigest.getInstance("MD5");
            DigestInputStream digestStream = new DigestInputStream(stream, digest);
            byte[] buf = new byte[20480];
            while (digestStream.read(buf) != -1) {
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte[] digestBytes = digest.digest();
        String returnString = new String(MD5Util.encodeHex(digestBytes));
        Scope.getCurrentScope().getLog(MD5Util.class).fine("Computed checksum for inputStream as " + returnString);
        return returnString;
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; ++i) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0xF & data[i]];
        }
        return out;
    }
}

