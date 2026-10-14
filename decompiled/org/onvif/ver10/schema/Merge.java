/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ObjectId;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Merge", propOrder={"from", "to"})
public class Merge {
    @XmlElement(required=true)
    protected List<ObjectId> from;
    @XmlElement(required=true)
    protected ObjectId to;

    public List<ObjectId> getFrom() {
        if (this.from == null) {
            this.from = new ArrayList<ObjectId>();
        }
        return this.from;
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

