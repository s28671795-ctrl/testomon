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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FocusOptions", propOrder={"autoFocusModes", "defaultSpeed", "nearLimit", "farLimit"})
public class FocusOptions {
    @XmlElement(name="AutoFocusModes")
    @XmlSchemaType(name="string")
    protected List<AutoFocusMode> autoFocusModes;
    @XmlElement(name="DefaultSpeed", required=true)
    protected FloatRange defaultSpeed;
    @XmlElement(name="NearLimit", required=true)
    protected FloatRange nearLimit;
    @XmlElement(name="FarLimit", required=true)
    protected FloatRange farLimit;

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

