/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLConnectionInfo {
    protected final URI theURI;

    public URLConnectionInfo(URL url2) {
        URI u = null;
        try {
            u = url2.toURI();
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        this.theURI = u;
    }

    public URLConnectionInfo(URI uri) {
        this.theURI = uri;
    }

    public URI getURI() {
        return this.theURI;
    }
}

