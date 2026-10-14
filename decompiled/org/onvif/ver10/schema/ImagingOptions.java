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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.BacklightCompensationOptions;
import org.onvif.ver10.schema.ExposureOptions;
import org.onvif.ver10.schema.FloatRange;
import org.onvif.ver10.schema.FocusOptions;
import org.onvif.ver10.schema.IrCutFilterMode;
import org.onvif.ver10.schema.WhiteBalanceOptions;
import org.onvif.ver10.schema.WideDynamicRangeOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingOptions", propOrder={"backlightCompensation", "brightness", "colorSaturation", "contrast", "exposure", "focus", "irCutFilterModes", "sharpness", "wideDynamicRange", "whiteBalance", "any"})
public class ImagingOptions {
    @XmlElement(name="BacklightCompensation", required=true)
    protected BacklightCompensationOptions backlightCompensation;
    @XmlElement(name="Brightness", required=true)
    protected FloatRange brightness;
    @XmlElement(name="ColorSaturation", required=true)
    protected FloatRange colorSaturation;
    @XmlElement(name="Contrast", required=true)
    protected FloatRange contrast;
    @XmlElement(name="Exposure", required=true)
    protected ExposureOptions exposure;
    @XmlElement(name="Focus", required=true)
    protected FocusOptions focus;
    @XmlElement(name="IrCutFilterModes", required=true)
    @XmlSchemaType(name="string")
    protected List<IrCutFilterMode> irCutFilterModes;
    @XmlElement(name="Sharpness", required=true)
    protected FloatRange sharpness;
    @XmlElement(name="WideDynamicRange", required=true)
    protected WideDynamicRangeOptions wideDynamicRange;
    @XmlElement(name="WhiteBalance", required=true)
    protected WhiteBalanceOptions whiteBalance;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public BacklightCompensationOptions getBacklightCompensation() {
        return this.backlightCompensation;
    }

    public void setBacklightCompensation(BacklightCompensationOptions value) {
        this.backlightCompensation = value;
    }

    public FloatRange getBrightness() {
        return this.brightness;
    }

    public void setBrightness(FloatRange value) {
        this.brightness = value;
    }

    public FloatRange getColorSaturation() {
        return this.colorSaturation;
    }

    public void setColorSaturation(FloatRange value) {
        this.colorSaturation = value;
    }

    public FloatRange getContrast() {
        return this.contrast;
    }

    public void setContrast(FloatRange value) {
        this.contrast = value;
    }

    public ExposureOptions getExposure() {
        return this.exposure;
    }

    public void setExposure(ExposureOptions value) {
        this.exposure = value;
    }

    public FocusOptions getFocus() {
        return this.focus;
    }

    public void setFocus(FocusOptions value) {
        this.focus = value;
    }

    public List<IrCutFilterMode> getIrCutFilterModes() {
        if (this.irCutFilterModes == null) {
            this.irCutFilterModes = new ArrayList<IrCutFilterMode>();
        }
        return this.irCutFilterModes;
    }

    public FloatRange getSharpness() {
        return this.sharpness;
    }

    public void setSharpness(FloatRange value) {
        this.sharpness = value;
    }

    public WideDynamicRangeOptions getWideDynamicRange() {
        return this.wideDynamicRange;
    }

    public void setWideDynamicRange(WideDynamicRangeOptions value) {
        this.wideDynamicRange = value;
    }

    public WhiteBalanceOptions getWhiteBalance() {
        return this.whiteBalance;
    }

    public void setWhiteBalance(WhiteBalanceOptions value) {
        this.whiteBalance = value;
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

