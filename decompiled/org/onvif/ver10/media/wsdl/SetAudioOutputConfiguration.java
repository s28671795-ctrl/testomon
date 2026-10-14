/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioOutputConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"configuration", "forcePersistence"})
@XmlRootElement(name="SetAudioOutputConfiguration")
public class SetAudioOutputConfiguration {
    @XmlElement(name="Configuration", required=true)
    protected AudioOutputConfiguration configuration;
    @XmlElement(name="ForcePersistence")
    protected boolean forcePersistence;

    public AudioOutputConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(AudioOutputConfiguration value) {
        this.configuration = value;
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

