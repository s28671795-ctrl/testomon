/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTControlDirection;
import org.onvif.ver10.schema.PTZConfigurationExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZConfigurationExtension", propOrder={"any", "ptControlDirection", "extension"})
public class PTZConfigurationExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="PTControlDirection")
    protected PTControlDirection ptControlDirection;
    @XmlElement(name="Extension")
    protected PTZConfigurationExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public PTControlDirection getPTControlDirection() {
        return this.ptControlDirection;
    }

    public void setPTControlDirection(PTControlDirection value) {
        this.ptControlDirection = value;
    }

    public PTZConfigurationExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(PTZConfigurationExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

