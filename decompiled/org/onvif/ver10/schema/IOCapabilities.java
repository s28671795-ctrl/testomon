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
import org.onvif.ver10.schema.IOCapabilitiesExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IOCapabilities", propOrder={"inputConnectors", "relayOutputs", "extension"})
public class IOCapabilities {
    @XmlElement(name="InputConnectors")
    protected Integer inputConnectors;
    @XmlElement(name="RelayOutputs")
    protected Integer relayOutputs;
    @XmlElement(name="Extension")
    protected IOCapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Integer getInputConnectors() {
        return this.inputConnectors;
    }

    public void setInputConnectors(Integer value) {
        this.inputConnectors = value;
    }

    public Integer getRelayOutputs() {
        return this.relayOutputs;
    }

    public void setRelayOutputs(Integer value) {
        this.relayOutputs = value;
    }

    public IOCapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(IOCapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

