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
import org.onvif.ver10.schema.AnalyticsEngineConfiguration;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.RuleEngineConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoAnalyticsConfiguration", propOrder={"analyticsEngineConfiguration", "ruleEngineConfiguration", "any"})
public class VideoAnalyticsConfiguration
extends ConfigurationEntity {
    @XmlElement(name="AnalyticsEngineConfiguration", required=true)
    protected AnalyticsEngineConfiguration analyticsEngineConfiguration;
    @XmlElement(name="RuleEngineConfiguration", required=true)
    protected RuleEngineConfiguration ruleEngineConfiguration;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AnalyticsEngineConfiguration getAnalyticsEngineConfiguration() {
        return this.analyticsEngineConfiguration;
    }

    public void setAnalyticsEngineConfiguration(AnalyticsEngineConfiguration value) {
        this.analyticsEngineConfiguration = value;
    }

    public RuleEngineConfiguration getRuleEngineConfiguration() {
        return this.ruleEngineConfiguration;
    }

    public void setRuleEngineConfiguration(RuleEngineConfiguration value) {
        this.ruleEngineConfiguration = value;
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

