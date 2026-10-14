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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ConfigDescription;
import org.onvif.ver10.schema.SupportedAnalyticsModulesExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SupportedAnalyticsModules", propOrder={"analyticsModuleContentSchemaLocation", "analyticsModuleDescription", "extension"})
public class SupportedAnalyticsModules {
    @XmlElement(name="AnalyticsModuleContentSchemaLocation")
    @XmlSchemaType(name="anyURI")
    protected List<String> analyticsModuleContentSchemaLocation;
    @XmlElement(name="AnalyticsModuleDescription")
    protected List<ConfigDescription> analyticsModuleDescription;
    @XmlElement(name="Extension")
    protected SupportedAnalyticsModulesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getAnalyticsModuleContentSchemaLocation() {
        if (this.analyticsModuleContentSchemaLocation == null) {
            this.analyticsModuleContentSchemaLocation = new ArrayList<String>();
        }
        return this.analyticsModuleContentSchemaLocation;
    }

    public List<ConfigDescription> getAnalyticsModuleDescription() {
        if (this.analyticsModuleDescription == null) {
            this.analyticsModuleDescription = new ArrayList<ConfigDescription>();
        }
        return this.analyticsModuleDescription;
    }

    public SupportedAnalyticsModulesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(SupportedAnalyticsModulesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

