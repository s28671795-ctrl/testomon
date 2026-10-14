/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AttachmentData;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SystemLog", propOrder={"binary", "string"})
public class SystemLog {
    @XmlElement(name="Binary")
    protected AttachmentData binary;
    @XmlElement(name="String")
    protected String string;

    public AttachmentData getBinary() {
        return this.binary;
    }

    public void setBinary(AttachmentData value) {
        this.binary = value;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String value) {
        this.string = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

