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
import org.onvif.ver10.schema.WideDynamicMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="WideDynamicRange20", propOrder={"mode", "level"})
public class WideDynamicRange20 {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected WideDynamicMode mode;
    @XmlElement(name="Level")
    protected Float level;

    public WideDynamicMode getMode() {
        return this.mode;
    }

    public void setMode(WideDynamicMode value) {
        this.mode = value;
    }

    public Float getLevel() {
        return this.level;
    }

    public void setLevel(Float value) {
        this.level = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

