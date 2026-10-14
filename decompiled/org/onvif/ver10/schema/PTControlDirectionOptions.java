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
import org.onvif.ver10.schema.EFlipOptions;
import org.onvif.ver10.schema.PTControlDirectionOptionsExtension;
import org.onvif.ver10.schema.ReverseOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTControlDirectionOptions", propOrder={"eFlip", "reverse", "extension"})
public class PTControlDirectionOptions {
    @XmlElement(name="EFlip")
    protected EFlipOptions eFlip;
    @XmlElement(name="Reverse")
    protected ReverseOptions reverse;
    @XmlElement(name="Extension")
    protected PTControlDirectionOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public EFlipOptions getEFlip() {
        return this.eFlip;
    }

    public void setEFlip(EFlipOptions value) {
        this.eFlip = value;
    }

    public ReverseOptions getReverse() {
        return this.reverse;
    }

    public void setReverse(ReverseOptions value) {
        this.reverse = value;
    }

    public PTControlDirectionOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTControlDirectionOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

