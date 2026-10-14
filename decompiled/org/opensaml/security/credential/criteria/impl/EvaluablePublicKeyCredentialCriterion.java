/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

import java.security.PublicKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.opensaml.security.criteria.PublicKeyCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluablePublicKeyCredentialCriterion
extends AbstractTriStatePredicate<Credential>
implements EvaluableCredentialCriterion {
    private final Logger log = LoggerFactory.getLogger(EvaluablePublicKeyCredentialCriterion.class);
    private final PublicKey publicKey;

    public EvaluablePublicKeyCredentialCriterion(@Nonnull PublicKeyCriterion criteria) {
        this.publicKey = Constraint.isNotNull(criteria, "Criterion instance cannot be null").getPublicKey();
    }

    public EvaluablePublicKeyCredentialCriterion(@Nonnull PublicKey newPublicKey) {
        this.publicKey = Constraint.isNotNull(newPublicKey, "Public key cannot be null");
    }

    @Override
    @Nullable
    public boolean apply(@Nullable Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return this.isNullInputSatisfies();
        }
        PublicKey key = target.getPublicKey();
        if (key == null) {
            this.log.info("Credential contained no public key, does not satisfy public key criteria");
            return false;
        }
        return this.publicKey.equals(key);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvaluablePublicKeyCredentialCriterion [publicKey=");
        builder.append(this.publicKey);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.publicKey.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EvaluablePublicKeyCredentialCriterion) {
            return this.publicKey.equals(((EvaluablePublicKeyCredentialCriterion)obj).publicKey);
        }
        return false;
    }
}

