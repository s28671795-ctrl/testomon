/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.Policy;

class LabelMap
extends LinkedHashMap<String, Label>
implements Iterable<Label> {
    private final Policy policy;

    public LabelMap() {
        this((Policy)null);
    }

    public LabelMap(Policy policy) {
        this.policy = policy;
    }

    @Override
    public Iterator<Label> iterator() {
        return this.values().iterator();
    }

    public Label getLabel(String name) {
        return (Label)this.remove(name);
    }

    public String[] getKeys() throws Exception {
        HashSet<String> list = new HashSet<String>();
        for (Label label : this) {
            if (label == null) continue;
            String path = label.getPath();
            String name = label.getName();
            list.add(path);
            list.add(name);
        }
        return this.getArray(list);
    }

    public String[] getPaths() throws Exception {
        HashSet<String> list = new HashSet<String>();
        for (Label label : this) {
            if (label == null) continue;
            String path = label.getPath();
            list.add(path);
        }
        return this.getArray(list);
    }

    public LabelMap getLabels() throws Exception {
        LabelMap map = new LabelMap(this.policy);
        for (Label label : this) {
            if (label == null) continue;
            String name = label.getPath();
            map.put(name, label);
        }
        return map;
    }

    private String[] getArray(Set<String> list) {
        return list.toArray(new String[0]);
    }

    public boolean isStrict(Context context) {
        if (this.policy == null) {
            return context.isStrict();
        }
        return context.isStrict() && this.policy.isStrict();
    }
}

