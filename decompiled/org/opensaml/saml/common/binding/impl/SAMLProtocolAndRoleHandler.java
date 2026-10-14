/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.common.binding.impl;

import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.AbstractSAMLEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;

public class SAMLProtocolAndRoleHandler
extends AbstractMessageHandler {
    @NonnullAfterInit
    @NotEmpty
    private String samlProtocol;
    @NonnullAfterInit
    private QName peerRole;
    @Nonnull
    private Class<? extends AbstractSAMLEntityContext> entityContextClass = SAMLPeerEntityContext.class;

    public void setEntityContextClass(@Nonnull Class<? extends AbstractSAMLEntityContext> clazz) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.entityContextClass = Constraint.isNotNull(clazz, "SAML entity context class may not be null");
    }

    public void setProtocol(@Nonnull @NotEmpty String protocol) {
        this.samlProtocol = Constraint.isNotNull(StringSupport.trimOrNull(protocol), "SAML protocol cannot be null");
    }

    public void setRole(@Nonnull QName role) {
        this.peerRole = Constraint.isNotNull(role, "SAML peer role cannot be null");
    }

    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.samlProtocol == null || this.peerRole == null) {
            throw new ComponentInitializationException("SAML protocol or peer role was null");
        }
    }

    protected void doInvoke(MessageContext messageContext) throws MessageHandlerException {
        ((SAMLProtocolContext)messageContext.getSubcontext(SAMLProtocolContext.class, true)).setProtocol(this.samlProtocol);
        ((AbstractSAMLEntityContext)messageContext.getSubcontext(this.entityContextClass, true)).setRole(this.peerRole);
    }
}

