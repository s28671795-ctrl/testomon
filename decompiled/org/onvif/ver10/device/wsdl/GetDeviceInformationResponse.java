/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"manufacturer", "model", "firmwareVersion", "serialNumber", "hardwareId"})
@XmlRootElement(name="GetDeviceInformationResponse")
public class GetDeviceInformationResponse {
    @XmlElement(name="Manufacturer", required=true)
    protected String manufacturer;
    @XmlElement(name="Model", required=true)
    protected String model;
    @XmlElement(name="FirmwareVersion", required=true)
    protected String firmwareVersion;
    @XmlElement(name="SerialNumber", required=true)
    protected String serialNumber;
    @XmlElement(name="HardwareId", required=true)
    protected String hardwareId;

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String value) {
        this.model = value;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String value) {
        this.firmwareVersion = value;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    public String getHardwareId() {
        return this.hardwareId;
    }

    public void setHardwareId(String value) {
        this.hardwareId = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

