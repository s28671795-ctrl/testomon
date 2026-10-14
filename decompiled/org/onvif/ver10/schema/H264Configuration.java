/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.H264Profile;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="H264Configuration", propOrder={"govLength", "h264Profile"})
public class H264Configuration {
    @XmlElement(name="GovLength")
    protected int govLength;
    @XmlElement(name="H264Profile", required=true)
    @XmlSchemaType(name="string")
    protected H264Profile h264Profile;

    public int getGovLength() {
        return this.govLength;
    }

    public void setGovLength(int value) {
        this.govLength = value;
    }

    public H264Profile getH264Profile() {
        return this.h264Profile;
    }

    public void setH264Profile(H264Profile value) {
        this.h264Profile = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

