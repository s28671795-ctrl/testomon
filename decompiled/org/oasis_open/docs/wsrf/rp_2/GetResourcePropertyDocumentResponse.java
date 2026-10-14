/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"any"})
@XmlRootElement(name="GetResourcePropertyDocumentResponse")
public class GetResourcePropertyDocumentResponse {
    @XmlAnyElement(lax=true)
    protected Object any;

    public Object getAny() {
        return this.any;
    }

    public void setAny(Object value) {
        this.any = value;
    }
}

