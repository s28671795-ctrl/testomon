/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.ElementTypesAreNonnullByDefault;
import com.google.common.base.ParametricNullness;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible
final class NullnessCasts {
    @ParametricNullness
    static <T> T uncheckedCastNullableTToT(@CheckForNull T t2) {
        return t2;
    }

    private NullnessCasts() {
    }
}

