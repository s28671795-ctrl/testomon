/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import liquibase.Scope;
import liquibase.resource.AbstractResourceAccessor;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.util.CollectionUtil;

public class CompositeResourceAccessor
extends AbstractResourceAccessor {
    private final List<ResourceAccessor> resourceAccessors;

    public CompositeResourceAccessor(ResourceAccessor ... resourceAccessors) {
        this.resourceAccessors = new ArrayList<ResourceAccessor>();
        this.resourceAccessors.addAll(Arrays.asList(resourceAccessors));
    }

    public CompositeResourceAccessor(Collection<ResourceAccessor> resourceAccessors) {
        this.resourceAccessors = new ArrayList<ResourceAccessor>(resourceAccessors);
    }

    @Override
    public void close() throws Exception {
        for (ResourceAccessor accessor : this.resourceAccessors) {
            try {
                accessor.close();
            }
            catch (Exception e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Error closing " + accessor.getClass().getName() + ": " + e.getMessage(), e);
            }
        }
    }

    public CompositeResourceAccessor addResourceAccessor(ResourceAccessor resourceAccessor) {
        this.resourceAccessors.add(resourceAccessor);
        return this;
    }

    public void removeResourceAccessor(ResourceAccessor resourceAccessor) {
        this.resourceAccessors.remove(resourceAccessor);
    }

    @Override
    public List<Resource> search(String path, ResourceAccessor.SearchOptions searchOptions) throws IOException {
        LinkedHashSet<Resource> returnList = new LinkedHashSet<Resource>();
        for (ResourceAccessor accessor : this.resourceAccessors) {
            returnList.addAll(CollectionUtil.createIfNull(accessor.search(path, searchOptions)));
        }
        return new ArrayList<Resource>(returnList);
    }

    @Override
    public List<Resource> search(String path, boolean recursive) throws IOException {
        LinkedHashSet<Resource> returnList = new LinkedHashSet<Resource>();
        for (ResourceAccessor accessor : this.resourceAccessors) {
            returnList.addAll(CollectionUtil.createIfNull(accessor.search(path, recursive)));
        }
        return new ArrayList<Resource>(returnList);
    }

    @Override
    public List<Resource> getAll(String path) throws IOException {
        LinkedHashSet<Resource> returnList = new LinkedHashSet<Resource>();
        for (ResourceAccessor accessor : this.resourceAccessors) {
            returnList.addAll(CollectionUtil.createIfNull(accessor.getAll(path)));
        }
        return new ArrayList<Resource>(returnList);
    }

    @Override
    public List<String> describeLocations() {
        LinkedHashSet<String> returnSet = new LinkedHashSet<String>();
        for (ResourceAccessor accessor : this.resourceAccessors) {
            returnSet.addAll(CollectionUtil.createIfNull(accessor.describeLocations()));
        }
        return new ArrayList<String>(returnSet);
    }
}

