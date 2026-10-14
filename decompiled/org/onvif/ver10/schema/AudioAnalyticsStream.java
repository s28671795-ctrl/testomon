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
import org.onvif.ver10.schema.AudioAnalyticsStreamExtension;
import org.onvif.ver10.schema.AudioDescriptor;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioAnalyticsStream", propOrder={"audioDescriptor", "extension"})
public class AudioAnalyticsStream {
    @XmlElement(name="AudioDescriptor")
    protected List<AudioDescriptor> audioDescriptor;
    @XmlElement(name="Extension")
    protected AudioAnalyticsStreamExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<AudioDescriptor> getAudioDescriptor() {
        if (this.audioDescriptor == null) {
            this.audioDescriptor = new ArrayList<AudioDescriptor>();
        }
        return this.audioDescriptor;
    }

    public AudioAnalyticsStreamExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AudioAnalyticsStreamExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

