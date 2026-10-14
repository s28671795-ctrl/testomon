/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.receiver.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Receiver;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"receivers"})
@XmlRootElement(name="GetReceiversResponse")
public class GetReceiversResponse {
    @XmlElement(name="Receivers")
    protected List<Receiver> receivers;

    public List<Receiver> getReceivers() {
        if (this.receivers == null) {
            this.receivers = new ArrayList<Receiver>();
        }
        return this.receivers;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

