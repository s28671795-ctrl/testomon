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
@XmlType(name="ColorCovariance")
public class ColorCovariance {
    @XmlAttribute(name="XX", required=true)
    protected float xx;
    @XmlAttribute(name="YY", required=true)
    protected float yy;
    @XmlAttribute(name="ZZ", required=true)
    protected float zz;
    @XmlAttribute(name="XY")
    protected Float xy;
    @XmlAttribute(name="XZ")
    protected Float xz;
    @XmlAttribute(name="YZ")
    protected Float yz;
    @XmlAttribute(name="Colorspace")
    @XmlSchemaType(name="anyURI")
    protected String colorspace;

    public float getXX() {
        return this.xx;
    }

    public void setXX(float value) {
        this.xx = value;
    }

    public float getYY() {
        return this.yy;
    }

    public void setYY(float value) {
        this.yy = value;
    }

    public float getZZ() {
        return this.zz;
    }

    public void setZZ(float value) {
        this.zz = value;
    }

    public Float getXY() {
        return this.xy;
    }

    public void setXY(Float value) {
        this.xy = value;
    }

    public Float getXZ() {
        return this.xz;
    }

    public void setXZ(Float value) {
        this.xz = value;
    }

    public Float getYZ() {
        return this.yz;
    }

    public void setYZ(Float value) {
        this.yz = value;
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

