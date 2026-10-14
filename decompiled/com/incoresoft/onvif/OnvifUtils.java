/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class OnvifUtils {
    public static String getPathFromURL(String uri) {
        URL url2;
        try {
            url2 = new URL(uri);
        }
        catch (MalformedURLException e) {
            return "";
        }
        Object result = url2.getPath();
        if (url2.getQuery() != null) {
            result = (String)result + url2.getQuery();
        }
        return result;
    }

    public static String retrieveXAddr(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String result = "";
        int eventType = xpp.getEventType();
        while (eventType != 1 || eventType == 3 && xpp.getName().equals("Service")) {
            if (eventType == 2 && xpp.getName().equals("XAddr")) {
                xpp.next();
                result = xpp.getText();
                break;
            }
            eventType = xpp.next();
        }
        return result;
    }

    public static String retrieveXAddrs(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String result = "";
        int eventType = xpp.getEventType();
        while (eventType != 1) {
            if (eventType == 2 && xpp.getName().equals("XAddrs")) {
                xpp.next();
                result = xpp.getText();
                break;
            }
            eventType = xpp.next();
        }
        return result;
    }
}

