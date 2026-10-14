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
import org.onvif.ver10.schema.WhiteBalanceOptions20Extension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="WhiteBalanceOptions20", propOrder={"mode", "yrGain", "ybGain", "extension"})
public class WhiteBalanceOptions20 {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<WhiteBalanceMode> mode;
    @XmlElement(name="YrGain")
    protected FloatRange yrGain;
    @XmlElement(name="YbGain")
    protected FloatRange ybGain;
    @XmlElement(name="Extension")
    protected WhiteBalanceOptions20Extension extension;

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

    public WhiteBalanceOptions20Extension getExtension() {
        return this.extension;
    }

    public void setExtension(WhiteBalanceOptions20Extension value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

