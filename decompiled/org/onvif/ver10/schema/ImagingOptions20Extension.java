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
import org.onvif.ver10.schema.ImageStabilizationOptions;
import org.onvif.ver10.schema.ImagingOptions20Extension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingOptions20Extension", propOrder={"any", "imageStabilization", "extension"})
public class ImagingOptions20Extension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="ImageStabilization")
    protected ImageStabilizationOptions imageStabilization;
    @XmlElement(name="Extension")
    protected ImagingOptions20Extension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public ImageStabilizationOptions getImageStabilization() {
        return this.imageStabilization;
    }

    public void setImageStabilization(ImageStabilizationOptions value) {
        this.imageStabilization = value;
    }

    public ImagingOptions20Extension2 getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingOptions20Extension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

