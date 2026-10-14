/*
 * Decompiled with CFR 0.152.
 */
package org.xmlsoap.schemas.ws._2005._04.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SigType", propOrder={"any"})
public class SigType {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="Scheme", required=true)
    @XmlSchemaType(name="anyURI")
    protected String scheme;
    @XmlAttribute(name="KeyId")
    protected byte[] keyId;
    @XmlAttribute(name="Refs", required=true)
    @XmlIDREF
    @XmlSchemaType(name="IDREFS")
    protected List<Object> refs;
    @XmlAttribute(name="Sig", required=true)
    protected byte[] sig;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String value) {
        this.scheme = value;
    }

    public byte[] getKeyId() {
        return this.keyId;
    }

    public void setKeyId(byte[] value) {
        this.keyId = value;
    }

    public List<Object> getRefs() {
        if (this.refs == null) {
            this.refs = new ArrayList<Object>();
        }
        return this.refs;
    }

    public byte[] getSig() {
        return this.sig;
    }

    public void setSig(byte[] value) {
        this.sig = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

