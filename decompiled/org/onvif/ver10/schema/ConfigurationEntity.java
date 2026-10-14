/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AnalyticsEngine;
import org.onvif.ver10.schema.AnalyticsEngineControl;
import org.onvif.ver10.schema.AnalyticsEngineInput;
import org.onvif.ver10.schema.AudioDecoderConfiguration;
import org.onvif.ver10.schema.AudioEncoder2Configuration;
import org.onvif.ver10.schema.AudioEncoderConfiguration;
import org.onvif.ver10.schema.AudioOutputConfiguration;
import org.onvif.ver10.schema.AudioSourceConfiguration;
import org.onvif.ver10.schema.MetadataConfiguration;
import org.onvif.ver10.schema.PTZConfiguration;
import org.onvif.ver10.schema.VideoAnalyticsConfiguration;
import org.onvif.ver10.schema.VideoEncoder2Configuration;
import org.onvif.ver10.schema.VideoEncoderConfiguration;
import org.onvif.ver10.schema.VideoOutputConfiguration;
import org.onvif.ver10.schema.VideoSourceConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ConfigurationEntity", propOrder={"name", "useCount"})
@XmlSeeAlso(value={VideoSourceConfiguration.class, AudioSourceConfiguration.class, VideoEncoderConfiguration.class, AudioEncoderConfiguration.class, VideoAnalyticsConfiguration.class, PTZConfiguration.class, MetadataConfiguration.class, AudioOutputConfiguration.class, AudioDecoderConfiguration.class, VideoEncoder2Configuration.class, AudioEncoder2Configuration.class, VideoOutputConfiguration.class, AnalyticsEngine.class, AnalyticsEngineInput.class, AnalyticsEngineControl.class})
public class ConfigurationEntity {
    @XmlElement(name="Name", required=true)
    protected String name;
    @XmlElement(name="UseCount")
    protected int useCount;
    @XmlAttribute(name="token", required=true)
    protected String token;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getUseCount() {
        return this.useCount;
    }

    public void setUseCount(int value) {
        this.useCount = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

