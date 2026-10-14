/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.parsers.OnvifParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import com.incoresoft.onvif.upnp.UPnPDeviceInformation;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParserException;

public class UPnPParser
extends OnvifParser<UPnPDeviceInformation> {
    public static final String TAG = UPnPParser.class.getSimpleName();
    private static final String KEY_DEVICE_TYPE = "deviceType";
    private static final String KEY_FRIENDLY_NAME = "friendlyName";
    private static final String KEY_MANUFACTURER = "manufacturer";
    private static final String KEY_MANUFACTURER_URL = "manufacturerURL";
    private static final String KEY_MODEL_DESCRIPTION = "modelDescription";
    private static final String KEY_MODEL_NAME = "modelName";
    private static final String KEY_MODEL_NUMBER = "modelNumber";
    private static final String KEY_MODEL_URL = "modelURL";
    private static final String KEY_SERIAL_NUMBER = "serialNumber";
    private static final String KEY_UDN = "UDN";
    private static final String KEY_PRESENTATION_URL = "presentationURL";
    private static final String KEY_URL_BASE = "URLBase";

    @Override
    public UPnPDeviceInformation parse(OnvifResponse response) {
        UPnPDeviceInformation deviceInformation = new UPnPDeviceInformation();
        try {
            this.getXpp().setInput(new StringReader(response.getXml()));
            this.eventType = this.getXpp().getEventType();
            while (this.eventType != 1) {
                if (this.eventType == 2 && this.getXpp().getName().equals(KEY_DEVICE_TYPE)) {
                    this.getXpp().next();
                    deviceInformation.setDeviceType(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_FRIENDLY_NAME)) {
                    this.getXpp().next();
                    deviceInformation.setFriendlyName(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MANUFACTURER)) {
                    this.getXpp().next();
                    deviceInformation.setManufacturer(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MANUFACTURER_URL)) {
                    this.getXpp().next();
                    deviceInformation.setManufacturerURL(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MODEL_DESCRIPTION)) {
                    this.getXpp().next();
                    deviceInformation.setModelDescription(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MODEL_NAME)) {
                    this.getXpp().next();
                    deviceInformation.setModelName(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MODEL_NUMBER)) {
                    this.getXpp().next();
                    deviceInformation.setModelNumber(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MODEL_URL)) {
                    this.getXpp().next();
                    deviceInformation.setModelURL(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_SERIAL_NUMBER)) {
                    this.getXpp().next();
                    deviceInformation.setSerialNumber(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_UDN)) {
                    this.getXpp().next();
                    deviceInformation.setUDN(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_PRESENTATION_URL)) {
                    this.getXpp().next();
                    deviceInformation.setPresentationURL(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_URL_BASE)) {
                    this.getXpp().next();
                    deviceInformation.setUrlBase(this.getXpp().getText());
                }
                this.eventType = this.getXpp().next();
            }
        }
        catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return deviceInformation;
    }
}

