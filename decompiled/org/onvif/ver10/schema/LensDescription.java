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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.LensOffset;
import org.onvif.ver10.schema.LensProjection;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="LensDescription", propOrder={"offset", "projection", "xFactor", "any"})
public class LensDescription {
    @XmlElement(name="Offset", required=true)
    protected LensOffset offset;
    @XmlElement(name="Projection", required=true)
    protected List<LensProjection> projection;
    @XmlElement(name="XFactor")
    protected float xFactor;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="FocalLength")
    protected Float focalLength;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public LensOffset getOffset() {
        return this.offset;
    }

    public void setOffset(LensOffset value) {
        this.offset = value;
    }

    public List<LensProjection> getProjection() {
        if (this.projection == null) {
            this.projection = new ArrayList<LensProjection>();
        }
        return this.projection;
    }

    public float getXFactor() {
        return this.xFactor;
    }

    public void setXFactor(float value) {
        this.xFactor = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Float getFocalLength() {
        return this.focalLength;
    }

    public void setFocalLength(Float value) {
        this.focalLength = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

