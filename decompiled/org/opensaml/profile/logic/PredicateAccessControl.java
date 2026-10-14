/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 */
package org.opensaml.profile.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.security.AccessControl;
import org.opensaml.profile.context.AccessControlContext;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredicateAccessControl
extends AbstractIdentifiableInitializableComponent
implements AccessControl {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(PredicateAccessControl.class);
    @Nonnull
    private final Predicate<ProfileRequestContext> predicate;

    public PredicateAccessControl(@Nonnull @ParameterName(name="condition") Predicate<ProfileRequestContext> condition) {
        this.predicate = Constraint.isNotNull(condition, "Predicate cannot be null");
    }

    @Override
    public boolean checkAccess(@Nonnull ServletRequest request, @Nullable String operation, @Nullable String resource) {
        Constraint.isNotNull(request, "ServletRequest cannot be null");
        Object attribute = request.getAttribute("opensamlProfileRequestContext");
        if (attribute != null && attribute instanceof ProfileRequestContext) {
            ProfileRequestContext prc = (ProfileRequestContext)((Object)attribute);
            AccessControlContext acc = (AccessControlContext)prc.getSubcontext(AccessControlContext.class, true);
            acc.setOperation(operation);
            acc.setResource(resource);
            if (this.predicate.apply(prc)) {
                return true;
            }
            this.log.warn("{} Denied request based on predicate", (Object)this.getLogPrefix());
        } else {
            this.log.warn("{} No ProfileRequestContext, access denied", (Object)this.getLogPrefix());
        }
        return false;
    }

    @Nonnull
    private String getLogPrefix() {
        return "Policy " + this.getId() + ":";
    }
}

