/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.helpers.FileURL;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class CatalogResolver
implements EntityResolver,
URIResolver {
    public boolean namespaceAware = true;
    public boolean validating = false;
    private Catalog catalog = null;
    private CatalogManager catalogManager = CatalogManager.getStaticManager();

    public CatalogResolver() {
        this.initializeCatalogs(false);
    }

    public CatalogResolver(boolean privateCatalog) {
        this.initializeCatalogs(privateCatalog);
    }

    public CatalogResolver(CatalogManager manager) {
        this.catalogManager = manager;
        this.initializeCatalogs(!this.catalogManager.getUseStaticCatalog());
    }

    private void initializeCatalogs(boolean privateCatalog) {
        this.catalog = this.catalogManager.getCatalog();
    }

    public Catalog getCatalog() {
        return this.catalog;
    }

    public String getResolvedEntity(String publicId, String systemId) {
        String resolved = null;
        if (this.catalog == null) {
            this.catalogManager.debug.message(1, "Catalog resolution attempted with null catalog; ignored");
            return null;
        }
        if (systemId != null) {
            try {
                resolved = this.catalog.resolveSystem(systemId);
            }
            catch (MalformedURLException me) {
                this.catalogManager.debug.message(1, "Malformed URL exception trying to resolve", publicId);
                resolved = null;
            }
            catch (IOException ie) {
                this.catalogManager.debug.message(1, "I/O exception trying to resolve", publicId);
                resolved = null;
            }
        }
        if (resolved == null) {
            if (publicId != null) {
                try {
                    resolved = this.catalog.resolvePublic(publicId, systemId);
                }
                catch (MalformedURLException me) {
                    this.catalogManager.debug.message(1, "Malformed URL exception trying to resolve", publicId);
                }
                catch (IOException ie) {
                    this.catalogManager.debug.message(1, "I/O exception trying to resolve", publicId);
                }
            }
            if (resolved != null) {
                this.catalogManager.debug.message(2, "Resolved public", publicId, resolved);
            }
        } else {
            this.catalogManager.debug.message(2, "Resolved system", systemId, resolved);
        }
        return resolved;
    }

    public InputSource resolveEntity(String publicId, String systemId) {
        String resolved = this.getResolvedEntity(publicId, systemId);
        if (resolved != null) {
            try {
                InputSource iSource = new InputSource(resolved);
                iSource.setPublicId(publicId);
                URL url2 = new URL(resolved);
                InputStream iStream = url2.openStream();
                iSource.setByteStream(iStream);
                return iSource;
            }
            catch (Exception e) {
                this.catalogManager.debug.message(1, "Failed to create InputSource (" + e.toString() + ")", resolved);
                return null;
            }
        }
        return null;
    }

    public Source resolve(String href, String base) throws TransformerException {
        String uri = href;
        String fragment = null;
        int hashPos = href.indexOf("#");
        if (hashPos >= 0) {
            uri = href.substring(0, hashPos);
            fragment = href.substring(hashPos + 1);
        }
        String result = null;
        try {
            result = this.catalog.resolveURI(href);
        }
        catch (Exception e) {
            // empty catch block
        }
        if (result == null) {
            try {
                URL url2 = null;
                if (base == null) {
                    url2 = new URL(uri);
                    result = url2.toString();
                } else {
                    URL baseURL = new URL(base);
                    url2 = href.length() == 0 ? baseURL : new URL(baseURL, uri);
                    result = url2.toString();
                }
            }
            catch (MalformedURLException mue) {
                String absBase = this.makeAbsolute(base);
                if (!absBase.equals(base)) {
                    return this.resolve(href, absBase);
                }
                throw new TransformerException("Malformed URL " + href + "(base " + base + ")", mue);
            }
        }
        this.catalogManager.debug.message(2, "Resolved URI", href, result);
        SAXSource source2 = new SAXSource();
        source2.setInputSource(new InputSource(result));
        this.setEntityResolver(source2);
        return source2;
    }

    private void setEntityResolver(SAXSource source2) throws TransformerException {
        XMLReader reader = source2.getXMLReader();
        if (reader == null) {
            SAXParserFactory spFactory = SAXParserFactory.newInstance();
            spFactory.setNamespaceAware(true);
            try {
                reader = spFactory.newSAXParser().getXMLReader();
            }
            catch (ParserConfigurationException ex) {
                throw new TransformerException(ex);
            }
            catch (SAXException ex) {
                throw new TransformerException(ex);
            }
        }
        reader.setEntityResolver(this);
        source2.setXMLReader(reader);
    }

    private String makeAbsolute(String uri) {
        if (uri == null) {
            uri = "";
        }
        try {
            URL url2 = new URL(uri);
            return url2.toString();
        }
        catch (MalformedURLException mue) {
            try {
                URL fileURL = FileURL.makeURL(uri);
                return fileURL.toString();
            }
            catch (MalformedURLException mue2) {
                return uri;
            }
        }
    }
}

