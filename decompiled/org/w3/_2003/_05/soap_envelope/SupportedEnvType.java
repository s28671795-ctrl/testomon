/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SupportedEnvType")
public class SupportedEnvType {
    @XmlAttribute(name="qname", required=true)
    protected QName qname;

    public QName getQname() {
        return this.qname;
    }

    public void setQname(QName value) {
        this.qname = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

