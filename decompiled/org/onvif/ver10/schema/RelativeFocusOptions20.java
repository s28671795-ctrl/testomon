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
import org.onvif.ver10.schema.FloatRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RelativeFocusOptions20", propOrder={"distance", "speed"})
public class RelativeFocusOptions20 {
    @XmlElement(name="Distance", required=true)
    protected FloatRange distance;
    @XmlElement(name="Speed")
    protected FloatRange speed;

    public FloatRange getDistance() {
        return this.distance;
    }

    public void setDistance(FloatRange value) {
        this.distance = value;
    }

    public FloatRange getSpeed() {
        return this.speed;
    }

    public void setSpeed(FloatRange value) {
        this.speed = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

