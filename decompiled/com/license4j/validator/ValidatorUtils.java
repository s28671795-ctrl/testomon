/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.validator;

import com.license4j.licensetext.LicenseText;
import com.license4j.util.Base32;
import com.license4j.util.HTTP;
import com.license4j.util.NtpMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorUtils {
    private static final Logger log = LoggerFactory.getLogger(ValidatorUtils.class);
    private static final char[] VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException ex) {
            log.debug("EX1191", ex);
            return "unknown";
        }
    }

    public static String getOSName() {
        return Optional.ofNullable(System.getProperty("os.name")).filter(s2 -> !s2.isBlank()).orElse("null");
    }

    public static int getPublicKeyHashcode(String publicKey) {
        return publicKey.replaceAll("\r\n|\n", "").hashCode();
    }

    public static Instant getRealCurrentDate(LicenseText licenseText) {
        Instant realCurrentDate = null;
        if (licenseText != null && licenseText.getDatetimeCheck() > 0) {
            String datetimeNTPServer = licenseText.getDatetimeNTPServer();
            int datetimeNTPServerTimeout = licenseText.getDatetimeNTPServerTimeout();
            String datetimeWebServer = licenseText.getDatetimeWebServer();
            int datetimeWebServerTimeout = licenseText.getDatetimeWebServerTimeout();
            Date ntpServerDate = null;
            Date webServerDate = null;
            Date ntpServerDate2 = null;
            if (datetimeNTPServer != null) {
                ntpServerDate = ValidatorUtils.ntpCheck(datetimeNTPServer, datetimeNTPServerTimeout);
            }
            if (ntpServerDate == null && datetimeWebServer != null) {
                webServerDate = ValidatorUtils.webServerDate(datetimeWebServer, datetimeWebServerTimeout);
            }
            if (ntpServerDate == null && webServerDate == null && ValidatorUtils.getLogonServer() != null) {
                ntpServerDate2 = ValidatorUtils.ntpCheck(datetimeNTPServer, 1000);
            }
            realCurrentDate = Optional.ofNullable(ntpServerDate).map(Date::toInstant).orElse(Optional.ofNullable(ntpServerDate2).map(Date::toInstant).orElse(null));
        }
        return realCurrentDate;
    }

    private static Date ntpCheck(String addr, int timeout2) {
        DatagramSocket socket = new DatagramSocket();
        try {
            socket.setSoTimeout(timeout2);
            InetAddress inetAddress = InetAddress.getByName(addr);
            byte[] ntpMessage = new NtpMessage().toByteArray();
            DatagramPacket packet = new DatagramPacket(ntpMessage, ntpMessage.length, inetAddress, 123);
            NtpMessage.encodeTimestamp(packet.getData(), 40, (double)System.currentTimeMillis() / 1000.0 + 2.2089888E9);
            socket.send(packet);
            packet = new DatagramPacket(ntpMessage, ntpMessage.length);
            socket.receive(packet);
            long timestamp = (long)((new NtpMessage((byte[])packet.getData()).receiveTimestamp - 2.2089888E9) * 1000.0);
            Date date = new Date(timestamp);
            socket.close();
            return date;
        }
        catch (Throwable throwable) {
            try {
                try {
                    socket.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (Exception e) {
                log.debug("EX1181", e);
                return null;
            }
        }
    }

    private static Date webServerDate(String url2, int timeout2) {
        String response = HTTP.sendGET(url2, timeout2);
        if (response != null) {
            try {
                return new Date(Long.parseLong(response) + (long)TimeZone.getDefault().getRawOffset());
            }
            catch (Exception ex) {
                log.debug("EX1186", ex);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String getLogonServer() {
        if (!ValidatorUtils.getOSName().startsWith("Windows")) return null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("cmd.exe /C set l").getInputStream()));){
            String str;
            do {
                if ((str = reader.readLine()) == null) return null;
            } while (!str.contains("\\\\"));
            String string = str.substring(str.indexOf("\\\\") + 2);
            return string;
        }
        catch (IOException e) {
            log.debug("EX1188", e);
        }
        return null;
    }

    public static String checkLicenseString(String licenseString) {
        if (!(licenseString.contains("0") || licenseString.contains("O") || licenseString.contains("1"))) {
            return ValidatorUtils.getLicenseKey(licenseString.replace('8', '0').replace('9', 'O').replace("-", ""));
        }
        return "";
    }

    private static String getLicenseKey(String licenseString) {
        String startKeyStr = licenseString.substring(0, 5);
        int i = licenseString.length() == 55 ? 10 : 0;
        return new StringBuilder().append(licenseString.charAt(10)).append(licenseString.charAt(i + 13)).append(licenseString.charAt(i + 17)).append(licenseString.charAt(i + 20)).append(licenseString.charAt(i + 23)).append(licenseString, 5, 10).append(startKeyStr.charAt(0)).append(licenseString, 11, i + 13).append(startKeyStr.charAt(1)).append(licenseString, i + 14, i + 17).append(startKeyStr.charAt(2)).append(licenseString, i + 18, i + 20).append(startKeyStr.charAt(3)).append(licenseString, i + 21, i + 23).append(startKeyStr.charAt(4)).append(licenseString.substring(i + 24)).toString();
    }

    public static String updatePublicKey(String publicKey) {
        if (publicKey.contains("SHA512withECDSA")) {
            return publicKey.substring(0, 50) + publicKey.substring(publicKey.indexOf("G") + 1);
        }
        if (publicKey.contains("NOEC")) {
            return publicKey.replace("NOECG", "");
        }
        return publicKey;
    }

    public static boolean checkLicenseString(String licenseString, String internalString) {
        char[] licenseChars = licenseString.toCharArray();
        byte[] internalBytes = Base32.encode(internalString.getBytes(StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < 29; ++i) {
            if (i % 6 != 5 || licenseChars[i] == '-') continue;
            return false;
        }
        byte[] decodedBytes = Base32.decode(ValidatorUtils.checkLicenseString(licenseString));
        byte[] combinedBytes = new byte[5 + internalBytes.length];
        System.arraycopy(decodedBytes, 0, combinedBytes, 0, 5);
        System.arraycopy(internalBytes, 0, combinedBytes, 5, internalBytes.length);
        int checkSum = 17;
        for (byte b : combinedBytes) {
            checkSum += b;
        }
        for (int i = 0; i < 32; ++i) {
            if (VALID_CHARS[i] != licenseChars[28] || (checkSum & 0x1F) == i) continue;
            return false;
        }
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = sha256.digest(combinedBytes);
            for (int i = 0; i < 10; ++i) {
                if (decodedBytes[i + 5] == hashedBytes[i]) continue;
                return false;
            }
        }
        catch (NoSuchAlgorithmException e) {
            log.debug("EX1160", e);
            return true;
        }
        return true;
    }
}

