/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.upnp;

public class UPnPDeviceInformation {
    public static final String TAG = UPnPDeviceInformation.class.getSimpleName();
    private String deviceType;
    private String friendlyName;
    private String serialNumber;
    private String modelDescription;
    private String modelName;
    private String modelNumber;
    private String modelURL;
    private String manufacturer;
    private String manufacturerURL;
    private String UDN;
    private String presentationURL;
    private String urlBase;

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getPresentationURL() {
        return this.presentationURL;
    }

    public void setPresentationURL(String presentationURL) {
        this.presentationURL = presentationURL;
    }

    public String getModelDescription() {
        return this.modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return this.modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getModelURL() {
        return this.modelURL;
    }

    public void setModelURL(String modelURL) {
        this.modelURL = modelURL;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerURL() {
        return this.manufacturerURL;
    }

    public void setManufacturerURL(String manufacturerURL) {
        this.manufacturerURL = manufacturerURL;
    }

    public String getUDN() {
        return this.UDN;
    }

    public void setUDN(String UDN) {
        this.UDN = UDN;
    }

    public String getUrlBase() {
        return this.urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public String toString() {
        return "UPnPDeviceInformation{deviceType='" + this.deviceType + "', friendlyName='" + this.friendlyName + "', serialNumber='" + this.serialNumber + "', modelDescription='" + this.modelDescription + "', modelName='" + this.modelName + "', modelNumber='" + this.modelNumber + "', modelURL='" + this.modelURL + "', manufacturer='" + this.manufacturer + "', manufacturerURL='" + this.manufacturerURL + "', UDN='" + this.UDN + "', presentationURL='" + this.presentationURL + "', urlBase='" + this.urlBase + "'}";
    }
}

