/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Vector1D;
import org.onvif.ver10.schema.Vector2D;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZSpeed", propOrder={"panTilt", "zoom"})
public class PTZSpeed {
    @XmlElement(name="PanTilt")
    protected Vector2D panTilt;
    @XmlElement(name="Zoom")
    protected Vector1D zoom;

    public Vector2D getPanTilt() {
        return this.panTilt;
    }

    public void setPanTilt(Vector2D value) {
        this.panTilt = value;
    }

    public Vector1D getZoom() {
        return this.zoom;
    }

    public void setZoom(Vector1D value) {
        this.zoom = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

