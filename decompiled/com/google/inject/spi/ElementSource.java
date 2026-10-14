/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Preconditions;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;
import com.google.inject.spi.ModuleSource;
import java.util.List;
import javax.annotation.Nullable;

public final class ElementSource {
    final ElementSource originalElementSource;
    final boolean trustedOriginalElementSource;
    final ModuleSource moduleSource;
    final Object declaringSource;
    final ModuleAnnotatedMethodScanner scanner;

    ElementSource(@Nullable ElementSource originalSource, boolean trustedOriginalSource, Object declaringSource, ModuleSource moduleSource, ModuleAnnotatedMethodScanner scanner) {
        Preconditions.checkNotNull(declaringSource, "declaringSource cannot be null.");
        Preconditions.checkNotNull(moduleSource, "moduleSource cannot be null.");
        this.originalElementSource = originalSource;
        this.trustedOriginalElementSource = trustedOriginalSource;
        this.declaringSource = declaringSource;
        this.moduleSource = moduleSource;
        this.scanner = scanner;
    }

    public ElementSource getOriginalElementSource() {
        return this.originalElementSource;
    }

    public Object getDeclaringSource() {
        return this.declaringSource;
    }

    public List<String> getModuleClassNames() {
        return this.moduleSource.getModuleClassNames();
    }

    public String toString() {
        return this.getDeclaringSource().toString();
    }
}

