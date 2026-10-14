/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.ext;

import javax.xml.namespace.QName;
import org.apache.xml.security.exceptions.XMLSecurityException;

public class WSSecurityException
extends XMLSecurityException {
    private static final long serialVersionUID = 4703352039717763655L;
    public static final String NS_WSSE10 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    public static final QName UNSUPPORTED_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UnsupportedSecurityToken");
    public static final QName UNSUPPORTED_ALGORITHM = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UnsupportedAlgorithm");
    public static final QName INVALID_SECURITY = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "InvalidSecurity");
    public static final QName INVALID_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "InvalidSecurityToken");
    public static final QName FAILED_AUTHENTICATION = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "FailedAuthentication");
    public static final QName FAILED_CHECK = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "FailedCheck");
    public static final QName SECURITY_TOKEN_UNAVAILABLE = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenUnavailable");
    public static final QName MESSAGE_EXPIRED = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "MessageExpired");
    public static final QName SECURITY_ERROR = new QName("http://ws.apache.org/wss4j", "SecurityError");
    public static final String UNSUPPORTED_TOKEN_ERR = "An unsupported token was provided";
    public static final String UNSUPPORTED_ALGORITHM_ERR = "An unsupported signature or encryption algorithm was used";
    public static final String INVALID_SECURITY_ERR = "An error was discovered processing the <wsse:Security> header.";
    public static final String INVALID_SECURITY_TOKEN_ERR = "An invalid security token was provided";
    public static final String FAILED_AUTHENTICATION_ERR = "The security token could not be authenticated or authorized";
    public static final String FAILED_CHECK_ERR = "The signature or decryption was invalid";
    public static final String SECURITY_TOKEN_UNAVAILABLE_ERR = "Referenced security token could not be retrieved";
    public static final String MESSAGE_EXPIRED_ERR = "The message has expired";
    public static final String UNIFIED_SECURITY_ERR = "A security error was encountered when verifying the message";
    private ErrorCode errorCode;

    public WSSecurityException(ErrorCode errorCode) {
        this(errorCode, errorCode.name());
    }

    public WSSecurityException(ErrorCode errorCode, String msgId) {
        super(msgId, new Object[0]);
        this.errorCode = errorCode;
    }

    public WSSecurityException(ErrorCode errorCode, Exception exception) {
        super(exception);
        this.errorCode = errorCode;
    }

    public WSSecurityException(ErrorCode errorCode, Exception exception, String msgId) {
        super(exception, msgId);
        this.errorCode = errorCode;
    }

    public WSSecurityException(ErrorCode errorCode, Exception exception, String msgId, Object[] arguments) {
        super(exception, msgId, arguments);
        this.errorCode = errorCode;
    }

    public WSSecurityException(ErrorCode errorCode, String msgId, Object[] arguments) {
        super(msgId, arguments);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public QName getFaultCode() {
        return this.errorCode.getQName();
    }

    public String getSafeExceptionMessage() {
        return UNIFIED_SECURITY_ERR;
    }

    public QName getSafeFaultCode() {
        return SECURITY_ERROR;
    }

    public static enum ErrorCode {
        FAILURE(null),
        UNSUPPORTED_SECURITY_TOKEN(UNSUPPORTED_SECURITY_TOKEN),
        UNSUPPORTED_ALGORITHM(UNSUPPORTED_ALGORITHM),
        INVALID_SECURITY(INVALID_SECURITY),
        INVALID_SECURITY_TOKEN(INVALID_SECURITY_TOKEN),
        FAILED_AUTHENTICATION(FAILED_AUTHENTICATION),
        FAILED_CHECK(FAILED_CHECK),
        SECURITY_TOKEN_UNAVAILABLE(SECURITY_TOKEN_UNAVAILABLE),
        MESSAGE_EXPIRED(MESSAGE_EXPIRED),
        FAILED_ENCRYPTION(null),
        FAILED_SIGNATURE(null),
        SECURITY_ERROR(SECURITY_ERROR);

        private QName qName;

        private ErrorCode(QName qName) {
            this.qName = qName;
        }

        public QName getQName() {
            return this.qName;
        }
    }
}

