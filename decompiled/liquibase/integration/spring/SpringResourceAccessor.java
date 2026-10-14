/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.ContextResource
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.ResourcePatternUtils
 *  org.springframework.util.StringUtils
 */
package liquibase.integration.spring;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.integration.spring.SpringResource;
import liquibase.resource.AbstractResourceAccessor;
import liquibase.resource.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.StringUtils;

public class SpringResourceAccessor
extends AbstractResourceAccessor {
    private final ResourceLoader resourceLoader;
    private final DefaultResourceLoader fallbackResourceLoader;

    public SpringResourceAccessor(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.fallbackResourceLoader = resourceLoader == null ? new DefaultResourceLoader(Thread.currentThread().getContextClassLoader()) : new DefaultResourceLoader(resourceLoader.getClassLoader());
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public List<Resource> getAll(String path) throws IOException {
        path = this.finalizeSearchPath(path);
        org.springframework.core.io.Resource[] springResources = ResourcePatternUtils.getResourcePatternResolver((ResourceLoader)this.resourceLoader).getResources(path);
        ArrayList<Resource> returnList = new ArrayList<Resource>();
        for (org.springframework.core.io.Resource resource : springResources) {
            if (!resource.exists()) continue;
            returnList.add(new SpringResource(path, resource.getURI(), resource));
        }
        if (returnList.isEmpty()) {
            return null;
        }
        return returnList;
    }

    @Override
    public List<Resource> search(String searchPath, boolean recursive) throws IOException {
        searchPath = recursive ? searchPath + "/**" : searchPath + "/*";
        searchPath = this.finalizeSearchPath(searchPath);
        org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver((ResourceLoader)this.resourceLoader).getResources(searchPath);
        ArrayList<Resource> returnList = new ArrayList<Resource>();
        for (org.springframework.core.io.Resource resource : resources) {
            boolean isFile = this.resourceIsFile(resource);
            if (!isFile) continue;
            returnList.add(new SpringResource(this.getResourcePath(resource), resource.getURI(), resource));
        }
        return returnList;
    }

    @Override
    public List<String> describeLocations() {
        return Collections.singletonList("Spring classpath");
    }

    protected String getResourcePath(org.springframework.core.io.Resource resource) {
        if (resource instanceof ContextResource) {
            return ((ContextResource)resource).getPathWithinContext();
        }
        if (resource instanceof ClassPathResource) {
            return ((ClassPathResource)resource).getPath();
        }
        try {
            String url2 = resource.getURL().toExternalForm();
            url2 = this.decodeUrl(resource, url2);
            if (url2.contains("!")) {
                return url2.replaceFirst(".*!", "");
            }
            while (!this.getResource("classpath:" + url2).exists()) {
                String newUrl = url2.replaceFirst("^/?.*?/", "");
                if (newUrl.equals(url2)) {
                    throw new UnexpectedLiquibaseException("Could not determine path for " + resource.getURL().toExternalForm());
                }
                url2 = newUrl;
            }
            return url2;
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException("Cannot determine resource path for " + resource.getDescription());
        }
    }

    protected String getCompletePath(String relativeTo, String path) throws IOException {
        String searchPath;
        path = path.replace("\\", "/");
        if (relativeTo == null) {
            searchPath = path;
        } else {
            org.springframework.core.io.Resource rootResource = this.getResource(relativeTo = relativeTo.replace("\\", "/"));
            boolean relativeIsFile = this.resourceIsFile(rootResource);
            if (relativeIsFile) {
                String relativePath = relativeTo.replaceFirst("[^/]+$", "");
                searchPath = relativePath + path;
            } else {
                searchPath = relativeTo + "/" + path;
            }
        }
        return searchPath;
    }

    protected org.springframework.core.io.Resource getResource(String resourcePath) {
        org.springframework.core.io.Resource defaultLoaderResource = this.fallbackResourceLoader.getResource(resourcePath);
        if (defaultLoaderResource.exists()) {
            return defaultLoaderResource;
        }
        return this.resourceLoader.getResource(resourcePath);
    }

    protected boolean resourceIsFile(org.springframework.core.io.Resource resource) throws IOException {
        if (resource.exists() && resource.isFile()) {
            try {
                return resource.getFile().isFile();
            }
            catch (UnsupportedOperationException e) {
                return true;
            }
        }
        String filename = resource.getFilename();
        return filename != null && filename.contains(".");
    }

    protected String finalizeSearchPath(String searchPath) {
        if (searchPath.matches("^classpath\\*?:.*")) {
            searchPath = searchPath.replace("classpath:", "").replace("classpath*:", "");
            searchPath = "classpath*:/" + searchPath;
        } else if (!searchPath.matches("^\\w+:.*")) {
            searchPath = "classpath*:/" + searchPath;
        }
        searchPath = searchPath.replace("\\", "/");
        searchPath = searchPath.replaceAll("//+", "/");
        searchPath = StringUtils.cleanPath((String)searchPath);
        return searchPath;
    }

    private String decodeUrl(org.springframework.core.io.Resource resource, String url2) throws IOException {
        try {
            url2 = URLDecoder.decode(resource.getURL().toExternalForm(), StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Failed to decode path " + url2 + "; continuing without decoding.", e);
        }
        return url2;
    }
}

