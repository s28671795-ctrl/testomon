/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.exceptions;

import java.text.MessageFormat;
import org.apache.xml.security.utils.I18n;

public class XMLSecurityRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String msgID;

    public XMLSecurityRuntimeException() {
        super("Missing message string");
        this.msgID = null;
    }

    public XMLSecurityRuntimeException(String msgID) {
        super(I18n.getExceptionMessage(msgID));
        this.msgID = msgID;
    }

    public XMLSecurityRuntimeException(String msgID, Object[] exArgs) {
        super(MessageFormat.format(I18n.getExceptionMessage(msgID), exArgs));
        this.msgID = msgID;
    }

    public XMLSecurityRuntimeException(Exception originalException) {
        super("Missing message ID to locate message string in resource bundle \"org/apache/xml/security/resource/xmlsecurity\". Original Exception was a " + originalException.getClass().getName() + " and message " + originalException.getMessage(), originalException);
    }

    public XMLSecurityRuntimeException(String msgID, Exception originalException) {
        super(I18n.getExceptionMessage(msgID, originalException), originalException);
        this.msgID = msgID;
    }

    public XMLSecurityRuntimeException(String msgID, Object[] exArgs, Exception originalException) {
        super(MessageFormat.format(I18n.getExceptionMessage(msgID), exArgs), originalException);
        this.msgID = msgID;
    }

    public String getMsgID() {
        if (this.msgID == null) {
            return "Missing message ID";
        }
        return this.msgID;
    }

    @Override
    public String toString() {
        String s2 = this.getClass().getName();
        String message = super.getLocalizedMessage();
        message = message != null ? s2 + ": " + message : s2;
        if (this.getCause() != null) {
            message = message + "\nOriginal Exception was " + this.getCause().toString();
        }
        return message;
    }

    public Exception getOriginalException() {
        if (this.getCause() instanceof Exception) {
            return (Exception)this.getCause();
        }
        return null;
    }
}

