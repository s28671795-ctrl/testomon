/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllowedResourceAliasChecker
extends AbstractLifeCycle
implements ContextHandler.AliasCheck {
    private static final Logger LOG = LoggerFactory.getLogger(AllowedResourceAliasChecker.class);
    protected static final LinkOption[] FOLLOW_LINKS = new LinkOption[0];
    protected static final LinkOption[] NO_FOLLOW_LINKS = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
    private final ContextHandler _contextHandler;
    private final Supplier<Resource> _resourceBaseSupplier;
    private final List<Path> _protected = new ArrayList<Path>();
    private final AllowedResourceAliasCheckListener _listener = new AllowedResourceAliasCheckListener();
    private boolean _initialized;
    protected Path _base;

    public AllowedResourceAliasChecker(ContextHandler contextHandler) {
        this(contextHandler, contextHandler::getBaseResource);
    }

    public AllowedResourceAliasChecker(ContextHandler contextHandler, Resource baseResource) {
        this(contextHandler, () -> baseResource);
    }

    public AllowedResourceAliasChecker(ContextHandler contextHandler, Supplier<Resource> resourceBaseSupplier) {
        this._contextHandler = Objects.requireNonNull(contextHandler);
        this._resourceBaseSupplier = Objects.requireNonNull(resourceBaseSupplier);
    }

    protected ContextHandler getContextHandler() {
        return this._contextHandler;
    }

    private void extractBaseResourceFromContext() {
        this._base = this.getPath(this._resourceBaseSupplier.get());
        if (this._base == null) {
            return;
        }
        try {
            String[] protectedTargets;
            if (Files.exists(this._base, NO_FOLLOW_LINKS)) {
                this._base = this._base.toRealPath(FOLLOW_LINKS);
            }
            if ((protectedTargets = this._contextHandler.getProtectedTargets()) != null) {
                for (String s2 : protectedTargets) {
                    this._protected.add(this._base.getFileSystem().getPath(this._base.toString(), s2));
                }
            }
        }
        catch (IOException e) {
            LOG.warn("Base resource failure ({} is disabled): {}", this.getClass().getName(), this._base, e);
            this._base = null;
        }
    }

    protected void initialize() {
        this.extractBaseResourceFromContext();
        this._initialized = true;
    }

    @Override
    protected void doStart() throws Exception {
        if (this._contextHandler.isStarted()) {
            this.initialize();
        } else {
            this._contextHandler.addEventListener(this._listener);
        }
    }

    @Override
    protected void doStop() throws Exception {
        this._contextHandler.removeEventListener(this._listener);
        this._base = null;
        this._protected.clear();
    }

    @Override
    public boolean check(String pathInContext, Resource resource) {
        if (!this._initialized) {
            this.extractBaseResourceFromContext();
        }
        if (this._base == null) {
            return false;
        }
        try {
            if (!resource.exists()) {
                return false;
            }
            Path path = this.getPath(resource);
            if (path == null) {
                return false;
            }
            return this.check(pathInContext, path);
        }
        catch (Throwable t2) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to check alias", t2);
            }
            return false;
        }
    }

    protected boolean check(String pathInContext, Path path) {
        return this.isAllowed(AllowedResourceAliasChecker.getRealPath(path));
    }

    protected boolean isAllowed(Path path) {
        if (path != null && Files.exists(path, new LinkOption[0])) {
            while (path != null) {
                if (this.isSameFile(path, this._base)) {
                    return true;
                }
                for (Path p : this._protected) {
                    if (!this.isSameFile(path, p)) continue;
                    return false;
                }
                path = path.getParent();
            }
        }
        return false;
    }

    protected boolean isSameFile(Path path1, Path path2) {
        block4: {
            if (Objects.equals(path1, path2)) {
                return true;
            }
            try {
                if (Files.isSameFile(path1, path2)) {
                    return true;
                }
            }
            catch (Throwable t2) {
                if (!LOG.isDebugEnabled()) break block4;
                LOG.debug("ignored", t2);
            }
        }
        return false;
    }

    private static Path getRealPath(Path path) {
        block4: {
            if (path == null || !Files.exists(path, new LinkOption[0])) {
                return null;
            }
            try {
                path = path.toRealPath(FOLLOW_LINKS);
                if (Files.exists(path, new LinkOption[0])) {
                    return path;
                }
            }
            catch (IOException e) {
                if (!LOG.isDebugEnabled()) break block4;
                LOG.debug("No real path for {}", (Object)path, (Object)e);
            }
        }
        return null;
    }

    protected Path getPath(Resource resource) {
        try {
            if (resource instanceof PathResource) {
                return ((PathResource)resource).getPath();
            }
            return resource == null ? null : resource.getFile().toPath();
        }
        catch (Throwable t2) {
            LOG.trace("getPath() failed", t2);
            return null;
        }
    }

    @Override
    public String toString() {
        String[] protectedTargets = this._contextHandler.getProtectedTargets();
        return String.format("%s@%x{base=%s,protected=%s}", this.getClass().getSimpleName(), this.hashCode(), this._base, protectedTargets == null ? null : Arrays.asList(protectedTargets));
    }

    private class AllowedResourceAliasCheckListener
    implements LifeCycle.Listener {
        private AllowedResourceAliasCheckListener() {
        }

        @Override
        public void lifeCycleStarted(LifeCycle event) {
            AllowedResourceAliasChecker.this.initialize();
        }
    }
}

