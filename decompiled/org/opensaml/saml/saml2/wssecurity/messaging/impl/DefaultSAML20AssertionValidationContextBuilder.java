/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.saml2.wssecurity.messaging.impl;

import com.google.common.base.Function;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.wssecurity.messaging.impl.SAML20AssertionTokenValidationInput;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.messaging.ServletRequestX509CredentialAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSAML20AssertionValidationContextBuilder
implements Function<SAML20AssertionTokenValidationInput, ValidationContext> {
    private Logger log = LoggerFactory.getLogger(DefaultSAML20AssertionValidationContextBuilder.class);
    private Function<Pair<MessageContext, Assertion>, CriteriaSet> signatureCriteriaSetFunction;
    private boolean signatureRequired = true;

    public boolean isSignatureRequired() {
        return this.signatureRequired;
    }

    public void setSignatureRequired(boolean flag) {
        this.signatureRequired = flag;
    }

    @Nullable
    public Function<Pair<MessageContext, Assertion>, CriteriaSet> getSignatureCriteriaSetFunction() {
        return this.signatureCriteriaSetFunction;
    }

    public void setSignatureCriteriaSetFunction(@Nullable Function<Pair<MessageContext, Assertion>, CriteriaSet> function) {
        this.signatureCriteriaSetFunction = function;
    }

    @Override
    @Nullable
    public ValidationContext apply(@Nullable SAML20AssertionTokenValidationInput input) {
        if (input == null) {
            return null;
        }
        return new ValidationContext(this.buildStaticParameters(input));
    }

    @Nonnull
    protected Map<String, Object> buildStaticParameters(@Nonnull SAML20AssertionTokenValidationInput input) {
        PublicKey attesterPublicKey;
        HashMap<String, Object> staticParams = new HashMap<String, Object>();
        staticParams.put("saml2.SignatureRequired", new Boolean(this.isSignatureRequired()));
        staticParams.put("saml2.SignatureValidationCriteriaSet", this.getSignatureCriteriaSet(input));
        X509Certificate attesterCertificate = this.getAttesterCertificate(input);
        if (attesterCertificate != null) {
            staticParams.put("saml2.SubjectConfirmation.HoK.PresenterCertificate", attesterCertificate);
        }
        if ((attesterPublicKey = this.getAttesterPublicKey(input)) != null) {
            staticParams.put("saml2.SubjectConfirmation.HoK.PresenterKey", attesterPublicKey);
        }
        staticParams.put("saml2.SubjectConfirmation.ValidRecipients", this.getValidRecipients(input));
        staticParams.put("saml2.SubjectConfirmation.ValidAddresses", this.getValidAddresses(input));
        staticParams.put("saml2.Conditions.ValidAudiences", this.getValidAudiences(input));
        this.log.trace("Built static parameters map: {}", (Object)staticParams);
        return staticParams;
    }

    @Nonnull
    protected CriteriaSet getSignatureCriteriaSet(@Nonnull SAML20AssertionTokenValidationInput input) {
        CriteriaSet dynamicCriteria;
        CriteriaSet criteriaSet = new CriteriaSet();
        if (this.getSignatureCriteriaSetFunction() != null && (dynamicCriteria = this.getSignatureCriteriaSetFunction().apply(new Pair())) != null) {
            criteriaSet.addAll(dynamicCriteria);
        }
        if (!criteriaSet.contains(EntityIdCriterion.class)) {
            String issuer = null;
            if (input.getAssertion().getIssuer() != null) {
                issuer = StringSupport.trimOrNull(input.getAssertion().getIssuer().getValue());
            }
            if (issuer != null) {
                this.log.debug("Adding internally-generated EntityIdCriterion with value of: {}", (Object)issuer);
                criteriaSet.add(new EntityIdCriterion(issuer));
            }
        }
        if (!criteriaSet.contains(UsageCriterion.class)) {
            this.log.debug("Adding internally-generated UsageCriterion with value of: {}", (Object)UsageType.SIGNING);
            criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
        }
        this.log.debug("Resolved Signature validation CriteriaSet: {}", (Object)criteriaSet);
        return criteriaSet;
    }

    @Nullable
    protected X509Certificate getAttesterCertificate(@Nonnull SAML20AssertionTokenValidationInput input) {
        try {
            ServletRequestX509CredentialAdapter credential = new ServletRequestX509CredentialAdapter((ServletRequest)input.getHttpServletRequest());
            return credential.getEntityCertificate();
        }
        catch (SecurityException e) {
            this.log.warn("Peer TLS X.509 certificate was not present. Holder-of-key proof-of-possession via client TLS cert will not be possible");
            return null;
        }
    }

    @Nullable
    protected PublicKey getAttesterPublicKey(@Nonnull SAML20AssertionTokenValidationInput input) {
        return null;
    }

    @Nonnull
    protected Set<String> getValidRecipients(@Nonnull SAML20AssertionTokenValidationInput input) {
        LazySet<String> validRecipients = new LazySet<String>();
        String endpoint = input.getHttpServletRequest().getRequestURL().toString();
        validRecipients.add(endpoint);
        SAMLSelfEntityContext selfContext = (SAMLSelfEntityContext)input.getMessageContext().getSubcontext(SAMLSelfEntityContext.class);
        if (selfContext != null && selfContext.getEntityId() != null) {
            validRecipients.add(selfContext.getEntityId());
        }
        this.log.debug("Resolved valid subject confirmation recipients set: {}", (Object)validRecipients);
        return validRecipients;
    }

    @Nonnull
    protected Set<InetAddress> getValidAddresses(@Nonnull SAML20AssertionTokenValidationInput input) {
        try {
            LazySet<InetAddress> validAddresses = new LazySet<InetAddress>();
            InetAddress[] addresses = null;
            String attesterIPAddress = this.getAttesterIPAddress(input);
            this.log.debug("Saw attester IP address: {}", (Object)attesterIPAddress);
            if (attesterIPAddress != null) {
                addresses = InetAddress.getAllByName(attesterIPAddress);
                validAddresses.addAll((Collection<InetAddress>)Arrays.asList(addresses));
                this.log.debug("Resolved valid subject confirmation InetAddress set: {}", (Object)validAddresses);
                return validAddresses;
            }
            this.log.warn("Could not determine attester IP address. Validation of Assertion may or may not succeed");
            return Collections.emptySet();
        }
        catch (UnknownHostException e) {
            this.log.warn("Processing of attester IP address failed. Validation of Assertion may or may not succeed", e);
            return Collections.emptySet();
        }
    }

    @Nonnull
    protected String getAttesterIPAddress(@Nonnull SAML20AssertionTokenValidationInput input) {
        return input.getHttpServletRequest().getRemoteAddr();
    }

    @Nonnull
    protected Set<String> getValidAudiences(@Nonnull SAML20AssertionTokenValidationInput input) {
        LazySet<String> validAudiences = new LazySet<String>();
        SAMLSelfEntityContext selfContext = (SAMLSelfEntityContext)input.getMessageContext().getSubcontext(SAMLSelfEntityContext.class);
        if (selfContext != null && selfContext.getEntityId() != null) {
            validAudiences.add(selfContext.getEntityId());
        }
        this.log.debug("Resolved valid audiences set: {}", (Object)validAudiences);
        return validAudiences;
    }
}

