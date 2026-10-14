/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Model;
import org.simpleframework.xml.core.ModelList;
import org.simpleframework.xml.core.PathException;

class ModelMap
extends LinkedHashMap<String, ModelList>
implements Iterable<ModelList> {
    private final Detail detail;

    public ModelMap(Detail detail) {
        this.detail = detail;
    }

    public ModelMap getModels() throws Exception {
        ModelMap map = new ModelMap(this.detail);
        for (String name : this.keySet()) {
            ModelList list = (ModelList)this.get(name);
            if (list != null) {
                list = list.build();
            }
            if (map.containsKey(name)) {
                throw new PathException("Path with name '%s' is a duplicate in %s ", name, this.detail);
            }
            map.put(name, list);
        }
        return map;
    }

    public Model lookup(String name, int index) {
        ModelList list = (ModelList)this.get(name);
        if (list != null) {
            return list.lookup(index);
        }
        return null;
    }

    public void register(String name, Model model) {
        ModelList list = (ModelList)this.get(name);
        if (list == null) {
            list = new ModelList();
            this.put(name, list);
        }
        list.register(model);
    }

    @Override
    public Iterator<ModelList> iterator() {
        return this.values().iterator();
    }
}

