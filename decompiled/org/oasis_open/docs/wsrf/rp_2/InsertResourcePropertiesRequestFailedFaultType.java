/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.oasis_open.docs.wsrf.bf_2.BaseFaultType;
import org.oasis_open.docs.wsrf.rp_2.ResourcePropertyChangeFailureType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="InsertResourcePropertiesRequestFailedFaultType", propOrder={"resourcePropertyChangeFailure"})
public class InsertResourcePropertiesRequestFailedFaultType
extends BaseFaultType {
    @XmlElement(name="ResourcePropertyChangeFailure", required=true)
    protected ResourcePropertyChangeFailureType resourcePropertyChangeFailure = new ResourcePropertyChangeFailureType();

    public ResourcePropertyChangeFailureType getResourcePropertyChangeFailure() {
        return this.resourcePropertyChangeFailure;
    }

    public void setResourcePropertyChangeFailure(ResourcePropertyChangeFailureType value) {
        this.resourcePropertyChangeFailure = value;
    }
}

