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
@XmlType(name="RelativeFocus", propOrder={"distance", "speed"})
public class RelativeFocus {
    @XmlElement(name="Distance")
    protected float distance;
    @XmlElement(name="Speed")
    protected Float speed;

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float value) {
        this.distance = value;
    }

    public Float getSpeed() {
        return this.speed;
    }

    public void setSpeed(Float value) {
        this.speed = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

