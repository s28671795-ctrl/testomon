/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Color;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDColor", propOrder={"color"})
public class OSDColor {
    @XmlElement(name="Color", required=true)
    protected Color color;
    @XmlAttribute(name="Transparent")
    protected Integer transparent;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color value) {
        this.color = value;
    }

    public Integer getTransparent() {
        return this.transparent;
    }

    public void setTransparent(Integer value) {
        this.transparent = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

