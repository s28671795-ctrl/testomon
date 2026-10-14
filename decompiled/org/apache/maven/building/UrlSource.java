/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import org.apache.maven.building.Source;

public class UrlSource
implements Source {
    private URL url;

    public UrlSource(URL url2) {
        this.url = Objects.requireNonNull(url2, "url cannot be null");
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.url.openStream();
    }

    @Override
    public String getLocation() {
        return this.url.toString();
    }

    public URL getUrl() {
        return this.url;
    }

    public String toString() {
        return this.getLocation();
    }
}

