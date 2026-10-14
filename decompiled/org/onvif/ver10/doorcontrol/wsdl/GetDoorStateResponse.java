/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.doorcontrol.wsdl.DoorState;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"doorState"})
@XmlRootElement(name="GetDoorStateResponse")
public class GetDoorStateResponse {
    @XmlElement(name="DoorState", required=true)
    protected DoorState doorState;

    public DoorState getDoorState() {
        return this.doorState;
    }

    public void setDoorState(DoorState value) {
        this.doorState = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

