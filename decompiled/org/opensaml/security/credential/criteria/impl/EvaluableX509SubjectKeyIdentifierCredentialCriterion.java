/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509SubjectKeyIdentifierCriterion;
import org.opensaml.security.x509.X509Support;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableX509SubjectKeyIdentifierCredentialCriterion
extends AbstractTriStatePredicate<Credential>
implements EvaluableCredentialCriterion {
    private final Logger log = LoggerFactory.getLogger(EvaluableX509SubjectKeyIdentifierCredentialCriterion.class);
    private final byte[] ski;

    public EvaluableX509SubjectKeyIdentifierCredentialCriterion(@Nonnull X509SubjectKeyIdentifierCriterion criteria) {
        this.ski = Constraint.isNotNull(criteria, "Criterion instance cannot be null").getSubjectKeyIdentifier();
    }

    public EvaluableX509SubjectKeyIdentifierCredentialCriterion(@Nonnull byte[] newSKI) {
        this.ski = Constraint.isNotEmpty(newSKI, "Subject key identifier cannot be null or empty");
    }

    @Override
    @Nullable
    public boolean apply(@Nullable Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return this.isNullInputSatisfies();
        }
        if (!(target instanceof X509Credential)) {
            this.log.info("Credential is not an X509Credential, does not satisfy subject key identifier criteria");
            return false;
        }
        X509Certificate entityCert = ((X509Credential)target).getEntityCertificate();
        if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return false;
        }
        byte[] credSKI = X509Support.getSubjectKeyIdentifier(entityCert);
        if (credSKI == null || credSKI.length == 0) {
            this.log.info("Could not evaluate criteria, certificate contained no subject key identifier extension");
            return this.isUnevaluableSatisfies();
        }
        return Arrays.equals(this.ski, credSKI);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvaluableX509SubjectKeyIdentifierCredentialCriterion [ski=");
        builder.append(Hex.encodeHexString(this.ski));
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.ski.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EvaluableX509SubjectKeyIdentifierCredentialCriterion) {
            return this.ski.equals(((EvaluableX509SubjectKeyIdentifierCredentialCriterion)obj).ski);
        }
        return false;
    }
}

