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
@XmlType(name="", propOrder={"videoSourceToken", "imagingSettings", "forcePersistence"})
@XmlRootElement(name="SetImagingSettings")
public class SetImagingSettings {
    @XmlElement(name="VideoSourceToken", required=true)
    protected String videoSourceToken;
    @XmlElement(name="ImagingSettings", required=true)
    protected ImagingSettings20 imagingSettings;
    @XmlElement(name="ForcePersistence")
    protected Boolean forcePersistence;

    public String getVideoSourceToken() {
        return this.videoSourceToken;
    }

    public void setVideoSourceToken(String value) {
        this.videoSourceToken = value;
    }

    public ImagingSettings20 getImagingSettings() {
        return this.imagingSettings;
    }

    public void setImagingSettings(ImagingSettings20 value) {
        this.imagingSettings = value;
    }

    public Boolean getForcePersistence() {
        return this.forcePersistence;
    }

    public void setForcePersistence(Boolean value) {
        this.forcePersistence = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

