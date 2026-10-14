/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.codehaus.plexus.util.io.InputStreamFacade;

public class URLInputStreamFacade
implements InputStreamFacade {
    private final URL url;

    public URLInputStreamFacade(URL url2) {
        this.url = url2;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.url.openStream();
    }
}

