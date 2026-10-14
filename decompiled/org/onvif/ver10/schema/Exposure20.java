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
@XmlType(name="Exposure20", propOrder={"mode", "priority", "window", "minExposureTime", "maxExposureTime", "minGain", "maxGain", "minIris", "maxIris", "exposureTime", "gain", "iris"})
public class Exposure20 {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected ExposureMode mode;
    @XmlElement(name="Priority")
    @XmlSchemaType(name="string")
    protected ExposurePriority priority;
    @XmlElement(name="Window")
    protected Rectangle window;
    @XmlElement(name="MinExposureTime")
    protected Float minExposureTime;
    @XmlElement(name="MaxExposureTime")
    protected Float maxExposureTime;
    @XmlElement(name="MinGain")
    protected Float minGain;
    @XmlElement(name="MaxGain")
    protected Float maxGain;
    @XmlElement(name="MinIris")
    protected Float minIris;
    @XmlElement(name="MaxIris")
    protected Float maxIris;
    @XmlElement(name="ExposureTime")
    protected Float exposureTime;
    @XmlElement(name="Gain")
    protected Float gain;
    @XmlElement(name="Iris")
    protected Float iris;

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

    public Float getMinExposureTime() {
        return this.minExposureTime;
    }

    public void setMinExposureTime(Float value) {
        this.minExposureTime = value;
    }

    public Float getMaxExposureTime() {
        return this.maxExposureTime;
    }

    public void setMaxExposureTime(Float value) {
        this.maxExposureTime = value;
    }

    public Float getMinGain() {
        return this.minGain;
    }

    public void setMinGain(Float value) {
        this.minGain = value;
    }

    public Float getMaxGain() {
        return this.maxGain;
    }

    public void setMaxGain(Float value) {
        this.maxGain = value;
    }

    public Float getMinIris() {
        return this.minIris;
    }

    public void setMinIris(Float value) {
        this.minIris = value;
    }

    public Float getMaxIris() {
        return this.maxIris;
    }

    public void setMaxIris(Float value) {
        this.maxIris = value;
    }

    public Float getExposureTime() {
        return this.exposureTime;
    }

    public void setExposureTime(Float value) {
        this.exposureTime = value;
    }

    public Float getGain() {
        return this.gain;
    }

    public void setGain(Float value) {
        this.gain = value;
    }

    public Float getIris() {
        return this.iris;
    }

    public void setIris(Float value) {
        this.iris = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

