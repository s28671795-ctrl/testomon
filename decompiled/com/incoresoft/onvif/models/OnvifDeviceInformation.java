/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

public class OnvifDeviceInformation {
    public static final String TAG = OnvifDeviceInformation.class.getSimpleName();
    private String manufacturer;
    private String model;
    private String firmwareVersion;
    private String serialNumber;
    private String hardwareId;

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

    public String toString() {
        return "OnvifDeviceInformation{manufacturer='" + this.manufacturer + "', model='" + this.model + "', firmwareVersion='" + this.firmwareVersion + "', serialNumber='" + this.serialNumber + "', hardwareId='" + this.hardwareId + "'}";
    }
}

