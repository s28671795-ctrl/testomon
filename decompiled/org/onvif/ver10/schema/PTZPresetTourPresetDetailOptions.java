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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourPresetDetailOptionsExtension;
import org.onvif.ver10.schema.Space1DDescription;
import org.onvif.ver10.schema.Space2DDescription;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourPresetDetailOptions", propOrder={"presetToken", "home", "panTiltPositionSpace", "zoomPositionSpace", "extension"})
public class PTZPresetTourPresetDetailOptions {
    @XmlElement(name="PresetToken")
    protected List<String> presetToken;
    @XmlElement(name="Home")
    protected Boolean home;
    @XmlElement(name="PanTiltPositionSpace")
    protected Space2DDescription panTiltPositionSpace;
    @XmlElement(name="ZoomPositionSpace")
    protected Space1DDescription zoomPositionSpace;
    @XmlElement(name="Extension")
    protected PTZPresetTourPresetDetailOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getPresetToken() {
        if (this.presetToken == null) {
            this.presetToken = new ArrayList<String>();
        }
        return this.presetToken;
    }

    public Boolean getHome() {
        return this.home;
    }

    public void setHome(Boolean value) {
        this.home = value;
    }

    public Space2DDescription getPanTiltPositionSpace() {
        return this.panTiltPositionSpace;
    }

    public void setPanTiltPositionSpace(Space2DDescription value) {
        this.panTiltPositionSpace = value;
    }

    public Space1DDescription getZoomPositionSpace() {
        return this.zoomPositionSpace;
    }

    public void setZoomPositionSpace(Space1DDescription value) {
        this.zoomPositionSpace = value;
    }

    public PTZPresetTourPresetDetailOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZPresetTourPresetDetailOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

