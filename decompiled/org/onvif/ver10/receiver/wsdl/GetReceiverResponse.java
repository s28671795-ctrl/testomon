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
import org.onvif.ver10.schema.Receiver;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"receiver"})
@XmlRootElement(name="GetReceiverResponse")
public class GetReceiverResponse {
    @XmlElement(name="Receiver", required=true)
    protected Receiver receiver;

    public Receiver getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Receiver value) {
        this.receiver = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

