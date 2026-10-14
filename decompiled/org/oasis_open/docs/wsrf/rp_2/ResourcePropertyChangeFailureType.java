/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ResourcePropertyChangeFailureType", propOrder={"currentValue", "requestedValue"})
public class ResourcePropertyChangeFailureType {
    @XmlElement(name="CurrentValue")
    protected CurrentValue currentValue;
    @XmlElement(name="RequestedValue")
    protected RequestedValue requestedValue;
    @XmlAttribute(name="Restored")
    protected Boolean restored;

    public CurrentValue getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue(CurrentValue value) {
        this.currentValue = value;
    }

    public RequestedValue getRequestedValue() {
        return this.requestedValue;
    }

    public void setRequestedValue(RequestedValue value) {
        this.requestedValue = value;
    }

    public Boolean isRestored() {
        return this.restored;
    }

    public void setRestored(Boolean value) {
        this.restored = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class RequestedValue {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

        public List<Object> getAny() {
            if (this.any == null) {
                this.any = new ArrayList<Object>();
            }
            return this.any;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class CurrentValue {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

        public List<Object> getAny() {
            if (this.any == null) {
                this.any = new ArrayList<Object>();
            }
            return this.any;
        }
    }
}

