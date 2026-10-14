/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="LensProjection", propOrder={"angle", "radius", "transmittance", "any"})
public class LensProjection {
    @XmlElement(name="Angle")
    protected float angle;
    @XmlElement(name="Radius")
    protected float radius;
    @XmlElement(name="Transmittance")
    protected Float transmittance;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float value) {
        this.angle = value;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float value) {
        this.radius = value;
    }

    public Float getTransmittance() {
        return this.transmittance;
    }

    public void setTransmittance(Float value) {
        this.transmittance = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

