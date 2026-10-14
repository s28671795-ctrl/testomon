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
import org.onvif.ver10.schema.BacklightCompensationMode;
import org.onvif.ver10.schema.FloatRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BacklightCompensationOptions20", propOrder={"mode", "level"})
public class BacklightCompensationOptions20 {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<BacklightCompensationMode> mode;
    @XmlElement(name="Level")
    protected FloatRange level;

    public List<BacklightCompensationMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<BacklightCompensationMode>();
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

