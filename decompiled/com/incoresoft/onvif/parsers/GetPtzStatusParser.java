/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.models.PtzStatus;
import com.incoresoft.onvif.parsers.OnvifParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParserException;

public class GetPtzStatusParser
extends OnvifParser<PtzStatus> {
    public static final String TAG = GetPtzStatusParser.class.getSimpleName();
    private static final String KEY_POSITION = "Position";
    private static final String KEY_PANTILT = "PanTilt";
    private static final String KEY_ZOOM = "Zoom";
    private static final String ATTR_PAN = "x";
    private static final String ATTR_TILT = "y";
    private static final String ATTR_ZOOM = "x";

    @Override
    public PtzStatus parse(OnvifResponse response) {
        double pan = 0.0;
        double tilt = 0.0;
        double zoom = 0.0;
        try {
            this.getXpp().setInput(new StringReader(response.getXml()));
            this.eventType = this.getXpp().getEventType();
            while (this.eventType != 1) {
                if (this.eventType == 2 && this.getXpp().getName().equals(KEY_POSITION)) {
                    while (this.eventType != 3 || !this.getXpp().getName().equals(KEY_POSITION)) {
                        try {
                            String x;
                            if (this.eventType == 2 && this.getXpp().getName().equals(KEY_PANTILT)) {
                                x = this.getXpp().getAttributeValue(null, "x");
                                String y = this.getXpp().getAttributeValue(null, ATTR_TILT);
                                pan = Double.parseDouble(x);
                                tilt = Double.parseDouble(y);
                            } else if (this.eventType == 2 && this.getXpp().getName().equals(KEY_ZOOM)) {
                                x = this.getXpp().getAttributeValue(null, "x");
                                zoom = Double.parseDouble(x);
                            }
                        }
                        catch (Exception e) {
                            throw new RuntimeException("failed to parse PTZ values in position: " + e.getMessage());
                        }
                        this.eventType = this.getXpp().next();
                    }
                }
                this.eventType = this.getXpp().next();
            }
        }
        catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return new PtzStatus(pan, tilt, zoom);
    }
}

