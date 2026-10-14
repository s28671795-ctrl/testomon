/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RelayOutput;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"relayOutput"})
@XmlRootElement(name="SetRelayOutputSettings")
public class SetRelayOutputSettings {
    @XmlElement(name="RelayOutput", required=true)
    protected RelayOutput relayOutput;

    public RelayOutput getRelayOutput() {
        return this.relayOutput;
    }

    public void setRelayOutput(RelayOutput value) {
        this.relayOutput = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

