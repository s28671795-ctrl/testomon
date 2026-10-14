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
import org.onvif.ver10.schema.AnalyticsDeviceEngineConfigurationExtension;
import org.onvif.ver10.schema.EngineConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsDeviceEngineConfiguration", propOrder={"engineConfiguration", "extension"})
public class AnalyticsDeviceEngineConfiguration {
    @XmlElement(name="EngineConfiguration", required=true)
    protected List<EngineConfiguration> engineConfiguration;
    @XmlElement(name="Extension")
    protected AnalyticsDeviceEngineConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<EngineConfiguration> getEngineConfiguration() {
        if (this.engineConfiguration == null) {
            this.engineConfiguration = new ArrayList<EngineConfiguration>();
        }
        return this.engineConfiguration;
    }

    public AnalyticsDeviceEngineConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AnalyticsDeviceEngineConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

