/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.doorcontrol.wsdl.DoorAlarmState;
import org.onvif.ver10.doorcontrol.wsdl.DoorFault;
import org.onvif.ver10.doorcontrol.wsdl.DoorMode;
import org.onvif.ver10.doorcontrol.wsdl.DoorPhysicalState;
import org.onvif.ver10.doorcontrol.wsdl.DoorTamper;
import org.onvif.ver10.doorcontrol.wsdl.LockPhysicalState;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DoorState", propOrder={"doorPhysicalState", "lockPhysicalState", "doubleLockPhysicalState", "alarm", "tamper", "fault", "doorMode", "any"})
public class DoorState {
    @XmlElement(name="DoorPhysicalState")
    @XmlSchemaType(name="string")
    protected DoorPhysicalState doorPhysicalState;
    @XmlElement(name="LockPhysicalState")
    @XmlSchemaType(name="string")
    protected LockPhysicalState lockPhysicalState;
    @XmlElement(name="DoubleLockPhysicalState")
    @XmlSchemaType(name="string")
    protected LockPhysicalState doubleLockPhysicalState;
    @XmlElement(name="Alarm")
    @XmlSchemaType(name="string")
    protected DoorAlarmState alarm;
    @XmlElement(name="Tamper")
    protected DoorTamper tamper;
    @XmlElement(name="Fault")
    protected DoorFault fault;
    @XmlElement(name="DoorMode", required=true)
    @XmlSchemaType(name="string")
    protected DoorMode doorMode;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public DoorPhysicalState getDoorPhysicalState() {
        return this.doorPhysicalState;
    }

    public void setDoorPhysicalState(DoorPhysicalState value) {
        this.doorPhysicalState = value;
    }

    public LockPhysicalState getLockPhysicalState() {
        return this.lockPhysicalState;
    }

    public void setLockPhysicalState(LockPhysicalState value) {
        this.lockPhysicalState = value;
    }

    public LockPhysicalState getDoubleLockPhysicalState() {
        return this.doubleLockPhysicalState;
    }

    public void setDoubleLockPhysicalState(LockPhysicalState value) {
        this.doubleLockPhysicalState = value;
    }

    public DoorAlarmState getAlarm() {
        return this.alarm;
    }

    public void setAlarm(DoorAlarmState value) {
        this.alarm = value;
    }

    public DoorTamper getTamper() {
        return this.tamper;
    }

    public void setTamper(DoorTamper value) {
        this.tamper = value;
    }

    public DoorFault getFault() {
        return this.fault;
    }

    public void setFault(DoorFault value) {
        this.fault = value;
    }

    public DoorMode getDoorMode() {
        return this.doorMode;
    }

    public void setDoorMode(DoorMode value) {
        this.doorMode = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

