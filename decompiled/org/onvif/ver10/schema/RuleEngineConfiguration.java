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
import org.onvif.ver10.schema.Config;
import org.onvif.ver10.schema.RuleEngineConfigurationExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RuleEngineConfiguration", propOrder={"rule", "extension"})
public class RuleEngineConfiguration {
    @XmlElement(name="Rule")
    protected List<Config> rule;
    @XmlElement(name="Extension")
    protected RuleEngineConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Config> getRule() {
        if (this.rule == null) {
            this.rule = new ArrayList<Config>();
        }
        return this.rule;
    }

    public RuleEngineConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RuleEngineConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

