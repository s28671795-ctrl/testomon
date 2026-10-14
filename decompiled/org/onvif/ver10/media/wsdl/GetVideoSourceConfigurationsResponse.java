/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.VideoSourceConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"configurations"})
@XmlRootElement(name="GetVideoSourceConfigurationsResponse")
public class GetVideoSourceConfigurationsResponse {
    @XmlElement(name="Configurations")
    protected List<VideoSourceConfiguration> configurations;

    public List<VideoSourceConfiguration> getConfigurations() {
        if (this.configurations == null) {
            this.configurations = new ArrayList<VideoSourceConfiguration>();
        }
        return this.configurations;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

