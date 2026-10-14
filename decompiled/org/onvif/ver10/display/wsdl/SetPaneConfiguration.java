/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.display.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PaneConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoOutput", "paneConfiguration", "any"})
@XmlRootElement(name="SetPaneConfiguration")
public class SetPaneConfiguration {
    @XmlElement(name="VideoOutput", required=true)
    protected String videoOutput;
    @XmlElement(name="PaneConfiguration", required=true)
    protected PaneConfiguration paneConfiguration;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public String getVideoOutput() {
        return this.videoOutput;
    }

    public void setVideoOutput(String value) {
        this.videoOutput = value;
    }

    public PaneConfiguration getPaneConfiguration() {
        return this.paneConfiguration;
    }

    public void setPaneConfiguration(PaneConfiguration value) {
        this.paneConfiguration = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

