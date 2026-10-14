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
import org.onvif.ver10.schema.WhiteBalance20Extension;
import org.onvif.ver10.schema.WhiteBalanceMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="WhiteBalance20", propOrder={"mode", "crGain", "cbGain", "extension"})
public class WhiteBalance20 {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected WhiteBalanceMode mode;
    @XmlElement(name="CrGain")
    protected Float crGain;
    @XmlElement(name="CbGain")
    protected Float cbGain;
    @XmlElement(name="Extension")
    protected WhiteBalance20Extension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public WhiteBalanceMode getMode() {
        return this.mode;
    }

    public void setMode(WhiteBalanceMode value) {
        this.mode = value;
    }

    public Float getCrGain() {
        return this.crGain;
    }

    public void setCrGain(Float value) {
        this.crGain = value;
    }

    public Float getCbGain() {
        return this.cbGain;
    }

    public void setCbGain(Float value) {
        this.cbGain = value;
    }

    public WhiteBalance20Extension getExtension() {
        return this.extension;
    }

    public void setExtension(WhiteBalance20Extension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

