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
import org.onvif.ver10.schema.Rotate;
import org.onvif.ver10.schema.VideoSourceConfigurationExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSourceConfigurationExtension", propOrder={"rotate", "extension"})
public class VideoSourceConfigurationExtension {
    @XmlElement(name="Rotate")
    protected Rotate rotate;
    @XmlElement(name="Extension")
    protected VideoSourceConfigurationExtension2 extension;

    public Rotate getRotate() {
        return this.rotate;
    }

    public void setRotate(Rotate value) {
        this.rotate = value;
    }

    public VideoSourceConfigurationExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(VideoSourceConfigurationExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

