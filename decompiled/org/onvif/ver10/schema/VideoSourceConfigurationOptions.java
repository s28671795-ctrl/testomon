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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IntRectangleRange;
import org.onvif.ver10.schema.VideoSourceConfigurationOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSourceConfigurationOptions", propOrder={"boundsRange", "videoSourceTokensAvailable", "extension"})
public class VideoSourceConfigurationOptions {
    @XmlElement(name="BoundsRange", required=true)
    protected IntRectangleRange boundsRange;
    @XmlElement(name="VideoSourceTokensAvailable", required=true)
    protected List<String> videoSourceTokensAvailable;
    @XmlElement(name="Extension")
    protected VideoSourceConfigurationOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public IntRectangleRange getBoundsRange() {
        return this.boundsRange;
    }

    public void setBoundsRange(IntRectangleRange value) {
        this.boundsRange = value;
    }

    public List<String> getVideoSourceTokensAvailable() {
        if (this.videoSourceTokensAvailable == null) {
            this.videoSourceTokensAvailable = new ArrayList<String>();
        }
        return this.videoSourceTokensAvailable;
    }

    public VideoSourceConfigurationOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(VideoSourceConfigurationOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

