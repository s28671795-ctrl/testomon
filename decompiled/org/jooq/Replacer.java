/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.Pro;
import org.jooq.QueryPart;

@ApiStatus.Experimental
@Pro
public interface Replacer {
    public static Replacer of(Function<? super QueryPart, ? extends QueryPart> replacer) {
        return Replacer.of(q -> true, replacer);
    }

    public static Replacer of(final Predicate<? super QueryPart> recurse, final Function<? super QueryPart, ? extends QueryPart> replacer) {
        return new Replacer(){

            @Override
            public Predicate<QueryPart> recurse() {
                return recurse;
            }

            @Override
            public Function<QueryPart, QueryPart> replacer() {
                return replacer;
            }
        };
    }

    public Predicate<QueryPart> recurse();

    public Function<QueryPart, QueryPart> replacer();
}

