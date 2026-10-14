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
import org.onvif.ver10.schema.ImagingSettings20;
import org.onvif.ver10.schema.VideoSourceExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSourceExtension", propOrder={"any", "imaging", "extension"})
public class VideoSourceExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Imaging")
    protected ImagingSettings20 imaging;
    @XmlElement(name="Extension")
    protected VideoSourceExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public ImagingSettings20 getImaging() {
        return this.imaging;
    }

    public void setImaging(ImagingSettings20 value) {
        this.imaging = value;
    }

    public VideoSourceExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(VideoSourceExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

