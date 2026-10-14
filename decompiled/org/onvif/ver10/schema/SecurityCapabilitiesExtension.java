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
import org.onvif.ver10.schema.SecurityCapabilitiesExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SecurityCapabilitiesExtension", propOrder={"tls10", "extension"})
public class SecurityCapabilitiesExtension {
    @XmlElement(name="TLS1.0")
    protected boolean tls10;
    @XmlElement(name="Extension")
    protected SecurityCapabilitiesExtension2 extension;

    public boolean getTLS10() {
        return this.tls10;
    }

    public void setTLS10(boolean value) {
        this.tls10 = value;
    }

    public SecurityCapabilitiesExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(SecurityCapabilitiesExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

