/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableEntityIDCredentialCriterion
extends AbstractTriStatePredicate<Credential>
implements EvaluableCredentialCriterion {
    private final Logger log = LoggerFactory.getLogger(EvaluableEntityIDCredentialCriterion.class);
    private final String entityID;

    public EvaluableEntityIDCredentialCriterion(@Nonnull EntityIdCriterion criteria) {
        this.entityID = Constraint.isNotNull(criteria, "Criterion instance may not be null").getEntityId();
    }

    public EvaluableEntityIDCredentialCriterion(@Nonnull String entity) {
        String trimmed = StringSupport.trimOrNull(entity);
        Constraint.isNotNull(trimmed, "EntityID criteria cannot be null or empty");
        this.entityID = trimmed;
    }

    @Override
    @Nullable
    public boolean apply(@Nullable Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return this.isNullInputSatisfies();
        }
        if (Strings.isNullOrEmpty(target.getEntityId())) {
            this.log.info("Could not evaluate criteria, credential contained no entityID");
            return this.isUnevaluableSatisfies();
        }
        return this.entityID.equals(target.getEntityId());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvaluableEntityIDCredentialCriterion [entityID=");
        builder.append(this.entityID);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.entityID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EvaluableEntityIDCredentialCriterion) {
            return this.entityID.equals(((EvaluableEntityIDCredentialCriterion)obj).entityID);
        }
        return false;
    }
}

