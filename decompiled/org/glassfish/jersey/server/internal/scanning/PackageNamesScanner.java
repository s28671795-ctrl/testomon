/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.scanning;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ReflectPermission;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.glassfish.jersey.internal.OsgiRegistry;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.Tokenizer;
import org.glassfish.jersey.server.internal.AbstractResourceFinderAdapter;
import org.glassfish.jersey.server.internal.scanning.BundleSchemeResourceFinderFactory;
import org.glassfish.jersey.server.internal.scanning.CompositeResourceFinder;
import org.glassfish.jersey.server.internal.scanning.FileSchemeResourceFinderFactory;
import org.glassfish.jersey.server.internal.scanning.JarZipSchemeResourceFinderFactory;
import org.glassfish.jersey.server.internal.scanning.ResourceFinderException;
import org.glassfish.jersey.server.internal.scanning.UriSchemeResourceFinderFactory;
import org.glassfish.jersey.server.internal.scanning.VfsSchemeResourceFinderFactory;
import org.glassfish.jersey.uri.UriComponent;

public final class PackageNamesScanner
extends AbstractResourceFinderAdapter {
    private final boolean recursive;
    private final String[] packages;
    private final ClassLoader classloader;
    private final Map<String, UriSchemeResourceFinderFactory> finderFactories;
    private CompositeResourceFinder compositeResourceFinder;

    public PackageNamesScanner(String[] packages, boolean recursive) {
        this(AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA()), Tokenizer.tokenize(packages, " ,;\n"), recursive);
    }

    public PackageNamesScanner(ClassLoader classLoader, String[] packages, final boolean recursive) {
        this.recursive = recursive;
        this.packages = (String[])packages.clone();
        this.classloader = classLoader;
        this.finderFactories = new HashMap<String, UriSchemeResourceFinderFactory>();
        this.add(new JarZipSchemeResourceFinderFactory());
        this.add(new FileSchemeResourceFinderFactory());
        this.add(new VfsSchemeResourceFinderFactory());
        this.add(new BundleSchemeResourceFinderFactory());
        final OsgiRegistry osgiRegistry = ReflectionHelper.getOsgiRegistryInstance();
        if (osgiRegistry != null) {
            PackageNamesScanner.setResourcesProvider(new ResourcesProvider(){

                @Override
                public Enumeration<URL> getResources(String packagePath, ClassLoader classLoader) throws IOException {
                    return osgiRegistry.getPackageResources(packagePath, classLoader, recursive);
                }
            });
        }
        this.init();
    }

    private void add(UriSchemeResourceFinderFactory uriSchemeResourceFinderFactory) {
        for (String scheme2 : uriSchemeResourceFinderFactory.getSchemes()) {
            this.finderFactories.put(scheme2.toLowerCase(Locale.ROOT), uriSchemeResourceFinderFactory);
        }
    }

    @Override
    public boolean hasNext() {
        return this.compositeResourceFinder.hasNext();
    }

    @Override
    public String next() {
        return this.compositeResourceFinder.next();
    }

    @Override
    public InputStream open() {
        return this.compositeResourceFinder.open();
    }

    @Override
    public void close() {
        this.compositeResourceFinder.close();
    }

    @Override
    public void reset() {
        this.close();
        this.init();
    }

    public ClassLoader getClassloader() {
        return this.classloader;
    }

    /*
     * Exception decompiling
     */
    private void init() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static void setResourcesProvider(ResourcesProvider provider) throws SecurityException {
        ResourcesProvider.setInstance(provider);
    }

    private void addResourceFinder(URI u) {
        UriSchemeResourceFinderFactory finderFactory = this.finderFactories.get(u.getScheme().toLowerCase(Locale.ROOT));
        if (finderFactory == null) {
            throw new ResourceFinderException("The URI scheme " + u.getScheme() + " of the URI " + u + " is not supported. Package scanning deployment is not supported for such URIs.\nTry using a different deployment mechanism such as explicitly declaring root resource and provider classes using an extension of javax.ws.rs.core.Application");
        }
        this.compositeResourceFinder.push(finderFactory.create(u, this.recursive));
    }

    private URI toURI(URL url2) throws URISyntaxException {
        try {
            return url2.toURI();
        }
        catch (URISyntaxException e) {
            return URI.create(this.toExternalForm(url2));
        }
    }

    private String toExternalForm(URL u) {
        int len = u.getProtocol().length() + 1;
        if (u.getAuthority() != null && u.getAuthority().length() > 0) {
            len += 2 + u.getAuthority().length();
        }
        if (u.getPath() != null) {
            len += u.getPath().length();
        }
        if (u.getQuery() != null) {
            len += 1 + u.getQuery().length();
        }
        if (u.getRef() != null) {
            len += 1 + u.getRef().length();
        }
        StringBuilder result = new StringBuilder(len);
        result.append(u.getProtocol());
        result.append(":");
        if (u.getAuthority() != null && u.getAuthority().length() > 0) {
            result.append("//");
            result.append(u.getAuthority());
        }
        if (u.getPath() != null) {
            result.append(UriComponent.contextualEncode(u.getPath(), UriComponent.Type.PATH));
        }
        if (u.getQuery() != null) {
            result.append('?');
            result.append(UriComponent.contextualEncode(u.getQuery(), UriComponent.Type.QUERY));
        }
        if (u.getRef() != null) {
            result.append("#");
            result.append(u.getRef());
        }
        return result.toString();
    }

    public static abstract class ResourcesProvider {
        private static volatile ResourcesProvider provider;

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static ResourcesProvider getInstance() {
            ResourcesProvider result = provider;
            if (result != null) return result;
            Class<ResourcesProvider> clazz = ResourcesProvider.class;
            synchronized (ResourcesProvider.class) {
                result = provider;
                if (result != null) return result;
                provider = result = new ResourcesProvider(){

                    @Override
                    public Enumeration<URL> getResources(String name, ClassLoader cl) throws IOException {
                        return cl.getResources(name);
                    }
                };
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return result;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static void setInstance(ResourcesProvider provider) throws SecurityException {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                ReflectPermission rp = new ReflectPermission("suppressAccessChecks");
                security.checkPermission(rp);
            }
            Class<ResourcesProvider> clazz = ResourcesProvider.class;
            synchronized (ResourcesProvider.class) {
                ResourcesProvider.provider = provider;
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return;
            }
        }

        public abstract Enumeration<URL> getResources(String var1, ClassLoader var2) throws IOException;

        static /* synthetic */ ResourcesProvider access$000() {
            return ResourcesProvider.getInstance();
        }
    }
}

