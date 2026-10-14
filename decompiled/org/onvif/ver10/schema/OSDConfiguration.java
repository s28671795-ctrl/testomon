/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DeviceEntity;
import org.onvif.ver10.schema.OSDConfigurationExtension;
import org.onvif.ver10.schema.OSDImgConfiguration;
import org.onvif.ver10.schema.OSDPosConfiguration;
import org.onvif.ver10.schema.OSDReference;
import org.onvif.ver10.schema.OSDTextConfiguration;
import org.onvif.ver10.schema.OSDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDConfiguration", propOrder={"videoSourceConfigurationToken", "type", "position", "textString", "image", "extension"})
public class OSDConfiguration
extends DeviceEntity {
    @XmlElement(name="VideoSourceConfigurationToken", required=true)
    protected OSDReference videoSourceConfigurationToken;
    @XmlElement(name="Type", required=true)
    @XmlSchemaType(name="string")
    protected OSDType type;
    @XmlElement(name="Position", required=true)
    protected OSDPosConfiguration position;
    @XmlElement(name="TextString")
    protected OSDTextConfiguration textString;
    @XmlElement(name="Image")
    protected OSDImgConfiguration image;
    @XmlElement(name="Extension")
    protected OSDConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public OSDReference getVideoSourceConfigurationToken() {
        return this.videoSourceConfigurationToken;
    }

    public void setVideoSourceConfigurationToken(OSDReference value) {
        this.videoSourceConfigurationToken = value;
    }

    public OSDType getType() {
        return this.type;
    }

    public void setType(OSDType value) {
        this.type = value;
    }

    public OSDPosConfiguration getPosition() {
        return this.position;
    }

    public void setPosition(OSDPosConfiguration value) {
        this.position = value;
    }

    public OSDTextConfiguration getTextString() {
        return this.textString;
    }

    public void setTextString(OSDTextConfiguration value) {
        this.textString = value;
    }

    public OSDImgConfiguration getImage() {
        return this.image;
    }

    public void setImage(OSDImgConfiguration value) {
        this.image = value;
    }

    public OSDConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(OSDConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

