/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="xsdQNameType", propOrder={"value"})
public class XsdQNameType {
    @XmlValue
    protected QName value;
    @XmlAttribute
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    public QName getValue() {
        return this.value;
    }

    public void setValue(QName value) {
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String v) {
        this.id = v;
    }
}

