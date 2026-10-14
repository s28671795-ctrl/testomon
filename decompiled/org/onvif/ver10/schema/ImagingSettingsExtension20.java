/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ImageStabilization;
import org.onvif.ver10.schema.ImagingSettingsExtension202;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingSettingsExtension20", propOrder={"any", "imageStabilization", "extension"})
public class ImagingSettingsExtension20 {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="ImageStabilization")
    protected ImageStabilization imageStabilization;
    @XmlElement(name="Extension")
    protected ImagingSettingsExtension202 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public ImageStabilization getImageStabilization() {
        return this.imageStabilization;
    }

    public void setImageStabilization(ImageStabilization value) {
        this.imageStabilization = value;
    }

    public ImagingSettingsExtension202 getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingSettingsExtension202 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

