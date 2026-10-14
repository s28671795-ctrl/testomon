/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.security.SecurityException;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SignAssertions
extends AbstractProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SignAssertions.class);
    @Nonnull
    private Function<ProfileRequestContext, SAMLObject> responseLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, SecurityParametersContext> securityParametersLookupStrategy = new ChildContextLookup(SecurityParametersContext.class);
    @Nullable
    private SignatureSigningParameters signatureSigningParameters;
    @Nullable
    private SAMLObject response;

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLObject> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public void setSecurityParametersLookupStrategy(@Nonnull Function<ProfileRequestContext, SecurityParametersContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.securityParametersLookupStrategy = Constraint.isNotNull(strategy, "SecurityParameterContext lookup strategy cannot be null");
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        SecurityParametersContext secParamCtx;
        if (!super.doPreExecute(profileRequestContext)) {
            return false;
        }
        this.response = this.responseLookupStrategy.apply(profileRequestContext);
        if (this.response == null) {
            this.log.debug("{} No SAML Response located in current profile request context", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        if (this.response instanceof ArtifactResponse) {
            this.log.debug("{} Found ArtifactResponse, stepping down into enclosed message", (Object)this.getLogPrefix());
            this.response = ((ArtifactResponse)this.response).getMessage();
        }
        if (this.response instanceof Response) {
            if (((Response)this.response).getAssertions().isEmpty()) {
                this.log.debug("{} No assertions available, nothing to do", (Object)this.getLogPrefix());
                return false;
            }
        } else if (this.response instanceof org.opensaml.saml.saml2.core.Response) {
            if (((org.opensaml.saml.saml2.core.Response)this.response).getAssertions().isEmpty()) {
                this.log.debug("{} No assertions available, nothing to do", (Object)this.getLogPrefix());
                return false;
            }
        } else {
            this.log.debug("{} Message returned by lookup strategy was not a SAML Response", (Object)this.getLogPrefix());
            return false;
        }
        if ((secParamCtx = this.securityParametersLookupStrategy.apply(profileRequestContext)) == null) {
            this.log.debug("{} Will not sign assertions because no security parameters context is available", (Object)this.getLogPrefix());
            return false;
        }
        this.signatureSigningParameters = secParamCtx.getSignatureSigningParameters();
        if (this.signatureSigningParameters == null) {
            this.log.debug("{} Will not sign assertions because no signature signing parameters available", (Object)this.getLogPrefix());
            return false;
        }
        return true;
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        try {
            if (this.log.isTraceEnabled()) {
                this.logResponse("Response before signing:");
            }
            if (this.response instanceof Response) {
                for (Assertion assertion : ((Response)this.response).getAssertions()) {
                    SignatureSupport.signObject(assertion, this.signatureSigningParameters);
                }
            } else if (this.response instanceof org.opensaml.saml.saml2.core.Response) {
                for (org.opensaml.saml.saml2.core.Assertion assertion : ((org.opensaml.saml.saml2.core.Response)this.response).getAssertions()) {
                    SignatureSupport.signObject(assertion, this.signatureSigningParameters);
                }
            }
            if (this.log.isTraceEnabled()) {
                this.logResponse("Response after signing:");
            }
        }
        catch (MarshallingException | SecurityException | SignatureException e) {
            this.log.warn("{} Error encountered while signing assertions", (Object)this.getLogPrefix(), (Object)e);
            ActionSupport.buildEvent(profileRequestContext, "UnableToSign");
        }
    }

    private void logResponse(@Nonnull String message) {
        if (message != null && this.response != null) {
            try {
                Element dom = XMLObjectSupport.marshall(this.response);
                this.log.trace(message + "\n" + SerializeSupport.prettyPrintXML(dom));
            }
            catch (MarshallingException e) {
                this.log.warn("Unable to marshall message for logging purposes", e);
            }
        }
    }
}

