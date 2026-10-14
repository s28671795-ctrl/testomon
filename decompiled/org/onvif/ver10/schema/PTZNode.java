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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DeviceEntity;
import org.onvif.ver10.schema.PTZNodeExtension;
import org.onvif.ver10.schema.PTZSpaces;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZNode", propOrder={"name", "supportedPTZSpaces", "maximumNumberOfPresets", "homeSupported", "auxiliaryCommands", "extension"})
public class PTZNode
extends DeviceEntity {
    @XmlElement(name="Name")
    protected String name;
    @XmlElement(name="SupportedPTZSpaces", required=true)
    protected PTZSpaces supportedPTZSpaces;
    @XmlElement(name="MaximumNumberOfPresets")
    protected int maximumNumberOfPresets;
    @XmlElement(name="HomeSupported")
    protected boolean homeSupported;
    @XmlElement(name="AuxiliaryCommands")
    protected List<String> auxiliaryCommands;
    @XmlElement(name="Extension")
    protected PTZNodeExtension extension;
    @XmlAttribute(name="FixedHomePosition")
    protected Boolean fixedHomePosition;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public PTZSpaces getSupportedPTZSpaces() {
        return this.supportedPTZSpaces;
    }

    public void setSupportedPTZSpaces(PTZSpaces value) {
        this.supportedPTZSpaces = value;
    }

    public int getMaximumNumberOfPresets() {
        return this.maximumNumberOfPresets;
    }

    public void setMaximumNumberOfPresets(int value) {
        this.maximumNumberOfPresets = value;
    }

    public boolean getHomeSupported() {
        return this.homeSupported;
    }

    public void setHomeSupported(boolean value) {
        this.homeSupported = value;
    }

    public List<String> getAuxiliaryCommands() {
        if (this.auxiliaryCommands == null) {
            this.auxiliaryCommands = new ArrayList<String>();
        }
        return this.auxiliaryCommands;
    }

    public PTZNodeExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZNodeExtension value) {
        this.extension = value;
    }

    public Boolean getFixedHomePosition() {
        return this.fixedHomePosition;
    }

    public void setFixedHomePosition(Boolean value) {
        this.fixedHomePosition = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

