/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.CXFPermissions;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.resource.SecurityActions;

public class URIResolver {
    private static final Logger LOG = LogUtils.getLogger(URIResolver.class);
    private Map<String, LoadingByteArrayOutputStream> cache = new HashMap<String, LoadingByteArrayOutputStream>();
    private File file;
    private URI uri;
    private URL url;
    private InputStream is;
    private Class<?> calling;

    public URIResolver() {
    }

    public URIResolver(String path) throws IOException {
        this("", path);
    }

    public URIResolver(String baseUriStr, String uriStr) throws IOException {
        this(baseUriStr, uriStr, null);
    }

    public URIResolver(String baseUriStr, String uriStr, Class<?> calling) throws IOException {
        Class<?> clazz = this.calling = calling != null ? calling : this.getClass();
        if (uriStr.startsWith("classpath:")) {
            this.tryClasspath(uriStr);
        } else if (baseUriStr != null && (baseUriStr.startsWith("jar:") || baseUriStr.startsWith("zip:") || baseUriStr.startsWith("wsjar:")) && !this.isAbsolute(uriStr)) {
            this.tryArchive(baseUriStr, uriStr);
        } else if (uriStr.startsWith("jar:") || uriStr.startsWith("zip:") || uriStr.startsWith("wsjar:")) {
            this.tryArchive(uriStr);
        } else {
            this.tryFileSystem(baseUriStr, uriStr);
        }
    }

    public void unresolve() {
        this.file = null;
        this.uri = null;
        this.is = null;
    }

    public void resolve(String baseUriStr, String uriStr, Class<?> callingCls) throws IOException {
        this.calling = callingCls != null ? callingCls : this.getClass();
        this.file = null;
        this.uri = null;
        this.is = null;
        if (uriStr.startsWith("classpath:")) {
            this.tryClasspath(uriStr);
        } else if (baseUriStr != null && (baseUriStr.startsWith("jar:") || baseUriStr.startsWith("zip:") || baseUriStr.startsWith("wsjar:")) && !this.isAbsolute(uriStr)) {
            this.tryArchive(baseUriStr, uriStr);
        } else if (uriStr.startsWith("jar:") || uriStr.startsWith("zip:") || uriStr.startsWith("wsjar:")) {
            this.tryArchive(uriStr);
        } else {
            this.tryFileSystem(baseUriStr, uriStr);
        }
    }

    private boolean isAbsolute(String uriStr) {
        try {
            return new URI(uriStr).isAbsolute();
        }
        catch (URISyntaxException e) {
            return false;
        }
    }

    private void tryFileSystem(String baseUriStr, String uriStr) throws IOException, MalformedURLException {
        String fileStr;
        block26: {
            block25: {
                fileStr = uriStr.replace("%20", " ");
                try {
                    URI relative;
                    final File uriFileTemp = new File(fileStr);
                    File uriFile = new File(AccessController.doPrivileged(new PrivilegedAction<String>(){

                        @Override
                        public String run() {
                            return uriFileTemp.getAbsolutePath();
                        }
                    }));
                    if (!SecurityActions.fileExists(uriFile, CXFPermissions.RESOLVE_URI)) {
                        try {
                            File f2;
                            URI urif = new URI(URLDecoder.decode(uriStr, "ASCII"));
                            if ("file".equals(urif.getScheme()) && urif.isAbsolute() && (f2 = new File(urif)).exists()) {
                                uriFile = f2;
                            }
                        }
                        catch (URISyntaxException urif) {
                            // empty catch block
                        }
                    }
                    if ((relative = !SecurityActions.fileExists(uriFile, CXFPermissions.RESOLVE_URI) ? new URI(uriStr.replace(" ", "%20")) : uriFile.getAbsoluteFile().toURI()).isAbsolute()) {
                        this.uri = relative;
                        this.url = relative.toURL();
                        try {
                            HttpURLConnection huc = this.createInputStream();
                            int status = huc.getResponseCode();
                            if (status != 200 && URIResolver.followRedirect(status)) {
                                this.uri = new URI(huc.getHeaderField("Location"));
                                this.url = this.uri.toURL();
                                this.createInputStream();
                            }
                            break block25;
                        }
                        catch (ClassCastException ex) {
                            this.is = this.url.openStream();
                        }
                        break block25;
                    }
                    if (!StringUtils.isEmpty(baseUriStr)) {
                        File baseFile = new File(baseUriStr);
                        if (!baseFile.exists() && baseUriStr.startsWith("file:")) {
                            baseFile = new File(this.getFilePathFromUri(baseUriStr));
                        }
                        URI base = baseFile.exists() ? baseFile.toURI() : new URI(baseUriStr);
                        if ((base = base.resolve(relative)).isAbsolute() && "file".equalsIgnoreCase(base.getScheme())) {
                            try {
                                baseFile = new File(base.getPath().replace("%20", " "));
                                if (baseFile.exists()) {
                                    this.is = base.toURL().openStream();
                                    this.uri = base;
                                    break block25;
                                }
                                this.tryClasspath(base.toString().startsWith("file:") ? base.toString().substring(5) : base.toString());
                            }
                            catch (Throwable th) {
                                this.tryClasspath(base.toString().startsWith("file:") ? base.toString().substring(5) : base.toString());
                            }
                            break block25;
                        }
                        this.tryClasspath(base.toString().startsWith("file:") ? base.toString().substring(5) : base.toString());
                        break block25;
                    }
                    this.tryClasspath(fileStr.startsWith("file:") ? fileStr.substring(5) : fileStr);
                }
                catch (URISyntaxException uriFileTemp) {
                    // empty catch block
                }
            }
            if (this.is == null && baseUriStr != null && baseUriStr.startsWith("classpath:")) {
                this.tryClasspath(baseUriStr + fileStr);
            }
            if (this.is == null && this.uri != null && "file".equals(this.uri.getScheme())) {
                try {
                    this.file = new File(this.uri);
                }
                catch (IllegalArgumentException iae) {
                    this.file = new File(this.uri.toURL().getPath());
                    if (this.file.exists()) break block26;
                    this.file = null;
                }
            }
        }
        if (this.is == null && this.file != null && this.file.exists()) {
            this.uri = this.file.toURI();
            try {
                this.is = Files.newInputStream(this.file.toPath(), new OpenOption[0]);
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException("File was deleted! " + fileStr, e);
            }
            this.url = this.file.toURI().toURL();
        } else if (this.is == null) {
            this.tryClasspath(fileStr);
        }
    }

    private static boolean followRedirect(int status) {
        return (status == 302 || status == 301 || status == 303) && Boolean.parseBoolean(SystemPropertyAction.getPropertyOrNull("http.autoredirect"));
    }

    private HttpURLConnection createInputStream() throws IOException {
        HttpURLConnection huc = (HttpURLConnection)this.url.openConnection();
        String host2 = SystemPropertyAction.getPropertyOrNull("http.proxyHost");
        if (host2 != null) {
            String username = SystemPropertyAction.getPropertyOrNull("http.proxy.user");
            String password = SystemPropertyAction.getPropertyOrNull("http.proxy.password");
            if (username != null && password != null) {
                String encoded = Base64Utility.encode((username + ":" + password).getBytes());
                huc.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
            }
        }
        huc.setConnectTimeout(30000);
        huc.setReadTimeout(60000);
        this.is = huc.getInputStream();
        return huc;
    }

    private String getFilePathFromUri(String uriString) {
        String path = null;
        try {
            path = new URL(uriString).getPath();
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        if (path == null) {
            if (uriString.startsWith("file:/")) {
                path = uriString.substring(6);
            } else if (uriString.startsWith("file:")) {
                path = uriString.substring(5);
            }
        }
        if (path != null) {
            return path.replace("%20", " ");
        }
        return null;
    }

    private void tryArchive(String baseStr, String uriStr) throws IOException {
        int i = baseStr.indexOf(33);
        if (i == -1) {
            this.tryFileSystem(baseStr, uriStr);
        }
        String archiveBase = baseStr.substring(0, i + 1);
        String archiveEntry = baseStr.substring(i + 1);
        try {
            URI u = new URI(archiveEntry).resolve(uriStr);
            this.tryArchive(archiveBase + u.toString());
            if (this.is != null) {
                if (u.isAbsolute()) {
                    this.url = u.toURL();
                }
                return;
            }
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        this.tryFileSystem("", uriStr);
    }

    private void tryArchive(String uriStr) throws IOException {
        int i = uriStr.indexOf(33);
        if (i == -1) {
            return;
        }
        this.url = new URL(uriStr);
        try {
            this.is = this.url.openStream();
            try {
                this.uri = this.url.toURI();
            }
            catch (URISyntaxException uRISyntaxException) {}
        }
        catch (IOException e) {
            uriStr = uriStr.substring(i + 1);
            this.tryClasspath(uriStr);
        }
    }

    private void tryClasspath(String uriStr) throws IOException {
        boolean isClasspathURL = false;
        if (uriStr.startsWith("classpath:")) {
            uriStr = uriStr.substring(10);
            isClasspathURL = true;
        }
        this.url = ClassLoaderUtils.getResource(uriStr, this.calling);
        if (this.url == null) {
            this.tryRemote(uriStr);
        } else {
            block11: {
                try {
                    this.uri = this.url.toURI();
                }
                catch (URISyntaxException e) {
                    if (this.url.toString().contains(" ")) {
                        this.url = new URL(this.url.toString().replace(" ", "%20"));
                    }
                    try {
                        this.uri = this.url.toURI();
                    }
                    catch (URISyntaxException e1) {
                        int pos;
                        String urlStr = this.url.toString();
                        if (!urlStr.startsWith("jar:") && !urlStr.startsWith("zip:") && !urlStr.startsWith("wsjar:") || (pos = urlStr.indexOf(33)) == -1) break block11;
                        try {
                            this.uri = new URI("classpath:" + urlStr.substring(pos + 1));
                        }
                        catch (URISyntaxException uRISyntaxException) {
                            // empty catch block
                        }
                    }
                }
            }
            this.is = this.url.openStream();
        }
        if (this.is == null && isClasspathURL) {
            LOG.log(Level.WARNING, "NOT_ON_CLASSPATH", uriStr);
        }
    }

    private void tryRemote(String uriStr) throws IOException {
        try {
            LoadingByteArrayOutputStream bout = this.cache.get(uriStr);
            this.url = new URL(uriStr);
            this.uri = new URI(this.url.toString());
            if (bout == null) {
                URLConnection connection = this.url.openConnection();
                this.is = connection.getInputStream();
                bout = new LoadingByteArrayOutputStream(1024);
                IOUtils.copy(this.is, bout);
                this.is.close();
                this.cache.put(uriStr, bout);
            }
            this.is = bout.createInputStream();
        }
        catch (MalformedURLException | URISyntaxException exception) {
            // empty catch block
        }
    }

    public URI getURI() {
        return this.uri;
    }

    public URL getURL() {
        return this.url;
    }

    public InputStream getInputStream() {
        return this.is;
    }

    public boolean isFile() {
        if (this.file != null) {
            return this.file.exists();
        }
        return false;
    }

    public File getFile() {
        return this.file;
    }

    public boolean isResolved() {
        return this.is != null;
    }
}

