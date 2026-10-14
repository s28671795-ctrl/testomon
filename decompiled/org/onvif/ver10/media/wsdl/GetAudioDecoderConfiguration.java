/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"configurationToken"})
@XmlRootElement(name="GetAudioDecoderConfiguration")
public class GetAudioDecoderConfiguration {
    @XmlElement(name="ConfigurationToken", required=true)
    protected String configurationToken;

    public String getConfigurationToken() {
        return this.configurationToken;
    }

    public void setConfigurationToken(String value) {
        this.configurationToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

