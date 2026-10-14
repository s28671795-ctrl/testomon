/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.models.OnvifMediaProfile;
import com.incoresoft.onvif.parsers.OnvifParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public class GetMediaProfilesParser
extends OnvifParser<List<OnvifMediaProfile>> {
    public static final String TAG = GetMediaProfilesParser.class.getSimpleName();
    private static final String KEY_PROFILES = "Profiles";
    private static final String ATTR_TOKEN = "token";
    private static final String ATTR_NAME = "Name";

    @Override
    public List<OnvifMediaProfile> parse(OnvifResponse response) {
        ArrayList<OnvifMediaProfile> profiles = new ArrayList<OnvifMediaProfile>();
        try {
            this.getXpp().setInput(new StringReader(response.getXml()));
            this.eventType = this.getXpp().getEventType();
            while (this.eventType != 1) {
                if (this.eventType == 2 && this.getXpp().getName().equals(KEY_PROFILES)) {
                    String token = this.getXpp().getAttributeValue(null, ATTR_TOKEN);
                    this.getXpp().nextTag();
                    if (this.getXpp().getName().equals(ATTR_NAME)) {
                        this.getXpp().next();
                        String name = this.getXpp().getText();
                        profiles.add(new OnvifMediaProfile(name, token));
                    }
                }
                this.eventType = this.getXpp().next();
            }
        }
        catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}

