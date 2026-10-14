/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.PTZConfigurationExtension;
import org.onvif.ver10.schema.PTZSpeed;
import org.onvif.ver10.schema.PanTiltLimits;
import org.onvif.ver10.schema.ZoomLimits;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZConfiguration", propOrder={"nodeToken", "defaultAbsolutePantTiltPositionSpace", "defaultAbsoluteZoomPositionSpace", "defaultRelativePanTiltTranslationSpace", "defaultRelativeZoomTranslationSpace", "defaultContinuousPanTiltVelocitySpace", "defaultContinuousZoomVelocitySpace", "defaultPTZSpeed", "defaultPTZTimeout", "panTiltLimits", "zoomLimits", "extension"})
public class PTZConfiguration
extends ConfigurationEntity {
    @XmlElement(name="NodeToken", required=true)
    protected String nodeToken;
    @XmlElement(name="DefaultAbsolutePantTiltPositionSpace")
    @XmlSchemaType(name="anyURI")
    protected String defaultAbsolutePantTiltPositionSpace;
    @XmlElement(name="DefaultAbsoluteZoomPositionSpace")
    @XmlSchemaType(name="anyURI")
    protected String defaultAbsoluteZoomPositionSpace;
    @XmlElement(name="DefaultRelativePanTiltTranslationSpace")
    @XmlSchemaType(name="anyURI")
    protected String defaultRelativePanTiltTranslationSpace;
    @XmlElement(name="DefaultRelativeZoomTranslationSpace")
    @XmlSchemaType(name="anyURI")
    protected String defaultRelativeZoomTranslationSpace;
    @XmlElement(name="DefaultContinuousPanTiltVelocitySpace")
    @XmlSchemaType(name="anyURI")
    protected String defaultContinuousPanTiltVelocitySpace;
    @XmlElement(name="DefaultContinuousZoomVelocitySpace")
    @XmlSchemaType(name="anyURI")
    protected String defaultContinuousZoomVelocitySpace;
    @XmlElement(name="DefaultPTZSpeed")
    protected PTZSpeed defaultPTZSpeed;
    @XmlElement(name="DefaultPTZTimeout")
    protected Duration defaultPTZTimeout;
    @XmlElement(name="PanTiltLimits")
    protected PanTiltLimits panTiltLimits;
    @XmlElement(name="ZoomLimits")
    protected ZoomLimits zoomLimits;
    @XmlElement(name="Extension")
    protected PTZConfigurationExtension extension;
    @XmlAttribute(name="MoveRamp")
    protected Integer moveRamp;
    @XmlAttribute(name="PresetRamp")
    protected Integer presetRamp;
    @XmlAttribute(name="PresetTourRamp")
    protected Integer presetTourRamp;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getNodeToken() {
        return this.nodeToken;
    }

    public void setNodeToken(String value) {
        this.nodeToken = value;
    }

    public String getDefaultAbsolutePantTiltPositionSpace() {
        return this.defaultAbsolutePantTiltPositionSpace;
    }

    public void setDefaultAbsolutePantTiltPositionSpace(String value) {
        this.defaultAbsolutePantTiltPositionSpace = value;
    }

    public String getDefaultAbsoluteZoomPositionSpace() {
        return this.defaultAbsoluteZoomPositionSpace;
    }

    public void setDefaultAbsoluteZoomPositionSpace(String value) {
        this.defaultAbsoluteZoomPositionSpace = value;
    }

    public String getDefaultRelativePanTiltTranslationSpace() {
        return this.defaultRelativePanTiltTranslationSpace;
    }

    public void setDefaultRelativePanTiltTranslationSpace(String value) {
        this.defaultRelativePanTiltTranslationSpace = value;
    }

    public String getDefaultRelativeZoomTranslationSpace() {
        return this.defaultRelativeZoomTranslationSpace;
    }

    public void setDefaultRelativeZoomTranslationSpace(String value) {
        this.defaultRelativeZoomTranslationSpace = value;
    }

    public String getDefaultContinuousPanTiltVelocitySpace() {
        return this.defaultContinuousPanTiltVelocitySpace;
    }

    public void setDefaultContinuousPanTiltVelocitySpace(String value) {
        this.defaultContinuousPanTiltVelocitySpace = value;
    }

    public String getDefaultContinuousZoomVelocitySpace() {
        return this.defaultContinuousZoomVelocitySpace;
    }

    public void setDefaultContinuousZoomVelocitySpace(String value) {
        this.defaultContinuousZoomVelocitySpace = value;
    }

    public PTZSpeed getDefaultPTZSpeed() {
        return this.defaultPTZSpeed;
    }

    public void setDefaultPTZSpeed(PTZSpeed value) {
        this.defaultPTZSpeed = value;
    }

    public Duration getDefaultPTZTimeout() {
        return this.defaultPTZTimeout;
    }

    public void setDefaultPTZTimeout(Duration value) {
        this.defaultPTZTimeout = value;
    }

    public PanTiltLimits getPanTiltLimits() {
        return this.panTiltLimits;
    }

    public void setPanTiltLimits(PanTiltLimits value) {
        this.panTiltLimits = value;
    }

    public ZoomLimits getZoomLimits() {
        return this.zoomLimits;
    }

    public void setZoomLimits(ZoomLimits value) {
        this.zoomLimits = value;
    }

    public PTZConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZConfigurationExtension value) {
        this.extension = value;
    }

    public Integer getMoveRamp() {
        return this.moveRamp;
    }

    public void setMoveRamp(Integer value) {
        this.moveRamp = value;
    }

    public Integer getPresetRamp() {
        return this.presetRamp;
    }

    public void setPresetRamp(Integer value) {
        this.presetRamp = value;
    }

    public Integer getPresetTourRamp() {
        return this.presetTourRamp;
    }

    public void setPresetTourRamp(Integer value) {
        this.presetTourRamp = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

