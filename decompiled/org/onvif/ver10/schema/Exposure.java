/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ExposureMode;
import org.onvif.ver10.schema.ExposurePriority;
import org.onvif.ver10.schema.Rectangle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Exposure", propOrder={"mode", "priority", "window", "minExposureTime", "maxExposureTime", "minGain", "maxGain", "minIris", "maxIris", "exposureTime", "gain", "iris"})
public class Exposure {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected ExposureMode mode;
    @XmlElement(name="Priority", required=true)
    @XmlSchemaType(name="string")
    protected ExposurePriority priority;
    @XmlElement(name="Window", required=true)
    protected Rectangle window;
    @XmlElement(name="MinExposureTime")
    protected float minExposureTime;
    @XmlElement(name="MaxExposureTime")
    protected float maxExposureTime;
    @XmlElement(name="MinGain")
    protected float minGain;
    @XmlElement(name="MaxGain")
    protected float maxGain;
    @XmlElement(name="MinIris")
    protected float minIris;
    @XmlElement(name="MaxIris")
    protected float maxIris;
    @XmlElement(name="ExposureTime")
    protected float exposureTime;
    @XmlElement(name="Gain")
    protected float gain;
    @XmlElement(name="Iris")
    protected float iris;

    public ExposureMode getMode() {
        return this.mode;
    }

    public void setMode(ExposureMode value) {
        this.mode = value;
    }

    public ExposurePriority getPriority() {
        return this.priority;
    }

    public void setPriority(ExposurePriority value) {
        this.priority = value;
    }

    public Rectangle getWindow() {
        return this.window;
    }

    public void setWindow(Rectangle value) {
        this.window = value;
    }

    public float getMinExposureTime() {
        return this.minExposureTime;
    }

    public void setMinExposureTime(float value) {
        this.minExposureTime = value;
    }

    public float getMaxExposureTime() {
        return this.maxExposureTime;
    }

    public void setMaxExposureTime(float value) {
        this.maxExposureTime = value;
    }

    public float getMinGain() {
        return this.minGain;
    }

    public void setMinGain(float value) {
        this.minGain = value;
    }

    public float getMaxGain() {
        return this.maxGain;
    }

    public void setMaxGain(float value) {
        this.maxGain = value;
    }

    public float getMinIris() {
        return this.minIris;
    }

    public void setMinIris(float value) {
        this.minIris = value;
    }

    public float getMaxIris() {
        return this.maxIris;
    }

    public void setMaxIris(float value) {
        this.maxIris = value;
    }

    public float getExposureTime() {
        return this.exposureTime;
    }

    public void setExposureTime(float value) {
        this.exposureTime = value;
    }

    public float getGain() {
        return this.gain;
    }

    public void setGain(float value) {
        this.gain = value;
    }

    public float getIris() {
        return this.iris;
    }

    public void setIris(float value) {
        this.iris = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

