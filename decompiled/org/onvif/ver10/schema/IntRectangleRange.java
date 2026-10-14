/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IntRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IntRectangleRange", propOrder={"xRange", "yRange", "widthRange", "heightRange"})
public class IntRectangleRange {
    @XmlElement(name="XRange", required=true)
    protected IntRange xRange;
    @XmlElement(name="YRange", required=true)
    protected IntRange yRange;
    @XmlElement(name="WidthRange", required=true)
    protected IntRange widthRange;
    @XmlElement(name="HeightRange", required=true)
    protected IntRange heightRange;

    public IntRange getXRange() {
        return this.xRange;
    }

    public void setXRange(IntRange value) {
        this.xRange = value;
    }

    public IntRange getYRange() {
        return this.yRange;
    }

    public void setYRange(IntRange value) {
        this.yRange = value;
    }

    public IntRange getWidthRange() {
        return this.widthRange;
    }

    public void setWidthRange(IntRange value) {
        this.widthRange = value;
    }

    public IntRange getHeightRange() {
        return this.heightRange;
    }

    public void setHeightRange(IntRange value) {
        this.heightRange = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

