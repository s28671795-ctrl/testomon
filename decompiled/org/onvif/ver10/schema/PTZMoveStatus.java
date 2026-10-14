/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.MoveStatus;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZMoveStatus", propOrder={"panTilt", "zoom"})
public class PTZMoveStatus {
    @XmlElement(name="PanTilt")
    @XmlSchemaType(name="string")
    protected MoveStatus panTilt;
    @XmlElement(name="Zoom")
    @XmlSchemaType(name="string")
    protected MoveStatus zoom;

    public MoveStatus getPanTilt() {
        return this.panTilt;
    }

    public void setPanTilt(MoveStatus value) {
        this.panTilt = value;
    }

    public MoveStatus getZoom() {
        return this.zoom;
    }

    public void setZoom(MoveStatus value) {
        this.zoom = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

