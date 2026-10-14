/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.impl;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationInformationResolver;
import org.opensaml.security.x509.TrustedNamesCriterion;

public class StaticPKIXValidationInformationResolver
implements PKIXValidationInformationResolver {
    private final List<PKIXValidationInformation> pkixInfo;
    private final Set<String> trustedNames;
    private boolean supportDynamicTrustedNames;

    public StaticPKIXValidationInformationResolver(@Nullable List<PKIXValidationInformation> info, @Nullable Set<String> names) {
        this(info, names, false);
    }

    public StaticPKIXValidationInformationResolver(@Nullable List<PKIXValidationInformation> info, @Nullable Set<String> names, boolean supportDynamicNames) {
        this.pkixInfo = info != null ? new ArrayList<PKIXValidationInformation>(info) : Collections.EMPTY_LIST;
        this.trustedNames = names != null ? new HashSet<String>(names) : Collections.EMPTY_SET;
        this.supportDynamicTrustedNames = supportDynamicNames;
    }

    @Override
    @Nonnull
    public Set<String> resolveTrustedNames(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        TrustedNamesCriterion trustedNamesCriterion;
        if (criteriaSet == null) {
            return ImmutableSet.copyOf(this.trustedNames);
        }
        HashSet<String> temp = new HashSet<String>(this.trustedNames);
        EntityIdCriterion entityIDCriterion = criteriaSet.get(EntityIdCriterion.class);
        if (entityIDCriterion != null) {
            temp.add(entityIDCriterion.getEntityId());
        }
        if (this.supportDynamicTrustedNames && (trustedNamesCriterion = criteriaSet.get(TrustedNamesCriterion.class)) != null) {
            temp.addAll(trustedNamesCriterion.getTrustedNames());
        }
        return ImmutableSet.copyOf(temp);
    }

    @Override
    public boolean supportsTrustedNameResolution() {
        return true;
    }

    @Override
    @Nonnull
    public Iterable<PKIXValidationInformation> resolve(@Nullable CriteriaSet criteria) throws ResolverException {
        return this.pkixInfo;
    }

    @Override
    @Nullable
    public PKIXValidationInformation resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
        if (!this.pkixInfo.isEmpty()) {
            return this.pkixInfo.get(0);
        }
        return null;
    }
}

