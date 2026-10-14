/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.net.URI;
import java.net.URL;
import org.apache.cxf.transport.http.URLConnectionInfo;

public class HttpURLConnectionInfo
extends URLConnectionInfo {
    private final String httpRequestMethod;

    public HttpURLConnectionInfo(URL url2, String method2) {
        super(url2);
        this.httpRequestMethod = method2;
    }

    public HttpURLConnectionInfo(URI uri, String method2) {
        super(uri);
        this.httpRequestMethod = method2;
    }

    public String getHttpRequestMethod() {
        return this.httpRequestMethod;
    }
}

