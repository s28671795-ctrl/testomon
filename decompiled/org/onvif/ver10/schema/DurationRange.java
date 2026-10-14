/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DurationRange", propOrder={"min", "max"})
public class DurationRange {
    @XmlElement(name="Min", required=true)
    protected Duration min;
    @XmlElement(name="Max", required=true)
    protected Duration max;

    public Duration getMin() {
        return this.min;
    }

    public void setMin(Duration value) {
        this.min = value;
    }

    public Duration getMax() {
        return this.max;
    }

    public void setMax(Duration value) {
        this.max = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

