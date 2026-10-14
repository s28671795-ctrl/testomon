/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.wstrust14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImageType", propOrder={"value"})
public class ImageType {
    @XmlValue
    protected byte[] value;
    @XmlAttribute(name="MimeType")
    protected String mimeType;

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String value) {
        this.mimeType = value;
    }
}

