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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AnalyticsEngineConfiguration;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.EventSubscription;
import org.onvif.ver10.schema.MetadataConfigurationExtension;
import org.onvif.ver10.schema.MulticastConfiguration;
import org.onvif.ver10.schema.PTZFilter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MetadataConfiguration", propOrder={"ptzStatus", "events", "analytics", "multicast", "sessionTimeout", "any", "analyticsEngineConfiguration", "extension"})
public class MetadataConfiguration
extends ConfigurationEntity {
    @XmlElement(name="PTZStatus")
    protected PTZFilter ptzStatus;
    @XmlElement(name="Events")
    protected EventSubscription events;
    @XmlElement(name="Analytics")
    protected Boolean analytics;
    @XmlElement(name="Multicast", required=true)
    protected MulticastConfiguration multicast;
    @XmlElement(name="SessionTimeout", required=true)
    protected Duration sessionTimeout;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="AnalyticsEngineConfiguration")
    protected AnalyticsEngineConfiguration analyticsEngineConfiguration;
    @XmlElement(name="Extension")
    protected MetadataConfigurationExtension extension;
    @XmlAttribute(name="CompressionType")
    protected String compressionType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZFilter getPTZStatus() {
        return this.ptzStatus;
    }

    public void setPTZStatus(PTZFilter value) {
        this.ptzStatus = value;
    }

    public EventSubscription getEvents() {
        return this.events;
    }

    public void setEvents(EventSubscription value) {
        this.events = value;
    }

    public Boolean getAnalytics() {
        return this.analytics;
    }

    public void setAnalytics(Boolean value) {
        this.analytics = value;
    }

    public MulticastConfiguration getMulticast() {
        return this.multicast;
    }

    public void setMulticast(MulticastConfiguration value) {
        this.multicast = value;
    }

    public Duration getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Duration value) {
        this.sessionTimeout = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public AnalyticsEngineConfiguration getAnalyticsEngineConfiguration() {
        return this.analyticsEngineConfiguration;
    }

    public void setAnalyticsEngineConfiguration(AnalyticsEngineConfiguration value) {
        this.analyticsEngineConfiguration = value;
    }

    public MetadataConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(MetadataConfigurationExtension value) {
        this.extension = value;
    }

    public String getCompressionType() {
        return this.compressionType;
    }

    public void setCompressionType(String value) {
        this.compressionType = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

