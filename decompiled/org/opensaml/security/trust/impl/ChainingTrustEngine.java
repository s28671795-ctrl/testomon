/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.trust.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.security.SecurityException;
import org.opensaml.security.trust.TrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingTrustEngine<TokenType>
implements TrustEngine<TokenType> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ChainingTrustEngine.class);
    @Nonnull
    @NonnullElements
    private List<TrustEngine<? super TokenType>> engines;

    public ChainingTrustEngine(@Nonnull List<TrustEngine<? super TokenType>> chain) {
        Constraint.isNotNull(chain, "TrustEngine list cannot be null");
        this.engines = new ArrayList<TrustEngine<TokenType>>(Collections2.filter(chain, Predicates.notNull()));
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<TrustEngine<? super TokenType>> getChain() {
        return ImmutableList.copyOf(this.engines);
    }

    @Override
    public boolean validate(@Nonnull TokenType token, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        for (TrustEngine<TokenType> engine : this.engines) {
            if (!engine.validate(token, trustBasisCriteria)) continue;
            this.log.debug("Token was trusted by chain member: {}", (Object)engine.getClass().getName());
            return true;
        }
        return false;
    }
}

