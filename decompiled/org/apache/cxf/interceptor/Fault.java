/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;
import org.apache.cxf.helpers.DOMUtils;
import org.w3c.dom.Element;

public class Fault
extends UncheckedException {
    public static final QName FAULT_CODE_CLIENT = new QName("http://cxf.apache.org/faultcode", "client");
    public static final QName FAULT_CODE_SERVER = new QName("http://cxf.apache.org/faultcode", "server");
    public static final String STACKTRACE_NAMESPACE = "http://cxf.apache.org/fault";
    public static final String STACKTRACE = "stackTrace";
    private static final int DEFAULT_HTTP_RESPONSE_CODE = 500;
    private static final long serialVersionUID = -1583932965031558864L;
    private Element detail;
    private String messageString;
    private QName code;
    private String lang;
    private int statusCode = 500;

    public Fault(Message message, Throwable throwable) {
        super(message, throwable);
        this.messageString = message.toString();
        this.code = FAULT_CODE_SERVER;
    }

    public Fault(Message message) {
        super(message);
        this.messageString = message.toString();
        this.code = FAULT_CODE_SERVER;
    }

    public Fault(String message, Logger log) {
        this(new Message(message, log, new Object[0]));
    }

    public Fault(String message, ResourceBundle b) {
        this(new Message(message, b, new Object[0]));
    }

    public Fault(String message, Logger log, Throwable t2) {
        this(new Message(message, log, new Object[0]), t2);
    }

    public Fault(String message, ResourceBundle b, Throwable t2) {
        this(new Message(message, b, new Object[0]), t2);
    }

    public Fault(String message, Logger log, Throwable t2, Object ... params) {
        this(new Message(message, log, params), t2);
    }

    public Fault(String message, ResourceBundle b, Throwable t2, Object ... params) {
        this(new Message(message, b, params), t2);
    }

    public Fault(Throwable t2) {
        super(t2);
        this.messageString = this.message != null ? this.message.toString() : (t2 == null ? null : t2.getMessage());
        this.code = FAULT_CODE_SERVER;
    }

    public Fault(Message message, Throwable throwable, QName fc) {
        super(message, throwable);
        this.messageString = message.toString();
        this.code = fc;
    }

    public Fault(Message message, QName fc) {
        super(message);
        this.messageString = message.toString();
        this.code = fc;
    }

    public Fault(Throwable t2, QName fc) {
        super(t2);
        this.messageString = this.message != null ? this.message.toString() : (t2 == null ? null : t2.getMessage());
        this.code = fc;
    }

    @Override
    public String getMessage() {
        return this.messageString;
    }

    public void setMessage(String message) {
        this.messageString = message;
    }

    public QName getFaultCode() {
        return this.code;
    }

    public Fault setFaultCode(QName c) {
        this.code = c;
        return this;
    }

    public Element getDetail() {
        return this.detail;
    }

    public void setDetail(Element details) {
        this.detail = details;
    }

    public boolean hasDetails() {
        return this.detail != null;
    }

    public Element getOrCreateDetail() {
        if (this.detail == null) {
            this.detail = DOMUtils.getEmptyDocument().createElement("detail");
        }
        return this.detail;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setLang(String convertedLang) {
        this.lang = convertedLang;
    }

    public String getLang() {
        return this.lang;
    }
}

