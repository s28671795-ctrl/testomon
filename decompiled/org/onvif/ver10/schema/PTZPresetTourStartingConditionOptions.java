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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DurationRange;
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.PTZPresetTourDirection;
import org.onvif.ver10.schema.PTZPresetTourStartingConditionOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourStartingConditionOptions", propOrder={"recurringTime", "recurringDuration", "direction", "extension"})
public class PTZPresetTourStartingConditionOptions {
    @XmlElement(name="RecurringTime")
    protected IntRange recurringTime;
    @XmlElement(name="RecurringDuration")
    protected DurationRange recurringDuration;
    @XmlElement(name="Direction")
    @XmlSchemaType(name="string")
    protected List<PTZPresetTourDirection> direction;
    @XmlElement(name="Extension")
    protected PTZPresetTourStartingConditionOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public IntRange getRecurringTime() {
        return this.recurringTime;
    }

    public void setRecurringTime(IntRange value) {
        this.recurringTime = value;
    }

    public DurationRange getRecurringDuration() {
        return this.recurringDuration;
    }

    public void setRecurringDuration(DurationRange value) {
        this.recurringDuration = value;
    }

    public List<PTZPresetTourDirection> getDirection() {
        if (this.direction == null) {
            this.direction = new ArrayList<PTZPresetTourDirection>();
        }
        return this.direction;
    }

    public PTZPresetTourStartingConditionOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZPresetTourStartingConditionOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

