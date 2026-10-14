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
import org.onvif.ver10.schema.PTZPresetTourDirection;
import org.onvif.ver10.schema.PTZPresetTourStartingConditionExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourStartingCondition", propOrder={"recurringTime", "recurringDuration", "direction", "extension"})
public class PTZPresetTourStartingCondition {
    @XmlElement(name="RecurringTime")
    protected Integer recurringTime;
    @XmlElement(name="RecurringDuration")
    protected Duration recurringDuration;
    @XmlElement(name="Direction")
    @XmlSchemaType(name="string")
    protected PTZPresetTourDirection direction;
    @XmlElement(name="Extension")
    protected PTZPresetTourStartingConditionExtension extension;
    @XmlAttribute(name="RandomPresetOrder")
    protected Boolean randomPresetOrder;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Integer getRecurringTime() {
        return this.recurringTime;
    }

    public void setRecurringTime(Integer value) {
        this.recurringTime = value;
    }

    public Duration getRecurringDuration() {
        return this.recurringDuration;
    }

    public void setRecurringDuration(Duration value) {
        this.recurringDuration = value;
    }

    public PTZPresetTourDirection getDirection() {
        return this.direction;
    }

    public void setDirection(PTZPresetTourDirection value) {
        this.direction = value;
    }

    public PTZPresetTourStartingConditionExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZPresetTourStartingConditionExtension value) {
        this.extension = value;
    }

    public Boolean getRandomPresetOrder() {
        return this.randomPresetOrder;
    }

    public void setRandomPresetOrder(Boolean value) {
        this.randomPresetOrder = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

