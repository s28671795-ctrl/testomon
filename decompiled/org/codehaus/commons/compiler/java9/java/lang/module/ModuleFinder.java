/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.java9.java.lang.module;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.commons.compiler.java9.java.lang.module.ModuleReference;
import org.codehaus.commons.compiler.util.reflect.Classes;
import org.codehaus.commons.compiler.util.reflect.Methods;

public final class ModuleFinder {
    private static final Class<?> CLASS = Classes.load("java.lang.module.ModuleFinder");
    private static final Method METHOD_ofSystem = Classes.getDeclaredMethod(CLASS, "ofSystem", new Class[0]);
    private static final Method METHOD_findAll = Classes.getDeclaredMethod(CLASS, "findAll", new Class[0]);
    private final Object delegate;

    private ModuleFinder(Object delegate) {
        this.delegate = delegate;
    }

    public static ModuleFinder ofSystem() {
        return new ModuleFinder(Methods.invoke(METHOD_ofSystem, null, new Object[0]));
    }

    public Set<ModuleReference> findAll() {
        return ModuleFinder.wrapModuleReferences((Set)Methods.invoke(METHOD_findAll, this.delegate, new Object[0]), new HashSet());
    }

    private static <C extends Collection<ModuleReference>> C wrapModuleReferences(Collection<?> moduleReferences, C result) {
        for (Object mref : moduleReferences) {
            result.add((ModuleReference)new ModuleReference(mref));
        }
        return result;
    }
}

