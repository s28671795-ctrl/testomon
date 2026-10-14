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
import org.onvif.ver10.schema.Dot11Cipher;
import org.onvif.ver10.schema.Dot11SignalStrength;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Dot11Status", propOrder={"ssid", "bssid", "pairCipher", "groupCipher", "signalStrength", "activeConfigAlias", "any"})
public class Dot11Status {
    @XmlElement(name="SSID", required=true, type=String.class)
    @XmlJavaTypeAdapter(value=HexBinaryAdapter.class)
    @XmlSchemaType(name="hexBinary")
    protected byte[] ssid;
    @XmlElement(name="BSSID")
    protected String bssid;
    @XmlElement(name="PairCipher")
    @XmlSchemaType(name="string")
    protected Dot11Cipher pairCipher;
    @XmlElement(name="GroupCipher")
    @XmlSchemaType(name="string")
    protected Dot11Cipher groupCipher;
    @XmlElement(name="SignalStrength")
    @XmlSchemaType(name="string")
    protected Dot11SignalStrength signalStrength;
    @XmlElement(name="ActiveConfigAlias", required=true)
    protected String activeConfigAlias;
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

    public String getBSSID() {
        return this.bssid;
    }

    public void setBSSID(String value) {
        this.bssid = value;
    }

    public Dot11Cipher getPairCipher() {
        return this.pairCipher;
    }

    public void setPairCipher(Dot11Cipher value) {
        this.pairCipher = value;
    }

    public Dot11Cipher getGroupCipher() {
        return this.groupCipher;
    }

    public void setGroupCipher(Dot11Cipher value) {
        this.groupCipher = value;
    }

    public Dot11SignalStrength getSignalStrength() {
        return this.signalStrength;
    }

    public void setSignalStrength(Dot11SignalStrength value) {
        this.signalStrength = value;
    }

    public String getActiveConfigAlias() {
        return this.activeConfigAlias;
    }

    public void setActiveConfigAlias(String value) {
        this.activeConfigAlias = value;
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

