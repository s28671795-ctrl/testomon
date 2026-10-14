/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jetty.http.CompressedContentFormat;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.ResourceContentFactory;
import org.eclipse.jetty.server.ResourceService;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceHandler
extends HandlerWrapper
implements ResourceFactory,
ResourceService.WelcomeFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceHandler.class);
    Resource _baseResource;
    ContextHandler _context;
    Resource _defaultStylesheet;
    MimeTypes _mimeTypes;
    private final ResourceService _resourceService;
    Resource _stylesheet;
    String[] _welcomes = new String[]{"index.html"};

    public ResourceHandler(ResourceService resourceService) {
        this._resourceService = resourceService;
    }

    public ResourceHandler() {
        this(new ResourceService(){

            @Override
            protected void notFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
            }
        });
        this._resourceService.setGzipEquivalentFileExtensions(new ArrayList<String>(Arrays.asList(".svgz")));
    }

    @Override
    public String getWelcomeFile(String pathInContext) throws IOException {
        if (this._welcomes == null) {
            return null;
        }
        for (int i = 0; i < this._welcomes.length; ++i) {
            String welcomeInContext = URIUtil.addPaths(pathInContext, this._welcomes[i]);
            Resource welcome = this.getResource(welcomeInContext);
            if (!welcome.exists()) continue;
            return welcomeInContext;
        }
        return null;
    }

    @Override
    public void doStart() throws Exception {
        ContextHandler.Context scontext = ContextHandler.getCurrentContext();
        ContextHandler contextHandler = this._context = scontext == null ? null : scontext.getContextHandler();
        if (this._mimeTypes == null) {
            this._mimeTypes = this._context == null ? new MimeTypes() : this._context.getMimeTypes();
        }
        this._resourceService.setContentFactory(new ResourceContentFactory(this, this._mimeTypes, this._resourceService.getPrecompressedFormats()));
        this._resourceService.setWelcomeFactory(this);
        super.doStart();
    }

    public Resource getBaseResource() {
        if (this._baseResource == null) {
            return null;
        }
        return this._baseResource;
    }

    public String getCacheControl() {
        return this._resourceService.getCacheControl().getValue();
    }

    public List<String> getGzipEquivalentFileExtensions() {
        return this._resourceService.getGzipEquivalentFileExtensions();
    }

    public MimeTypes getMimeTypes() {
        return this._mimeTypes;
    }

    @Override
    public Resource getResource(String path) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} getResource({})", this._context == null ? this._baseResource : this._context, (Object)path);
        }
        if (StringUtil.isBlank(path)) {
            throw new IllegalArgumentException("Path is blank");
        }
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Path reference invalid: " + path);
        }
        Resource r = null;
        if (this._baseResource != null) {
            r = this._baseResource.addPath(path);
            if (r.isAlias() && (this._context == null || !this._context.checkAlias(path, r))) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Rejected alias resource={} alias={}", (Object)r, (Object)r.getAlias());
                }
                throw new IllegalStateException("Rejected alias reference: " + path);
            }
        } else if (this._context != null) {
            r = this._context.getResource(path);
        }
        if ((r == null || !r.exists()) && path.endsWith("/jetty-dir.css")) {
            r = this.getStylesheet();
        }
        if (r == null) {
            throw new FileNotFoundException("Resource: " + path);
        }
        return r;
    }

    public String getResourceBase() {
        if (this._baseResource == null) {
            return null;
        }
        return this._baseResource.toString();
    }

    public Resource getStylesheet() {
        if (this._stylesheet != null) {
            return this._stylesheet;
        }
        if (this._defaultStylesheet == null) {
            this._defaultStylesheet = ResourceHandler.getDefaultStylesheet();
        }
        return this._defaultStylesheet;
    }

    public static Resource getDefaultStylesheet() {
        return Resource.newResource(ResourceHandler.class.getResource("/jetty-dir.css"));
    }

    public String[] getWelcomeFiles() {
        return this._welcomes;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (baseRequest.isHandled()) {
            return;
        }
        if (!HttpMethod.GET.is(request.getMethod()) && !HttpMethod.HEAD.is(request.getMethod())) {
            super.handle(target, baseRequest, request, response);
            return;
        }
        if (this._resourceService.doGet(request, response)) {
            baseRequest.setHandled(true);
        } else {
            super.handle(target, baseRequest, request, response);
        }
    }

    public boolean isAcceptRanges() {
        return this._resourceService.isAcceptRanges();
    }

    public boolean isDirAllowed() {
        return this._resourceService.isDirAllowed();
    }

    public boolean isDirectoriesListed() {
        return this._resourceService.isDirAllowed();
    }

    public boolean isEtags() {
        return this._resourceService.isEtags();
    }

    public CompressedContentFormat[] getPrecompressedFormats() {
        return this._resourceService.getPrecompressedFormats();
    }

    public boolean isPathInfoOnly() {
        return this._resourceService.isPathInfoOnly();
    }

    public boolean isRedirectWelcome() {
        return this._resourceService.isRedirectWelcome();
    }

    public void setAcceptRanges(boolean acceptRanges) {
        this._resourceService.setAcceptRanges(acceptRanges);
    }

    public void setBaseResource(Resource base) {
        this._baseResource = base;
    }

    public void setCacheControl(String cacheControl) {
        this._resourceService.setCacheControl(new PreEncodedHttpField(HttpHeader.CACHE_CONTROL, cacheControl));
    }

    public void setDirAllowed(boolean dirAllowed) {
        this._resourceService.setDirAllowed(dirAllowed);
    }

    public void setDirectoriesListed(boolean directory) {
        this._resourceService.setDirAllowed(directory);
    }

    public void setEtags(boolean etags) {
        this._resourceService.setEtags(etags);
    }

    public void setGzipEquivalentFileExtensions(List<String> gzipEquivalentFileExtensions) {
        this._resourceService.setGzipEquivalentFileExtensions(gzipEquivalentFileExtensions);
    }

    public void setPrecompressedFormats(CompressedContentFormat[] precompressedFormats) {
        this._resourceService.setPrecompressedFormats(precompressedFormats);
    }

    public void setMimeTypes(MimeTypes mimeTypes) {
        this._mimeTypes = mimeTypes;
    }

    public void setPathInfoOnly(boolean pathInfoOnly) {
        this._resourceService.setPathInfoOnly(pathInfoOnly);
    }

    public void setRedirectWelcome(boolean redirectWelcome) {
        this._resourceService.setRedirectWelcome(redirectWelcome);
    }

    public void setResourceBase(String resourceBase) {
        try {
            this.setBaseResource(Resource.newResource(resourceBase));
        }
        catch (Exception e) {
            LOG.warn("Invalid Base Resource reference: {}", (Object)resourceBase, (Object)e);
            throw new IllegalArgumentException(resourceBase);
        }
    }

    public void setStylesheet(String stylesheet) {
        try {
            this._stylesheet = Resource.newResource(stylesheet);
            if (!this._stylesheet.exists()) {
                LOG.warn("unable to find custom stylesheet: {}", (Object)stylesheet);
                this._stylesheet = null;
            }
        }
        catch (Exception e) {
            LOG.warn("Invalid StyleSheet reference: {}", (Object)stylesheet, (Object)e);
            throw new IllegalArgumentException(stylesheet);
        }
    }

    public void setWelcomeFiles(String[] welcomeFiles) {
        this._welcomes = welcomeFiles;
    }
}

