/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.resolver;

import java.lang.annotation.ElementType;
import javax.validation.Path;
import javax.validation.TraversableResolver;

class TraverseAllTraversableResolver
implements TraversableResolver {
    TraverseAllTraversableResolver() {
    }

    @Override
    public boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
        return true;
    }

    @Override
    public boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
        return true;
    }
}

