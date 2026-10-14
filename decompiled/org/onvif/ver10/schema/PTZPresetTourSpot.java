/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourPresetDetail;
import org.onvif.ver10.schema.PTZPresetTourSpotExtension;
import org.onvif.ver10.schema.PTZSpeed;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourSpot", propOrder={"presetDetail", "speed", "stayTime", "extension"})
public class PTZPresetTourSpot {
    @XmlElement(name="PresetDetail", required=true)
    protected PTZPresetTourPresetDetail presetDetail;
    @XmlElement(name="Speed")
    protected PTZSpeed speed;
    @XmlElement(name="StayTime")
    protected Duration stayTime;
    @XmlElement(name="Extension")
    protected PTZPresetTourSpotExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZPresetTourPresetDetail getPresetDetail() {
        return this.presetDetail;
    }

    public void setPresetDetail(PTZPresetTourPresetDetail value) {
        this.presetDetail = value;
    }

    public PTZSpeed getSpeed() {
        return this.speed;
    }

    public void setSpeed(PTZSpeed value) {
        this.speed = value;
    }

    public Duration getStayTime() {
        return this.stayTime;
    }

    public void setStayTime(Duration value) {
        this.stayTime = value;
    }

    public PTZPresetTourSpotExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZPresetTourSpotExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

