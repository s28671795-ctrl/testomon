/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.parsers.OnvifParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParserException;

public class GetMediaStreamParser
extends OnvifParser<String> {
    public static final String TAG = GetMediaStreamParser.class.getSimpleName();
    private static final String KEY_URI = "Uri";

    @Override
    public String parse(OnvifResponse response) {
        String uri = "";
        try {
            this.getXpp().setInput(new StringReader(response.getXml()));
            this.eventType = this.getXpp().getEventType();
            while (this.eventType != 1) {
                if (this.eventType == 2 && this.getXpp().getName().equals(KEY_URI)) {
                    this.getXpp().next();
                    uri = this.getXpp().getText();
                    break;
                }
                this.eventType = this.getXpp().next();
            }
        }
        catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return uri;
    }
}

