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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourSpotOptions;
import org.onvif.ver10.schema.PTZPresetTourStartingConditionOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourOptions", propOrder={"autoStart", "startingCondition", "tourSpot", "any"})
public class PTZPresetTourOptions {
    @XmlElement(name="AutoStart")
    protected boolean autoStart;
    @XmlElement(name="StartingCondition", required=true)
    protected PTZPresetTourStartingConditionOptions startingCondition;
    @XmlElement(name="TourSpot", required=true)
    protected PTZPresetTourSpotOptions tourSpot;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getAutoStart() {
        return this.autoStart;
    }

    public void setAutoStart(boolean value) {
        this.autoStart = value;
    }

    public PTZPresetTourStartingConditionOptions getStartingCondition() {
        return this.startingCondition;
    }

    public void setStartingCondition(PTZPresetTourStartingConditionOptions value) {
        this.startingCondition = value;
    }

    public PTZPresetTourSpotOptions getTourSpot() {
        return this.tourSpot;
    }

    public void setTourSpot(PTZPresetTourSpotOptions value) {
        this.tourSpot = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

