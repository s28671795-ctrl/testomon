/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.spi.BindingSourceRestriction;
import java.util.List;
import javax.annotation.Nullable;

final class ModuleSource {
    private final String moduleClassName;
    private final ModuleSource parent;
    private final BindingSourceRestriction.PermitMap permitMap;

    ModuleSource(Class<?> moduleClass, BindingSourceRestriction.PermitMap permitMap) {
        this(null, moduleClass, permitMap);
    }

    private ModuleSource(@Nullable ModuleSource parent, Class<?> moduleClass, BindingSourceRestriction.PermitMap permitMap) {
        Preconditions.checkNotNull(moduleClass, "module cannot be null.");
        this.parent = parent;
        this.moduleClassName = moduleClass.getName();
        this.permitMap = permitMap;
    }

    String getModuleClassName() {
        return this.moduleClassName;
    }

    ModuleSource createChild(Class<?> moduleClass) {
        return new ModuleSource(this, moduleClass, this.permitMap);
    }

    ModuleSource getParent() {
        return this.parent;
    }

    List<String> getModuleClassNames() {
        ImmutableList.Builder classNames = ImmutableList.builder();
        ModuleSource current = this;
        while (current != null) {
            String className = current.moduleClassName;
            classNames.add(className);
            current = current.parent;
        }
        return classNames.build();
    }

    int size() {
        if (this.parent == null) {
            return 1;
        }
        return this.parent.size() + 1;
    }

    BindingSourceRestriction.PermitMap getPermitMap() {
        return this.permitMap;
    }
}

