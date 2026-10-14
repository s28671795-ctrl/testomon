/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RelayIdleState;
import org.onvif.ver10.schema.RelayMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RelayOutputSettings", propOrder={"mode", "delayTime", "idleState"})
public class RelayOutputSettings {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected RelayMode mode;
    @XmlElement(name="DelayTime", required=true)
    protected Duration delayTime;
    @XmlElement(name="IdleState", required=true)
    @XmlSchemaType(name="string")
    protected RelayIdleState idleState;

    public RelayMode getMode() {
        return this.mode;
    }

    public void setMode(RelayMode value) {
        this.mode = value;
    }

    public Duration getDelayTime() {
        return this.delayTime;
    }

    public void setDelayTime(Duration value) {
        this.delayTime = value;
    }

    public RelayIdleState getIdleState() {
        return this.idleState;
    }

    public void setIdleState(RelayIdleState value) {
        this.idleState = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

