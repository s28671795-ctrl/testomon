/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 */
package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.FunctionSupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.saml2.encryption.Encrypter;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEncryptAction
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractEncryptAction.class);
    @Nonnull
    private Function<ProfileRequestContext, EncryptionContext> encryptionCtxLookupStrategy = Functions.compose(new ChildContextLookup(EncryptionContext.class), new OutboundMessageContextLookup());
    @Nullable
    private Function<ProfileRequestContext, String> recipientLookupStrategy;
    @Nonnull
    private Function<ProfileRequestContext, Encrypter.KeyPlacement> keyPlacementLookupStrategy = FunctionSupport.constant(Encrypter.KeyPlacement.INLINE);
    @Nonnull
    private Predicate<ProfileRequestContext> encryptToSelf = Predicates.alwaysFalse();
    @Nullable
    private Function<Pair<ProfileRequestContext, EncryptionParameters>, List<EncryptionParameters>> encryptToSelfParametersStrategy;
    @Nullable
    private Function<ProfileRequestContext, String> selfRecipientLookupStrategy;
    @Nullable
    private Encrypter encrypter;

    public void setEncryptionContextLookupStrategy(@Nonnull Function<ProfileRequestContext, EncryptionContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.encryptionCtxLookupStrategy = Constraint.isNotNull(strategy, "EncryptionContext lookup strategy cannot be null");
    }

    public void setRecipientLookupStrategy(@Nonnull Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.recipientLookupStrategy = Constraint.isNotNull(strategy, "Recipient lookup strategy cannot be null");
    }

    public void setKeyPlacementLookupStrategy(@Nonnull Function<ProfileRequestContext, Encrypter.KeyPlacement> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keyPlacementLookupStrategy = Constraint.isNotNull(strategy, "Key placement lookup strategy cannot be null");
    }

    public void setEncryptToSelf(@Nonnull Predicate<ProfileRequestContext> predicate) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.encryptToSelf = Constraint.isNotNull(predicate, "Encrypt-to-self predicate cannot be null");
    }

    public void setEncryptToSelfParametersStrategy(@Nullable Function<Pair<ProfileRequestContext, EncryptionParameters>, List<EncryptionParameters>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.encryptToSelfParametersStrategy = strategy;
    }

    public void setSelfRecipientLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.selfRecipientLookupStrategy = strategy;
    }

    @Nullable
    public Encrypter getEncrypter() {
        return this.encrypter;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        if (!super.doPreExecute(profileRequestContext)) {
            return false;
        }
        EncryptionParameters params = this.getApplicableParameters(this.encryptionCtxLookupStrategy.apply(profileRequestContext));
        if (params == null) {
            this.log.debug("{} No encryption parameters, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        String recipient = this.recipientLookupStrategy != null ? this.recipientLookupStrategy.apply(profileRequestContext) : null;
        DataEncryptionParameters dataParams = new DataEncryptionParameters(params);
        ArrayList<KeyEncryptionParameters> keyParams = new ArrayList<KeyEncryptionParameters>();
        keyParams.add(new KeyEncryptionParameters(params, recipient));
        if (this.encryptToSelf.apply(profileRequestContext)) {
            this.log.debug("{} Encryption to self was indicated", (Object)this.getLogPrefix());
            String selfRecipient = null;
            if (this.selfRecipientLookupStrategy != null) {
                selfRecipient = this.selfRecipientLookupStrategy.apply(profileRequestContext);
                this.log.debug("{} Resolved self-encryption recipient value: {}", (Object)this.getLogPrefix(), (Object)selfRecipient);
            }
            if (this.encryptToSelfParametersStrategy == null) {
                this.log.error("{} Self-encryption was indicated, but no parameters strategy was supplied", (Object)this.getLogPrefix());
                ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
                return false;
            }
            List<EncryptionParameters> selfParams = this.encryptToSelfParametersStrategy.apply(new Pair<ProfileRequestContext, EncryptionParameters>(profileRequestContext, params));
            if (selfParams != null && !selfParams.isEmpty()) {
                this.log.debug("{} Saw {} self-encryption parameters", (Object)this.getLogPrefix(), (Object)selfParams.size());
                for (EncryptionParameters selfParam : selfParams) {
                    keyParams.add(new KeyEncryptionParameters(selfParam, selfRecipient));
                }
            } else {
                this.log.error("{} Self-encryption self was indicated, but no parameters were resolved", (Object)this.getLogPrefix());
                ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
                return false;
            }
        }
        this.encrypter = new Encrypter(dataParams, keyParams);
        this.encrypter.setKeyPlacement(this.keyPlacementLookupStrategy.apply(profileRequestContext));
        return true;
    }

    @Nullable
    protected abstract EncryptionParameters getApplicableParameters(@Nullable EncryptionContext var1);
}

