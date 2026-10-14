/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

import com.burgstaller.okhttp.digest.Credentials;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class OnvifXMLBuilder {
    public static final String TAG = OnvifXMLBuilder.class.getSimpleName();

    public static byte[] createNonce() {
        byte[] nonce = new byte[20];
        for (int i = 0; i < 20; ++i) {
            nonce[i] = (byte)Math.round(Math.random() * 127.0);
        }
        return nonce;
    }

    public static String getUTCTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss.S'Z'");
        sdf.setTimeZone(new SimpleTimeZone(2, "UTC"));
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        return sdf.format(cal.getTime());
    }

    public static String encryptPassword(byte[] nonce, String timestamp, String password) {
        byte[] encryptedRaw;
        byte[] beforeEncryption = ByteBuffer.allocate(nonce.length + timestamp.length() + password.length()).put(nonce).put(timestamp.getBytes()).put(password.getBytes()).array();
        try {
            encryptedRaw = OnvifXMLBuilder.sha1(beforeEncryption);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(encryptedRaw);
    }

    private static byte[] sha1(byte[] s2) throws NoSuchAlgorithmException {
        MessageDigest SHA12 = MessageDigest.getInstance("SHA1");
        SHA12.reset();
        SHA12.update(s2);
        return SHA12.digest();
    }

    public static String getSoapHeader(Credentials credentials) {
        String userName = credentials.getUserName();
        byte[] nonce = OnvifXMLBuilder.createNonce();
        String timestamp = OnvifXMLBuilder.getUTCTime();
        String encryptedPassword = OnvifXMLBuilder.encryptPassword(nonce, timestamp, credentials.getPassword());
        String encryptedNonce = Base64.getEncoder().encodeToString(nonce);
        return "<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\"><s:Header><Security s:mustUnderstand=\"1\" xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><UsernameToken><Username>" + userName + "</Username><Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + encryptedPassword + "</Password><Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">" + encryptedNonce + "</Nonce><Created xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" + timestamp + "</Created></UsernameToken></Security></s:Header><s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";
    }

    public static String getEnvelopeEnd() {
        return "</s:Body></s:Envelope>";
    }

    public static String getDiscoverySoapHeader() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\"><soap:Header><wsa:Action>http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action><wsa:MessageID>urn:uuid:%s</wsa:MessageID>\n<wsa:To>urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To>\n</soap:Header><soap:Body>";
    }

    public static String getDiscoverySoapBody() {
        return "<tns:Probe/>";
    }
}

