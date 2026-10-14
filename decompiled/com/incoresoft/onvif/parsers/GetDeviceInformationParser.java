/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.models.OnvifDeviceInformation;
import com.incoresoft.onvif.parsers.OnvifParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParserException;

public class GetDeviceInformationParser
extends OnvifParser<OnvifDeviceInformation> {
    public static final String TAG = GetDeviceInformationParser.class.getSimpleName();
    private static final String KEY_MANUFACTURER = "Manufacturer";
    private static final String KEY_MODEL = "Model";
    private static final String KEY_FIRMWARE_VERSION = "FirmwareVersion";
    private static final String KEY_SERIAL_NUMBER = "SerialNumber";
    private static final String KEY_HARDWARE_ID = "HardwareId";

    @Override
    public OnvifDeviceInformation parse(OnvifResponse response) {
        OnvifDeviceInformation deviceInformation = new OnvifDeviceInformation();
        try {
            this.getXpp().setInput(new StringReader(response.getXml()));
            this.eventType = this.getXpp().getEventType();
            while (this.eventType != 1) {
                if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MANUFACTURER)) {
                    this.getXpp().next();
                    deviceInformation.setManufacturer(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_MODEL)) {
                    this.getXpp().next();
                    deviceInformation.setModel(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_FIRMWARE_VERSION)) {
                    this.getXpp().next();
                    deviceInformation.setFirmwareVersion(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_SERIAL_NUMBER)) {
                    this.getXpp().next();
                    deviceInformation.setSerialNumber(this.getXpp().getText());
                } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_HARDWARE_ID)) {
                    this.getXpp().next();
                    deviceInformation.setHardwareId(this.getXpp().getText());
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

