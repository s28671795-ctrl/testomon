/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IPv6Configuration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IPv6NetworkInterface", propOrder={"enabled", "config"})
public class IPv6NetworkInterface {
    @XmlElement(name="Enabled")
    protected boolean enabled;
    @XmlElement(name="Config")
    protected IPv6Configuration config;

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    public IPv6Configuration getConfig() {
        return this.config;
    }

    public void setConfig(IPv6Configuration value) {
        this.config = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

