/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.resolver;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

public final class ResolverSupport {
    private ResolverSupport() {
    }

    @Nonnull
    public static <T, E extends Predicate<T>> Set<Predicate<T>> getPredicates(@Nullable CriteriaSet criteriaSet, @Nullable Class<E> predicateCriterionType, @Nullable CriterionPredicateRegistry<T> registry) throws ResolverException {
        if (criteriaSet == null) {
            return Collections.emptySet();
        }
        HashSet<Predicate<T>> predicates = new HashSet<Predicate<T>>(criteriaSet.size());
        for (Criterion criterion : criteriaSet) {
            Predicate<T> predicate;
            if (predicateCriterionType != null && predicateCriterionType.isInstance(criterion)) {
                predicates.add((Predicate<T>)predicateCriterionType.cast(criterion));
                continue;
            }
            if (registry == null || (predicate = registry.getPredicate(criterion)) == null) continue;
            predicates.add(predicate);
        }
        return predicates;
    }

    @Nonnull
    public static <T> Iterable<T> getFilteredIterable(@Nullable Iterable<T> candidates, @Nullable Set<Predicate<T>> predicates, boolean satisfyAny, boolean onEmptyPredicatesReturnEmpty) {
        if (candidates == null || !candidates.iterator().hasNext()) {
            return Collections.emptySet();
        }
        if (predicates == null || predicates.isEmpty()) {
            if (onEmptyPredicatesReturnEmpty) {
                return Collections.emptySet();
            }
            return candidates;
        }
        Predicate<T> predicate = satisfyAny ? Predicates.or(predicates) : Predicates.and(predicates);
        return Iterables.filter(candidates, predicate);
    }
}

