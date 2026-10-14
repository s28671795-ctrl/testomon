/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.responses.OnvifResponse;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public abstract class OnvifParser<T> {
    public static final String TAG = OnvifParser.class.getSimpleName();
    private XmlPullParserFactory xmlFactory;
    private XmlPullParser xpp;
    int eventType;

    OnvifParser() {
        try {
            this.xmlFactory = XmlPullParserFactory.newInstance();
            this.xmlFactory.setNamespaceAware(true);
            this.xpp = this.xmlFactory.newPullParser();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    protected XmlPullParserFactory getXmlFactory() {
        return this.xmlFactory;
    }

    protected void setXmlFactory(XmlPullParserFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    XmlPullParser getXpp() {
        return this.xpp;
    }

    public abstract T parse(OnvifResponse var1);
}

