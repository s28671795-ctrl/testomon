/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="UseKeyType", propOrder={"any"})
public class UseKeyType {
    @XmlAnyElement(lax=true)
    protected Object any;
    @XmlAttribute(name="Sig")
    @XmlSchemaType(name="anyURI")
    protected String sig;

    public Object getAny() {
        return this.any;
    }

    public void setAny(Object value) {
        this.any = value;
    }

    public String getSig() {
        return this.sig;
    }

    public void setSig(String value) {
        this.sig = value;
    }
}

