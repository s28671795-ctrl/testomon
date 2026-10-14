/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.media.wsdl.ProfileCapabilities;
import org.onvif.ver10.media.wsdl.StreamingCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"profileCapabilities", "streamingCapabilities", "any"})
public class Capabilities {
    @XmlElement(name="ProfileCapabilities", required=true)
    protected ProfileCapabilities profileCapabilities;
    @XmlElement(name="StreamingCapabilities", required=true)
    protected StreamingCapabilities streamingCapabilities;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="SnapshotUri")
    protected Boolean snapshotUri;
    @XmlAttribute(name="Rotation")
    protected Boolean rotation;
    @XmlAttribute(name="VideoSourceMode")
    protected Boolean videoSourceMode;
    @XmlAttribute(name="OSD")
    protected Boolean osd;
    @XmlAttribute(name="EXICompression")
    protected Boolean exiCompression;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ProfileCapabilities getProfileCapabilities() {
        return this.profileCapabilities;
    }

    public void setProfileCapabilities(ProfileCapabilities value) {
        this.profileCapabilities = value;
    }

    public StreamingCapabilities getStreamingCapabilities() {
        return this.streamingCapabilities;
    }

    public void setStreamingCapabilities(StreamingCapabilities value) {
        this.streamingCapabilities = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getSnapshotUri() {
        return this.snapshotUri;
    }

    public void setSnapshotUri(Boolean value) {
        this.snapshotUri = value;
    }

    public Boolean getRotation() {
        return this.rotation;
    }

    public void setRotation(Boolean value) {
        this.rotation = value;
    }

    public Boolean getVideoSourceMode() {
        return this.videoSourceMode;
    }

    public void setVideoSourceMode(Boolean value) {
        this.videoSourceMode = value;
    }

    public Boolean getOSD() {
        return this.osd;
    }

    public void setOSD(Boolean value) {
        this.osd = value;
    }

    public Boolean getEXICompression() {
        return this.exiCompression;
    }

    public void setEXICompression(Boolean value) {
        this.exiCompression = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

