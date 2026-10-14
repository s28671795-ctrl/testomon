/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="subcode", propOrder={"value", "subcode"})
public class Subcode {
    @XmlElement(name="Value", required=true)
    protected QName value;
    @XmlElement(name="Subcode")
    protected Subcode subcode;

    public QName getValue() {
        return this.value;
    }

    public void setValue(QName value) {
        this.value = value;
    }

    public Subcode getSubcode() {
        return this.subcode;
    }

    public void setSubcode(Subcode value) {
        this.subcode = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

