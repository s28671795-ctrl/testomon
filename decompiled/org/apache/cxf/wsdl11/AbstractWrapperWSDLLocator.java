/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.wsdl.xml.WSDLLocator;
import org.xml.sax.InputSource;

public abstract class AbstractWrapperWSDLLocator
implements WSDLLocator {
    private static final List<String> ALLOWED_SCHEMES;
    protected WSDLLocator parent;
    String wsdlUrl;
    InputSource last;
    String baseUri;
    String lastImport;
    boolean fromParent;

    public AbstractWrapperWSDLLocator(String wsdlUrl, WSDLLocator parent) {
        this.wsdlUrl = wsdlUrl;
        this.parent = parent;
    }

    @Override
    public void close() {
        if (!this.fromParent) {
            try {
                if (this.last.getByteStream() != null) {
                    this.last.getByteStream().close();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.parent.close();
    }

    public abstract InputSource getInputSource();

    public abstract InputSource getInputSource(String var1, String var2);

    @Override
    public InputSource getBaseInputSource() {
        InputSource is = this.parent.getBaseInputSource();
        this.fromParent = true;
        if (is == null) {
            is = this.getInputSource();
            this.fromParent = false;
        } else {
            this.baseUri = is.getSystemId();
        }
        this.last = is;
        return is;
    }

    @Override
    public String getBaseURI() {
        if (this.last == null) {
            this.getBaseInputSource();
            try {
                if (this.last.getByteStream() != null) {
                    this.last.getByteStream().close();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return this.baseUri;
    }

    @Override
    public InputSource getImportInputSource(String parentLocation, String importLocation) {
        try {
            URI url2 = new URI(importLocation);
            if (url2.getScheme() != null && !ALLOWED_SCHEMES.contains(url2.getScheme())) {
                throw new IllegalArgumentException("The " + url2.getScheme() + " URI scheme is not allowed");
            }
        }
        catch (URISyntaxException url2) {
            // empty catch block
        }
        InputSource src = this.parent.getImportInputSource(parentLocation, importLocation);
        this.lastImport = null;
        if ((src == null || src.getByteStream() == null && src.getCharacterStream() == null) && (src = this.getInputSource(parentLocation, importLocation)) != null) {
            this.lastImport = src.getSystemId();
        }
        return src;
    }

    @Override
    public String getLatestImportURI() {
        if (this.lastImport != null) {
            return this.lastImport;
        }
        return this.parent.getLatestImportURI();
    }

    static {
        List<String> schemes = Arrays.asList("file", "http", "https", "classpath", "jar");
        ALLOWED_SCHEMES = Collections.unmodifiableList(schemes);
    }
}

