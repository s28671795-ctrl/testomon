/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Locale;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.JarFileResource;
import org.eclipse.jetty.util.resource.JarResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebInfConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(WebInfConfiguration.class);
    public static final String TEMPDIR_CONFIGURED = "org.eclipse.jetty.tmpdirConfigured";
    public static final String TEMPORARY_RESOURCE_BASE = "org.eclipse.jetty.webapp.tmpResourceBase";
    protected Resource _preUnpackBaseResource;

    @Override
    public void preConfigure(WebAppContext context) throws Exception {
        this.resolveTempDirectory(context);
        this.unpack(context);
    }

    @Override
    public void configure(WebAppContext context) throws Exception {
        Resource webInf = context.getWebInf();
        if (webInf != null && webInf.isDirectory() && context.getClassLoader() instanceof WebAppClassLoader) {
            Resource lib;
            Resource classes = webInf.addPath("classes/");
            if (classes.exists()) {
                ((WebAppClassLoader)context.getClassLoader()).addClassPath(classes);
            }
            if ((lib = webInf.addPath("lib/")).exists() || lib.isDirectory()) {
                ((WebAppClassLoader)context.getClassLoader()).addJars(lib);
            }
        }
    }

    @Override
    public void deconfigure(WebAppContext context) throws Exception {
        Boolean tmpdirConfigured;
        File tempDirectory = context.getTempDirectory();
        if (!context.isPersistTempDirectory()) {
            IO.delete(tempDirectory);
        }
        if ((tmpdirConfigured = (Boolean)context.getAttribute(TEMPDIR_CONFIGURED)) != null && !tmpdirConfigured.booleanValue()) {
            context.setTempDirectory(null);
        }
        if (context.getBaseResource() != null) {
            context.getBaseResource().close();
        }
        context.setBaseResource(this._preUnpackBaseResource);
    }

    @Override
    public void cloneConfigure(WebAppContext template, WebAppContext context) throws Exception {
        Path tmpDir = Files.createTempDirectory(template.getTempDirectory().getParentFile().toPath(), WebInfConfiguration.getCanonicalNameForWebAppTmpDir(context), new FileAttribute[0]);
        File tmpDirAsFile = tmpDir.toFile();
        tmpDirAsFile.deleteOnExit();
        context.setTempDirectory(tmpDirAsFile);
    }

    public void resolveTempDirectory(WebAppContext context) throws Exception {
        File work;
        File tmpDir = context.getTempDirectory();
        if (tmpDir != null) {
            this.configureTempDirectory(tmpDir, context);
            context.setAttribute(TEMPDIR_CONFIGURED, Boolean.TRUE);
            return;
        }
        File servletTmpDir = this.asFile(context.getAttribute("jakarta.servlet.context.tempdir"));
        if (servletTmpDir != null) {
            tmpDir = servletTmpDir;
            this.configureTempDirectory(tmpDir, context);
            context.setAttribute("jakarta.servlet.context.tempdir", tmpDir);
            context.setTempDirectory(tmpDir);
            return;
        }
        File baseTemp = this.asFile(context.getAttribute("org.eclipse.jetty.webapp.basetempdir"));
        if (baseTemp != null) {
            if (!baseTemp.isDirectory() || !baseTemp.canWrite()) {
                throw new IllegalStateException("org.eclipse.jetty.webapp.basetempdir is not a writable directory");
            }
            this.makeTempDirectory(baseTemp, context);
            return;
        }
        File jettyBase = this.asFile(System.getProperty("jetty.base"));
        if (jettyBase != null && (work = new File(jettyBase, "work")).exists() && work.isDirectory() && work.canWrite()) {
            context.setPersistTempDirectory(true);
            this.makeTempDirectory(work, context);
            return;
        }
        this.makeTempDirectory(new File(System.getProperty("java.io.tmpdir")), context);
    }

    private File asFile(Object fileattr) {
        if (fileattr == null) {
            return null;
        }
        if (fileattr instanceof File) {
            return (File)fileattr;
        }
        if (fileattr instanceof String) {
            return new File((String)fileattr);
        }
        if (fileattr instanceof Path) {
            return ((Path)fileattr).toFile();
        }
        return null;
    }

    public void makeTempDirectory(File parent, WebAppContext context) throws Exception {
        if (!(parent != null && parent.exists() && parent.canWrite() && parent.isDirectory())) {
            throw new IllegalStateException("Parent for temp dir not configured correctly: " + (String)(parent == null ? "null" : "writeable=" + parent.canWrite()));
        }
        String temp = WebInfConfiguration.getCanonicalNameForWebAppTmpDir(context);
        File tmpDir = null;
        if (context.isPersistTempDirectory()) {
            tmpDir = new File(parent, temp);
            this.configureTempDirectory(tmpDir, context);
        } else {
            tmpDir = Files.createTempDirectory(parent.toPath(), temp, new FileAttribute[0]).toFile();
            tmpDir.deleteOnExit();
            this.ensureTempDirUsable(tmpDir);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Set temp dir {}", (Object)tmpDir);
        }
        context.setTempDirectory(tmpDir);
    }

    public void configureTempDirectory(File dir, WebAppContext context) {
        if (dir == null) {
            throw new IllegalArgumentException("Null temp dir");
        }
        if (!context.isPersistTempDirectory() && dir.exists() && !IO.delete(dir)) {
            throw new IllegalStateException("Failed to delete temp dir " + dir);
        }
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("Unable to create temp dir " + dir);
        }
        if (!context.isPersistTempDirectory()) {
            dir.deleteOnExit();
        }
        this.ensureTempDirUsable(dir);
    }

    private void ensureTempDirUsable(File dir) {
        if (!dir.canWrite() || !dir.isDirectory()) {
            throw new IllegalStateException("Temp dir " + dir + " not useable: writeable=" + dir.canWrite() + ", dir=" + dir.isDirectory());
        }
    }

    public void unpack(WebAppContext context) throws IOException {
        Resource webApp = context.getBaseResource();
        this._preUnpackBaseResource = context.getBaseResource();
        if (webApp == null) {
            Resource jarWebApp;
            String war = context.getWar();
            webApp = war != null && war.length() > 0 ? context.newResource(war) : context.getBaseResource();
            if (webApp == null) {
                throw new IllegalStateException("No resourceBase or war set for context");
            }
            if (webApp.isAlias()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} anti-aliased to {}", (Object)webApp, (Object)webApp.getAlias());
                }
                webApp = context.newResource(webApp.getAlias());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Try webapp={} exists={} directory={} file={}", webApp, webApp.exists(), webApp.isDirectory(), webApp.getFile());
            }
            Resource originalWarResource = webApp;
            if (webApp.exists() && !webApp.isDirectory() && !webApp.toString().startsWith("jar:") && (jarWebApp = JarResource.newJarResource(webApp)).exists() && jarWebApp.isDirectory()) {
                webApp = jarWebApp;
            }
            if (webApp.exists() && (context.isCopyWebDir() && webApp.getFile() != null && webApp.getFile().isDirectory() || context.isExtractWAR() && webApp.getFile() != null && !webApp.getFile().isDirectory() || context.isExtractWAR() && webApp.getFile() == null || !webApp.isDirectory())) {
                File sibling;
                File warfile;
                File extractedWebAppDir = null;
                if (war != null && (warfile = Resource.newResource(war).getFile()) != null && warfile.getName().toLowerCase(Locale.ENGLISH).endsWith(".war") && (sibling = new File(warfile.getParent(), warfile.getName().substring(0, warfile.getName().length() - 4))).exists() && sibling.isDirectory() && sibling.canWrite()) {
                    extractedWebAppDir = sibling;
                }
                if (extractedWebAppDir == null) {
                    extractedWebAppDir = new File(context.getTempDirectory(), "webapp");
                    context.setAttribute(TEMPORARY_RESOURCE_BASE, extractedWebAppDir);
                }
                if (webApp.getFile() != null && webApp.getFile().isDirectory()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Copy {} to  {}", (Object)webApp, (Object)extractedWebAppDir);
                    }
                    webApp.copyTo(extractedWebAppDir);
                } else {
                    Resource jarWebApp2;
                    File extractionLock = new File(context.getTempDirectory(), ".extract_lock");
                    if (!extractedWebAppDir.exists()) {
                        extractionLock.createNewFile();
                        extractedWebAppDir.mkdir();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Extract {} to {}", (Object)webApp, (Object)extractedWebAppDir);
                        }
                        jarWebApp2 = JarResource.newJarResource(webApp);
                        jarWebApp2.copyTo(extractedWebAppDir);
                        extractionLock.delete();
                    } else if (originalWarResource.lastModified() > extractedWebAppDir.lastModified() || extractionLock.exists()) {
                        extractionLock.createNewFile();
                        IO.delete(extractedWebAppDir);
                        extractedWebAppDir.mkdir();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Extract {} to {}", (Object)webApp, (Object)extractedWebAppDir);
                        }
                        jarWebApp2 = JarResource.newJarResource(webApp);
                        jarWebApp2.copyTo(extractedWebAppDir);
                        extractionLock.delete();
                    }
                }
                webApp = Resource.newResource(extractedWebAppDir.getCanonicalPath());
            }
            if (!webApp.exists() || !webApp.isDirectory()) {
                LOG.warn("Web application not found {}", (Object)war);
                throw new FileNotFoundException(war);
            }
            context.setBaseResource(webApp);
            if (LOG.isDebugEnabled()) {
                LOG.debug("webapp={}", (Object)webApp);
            }
        }
        if (context.isCopyWebInf() && !context.isCopyWebDir()) {
            Resource webInfClasses;
            Resource webInf = webApp.addPath("WEB-INF/");
            File extractedWebInfDir = new File(context.getTempDirectory(), "webinf");
            if (extractedWebInfDir.exists()) {
                IO.delete(extractedWebInfDir);
            }
            extractedWebInfDir.mkdir();
            Resource webInfLib = webInf.addPath("lib/");
            File webInfDir = new File(extractedWebInfDir, "WEB-INF");
            webInfDir.mkdir();
            if (webInfLib.exists()) {
                File webInfLibDir = new File(webInfDir, "lib");
                if (webInfLibDir.exists()) {
                    IO.delete(webInfLibDir);
                }
                webInfLibDir.mkdir();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Copying WEB-INF/lib {} to {}", (Object)webInfLib, (Object)webInfLibDir);
                }
                webInfLib.copyTo(webInfLibDir);
            }
            if ((webInfClasses = webInf.addPath("classes/")).exists()) {
                File webInfClassesDir = new File(webInfDir, "classes");
                if (webInfClassesDir.exists()) {
                    IO.delete(webInfClassesDir);
                }
                webInfClassesDir.mkdir();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Copying WEB-INF/classes from {} to {}", (Object)webInfClasses, (Object)webInfClassesDir.getAbsolutePath());
                }
                webInfClasses.copyTo(webInfClassesDir);
            }
            webInf = Resource.newResource(extractedWebInfDir.getCanonicalPath());
            ResourceCollection rc = new ResourceCollection(webInf, webApp);
            if (LOG.isDebugEnabled()) {
                LOG.debug("context.resourcebase={}", (Object)rc);
            }
            context.setBaseResource(rc);
        }
    }

    public static String getCanonicalNameForWebAppTmpDir(WebAppContext context) {
        Connector[] connectors;
        StringBuffer canonicalName = new StringBuffer();
        canonicalName.append("jetty-");
        Server server = context.getServer();
        if (server != null && (connectors = context.getServer().getConnectors()).length > 0) {
            String host2 = null;
            int port = 0;
            if (connectors != null && connectors[0] instanceof NetworkConnector) {
                NetworkConnector connector = (NetworkConnector)connectors[0];
                host2 = connector.getHost();
                port = connector.getLocalPort();
                if (port < 0) {
                    port = connector.getPort();
                }
            }
            if (host2 == null) {
                host2 = "0.0.0.0";
            }
            canonicalName.append(host2);
            canonicalName.append("-");
            canonicalName.append(port);
            canonicalName.append("-");
        }
        try {
            Resource resource = context.getBaseResource();
            if (resource == null) {
                if (context.getWar() == null || context.getWar().length() == 0) {
                    throw new IllegalStateException("No resourceBase or war set for context");
                }
                resource = context.newResource(context.getWar());
            }
            String resourceBaseName = WebInfConfiguration.getResourceBaseName(resource);
            canonicalName.append(resourceBaseName);
            canonicalName.append("-");
        }
        catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Can't get resource base name", e);
            }
            canonicalName.append("-");
        }
        String contextPath = context.getContextPath();
        contextPath = contextPath.replace('/', '_');
        contextPath = contextPath.replace('\\', '_');
        canonicalName.append(contextPath);
        canonicalName.append("-");
        String[] vhosts = context.getVirtualHosts();
        if (vhosts == null || vhosts.length <= 0) {
            canonicalName.append("any");
        } else {
            canonicalName.append(vhosts[0]);
        }
        for (int i = 0; i < canonicalName.length(); ++i) {
            char c = canonicalName.charAt(i);
            if (Character.isJavaIdentifierPart(c) || "-.".indexOf(c) >= 0) continue;
            canonicalName.setCharAt(i, '.');
        }
        canonicalName.append("-");
        return StringUtil.sanitizeFileSystemName(canonicalName.toString());
    }

    protected static String getResourceBaseName(Resource resource) {
        URI uri;
        block6: {
            try {
                File resourceFile = resource.getFile();
                if (resourceFile != null && resource instanceof JarFileResource) {
                    resourceFile = ((JarFileResource)resource).getJarFile();
                }
                if (resourceFile != null) {
                    return resourceFile.getName();
                }
            }
            catch (IOException e) {
                if (!LOG.isDebugEnabled()) break block6;
                LOG.debug("Resource has no File reference: {}", (Object)resource);
            }
        }
        if ((uri = resource.getURI()) == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Resource has no URI reference: {}", (Object)resource);
            }
            return "";
        }
        return URIUtil.getUriLastPathSegment(uri);
    }
}

