/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BinaryData", propOrder={"data"})
public class BinaryData {
    @XmlElement(name="Data", required=true)
    protected byte[] data;
    @XmlAttribute(name="contentType", namespace="http://www.w3.org/2005/05/xmlmime")
    protected String contentType;

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] value) {
        this.data = value;
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

