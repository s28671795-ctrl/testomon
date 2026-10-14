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
@XmlType(name="IntRange", propOrder={"min", "max"})
public class IntRange {
    @XmlElement(name="Min")
    protected int min;
    @XmlElement(name="Max")
    protected int max;

    public int getMin() {
        return this.min;
    }

    public void setMin(int value) {
        this.min = value;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int value) {
        this.max = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

