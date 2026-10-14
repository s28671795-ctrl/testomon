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
import org.onvif.ver10.schema.NetworkInterfaceExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkInterfaceExtension", propOrder={"any", "interfaceType", "dot3", "dot11", "extension"})
public class NetworkInterfaceExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="InterfaceType")
    protected int interfaceType;
    @XmlElement(name="Dot3")
    protected List<Dot3Configuration> dot3;
    @XmlElement(name="Dot11")
    protected List<Dot11Configuration> dot11;
    @XmlElement(name="Extension")
    protected NetworkInterfaceExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public int getInterfaceType() {
        return this.interfaceType;
    }

    public void setInterfaceType(int value) {
        this.interfaceType = value;
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

    public NetworkInterfaceExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkInterfaceExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

