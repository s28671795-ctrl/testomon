/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrackOptions")
public class TrackOptions {
    @XmlAttribute(name="SpareTotal")
    protected Integer spareTotal;
    @XmlAttribute(name="SpareVideo")
    protected Integer spareVideo;
    @XmlAttribute(name="SpareAudio")
    protected Integer spareAudio;
    @XmlAttribute(name="SpareMetadata")
    protected Integer spareMetadata;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Integer getSpareTotal() {
        return this.spareTotal;
    }

    public void setSpareTotal(Integer value) {
        this.spareTotal = value;
    }

    public Integer getSpareVideo() {
        return this.spareVideo;
    }

    public void setSpareVideo(Integer value) {
        this.spareVideo = value;
    }

    public Integer getSpareAudio() {
        return this.spareAudio;
    }

    public void setSpareAudio(Integer value) {
        this.spareAudio = value;
    }

    public Integer getSpareMetadata() {
        return this.spareMetadata;
    }

    public void setSpareMetadata(Integer value) {
        this.spareMetadata = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

