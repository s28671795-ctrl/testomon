/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.receiver.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ReceiverMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"receiverToken", "mode"})
@XmlRootElement(name="SetReceiverMode")
public class SetReceiverMode {
    @XmlElement(name="ReceiverToken", required=true)
    protected String receiverToken;
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected ReceiverMode mode;

    public String getReceiverToken() {
        return this.receiverToken;
    }

    public void setReceiverToken(String value) {
        this.receiverToken = value;
    }

    public ReceiverMode getMode() {
        return this.mode;
    }

    public void setMode(ReceiverMode value) {
        this.mode = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

