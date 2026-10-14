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
import org.onvif.ver10.schema.Dot1XConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"dot1XConfiguration"})
@XmlRootElement(name="GetDot1XConfigurationResponse")
public class GetDot1XConfigurationResponse {
    @XmlElement(name="Dot1XConfiguration", required=true)
    protected Dot1XConfiguration dot1XConfiguration;

    public Dot1XConfiguration getDot1XConfiguration() {
        return this.dot1XConfiguration;
    }

    public void setDot1XConfiguration(Dot1XConfiguration value) {
        this.dot1XConfiguration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

