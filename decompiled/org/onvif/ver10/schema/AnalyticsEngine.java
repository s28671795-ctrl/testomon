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
import org.onvif.ver10.schema.AnalyticsDeviceEngineConfiguration;
import org.onvif.ver10.schema.ConfigurationEntity;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsEngine", propOrder={"analyticsEngineConfiguration", "any"})
public class AnalyticsEngine
extends ConfigurationEntity {
    @XmlElement(name="AnalyticsEngineConfiguration", required=true)
    protected AnalyticsDeviceEngineConfiguration analyticsEngineConfiguration;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AnalyticsDeviceEngineConfiguration getAnalyticsEngineConfiguration() {
        return this.analyticsEngineConfiguration;
    }

    public void setAnalyticsEngineConfiguration(AnalyticsDeviceEngineConfiguration value) {
        this.analyticsEngineConfiguration = value;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

