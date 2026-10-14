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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FloatRange", propOrder={"min", "max"})
public class FloatRange {
    @XmlElement(name="Min")
    protected float min;
    @XmlElement(name="Max")
    protected float max;

    public float getMin() {
        return this.min;
    }

    public void setMin(float value) {
        this.min = value;
    }

    public float getMax() {
        return this.max;
    }

    public void setMax(float value) {
        this.max = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

