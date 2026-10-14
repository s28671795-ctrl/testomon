/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import java.net.URL;

public class HTTPException
extends IOException {
    private static final long serialVersionUID = -6186825695409511895L;
    private int responseCode;
    private String responseMessage;
    private URL url;

    public HTTPException(int responseCode, String responseMessage, URL url2) {
        super("HTTP response '" + responseCode + ": " + responseMessage + "' when communicating with " + url2.toString());
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.url = url2;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public URL getUrl() {
        return this.url;
    }
}

