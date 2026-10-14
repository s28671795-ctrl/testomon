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
@XmlType(name="BackupFile", propOrder={"name", "data"})
public class BackupFile {
    @XmlElement(name="Name", required=true)
    protected String name;
    @XmlElement(name="Data", required=true)
    protected AttachmentData data;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public AttachmentData getData() {
        return this.data;
    }

    public void setData(AttachmentData value) {
        this.data = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

