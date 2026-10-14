/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

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
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriteriaRegistry;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.opensaml.security.credential.impl.AbstractCredentialResolver;

public abstract class AbstractCriteriaFilteringCredentialResolver
extends AbstractCredentialResolver {
    private boolean satisfyAllPredicates = true;

    @Override
    @Nonnull
    public Iterable<Credential> resolve(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        Iterable<Credential> storeCandidates = this.resolveFromSource(criteriaSet);
        Set<Predicate<Credential>> predicates = this.getPredicates(criteriaSet);
        if (predicates.isEmpty()) {
            return storeCandidates;
        }
        Predicate<Credential> aggregatePredicate = null;
        aggregatePredicate = this.isSatisfyAllPredicates() ? Predicates.and(predicates) : Predicates.or(predicates);
        return Iterables.filter(storeCandidates, aggregatePredicate);
    }

    public boolean isSatisfyAllPredicates() {
        return this.satisfyAllPredicates;
    }

    public void setSatisfyAllPredicates(boolean flag) {
        this.satisfyAllPredicates = flag;
    }

    @Nonnull
    protected abstract Iterable<Credential> resolveFromSource(@Nullable CriteriaSet var1) throws ResolverException;

    private Set<Predicate<Credential>> getPredicates(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        if (criteriaSet == null) {
            return Collections.emptySet();
        }
        HashSet<Predicate<Credential>> predicates = new HashSet<Predicate<Credential>>(criteriaSet.size());
        for (Criterion criteria : criteriaSet) {
            EvaluableCredentialCriterion evaluableCriteria;
            if (criteria instanceof EvaluableCredentialCriterion) {
                predicates.add((EvaluableCredentialCriterion)criteria);
                continue;
            }
            try {
                evaluableCriteria = EvaluableCredentialCriteriaRegistry.getEvaluator(criteria);
            }
            catch (SecurityException e) {
                throw new ResolverException("Exception obtaining EvaluableCredentialCriterion", e);
            }
            if (evaluableCriteria == null) continue;
            predicates.add(evaluableCriteria);
        }
        return predicates;
    }
}

