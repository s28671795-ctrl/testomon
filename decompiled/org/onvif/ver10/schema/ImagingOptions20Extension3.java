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
import org.onvif.ver10.schema.DefoggingOptions;
import org.onvif.ver10.schema.ImagingOptions20Extension4;
import org.onvif.ver10.schema.NoiseReductionOptions;
import org.onvif.ver10.schema.ToneCompensationOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingOptions20Extension3", propOrder={"toneCompensationOptions", "defoggingOptions", "noiseReductionOptions", "extension"})
public class ImagingOptions20Extension3 {
    @XmlElement(name="ToneCompensationOptions")
    protected ToneCompensationOptions toneCompensationOptions;
    @XmlElement(name="DefoggingOptions")
    protected DefoggingOptions defoggingOptions;
    @XmlElement(name="NoiseReductionOptions")
    protected NoiseReductionOptions noiseReductionOptions;
    @XmlElement(name="Extension")
    protected ImagingOptions20Extension4 extension;

    public ToneCompensationOptions getToneCompensationOptions() {
        return this.toneCompensationOptions;
    }

    public void setToneCompensationOptions(ToneCompensationOptions value) {
        this.toneCompensationOptions = value;
    }

    public DefoggingOptions getDefoggingOptions() {
        return this.defoggingOptions;
    }

    public void setDefoggingOptions(DefoggingOptions value) {
        this.defoggingOptions = value;
    }

    public NoiseReductionOptions getNoiseReductionOptions() {
        return this.noiseReductionOptions;
    }

    public void setNoiseReductionOptions(NoiseReductionOptions value) {
        this.noiseReductionOptions = value;
    }

    public ImagingOptions20Extension4 getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingOptions20Extension4 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

