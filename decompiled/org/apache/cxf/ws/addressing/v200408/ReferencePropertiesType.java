/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.v200408;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ReferencePropertiesType", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", propOrder={"any"})
public class ReferencePropertiesType {
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }
}

