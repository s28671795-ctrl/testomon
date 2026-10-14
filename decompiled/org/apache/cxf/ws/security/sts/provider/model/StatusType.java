/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StatusType", propOrder={"code", "reason"})
public class StatusType {
    @XmlElement(name="Code", required=true)
    protected String code;
    @XmlElement(name="Reason")
    protected String reason;

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }
}

