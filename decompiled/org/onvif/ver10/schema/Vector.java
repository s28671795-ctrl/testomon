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
@XmlType(name="Vector")
public class Vector {
    @XmlAttribute(name="x")
    protected Float x;
    @XmlAttribute(name="y")
    protected Float y;

    public Float getX() {
        return this.x;
    }

    public void setX(Float value) {
        this.x = value;
    }

    public Float getY() {
        return this.y;
    }

    public void setY(Float value) {
        this.y = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

