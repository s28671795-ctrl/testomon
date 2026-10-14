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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.media.wsdl.VideoSourceModeExtension;
import org.onvif.ver10.schema.VideoResolution;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSourceMode", propOrder={"maxFramerate", "maxResolution", "encodings", "reboot", "description", "extension"})
public class VideoSourceMode {
    @XmlElement(name="MaxFramerate")
    protected float maxFramerate;
    @XmlElement(name="MaxResolution", required=true)
    protected VideoResolution maxResolution;
    @XmlList
    @XmlElement(name="Encodings", required=true)
    protected List<String> encodings;
    @XmlElement(name="Reboot")
    protected boolean reboot;
    @XmlElement(name="Description")
    protected String description;
    @XmlElement(name="Extension")
    protected VideoSourceModeExtension extension;
    @XmlAttribute(name="token", required=true)
    protected String token;
    @XmlAttribute(name="Enabled")
    protected Boolean enabled;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public float getMaxFramerate() {
        return this.maxFramerate;
    }

    public void setMaxFramerate(float value) {
        this.maxFramerate = value;
    }

    public VideoResolution getMaxResolution() {
        return this.maxResolution;
    }

    public void setMaxResolution(VideoResolution value) {
        this.maxResolution = value;
    }

    public List<String> getEncodings() {
        if (this.encodings == null) {
            this.encodings = new ArrayList<String>();
        }
        return this.encodings;
    }

    public boolean getReboot() {
        return this.reboot;
    }

    public void setReboot(boolean value) {
        this.reboot = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public VideoSourceModeExtension getExtension() {
        return this.extension;
    }

    public void setExtension(VideoSourceModeExtension value) {
        this.extension = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

