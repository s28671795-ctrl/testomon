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
import org.onvif.ver10.schema.WideDynamicMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="WideDynamicRangeOptions20", propOrder={"mode", "level"})
public class WideDynamicRangeOptions20 {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<WideDynamicMode> mode;
    @XmlElement(name="Level")
    protected FloatRange level;

    public List<WideDynamicMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<WideDynamicMode>();
        }
        return this.mode;
    }

    public FloatRange getLevel() {
        return this.level;
    }

    public void setLevel(FloatRange value) {
        this.level = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

