/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.security.impl;

import com.google.common.base.Predicate;
import java.security.Key;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.saml.security.impl.MetadataCredentialResolver;
import org.opensaml.saml.security.impl.SAMLMDCredentialContext;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.crypto.KeySupport;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.KeyTransportAlgorithmPredicate;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.encryption.MGF;
import org.opensaml.xmlsec.encryption.OAEPparams;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.impl.BasicEncryptionParametersResolver;
import org.opensaml.xmlsec.signature.DigestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLMetadataEncryptionParametersResolver
extends BasicEncryptionParametersResolver {
    private Logger log = LoggerFactory.getLogger(SAMLMetadataEncryptionParametersResolver.class);
    private MetadataCredentialResolver credentialResolver;
    private boolean mergeMetadataRSAOAEPParametersWithConfig;

    public SAMLMetadataEncryptionParametersResolver(@Nonnull @ParameterName(name="resolver") MetadataCredentialResolver resolver2) {
        this.credentialResolver = Constraint.isNotNull(resolver2, "MetadataCredentialResoler may not be null");
    }

    public boolean isMergeMetadataRSAOAEPParametersWithConfig() {
        return this.mergeMetadataRSAOAEPParametersWithConfig;
    }

    public void setMergeMetadataRSAOAEPParametersWithConfig(boolean flag) {
        this.mergeMetadataRSAOAEPParametersWithConfig = flag;
    }

    @Nonnull
    protected MetadataCredentialResolver getMetadataCredentialResolver() {
        return this.credentialResolver;
    }

    @Override
    protected void resolveAndPopulateCredentialsAndAlgorithms(@Nonnull EncryptionParameters params, @Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        CriteriaSet mdCredResolverCriteria = new CriteriaSet();
        mdCredResolverCriteria.addAll(criteria);
        mdCredResolverCriteria.add(new UsageCriterion(UsageType.ENCRYPTION), true);
        try {
            for (Credential keyTransportCredential : this.getMetadataCredentialResolver().resolve(mdCredResolverCriteria)) {
                SAMLMDCredentialContext metadataCredContext;
                Pair<String, EncryptionMethod> dataEncryptionAlgorithmAndMethod;
                Pair<String, EncryptionMethod> keyTransportAlgorithmAndMethod;
                if (this.log.isTraceEnabled()) {
                    Key key = CredentialSupport.extractEncryptionKey(keyTransportCredential);
                    this.log.trace("Evaluating key transport encryption credential from SAML metadata of type: {}", (Object)(key != null ? key.getAlgorithm() : "n/a"));
                }
                if ((keyTransportAlgorithmAndMethod = this.resolveKeyTransportAlgorithm(keyTransportCredential, criteria, whitelistBlacklistPredicate, (dataEncryptionAlgorithmAndMethod = this.resolveDataEncryptionAlgorithm(criteria, whitelistBlacklistPredicate, metadataCredContext = keyTransportCredential.getCredentialContextSet().get(SAMLMDCredentialContext.class))).getFirst(), metadataCredContext)).getFirst() == null) {
                    this.log.debug("Unable to resolve key transport algorithm for credential with key type '{}', considering other credentials", (Object)CredentialSupport.extractEncryptionKey(keyTransportCredential).getAlgorithm());
                    continue;
                }
                params.setKeyTransportEncryptionCredential(keyTransportCredential);
                params.setKeyTransportEncryptionAlgorithm(keyTransportAlgorithmAndMethod.getFirst());
                params.setDataEncryptionAlgorithm(dataEncryptionAlgorithmAndMethod.getFirst());
                this.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate, keyTransportAlgorithmAndMethod.getSecond());
                this.processDataEncryptionCredentialAutoGeneration(params);
                return;
            }
        }
        catch (ResolverException e) {
            this.log.warn("Problem resolving credentials from metadata, falling back to local configuration", e);
        }
        this.log.debug("Could not resolve encryption parameters based on SAML metadata, falling back to locally configured credentials and algorithms");
        super.resolveAndPopulateCredentialsAndAlgorithms(params, criteria, whitelistBlacklistPredicate);
    }

    protected void resolveAndPopulateRSAOAEPParams(@Nonnull EncryptionParameters params, @Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate, @Nullable EncryptionMethod encryptionMethod) {
        if (!AlgorithmSupport.isRSAOAEP(params.getKeyTransportEncryptionAlgorithm())) {
            return;
        }
        if (encryptionMethod == null) {
            super.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate);
            return;
        }
        if (params.getRSAOAEPParameters() == null) {
            params.setRSAOAEPParameters(new RSAOAEPParameters());
        }
        this.populateRSAOAEPParamsFromEncryptionMethod(params.getRSAOAEPParameters(), encryptionMethod, whitelistBlacklistPredicate);
        if (params.getRSAOAEPParameters().isComplete()) {
            return;
        }
        if (params.getRSAOAEPParameters().isEmpty()) {
            super.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate);
        } else if (this.isMergeMetadataRSAOAEPParametersWithConfig()) {
            super.resolveAndPopulateRSAOAEPParams(params, criteria, whitelistBlacklistPredicate);
        }
    }

    protected void populateRSAOAEPParamsFromEncryptionMethod(@Nonnull RSAOAEPParameters params, @Nonnull EncryptionMethod encryptionMethod, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        String value;
        OAEPparams oaepParams;
        MGF mgf;
        String mgfAlgorithm;
        List<XMLObject> mgfs;
        DigestMethod digestMethod;
        String digestAlgorithm;
        Predicate<String> algoSupportPredicate = this.getAlgorithmRuntimeSupportedPredicate();
        List<XMLObject> digestMethods = encryptionMethod.getUnknownXMLObjects(DigestMethod.DEFAULT_ELEMENT_NAME);
        if (digestMethods.size() > 0 && (digestAlgorithm = StringSupport.trimOrNull((digestMethod = (DigestMethod)digestMethods.get(0)).getAlgorithm())) != null && whitelistBlacklistPredicate.apply(digestAlgorithm) && algoSupportPredicate.apply(digestAlgorithm)) {
            params.setDigestMethod(digestAlgorithm);
        }
        if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionMethod.getAlgorithm()) && (mgfs = encryptionMethod.getUnknownXMLObjects(MGF.DEFAULT_ELEMENT_NAME)).size() > 0 && (mgfAlgorithm = StringSupport.trimOrNull((mgf = (MGF)mgfs.get(0)).getAlgorithm())) != null && whitelistBlacklistPredicate.apply(mgfAlgorithm)) {
            params.setMaskGenerationFunction(mgfAlgorithm);
        }
        if ((oaepParams = encryptionMethod.getOAEPparams()) != null && (value = StringSupport.trimOrNull(oaepParams.getValue())) != null) {
            params.setOAEPparams(value);
        }
    }

    @Nonnull
    protected Pair<String, EncryptionMethod> resolveKeyTransportAlgorithm(@Nonnull Credential keyTransportCredential, @Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate, @Nullable String dataEncryptionAlgorithm, @Nullable SAMLMDCredentialContext metadataCredContext) {
        if (metadataCredContext != null) {
            KeyTransportAlgorithmPredicate keyTransportPredicate = this.resolveKeyTransportAlgorithmPredicate(criteria);
            for (EncryptionMethod encryptionMethod : metadataCredContext.getEncryptionMethods()) {
                String algorithm = encryptionMethod.getAlgorithm();
                this.log.trace("Evaluating SAML metadata EncryptionMethod algorithm for key transport: {}", (Object)algorithm);
                if (!this.isKeyTransportAlgorithm(algorithm) || !whitelistBlacklistPredicate.apply(algorithm) || !this.getAlgorithmRuntimeSupportedPredicate().apply(algorithm) || !this.credentialSupportsEncryptionMethod(keyTransportCredential, encryptionMethod) || !this.evaluateEncryptionMethodChildren(encryptionMethod, criteria, whitelistBlacklistPredicate)) continue;
                boolean accepted = true;
                if (keyTransportPredicate != null) {
                    accepted = keyTransportPredicate.apply(new KeyTransportAlgorithmPredicate.SelectionInput(algorithm, dataEncryptionAlgorithm, keyTransportCredential));
                }
                if (!accepted) continue;
                this.log.debug("Resolved key transport algorithm URI from SAML metadata EncryptionMethod: {}", (Object)algorithm);
                return new Pair<String, EncryptionMethod>(algorithm, encryptionMethod);
            }
        }
        this.log.debug("Could not resolve key transport algorithm based on SAML metadata, falling back to locally configured algorithms");
        return new Pair<String, Object>(super.resolveKeyTransportAlgorithm(keyTransportCredential, criteria, whitelistBlacklistPredicate, dataEncryptionAlgorithm), null);
    }

    @Nonnull
    protected Pair<String, EncryptionMethod> resolveDataEncryptionAlgorithm(@Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate, @Nullable SAMLMDCredentialContext metadataCredContext) {
        if (metadataCredContext != null) {
            for (EncryptionMethod encryptionMethod : metadataCredContext.getEncryptionMethods()) {
                String algorithm = encryptionMethod.getAlgorithm();
                this.log.trace("Evaluating SAML metadata EncryptionMethod algorithm for data encryption: {}", (Object)algorithm);
                if (!this.isDataEncryptionAlgorithm(algorithm) || !whitelistBlacklistPredicate.apply(algorithm) || !this.getAlgorithmRuntimeSupportedPredicate().apply(algorithm) || !this.evaluateEncryptionMethodChildren(encryptionMethod, criteria, whitelistBlacklistPredicate)) continue;
                this.log.debug("Resolved data encryption algorithm URI from SAML metadata EncryptionMethod: {}", (Object)algorithm);
                return new Pair<String, EncryptionMethod>(algorithm, encryptionMethod);
            }
        }
        this.log.debug("Could not resolve data encryption algorithm based on SAML metadata, falling back to locally configured algorithms");
        return new Pair<String, Object>(super.resolveDataEncryptionAlgorithm(null, criteria, whitelistBlacklistPredicate), null);
    }

    protected boolean evaluateEncryptionMethodChildren(@Nonnull EncryptionMethod encryptionMethod, @Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        switch (encryptionMethod.getAlgorithm()) {
            case "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p": 
            case "http://www.w3.org/2009/xmlenc11#rsa-oaep": {
                return this.evaluateRSAOAEPChildren(encryptionMethod, criteria, whitelistBlacklistPredicate);
            }
        }
        return true;
    }

    protected boolean evaluateRSAOAEPChildren(@Nonnull EncryptionMethod encryptionMethod, @Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        MGF mgf;
        String mgfAlgorithm;
        List<XMLObject> mgfs;
        DigestMethod digestMethod;
        String digestAlgorithm;
        Predicate<String> algoSupportPredicate = this.getAlgorithmRuntimeSupportedPredicate();
        List<XMLObject> digestMethods = encryptionMethod.getUnknownXMLObjects(DigestMethod.DEFAULT_ELEMENT_NAME);
        if (!(digestMethods.size() <= 0 || (digestAlgorithm = StringSupport.trimOrNull((digestMethod = (DigestMethod)digestMethods.get(0)).getAlgorithm())) == null || whitelistBlacklistPredicate.apply(digestAlgorithm) && algoSupportPredicate.apply(digestAlgorithm))) {
            this.log.debug("Rejecting RSA OAEP EncryptionMethod due to unsupported or disallowed DigestMethod: {}", (Object)digestAlgorithm);
            return false;
        }
        if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionMethod.getAlgorithm()) && (mgfs = encryptionMethod.getUnknownXMLObjects(MGF.DEFAULT_ELEMENT_NAME)).size() > 0 && (mgfAlgorithm = StringSupport.trimOrNull((mgf = (MGF)mgfs.get(0)).getAlgorithm())) != null && !whitelistBlacklistPredicate.apply(mgfAlgorithm)) {
            this.log.debug("Rejecting RSA OAEP EncryptionMethod due to disallowed MGF: {}", (Object)mgfAlgorithm);
            return false;
        }
        return true;
    }

    protected boolean credentialSupportsEncryptionMethod(@Nonnull Credential credential, @Nonnull @NotEmpty EncryptionMethod encryptionMethod) {
        if (!this.credentialSupportsAlgorithm(credential, encryptionMethod.getAlgorithm())) {
            return false;
        }
        if (encryptionMethod.getKeySize() != null && encryptionMethod.getKeySize().getValue() != null) {
            Key encryptionKey = CredentialSupport.extractEncryptionKey(credential);
            if (encryptionKey == null) {
                this.log.warn("Could not extract encryption key from credential. Failing evaluation");
                return false;
            }
            Integer keyLength = KeySupport.getKeyLength(encryptionKey);
            if (keyLength == null) {
                this.log.warn("Could not determine key length of candidate encryption credential. Failing evaluation");
                return false;
            }
            if (!keyLength.equals(encryptionMethod.getKeySize().getValue())) {
                return false;
            }
        }
        return true;
    }
}

