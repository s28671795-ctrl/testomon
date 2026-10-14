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
import org.onvif.ver10.schema.PTZSpacesExtension;
import org.onvif.ver10.schema.Space1DDescription;
import org.onvif.ver10.schema.Space2DDescription;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZSpaces", propOrder={"absolutePanTiltPositionSpace", "absoluteZoomPositionSpace", "relativePanTiltTranslationSpace", "relativeZoomTranslationSpace", "continuousPanTiltVelocitySpace", "continuousZoomVelocitySpace", "panTiltSpeedSpace", "zoomSpeedSpace", "extension"})
public class PTZSpaces {
    @XmlElement(name="AbsolutePanTiltPositionSpace")
    protected List<Space2DDescription> absolutePanTiltPositionSpace;
    @XmlElement(name="AbsoluteZoomPositionSpace")
    protected List<Space1DDescription> absoluteZoomPositionSpace;
    @XmlElement(name="RelativePanTiltTranslationSpace")
    protected List<Space2DDescription> relativePanTiltTranslationSpace;
    @XmlElement(name="RelativeZoomTranslationSpace")
    protected List<Space1DDescription> relativeZoomTranslationSpace;
    @XmlElement(name="ContinuousPanTiltVelocitySpace")
    protected List<Space2DDescription> continuousPanTiltVelocitySpace;
    @XmlElement(name="ContinuousZoomVelocitySpace")
    protected List<Space1DDescription> continuousZoomVelocitySpace;
    @XmlElement(name="PanTiltSpeedSpace")
    protected List<Space1DDescription> panTiltSpeedSpace;
    @XmlElement(name="ZoomSpeedSpace")
    protected List<Space1DDescription> zoomSpeedSpace;
    @XmlElement(name="Extension")
    protected PTZSpacesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Space2DDescription> getAbsolutePanTiltPositionSpace() {
        if (this.absolutePanTiltPositionSpace == null) {
            this.absolutePanTiltPositionSpace = new ArrayList<Space2DDescription>();
        }
        return this.absolutePanTiltPositionSpace;
    }

    public List<Space1DDescription> getAbsoluteZoomPositionSpace() {
        if (this.absoluteZoomPositionSpace == null) {
            this.absoluteZoomPositionSpace = new ArrayList<Space1DDescription>();
        }
        return this.absoluteZoomPositionSpace;
    }

    public List<Space2DDescription> getRelativePanTiltTranslationSpace() {
        if (this.relativePanTiltTranslationSpace == null) {
            this.relativePanTiltTranslationSpace = new ArrayList<Space2DDescription>();
        }
        return this.relativePanTiltTranslationSpace;
    }

    public List<Space1DDescription> getRelativeZoomTranslationSpace() {
        if (this.relativeZoomTranslationSpace == null) {
            this.relativeZoomTranslationSpace = new ArrayList<Space1DDescription>();
        }
        return this.relativeZoomTranslationSpace;
    }

    public List<Space2DDescription> getContinuousPanTiltVelocitySpace() {
        if (this.continuousPanTiltVelocitySpace == null) {
            this.continuousPanTiltVelocitySpace = new ArrayList<Space2DDescription>();
        }
        return this.continuousPanTiltVelocitySpace;
    }

    public List<Space1DDescription> getContinuousZoomVelocitySpace() {
        if (this.continuousZoomVelocitySpace == null) {
            this.continuousZoomVelocitySpace = new ArrayList<Space1DDescription>();
        }
        return this.continuousZoomVelocitySpace;
    }

    public List<Space1DDescription> getPanTiltSpeedSpace() {
        if (this.panTiltSpeedSpace == null) {
            this.panTiltSpeedSpace = new ArrayList<Space1DDescription>();
        }
        return this.panTiltSpeedSpace;
    }

    public List<Space1DDescription> getZoomSpeedSpace() {
        if (this.zoomSpeedSpace == null) {
            this.zoomSpeedSpace = new ArrayList<Space1DDescription>();
        }
        return this.zoomSpeedSpace;
    }

    public PTZSpacesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZSpacesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

