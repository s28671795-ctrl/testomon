/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.common.messaging.context.navigate;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.AbstractAuthenticatableSAMLEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;

public class SAMLMessageContextAuthenticationFunction
implements Function<MessageContext, Boolean> {
    @Nonnull
    private Class<? extends AbstractAuthenticatableSAMLEntityContext> entityContextClass = SAMLPeerEntityContext.class;

    public void setEntityContextClass(@Nonnull Class<? extends AbstractAuthenticatableSAMLEntityContext> clazz) {
        this.entityContextClass = Constraint.isNotNull(clazz, "The SAML entity context class may not be null");
    }

    @Override
    @Nullable
    public Boolean apply(@Nullable MessageContext input) {
        AbstractAuthenticatableSAMLEntityContext entityCtx;
        if (input != null && (entityCtx = (AbstractAuthenticatableSAMLEntityContext)input.getSubcontext(this.entityContextClass)) != null) {
            return entityCtx.isAuthenticated();
        }
        return null;
    }
}

