/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2005._05.xmlmime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="hexBinary", propOrder={"value"})
public class HexBinary {
    @XmlValue
    @XmlJavaTypeAdapter(value=HexBinaryAdapter.class)
    @XmlSchemaType(name="hexBinary")
    protected byte[] value;
    @XmlAttribute(name="contentType", namespace="http://www.w3.org/2005/05/xmlmime")
    protected String contentType;

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String value) {
        this.contentType = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

