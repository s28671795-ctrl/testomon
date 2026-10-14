/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.saml2.wssecurity.messaging.impl;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.saml2.core.Assertion;

public class SAML20AssertionTokenValidationInput {
    private MessageContext messageContext;
    private HttpServletRequest httpServletRequest;
    private Assertion assertion;

    public SAML20AssertionTokenValidationInput(@Nonnull MessageContext context, @Nonnull HttpServletRequest request, @Nonnull Assertion samlAssertion) {
        this.messageContext = Constraint.isNotNull(context, "MessageContext may not be null");
        this.httpServletRequest = Constraint.isNotNull(request, "HttpServletRequest may not be null");
        this.assertion = Constraint.isNotNull(samlAssertion, "Assertion may not be null");
    }

    @Nonnull
    public MessageContext getMessageContext() {
        return this.messageContext;
    }

    @Nonnull
    public HttpServletRequest getHttpServletRequest() {
        return this.httpServletRequest;
    }

    @Nonnull
    public Assertion getAssertion() {
        return this.assertion;
    }
}

