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
@XmlType(name="Rectangle")
public class Rectangle {
    @XmlAttribute(name="bottom")
    protected Float bottom;
    @XmlAttribute(name="top")
    protected Float top;
    @XmlAttribute(name="right")
    protected Float right;
    @XmlAttribute(name="left")
    protected Float left;

    public Float getBottom() {
        return this.bottom;
    }

    public void setBottom(Float value) {
        this.bottom = value;
    }

    public Float getTop() {
        return this.top;
    }

    public void setTop(Float value) {
        this.top = value;
    }

    public Float getRight() {
        return this.right;
    }

    public void setRight(Float value) {
        this.right = value;
    }

    public Float getLeft() {
        return this.left;
    }

    public void setLeft(Float value) {
        this.left = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

