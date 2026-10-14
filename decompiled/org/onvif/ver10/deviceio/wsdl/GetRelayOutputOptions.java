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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"relayOutputToken"})
@XmlRootElement(name="GetRelayOutputOptions")
public class GetRelayOutputOptions {
    @XmlElement(name="RelayOutputToken")
    protected String relayOutputToken;

    public String getRelayOutputToken() {
        return this.relayOutputToken;
    }

    public void setRelayOutputToken(String value) {
        this.relayOutputToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

