/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AuthenticatorType", propOrder={"combinedHash", "any"})
public class AuthenticatorType {
    @XmlElement(name="CombinedHash")
    protected byte[] combinedHash;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public byte[] getCombinedHash() {
        return this.combinedHash;
    }

    public void setCombinedHash(byte[] value) {
        this.combinedHash = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }
}

