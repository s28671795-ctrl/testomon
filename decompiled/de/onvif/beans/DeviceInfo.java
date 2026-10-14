/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.beans;

public class DeviceInfo {
    private String manufacturer;
    private String model;
    private String firmwareVersion;
    private String serialNumber;
    private String hardwareId;

    public DeviceInfo(String manufacturer, String model, String firmwareVersion, String serialNumber, String hardwareId) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.firmwareVersion = firmwareVersion;
        this.serialNumber = serialNumber;
        this.hardwareId = hardwareId;
    }

    public String toString() {
        return "DeviceInfo [manufacturer=" + this.manufacturer + ", model=" + this.model + ", firmwareVersion=" + this.firmwareVersion + ", serialNumber=" + this.serialNumber + ", hardwareId=" + this.hardwareId + "]";
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getHardwareId() {
        return this.hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }
}

