/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.resolver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.apache.ws.commons.schema.resolver.CollectionURIResolver;
import org.xml.sax.InputSource;

public class DefaultURIResolver
implements CollectionURIResolver {
    private String collectionBaseURI;

    @Override
    public InputSource resolveEntity(String namespace, String schemaLocation, String baseUri) {
        if (baseUri != null) {
            try {
                File baseFile = null;
                try {
                    URI uri = new URI(baseUri);
                    baseFile = new File(uri);
                    if (!baseFile.exists()) {
                        baseFile = new File(baseUri);
                    }
                }
                catch (Throwable ex) {
                    baseFile = new File(baseUri);
                }
                if (baseFile.exists()) {
                    baseUri = baseFile.toURI().toString();
                } else if (this.collectionBaseURI != null && (baseFile = new File(this.collectionBaseURI)).exists()) {
                    baseUri = baseFile.toURI().toString();
                }
                String ref = new URL(new URL(baseUri), schemaLocation).toString();
                return new InputSource(ref);
            }
            catch (MalformedURLException e1) {
                throw new RuntimeException(e1);
            }
        }
        return new InputSource(schemaLocation);
    }

    protected boolean isAbsolute(String uri) {
        return uri.startsWith("http://") || uri.startsWith("https://") || uri.startsWith("urn:");
    }

    protected URL getURL(URL contextURL, String spec) throws IOException {
        URL url2;
        String path = spec.replace('\\', '/');
        try {
            url2 = new URL(contextURL, path);
            if (contextURL != null && url2.getProtocol().equals("file") && contextURL.getProtocol().equals("file")) {
                url2 = this.getFileURL(contextURL, path);
            }
        }
        catch (MalformedURLException me) {
            url2 = this.getFileURL(contextURL, path);
        }
        return url2;
    }

    protected URL getFileURL(URL contextURL, String path) throws IOException {
        if (contextURL != null) {
            String contextFileName = contextURL.getFile();
            URL parent = null;
            File contextFile = new File(contextFileName);
            File parentFile = contextFile.isDirectory() ? contextFile : contextFile.getParentFile();
            if (parentFile != null) {
                parent = parentFile.toURI().toURL();
            }
            if (parent != null) {
                return new URL(parent, path);
            }
        }
        return new URL("file", "", path);
    }

    @Override
    public String getCollectionBaseURI() {
        return this.collectionBaseURI;
    }

    @Override
    public void setCollectionBaseURI(String collectionBaseURI) {
        this.collectionBaseURI = collectionBaseURI;
    }
}

