/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509SubjectNameCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableX509SubjectNameCredentialCriterion
extends AbstractTriStatePredicate<Credential>
implements EvaluableCredentialCriterion {
    private final Logger log = LoggerFactory.getLogger(EvaluableX509SubjectNameCredentialCriterion.class);
    private final X500Principal subjectName;

    public EvaluableX509SubjectNameCredentialCriterion(@Nonnull X509SubjectNameCriterion criteria) {
        this.subjectName = Constraint.isNotNull(criteria, "Criterion instance cannot be null").getSubjectName();
    }

    public EvaluableX509SubjectNameCredentialCriterion(@Nonnull X500Principal newSubjectName) {
        this.subjectName = Constraint.isNotNull(newSubjectName, "Subject name cannot be null");
    }

    @Override
    @Nullable
    public boolean apply(@Nullable Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return this.isNullInputSatisfies();
        }
        if (!(target instanceof X509Credential)) {
            this.log.info("Credential is not an X509Credential, does not satisfy subject name criteria");
            return false;
        }
        X509Certificate entityCert = ((X509Credential)target).getEntityCertificate();
        if (entityCert == null) {
            this.log.info("X509Credential did not contain an entity certificate, does not satisfy criteria");
            return false;
        }
        return this.subjectName.equals(entityCert.getSubjectX500Principal());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvaluableX509SubjectNameCredentialCriterion [subjectName=");
        builder.append(this.subjectName.getName());
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.subjectName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EvaluableX509SubjectNameCredentialCriterion) {
            return this.subjectName.equals(((EvaluableX509SubjectNameCredentialCriterion)obj).subjectName);
        }
        return false;
    }
}

