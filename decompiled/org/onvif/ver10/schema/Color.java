/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Color")
public class Color {
    @XmlAttribute(name="X", required=true)
    protected float x;
    @XmlAttribute(name="Y", required=true)
    protected float y;
    @XmlAttribute(name="Z", required=true)
    protected float z;
    @XmlAttribute(name="Colorspace")
    @XmlSchemaType(name="anyURI")
    protected String colorspace;

    public float getX() {
        return this.x;
    }

    public void setX(float value) {
        this.x = value;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float value) {
        this.y = value;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float value) {
        this.z = value;
    }

    public String getColorspace() {
        return this.colorspace;
    }

    public void setColorspace(String value) {
        this.colorspace = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

