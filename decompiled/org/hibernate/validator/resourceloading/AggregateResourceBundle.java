/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.resourceloading;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.hibernate.validator.internal.util.CollectionHelper;

class AggregateResourceBundle
extends ResourceBundle {
    private final Map<String, Object> contents;

    AggregateResourceBundle(List<ResourceBundle> bundles) {
        if (bundles == null || bundles.isEmpty()) {
            this.contents = Collections.emptyMap();
            return;
        }
        HashMap<String, Object> contents = new HashMap<String, Object>();
        for (ResourceBundle bundle2 : bundles) {
            Enumeration<String> keys2 = bundle2.getKeys();
            while (keys2.hasMoreElements()) {
                String key = keys2.nextElement();
                contents.putIfAbsent(key, bundle2.getObject(key));
            }
        }
        this.contents = CollectionHelper.toImmutableMap(contents);
    }

    @Override
    protected Object handleGetObject(String key) {
        return this.contents.get(key);
    }

    @Override
    protected Set<String> handleKeySet() {
        return this.contents.keySet();
    }

    @Override
    public Enumeration<String> getKeys() {
        if (this.parent == null) {
            return Collections.enumeration(this.contents.keySet());
        }
        HashSet<String> keySet = CollectionHelper.newHashSet(this.contents.keySet());
        keySet.addAll(Collections.list(this.parent.getKeys()));
        return Collections.enumeration(keySet);
    }
}

