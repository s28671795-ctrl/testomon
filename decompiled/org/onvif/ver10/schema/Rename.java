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
import org.onvif.ver10.schema.ObjectId;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Rename", propOrder={"from", "to"})
public class Rename {
    @XmlElement(required=true)
    protected ObjectId from;
    @XmlElement(required=true)
    protected ObjectId to;

    public ObjectId getFrom() {
        return this.from;
    }

    public void setFrom(ObjectId value) {
        this.from = value;
    }

    public ObjectId getTo() {
        return this.to;
    }

    public void setTo(ObjectId value) {
        this.to = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

