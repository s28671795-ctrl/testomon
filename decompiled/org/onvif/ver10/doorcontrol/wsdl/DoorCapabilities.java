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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DoorCapabilities", propOrder={"any"})
public class DoorCapabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="Access")
    protected Boolean access;
    @XmlAttribute(name="AccessTimingOverride")
    protected Boolean accessTimingOverride;
    @XmlAttribute(name="Lock")
    protected Boolean lock;
    @XmlAttribute(name="Unlock")
    protected Boolean unlock;
    @XmlAttribute(name="Block")
    protected Boolean block;
    @XmlAttribute(name="DoubleLock")
    protected Boolean doubleLock;
    @XmlAttribute(name="LockDown")
    protected Boolean lockDown;
    @XmlAttribute(name="LockOpen")
    protected Boolean lockOpen;
    @XmlAttribute(name="DoorMonitor")
    protected Boolean doorMonitor;
    @XmlAttribute(name="LockMonitor")
    protected Boolean lockMonitor;
    @XmlAttribute(name="DoubleLockMonitor")
    protected Boolean doubleLockMonitor;
    @XmlAttribute(name="Alarm")
    protected Boolean alarm;
    @XmlAttribute(name="Tamper")
    protected Boolean tamper;
    @XmlAttribute(name="Fault")
    protected Boolean fault;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getAccess() {
        return this.access;
    }

    public void setAccess(Boolean value) {
        this.access = value;
    }

    public Boolean getAccessTimingOverride() {
        return this.accessTimingOverride;
    }

    public void setAccessTimingOverride(Boolean value) {
        this.accessTimingOverride = value;
    }

    public Boolean getLock() {
        return this.lock;
    }

    public void setLock(Boolean value) {
        this.lock = value;
    }

    public Boolean getUnlock() {
        return this.unlock;
    }

    public void setUnlock(Boolean value) {
        this.unlock = value;
    }

    public Boolean getBlock() {
        return this.block;
    }

    public void setBlock(Boolean value) {
        this.block = value;
    }

    public Boolean getDoubleLock() {
        return this.doubleLock;
    }

    public void setDoubleLock(Boolean value) {
        this.doubleLock = value;
    }

    public Boolean getLockDown() {
        return this.lockDown;
    }

    public void setLockDown(Boolean value) {
        this.lockDown = value;
    }

    public Boolean getLockOpen() {
        return this.lockOpen;
    }

    public void setLockOpen(Boolean value) {
        this.lockOpen = value;
    }

    public Boolean getDoorMonitor() {
        return this.doorMonitor;
    }

    public void setDoorMonitor(Boolean value) {
        this.doorMonitor = value;
    }

    public Boolean getLockMonitor() {
        return this.lockMonitor;
    }

    public void setLockMonitor(Boolean value) {
        this.lockMonitor = value;
    }

    public Boolean getDoubleLockMonitor() {
        return this.doubleLockMonitor;
    }

    public void setDoubleLockMonitor(Boolean value) {
        this.doubleLockMonitor = value;
    }

    public Boolean getAlarm() {
        return this.alarm;
    }

    public void setAlarm(Boolean value) {
        this.alarm = value;
    }

    public Boolean getTamper() {
        return this.tamper;
    }

    public void setTamper(Boolean value) {
        this.tamper = value;
    }

    public Boolean getFault() {
        return this.fault;
    }

    public void setFault(Boolean value) {
        this.fault = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

