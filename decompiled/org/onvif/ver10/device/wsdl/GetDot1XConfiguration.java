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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"dot1XConfigurationToken"})
@XmlRootElement(name="GetDot1XConfiguration")
public class GetDot1XConfiguration {
    @XmlElement(name="Dot1XConfigurationToken", required=true)
    protected String dot1XConfigurationToken;

    public String getDot1XConfigurationToken() {
        return this.dot1XConfigurationToken;
    }

    public void setDot1XConfigurationToken(String value) {
        this.dot1XConfigurationToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

