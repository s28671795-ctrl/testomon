/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.BacklightCompensation20;
import org.onvif.ver10.schema.Exposure20;
import org.onvif.ver10.schema.FocusConfiguration20;
import org.onvif.ver10.schema.ImagingSettingsExtension20;
import org.onvif.ver10.schema.IrCutFilterMode;
import org.onvif.ver10.schema.WhiteBalance20;
import org.onvif.ver10.schema.WideDynamicRange20;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingSettings20", propOrder={"backlightCompensation", "brightness", "colorSaturation", "contrast", "exposure", "focus", "irCutFilter", "sharpness", "wideDynamicRange", "whiteBalance", "extension"})
public class ImagingSettings20 {
    @XmlElement(name="BacklightCompensation")
    protected BacklightCompensation20 backlightCompensation;
    @XmlElement(name="Brightness")
    protected Float brightness;
    @XmlElement(name="ColorSaturation")
    protected Float colorSaturation;
    @XmlElement(name="Contrast")
    protected Float contrast;
    @XmlElement(name="Exposure")
    protected Exposure20 exposure;
    @XmlElement(name="Focus")
    protected FocusConfiguration20 focus;
    @XmlElement(name="IrCutFilter")
    @XmlSchemaType(name="string")
    protected IrCutFilterMode irCutFilter;
    @XmlElement(name="Sharpness")
    protected Float sharpness;
    @XmlElement(name="WideDynamicRange")
    protected WideDynamicRange20 wideDynamicRange;
    @XmlElement(name="WhiteBalance")
    protected WhiteBalance20 whiteBalance;
    @XmlElement(name="Extension")
    protected ImagingSettingsExtension20 extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public BacklightCompensation20 getBacklightCompensation() {
        return this.backlightCompensation;
    }

    public void setBacklightCompensation(BacklightCompensation20 value) {
        this.backlightCompensation = value;
    }

    public Float getBrightness() {
        return this.brightness;
    }

    public void setBrightness(Float value) {
        this.brightness = value;
    }

    public Float getColorSaturation() {
        return this.colorSaturation;
    }

    public void setColorSaturation(Float value) {
        this.colorSaturation = value;
    }

    public Float getContrast() {
        return this.contrast;
    }

    public void setContrast(Float value) {
        this.contrast = value;
    }

    public Exposure20 getExposure() {
        return this.exposure;
    }

    public void setExposure(Exposure20 value) {
        this.exposure = value;
    }

    public FocusConfiguration20 getFocus() {
        return this.focus;
    }

    public void setFocus(FocusConfiguration20 value) {
        this.focus = value;
    }

    public IrCutFilterMode getIrCutFilter() {
        return this.irCutFilter;
    }

    public void setIrCutFilter(IrCutFilterMode value) {
        this.irCutFilter = value;
    }

    public Float getSharpness() {
        return this.sharpness;
    }

    public void setSharpness(Float value) {
        this.sharpness = value;
    }

    public WideDynamicRange20 getWideDynamicRange() {
        return this.wideDynamicRange;
    }

    public void setWideDynamicRange(WideDynamicRange20 value) {
        this.wideDynamicRange = value;
    }

    public WhiteBalance20 getWhiteBalance() {
        return this.whiteBalance;
    }

    public void setWhiteBalance(WhiteBalance20 value) {
        this.whiteBalance = value;
    }

    public ImagingSettingsExtension20 getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingSettingsExtension20 value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

