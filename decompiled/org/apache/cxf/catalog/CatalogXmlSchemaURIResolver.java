/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.catalog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.Bus;
import org.apache.cxf.catalog.OASISCatalogManager;
import org.apache.cxf.catalog.OASISCatalogManagerHelper;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.resource.ExtendedURIResolver;
import org.apache.cxf.transport.TransportURIResolver;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.resolver.URIResolver;
import org.xml.sax.InputSource;

public class CatalogXmlSchemaURIResolver
implements URIResolver {
    private ExtendedURIResolver resolver;
    private Bus bus;
    private Map<String, String> resolved = new HashMap<String, String>();

    public CatalogXmlSchemaURIResolver(Bus bus) {
        this.resolver = new TransportURIResolver(bus);
        this.bus = bus;
    }

    public Map<String, String> getResolvedMap() {
        return this.resolved;
    }

    @Override
    public InputSource resolveEntity(String targetNamespace, String schemaLocation, String baseUri) {
        InputSource in;
        String resolvedSchemaLocation = null;
        OASISCatalogManager catalogResolver = OASISCatalogManager.getCatalogManager(this.bus);
        try {
            resolvedSchemaLocation = new OASISCatalogManagerHelper().resolve(catalogResolver, schemaLocation, baseUri);
        }
        catch (Exception e) {
            throw new RuntimeException("Catalog resolution failed", e);
        }
        if (resolvedSchemaLocation == null) {
            in = this.resolver.resolve(schemaLocation, baseUri);
        } else {
            this.resolved.put(schemaLocation, resolvedSchemaLocation);
            in = this.resolver.resolve(resolvedSchemaLocation, baseUri);
        }
        if (in == null) {
            throw new XmlSchemaException("Unable to locate imported document at '" + schemaLocation + "'" + (baseUri == null ? "." : ", relative to '" + baseUri + "'."));
        }
        if (in.getByteStream() != null && !(in.getByteStream() instanceof ByteArrayInputStream)) {
            try {
                ByteArrayInputStream ins = IOUtils.loadIntoBAIS(in.getByteStream());
                in.setByteStream(ins);
            }
            catch (IOException e) {
                throw new XmlSchemaException("Unable to load imported document at '" + schemaLocation + "'" + (baseUri == null ? "." : ", relative to '" + baseUri + "'."), e);
            }
        }
        return in;
    }
}

