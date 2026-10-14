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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsCapabilities", propOrder={"xAddr", "ruleSupport", "analyticsModuleSupport", "any"})
public class AnalyticsCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="RuleSupport")
    protected boolean ruleSupport;
    @XmlElement(name="AnalyticsModuleSupport")
    protected boolean analyticsModuleSupport;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public boolean getRuleSupport() {
        return this.ruleSupport;
    }

    public void setRuleSupport(boolean value) {
        this.ruleSupport = value;
    }

    public boolean getAnalyticsModuleSupport() {
        return this.analyticsModuleSupport;
    }

    public void setAnalyticsModuleSupport(boolean value) {
        this.analyticsModuleSupport = value;
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

