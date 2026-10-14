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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Dot11AuthAndMangementSuite;
import org.onvif.ver10.schema.Dot11AvailableNetworksExtension;
import org.onvif.ver10.schema.Dot11Cipher;
import org.onvif.ver10.schema.Dot11SignalStrength;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Dot11AvailableNetworks", propOrder={"ssid", "bssid", "authAndMangementSuite", "pairCipher", "groupCipher", "signalStrength", "extension"})
public class Dot11AvailableNetworks {
    @XmlElement(name="SSID", required=true, type=String.class)
    @XmlJavaTypeAdapter(value=HexBinaryAdapter.class)
    @XmlSchemaType(name="hexBinary")
    protected byte[] ssid;
    @XmlElement(name="BSSID")
    protected String bssid;
    @XmlElement(name="AuthAndMangementSuite")
    @XmlSchemaType(name="string")
    protected List<Dot11AuthAndMangementSuite> authAndMangementSuite;
    @XmlElement(name="PairCipher")
    @XmlSchemaType(name="string")
    protected List<Dot11Cipher> pairCipher;
    @XmlElement(name="GroupCipher")
    @XmlSchemaType(name="string")
    protected List<Dot11Cipher> groupCipher;
    @XmlElement(name="SignalStrength")
    @XmlSchemaType(name="string")
    protected Dot11SignalStrength signalStrength;
    @XmlElement(name="Extension")
    protected Dot11AvailableNetworksExtension extension;
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

    public List<Dot11AuthAndMangementSuite> getAuthAndMangementSuite() {
        if (this.authAndMangementSuite == null) {
            this.authAndMangementSuite = new ArrayList<Dot11AuthAndMangementSuite>();
        }
        return this.authAndMangementSuite;
    }

    public List<Dot11Cipher> getPairCipher() {
        if (this.pairCipher == null) {
            this.pairCipher = new ArrayList<Dot11Cipher>();
        }
        return this.pairCipher;
    }

    public List<Dot11Cipher> getGroupCipher() {
        if (this.groupCipher == null) {
            this.groupCipher = new ArrayList<Dot11Cipher>();
        }
        return this.groupCipher;
    }

    public Dot11SignalStrength getSignalStrength() {
        return this.signalStrength;
    }

    public void setSignalStrength(Dot11SignalStrength value) {
        this.signalStrength = value;
    }

    public Dot11AvailableNetworksExtension getExtension() {
        return this.extension;
    }

    public void setExtension(Dot11AvailableNetworksExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

