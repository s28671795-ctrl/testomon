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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.NTPInformationExtension;
import org.onvif.ver10.schema.NetworkHost;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NTPInformation", propOrder={"fromDHCP", "ntpFromDHCP", "ntpManual", "extension"})
public class NTPInformation {
    @XmlElement(name="FromDHCP")
    protected boolean fromDHCP;
    @XmlElement(name="NTPFromDHCP")
    protected List<NetworkHost> ntpFromDHCP;
    @XmlElement(name="NTPManual")
    protected List<NetworkHost> ntpManual;
    @XmlElement(name="Extension")
    protected NTPInformationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getFromDHCP() {
        return this.fromDHCP;
    }

    public void setFromDHCP(boolean value) {
        this.fromDHCP = value;
    }

    public List<NetworkHost> getNTPFromDHCP() {
        if (this.ntpFromDHCP == null) {
            this.ntpFromDHCP = new ArrayList<NetworkHost>();
        }
        return this.ntpFromDHCP;
    }

    public List<NetworkHost> getNTPManual() {
        if (this.ntpManual == null) {
            this.ntpManual = new ArrayList<NetworkHost>();
        }
        return this.ntpManual;
    }

    public NTPInformationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(NTPInformationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

