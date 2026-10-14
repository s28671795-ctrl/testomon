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
import org.onvif.ver10.schema.Dot11Configuration;
import org.onvif.ver10.schema.Dot3Configuration;
import org.onvif.ver10.schema.NetworkInterfaceSetConfigurationExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkInterfaceSetConfigurationExtension", propOrder={"any", "dot3", "dot11", "extension"})
public class NetworkInterfaceSetConfigurationExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Dot3")
    protected List<Dot3Configuration> dot3;
    @XmlElement(name="Dot11")
    protected List<Dot11Configuration> dot11;
    @XmlElement(name="Extension")
    protected NetworkInterfaceSetConfigurationExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public List<Dot3Configuration> getDot3() {
        if (this.dot3 == null) {
            this.dot3 = new ArrayList<Dot3Configuration>();
        }
        return this.dot3;
    }

    public List<Dot11Configuration> getDot11() {
        if (this.dot11 == null) {
            this.dot11 = new ArrayList<Dot11Configuration>();
        }
        return this.dot11;
    }

    public NetworkInterfaceSetConfigurationExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkInterfaceSetConfigurationExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

