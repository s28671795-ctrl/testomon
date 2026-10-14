/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.saml2.wssecurity.messaging.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.SAML20AssertionValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.wssecurity.SAML20AssertionToken;
import org.opensaml.saml.saml2.wssecurity.messaging.impl.DefaultSAML20AssertionValidationContextBuilder;
import org.opensaml.saml.saml2.wssecurity.messaging.impl.SAML20AssertionTokenValidationInput;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.wssecurity.Security;
import org.opensaml.soap.wssecurity.WSSecurityConstants;
import org.opensaml.soap.wssecurity.messaging.Token;
import org.opensaml.soap.wssecurity.messaging.WSSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSSecuritySAML20AssertionTokenSecurityHandler
extends AbstractMessageHandler {
    private final Logger log = LoggerFactory.getLogger(WSSecuritySAML20AssertionTokenSecurityHandler.class);
    @NonnullAfterInit
    private HttpServletRequest httpServletRequest;
    private boolean invalidFatal;
    @Nullable
    private SAML20AssertionValidator assertionValidator;
    @Nullable
    private Function<Pair<MessageContext, Assertion>, SAML20AssertionValidator> assertionValidatorLookup;
    @NonnullAfterInit
    private Function<SAML20AssertionTokenValidationInput, ValidationContext> validationContextBuilder;

    public WSSecuritySAML20AssertionTokenSecurityHandler() {
        this.setInvalidFatal(true);
        this.setValidationContextBuilder(new DefaultSAML20AssertionValidationContextBuilder());
    }

    @NonnullAfterInit
    public Function<SAML20AssertionTokenValidationInput, ValidationContext> getValidationContextBuilder() {
        return this.validationContextBuilder;
    }

    public void setValidationContextBuilder(@Nonnull Function<SAML20AssertionTokenValidationInput, ValidationContext> builder) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.validationContextBuilder = Constraint.isNotNull(builder, "Validation context builder may not be null");
    }

    @NonnullAfterInit
    public HttpServletRequest getHttpServletRequest() {
        return this.httpServletRequest;
    }

    public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.httpServletRequest = Constraint.isNotNull(request, "HttpServletRequest cannot be null");
    }

    public boolean isInvalidFatal() {
        return this.invalidFatal;
    }

    public void setInvalidFatal(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.invalidFatal = flag;
    }

    @Nullable
    public SAML20AssertionValidator getAssertionValidator() {
        return this.assertionValidator;
    }

    public void setAssertionValidator(@Nullable SAML20AssertionValidator validator) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.assertionValidator = validator;
    }

    @Nullable
    public Function<Pair<MessageContext, Assertion>, SAML20AssertionValidator> getAssertionValidatorLookup() {
        return this.assertionValidatorLookup;
    }

    public void setAssertionValidatorLookup(@Nullable Function<Pair<MessageContext, Assertion>, SAML20AssertionValidator> function) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.assertionValidatorLookup = function;
    }

    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.getValidationContextBuilder() == null) {
            throw new ComponentInitializationException("ValidationContext builder cannot be null");
        }
        if (this.getHttpServletRequest() == null) {
            throw new ComponentInitializationException("HttpServletRequest cannot be null");
        }
        if (this.getAssertionValidator() == null) {
            if (this.getAssertionValidatorLookup() == null) {
                throw new ComponentInitializationException("Both Assertion validator and lookup function were null");
            }
            this.log.info("Assertion validator is null, must be resovleable via the lookup function");
        }
    }

    protected void doDestroy() {
        this.httpServletRequest = null;
        super.doDestroy();
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        if (!SOAPMessagingSupport.isSOAPMessage((MessageContext<? extends XMLObject>)messageContext)) {
            this.log.info("Message context does not contain a SOAP envelope. Skipping rule...");
            return;
        }
        List<Assertion> assertions = this.resolveAssertions(messageContext);
        if (assertions == null || assertions.isEmpty()) {
            this.log.info("Inbound SOAP envelope contained no Assertion tokens. Skipping further processing");
            return;
        }
        WSSecurityContext wsContext = (WSSecurityContext)messageContext.getSubcontext(WSSecurityContext.class, true);
        for (Assertion assertion : assertions) {
            SAML20AssertionValidator validator = this.resolveValidator(messageContext, assertion);
            if (validator == null) {
                this.log.warn("No SAML20AssertionValidator was available, terminating");
                SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal processing error", null, null, null);
                throw new MessageHandlerException("No SAML20AssertionValidator was available");
            }
            ValidationContext validationContext = this.buildValidationContext(messageContext, assertion);
            try {
                ValidationResult validationResult = validator.validate(assertion, validationContext);
                SAML20AssertionToken token = new SAML20AssertionToken(assertion);
                this.processResult(validationContext, validationResult, token, messageContext);
                wsContext.getTokens().add(token);
            }
            catch (AssertionValidationException e) {
                this.log.warn("There was a problem determining Assertion validity: {}", (Object)e.getMessage());
                SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal security token processing error", null, null, null);
                throw new MessageHandlerException("Error determining SAML 2.0 Assertion validity", (Exception)e);
            }
        }
    }

    protected void processResult(@Nonnull ValidationContext validationContext, @Nonnull ValidationResult validationResult, @Nonnull SAML20AssertionToken token, @Nonnull MessageContext messageContext) throws MessageHandlerException {
        this.log.debug("Assertion token validation result was: {}", (Object)validationResult);
        String validationMsg = validationContext.getValidationFailureMessage();
        if (Strings.isNullOrEmpty(validationMsg)) {
            validationMsg = "unspecified";
        }
        switch (validationResult) {
            case VALID: {
                token.setValidationStatus(Token.ValidationStatus.VALID);
                token.setSubjectConfirmation((SubjectConfirmation)validationContext.getDynamicParameters().get("saml2.ConfirmedSubjectConfirmation"));
                break;
            }
            case INVALID: {
                this.log.warn("Assertion token validation was INVALID.  Reason: {}", (Object)validationMsg);
                if (this.isInvalidFatal()) {
                    SOAPMessagingSupport.registerSOAP11Fault(messageContext, WSSecurityConstants.SOAP_FAULT_INVALID_SECURITY_TOKEN, "The SAML 2.0 Assertion token was invalid", null, null, null);
                    throw new MessageHandlerException("Assertion token validation result was INVALID");
                }
                token.setValidationStatus(Token.ValidationStatus.INVALID);
                token.setSubjectConfirmation((SubjectConfirmation)validationContext.getDynamicParameters().get("saml2.ConfirmedSubjectConfirmation"));
                break;
            }
            case INDETERMINATE: {
                this.log.warn("Assertion token validation was INDETERMINATE. Reason: {}", (Object)validationMsg);
                if (this.isInvalidFatal()) {
                    SOAPMessagingSupport.registerSOAP11Fault(messageContext, WSSecurityConstants.SOAP_FAULT_INVALID_SECURITY_TOKEN, "The SAML 2.0 Assertion token's validity could not be determined", null, null, null);
                    throw new MessageHandlerException("Assertion token validation result was INDETERMINATE");
                }
                token.setValidationStatus(Token.ValidationStatus.INDETERMINATE);
                token.setSubjectConfirmation((SubjectConfirmation)validationContext.getDynamicParameters().get("saml2.ConfirmedSubjectConfirmation"));
                break;
            }
            default: {
                this.log.warn("Assertion validation result indicated an unknown value: {}", (Object)validationResult);
                SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal processing error", null, null, null);
                throw new IllegalArgumentException("Assertion validation result indicated an unknown value: " + (Object)((Object)validationResult));
            }
        }
    }

    @Nullable
    protected SAML20AssertionValidator resolveValidator(@Nonnull MessageContext messageContext, @Nonnull Assertion assertion) {
        if (this.getAssertionValidatorLookup() != null) {
            this.log.debug("Attempting to resolve SAML 2 Assertion validator via lookup function");
            SAML20AssertionValidator validator = this.getAssertionValidatorLookup().apply(new Pair<MessageContext, Assertion>(messageContext, assertion));
            if (validator != null) {
                this.log.debug("Resolved SAML 2 Assertion validator via lookup function");
                return validator;
            }
        }
        if (this.getAssertionValidator() != null) {
            this.log.debug("Resolved locally configured SAML 2 Assertion validator");
            return this.getAssertionValidator();
        }
        this.log.debug("No SAML 2 Assertion validator could be resolved");
        return null;
    }

    @Nonnull
    protected ValidationContext buildValidationContext(@Nonnull MessageContext messageContext, @Nonnull Assertion assertion) throws MessageHandlerException {
        ValidationContext validationContext = this.getValidationContextBuilder().apply(new SAML20AssertionTokenValidationInput(messageContext, this.getHttpServletRequest(), assertion));
        if (validationContext == null) {
            this.log.warn("ValidationContext produced was null");
            SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal processing error", null, null, null);
            throw new MessageHandlerException("No ValidationContext was produced");
        }
        return validationContext;
    }

    @Nonnull
    protected List<Assertion> resolveAssertions(@Nonnull MessageContext messageContext) {
        List<XMLObject> securityHeaders = SOAPMessagingSupport.getInboundHeaderBlock((MessageContext<? extends XMLObject>)messageContext, Security.ELEMENT_NAME);
        if (securityHeaders == null || securityHeaders.isEmpty()) {
            this.log.debug("No WS-Security Security header found in inbound SOAP message. Skipping further processing.");
            return Collections.emptyList();
        }
        LazyList<Assertion> assertions = new LazyList<Assertion>();
        for (XMLObject header : securityHeaders) {
            Security securityHeader = (Security)header;
            List<XMLObject> xmlObjects = securityHeader.getUnknownXMLObjects(Assertion.DEFAULT_ELEMENT_NAME);
            if (xmlObjects == null || xmlObjects.isEmpty()) continue;
            for (XMLObject xmlObject : xmlObjects) {
                assertions.add((Assertion)xmlObject);
            }
        }
        return assertions;
    }
}

