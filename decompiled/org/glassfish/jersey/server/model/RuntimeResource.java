/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceModelComponent;
import org.glassfish.jersey.server.model.ResourceModelVisitor;
import org.glassfish.jersey.uri.PathPattern;

public class RuntimeResource
implements ResourceModelComponent {
    public static final Comparator<RuntimeResource> COMPARATOR = new Comparator<RuntimeResource>(){

        @Override
        public int compare(RuntimeResource o1, RuntimeResource o2) {
            int cmp = PathPattern.COMPARATOR.compare(o1.getPathPattern(), o2.getPathPattern());
            if (cmp == 0) {
                int locatorCmp = o1.resourceLocators.size() - o2.resourceLocators.size();
                return locatorCmp == 0 ? o2.regex.compareTo(o1.regex) : locatorCmp;
            }
            return cmp;
        }
    };
    private final String regex;
    private final List<ResourceMethod> resourceMethods;
    private final List<ResourceMethod> resourceLocators;
    private final List<RuntimeResource> childRuntimeResources;
    private final List<Resource> resources;
    private final RuntimeResource parent;
    private final PathPattern pathPattern;

    private RuntimeResource(List<Resource> resources, List<Builder> childRuntimeResourceBuilders, RuntimeResource parent, String regex2) {
        this.parent = parent;
        this.pathPattern = resources.get(0).getPathPattern();
        this.resources = new ArrayList<Resource>(resources);
        this.regex = regex2;
        this.resourceMethods = new ArrayList<ResourceMethod>();
        this.resourceLocators = new ArrayList<ResourceMethod>();
        this.childRuntimeResources = new ArrayList<RuntimeResource>();
        for (Builder childRuntimeResourceBuilder : childRuntimeResourceBuilders) {
            this.childRuntimeResources.add(childRuntimeResourceBuilder.build(this));
        }
        Collections.sort(this.childRuntimeResources, COMPARATOR);
        for (Resource res : this.resources) {
            this.resourceMethods.addAll(res.getResourceMethods());
            ResourceMethod resourceLocator = res.getResourceLocator();
            if (resourceLocator == null) continue;
            this.resourceLocators.add(resourceLocator);
        }
    }

    public List<RuntimeResource> getChildRuntimeResources() {
        return this.childRuntimeResources;
    }

    public String getRegex() {
        return this.regex;
    }

    public List<ResourceMethod> getResourceMethods() {
        return this.resourceMethods;
    }

    public List<ResourceMethod> getResourceLocators() {
        return this.resourceLocators;
    }

    public ResourceMethod getResourceLocator() {
        if (this.resourceLocators.size() >= 1) {
            return this.resourceLocators.get(0);
        }
        return null;
    }

    public RuntimeResource getParent() {
        return this.parent;
    }

    public PathPattern getPathPattern() {
        return this.pathPattern;
    }

    public String getFullPathRegex() {
        if (this.parent == null) {
            return this.regex;
        }
        return this.parent.getRegex() + this.regex;
    }

    public List<Resource> getParentResources() {
        return this.resources.stream().map(child -> child == null ? null : child.getParent()).collect(Collectors.toList());
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    @Override
    public void accept(ResourceModelVisitor visitor) {
        visitor.visitRuntimeResource(this);
    }

    @Override
    public List<? extends ResourceModelComponent> getComponents() {
        return this.getChildRuntimeResources();
    }

    static class Builder {
        private final List<Resource> resources;
        private final String regex;
        private final List<Builder> childRuntimeResourceBuilders;

        public Builder(List<Resource> resources, List<Builder> childRuntimeResourceBuilders, String regex2) {
            this.childRuntimeResourceBuilders = childRuntimeResourceBuilders;
            this.resources = resources;
            this.regex = regex2;
        }

        public RuntimeResource build(RuntimeResource parent) {
            return new RuntimeResource(this.resources, this.childRuntimeResourceBuilders, parent, this.regex);
        }
    }
}

