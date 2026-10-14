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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AnalyticsEngineInputInfoExtension;
import org.onvif.ver10.schema.Config;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsEngineInputInfo", propOrder={"inputInfo", "extension"})
public class AnalyticsEngineInputInfo {
    @XmlElement(name="InputInfo")
    protected Config inputInfo;
    @XmlElement(name="Extension")
    protected AnalyticsEngineInputInfoExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Config getInputInfo() {
        return this.inputInfo;
    }

    public void setInputInfo(Config value) {
        this.inputInfo = value;
    }

    public AnalyticsEngineInputInfoExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AnalyticsEngineInputInfoExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

