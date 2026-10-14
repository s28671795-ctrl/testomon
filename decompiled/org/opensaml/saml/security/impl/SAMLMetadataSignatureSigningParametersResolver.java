/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.security.impl;

import com.google.common.base.Predicate;
import java.security.Key;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.saml.ext.saml2alg.DigestMethod;
import org.opensaml.saml.ext.saml2alg.SigningMethod;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.crypto.KeySupport;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.impl.BasicSignatureSigningParametersResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLMetadataSignatureSigningParametersResolver
extends BasicSignatureSigningParametersResolver {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(SAMLMetadataSignatureSigningParametersResolver.class);

    @Override
    protected void resolveAndPopulateCredentialAndSignatureAlgorithm(@Nonnull SignatureSigningParameters params, @Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        if (!criteria.contains(RoleDescriptorCriterion.class)) {
            super.resolveAndPopulateCredentialAndSignatureAlgorithm(params, criteria, whitelistBlacklistPredicate);
            return;
        }
        List<XMLObject> signingMethods = this.getExtensions(criteria.get(RoleDescriptorCriterion.class).getRole(), SigningMethod.DEFAULT_ELEMENT_NAME);
        if (signingMethods == null || signingMethods.isEmpty()) {
            super.resolveAndPopulateCredentialAndSignatureAlgorithm(params, criteria, whitelistBlacklistPredicate);
            return;
        }
        List<Credential> credentials = this.getEffectiveSigningCredentials(criteria);
        for (XMLObject xmlObject : signingMethods) {
            SigningMethod signingMethod = (SigningMethod)xmlObject;
            this.log.trace("Evaluating SAML metadata SigningMethod with algorithm: {}, minKeySize: {}, maxKeySize: {}", signingMethod.getAlgorithm(), signingMethod.getMinKeySize(), signingMethod.getMaxKeySize());
            if (signingMethod.getAlgorithm() == null || !this.getAlgorithmRuntimeSupportedPredicate().apply(signingMethod.getAlgorithm()) || !whitelistBlacklistPredicate.apply(signingMethod.getAlgorithm())) continue;
            for (Credential credential : credentials) {
                if (this.log.isTraceEnabled()) {
                    Key key = CredentialSupport.extractSigningKey(credential);
                    this.log.trace("Evaluating credential of type: {}, with length: {}", (Object)(key != null ? key.getAlgorithm() : "n/a"), (Object)KeySupport.getKeyLength(key));
                }
                if (this.credentialSupportsSigningMethod(credential, signingMethod)) {
                    this.log.trace("Credential passed eval against SigningMethod");
                    this.log.debug("Resolved signature algorithm URI from SAML metadata SigningMethod: {}", (Object)signingMethod.getAlgorithm());
                    params.setSigningCredential(credential);
                    params.setSignatureAlgorithm(signingMethod.getAlgorithm());
                    return;
                }
                this.log.trace("Credential failed eval against SigningMethod");
            }
        }
        this.log.debug("Could not resolve signing credential and algorithm based on SAML metadata, falling back to locally configured algorithms");
        super.resolveAndPopulateCredentialAndSignatureAlgorithm(params, criteria, whitelistBlacklistPredicate);
    }

    protected boolean credentialSupportsSigningMethod(@Nonnull Credential credential, @Nonnull @NotEmpty SigningMethod signingMethod) {
        if (!this.credentialSupportsAlgorithm(credential, signingMethod.getAlgorithm())) {
            return false;
        }
        if (signingMethod.getMinKeySize() != null || signingMethod.getMaxKeySize() != null) {
            Key signingKey = CredentialSupport.extractSigningKey(credential);
            if (signingKey == null) {
                this.log.warn("Could not extract signing key from credential. Failing evaluation");
                return false;
            }
            Integer keyLength = KeySupport.getKeyLength(signingKey);
            if (keyLength == null) {
                this.log.warn("Could not determine key length of candidate signing credential. Failing evaluation");
                return false;
            }
            if (signingMethod.getMinKeySize() != null && keyLength < signingMethod.getMinKeySize()) {
                this.log.trace("Candidate signing credential does not meet minKeySize requirement");
                return false;
            }
            if (signingMethod.getMaxKeySize() != null && keyLength > signingMethod.getMaxKeySize()) {
                this.log.trace("Candidate signing credential does not meet maxKeySize requirement");
                return false;
            }
        }
        return true;
    }

    @Override
    @Nullable
    protected String resolveReferenceDigestMethod(@Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        if (!criteria.contains(RoleDescriptorCriterion.class)) {
            return super.resolveReferenceDigestMethod(criteria, whitelistBlacklistPredicate);
        }
        List<XMLObject> digestMethods = this.getExtensions(criteria.get(RoleDescriptorCriterion.class).getRole(), DigestMethod.DEFAULT_ELEMENT_NAME);
        if (digestMethods == null || digestMethods.isEmpty()) {
            return super.resolveReferenceDigestMethod(criteria, whitelistBlacklistPredicate);
        }
        for (XMLObject xmlObject : digestMethods) {
            DigestMethod digestMethod = (DigestMethod)xmlObject;
            this.log.trace("Evaluating SAML metadata DigestMethod with algorithm: {}", (Object)digestMethod.getAlgorithm());
            if (digestMethod.getAlgorithm() == null || !this.getAlgorithmRuntimeSupportedPredicate().apply(digestMethod.getAlgorithm()) || !whitelistBlacklistPredicate.apply(digestMethod.getAlgorithm())) continue;
            this.log.debug("Resolved reference digest method algorithm URI from SAML metadata DigestMethod: {}", (Object)digestMethod.getAlgorithm());
            return digestMethod.getAlgorithm();
        }
        this.log.debug("Could not resolve signature reference digest method algorithm based on SAML metadata, falling back to locally configured algorithms");
        return super.resolveReferenceDigestMethod(criteria, whitelistBlacklistPredicate);
    }

    @Nullable
    protected List<XMLObject> getExtensions(@Nonnull RoleDescriptor roleDescriptor, @Nonnull QName extensionName) {
        List<XMLObject> result;
        Extensions extensions = roleDescriptor.getExtensions();
        if (extensions != null && !(result = extensions.getUnknownXMLObjects(extensionName)).isEmpty()) {
            this.log.trace("Resolved extensions from RoleDescriptor: {}", (Object)extensionName);
            return result;
        }
        if (roleDescriptor.getParent() instanceof EntityDescriptor && (extensions = ((EntityDescriptor)roleDescriptor.getParent()).getExtensions()) != null && !(result = extensions.getUnknownXMLObjects(extensionName)).isEmpty()) {
            this.log.trace("Resolved extensions from parent EntityDescriptor: {}", (Object)extensionName);
            return result;
        }
        return null;
    }
}

