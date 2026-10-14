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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="string", propOrder={"value"})
public class CString {
    @XmlValue
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String value;
    @XmlAttribute
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String v) {
        this.id = v;
    }
}

