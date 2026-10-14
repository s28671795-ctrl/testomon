/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.EapMethodExtension;
import org.onvif.ver10.schema.TLSConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EAPMethodConfiguration", propOrder={"tlsConfiguration", "password", "extension"})
public class EAPMethodConfiguration {
    @XmlElement(name="TLSConfiguration")
    protected TLSConfiguration tlsConfiguration;
    @XmlElement(name="Password")
    protected String password;
    @XmlElement(name="Extension")
    protected EapMethodExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public TLSConfiguration getTLSConfiguration() {
        return this.tlsConfiguration;
    }

    public void setTLSConfiguration(TLSConfiguration value) {
        this.tlsConfiguration = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public EapMethodExtension getExtension() {
        return this.extension;
    }

    public void setExtension(EapMethodExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

