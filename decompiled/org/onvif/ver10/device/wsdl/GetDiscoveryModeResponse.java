/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DiscoveryMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"discoveryMode"})
@XmlRootElement(name="GetDiscoveryModeResponse")
public class GetDiscoveryModeResponse {
    @XmlElement(name="DiscoveryMode", required=true)
    @XmlSchemaType(name="string")
    protected DiscoveryMode discoveryMode;

    public DiscoveryMode getDiscoveryMode() {
        return this.discoveryMode;
    }

    public void setDiscoveryMode(DiscoveryMode value) {
        this.discoveryMode = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

