/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.opensaml.security.criteria.UsageCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableUsageCredentialCriterion
extends AbstractTriStatePredicate<Credential>
implements EvaluableCredentialCriterion {
    private final Logger log = LoggerFactory.getLogger(EvaluableUsageCredentialCriterion.class);
    private final UsageType usage;

    public EvaluableUsageCredentialCriterion(@Nonnull UsageCriterion criteria) {
        this.usage = Constraint.isNotNull(criteria, "Criterion instance cannot be null").getUsage();
    }

    public EvaluableUsageCredentialCriterion(@Nonnull UsageType newUsage) {
        this.usage = Constraint.isNotNull(newUsage, "Usage cannot be null");
    }

    @Override
    @Nullable
    public boolean apply(@Nullable Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return this.isNullInputSatisfies();
        }
        UsageType credUsage = target.getUsageType();
        if (credUsage == null) {
            this.log.info("Could not evaluate criteria, credential contained no usage specifier");
            return this.isUnevaluableSatisfies();
        }
        return this.matchUsage(credUsage, this.usage);
    }

    protected boolean matchUsage(@Nonnull UsageType credentialUsage, @Nonnull UsageType criteriaUsage) {
        if (credentialUsage == UsageType.UNSPECIFIED || criteriaUsage == UsageType.UNSPECIFIED) {
            return true;
        }
        return credentialUsage == criteriaUsage;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvaluableUsageCredentialCriterion [usage=");
        builder.append((Object)this.usage);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.usage.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EvaluableUsageCredentialCriterion) {
            return this.usage.equals((Object)((EvaluableUsageCredentialCriterion)obj).usage);
        }
        return false;
    }
}

