/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.stax.impl.securityToken.SamlSecurityTokenImpl;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.validate.SamlTokenValidator;
import org.apache.wss4j.stax.validate.SignatureTokenValidatorImpl;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamlTokenValidatorImpl
extends SignatureTokenValidatorImpl
implements SamlTokenValidator {
    private static final transient Logger LOG = LoggerFactory.getLogger(SamlTokenValidatorImpl.class);
    private int futureTTL = 60;
    private int ttl = 1800;
    private boolean validateSignatureAgainstProfile = true;
    private String requiredSubjectConfirmationMethod;
    private boolean requireStandardSubjectConfirmationMethod = true;
    private boolean requireBearerSignature = true;

    public void setFutureTTL(int newFutureTTL) {
        this.futureTTL = newFutureTTL;
    }

    public boolean isValidateSignatureAgainstProfile() {
        return this.validateSignatureAgainstProfile;
    }

    public void setValidateSignatureAgainstProfile(boolean validateSignatureAgainstProfile) {
        this.validateSignatureAgainstProfile = validateSignatureAgainstProfile;
    }

    public String getRequiredSubjectConfirmationMethod() {
        return this.requiredSubjectConfirmationMethod;
    }

    public void setRequiredSubjectConfirmationMethod(String requiredSubjectConfirmationMethod) {
        this.requiredSubjectConfirmationMethod = requiredSubjectConfirmationMethod;
    }

    @Override
    public <T extends SamlSecurityToken & InboundSecurityToken> T validate(SamlAssertionWrapper samlAssertionWrapper, InboundSecurityToken subjectSecurityToken, TokenContext tokenContext) throws WSSecurityException {
        this.checkConditions(samlAssertionWrapper, tokenContext.getWssSecurityProperties().getAudienceRestrictions());
        this.checkAuthnStatements(samlAssertionWrapper);
        this.verifySubjectConfirmationMethod(samlAssertionWrapper);
        this.checkOneTimeUse(samlAssertionWrapper, tokenContext.getWssSecurityProperties().getSamlOneTimeUseReplayCache());
        this.validateAssertion(samlAssertionWrapper);
        Crypto sigVerCrypto = null;
        if (samlAssertionWrapper.isSigned()) {
            sigVerCrypto = tokenContext.getWssSecurityProperties().getSignatureVerificationCrypto();
        }
        SamlSecurityTokenImpl securityToken = new SamlSecurityTokenImpl(samlAssertionWrapper, subjectSecurityToken, tokenContext.getWsSecurityContext(), sigVerCrypto, WSSecurityTokenConstants.KeyIdentifier_NoKeyInfo, tokenContext.getWssSecurityProperties());
        securityToken.setElementPath(tokenContext.getElementPath());
        securityToken.setXMLSecEvent(tokenContext.getFirstXMLSecEvent());
        SamlSecurityTokenImpl token = securityToken;
        return (T)token;
    }

    protected void verifySubjectConfirmationMethod(SamlAssertionWrapper samlAssertion) throws WSSecurityException {
        List<String> methods = samlAssertion.getConfirmationMethods();
        if (methods == null || methods.isEmpty()) {
            if (this.requiredSubjectConfirmationMethod != null) {
                LOG.debug("A required subject confirmation method was not present");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
            if (this.requireStandardSubjectConfirmationMethod) {
                LOG.debug("A standard subject confirmation method was not present");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
        }
        boolean signed = samlAssertion.isSigned();
        boolean requiredMethodFound = false;
        boolean standardMethodFound = false;
        if (methods != null) {
            for (String method2 : methods) {
                if (OpenSAMLUtil.isMethodHolderOfKey(method2)) {
                    if (!signed) {
                        LOG.debug("A holder-of-key assertion must be signed");
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
                    }
                    standardMethodFound = true;
                }
                if (method2 == null) continue;
                if (method2.equals(this.requiredSubjectConfirmationMethod)) {
                    requiredMethodFound = true;
                }
                if ("urn:oasis:names:tc:SAML:2.0:cm:bearer".equals(method2) || "urn:oasis:names:tc:SAML:1.0:cm:bearer".equals(method2)) {
                    standardMethodFound = true;
                    if (!this.requireBearerSignature || signed) continue;
                    LOG.debug("A Bearer Assertion was not signed");
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
                }
                if (!"urn:oasis:names:tc:SAML:2.0:cm:sender-vouches".equals(method2) && !"urn:oasis:names:tc:SAML:1.0:cm:sender-vouches".equals(method2)) continue;
                standardMethodFound = true;
            }
        }
        if (!requiredMethodFound && this.requiredSubjectConfirmationMethod != null) {
            LOG.debug("A required subject confirmation method was not present");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
        }
        if (!standardMethodFound && this.requireStandardSubjectConfirmationMethod) {
            LOG.debug("A standard subject confirmation method was not present");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
        }
    }

    protected void checkConditions(SamlAssertionWrapper samlAssertion, List<String> audienceRestrictions) throws WSSecurityException {
        this.checkConditions(samlAssertion);
        samlAssertion.checkAudienceRestrictions(audienceRestrictions);
    }

    protected void checkConditions(SamlAssertionWrapper samlAssertion) throws WSSecurityException {
        samlAssertion.checkConditions(this.futureTTL);
        samlAssertion.checkIssueInstant(this.futureTTL, this.ttl);
    }

    protected void checkAuthnStatements(SamlAssertionWrapper samlAssertion) throws WSSecurityException {
        samlAssertion.checkAuthnStatements(this.futureTTL);
    }

    protected void checkOneTimeUse(SamlAssertionWrapper samlAssertion, ReplayCache replayCache) throws WSSecurityException {
        if (replayCache != null && samlAssertion.getSamlVersion().equals(SAMLVersion.VERSION_20) && samlAssertion.getSaml2().getConditions() != null && samlAssertion.getSaml2().getConditions().getOneTimeUse() != null) {
            String identifier = samlAssertion.getId();
            if (replayCache.contains(identifier)) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "badSamlToken", new Object[]{"A replay attack has been detected"});
            }
            DateTime expires = samlAssertion.getSaml2().getConditions().getNotOnOrAfter();
            if (expires != null) {
                Instant currentTime = Instant.now();
                replayCache.add(identifier, 1L + Duration.between(currentTime, expires.toDate().toInstant()).getSeconds());
            } else {
                replayCache.add(identifier);
            }
        }
    }

    protected void validateAssertion(SamlAssertionWrapper samlAssertion) throws WSSecurityException {
        if (this.validateSignatureAgainstProfile) {
            samlAssertion.validateSignatureAgainstProfile();
        }
    }

    public boolean isRequireStandardSubjectConfirmationMethod() {
        return this.requireStandardSubjectConfirmationMethod;
    }

    public void setRequireStandardSubjectConfirmationMethod(boolean requireStandardSubjectConfirmationMethod) {
        this.requireStandardSubjectConfirmationMethod = requireStandardSubjectConfirmationMethod;
    }

    public boolean isRequireBearerSignature() {
        return this.requireBearerSignature;
    }

    public void setRequireBearerSignature(boolean requireBearerSignature) {
        this.requireBearerSignature = requireBearerSignature;
    }

    public int getTtl() {
        return this.ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}

