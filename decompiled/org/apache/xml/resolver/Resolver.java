/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Vector;
import javax.xml.parsers.SAXParserFactory;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogEntry;
import org.apache.xml.resolver.CatalogException;
import org.apache.xml.resolver.readers.SAXCatalogReader;
import org.apache.xml.resolver.readers.TR9401CatalogReader;

public class Resolver
extends Catalog {
    public static final int URISUFFIX = CatalogEntry.addEntryType("URISUFFIX", 2);
    public static final int SYSTEMSUFFIX = CatalogEntry.addEntryType("SYSTEMSUFFIX", 2);
    public static final int RESOLVER = CatalogEntry.addEntryType("RESOLVER", 1);
    public static final int SYSTEMREVERSE = CatalogEntry.addEntryType("SYSTEMREVERSE", 1);

    public void setupReaders() {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(false);
        SAXCatalogReader saxReader = new SAXCatalogReader(spf);
        saxReader.setCatalogParser(null, "XCatalog", "org.apache.xml.resolver.readers.XCatalogReader");
        saxReader.setCatalogParser("urn:oasis:names:tc:entity:xmlns:xml:catalog", "catalog", "org.apache.xml.resolver.readers.ExtendedXMLCatalogReader");
        this.addReader("application/xml", saxReader);
        TR9401CatalogReader textReader = new TR9401CatalogReader();
        this.addReader("text/plain", textReader);
    }

    public void addEntry(CatalogEntry entry) {
        int type = entry.getEntryType();
        if (type == URISUFFIX) {
            String suffix = this.normalizeURI(entry.getEntryArg(0));
            String fsi = this.makeAbsolute(this.normalizeURI(entry.getEntryArg(1)));
            entry.setEntryArg(1, fsi);
            this.catalogManager.debug.message(4, "URISUFFIX", suffix, fsi);
        } else if (type == SYSTEMSUFFIX) {
            String suffix = this.normalizeURI(entry.getEntryArg(0));
            String fsi = this.makeAbsolute(this.normalizeURI(entry.getEntryArg(1)));
            entry.setEntryArg(1, fsi);
            this.catalogManager.debug.message(4, "SYSTEMSUFFIX", suffix, fsi);
        }
        super.addEntry(entry);
    }

    public String resolveURI(String uri) throws MalformedURLException, IOException {
        String resolved = super.resolveURI(uri);
        if (resolved != null) {
            return resolved;
        }
        Enumeration en = this.catalogEntries.elements();
        while (en.hasMoreElements()) {
            CatalogEntry e = (CatalogEntry)en.nextElement();
            if (e.getEntryType() == RESOLVER) {
                resolved = this.resolveExternalSystem(uri, e.getEntryArg(0));
                if (resolved == null) continue;
                return resolved;
            }
            if (e.getEntryType() != URISUFFIX) continue;
            String suffix = e.getEntryArg(0);
            String result = e.getEntryArg(1);
            if (suffix.length() > uri.length() || !uri.substring(uri.length() - suffix.length()).equals(suffix)) continue;
            return result;
        }
        return this.resolveSubordinateCatalogs(Catalog.URI, null, null, uri);
    }

    public String resolveSystem(String systemId) throws MalformedURLException, IOException {
        String resolved = super.resolveSystem(systemId);
        if (resolved != null) {
            return resolved;
        }
        Enumeration en = this.catalogEntries.elements();
        while (en.hasMoreElements()) {
            CatalogEntry e = (CatalogEntry)en.nextElement();
            if (e.getEntryType() == RESOLVER) {
                resolved = this.resolveExternalSystem(systemId, e.getEntryArg(0));
                if (resolved == null) continue;
                return resolved;
            }
            if (e.getEntryType() != SYSTEMSUFFIX) continue;
            String suffix = e.getEntryArg(0);
            String result = e.getEntryArg(1);
            if (suffix.length() > systemId.length() || !systemId.substring(systemId.length() - suffix.length()).equals(suffix)) continue;
            return result;
        }
        return this.resolveSubordinateCatalogs(Catalog.SYSTEM, null, null, systemId);
    }

    public String resolvePublic(String publicId, String systemId) throws MalformedURLException, IOException {
        String resolved = super.resolvePublic(publicId, systemId);
        if (resolved != null) {
            return resolved;
        }
        Enumeration en = this.catalogEntries.elements();
        while (en.hasMoreElements()) {
            CatalogEntry e = (CatalogEntry)en.nextElement();
            if (e.getEntryType() != RESOLVER) continue;
            if (systemId != null && (resolved = this.resolveExternalSystem(systemId, e.getEntryArg(0))) != null) {
                return resolved;
            }
            resolved = this.resolveExternalPublic(publicId, e.getEntryArg(0));
            if (resolved == null) continue;
            return resolved;
        }
        return this.resolveSubordinateCatalogs(Catalog.PUBLIC, null, publicId, systemId);
    }

    protected String resolveExternalSystem(String systemId, String resolver2) throws MalformedURLException, IOException {
        Resolver r = this.queryResolver(resolver2, "i2l", systemId, null);
        if (r != null) {
            return r.resolveSystem(systemId);
        }
        return null;
    }

    protected String resolveExternalPublic(String publicId, String resolver2) throws MalformedURLException, IOException {
        Resolver r = this.queryResolver(resolver2, "fpi2l", publicId, null);
        if (r != null) {
            return r.resolvePublic(publicId, null);
        }
        return null;
    }

    protected Resolver queryResolver(String resolver2, String command, String arg1, String arg2) {
        Object iStream = null;
        String RFC2483 = resolver2 + "?command=" + command + "&format=tr9401&uri=" + arg1 + "&uri2=" + arg2;
        Object line = null;
        try {
            URL url2 = new URL(RFC2483);
            URLConnection urlCon = url2.openConnection();
            urlCon.setUseCaches(false);
            Resolver r = (Resolver)this.newCatalog();
            String cType = urlCon.getContentType();
            if (cType.indexOf(";") > 0) {
                cType = cType.substring(0, cType.indexOf(";"));
            }
            r.parseCatalog(cType, urlCon.getInputStream());
            return r;
        }
        catch (CatalogException cex) {
            if (cex.getExceptionType() == 6) {
                this.catalogManager.debug.message(1, "Unparseable catalog: " + RFC2483);
            } else if (cex.getExceptionType() == 5) {
                this.catalogManager.debug.message(1, "Unknown catalog format: " + RFC2483);
            }
            return null;
        }
        catch (MalformedURLException mue) {
            this.catalogManager.debug.message(1, "Malformed resolver URL: " + RFC2483);
            return null;
        }
        catch (IOException ie) {
            this.catalogManager.debug.message(1, "I/O Exception opening resolver: " + RFC2483);
            return null;
        }
    }

    private Vector appendVector(Vector vec, Vector appvec) {
        if (appvec != null) {
            int count = 0;
            while (count < appvec.size()) {
                vec.addElement(appvec.elementAt(count));
                ++count;
            }
        }
        return vec;
    }

    public Vector resolveAllSystemReverse(String systemId) throws MalformedURLException, IOException {
        Vector resolved = new Vector();
        if (systemId != null) {
            Vector localResolved = this.resolveLocalSystemReverse(systemId);
            resolved = this.appendVector(resolved, localResolved);
        }
        Vector subResolved = this.resolveAllSubordinateCatalogs(SYSTEMREVERSE, null, null, systemId);
        return this.appendVector(resolved, subResolved);
    }

    public String resolveSystemReverse(String systemId) throws MalformedURLException, IOException {
        Vector resolved = this.resolveAllSystemReverse(systemId);
        if (resolved != null && resolved.size() > 0) {
            return (String)resolved.elementAt(0);
        }
        return null;
    }

    public Vector resolveAllSystem(String systemId) throws MalformedURLException, IOException {
        Vector subResolutions;
        Vector resolutions = new Vector();
        if (systemId != null) {
            Vector localResolutions = this.resolveAllLocalSystem(systemId);
            resolutions = this.appendVector(resolutions, localResolutions);
        }
        if ((resolutions = this.appendVector(resolutions, subResolutions = this.resolveAllSubordinateCatalogs(Catalog.SYSTEM, null, null, systemId))).size() > 0) {
            return resolutions;
        }
        return null;
    }

    private Vector resolveAllLocalSystem(String systemId) {
        Vector<String> map = new Vector<String>();
        String osname = System.getProperty("os.name");
        boolean windows = osname.indexOf("Windows") >= 0;
        Enumeration en = this.catalogEntries.elements();
        while (en.hasMoreElements()) {
            CatalogEntry e = (CatalogEntry)en.nextElement();
            if (e.getEntryType() != Catalog.SYSTEM || !e.getEntryArg(0).equals(systemId) && (!windows || !e.getEntryArg(0).equalsIgnoreCase(systemId))) continue;
            map.addElement(e.getEntryArg(1));
        }
        if (map.size() == 0) {
            return null;
        }
        return map;
    }

    private Vector resolveLocalSystemReverse(String systemId) {
        Vector<String> map = new Vector<String>();
        String osname = System.getProperty("os.name");
        boolean windows = osname.indexOf("Windows") >= 0;
        Enumeration en = this.catalogEntries.elements();
        while (en.hasMoreElements()) {
            CatalogEntry e = (CatalogEntry)en.nextElement();
            if (e.getEntryType() != Catalog.SYSTEM || !e.getEntryArg(1).equals(systemId) && (!windows || !e.getEntryArg(1).equalsIgnoreCase(systemId))) continue;
            map.addElement(e.getEntryArg(0));
        }
        if (map.size() == 0) {
            return null;
        }
        return map;
    }

    private synchronized Vector resolveAllSubordinateCatalogs(int entityType, String entityName, String publicId, String systemId) throws MalformedURLException, IOException {
        Vector resolutions = new Vector();
        int catPos = 0;
        while (catPos < this.catalogs.size()) {
            Resolver c = null;
            try {
                c = (Resolver)this.catalogs.elementAt(catPos);
            }
            catch (ClassCastException e) {
                String catfile = (String)this.catalogs.elementAt(catPos);
                c = (Resolver)this.newCatalog();
                try {
                    c.parseCatalog(catfile);
                }
                catch (MalformedURLException mue) {
                    this.catalogManager.debug.message(1, "Malformed Catalog URL", catfile);
                }
                catch (FileNotFoundException fnfe) {
                    this.catalogManager.debug.message(1, "Failed to load catalog, file not found", catfile);
                }
                catch (IOException ioe) {
                    this.catalogManager.debug.message(1, "Failed to load catalog, I/O error", catfile);
                }
                this.catalogs.setElementAt(c, catPos);
            }
            String resolved = null;
            if (entityType == Catalog.DOCTYPE) {
                resolved = c.resolveDoctype(entityName, publicId, systemId);
                if (resolved != null) {
                    resolutions.addElement(resolved);
                    return resolutions;
                }
            } else if (entityType == Catalog.DOCUMENT) {
                resolved = c.resolveDocument();
                if (resolved != null) {
                    resolutions.addElement(resolved);
                    return resolutions;
                }
            } else if (entityType == Catalog.ENTITY) {
                resolved = c.resolveEntity(entityName, publicId, systemId);
                if (resolved != null) {
                    resolutions.addElement(resolved);
                    return resolutions;
                }
            } else if (entityType == Catalog.NOTATION) {
                resolved = c.resolveNotation(entityName, publicId, systemId);
                if (resolved != null) {
                    resolutions.addElement(resolved);
                    return resolutions;
                }
            } else if (entityType == Catalog.PUBLIC) {
                resolved = c.resolvePublic(publicId, systemId);
                if (resolved != null) {
                    resolutions.addElement(resolved);
                    return resolutions;
                }
            } else {
                Vector localResolutions;
                if (entityType == Catalog.SYSTEM) {
                    localResolutions = c.resolveAllSystem(systemId);
                    resolutions = this.appendVector(resolutions, localResolutions);
                    break;
                }
                if (entityType == SYSTEMREVERSE) {
                    localResolutions = c.resolveAllSystemReverse(systemId);
                    resolutions = this.appendVector(resolutions, localResolutions);
                }
            }
            ++catPos;
        }
        if (resolutions != null) {
            return resolutions;
        }
        return null;
    }
}

