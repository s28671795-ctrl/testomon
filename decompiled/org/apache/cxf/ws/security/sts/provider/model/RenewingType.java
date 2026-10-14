/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RenewingType")
public class RenewingType {
    @XmlAttribute(name="Allow")
    protected Boolean allow;
    @XmlAttribute(name="OK")
    protected Boolean ok;

    public Boolean isAllow() {
        return this.allow;
    }

    public void setAllow(Boolean value) {
        this.allow = value;
    }

    public Boolean isOK() {
        return this.ok;
    }

    public void setOK(Boolean value) {
        this.ok = value;
    }
}

