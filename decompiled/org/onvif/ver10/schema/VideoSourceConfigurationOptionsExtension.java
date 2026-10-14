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
import org.onvif.ver10.schema.RotateOptions;
import org.onvif.ver10.schema.VideoSourceConfigurationOptionsExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSourceConfigurationOptionsExtension", propOrder={"any", "rotate", "extension"})
public class VideoSourceConfigurationOptionsExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Rotate")
    protected RotateOptions rotate;
    @XmlElement(name="Extension")
    protected VideoSourceConfigurationOptionsExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public RotateOptions getRotate() {
        return this.rotate;
    }

    public void setRotate(RotateOptions value) {
        this.rotate = value;
    }

    public VideoSourceConfigurationOptionsExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(VideoSourceConfigurationOptionsExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

