/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.wstrust14;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ContextDataType", propOrder={"any"})
public class ContextDataType {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="RefID", required=true)
    @XmlSchemaType(name="anyURI")
    protected String refID;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getRefID() {
        return this.refID;
    }

    public void setRefID(String value) {
        this.refID = value;
    }
}

