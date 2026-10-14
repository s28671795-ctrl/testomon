/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProofEncryptionType", propOrder={"any"})
public class ProofEncryptionType {
    @XmlAnyElement(lax=true)
    protected Object any;

    public Object getAny() {
        return this.any;
    }

    public void setAny(Object value) {
        this.any = value;
    }
}

