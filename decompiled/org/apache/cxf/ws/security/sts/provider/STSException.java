/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

public class STSException
extends RuntimeException {
    public static final String WST_NS_05_12 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
    public static final QName INVALID_REQUEST = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidRequest");
    public static final QName FAILED_AUTH = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "FailedAuthentication");
    public static final QName REQUEST_FAILED = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestFailed");
    public static final QName INVALID_TOKEN = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidSecurityToken");
    public static final QName AUTH_BAD_ELEMENTS = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticationBadElements");
    public static final QName BAD_REQUEST = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BadRequest");
    public static final QName EXPIRED_DATA = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ExpiredData");
    public static final QName INVALID_TIME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidTimeRange");
    public static final QName INVALID_SCOPE = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidScope");
    public static final QName RENEW_NEEDED = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewNeeded");
    public static final QName UNABLE_TO_RENEW = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UnableToRenew");
    private static final Map<QName, String> FAULT_CODE_MAP = new HashMap<QName, String>();
    private static final long serialVersionUID = 2186924985128534490L;
    private QName faultCode;

    public STSException(String message) {
        super(message);
    }

    public STSException(String message, QName faultCode) {
        super(message);
        this.faultCode = faultCode;
    }

    public STSException(String message, Throwable e) {
        super(message, e);
    }

    public STSException(String message, Throwable e, QName faultCode) {
        super(message, e);
        this.faultCode = faultCode;
    }

    public void setFaultCode(QName faultCode) {
        this.faultCode = faultCode;
    }

    public QName getFaultCode() {
        return this.faultCode;
    }

    @Override
    public String getMessage() {
        if (this.faultCode != null && FAULT_CODE_MAP.get(this.faultCode) != null) {
            return FAULT_CODE_MAP.get(this.faultCode);
        }
        return super.getMessage();
    }

    static {
        FAULT_CODE_MAP.put(INVALID_REQUEST, "The request was invalid or malformed");
        FAULT_CODE_MAP.put(FAILED_AUTH, "Authentication failed");
        FAULT_CODE_MAP.put(REQUEST_FAILED, "The specified request failed");
        FAULT_CODE_MAP.put(INVALID_TOKEN, "Security token has been revoked");
        FAULT_CODE_MAP.put(AUTH_BAD_ELEMENTS, "Insufficient Digest Elements");
        FAULT_CODE_MAP.put(BAD_REQUEST, "The specified RequestSecurityToken is not understood");
        FAULT_CODE_MAP.put(EXPIRED_DATA, "The request data is out-of-date");
        FAULT_CODE_MAP.put(INVALID_TIME, "The requested time range is invalid or unsupported");
        FAULT_CODE_MAP.put(INVALID_SCOPE, "The request scope is invalid or unsupported");
        FAULT_CODE_MAP.put(RENEW_NEEDED, "A renewable security token has expired");
        FAULT_CODE_MAP.put(UNABLE_TO_RENEW, "The requested renewal failed");
    }
}

