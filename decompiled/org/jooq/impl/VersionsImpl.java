/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jooq.Version;
import org.jooq.Versions;

final class VersionsImpl
implements Versions {
    final Version root;
    final Map<String, Version> versions;

    VersionsImpl(Version root) {
        this.root = root;
        this.versions = new HashMap<String, Version>();
        this.add(root);
    }

    void add(Version version) {
        this.versions.put(version.id(), version);
    }

    void addAll(Collection<? extends Version> v) {
        for (Version version : v) {
            this.add(version);
        }
    }

    void remove(Version version) {
        this.versions.remove(version.id());
    }

    @Override
    public final Version root() {
        return this.root;
    }

    @Override
    public final Version get(String id) {
        return this.versions.get(id);
    }

    @Override
    public final Iterator<Version> iterator() {
        return this.versions.values().iterator();
    }
}

