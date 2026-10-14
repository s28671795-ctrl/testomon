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
import org.onvif.ver10.schema.SystemCapabilitiesExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SystemCapabilitiesExtension", propOrder={"any", "httpFirmwareUpgrade", "httpSystemBackup", "httpSystemLogging", "httpSupportInformation", "extension"})
public class SystemCapabilitiesExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="HttpFirmwareUpgrade")
    protected Boolean httpFirmwareUpgrade;
    @XmlElement(name="HttpSystemBackup")
    protected Boolean httpSystemBackup;
    @XmlElement(name="HttpSystemLogging")
    protected Boolean httpSystemLogging;
    @XmlElement(name="HttpSupportInformation")
    protected Boolean httpSupportInformation;
    @XmlElement(name="Extension")
    protected SystemCapabilitiesExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getHttpFirmwareUpgrade() {
        return this.httpFirmwareUpgrade;
    }

    public void setHttpFirmwareUpgrade(Boolean value) {
        this.httpFirmwareUpgrade = value;
    }

    public Boolean getHttpSystemBackup() {
        return this.httpSystemBackup;
    }

    public void setHttpSystemBackup(Boolean value) {
        this.httpSystemBackup = value;
    }

    public Boolean getHttpSystemLogging() {
        return this.httpSystemLogging;
    }

    public void setHttpSystemLogging(Boolean value) {
        this.httpSystemLogging = value;
    }

    public Boolean getHttpSupportInformation() {
        return this.httpSupportInformation;
    }

    public void setHttpSupportInformation(Boolean value) {
        this.httpSupportInformation = value;
    }

    public SystemCapabilitiesExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(SystemCapabilitiesExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

