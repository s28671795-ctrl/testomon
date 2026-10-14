/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SystemCapabilities")
public class SystemCapabilities {
    @XmlAttribute(name="DiscoveryResolve")
    protected Boolean discoveryResolve;
    @XmlAttribute(name="DiscoveryBye")
    protected Boolean discoveryBye;
    @XmlAttribute(name="RemoteDiscovery")
    protected Boolean remoteDiscovery;
    @XmlAttribute(name="SystemBackup")
    protected Boolean systemBackup;
    @XmlAttribute(name="SystemLogging")
    protected Boolean systemLogging;
    @XmlAttribute(name="FirmwareUpgrade")
    protected Boolean firmwareUpgrade;
    @XmlAttribute(name="HttpFirmwareUpgrade")
    protected Boolean httpFirmwareUpgrade;
    @XmlAttribute(name="HttpSystemBackup")
    protected Boolean httpSystemBackup;
    @XmlAttribute(name="HttpSystemLogging")
    protected Boolean httpSystemLogging;
    @XmlAttribute(name="HttpSupportInformation")
    protected Boolean httpSupportInformation;
    @XmlAttribute(name="StorageConfiguration")
    protected Boolean storageConfiguration;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Boolean getDiscoveryResolve() {
        return this.discoveryResolve;
    }

    public void setDiscoveryResolve(Boolean value) {
        this.discoveryResolve = value;
    }

    public Boolean getDiscoveryBye() {
        return this.discoveryBye;
    }

    public void setDiscoveryBye(Boolean value) {
        this.discoveryBye = value;
    }

    public Boolean getRemoteDiscovery() {
        return this.remoteDiscovery;
    }

    public void setRemoteDiscovery(Boolean value) {
        this.remoteDiscovery = value;
    }

    public Boolean getSystemBackup() {
        return this.systemBackup;
    }

    public void setSystemBackup(Boolean value) {
        this.systemBackup = value;
    }

    public Boolean getSystemLogging() {
        return this.systemLogging;
    }

    public void setSystemLogging(Boolean value) {
        this.systemLogging = value;
    }

    public Boolean getFirmwareUpgrade() {
        return this.firmwareUpgrade;
    }

    public void setFirmwareUpgrade(Boolean value) {
        this.firmwareUpgrade = value;
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

    public Boolean getStorageConfiguration() {
        return this.storageConfiguration;
    }

    public void setStorageConfiguration(Boolean value) {
        this.storageConfiguration = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

