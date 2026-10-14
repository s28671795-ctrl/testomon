/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourExtension;
import org.onvif.ver10.schema.PTZPresetTourSpot;
import org.onvif.ver10.schema.PTZPresetTourStartingCondition;
import org.onvif.ver10.schema.PTZPresetTourStatus;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PresetTour", propOrder={"name", "status", "autoStart", "startingCondition", "tourSpot", "extension"})
public class PresetTour {
    @XmlElement(name="Name")
    protected String name;
    @XmlElement(name="Status", required=true)
    protected PTZPresetTourStatus status;
    @XmlElement(name="AutoStart")
    protected boolean autoStart;
    @XmlElement(name="StartingCondition", required=true)
    protected PTZPresetTourStartingCondition startingCondition;
    @XmlElement(name="TourSpot")
    protected List<PTZPresetTourSpot> tourSpot;
    @XmlElement(name="Extension")
    protected PTZPresetTourExtension extension;
    @XmlAttribute(name="token")
    protected String token;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public PTZPresetTourStatus getStatus() {
        return this.status;
    }

    public void setStatus(PTZPresetTourStatus value) {
        this.status = value;
    }

    public boolean getAutoStart() {
        return this.autoStart;
    }

    public void setAutoStart(boolean value) {
        this.autoStart = value;
    }

    public PTZPresetTourStartingCondition getStartingCondition() {
        return this.startingCondition;
    }

    public void setStartingCondition(PTZPresetTourStartingCondition value) {
        this.startingCondition = value;
    }

    public List<PTZPresetTourSpot> getTourSpot() {
        if (this.tourSpot == null) {
            this.tourSpot = new ArrayList<PTZPresetTourSpot>();
        }
        return this.tourSpot;
    }

    public PTZPresetTourExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZPresetTourExtension value) {
        this.extension = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

