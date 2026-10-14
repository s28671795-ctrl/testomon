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
import org.onvif.ver10.schema.Defogging;
import org.onvif.ver10.schema.ImagingSettingsExtension204;
import org.onvif.ver10.schema.NoiseReduction;
import org.onvif.ver10.schema.ToneCompensation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingSettingsExtension203", propOrder={"toneCompensation", "defogging", "noiseReduction", "extension"})
public class ImagingSettingsExtension203 {
    @XmlElement(name="ToneCompensation")
    protected ToneCompensation toneCompensation;
    @XmlElement(name="Defogging")
    protected Defogging defogging;
    @XmlElement(name="NoiseReduction")
    protected NoiseReduction noiseReduction;
    @XmlElement(name="Extension")
    protected ImagingSettingsExtension204 extension;

    public ToneCompensation getToneCompensation() {
        return this.toneCompensation;
    }

    public void setToneCompensation(ToneCompensation value) {
        this.toneCompensation = value;
    }

    public Defogging getDefogging() {
        return this.defogging;
    }

    public void setDefogging(Defogging value) {
        this.defogging = value;
    }

    public NoiseReduction getNoiseReduction() {
        return this.noiseReduction;
    }

    public void setNoiseReduction(NoiseReduction value) {
        this.noiseReduction = value;
    }

    public ImagingSettingsExtension204 getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingSettingsExtension204 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

