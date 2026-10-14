/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.OnvifUtils;
import com.incoresoft.onvif.models.OnvifServices;
import com.incoresoft.onvif.models.OnvifType;
import com.incoresoft.onvif.parsers.OnvifParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParserException;

public class GetServicesParser
extends OnvifParser<OnvifServices> {
    @Override
    public OnvifServices parse(OnvifResponse response) {
        OnvifServices path = new OnvifServices();
        try {
            this.getXpp().setInput(new StringReader(response.getXml()));
            this.eventType = this.getXpp().getEventType();
            while (this.eventType != 1) {
                if (this.eventType == 2 && this.getXpp().getName().equals("Namespace")) {
                    String uri;
                    this.getXpp().next();
                    String currentNamespace = this.getXpp().getText();
                    if (currentNamespace.equals(OnvifType.GET_DEVICE_INFORMATION.namespace)) {
                        uri = OnvifUtils.retrieveXAddr(this.getXpp());
                        path.setDeviceInformationPath(OnvifUtils.getPathFromURL(uri));
                    } else if (currentNamespace.equals(OnvifType.GET_MEDIA_PROFILES.namespace) || currentNamespace.equals(OnvifType.GET_STREAM_URI.namespace)) {
                        uri = OnvifUtils.retrieveXAddr(this.getXpp());
                        path.setProfilesPath(OnvifUtils.getPathFromURL(uri));
                        path.setStreamURIPath(OnvifUtils.getPathFromURL(uri));
                    }
                }
                this.eventType = this.getXpp().next();
            }
        }
        catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return path;
    }
}

