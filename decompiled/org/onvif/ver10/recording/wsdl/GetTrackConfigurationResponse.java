/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.TrackConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"trackConfiguration"})
@XmlRootElement(name="GetTrackConfigurationResponse")
public class GetTrackConfigurationResponse {
    @XmlElement(name="TrackConfiguration", required=true)
    protected TrackConfiguration trackConfiguration;

    public TrackConfiguration getTrackConfiguration() {
        return this.trackConfiguration;
    }

    public void setTrackConfiguration(TrackConfiguration value) {
        this.trackConfiguration = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

