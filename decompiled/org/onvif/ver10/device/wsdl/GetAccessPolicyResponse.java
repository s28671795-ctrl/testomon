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
@XmlType(name="", propOrder={"policyFile"})
@XmlRootElement(name="GetAccessPolicyResponse")
public class GetAccessPolicyResponse {
    @XmlElement(name="PolicyFile", required=true)
    protected BinaryData policyFile;

    public BinaryData getPolicyFile() {
        return this.policyFile;
    }

    public void setPolicyFile(BinaryData value) {
        this.policyFile = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

