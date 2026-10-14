/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Object;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ObjectId")
@XmlSeeAlso(value={Object.class})
public class ObjectId {
    @XmlAttribute(name="ObjectId")
    protected BigInteger objectId;

    public BigInteger getObjectId() {
        return this.objectId;
    }

    public void setObjectId(BigInteger value) {
        this.objectId = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

