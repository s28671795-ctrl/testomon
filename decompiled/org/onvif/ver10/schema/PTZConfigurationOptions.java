/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DurationRange;
import org.onvif.ver10.schema.PTControlDirectionOptions;
import org.onvif.ver10.schema.PTZConfigurationOptions2;
import org.onvif.ver10.schema.PTZSpaces;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZConfigurationOptions", propOrder={"spaces", "ptzTimeout", "any", "ptControlDirection", "extension"})
public class PTZConfigurationOptions {
    @XmlElement(name="Spaces", required=true)
    protected PTZSpaces spaces;
    @XmlElement(name="PTZTimeout", required=true)
    protected DurationRange ptzTimeout;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="PTControlDirection")
    protected PTControlDirectionOptions ptControlDirection;
    @XmlElement(name="Extension")
    protected PTZConfigurationOptions2 extension;
    @XmlAttribute(name="PTZRamps")
    protected List<Integer> ptzRamps;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZSpaces getSpaces() {
        return this.spaces;
    }

    public void setSpaces(PTZSpaces value) {
        this.spaces = value;
    }

    public DurationRange getPTZTimeout() {
        return this.ptzTimeout;
    }

    public void setPTZTimeout(DurationRange value) {
        this.ptzTimeout = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public PTControlDirectionOptions getPTControlDirection() {
        return this.ptControlDirection;
    }

    public void setPTControlDirection(PTControlDirectionOptions value) {
        this.ptControlDirection = value;
    }

    public PTZConfigurationOptions2 getExtension() {
        return this.extension;
    }

    public void setExtension(PTZConfigurationOptions2 value) {
        this.extension = value;
    }

    public List<Integer> getPTZRamps() {
        if (this.ptzRamps == null) {
            this.ptzRamps = new ArrayList<Integer>();
        }
        return this.ptzRamps;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

