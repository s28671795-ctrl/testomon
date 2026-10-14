/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Criteria;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.Variable;

class Collector
implements Criteria {
    private final Registry registry = new Registry();
    private final Registry alias = new Registry();

    @Override
    public Variable get(Object key) {
        return (Variable)this.registry.get(key);
    }

    @Override
    public Variable get(Label label) throws Exception {
        if (label != null) {
            Object key = label.getKey();
            return (Variable)this.registry.get(key);
        }
        return null;
    }

    @Override
    public Variable resolve(String path) {
        return (Variable)this.alias.get(path);
    }

    @Override
    public Variable remove(Object key) throws Exception {
        return (Variable)this.registry.remove(key);
    }

    @Override
    public Iterator<Object> iterator() {
        return this.registry.iterator();
    }

    @Override
    public void set(Label label, Object value) throws Exception {
        Variable variable = new Variable(label, value);
        if (label != null) {
            String[] paths = label.getPaths();
            Object key = label.getKey();
            for (String path : paths) {
                this.alias.put(path, variable);
            }
            this.registry.put(key, variable);
        }
    }

    @Override
    public void commit(Object source2) throws Exception {
        Collection set = this.registry.values();
        for (Variable entry : set) {
            Contact contact = entry.getContact();
            Object value = entry.getValue();
            contact.set(source2, value);
        }
    }

    private static class Registry
    extends LinkedHashMap<Object, Variable> {
        private Registry() {
        }

        public Iterator<Object> iterator() {
            return this.keySet().iterator();
        }
    }
}

