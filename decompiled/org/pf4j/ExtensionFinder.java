/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.List;
import java.util.Set;
import org.pf4j.ExtensionWrapper;

public interface ExtensionFinder {
    public <T> List<ExtensionWrapper<T>> find(Class<T> var1);

    public <T> List<ExtensionWrapper<T>> find(Class<T> var1, String var2);

    public List<ExtensionWrapper> find(String var1);

    public Set<String> findClassNames(String var1);
}

