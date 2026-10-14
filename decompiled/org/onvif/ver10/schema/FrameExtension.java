/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FrameExtension2;
import org.onvif.ver10.schema.MotionInCells;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FrameExtension", propOrder={"any", "motionInCells", "extension"})
public class FrameExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="MotionInCells")
    protected MotionInCells motionInCells;
    @XmlElement(name="Extension")
    protected FrameExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public MotionInCells getMotionInCells() {
        return this.motionInCells;
    }

    public void setMotionInCells(MotionInCells value) {
        this.motionInCells = value;
    }

    public FrameExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(FrameExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

