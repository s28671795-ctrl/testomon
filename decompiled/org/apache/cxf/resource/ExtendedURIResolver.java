/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.cxf.resource.URIResolver;
import org.xml.sax.InputSource;

public class ExtendedURIResolver {
    protected URIResolver currentResolver;
    protected String lastestImportUri;
    protected final Deque<InputStream> resourceOpened = new ArrayDeque<InputStream>();

    public ExtendedURIResolver() {
        this.currentResolver = new URIResolver();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public InputSource resolve(String curUri, String baseUri) {
        try {
            this.currentResolver.resolve(baseUri, curUri, this.getClass());
            if (this.currentResolver.isResolved()) {
                if (this.currentResolver.getURI() != null && this.currentResolver.getURI().isAbsolute()) {
                    curUri = this.currentResolver.getURI().toString();
                }
                if (this.currentResolver.isFile()) {
                    curUri = this.currentResolver.getFile().getAbsoluteFile().toURI().toString();
                }
                InputStream in = this.currentResolver.getInputStream();
                this.resourceOpened.add(in);
                InputSource source2 = new InputSource(in);
                source2.setSystemId(curUri);
                source2.setPublicId(curUri);
                InputSource inputSource = source2;
                return inputSource;
            }
        }
        catch (IOException iOException) {
        }
        finally {
            this.lastestImportUri = curUri;
        }
        return null;
    }

    public void close() {
        while (!this.resourceOpened.isEmpty()) {
            try {
                InputStream in = this.resourceOpened.pop();
                if (in == null) continue;
                in.close();
            }
            catch (IOException iOException) {}
        }
    }

    public String getLatestImportURI() {
        return this.getURI();
    }

    public String getURI() {
        if (this.currentResolver.getURI() != null) {
            return this.currentResolver.getURI().toString();
        }
        return this.lastestImportUri;
    }
}

