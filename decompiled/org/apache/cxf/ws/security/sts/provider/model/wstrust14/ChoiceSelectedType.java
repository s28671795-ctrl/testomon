/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.wstrust14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ChoiceSelectedType")
public class ChoiceSelectedType {
    @XmlAttribute(name="RefId", required=true)
    @XmlSchemaType(name="anyURI")
    protected String refId;

    public String getRefId() {
        return this.refId;
    }

    public void setRefId(String value) {
        this.refId = value;
    }
}

