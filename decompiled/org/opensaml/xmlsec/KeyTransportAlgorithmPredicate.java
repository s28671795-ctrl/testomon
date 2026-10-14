/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;

public interface KeyTransportAlgorithmPredicate
extends Predicate<SelectionInput> {

    public static final class SelectionInput {
        private String keyTransportAlgorithm;
        private String dataEncryptionAlgorithm;
        private Credential keyTransportCredential;

        public SelectionInput(@Nonnull String keyTransportAlgorithmCandidate, @Nullable String dataEncryptionAlgorithmCandidate, @Nullable Credential keyTransportCredentialCandidate) {
            this.keyTransportAlgorithm = Constraint.isNotNull(keyTransportAlgorithmCandidate, "Key transport algorithm candidate was not supplied");
            this.dataEncryptionAlgorithm = dataEncryptionAlgorithmCandidate;
            this.keyTransportCredential = keyTransportCredentialCandidate;
        }

        @Nonnull
        public String getKeyTransportAlgorithm() {
            return this.keyTransportAlgorithm;
        }

        @Nullable
        public String getDataEncryptionAlgorithm() {
            return this.dataEncryptionAlgorithm;
        }

        @Nullable
        public Credential getKeyTransportCredential() {
            return this.keyTransportCredential;
        }
    }
}

