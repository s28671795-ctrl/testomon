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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Dot11Cipher;
import org.onvif.ver10.schema.Dot11PSKSet;
import org.onvif.ver10.schema.Dot11SecurityConfigurationExtension;
import org.onvif.ver10.schema.Dot11SecurityMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Dot11SecurityConfiguration", propOrder={"mode", "algorithm", "psk", "dot1X", "extension"})
public class Dot11SecurityConfiguration {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected Dot11SecurityMode mode;
    @XmlElement(name="Algorithm")
    @XmlSchemaType(name="string")
    protected Dot11Cipher algorithm;
    @XmlElement(name="PSK")
    protected Dot11PSKSet psk;
    @XmlElement(name="Dot1X")
    protected String dot1X;
    @XmlElement(name="Extension")
    protected Dot11SecurityConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Dot11SecurityMode getMode() {
        return this.mode;
    }

    public void setMode(Dot11SecurityMode value) {
        this.mode = value;
    }

    public Dot11Cipher getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(Dot11Cipher value) {
        this.algorithm = value;
    }

    public Dot11PSKSet getPSK() {
        return this.psk;
    }

    public void setPSK(Dot11PSKSet value) {
        this.psk = value;
    }

    public String getDot1X() {
        return this.dot1X;
    }

    public void setDot1X(String value) {
        this.dot1X = value;
    }

    public Dot11SecurityConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(Dot11SecurityConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

