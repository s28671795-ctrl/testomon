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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IOCapabilitiesExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IOCapabilitiesExtension", propOrder={"any", "auxiliary", "auxiliaryCommands", "extension"})
public class IOCapabilitiesExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Auxiliary")
    protected Boolean auxiliary;
    @XmlElement(name="AuxiliaryCommands")
    protected List<String> auxiliaryCommands;
    @XmlElement(name="Extension", required=true)
    protected IOCapabilitiesExtension2 extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getAuxiliary() {
        return this.auxiliary;
    }

    public void setAuxiliary(Boolean value) {
        this.auxiliary = value;
    }

    public List<String> getAuxiliaryCommands() {
        if (this.auxiliaryCommands == null) {
            this.auxiliaryCommands = new ArrayList<String>();
        }
        return this.auxiliaryCommands;
    }

    public IOCapabilitiesExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(IOCapabilitiesExtension2 value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

