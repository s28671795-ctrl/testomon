/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.AllMatchPredicate;
import net.shibboleth.utilities.java.support.logic.AnyMatchPredicate;
import net.shibboleth.utilities.java.support.logic.CaseInsensitiveStringMatchPredicate;

public final class PredicateSupport {
    private PredicateSupport() {
    }

    @Nonnull
    public static <T> Predicate<Iterable<T>> allMatch(@Nonnull Predicate<T> target) {
        return new AllMatchPredicate<T>(target);
    }

    @Nonnull
    public static <T> Predicate<Iterable<T>> anyMatch(@Nonnull Predicate<T> target) {
        return new AnyMatchPredicate<T>(target);
    }

    @Nonnull
    public static Predicate<CharSequence> caseInsensitiveMatch(@Nonnull String target) {
        return new CaseInsensitiveStringMatchPredicate(target);
    }
}

