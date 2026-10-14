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
import org.onvif.ver10.schema.DurationRange;
import org.onvif.ver10.schema.PTZPresetTourPresetDetailOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourSpotOptions", propOrder={"presetDetail", "stayTime", "any"})
public class PTZPresetTourSpotOptions {
    @XmlElement(name="PresetDetail", required=true)
    protected PTZPresetTourPresetDetailOptions presetDetail;
    @XmlElement(name="StayTime", required=true)
    protected DurationRange stayTime;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZPresetTourPresetDetailOptions getPresetDetail() {
        return this.presetDetail;
    }

    public void setPresetDetail(PTZPresetTourPresetDetailOptions value) {
        this.presetDetail = value;
    }

    public DurationRange getStayTime() {
        return this.stayTime;
    }

    public void setStayTime(DurationRange value) {
        this.stayTime = value;
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

