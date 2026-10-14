/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.jms.interceptor;

import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.jms.interceptor.JMSFaultType;

public class JMSFault
extends Exception {
    private static final long serialVersionUID = 8567771013189842711L;
    private JMSFaultType jmsFaultType;
    private boolean sender;
    private Object detail;

    public JMSFault(String message) {
        super(message);
    }

    public JMSFault(String message, Throwable cause) {
        super(message, cause);
    }

    public JMSFaultType getJmsFaultType() {
        return this.jmsFaultType;
    }

    public void setJmsFaultType(JMSFaultType jmsFaultType) {
        this.jmsFaultType = jmsFaultType;
    }

    public boolean isSender() {
        return this.sender;
    }

    public void setSender(boolean sender) {
        this.sender = sender;
    }

    public Object getDetail() {
        return this.detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public QName getSubCode() {
        return this.jmsFaultType.faultCode;
    }

    public String getReason() {
        return this.getMessage();
    }
}

