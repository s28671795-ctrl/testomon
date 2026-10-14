/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.common.binding.security.impl;

import com.google.common.base.Strings;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.opensaml.xmlsec.signature.support.SignatureValidationParametersCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSAMLSimpleSignatureSecurityHandler
extends AbstractMessageHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(BaseSAMLSimpleSignatureSecurityHandler.class);
    @NonnullAfterInit
    private HttpServletRequest httpServletRequest;
    @Nullable
    private SAMLPeerEntityContext peerContext;
    @Nullable
    private SAMLProtocolContext samlProtocolContext;
    @Nullable
    private SignatureTrustEngine trustEngine;

    @Nullable
    protected SignatureTrustEngine getTrustEngine() {
        return this.trustEngine;
    }

    @NonnullAfterInit
    public HttpServletRequest getHttpServletRequest() {
        return this.httpServletRequest;
    }

    public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.httpServletRequest = Constraint.isNotNull(request, "HttpServletRequest cannot be null");
    }

    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.httpServletRequest == null) {
            throw new ComponentInitializationException("HttpServletRequest cannot be null");
        }
    }

    protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        if (!super.doPreInvoke(messageContext)) {
            return false;
        }
        this.peerContext = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class);
        if (this.peerContext == null || this.peerContext.getRole() == null) {
            throw new MessageHandlerException("SAMLPeerEntityContext was missing or unpopulated");
        }
        this.samlProtocolContext = (SAMLProtocolContext)messageContext.getSubcontext(SAMLProtocolContext.class);
        if (this.samlProtocolContext == null || this.samlProtocolContext.getProtocol() == null) {
            throw new MessageHandlerException("SAMLProtocolContext was missing or unpopulated");
        }
        SecurityParametersContext secParams = (SecurityParametersContext)messageContext.getSubcontext(SecurityParametersContext.class);
        if (secParams == null || secParams.getSignatureValidationParameters() == null || secParams.getSignatureValidationParameters().getSignatureTrustEngine() == null) {
            throw new MessageHandlerException("No SignatureTrustEngine was available from the MessageContext");
        }
        this.trustEngine = secParams.getSignatureValidationParameters().getSignatureTrustEngine();
        return true;
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        this.log.debug("{} Evaluating simple signature rule of type: {}", (Object)this.getLogPrefix(), (Object)((Object)((Object)this)).getClass().getName());
        if (!this.ruleHandles(messageContext)) {
            this.log.debug("{} Handler can not handle this request, skipping processing", (Object)this.getLogPrefix());
            return;
        }
        byte[] signature = this.getSignature();
        if (signature == null || signature.length == 0) {
            this.log.debug("{} HTTP request was not signed via simple signature mechanism, skipping", (Object)this.getLogPrefix());
            return;
        }
        String sigAlg = this.getSignatureAlgorithm();
        if (Strings.isNullOrEmpty(sigAlg)) {
            this.log.warn("{} Signature algorithm could not be extracted from request, cannot validate simple signature", (Object)this.getLogPrefix());
            return;
        }
        byte[] signedContent = this.getSignedContent();
        if (signedContent == null || signedContent.length == 0) {
            this.log.warn("{} Signed content could not be extracted from HTTP request, cannot validate", (Object)this.getLogPrefix());
            return;
        }
        this.doEvaluate(signature, signedContent, sigAlg, messageContext);
    }

    private void doEvaluate(@Nonnull @NotEmpty byte[] signature, @Nonnull @NotEmpty byte[] signedContent, @Nonnull @NotEmpty String algorithmURI, @Nonnull MessageContext messageContext) throws MessageHandlerException {
        List<Credential> candidateCredentials = this.getRequestCredentials(messageContext);
        String contextEntityID = this.peerContext.getEntityId();
        if (contextEntityID != null) {
            this.log.debug("{} Attempting to validate SAML protocol message simple signature using context entityID: {}", (Object)this.getLogPrefix(), (Object)contextEntityID);
            CriteriaSet criteriaSet = this.buildCriteriaSet(contextEntityID, messageContext);
            if (this.validateSignature(signature, signedContent, algorithmURI, criteriaSet, candidateCredentials)) {
                this.log.debug("{} Validation of request simple signature succeeded", (Object)this.getLogPrefix());
                if (!this.peerContext.isAuthenticated()) {
                    this.log.debug("{} Authentication via request simple signature succeeded for context issuer entity ID {}", (Object)this.getLogPrefix(), (Object)contextEntityID);
                    this.peerContext.setAuthenticated(true);
                }
                return;
            }
            this.log.warn("{} Validation of request simple signature failed for context issuer: {}", (Object)this.getLogPrefix(), (Object)contextEntityID);
            throw new MessageHandlerException("Validation of request simple signature failed for context issuer");
        }
        String derivedEntityID = this.deriveSignerEntityID(messageContext);
        if (derivedEntityID != null) {
            this.log.debug("{} Attempting to validate SAML protocol message simple signature using derived entityID: {}", (Object)this.getLogPrefix(), (Object)derivedEntityID);
            CriteriaSet criteriaSet = this.buildCriteriaSet(derivedEntityID, messageContext);
            if (this.validateSignature(signature, signedContent, algorithmURI, criteriaSet, candidateCredentials)) {
                this.log.debug("{} Validation of request simple signature succeeded", (Object)this.getLogPrefix());
                if (!this.peerContext.isAuthenticated()) {
                    this.log.debug("{} Authentication via request simple signature succeeded for derived issuer {}", (Object)this.getLogPrefix(), (Object)derivedEntityID);
                    this.peerContext.setEntityId(derivedEntityID);
                    this.peerContext.setAuthenticated(true);
                }
                return;
            }
            this.log.warn("{} Validation of request simple signature failed for derived issuer: {}", (Object)this.getLogPrefix(), (Object)derivedEntityID);
            throw new MessageHandlerException("Validation of request simple signature failed for derived issuer");
        }
        this.log.warn("{} Neither context nor derived issuer available, cannot attempt SAML simple signature validation", (Object)this.getLogPrefix());
        throw new MessageHandlerException("No message issuer available, cannot attempt simple signature validation");
    }

    protected boolean validateSignature(@Nonnull @NotEmpty byte[] signature, @Nonnull @NotEmpty byte[] signedContent, @Nonnull @NotEmpty String algorithmURI, @Nonnull CriteriaSet criteriaSet, @Nonnull @NonnullElements List<Credential> candidateCredentials) throws MessageHandlerException {
        SignatureTrustEngine engine = this.getTrustEngine();
        try {
            if (candidateCredentials == null || candidateCredentials.isEmpty()) {
                if (engine.validate(signature, signedContent, algorithmURI, criteriaSet, null)) {
                    this.log.debug("{} Simple signature validation (with no request-derived credentials) was successful", (Object)this.getLogPrefix());
                    return true;
                }
                this.log.warn("{} Simple signature validation (with no request-derived credentials) failed", (Object)this.getLogPrefix());
                return false;
            }
            for (Credential cred : candidateCredentials) {
                if (!engine.validate(signature, signedContent, algorithmURI, criteriaSet, cred)) continue;
                this.log.debug("{} Simple signature validation succeeded with a request-derived credential", (Object)this.getLogPrefix());
                return true;
            }
            this.log.warn("{} Signature validation using request-derived credentials failed", (Object)this.getLogPrefix());
            return false;
        }
        catch (SecurityException e) {
            this.log.warn("{} Error evaluating the request's simple signature using the trust engine", (Object)this.getLogPrefix(), (Object)e);
            throw new MessageHandlerException("Error during trust engine evaluation of the simple signature", (Exception)e);
        }
    }

    @Nonnull
    @NonnullElements
    protected List<Credential> getRequestCredentials(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        return Collections.emptyList();
    }

    @Nullable
    protected byte[] getSignature() throws MessageHandlerException {
        String signature = this.getHttpServletRequest().getParameter("Signature");
        if (Strings.isNullOrEmpty(signature)) {
            return null;
        }
        return Base64Support.decode(signature);
    }

    @Nullable
    protected String getSignatureAlgorithm() throws MessageHandlerException {
        return this.getHttpServletRequest().getParameter("SigAlg");
    }

    @Nullable
    protected String deriveSignerEntityID(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        return null;
    }

    @Nonnull
    protected CriteriaSet buildCriteriaSet(@Nullable String entityID, @Nonnull MessageContext messageContext) throws MessageHandlerException {
        CriteriaSet criteriaSet = new CriteriaSet();
        if (!Strings.isNullOrEmpty(entityID)) {
            criteriaSet.add(new EntityIdCriterion(entityID));
        }
        criteriaSet.add(new EntityRoleCriterion(this.peerContext.getRole()));
        criteriaSet.add(new ProtocolCriterion(this.samlProtocolContext.getProtocol()));
        criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
        SecurityParametersContext secParamsContext = (SecurityParametersContext)messageContext.getSubcontext(SecurityParametersContext.class);
        if (secParamsContext != null && secParamsContext.getSignatureValidationParameters() != null) {
            criteriaSet.add(new SignatureValidationParametersCriterion(secParamsContext.getSignatureValidationParameters()));
        }
        return criteriaSet;
    }

    @Nullable
    protected abstract byte[] getSignedContent() throws MessageHandlerException;

    protected abstract boolean ruleHandles(@Nonnull MessageContext var1) throws MessageHandlerException;
}

