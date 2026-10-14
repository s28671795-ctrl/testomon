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
@XmlType(name="Vector1D")
public class Vector1D {
    @XmlAttribute(name="x", required=true)
    protected float x;
    @XmlAttribute(name="space")
    @XmlSchemaType(name="anyURI")
    protected String space;

    public float getX() {
        return this.x;
    }

    public void setX(float value) {
        this.x = value;
    }

    public String getSpace() {
        return this.space;
    }

    public void setSpace(String value) {
        this.space = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

