/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="reasontext", propOrder={"value"})
public class Reasontext {
    @XmlValue
    protected String value;
    @XmlAttribute(name="lang", namespace="http://www.w3.org/XML/1998/namespace", required=true)
    protected String lang;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String value) {
        this.lang = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

