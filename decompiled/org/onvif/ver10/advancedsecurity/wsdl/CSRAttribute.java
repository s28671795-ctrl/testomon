/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.BasicRequestAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.X509V3Extension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CSRAttribute", propOrder={"x509V3Extension", "basicRequestAttribute", "anyAttribute"})
public class CSRAttribute {
    @XmlElement(name="X509v3Extension")
    protected X509V3Extension x509V3Extension;
    @XmlElement(name="BasicRequestAttribute")
    protected BasicRequestAttribute basicRequestAttribute;
    protected AnyAttribute anyAttribute;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public X509V3Extension getX509V3Extension() {
        return this.x509V3Extension;
    }

    public void setX509V3Extension(X509V3Extension value) {
        this.x509V3Extension = value;
    }

    public BasicRequestAttribute getBasicRequestAttribute() {
        return this.basicRequestAttribute;
    }

    public void setBasicRequestAttribute(BasicRequestAttribute value) {
        this.basicRequestAttribute = value;
    }

    public AnyAttribute getAnyAttribute() {
        return this.anyAttribute;
    }

    public void setAnyAttribute(AnyAttribute value) {
        this.anyAttribute = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class AnyAttribute {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

        public List<Object> getAny() {
            if (this.any == null) {
                this.any = new ArrayList<Object>();
            }
            return this.any;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

