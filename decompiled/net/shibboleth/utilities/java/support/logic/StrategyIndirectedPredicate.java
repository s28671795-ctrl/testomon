/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class StrategyIndirectedPredicate<T1, T2>
implements Predicate<T1> {
    @Nonnull
    private final Function<T1, T2> objectLookupStrategy;
    @Nonnull
    private final Predicate<T2> predicate;

    public StrategyIndirectedPredicate(@Nonnull Function<T1, T2> objectStrategy, @Nonnull Predicate<T2> pred) {
        this.objectLookupStrategy = Constraint.isNotNull(objectStrategy, "Object lookup strategy cannot be null");
        this.predicate = Constraint.isNotNull(pred, "Predicate cannot be null");
    }

    public StrategyIndirectedPredicate(@Nonnull Function<T1, T2> objectStrategy, @Nonnull Collection<T2> collection) {
        this.objectLookupStrategy = Constraint.isNotNull(objectStrategy, "Object lookup strategy cannot be null");
        this.predicate = Predicates.in(collection);
    }

    @Override
    public boolean apply(@Nullable T1 input) {
        return this.predicate.apply(this.objectLookupStrategy.apply(input));
    }

    @Nonnull
    public static StrategyIndirectedPredicate forPredicate(@Nonnull Function objectStrategy, @Nonnull Predicate pred) {
        return new StrategyIndirectedPredicate(objectStrategy, pred);
    }

    @Nonnull
    public static StrategyIndirectedPredicate forCollection(@Nonnull Function objectStrategy, @Nonnull Collection collection) {
        return new StrategyIndirectedPredicate(objectStrategy, collection);
    }
}

