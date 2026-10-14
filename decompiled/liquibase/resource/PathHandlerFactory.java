/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.resource.OpenOptions;
import liquibase.resource.PathHandler;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;

public class PathHandlerFactory
extends AbstractPluginFactory<PathHandler> {
    private PathHandlerFactory() {
    }

    @Override
    protected Class<PathHandler> getPluginClass() {
        return PathHandler.class;
    }

    @Override
    protected int getPriority(PathHandler obj, Object ... args2) {
        return obj.getPriority((String)args2[0]);
    }

    public ResourceAccessor getResourceAccessor(String root) throws IOException {
        PathHandler plugin = (PathHandler)this.getPlugin(root);
        if (plugin == null) {
            throw new IOException("Cannot parse resource location: '" + root + "'");
        }
        return plugin.getResourceAccessor(root);
    }

    public OutputStream createResource(String resourcePath) throws IOException {
        PathHandler plugin = (PathHandler)this.getPlugin(resourcePath);
        if (plugin == null) {
            throw new IOException("Cannot parse resource location: '" + resourcePath + "'");
        }
        return plugin.createResource(resourcePath);
    }

    public Resource getResource(String resourcePath) throws IOException {
        PathHandler plugin = (PathHandler)this.getPlugin(resourcePath);
        if (plugin == null) {
            throw new IOException("Cannot parse resource location: '" + resourcePath + "'");
        }
        return plugin.getResource(resourcePath);
    }

    @Deprecated
    public OutputStream openResourceOutputStream(String resourcePath, boolean createIfNotExists) throws IOException {
        return this.openResourceOutputStream(resourcePath, new OpenOptions().setCreateIfNeeded(createIfNotExists));
    }

    public OutputStream openResourceOutputStream(String resourcePath, OpenOptions openOptions) throws IOException {
        Resource resource = this.getResource(resourcePath);
        if (!resource.exists()) {
            if (openOptions.isCreateIfNeeded()) {
                return this.createResource(resourcePath);
            }
            return null;
        }
        return resource.openOutputStream(openOptions);
    }

    private static class FoundResourceAccessor
    implements ResourceAccessor {
        private final Resource foundResource;
        private final String location;

        public FoundResourceAccessor(String location, Resource foundResource) {
            this.location = location;
            this.foundResource = foundResource;
        }

        @Override
        public List<Resource> search(String path, ResourceAccessor.SearchOptions searchOptions) throws IOException {
            throw new UnexpectedLiquibaseException("Method not implemented");
        }

        @Override
        public List<Resource> search(String path, boolean recursive) throws IOException {
            throw new UnexpectedLiquibaseException("Method not implemented");
        }

        @Override
        public List<Resource> getAll(String path) throws IOException {
            if (this.foundResource == null || !this.foundResource.exists()) {
                return null;
            }
            return Collections.singletonList(this.foundResource);
        }

        @Override
        public List<String> describeLocations() {
            return Collections.singletonList(this.location);
        }

        @Override
        public void close() throws Exception {
        }
    }
}

