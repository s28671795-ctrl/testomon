/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.receiver.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ReceiverConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"receiverToken", "configuration"})
@XmlRootElement(name="ConfigureReceiver")
public class ConfigureReceiver {
    @XmlElement(name="ReceiverToken", required=true)
    protected String receiverToken;
    @XmlElement(name="Configuration", required=true)
    protected ReceiverConfiguration configuration;

    public String getReceiverToken() {
        return this.receiverToken;
    }

    public void setReceiverToken(String value) {
        this.receiverToken = value;
    }

    public ReceiverConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(ReceiverConfiguration value) {
        this.configuration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

