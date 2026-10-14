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
import org.onvif.ver10.schema.EFlip;
import org.onvif.ver10.schema.PTControlDirectionExtension;
import org.onvif.ver10.schema.Reverse;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTControlDirection", propOrder={"eFlip", "reverse", "extension"})
public class PTControlDirection {
    @XmlElement(name="EFlip")
    protected EFlip eFlip;
    @XmlElement(name="Reverse")
    protected Reverse reverse;
    @XmlElement(name="Extension")
    protected PTControlDirectionExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public EFlip getEFlip() {
        return this.eFlip;
    }

    public void setEFlip(EFlip value) {
        this.eFlip = value;
    }

    public Reverse getReverse() {
        return this.reverse;
    }

    public void setReverse(Reverse value) {
        this.reverse = value;
    }

    public PTControlDirectionExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTControlDirectionExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

