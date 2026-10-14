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
import org.onvif.ver10.schema.PTZConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ptzConfiguration"})
@XmlRootElement(name="GetConfigurationResponse")
public class GetConfigurationResponse {
    @XmlElement(name="PTZConfiguration", required=true)
    protected PTZConfiguration ptzConfiguration;

    public PTZConfiguration getPTZConfiguration() {
        return this.ptzConfiguration;
    }

    public void setPTZConfiguration(PTZConfiguration value) {
        this.ptzConfiguration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

