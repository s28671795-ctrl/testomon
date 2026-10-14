/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.context.navigate.RecursiveTypedParentContextLookup
 */
package org.opensaml.profile.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.RecursiveTypedParentContextLookup;
import org.opensaml.profile.context.ProfileRequestContext;

public class MessageContextPredicateAdapter
implements Predicate<MessageContext> {
    private Predicate<ProfileRequestContext> adapted;
    private RecursiveTypedParentContextLookup<MessageContext, ProfileRequestContext> prcLookup;
    private boolean noPRCSatisfies;

    public MessageContextPredicateAdapter(@Nonnull @ParameterName(name="prcPredicate") Predicate<ProfileRequestContext> prcPredicate) {
        this(prcPredicate, false);
    }

    public MessageContextPredicateAdapter(@Nonnull @ParameterName(name="prcPredicate") Predicate<ProfileRequestContext> prcPredicate, @ParameterName(name="unresolvedSatisfies") boolean unresolvedSatisfies) {
        this.adapted = Constraint.isNotNull(prcPredicate, "The adapted predicate may not be null");
        this.noPRCSatisfies = unresolvedSatisfies;
        this.prcLookup = new RecursiveTypedParentContextLookup(ProfileRequestContext.class);
    }

    @Override
    public boolean apply(@Nullable MessageContext input) {
        if (input == null) {
            return false;
        }
        ProfileRequestContext prc = (ProfileRequestContext)this.prcLookup.apply((BaseContext)input);
        if (prc == null) {
            return this.noPRCSatisfies;
        }
        return this.adapted.apply(prc);
    }
}

