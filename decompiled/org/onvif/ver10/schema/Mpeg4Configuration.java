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
import org.onvif.ver10.schema.Mpeg4Profile;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Mpeg4Configuration", propOrder={"govLength", "mpeg4Profile"})
public class Mpeg4Configuration {
    @XmlElement(name="GovLength")
    protected int govLength;
    @XmlElement(name="Mpeg4Profile", required=true)
    @XmlSchemaType(name="string")
    protected Mpeg4Profile mpeg4Profile;

    public int getGovLength() {
        return this.govLength;
    }

    public void setGovLength(int value) {
        this.govLength = value;
    }

    public Mpeg4Profile getMpeg4Profile() {
        return this.mpeg4Profile;
    }

    public void setMpeg4Profile(Mpeg4Profile value) {
        this.mpeg4Profile = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

