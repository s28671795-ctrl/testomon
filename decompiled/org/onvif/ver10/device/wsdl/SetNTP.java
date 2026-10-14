/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.NetworkHost;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"fromDHCP", "ntpManual"})
@XmlRootElement(name="SetNTP")
public class SetNTP {
    @XmlElement(name="FromDHCP")
    protected boolean fromDHCP;
    @XmlElement(name="NTPManual")
    protected List<NetworkHost> ntpManual;

    public boolean getFromDHCP() {
        return this.fromDHCP;
    }

    public void setFromDHCP(boolean value) {
        this.fromDHCP = value;
    }

    public List<NetworkHost> getNTPManual() {
        if (this.ntpManual == null) {
            this.ntpManual = new ArrayList<NetworkHost>();
        }
        return this.ntpManual;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

