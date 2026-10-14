/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.core;

import org.hibernate.validator.internal.properties.Constrainable;

public interface AnnotationProcessingOptions {
    public boolean areClassLevelConstraintsIgnoredFor(Class<?> var1);

    public boolean areMemberConstraintsIgnoredFor(Constrainable var1);

    public boolean areReturnValueConstraintsIgnoredFor(Constrainable var1);

    public boolean areCrossParameterConstraintsIgnoredFor(Constrainable var1);

    public boolean areParameterConstraintsIgnoredFor(Constrainable var1, int var2);

    public void merge(AnnotationProcessingOptions var1);
}

