/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.OnvifVersion;
import org.onvif.ver10.schema.SystemCapabilitiesExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SystemCapabilities", propOrder={"discoveryResolve", "discoveryBye", "remoteDiscovery", "systemBackup", "systemLogging", "firmwareUpgrade", "supportedVersions", "extension"})
public class SystemCapabilities {
    @XmlElement(name="DiscoveryResolve")
    protected boolean discoveryResolve;
    @XmlElement(name="DiscoveryBye")
    protected boolean discoveryBye;
    @XmlElement(name="RemoteDiscovery")
    protected boolean remoteDiscovery;
    @XmlElement(name="SystemBackup")
    protected boolean systemBackup;
    @XmlElement(name="SystemLogging")
    protected boolean systemLogging;
    @XmlElement(name="FirmwareUpgrade")
    protected boolean firmwareUpgrade;
    @XmlElement(name="SupportedVersions", required=true)
    protected List<OnvifVersion> supportedVersions;
    @XmlElement(name="Extension")
    protected SystemCapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getDiscoveryResolve() {
        return this.discoveryResolve;
    }

    public void setDiscoveryResolve(boolean value) {
        this.discoveryResolve = value;
    }

    public boolean getDiscoveryBye() {
        return this.discoveryBye;
    }

    public void setDiscoveryBye(boolean value) {
        this.discoveryBye = value;
    }

    public boolean getRemoteDiscovery() {
        return this.remoteDiscovery;
    }

    public void setRemoteDiscovery(boolean value) {
        this.remoteDiscovery = value;
    }

    public boolean getSystemBackup() {
        return this.systemBackup;
    }

    public void setSystemBackup(boolean value) {
        this.systemBackup = value;
    }

    public boolean getSystemLogging() {
        return this.systemLogging;
    }

    public void setSystemLogging(boolean value) {
        this.systemLogging = value;
    }

    public boolean getFirmwareUpgrade() {
        return this.firmwareUpgrade;
    }

    public void setFirmwareUpgrade(boolean value) {
        this.firmwareUpgrade = value;
    }

    public List<OnvifVersion> getSupportedVersions() {
        if (this.supportedVersions == null) {
            this.supportedVersions = new ArrayList<OnvifVersion>();
        }
        return this.supportedVersions;
    }

    public SystemCapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(SystemCapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

