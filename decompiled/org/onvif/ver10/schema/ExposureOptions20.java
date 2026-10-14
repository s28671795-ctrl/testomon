/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ExposureMode;
import org.onvif.ver10.schema.ExposurePriority;
import org.onvif.ver10.schema.FloatRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ExposureOptions20", propOrder={"mode", "priority", "minExposureTime", "maxExposureTime", "minGain", "maxGain", "minIris", "maxIris", "exposureTime", "gain", "iris"})
public class ExposureOptions20 {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<ExposureMode> mode;
    @XmlElement(name="Priority")
    @XmlSchemaType(name="string")
    protected List<ExposurePriority> priority;
    @XmlElement(name="MinExposureTime")
    protected FloatRange minExposureTime;
    @XmlElement(name="MaxExposureTime")
    protected FloatRange maxExposureTime;
    @XmlElement(name="MinGain")
    protected FloatRange minGain;
    @XmlElement(name="MaxGain")
    protected FloatRange maxGain;
    @XmlElement(name="MinIris")
    protected FloatRange minIris;
    @XmlElement(name="MaxIris")
    protected FloatRange maxIris;
    @XmlElement(name="ExposureTime")
    protected FloatRange exposureTime;
    @XmlElement(name="Gain")
    protected FloatRange gain;
    @XmlElement(name="Iris")
    protected FloatRange iris;

    public List<ExposureMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<ExposureMode>();
        }
        return this.mode;
    }

    public List<ExposurePriority> getPriority() {
        if (this.priority == null) {
            this.priority = new ArrayList<ExposurePriority>();
        }
        return this.priority;
    }

    public FloatRange getMinExposureTime() {
        return this.minExposureTime;
    }

    public void setMinExposureTime(FloatRange value) {
        this.minExposureTime = value;
    }

    public FloatRange getMaxExposureTime() {
        return this.maxExposureTime;
    }

    public void setMaxExposureTime(FloatRange value) {
        this.maxExposureTime = value;
    }

    public FloatRange getMinGain() {
        return this.minGain;
    }

    public void setMinGain(FloatRange value) {
        this.minGain = value;
    }

    public FloatRange getMaxGain() {
        return this.maxGain;
    }

    public void setMaxGain(FloatRange value) {
        this.maxGain = value;
    }

    public FloatRange getMinIris() {
        return this.minIris;
    }

    public void setMinIris(FloatRange value) {
        this.minIris = value;
    }

    public FloatRange getMaxIris() {
        return this.maxIris;
    }

    public void setMaxIris(FloatRange value) {
        this.maxIris = value;
    }

    public FloatRange getExposureTime() {
        return this.exposureTime;
    }

    public void setExposureTime(FloatRange value) {
        this.exposureTime = value;
    }

    public FloatRange getGain() {
        return this.gain;
    }

    public void setGain(FloatRange value) {
        this.gain = value;
    }

    public FloatRange getIris() {
        return this.iris;
    }

    public void setIris(FloatRange value) {
        this.iris = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

