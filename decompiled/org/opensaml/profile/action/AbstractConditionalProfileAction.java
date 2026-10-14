/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.action;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.LoggerFactory;

public abstract class AbstractConditionalProfileAction<InboundMessageType, OutboundMessageType>
extends AbstractProfileAction<InboundMessageType, OutboundMessageType> {
    @Nonnull
    private Predicate<ProfileRequestContext> activationCondition = Predicates.alwaysTrue();

    @Nonnull
    public Predicate<ProfileRequestContext> getActivationCondition() {
        return this.activationCondition;
    }

    public void setActivationCondition(@Nonnull Predicate<ProfileRequestContext> condition) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.activationCondition = Constraint.isNotNull(condition, "Predicate cannot be null");
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext<InboundMessageType, OutboundMessageType> profileRequestContext) {
        if (this.activationCondition.apply(profileRequestContext)) {
            return super.doPreExecute(profileRequestContext);
        }
        LoggerFactory.getLogger(AbstractConditionalProfileAction.class).debug("{} Activation condition for action returned false", (Object)this.getLogPrefix());
        return false;
    }
}

