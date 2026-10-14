/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FloatRange;
import org.onvif.ver10.schema.WhiteBalanceMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="WhiteBalanceOptions", propOrder={"mode", "yrGain", "ybGain"})
public class WhiteBalanceOptions {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<WhiteBalanceMode> mode;
    @XmlElement(name="YrGain", required=true)
    protected FloatRange yrGain;
    @XmlElement(name="YbGain", required=true)
    protected FloatRange ybGain;

    public List<WhiteBalanceMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<WhiteBalanceMode>();
        }
        return this.mode;
    }

    public FloatRange getYrGain() {
        return this.yrGain;
    }

    public void setYrGain(FloatRange value) {
        this.yrGain = value;
    }

    public FloatRange getYbGain() {
        return this.ybGain;
    }

    public void setYbGain(FloatRange value) {
        this.ybGain = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

