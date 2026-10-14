/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class AnyMatchPredicate<T>
implements Predicate<Iterable<T>> {
    private final Predicate<T> predicate;

    public AnyMatchPredicate(@Nonnull Predicate<T> target) {
        this.predicate = Constraint.isNotNull(target, "Target predicate can not be null");
    }

    @Override
    public boolean apply(@Nullable Iterable<T> inputs) {
        if (inputs == null) {
            return false;
        }
        for (T input : inputs) {
            if (!this.predicate.apply(input)) continue;
            return true;
        }
        return false;
    }
}

