/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.saml.common.profile.logic.AbstractNameIDPolicyPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultNameIDPolicyPredicate
extends AbstractNameIDPolicyPredicate {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(DefaultNameIDPolicyPredicate.class);

    @Override
    protected boolean doApply(@Nullable String requesterId, @Nullable String responderId, @Nullable String format, @Nullable String nameQualifier, @Nullable String spNameQualifier) {
        if (!(spNameQualifier == null || requesterId != null && spNameQualifier.equals(requesterId))) {
            this.log.debug("Requested SPNameQualifier {} did not match requester ID", (Object)spNameQualifier);
            return false;
        }
        if (!(nameQualifier == null || responderId != null && nameQualifier.equals(responderId))) {
            this.log.debug("Requested NameQualifier {} did not match responder ID", (Object)nameQualifier);
            return false;
        }
        return true;
    }
}

