/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.simpleframework.xml.core.Parameter;

class ParameterMap
extends LinkedHashMap<Object, Parameter>
implements Iterable<Parameter> {
    @Override
    public Iterator<Parameter> iterator() {
        return this.values().iterator();
    }

    public Parameter get(int ordinal) {
        return this.getAll().get(ordinal);
    }

    public List<Parameter> getAll() {
        Collection list = this.values();
        if (!list.isEmpty()) {
            return new ArrayList<Parameter>(list);
        }
        return Collections.emptyList();
    }
}

