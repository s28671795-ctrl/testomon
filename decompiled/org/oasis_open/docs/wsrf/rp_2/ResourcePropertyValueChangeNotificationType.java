/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ResourcePropertyValueChangeNotificationType", propOrder={"oldValues", "newValues"})
public class ResourcePropertyValueChangeNotificationType {
    @XmlElementRef(name="OldValues", namespace="http://docs.oasis-open.org/wsrf/rp-2", type=JAXBElement.class, required=false)
    protected JAXBElement<OldValues> oldValues;
    @XmlElement(name="NewValues", required=true, nillable=true)
    protected NewValues newValues = new NewValues();

    public JAXBElement<OldValues> getOldValues() {
        return this.oldValues;
    }

    public void setOldValues(JAXBElement<OldValues> value) {
        this.oldValues = value;
    }

    public NewValues getNewValues() {
        return this.newValues;
    }

    public void setNewValues(NewValues value) {
        this.newValues = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class OldValues {
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
    public static class NewValues {
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

