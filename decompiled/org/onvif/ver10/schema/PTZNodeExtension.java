/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZNodeExtension2;
import org.onvif.ver10.schema.PTZPresetTourSupported;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZNodeExtension", propOrder={"any", "supportedPresetTour", "extension"})
public class PTZNodeExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="SupportedPresetTour")
    protected PTZPresetTourSupported supportedPresetTour;
    @XmlElement(name="Extension")
    protected PTZNodeExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public PTZPresetTourSupported getSupportedPresetTour() {
        return this.supportedPresetTour;
    }

    public void setSupportedPresetTour(PTZPresetTourSupported value) {
        this.supportedPresetTour = value;
    }

    public PTZNodeExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(PTZNodeExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

