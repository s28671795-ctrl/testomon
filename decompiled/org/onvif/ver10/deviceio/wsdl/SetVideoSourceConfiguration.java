/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.VideoSourceConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"configuration", "forcePersistence", "any"})
@XmlRootElement(name="SetVideoSourceConfiguration")
public class SetVideoSourceConfiguration {
    @XmlElement(name="Configuration", required=true)
    protected VideoSourceConfiguration configuration;
    @XmlElement(name="ForcePersistence")
    protected boolean forcePersistence;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public VideoSourceConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(VideoSourceConfiguration value) {
        this.configuration = value;
    }

    public boolean getForcePersistence() {
        return this.forcePersistence;
    }

    public void setForcePersistence(boolean value) {
        this.forcePersistence = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

