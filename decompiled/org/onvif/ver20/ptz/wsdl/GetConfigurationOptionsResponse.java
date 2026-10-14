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
import org.onvif.ver10.schema.PTZConfigurationOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ptzConfigurationOptions"})
@XmlRootElement(name="GetConfigurationOptionsResponse")
public class GetConfigurationOptionsResponse {
    @XmlElement(name="PTZConfigurationOptions", required=true)
    protected PTZConfigurationOptions ptzConfigurationOptions;

    public PTZConfigurationOptions getPTZConfigurationOptions() {
        return this.ptzConfigurationOptions;
    }

    public void setPTZConfigurationOptions(PTZConfigurationOptions value) {
        this.ptzConfigurationOptions = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

