/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ptzConfiguration", "forcePersistence"})
@XmlRootElement(name="SetConfiguration")
public class SetConfiguration {
    @XmlElement(name="PTZConfiguration", required=true)
    protected PTZConfiguration ptzConfiguration;
    @XmlElement(name="ForcePersistence")
    protected boolean forcePersistence;

    public PTZConfiguration getPTZConfiguration() {
        return this.ptzConfiguration;
    }

    public void setPTZConfiguration(PTZConfiguration value) {
        this.ptzConfiguration = value;
    }

    public boolean getForcePersistence() {
        return this.forcePersistence;
    }

    public void setForcePersistence(boolean value) {
        this.forcePersistence = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

