/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceModelComponent;
import org.glassfish.jersey.server.model.ResourceModelVisitor;
import org.glassfish.jersey.server.model.RuntimeResourceModel;

public class ResourceModel
implements ResourceModelComponent {
    private final List<Resource> rootResources;
    private final List<Resource> resources;
    private final Value<RuntimeResourceModel> runtimeRootResourceModelValue;

    private ResourceModel(List<Resource> rootResources, List<Resource> allResources) {
        this.resources = allResources;
        this.rootResources = rootResources;
        this.runtimeRootResourceModelValue = Values.lazy(new Value<RuntimeResourceModel>(){

            @Override
            public RuntimeResourceModel get() {
                return new RuntimeResourceModel(ResourceModel.this.resources);
            }
        });
    }

    public List<Resource> getRootResources() {
        return this.rootResources;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    @Override
    public void accept(ResourceModelVisitor visitor) {
        visitor.visitResourceModel(this);
    }

    @Override
    public List<? extends ResourceModelComponent> getComponents() {
        ArrayList<ResourceModelComponent> components = new ArrayList<ResourceModelComponent>();
        components.addAll(this.resources);
        components.addAll(this.getRuntimeResourceModel().getRuntimeResources());
        return components;
    }

    public RuntimeResourceModel getRuntimeResourceModel() {
        return this.runtimeRootResourceModelValue.get();
    }

    public static class Builder {
        private final List<Resource> resources;
        private final boolean subResourceModel;

        public Builder(ResourceModel resourceModel, boolean subResourceModel) {
            this.resources = resourceModel.getResources();
            this.subResourceModel = subResourceModel;
        }

        public Builder(List<Resource> resources, boolean subResourceModel) {
            this.resources = resources;
            this.subResourceModel = subResourceModel;
        }

        public Builder(boolean subResourceModel) {
            this.resources = new ArrayList<Resource>();
            this.subResourceModel = subResourceModel;
        }

        public Builder addResource(Resource resource) {
            this.resources.add(resource);
            return this;
        }

        public ResourceModel build() {
            LinkedHashMap<String, Resource> resourceMap = new LinkedHashMap<String, Resource>();
            Set separateResources = Collections.newSetFromMap(new IdentityHashMap());
            for (Resource resource : this.resources) {
                String path = resource.getPath();
                if (path == null && !this.subResourceModel) {
                    separateResources.add(resource);
                    continue;
                }
                Resource fromMap = (Resource)resourceMap.get(path);
                if (fromMap == null) {
                    resourceMap.put(path, resource);
                    continue;
                }
                resourceMap.put(path, Resource.builder(fromMap).mergeWith(resource).build());
            }
            ArrayList rootResources = new ArrayList();
            ArrayList<Object> allResources = new ArrayList<Object>();
            for (Map.Entry entry : resourceMap.entrySet()) {
                if (entry.getKey() != null) {
                    rootResources.add(entry.getValue());
                }
                allResources.add(entry.getValue());
            }
            if (!this.subResourceModel) {
                allResources.addAll(separateResources);
            }
            return new ResourceModel(rootResources, allResources);
        }
    }
}

