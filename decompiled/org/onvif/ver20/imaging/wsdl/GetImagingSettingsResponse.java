/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.imaging.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ImagingSettings20;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"imagingSettings"})
@XmlRootElement(name="GetImagingSettingsResponse")
public class GetImagingSettingsResponse {
    @XmlElement(name="ImagingSettings", required=true)
    protected ImagingSettings20 imagingSettings;

    public ImagingSettings20 getImagingSettings() {
        return this.imagingSettings;
    }

    public void setImagingSettings(ImagingSettings20 value) {
        this.imagingSettings = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

