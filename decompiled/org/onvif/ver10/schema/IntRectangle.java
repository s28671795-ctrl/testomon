/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IntRectangle")
public class IntRectangle {
    @XmlAttribute(name="x", required=true)
    protected int x;
    @XmlAttribute(name="y", required=true)
    protected int y;
    @XmlAttribute(name="width", required=true)
    protected int width;
    @XmlAttribute(name="height", required=true)
    protected int height;

    public int getX() {
        return this.x;
    }

    public void setX(int value) {
        this.x = value;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int value) {
        this.y = value;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int value) {
        this.width = value;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

