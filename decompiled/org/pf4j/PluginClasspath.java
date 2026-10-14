/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PluginClasspath {
    private Set<String> classesDirectories = new HashSet<String>();
    private Set<String> jarsDirectories = new HashSet<String>();

    public Set<String> getClassesDirectories() {
        return this.classesDirectories;
    }

    public PluginClasspath addClassesDirectories(String ... classesDirectories) {
        return this.addClassesDirectories(Arrays.asList(classesDirectories));
    }

    public PluginClasspath addClassesDirectories(Collection<String> classesDirectories) {
        this.classesDirectories.addAll(classesDirectories);
        return this;
    }

    public Set<String> getJarsDirectories() {
        return this.jarsDirectories;
    }

    public PluginClasspath addJarsDirectories(String ... jarsDirectories) {
        return this.addJarsDirectories(Arrays.asList(jarsDirectories));
    }

    public PluginClasspath addJarsDirectories(Collection<String> jarsDirectories) {
        this.jarsDirectories.addAll(jarsDirectories);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PluginClasspath)) {
            return false;
        }
        PluginClasspath that = (PluginClasspath)o;
        return this.classesDirectories.equals(that.classesDirectories) && this.jarsDirectories.equals(that.jarsDirectories);
    }

    public int hashCode() {
        return Objects.hash(this.classesDirectories, this.jarsDirectories);
    }
}

