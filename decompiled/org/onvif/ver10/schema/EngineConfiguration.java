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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AnalyticsEngineInputInfo;
import org.onvif.ver10.schema.VideoAnalyticsConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EngineConfiguration", propOrder={"videoAnalyticsConfiguration", "analyticsEngineInputInfo", "any"})
public class EngineConfiguration {
    @XmlElement(name="VideoAnalyticsConfiguration", required=true)
    protected VideoAnalyticsConfiguration videoAnalyticsConfiguration;
    @XmlElement(name="AnalyticsEngineInputInfo", required=true)
    protected AnalyticsEngineInputInfo analyticsEngineInputInfo;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public VideoAnalyticsConfiguration getVideoAnalyticsConfiguration() {
        return this.videoAnalyticsConfiguration;
    }

    public void setVideoAnalyticsConfiguration(VideoAnalyticsConfiguration value) {
        this.videoAnalyticsConfiguration = value;
    }

    public AnalyticsEngineInputInfo getAnalyticsEngineInputInfo() {
        return this.analyticsEngineInputInfo;
    }

    public void setAnalyticsEngineInputInfo(AnalyticsEngineInputInfo value) {
        this.analyticsEngineInputInfo = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

