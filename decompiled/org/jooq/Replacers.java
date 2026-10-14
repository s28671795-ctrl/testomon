/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.impl.Internal;

@ApiStatus.Experimental
@Pro
public final class Replacers {
    @NotNull
    public static final Replacer decomposing() {
        return Internal.decomposing();
    }

    @NotNull
    public static final Replacer listening(Replacer replacer, BiConsumer<? super QueryPart, ? super QueryPart> replacement) {
        return Replacer.of(replacer.recurse(), p -> {
            QueryPart q = replacer.replacer().apply((QueryPart)p);
            if (p != q) {
                replacement.accept((QueryPart)p, q);
            }
            return q;
        });
    }

    private Replacers() {
    }
}

