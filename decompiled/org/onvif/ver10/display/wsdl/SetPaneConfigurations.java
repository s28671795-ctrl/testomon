/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.display.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PaneConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoOutput", "paneConfiguration"})
@XmlRootElement(name="SetPaneConfigurations")
public class SetPaneConfigurations {
    @XmlElement(name="VideoOutput", required=true)
    protected String videoOutput;
    @XmlElement(name="PaneConfiguration", required=true)
    protected List<PaneConfiguration> paneConfiguration;

    public String getVideoOutput() {
        return this.videoOutput;
    }

    public void setVideoOutput(String value) {
        this.videoOutput = value;
    }

    public List<PaneConfiguration> getPaneConfiguration() {
        if (this.paneConfiguration == null) {
            this.paneConfiguration = new ArrayList<PaneConfiguration>();
        }
        return this.paneConfiguration;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

