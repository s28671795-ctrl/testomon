/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RelayOutputSettings;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"relayOutputToken", "properties"})
@XmlRootElement(name="SetRelayOutputSettings")
public class SetRelayOutputSettings {
    @XmlElement(name="RelayOutputToken", required=true)
    protected String relayOutputToken;
    @XmlElement(name="Properties", required=true)
    protected RelayOutputSettings properties;

    public String getRelayOutputToken() {
        return this.relayOutputToken;
    }

    public void setRelayOutputToken(String value) {
        this.relayOutputToken = value;
    }

    public RelayOutputSettings getProperties() {
        return this.properties;
    }

    public void setProperties(RelayOutputSettings value) {
        this.properties = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

