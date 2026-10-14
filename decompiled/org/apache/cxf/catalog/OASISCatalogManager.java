/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.catalog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.resource.URIResolver;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.tools.CatalogResolver;
import org.xml.sax.EntityResolver;

@NoJSR250Annotations(unlessNull={"bus"})
public class OASISCatalogManager {
    public static final String DEFAULT_CATALOG_NAME = "META-INF/jax-ws-catalog.xml";
    public static final String CATALOG_DEBUG_KEY = "OASISCatalogManager.catalog.debug.level";
    private static final Logger LOG = LogUtils.getL7dLogger(OASISCatalogManager.class);
    private static final String DEBUG_LEVEL = SystemPropertyAction.getPropertyOrNull("OASISCatalogManager.catalog.debug.level");
    private EntityResolver resolver;
    private Object catalog;
    private Set<String> loadedCatalogs = new CopyOnWriteArraySet<String>();
    private Bus bus;

    public OASISCatalogManager() {
        this.resolver = OASISCatalogManager.getResolver();
        this.catalog = OASISCatalogManager.getCatalog(this.resolver);
    }

    public OASISCatalogManager(Bus b) {
        this.bus = b;
        this.resolver = OASISCatalogManager.getResolver();
        this.catalog = OASISCatalogManager.getCatalog(this.resolver);
        this.loadContextCatalogs(DEFAULT_CATALOG_NAME);
    }

    public boolean hasCatalogs() {
        return !this.loadedCatalogs.isEmpty();
    }

    private static Object getCatalog(EntityResolver resolver2) {
        try {
            return ((CatalogResolver)resolver2).getCatalog();
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static EntityResolver getResolver() {
        try {
            CatalogManager catalogManager = new CatalogManager();
            if (DEBUG_LEVEL != null) {
                catalogManager.debug.setDebug(Integer.parseInt(DEBUG_LEVEL));
            }
            catalogManager.setUseStaticCatalog(false);
            catalogManager.setIgnoreMissingProperties(true);
            return new CatalogResolver(catalogManager){

                @Override
                public String getResolvedEntity(String publicId, String systemId) {
                    String s2 = super.getResolvedEntity(publicId, systemId);
                    if (s2 != null && s2.startsWith("classpath:")) {
                        try {
                            URIResolver r = new URIResolver(s2);
                            if (r.isResolved()) {
                                r.getInputStream().close();
                                return r.getURL().toExternalForm();
                            }
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                    }
                    return s2;
                }
            };
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public Bus getBus() {
        return this.bus;
    }

    @Resource
    public void setBus(Bus bus) {
        this.bus = bus;
        if (null != bus) {
            bus.setExtension(this, OASISCatalogManager.class);
        }
        this.loadContextCatalogs();
    }

    public void loadContextCatalogs() {
        this.loadContextCatalogs(DEFAULT_CATALOG_NAME);
    }

    public final void loadContextCatalogs(String name) {
        try {
            this.loadCatalogs(OASISCatalogManager.getContextClassLoader(), name);
        }
        catch (IOException e) {
            LOG.log(Level.WARNING, "Error loading " + name + " catalog files", e);
        }
    }

    public final void loadCatalogs(ClassLoader classLoader, String name) throws IOException {
        if (classLoader == null) {
            return;
        }
        Enumeration<URL> catalogs = classLoader.getResources(name);
        while (catalogs.hasMoreElements()) {
            final URL catalogURL = catalogs.nextElement();
            if (this.catalog == null) {
                LOG.log(Level.WARNING, "Catalog found at {0} but no org.apache.xml.resolver.CatalogManager was found.  Check the classpatch for an xmlresolver jar.", catalogURL.toString());
                continue;
            }
            if (this.loadedCatalogs.contains(catalogURL.toString())) continue;
            SecurityManager sm = System.getSecurityManager();
            if (sm == null) {
                ((Catalog)this.catalog).parseCatalog(catalogURL);
            } else {
                try {
                    AccessController.doPrivileged(new PrivilegedExceptionAction<Void>(){

                        @Override
                        public Void run() throws Exception {
                            ((Catalog)OASISCatalogManager.this.catalog).parseCatalog(catalogURL);
                            return null;
                        }
                    });
                }
                catch (PrivilegedActionException e) {
                    throw (IOException)e.getException();
                }
            }
            this.loadedCatalogs.add(catalogURL.toString());
        }
    }

    public final void loadCatalog(URL catalogURL) throws IOException {
        if (!this.loadedCatalogs.contains(catalogURL.toString())) {
            if ("file".equals(catalogURL.getProtocol())) {
                try {
                    File file = new File(catalogURL.toURI());
                    if (!file.exists()) {
                        throw new FileNotFoundException(file.getAbsolutePath());
                    }
                }
                catch (URISyntaxException uRISyntaxException) {
                    // empty catch block
                }
            }
            if (this.catalog == null) {
                LOG.log(Level.WARNING, "Catalog found at {0} but no org.apache.xml.resolver.CatalogManager was found.  Check the classpatch for an xmlresolver jar.", catalogURL.toString());
            } else {
                ((Catalog)this.catalog).parseCatalog(catalogURL);
                this.loadedCatalogs.add(catalogURL.toString());
            }
        }
    }

    private static OASISCatalogManager getContextCatalog() {
        try {
            OASISCatalogManager oasisCatalog = new OASISCatalogManager();
            oasisCatalog.loadContextCatalogs();
            return oasisCatalog;
        }
        catch (Throwable ex) {
            return null;
        }
    }

    public static OASISCatalogManager getCatalogManager(Bus bus) {
        if (bus == null) {
            return OASISCatalogManager.getContextCatalog();
        }
        OASISCatalogManager catalog = bus.getExtension(OASISCatalogManager.class);
        if (catalog == null && (catalog = OASISCatalogManager.getContextCatalog()) != null) {
            bus.setExtension(catalog, OASISCatalogManager.class);
        }
        return catalog;
    }

    public String resolveSystem(String sys) throws MalformedURLException, IOException {
        if (this.catalog == null) {
            return null;
        }
        return ((Catalog)this.catalog).resolveSystem(sys);
    }

    public String resolveURI(String uri) throws MalformedURLException, IOException {
        if (this.catalog == null) {
            return null;
        }
        return ((Catalog)this.catalog).resolveURI(uri);
    }

    public String resolvePublic(String uri, String parent) throws MalformedURLException, IOException {
        if (this.resolver == null) {
            return null;
        }
        return ((Catalog)this.catalog).resolvePublic(uri, parent);
    }

    public EntityResolver getEntityResolver() {
        return this.resolver;
    }

    private static ClassLoader getContextClassLoader() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
            });
        }
        return Thread.currentThread().getContextClassLoader();
    }
}

