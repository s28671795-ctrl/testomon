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
import org.onvif.ver10.schema.NetworkZeroConfiguration;
import org.onvif.ver10.schema.NetworkZeroConfigurationExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkZeroConfigurationExtension", propOrder={"any", "additional", "extension"})
public class NetworkZeroConfigurationExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Additional")
    protected List<NetworkZeroConfiguration> additional;
    @XmlElement(name="Extension")
    protected NetworkZeroConfigurationExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public List<NetworkZeroConfiguration> getAdditional() {
        if (this.additional == null) {
            this.additional = new ArrayList<NetworkZeroConfiguration>();
        }
        return this.additional;
    }

    public NetworkZeroConfigurationExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkZeroConfigurationExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

