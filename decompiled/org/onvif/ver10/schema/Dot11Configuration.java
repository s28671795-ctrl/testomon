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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Dot11SecurityConfiguration;
import org.onvif.ver10.schema.Dot11StationMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Dot11Configuration", propOrder={"ssid", "mode", "alias", "priority", "security", "any"})
public class Dot11Configuration {
    @XmlElement(name="SSID", required=true, type=String.class)
    @XmlJavaTypeAdapter(value=HexBinaryAdapter.class)
    @XmlSchemaType(name="hexBinary")
    protected byte[] ssid;
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected Dot11StationMode mode;
    @XmlElement(name="Alias", required=true)
    protected String alias;
    @XmlElement(name="Priority")
    @XmlSchemaType(name="integer")
    protected int priority;
    @XmlElement(name="Security", required=true)
    protected Dot11SecurityConfiguration security;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public byte[] getSSID() {
        return this.ssid;
    }

    public void setSSID(byte[] value) {
        this.ssid = value;
    }

    public Dot11StationMode getMode() {
        return this.mode;
    }

    public void setMode(Dot11StationMode value) {
        this.mode = value;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int value) {
        this.priority = value;
    }

    public Dot11SecurityConfiguration getSecurity() {
        return this.security;
    }

    public void setSecurity(Dot11SecurityConfiguration value) {
        this.security = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

