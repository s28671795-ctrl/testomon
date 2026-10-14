/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 */
package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerifyChannelBindings
extends AbstractProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(VerifyChannelBindings.class);
    @Nonnull
    private Function<ProfileRequestContext, ChannelBindingsContext> channelBindingsLookupStrategy1 = Functions.compose(new ChildContextLookup(ChannelBindingsContext.class), new InboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, ChannelBindingsContext> channelBindingsLookupStrategy2 = Functions.compose(new ChildContextLookup(ChannelBindingsContext.class), Functions.compose(new ChildContextLookup(SOAP11Context.class), new InboundMessageContextLookup()));
    @Nonnull
    private Function<ProfileRequestContext, ChannelBindingsContext> channelBindingsCreationStrategy = Functions.compose(new ChildContextLookup(ChannelBindingsContext.class, true), new OutboundMessageContextLookup());
    @Nullable
    private ChannelBindingsContext channelBindingsContext1;
    @Nullable
    private ChannelBindingsContext channelBindingsContext2;

    public void setChannelBindingsLookupStrategy1(@Nonnull Function<ProfileRequestContext, ChannelBindingsContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.channelBindingsLookupStrategy1 = Constraint.isNotNull(strategy, "First ChannelBindingsContext lookup strategy cannot be null");
    }

    public void setChannelBindingsLookupStrategy2(@Nonnull Function<ProfileRequestContext, ChannelBindingsContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.channelBindingsLookupStrategy2 = Constraint.isNotNull(strategy, "Second ChannelBindingsContext lookup strategy cannot be null");
    }

    public void setChannelBindingsCreationStrategy(@Nonnull Function<ProfileRequestContext, ChannelBindingsContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.channelBindingsCreationStrategy = Constraint.isNotNull(strategy, "ChannelBindingsContext creation strategy cannot be null");
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        if (!super.doPreExecute(profileRequestContext)) {
            return false;
        }
        this.channelBindingsContext1 = this.channelBindingsLookupStrategy1.apply(profileRequestContext);
        this.channelBindingsContext2 = this.channelBindingsLookupStrategy2.apply(profileRequestContext);
        if (this.channelBindingsContext1 != null && this.channelBindingsContext1.getChannelBindings().isEmpty()) {
            this.channelBindingsContext1 = null;
        }
        if (this.channelBindingsContext2 != null && this.channelBindingsContext2.getChannelBindings().isEmpty()) {
            this.channelBindingsContext2 = null;
        }
        if (this.channelBindingsContext1 == null && this.channelBindingsContext2 == null) {
            this.log.debug("{} No channel bindings found to verify, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        return true;
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        if (this.channelBindingsContext1 == null || this.channelBindingsContext2 == null) {
            this.log.warn("{} Unable to verify channel bindings sent for comparison", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "ChannelBindingsError");
            return;
        }
        ChannelBindings matched = null;
        for (ChannelBindings cb1 : this.channelBindingsContext1.getChannelBindings()) {
            for (ChannelBindings cb2 : this.channelBindingsContext2.getChannelBindings()) {
                String cb2Data;
                String cb1Data;
                if (!Objects.equals(cb1.getType(), cb2.getType()) || !Objects.equals(cb1Data = StringSupport.trimOrNull(cb1.getValue()), cb2Data = StringSupport.trimOrNull(cb2.getValue()))) continue;
                matched = cb1;
                break;
            }
            if (matched == null) continue;
            break;
        }
        if (matched == null) {
            this.log.warn("{} Unable to verify channel bindings sent for comparison", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "ChannelBindingsError");
            return;
        }
        this.log.debug("{} Saving matched channel bindings for later use", (Object)this.getLogPrefix());
        ChannelBindingsContext cbCtx = this.channelBindingsCreationStrategy.apply(profileRequestContext);
        if (cbCtx != null) {
            cbCtx.getChannelBindings().add(matched);
        } else {
            this.log.error("{} Unable to create ChannelBindingContext to store result", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "ChannelBindingsError");
        }
    }
}

