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
import org.onvif.ver10.schema.AnalyticsDeviceExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsDeviceCapabilities", propOrder={"xAddr", "ruleSupport", "extension"})
public class AnalyticsDeviceCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="RuleSupport")
    protected Boolean ruleSupport;
    @XmlElement(name="Extension")
    protected AnalyticsDeviceExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public Boolean getRuleSupport() {
        return this.ruleSupport;
    }

    public void setRuleSupport(Boolean value) {
        this.ruleSupport = value;
    }

    public AnalyticsDeviceExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AnalyticsDeviceExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

