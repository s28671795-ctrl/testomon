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
import org.onvif.ver10.schema.BacklightCompensationMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BacklightCompensation", propOrder={"mode", "level"})
public class BacklightCompensation {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected BacklightCompensationMode mode;
    @XmlElement(name="Level")
    protected float level;

    public BacklightCompensationMode getMode() {
        return this.mode;
    }

    public void setMode(BacklightCompensationMode value) {
        this.mode = value;
    }

    public float getLevel() {
        return this.level;
    }

    public void setLevel(float value) {
        this.level = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

