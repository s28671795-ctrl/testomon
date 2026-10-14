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
import org.onvif.ver10.schema.Duplex;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkInterfaceConnectionSetting", propOrder={"autoNegotiation", "speed", "duplex"})
public class NetworkInterfaceConnectionSetting {
    @XmlElement(name="AutoNegotiation")
    protected boolean autoNegotiation;
    @XmlElement(name="Speed")
    protected int speed;
    @XmlElement(name="Duplex", required=true)
    @XmlSchemaType(name="string")
    protected Duplex duplex;

    public boolean getAutoNegotiation() {
        return this.autoNegotiation;
    }

    public void setAutoNegotiation(boolean value) {
        this.autoNegotiation = value;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int value) {
        this.speed = value;
    }

    public Duplex getDuplex() {
        return this.duplex;
    }

    public void setDuplex(Duplex value) {
        this.duplex = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

