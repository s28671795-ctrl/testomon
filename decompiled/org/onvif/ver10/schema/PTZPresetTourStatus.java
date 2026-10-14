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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourSpot;
import org.onvif.ver10.schema.PTZPresetTourState;
import org.onvif.ver10.schema.PTZPresetTourStatusExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourStatus", propOrder={"state", "currentTourSpot", "extension"})
public class PTZPresetTourStatus {
    @XmlElement(name="State", required=true)
    @XmlSchemaType(name="string")
    protected PTZPresetTourState state;
    @XmlElement(name="CurrentTourSpot")
    protected PTZPresetTourSpot currentTourSpot;
    @XmlElement(name="Extension")
    protected PTZPresetTourStatusExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZPresetTourState getState() {
        return this.state;
    }

    public void setState(PTZPresetTourState value) {
        this.state = value;
    }

    public PTZPresetTourSpot getCurrentTourSpot() {
        return this.currentTourSpot;
    }

    public void setCurrentTourSpot(PTZPresetTourSpot value) {
        this.currentTourSpot = value;
    }

    public PTZPresetTourStatusExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZPresetTourStatusExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

