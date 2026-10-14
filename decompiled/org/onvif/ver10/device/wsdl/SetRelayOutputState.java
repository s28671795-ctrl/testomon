/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RelayLogicalState;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"relayOutputToken", "logicalState"})
@XmlRootElement(name="SetRelayOutputState")
public class SetRelayOutputState {
    @XmlElement(name="RelayOutputToken", required=true)
    protected String relayOutputToken;
    @XmlElement(name="LogicalState", required=true)
    @XmlSchemaType(name="string")
    protected RelayLogicalState logicalState;

    public String getRelayOutputToken() {
        return this.relayOutputToken;
    }

    public void setRelayOutputToken(String value) {
        this.relayOutputToken = value;
    }

    public RelayLogicalState getLogicalState() {
        return this.logicalState;
    }

    public void setLogicalState(RelayLogicalState value) {
        this.logicalState = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

