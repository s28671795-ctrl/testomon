/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.context.EventContext;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.CurrentOrPreviousEventLookup;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.common.messaging.context.SAMLEndpointContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLocalErrorPredicate
implements Predicate<ProfileRequestContext> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(DefaultLocalErrorPredicate.class);
    @Nonnull
    private Function<ProfileRequestContext, SAMLBindingContext> bindingContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLBindingContext.class), new OutboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, SAMLEndpointContext> endpointContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLEndpointContext.class), Functions.compose(new ChildContextLookup(SAMLPeerEntityContext.class), new OutboundMessageContextLookup()));
    @Nonnull
    private Function<ProfileRequestContext, EventContext> eventContextLookupStrategy = new CurrentOrPreviousEventLookup();
    @Nonnull
    @NonnullElements
    private Set<String> localEvents = Collections.emptySet();

    public void setBindingContextLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLBindingContext> strategy) {
        this.bindingContextLookupStrategy = Constraint.isNotNull(strategy, "SAMLBindingContext lookup strategy cannot be null");
    }

    public void setEndpointContextLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLEndpointContext> strategy) {
        this.endpointContextLookupStrategy = Constraint.isNotNull(strategy, "SAMLEndpointContext lookup strategy cannot be null");
    }

    public void setEventContextLookupStrategy(@Nonnull Function<ProfileRequestContext, EventContext> strategy) {
        this.eventContextLookupStrategy = Constraint.isNotNull(strategy, "EventContext lookup strategy cannot be null");
    }

    public void setLocalEvents(@Nonnull @NonnullElements Collection<String> events) {
        if (events == null) {
            this.localEvents = Collections.emptySet();
        } else {
            this.localEvents = new HashSet<String>(events.size());
            for (String e : events) {
                String trimmed = StringSupport.trimOrNull(e);
                if (trimmed == null) continue;
                this.localEvents.add(trimmed);
            }
        }
    }

    @Override
    public boolean apply(@Nullable ProfileRequestContext input) {
        if (input == null) {
            return true;
        }
        SAMLBindingContext bindingCtx = this.bindingContextLookupStrategy.apply(input);
        if (bindingCtx == null || bindingCtx.getBindingUri() == null) {
            this.log.debug("No SAMLBindingContext or binding URI available, error must be handled locally");
            return true;
        }
        SAMLEndpointContext endpointCtx = this.endpointContextLookupStrategy.apply(input);
        if (endpointCtx == null || endpointCtx.getEndpoint() == null || endpointCtx.getEndpoint().getLocation() == null && endpointCtx.getEndpoint().getResponseLocation() == null) {
            this.log.debug("No SAMLEndpointContext or endpoint location available, error must be handled locally");
            return true;
        }
        AuthnRequest authnRequest = (AuthnRequest)new MessageLookup(AuthnRequest.class).apply(new InboundMessageContextLookup().apply(input));
        if (authnRequest != null && authnRequest.isPassive().booleanValue()) {
            this.log.debug("Request was a SAML 2 AuthnRequest with IsPassive set, handling error with response");
            return false;
        }
        EventContext eventCtx = this.eventContextLookupStrategy.apply(input);
        if (eventCtx == null || eventCtx.getEvent() == null) {
            this.log.debug("No event found, assuming error handled with response");
            return false;
        }
        String event = eventCtx.getEvent().toString();
        if (this.localEvents.contains(event)) {
            this.log.debug("Error event {} will be handled locally", (Object)event);
            return true;
        }
        this.log.debug("Error event {} will be handled with response", (Object)event);
        return false;
    }
}

