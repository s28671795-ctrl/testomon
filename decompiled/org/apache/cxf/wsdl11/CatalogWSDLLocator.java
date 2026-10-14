/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.io.IOException;
import javax.wsdl.xml.WSDLLocator;
import org.apache.cxf.Bus;
import org.apache.cxf.catalog.OASISCatalogManager;
import org.apache.cxf.catalog.OASISCatalogManagerHelper;
import org.apache.cxf.resource.ExtendedURIResolver;
import org.apache.cxf.transport.TransportURIResolver;
import org.xml.sax.InputSource;

public class CatalogWSDLLocator
implements WSDLLocator {
    private String wsdlUrl;
    private ExtendedURIResolver resolver;
    private OASISCatalogManager manager;
    private String baseUri;

    public CatalogWSDLLocator(String wsdlUrl) {
        this.baseUri = wsdlUrl;
        this.resolver = new ExtendedURIResolver();
    }

    public CatalogWSDLLocator(String wsdlUrl, OASISCatalogManager catalogManager) {
        this.baseUri = wsdlUrl;
        this.manager = catalogManager;
        this.resolver = new ExtendedURIResolver();
    }

    public CatalogWSDLLocator(String wsdlUrl, Bus b) {
        this.baseUri = wsdlUrl;
        this.manager = OASISCatalogManager.getCatalogManager(b);
        this.resolver = new TransportURIResolver(b);
    }

    @Override
    public InputSource getBaseInputSource() {
        InputSource result = null;
        if (this.manager != null) {
            try {
                String s2 = this.manager.resolveSystem(this.baseUri);
                if (s2 != null) {
                    result = this.resolver.resolve(s2, null);
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (result == null) {
            result = this.resolver.resolve(this.baseUri, null);
        }
        if (this.wsdlUrl == null && result != null) {
            this.wsdlUrl = result.getSystemId();
        }
        this.baseUri = this.resolver.getURI();
        return result;
    }

    @Override
    public String getBaseURI() {
        InputSource is;
        if (this.wsdlUrl == null && (is = this.getBaseInputSource()).getByteStream() != null) {
            try {
                is.getByteStream().close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return this.wsdlUrl;
    }

    @Override
    public String getLatestImportURI() {
        return this.resolver.getLatestImportURI();
    }

    @Override
    public InputSource getImportInputSource(String parent, String importLocation) {
        String resolvedImportLocation = null;
        try {
            resolvedImportLocation = new OASISCatalogManagerHelper().resolve(this.manager, importLocation, parent);
        }
        catch (IOException e) {
            throw new RuntimeException("Catalog resolution failed", e);
        }
        InputSource in = null;
        in = resolvedImportLocation == null ? this.resolver.resolve(importLocation, parent) : this.resolver.resolve(resolvedImportLocation, null);
        if (in == null) {
            in = new InputSource();
        }
        return in;
    }

    @Override
    public void close() {
        this.resolver.close();
    }
}

