/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.commons.codec.binary.Base64;

public class SimpleSecurityHandler
implements SOAPHandler<SOAPMessageContext> {
    private final String username;
    private final String password;
    private final String nonce;
    private String utcTime;
    private static Random rnd = new SecureRandom();

    public SimpleSecurityHandler(String username, String password) {
        this.username = username;
        this.password = password;
        this.nonce = "" + rnd.nextInt();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext msgCtx) {
        Boolean outInd = (Boolean)msgCtx.get("javax.xml.ws.handler.message.outbound");
        if (outInd.booleanValue()) {
            try {
                SOAPMessage soapMessage = msgCtx.getMessage();
                SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                if (header == null) {
                    header = envelope.addHeader();
                }
                SOAPPart sp2 = soapMessage.getSOAPPart();
                SOAPEnvelope se = sp2.getEnvelope();
                se.addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                se.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                SOAPElement securityElem = header.addChildElement("Security", "wsse");
                SOAPElement usernameTokenElem = securityElem.addChildElement("UsernameToken", "wsse");
                SOAPElement usernameElem = usernameTokenElem.addChildElement("Username", "wsse");
                usernameElem.setTextContent(this.username);
                SOAPElement passwordElem = usernameTokenElem.addChildElement("Password", "wsse");
                passwordElem.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
                passwordElem.setTextContent(this.encryptPassword(this.password));
                SOAPElement nonceElem = usernameTokenElem.addChildElement("Nonce", "wsse");
                nonceElem.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
                nonceElem.setTextContent(Base64.encodeBase64String(this.nonce.getBytes()));
                SOAPElement createdElem = usernameTokenElem.addChildElement("Created", "wsu");
                createdElem.setTextContent(this.getLastUTCTime());
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public String getLastUTCTime() {
        return this.utcTime;
    }

    public String getUTCTime() {
        String utcTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'");
        sdf.setTimeZone(new SimpleTimeZone(2, "UTC"));
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        this.utcTime = utcTime = sdf.format(cal.getTime());
        return utcTime;
    }

    public String encryptPassword(String password) {
        byte[] encryptedRaw;
        String timestamp = this.getUTCTime();
        String beforeEncryption = this.nonce + timestamp + password;
        try {
            encryptedRaw = SimpleSecurityHandler.sha1(beforeEncryption);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        String encoded = Base64.encodeBase64String(encryptedRaw);
        return encoded;
    }

    private static byte[] sha1(String s2) throws NoSuchAlgorithmException {
        MessageDigest SHA12 = MessageDigest.getInstance("SHA-1");
        SHA12.reset();
        SHA12.update(s2.getBytes());
        return SHA12.digest();
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}

