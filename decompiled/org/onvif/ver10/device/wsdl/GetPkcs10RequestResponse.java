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
import org.onvif.ver10.schema.BinaryData;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"pkcs10Request"})
@XmlRootElement(name="GetPkcs10RequestResponse")
public class GetPkcs10RequestResponse {
    @XmlElement(name="Pkcs10Request", required=true)
    protected BinaryData pkcs10Request;

    public BinaryData getPkcs10Request() {
        return this.pkcs10Request;
    }

    public void setPkcs10Request(BinaryData value) {
        this.pkcs10Request = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

