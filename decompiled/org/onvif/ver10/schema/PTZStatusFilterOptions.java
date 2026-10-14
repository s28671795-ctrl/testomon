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
import org.onvif.ver10.schema.PTZStatusFilterOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZStatusFilterOptions", propOrder={"panTiltStatusSupported", "zoomStatusSupported", "any", "panTiltPositionSupported", "zoomPositionSupported", "extension"})
public class PTZStatusFilterOptions {
    @XmlElement(name="PanTiltStatusSupported")
    protected boolean panTiltStatusSupported;
    @XmlElement(name="ZoomStatusSupported")
    protected boolean zoomStatusSupported;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="PanTiltPositionSupported")
    protected Boolean panTiltPositionSupported;
    @XmlElement(name="ZoomPositionSupported")
    protected Boolean zoomPositionSupported;
    @XmlElement(name="Extension")
    protected PTZStatusFilterOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getPanTiltStatusSupported() {
        return this.panTiltStatusSupported;
    }

    public void setPanTiltStatusSupported(boolean value) {
        this.panTiltStatusSupported = value;
    }

    public boolean getZoomStatusSupported() {
        return this.zoomStatusSupported;
    }

    public void setZoomStatusSupported(boolean value) {
        this.zoomStatusSupported = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getPanTiltPositionSupported() {
        return this.panTiltPositionSupported;
    }

    public void setPanTiltPositionSupported(Boolean value) {
        this.panTiltPositionSupported = value;
    }

    public Boolean getZoomPositionSupported() {
        return this.zoomPositionSupported;
    }

    public void setZoomPositionSupported(Boolean value) {
        this.zoomPositionSupported = value;
    }

    public PTZStatusFilterOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZStatusFilterOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

