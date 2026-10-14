/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingSignatureTrustEngine
implements SignatureTrustEngine {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ChainingSignatureTrustEngine.class);
    @Nonnull
    @NonnullElements
    private List<SignatureTrustEngine> engines;

    public ChainingSignatureTrustEngine(@Nonnull @ParameterName(name="chain") @NonnullElements List<SignatureTrustEngine> chain) {
        Constraint.isNotNull(chain, "SignatureTrustEngine list cannot be null");
        this.engines = new ArrayList<SignatureTrustEngine>(Collections2.filter(chain, Predicates.notNull()));
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<SignatureTrustEngine> getChain() {
        return ImmutableList.copyOf(this.engines);
    }

    @Override
    @Nullable
    public KeyInfoCredentialResolver getKeyInfoResolver() {
        return null;
    }

    @Override
    public boolean validate(@Nonnull Signature token, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        for (SignatureTrustEngine engine : this.engines) {
            if (!engine.validate(token, trustBasisCriteria)) continue;
            this.log.debug("Signature was trusted by chain member: {}", (Object)engine.getClass().getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nullable CriteriaSet trustBasisCriteria, @Nonnull Credential candidateCredential) throws SecurityException {
        for (SignatureTrustEngine engine : this.engines) {
            if (!engine.validate(signature, content, algorithmURI, trustBasisCriteria, candidateCredential)) continue;
            this.log.debug("Signature was trusted by chain member: {}", (Object)engine.getClass().getName());
            return true;
        }
        return false;
    }
}

