/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.RuntimeResource;
import org.glassfish.jersey.uri.PathTemplate;

public class RuntimeResourceModel {
    private final List<RuntimeResource> runtimeResources = new ArrayList<RuntimeResource>();

    public RuntimeResourceModel(List<Resource> resources) {
        for (RuntimeResource.Builder builder : this.getRuntimeResources(resources)) {
            this.runtimeResources.add(builder.build(null));
        }
        Collections.sort(this.runtimeResources, RuntimeResource.COMPARATOR);
    }

    private List<RuntimeResource.Builder> getRuntimeResources(List<Resource> resources) {
        HashMap<String, ArrayList<Resource>> regexMap = new HashMap<String, ArrayList<Resource>>();
        for (Resource resource : resources) {
            ArrayList<Resource> listFromMap;
            String path = resource.getPath();
            String regex2 = null;
            if (path != null) {
                if (path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }
                regex2 = new PathTemplate(path).getPattern().getRegex();
            }
            if ((listFromMap = (ArrayList<Resource>)regexMap.get(regex2)) == null) {
                listFromMap = new ArrayList<Resource>();
                regexMap.put(regex2, listFromMap);
            }
            listFromMap.add(resource);
        }
        ArrayList<RuntimeResource.Builder> runtimeResources = new ArrayList<RuntimeResource.Builder>();
        for (Map.Entry entry : regexMap.entrySet()) {
            List resourcesWithSameRegex = (List)entry.getValue();
            ArrayList<Resource> childResources = new ArrayList<Resource>();
            for (Resource res : resourcesWithSameRegex) {
                childResources.addAll(res.getChildResources());
            }
            List<RuntimeResource.Builder> childRuntimeResources = this.getRuntimeResources(childResources);
            runtimeResources.add(new RuntimeResource.Builder(resourcesWithSameRegex, childRuntimeResources, (String)entry.getKey()));
        }
        return runtimeResources;
    }

    public List<RuntimeResource> getRuntimeResources() {
        return this.runtimeResources;
    }
}

