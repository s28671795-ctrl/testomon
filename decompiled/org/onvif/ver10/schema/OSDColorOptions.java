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
import org.onvif.ver10.schema.ColorOptions;
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.OSDColorOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDColorOptions", propOrder={"color", "transparent", "extension"})
public class OSDColorOptions {
    @XmlElement(name="Color")
    protected ColorOptions color;
    @XmlElement(name="Transparent")
    protected IntRange transparent;
    @XmlElement(name="Extension")
    protected OSDColorOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ColorOptions getColor() {
        return this.color;
    }

    public void setColor(ColorOptions value) {
        this.color = value;
    }

    public IntRange getTransparent() {
        return this.transparent;
    }

    public void setTransparent(IntRange value) {
        this.transparent = value;
    }

    public OSDColorOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(OSDColorOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

