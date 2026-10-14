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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Dot11Capabilities", propOrder={"tkip", "scanAvailableNetworks", "multipleConfiguration", "adHocStationMode", "wep", "any"})
public class Dot11Capabilities {
    @XmlElement(name="TKIP")
    protected boolean tkip;
    @XmlElement(name="ScanAvailableNetworks")
    protected boolean scanAvailableNetworks;
    @XmlElement(name="MultipleConfiguration")
    protected boolean multipleConfiguration;
    @XmlElement(name="AdHocStationMode")
    protected boolean adHocStationMode;
    @XmlElement(name="WEP")
    protected boolean wep;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getTKIP() {
        return this.tkip;
    }

    public void setTKIP(boolean value) {
        this.tkip = value;
    }

    public boolean getScanAvailableNetworks() {
        return this.scanAvailableNetworks;
    }

    public void setScanAvailableNetworks(boolean value) {
        this.scanAvailableNetworks = value;
    }

    public boolean getMultipleConfiguration() {
        return this.multipleConfiguration;
    }

    public void setMultipleConfiguration(boolean value) {
        this.multipleConfiguration = value;
    }

    public boolean getAdHocStationMode() {
        return this.adHocStationMode;
    }

    public void setAdHocStationMode(boolean value) {
        this.adHocStationMode = value;
    }

    public boolean getWEP() {
        return this.wep;
    }

    public void setWEP(boolean value) {
        this.wep = value;
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

