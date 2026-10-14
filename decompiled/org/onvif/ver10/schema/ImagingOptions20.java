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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.BacklightCompensationOptions20;
import org.onvif.ver10.schema.ExposureOptions20;
import org.onvif.ver10.schema.FloatRange;
import org.onvif.ver10.schema.FocusOptions20;
import org.onvif.ver10.schema.ImagingOptions20Extension;
import org.onvif.ver10.schema.IrCutFilterMode;
import org.onvif.ver10.schema.WhiteBalanceOptions20;
import org.onvif.ver10.schema.WideDynamicRangeOptions20;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingOptions20", propOrder={"backlightCompensation", "brightness", "colorSaturation", "contrast", "exposure", "focus", "irCutFilterModes", "sharpness", "wideDynamicRange", "whiteBalance", "extension"})
public class ImagingOptions20 {
    @XmlElement(name="BacklightCompensation")
    protected BacklightCompensationOptions20 backlightCompensation;
    @XmlElement(name="Brightness")
    protected FloatRange brightness;
    @XmlElement(name="ColorSaturation")
    protected FloatRange colorSaturation;
    @XmlElement(name="Contrast")
    protected FloatRange contrast;
    @XmlElement(name="Exposure")
    protected ExposureOptions20 exposure;
    @XmlElement(name="Focus")
    protected FocusOptions20 focus;
    @XmlElement(name="IrCutFilterModes")
    @XmlSchemaType(name="string")
    protected List<IrCutFilterMode> irCutFilterModes;
    @XmlElement(name="Sharpness")
    protected FloatRange sharpness;
    @XmlElement(name="WideDynamicRange")
    protected WideDynamicRangeOptions20 wideDynamicRange;
    @XmlElement(name="WhiteBalance")
    protected WhiteBalanceOptions20 whiteBalance;
    @XmlElement(name="Extension")
    protected ImagingOptions20Extension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public BacklightCompensationOptions20 getBacklightCompensation() {
        return this.backlightCompensation;
    }

    public void setBacklightCompensation(BacklightCompensationOptions20 value) {
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

    public ExposureOptions20 getExposure() {
        return this.exposure;
    }

    public void setExposure(ExposureOptions20 value) {
        this.exposure = value;
    }

    public FocusOptions20 getFocus() {
        return this.focus;
    }

    public void setFocus(FocusOptions20 value) {
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

    public WideDynamicRangeOptions20 getWideDynamicRange() {
        return this.wideDynamicRange;
    }

    public void setWideDynamicRange(WideDynamicRangeOptions20 value) {
        this.wideDynamicRange = value;
    }

    public WhiteBalanceOptions20 getWhiteBalance() {
        return this.whiteBalance;
    }

    public void setWhiteBalance(WhiteBalanceOptions20 value) {
        this.whiteBalance = value;
    }

    public ImagingOptions20Extension getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingOptions20Extension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

