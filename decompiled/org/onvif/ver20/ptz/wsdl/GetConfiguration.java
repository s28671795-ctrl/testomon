/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ptzConfigurationToken"})
@XmlRootElement(name="GetConfiguration")
public class GetConfiguration {
    @XmlElement(name="PTZConfigurationToken", required=true)
    protected String ptzConfigurationToken;

    public String getPTZConfigurationToken() {
        return this.ptzConfigurationToken;
    }

    public void setPTZConfigurationToken(String value) {
        this.ptzConfigurationToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

