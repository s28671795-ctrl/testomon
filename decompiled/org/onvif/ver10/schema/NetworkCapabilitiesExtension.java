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
import org.onvif.ver10.schema.NetworkCapabilitiesExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkCapabilitiesExtension", propOrder={"any", "dot11Configuration", "extension"})
public class NetworkCapabilitiesExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Dot11Configuration")
    protected Boolean dot11Configuration;
    @XmlElement(name="Extension")
    protected NetworkCapabilitiesExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getDot11Configuration() {
        return this.dot11Configuration;
    }

    public void setDot11Configuration(Boolean value) {
        this.dot11Configuration = value;
    }

    public NetworkCapabilitiesExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkCapabilitiesExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

