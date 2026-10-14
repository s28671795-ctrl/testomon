/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AutoFocusMode;
import org.onvif.ver10.schema.FloatRange;
import org.onvif.ver10.schema.FocusOptions20Extension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FocusOptions20", propOrder={"autoFocusModes", "defaultSpeed", "nearLimit", "farLimit", "extension"})
public class FocusOptions20 {
    @XmlElement(name="AutoFocusModes")
    @XmlSchemaType(name="string")
    protected List<AutoFocusMode> autoFocusModes;
    @XmlElement(name="DefaultSpeed")
    protected FloatRange defaultSpeed;
    @XmlElement(name="NearLimit")
    protected FloatRange nearLimit;
    @XmlElement(name="FarLimit")
    protected FloatRange farLimit;
    @XmlElement(name="Extension")
    protected FocusOptions20Extension extension;

    public List<AutoFocusMode> getAutoFocusModes() {
        if (this.autoFocusModes == null) {
            this.autoFocusModes = new ArrayList<AutoFocusMode>();
        }
        return this.autoFocusModes;
    }

    public FloatRange getDefaultSpeed() {
        return this.defaultSpeed;
    }

    public void setDefaultSpeed(FloatRange value) {
        this.defaultSpeed = value;
    }

    public FloatRange getNearLimit() {
        return this.nearLimit;
    }

    public void setNearLimit(FloatRange value) {
        this.nearLimit = value;
    }

    public FloatRange getFarLimit() {
        return this.farLimit;
    }

    public void setFarLimit(FloatRange value) {
        this.farLimit = value;
    }

    public FocusOptions20Extension getExtension() {
        return this.extension;
    }

    public void setExtension(FocusOptions20Extension value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

