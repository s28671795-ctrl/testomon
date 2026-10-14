/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RelayOutput;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"relayOutputs"})
@XmlRootElement(name="GetRelayOutputsResponse")
public class GetRelayOutputsResponse {
    @XmlElement(name="RelayOutputs")
    protected List<RelayOutput> relayOutputs;

    public List<RelayOutput> getRelayOutputs() {
        if (this.relayOutputs == null) {
            this.relayOutputs = new ArrayList<RelayOutput>();
        }
        return this.relayOutputs;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

