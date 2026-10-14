/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.security.messaging.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.trust.TrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTrustEngineSecurityHandler<TokenType>
extends AbstractMessageHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(BaseTrustEngineSecurityHandler.class);
    @Nullable
    private TrustEngine<? super TokenType> trustEngine;

    @Nullable
    protected TrustEngine<? super TokenType> getTrustEngine() {
        return this.trustEngine;
    }

    protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        if (!super.doPreInvoke(messageContext)) {
            return false;
        }
        TrustEngine<TokenType> engine = this.resolveTrustEngine(messageContext);
        if (engine == null) {
            throw new MessageHandlerException("TrustEngine could not be resolved from MessageContext");
        }
        this.trustEngine = engine;
        return true;
    }

    @Nullable
    protected abstract TrustEngine<? super TokenType> resolveTrustEngine(@Nonnull MessageContext var1);

    @Nullable
    protected abstract CriteriaSet buildCriteriaSet(@Nullable String var1, @Nonnull MessageContext var2) throws MessageHandlerException;

    protected boolean evaluate(@Nonnull TokenType token, @Nullable String entityID, @Nonnull MessageContext messageContext) throws MessageHandlerException {
        CriteriaSet criteriaSet = this.buildCriteriaSet(entityID, messageContext);
        if (criteriaSet == null) {
            this.log.error("{} Returned criteria set was null, can not perform trust engine evaluation of token", (Object)this.getLogPrefix());
            throw new MessageHandlerException("Returned criteria set was null");
        }
        return this.evaluate(token, criteriaSet);
    }

    protected boolean evaluate(@Nonnull TokenType token, @Nullable CriteriaSet criteriaSet) throws MessageHandlerException {
        try {
            return this.getTrustEngine().validate(token, criteriaSet);
        }
        catch (SecurityException e) {
            this.log.error("{} There was an error evaluating the request's token using the trust engine", (Object)this.getLogPrefix(), (Object)e);
            throw new MessageHandlerException("Error during trust engine evaluation of the token", (Exception)e);
        }
    }
}

