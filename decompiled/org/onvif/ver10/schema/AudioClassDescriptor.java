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
import org.onvif.ver10.schema.AudioClassCandidate;
import org.onvif.ver10.schema.AudioClassDescriptorExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioClassDescriptor", propOrder={"classCandidate", "extension"})
public class AudioClassDescriptor {
    @XmlElement(name="ClassCandidate")
    protected List<AudioClassCandidate> classCandidate;
    @XmlElement(name="Extension")
    protected AudioClassDescriptorExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<AudioClassCandidate> getClassCandidate() {
        if (this.classCandidate == null) {
            this.classCandidate = new ArrayList<AudioClassCandidate>();
        }
        return this.classCandidate;
    }

    public AudioClassDescriptorExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AudioClassDescriptorExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

