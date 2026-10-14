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
import org.onvif.ver10.schema.AnalyticsEngineConfigurationExtension;
import org.onvif.ver10.schema.Config;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsEngineConfiguration", propOrder={"analyticsModule", "extension"})
public class AnalyticsEngineConfiguration {
    @XmlElement(name="AnalyticsModule")
    protected List<Config> analyticsModule;
    @XmlElement(name="Extension")
    protected AnalyticsEngineConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Config> getAnalyticsModule() {
        if (this.analyticsModule == null) {
            this.analyticsModule = new ArrayList<Config>();
        }
        return this.analyticsModule;
    }

    public AnalyticsEngineConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AnalyticsEngineConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

