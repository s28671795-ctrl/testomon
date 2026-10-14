/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ElementTypesAreNonnullByDefault;
import com.google.common.collect.ParametricNullness;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible
final class NullnessCasts {
    @ParametricNullness
    static <T> T uncheckedCastNullableTToT(@CheckForNull T t2) {
        return t2;
    }

    @ParametricNullness
    static <T> T unsafeNull() {
        return null;
    }

    private NullnessCasts() {
    }
}

