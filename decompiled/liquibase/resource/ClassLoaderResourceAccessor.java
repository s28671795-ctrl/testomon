/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.resource.AbstractResource;
import liquibase.resource.AbstractResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.PathResource;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.URIResource;

public class ClassLoaderResourceAccessor
extends AbstractResourceAccessor {
    private final ClassLoader classLoader;
    private CompositeResourceAccessor additionalResourceAccessors;
    protected SortedSet<String> description;

    public ClassLoaderResourceAccessor() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ClassLoaderResourceAccessor(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public List<String> describeLocations() {
        this.init();
        return this.additionalResourceAccessors.describeLocations();
    }

    @Override
    public void close() throws Exception {
        if (this.additionalResourceAccessors != null) {
            this.additionalResourceAccessors.close();
        }
    }

    protected synchronized void init() {
        if (this.additionalResourceAccessors == null) {
            this.description = new TreeSet<String>();
            this.additionalResourceAccessors = new CompositeResourceAccessor(new ResourceAccessor[0]);
            this.configureAdditionalResourceAccessors(this.classLoader);
        }
    }

    protected void configureAdditionalResourceAccessors(ClassLoader classLoader) {
        ClassLoader parent;
        URL[] urls2;
        if (classLoader instanceof URLClassLoader && (urls2 = ((URLClassLoader)classLoader).getURLs()) != null) {
            PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
            for (URL url2 : urls2) {
                try {
                    if (!url2.getProtocol().equals("file")) continue;
                    this.additionalResourceAccessors.addResourceAccessor(pathHandlerFactory.getResourceAccessor(url2.toExternalForm()));
                }
                catch (FileNotFoundException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Classloader URL " + url2.toExternalForm() + " does not exist", e);
                }
                catch (Throwable e) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning("Cannot handle classloader url " + url2.toExternalForm() + ": " + e.getMessage() + ". Operations that need to list files from this location may not work as expected", e);
                }
            }
        }
        if ((parent = classLoader.getParent()) != null) {
            this.configureAdditionalResourceAccessors(parent);
        }
    }

    @Override
    public List<Resource> search(String path, ResourceAccessor.SearchOptions searchOptions) throws IOException {
        Enumeration<URL> resources;
        this.init();
        LinkedHashSet<Resource> returnList = new LinkedHashSet<Resource>();
        PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
        try {
            resources = this.classLoader.getResources(path);
        }
        catch (IOException e) {
            throw new IOException("Cannot list resources in path " + path + ": " + e.getMessage(), e);
        }
        while (resources.hasMoreElements()) {
            URL url2 = resources.nextElement();
            String urlExternalForm = url2.toExternalForm();
            urlExternalForm = urlExternalForm.replaceFirst(Pattern.quote(path) + "/?$", "");
            try {
                ResourceAccessor resourceAccessor = pathHandlerFactory.getResourceAccessor(urlExternalForm);
                try {
                    returnList.addAll(resourceAccessor.search(path, searchOptions));
                }
                finally {
                    if (resourceAccessor == null) continue;
                    resourceAccessor.close();
                }
            }
            catch (Exception e) {
                throw new IOException(e.getMessage(), e);
            }
        }
        returnList.addAll(this.additionalResourceAccessors.search(path, searchOptions));
        return new ArrayList<Resource>(returnList);
    }

    @Override
    public List<Resource> search(String path, boolean recursive) throws IOException {
        ResourceAccessor.SearchOptions searchOptions = new ResourceAccessor.SearchOptions();
        searchOptions.setRecursive(recursive);
        return this.search(path, searchOptions);
    }

    @Override
    public List<Resource> getAll(String path) throws IOException {
        LinkedHashSet<AbstractResource> returnList = new LinkedHashSet<AbstractResource>();
        path = path.replace("\\", "/").replaceFirst("^/", "");
        Enumeration<URL> all = this.classLoader.getResources(path);
        try {
            while (all.hasMoreElements()) {
                URI uri = all.nextElement().toURI();
                if (uri.getScheme().equals("file")) {
                    returnList.add(new PathResource(path, Paths.get(uri)));
                    continue;
                }
                returnList.add(new URIResource(path, uri));
            }
        }
        catch (URISyntaxException e) {
            throw new IOException(e.getMessage(), e);
        }
        if (returnList.size() == 0) {
            return null;
        }
        return new ArrayList<Resource>(returnList);
    }
}

