/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.builder.SAML1ComponentBuilder;
import org.apache.wss4j.common.saml.builder.SAML2ComponentBuilder;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.InetAddressUtils;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.utils.XMLUtils;
import org.joda.time.DateTime;
import org.joda.time.base.AbstractInstant;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLObjectContentReference;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectStatement;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.security.impl.SAMLSignatureProfileValidator;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.xmlsec.keyinfo.impl.X509KeyInfoGeneratorFactory;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureValidator;
import org.opensaml.xmlsec.signature.support.SignerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SamlAssertionWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(SamlAssertionWrapper.class);
    private SAMLObject samlObject;
    private SAMLVersion samlVersion;
    private Element assertionElement;
    private SAMLKeyInfo subjectKeyInfo;
    private SAMLKeyInfo signatureKeyInfo;
    private final String defaultCanonicalizationAlgorithm = "http://www.w3.org/2001/10/xml-exc-c14n#";
    private final String defaultRSASignatureAlgorithm = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
    private final String defaultDSASignatureAlgorithm = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
    private final String defaultECDSASignatureAlgorithm = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
    private final String defaultSignatureDigestAlgorithm = "http://www.w3.org/2000/09/xmldsig#sha1";
    private final boolean fromDOM;

    public SamlAssertionWrapper(Element element) throws WSSecurityException {
        OpenSAMLUtil.initSamlEngine();
        this.parseElement(element);
        this.fromDOM = true;
    }

    public SamlAssertionWrapper(SAMLObject samlObject) throws WSSecurityException {
        OpenSAMLUtil.initSamlEngine();
        this.samlObject = samlObject;
        if (samlObject instanceof Assertion) {
            this.samlVersion = SAMLVersion.VERSION_11;
        } else if (samlObject instanceof org.opensaml.saml.saml2.core.Assertion) {
            this.samlVersion = SAMLVersion.VERSION_20;
        } else {
            LOG.error("SamlAssertionWrapper: found unexpected type " + (samlObject != null ? samlObject.getClass().getName() : null));
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"A SAML 2.0 or 1.1 Assertion can only be used with SamlAssertionWrapper"});
        }
        this.fromDOM = false;
    }

    public SamlAssertionWrapper(SAMLCallback samlCallback) throws WSSecurityException {
        OpenSAMLUtil.initSamlEngine();
        if (samlCallback.getAssertionElement() != null) {
            this.parseElement(samlCallback.getAssertionElement());
            this.fromDOM = true;
        } else {
            this.parseCallback(samlCallback);
            this.fromDOM = false;
        }
    }

    public Assertion getSaml1() {
        if (this.samlVersion == SAMLVersion.VERSION_11) {
            return (Assertion)this.samlObject;
        }
        return null;
    }

    public org.opensaml.saml.saml2.core.Assertion getSaml2() {
        if (this.samlVersion == SAMLVersion.VERSION_20) {
            return (org.opensaml.saml.saml2.core.Assertion)this.samlObject;
        }
        return null;
    }

    public boolean isCreated() {
        return this.samlObject != null;
    }

    public Element toDOM(Document doc) throws WSSecurityException {
        if (this.fromDOM && this.assertionElement != null) {
            this.parseElement(this.assertionElement);
            if (doc != null) {
                return (Element)doc.importNode(this.assertionElement, true);
            }
            return this.assertionElement;
        }
        this.assertionElement = OpenSAMLUtil.toDom(this.samlObject, doc);
        return this.assertionElement;
    }

    public String assertionToString() throws WSSecurityException {
        if (this.assertionElement == null) {
            Element element = this.toDOM(null);
            return DOM2Writer.nodeToString(element);
        }
        return DOM2Writer.nodeToString(this.assertionElement);
    }

    public Instant getNotBefore() {
        DateTime validFrom = null;
        validFrom = this.getSamlVersion().equals(SAMLVersion.VERSION_20) ? this.getSaml2().getConditions().getNotBefore() : this.getSaml1().getConditions().getNotBefore();
        if (validFrom != null) {
            return validFrom.toDate().toInstant();
        }
        return null;
    }

    public Instant getNotOnOrAfter() {
        DateTime validTill = null;
        validTill = this.getSamlVersion().equals(SAMLVersion.VERSION_20) ? this.getSaml2().getConditions().getNotOnOrAfter() : this.getSaml1().getConditions().getNotOnOrAfter();
        if (validTill != null) {
            return validTill.toDate().toInstant();
        }
        return null;
    }

    public String getId() {
        String id = null;
        if (this.samlVersion == SAMLVersion.VERSION_20) {
            id = ((org.opensaml.saml.saml2.core.Assertion)this.samlObject).getID();
            if (id == null || id.length() == 0) {
                LOG.error("SamlAssertionWrapper: ID was null, seeting a new ID value");
                id = IDGenerator.generateID("_");
                ((org.opensaml.saml.saml2.core.Assertion)this.samlObject).setID(id);
            }
        } else if (this.samlVersion == SAMLVersion.VERSION_11) {
            id = ((Assertion)this.samlObject).getID();
            if (id == null || id.length() == 0) {
                LOG.error("SamlAssertionWrapper: ID was null, seeting a new ID value");
                id = IDGenerator.generateID("_");
                ((Assertion)this.samlObject).setID(id);
            }
        } else {
            LOG.error("SamlAssertionWrapper: unable to return ID - no saml assertion object");
        }
        return id;
    }

    public String getIssuerString() {
        if (this.samlVersion == SAMLVersion.VERSION_20 && ((org.opensaml.saml.saml2.core.Assertion)this.samlObject).getIssuer() != null) {
            return ((org.opensaml.saml.saml2.core.Assertion)this.samlObject).getIssuer().getValue();
        }
        if (this.samlVersion == SAMLVersion.VERSION_11 && ((Assertion)this.samlObject).getIssuer() != null) {
            return ((Assertion)this.samlObject).getIssuer();
        }
        LOG.error("SamlAssertionWrapper: unable to return Issuer string - no saml assertion object or issuer is null");
        return null;
    }

    public String getSubjectName() {
        if (this.samlVersion == SAMLVersion.VERSION_20) {
            org.opensaml.saml.saml2.core.Subject subject = ((org.opensaml.saml.saml2.core.Assertion)this.samlObject).getSubject();
            if (subject != null && subject.getNameID() != null) {
                return subject.getNameID().getValue();
            }
        } else if (this.samlVersion == SAMLVersion.VERSION_11) {
            Subject samlSubject = null;
            for (Statement stmt : ((Assertion)this.samlObject).getStatements()) {
                if (stmt instanceof org.opensaml.saml.saml1.core.AttributeStatement) {
                    org.opensaml.saml.saml1.core.AttributeStatement attrStmt = (org.opensaml.saml.saml1.core.AttributeStatement)stmt;
                    samlSubject = attrStmt.getSubject();
                } else if (stmt instanceof AuthenticationStatement) {
                    AuthenticationStatement authStmt = (AuthenticationStatement)stmt;
                    samlSubject = authStmt.getSubject();
                } else {
                    AuthorizationDecisionStatement authzStmt = (AuthorizationDecisionStatement)stmt;
                    samlSubject = authzStmt.getSubject();
                }
                if (samlSubject == null) continue;
                break;
            }
            if (samlSubject != null && samlSubject.getNameIdentifier() != null) {
                return samlSubject.getNameIdentifier().getValue();
            }
        }
        LOG.error("SamlAssertionWrapper: unable to return SubjectName - no saml assertion object or subject is null");
        return null;
    }

    public List<String> getConfirmationMethods() {
        ArrayList<String> methods;
        block5: {
            block4: {
                methods = new ArrayList<String>();
                if (this.samlVersion != SAMLVersion.VERSION_20) break block4;
                org.opensaml.saml.saml2.core.Subject subject = ((org.opensaml.saml.saml2.core.Assertion)this.samlObject).getSubject();
                List<SubjectConfirmation> confirmations = subject.getSubjectConfirmations();
                for (SubjectConfirmation confirmation : confirmations) {
                    methods.add(confirmation.getMethod());
                }
                break block5;
            }
            if (this.samlVersion != SAMLVersion.VERSION_11) break block5;
            ArrayList<SubjectStatement> subjectStatements = new ArrayList<SubjectStatement>();
            Assertion saml1 = (Assertion)this.samlObject;
            subjectStatements.addAll(saml1.getSubjectStatements());
            subjectStatements.addAll(saml1.getAuthenticationStatements());
            subjectStatements.addAll(saml1.getAttributeStatements());
            subjectStatements.addAll(saml1.getAuthorizationDecisionStatements());
            for (SubjectStatement subjectStatement : subjectStatements) {
                org.opensaml.saml.saml1.core.SubjectConfirmation confirmation;
                Subject subject = subjectStatement.getSubject();
                if (subject == null || (confirmation = subject.getSubjectConfirmation()) == null) continue;
                XMLObject data = confirmation.getSubjectConfirmationData();
                if (data instanceof ConfirmationMethod) {
                    ConfirmationMethod method2 = (ConfirmationMethod)data;
                    methods.add(method2.getConfirmationMethod());
                }
                List<ConfirmationMethod> confirmationMethods = confirmation.getConfirmationMethods();
                for (ConfirmationMethod confirmationMethod : confirmationMethods) {
                    methods.add(confirmationMethod.getConfirmationMethod());
                }
            }
        }
        return methods;
    }

    public boolean isSigned() {
        return this.samlObject instanceof SignableSAMLObject && (((SignableSAMLObject)this.samlObject).isSigned() || ((SignableSAMLObject)this.samlObject).getSignature() != null);
    }

    public void setSignature(Signature signature) {
        this.setSignature(signature, "http://www.w3.org/2000/09/xmldsig#sha1");
    }

    public void setSignature(Signature signature, String signatureDigestAlgorithm) {
        if (this.samlObject instanceof SignableSAMLObject) {
            SignableSAMLObject signableObject = (SignableSAMLObject)this.samlObject;
            signableObject.setSignature(signature);
            String digestAlg = signatureDigestAlgorithm;
            if (digestAlg == null) {
                digestAlg = "http://www.w3.org/2000/09/xmldsig#sha1";
            }
            SAMLObjectContentReference contentRef = (SAMLObjectContentReference)signature.getContentReferences().get(0);
            contentRef.setDigestAlgorithm(digestAlg);
            signableObject.releaseDOM();
            signableObject.releaseChildrenDOM(true);
        } else {
            LOG.error("Attempt to sign an unsignable object " + this.samlObject.getClass().getName());
        }
    }

    public void signAssertion(String issuerKeyName, String issuerKeyPassword, Crypto issuerCrypto, boolean sendKeyValue) throws WSSecurityException {
        this.signAssertion(issuerKeyName, issuerKeyPassword, issuerCrypto, sendKeyValue, "http://www.w3.org/2001/10/xml-exc-c14n#", "http://www.w3.org/2000/09/xmldsig#rsa-sha1", "http://www.w3.org/2000/09/xmldsig#sha1");
    }

    public void signAssertion(String issuerKeyName, String issuerKeyPassword, Crypto issuerCrypto, boolean sendKeyValue, String canonicalizationAlgorithm, String signatureAlgorithm) throws WSSecurityException {
        this.signAssertion(issuerKeyName, issuerKeyPassword, issuerCrypto, sendKeyValue, canonicalizationAlgorithm, signatureAlgorithm, "http://www.w3.org/2000/09/xmldsig#sha1");
    }

    public void signAssertion(String issuerKeyName, String issuerKeyPassword, Crypto issuerCrypto, boolean sendKeyValue, String canonicalizationAlgorithm, String signatureAlgorithm, String signatureDigestAlgorithm) throws WSSecurityException {
        PrivateKey privateKey;
        Signature signature = OpenSAMLUtil.buildSignature();
        String c14nAlgo = canonicalizationAlgorithm;
        if (c14nAlgo == null) {
            c14nAlgo = "http://www.w3.org/2001/10/xml-exc-c14n#";
        }
        signature.setCanonicalizationAlgorithm(c14nAlgo);
        LOG.debug("Using Canonicalization algorithm {}", (Object)c14nAlgo);
        CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
        cryptoType.setAlias(issuerKeyName);
        X509Certificate[] issuerCerts = null;
        if (issuerCrypto != null) {
            issuerCerts = issuerCrypto.getX509Certificates(cryptoType);
        }
        if (issuerCerts == null || issuerCerts.length == 0) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"No issuer certs were found to sign the SAML Assertion using issuer name: " + issuerKeyName});
        }
        String sigAlgo = signatureAlgorithm;
        if (sigAlgo == null) {
            sigAlgo = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
        }
        String pubKeyAlgo = issuerCerts[0].getPublicKey().getAlgorithm();
        LOG.debug("automatic sig algo detection: {}", (Object)pubKeyAlgo);
        if (pubKeyAlgo.equalsIgnoreCase("DSA")) {
            sigAlgo = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
        } else if (pubKeyAlgo.equalsIgnoreCase("EC")) {
            sigAlgo = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
        }
        LOG.debug("Using Signature algorithm {}", (Object)sigAlgo);
        try {
            privateKey = issuerCrypto.getPrivateKey(issuerKeyName, issuerKeyPassword);
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        if (privateKey == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"No private key was found using issuer name: " + issuerKeyName});
        }
        signature.setSignatureAlgorithm(sigAlgo);
        BasicX509Credential signingCredential = new BasicX509Credential(issuerCerts[0], privateKey);
        signature.setSigningCredential(signingCredential);
        X509KeyInfoGeneratorFactory kiFactory = new X509KeyInfoGeneratorFactory();
        if (sendKeyValue) {
            kiFactory.setEmitPublicKeyValue(true);
        } else {
            kiFactory.setEmitEntityCertificate(true);
        }
        try {
            KeyInfo keyInfo = kiFactory.newInstance().generate(signingCredential);
            signature.setKeyInfo(keyInfo);
        }
        catch (SecurityException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error generating KeyInfo from signing credential"});
        }
        this.setSignature(signature, signatureDigestAlgorithm);
    }

    public void verifySignature(SAMLKeyInfoProcessor keyInfoProcessor, Crypto sigCrypto) throws WSSecurityException {
        Signature sig = this.getSignature();
        if (sig != null) {
            KeyInfo keyInfo = sig.getKeyInfo();
            if (keyInfo == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
            }
            SAMLKeyInfo samlKeyInfo = SAMLUtil.getCredentialFromKeyInfo(keyInfo.getDOM(), keyInfoProcessor, sigCrypto);
            this.verifySignature(samlKeyInfo);
        } else {
            LOG.debug("SamlAssertionWrapper: no signature to validate");
        }
    }

    public void verifySignature(SAMLKeyInfo samlKeyInfo) throws WSSecurityException {
        Signature sig = this.getSignature();
        if (sig != null) {
            if (samlKeyInfo == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
            }
            BasicCredential credential = null;
            if (samlKeyInfo.getCerts() != null) {
                credential = new BasicX509Credential(samlKeyInfo.getCerts()[0]);
            } else if (samlKeyInfo.getPublicKey() != null) {
                credential = new BasicCredential(samlKeyInfo.getPublicKey());
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
            }
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(SignerProvider.class.getClassLoader());
                SignatureValidator.validate(sig, credential);
            }
            catch (SignatureException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"SAML signature validation failed"});
            }
            finally {
                Thread.currentThread().setContextClassLoader(loader);
            }
            this.signatureKeyInfo = samlKeyInfo;
        } else {
            LOG.debug("SamlAssertionWrapper: no signature to validate");
        }
    }

    public void validateSignatureAgainstProfile() throws WSSecurityException {
        Signature sig = this.getSignature();
        if (sig != null) {
            SAMLSignatureProfileValidator validator = new SAMLSignatureProfileValidator();
            try {
                validator.validate(sig);
            }
            catch (SignatureException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"SAML signature validation failed"});
            }
        }
    }

    public void parseSubject(SAMLKeyInfoProcessor keyInfoProcessor, Crypto sigCrypto, CallbackHandler callbackHandler) throws WSSecurityException {
        if (this.samlVersion == SAMLVersion.VERSION_11) {
            this.subjectKeyInfo = SAMLUtil.getCredentialFromSubject((Assertion)this.samlObject, keyInfoProcessor, sigCrypto, callbackHandler);
        } else if (this.samlVersion == SAMLVersion.VERSION_20) {
            this.subjectKeyInfo = SAMLUtil.getCredentialFromSubject((org.opensaml.saml.saml2.core.Assertion)this.samlObject, keyInfoProcessor, sigCrypto, callbackHandler);
        }
    }

    public SAMLVersion getSamlVersion() {
        if (this.samlVersion == null) {
            LOG.debug("The SAML version was null in getSamlVersion(). Recomputing SAML version...");
            if (this.samlObject instanceof Assertion) {
                this.samlVersion = SAMLVersion.VERSION_11;
            } else if (this.samlObject instanceof org.opensaml.saml.saml2.core.Assertion) {
                this.samlVersion = SAMLVersion.VERSION_20;
            } else {
                throw new IllegalStateException("Could not determine the SAML version number. Check your configuration and try again.");
            }
        }
        return this.samlVersion;
    }

    public Element getElement() {
        return this.assertionElement;
    }

    public SAMLKeyInfo getSignatureKeyInfo() {
        return this.signatureKeyInfo;
    }

    public SAMLKeyInfo getSubjectKeyInfo() {
        return this.subjectKeyInfo;
    }

    public byte[] getSignatureValue() throws WSSecurityException {
        Signature sig = null;
        if (this.samlObject instanceof SignableSAMLObject) {
            sig = ((SignableSAMLObject)this.samlObject).getSignature();
        }
        if (sig != null) {
            return this.getSignatureValue(sig);
        }
        return null;
    }

    private byte[] getSignatureValue(Signature signature) throws WSSecurityException {
        Element signatureValueElement;
        Element signedInfoElem;
        Element signatureElement = signature.getDOM();
        if (signatureElement != null && (signedInfoElem = XMLUtils.getNextElement(signatureElement.getFirstChild())) != null && (signatureValueElement = XMLUtils.getNextElement(signedInfoElem.getNextSibling())) != null) {
            String base64Input = XMLUtils.getFullTextChildrenFromElement(signatureValueElement);
            return XMLUtils.decode(base64Input);
        }
        return null;
    }

    public Signature getSignature() throws WSSecurityException {
        if (this.samlObject instanceof SignableSAMLObject) {
            return ((SignableSAMLObject)this.samlObject).getSignature();
        }
        return null;
    }

    public SAMLObject getSamlObject() {
        return this.samlObject;
    }

    public void checkConditions(int futureTTL) throws WSSecurityException {
        DateTime validFrom = null;
        AbstractInstant validTill = null;
        if (this.getSamlVersion().equals(SAMLVersion.VERSION_20) && this.getSaml2().getConditions() != null) {
            validFrom = this.getSaml2().getConditions().getNotBefore();
            validTill = this.getSaml2().getConditions().getNotOnOrAfter();
        } else if (this.getSamlVersion().equals(SAMLVersion.VERSION_11) && this.getSaml1().getConditions() != null) {
            validFrom = this.getSaml1().getConditions().getNotBefore();
            validTill = this.getSaml1().getConditions().getNotOnOrAfter();
        }
        if (validFrom != null) {
            DateTime currentTime = new DateTime();
            if (validFrom.isAfter(currentTime = currentTime.plusSeconds(futureTTL))) {
                LOG.debug("SAML Token condition (Not Before) not met");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
        }
        if (validTill != null && validTill.isBeforeNow()) {
            LOG.debug("SAML Token condition (Not On Or After) not met");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
        }
    }

    public void checkIssueInstant(int futureTTL, int ttl) throws WSSecurityException {
        DateTime issueInstant = null;
        DateTime validTill = null;
        if (this.getSamlVersion().equals(SAMLVersion.VERSION_20) && this.getSaml2().getConditions() != null) {
            validTill = this.getSaml2().getConditions().getNotOnOrAfter();
            issueInstant = this.getSaml2().getIssueInstant();
        } else if (this.getSamlVersion().equals(SAMLVersion.VERSION_11) && this.getSaml1().getConditions() != null) {
            validTill = this.getSaml1().getConditions().getNotOnOrAfter();
            issueInstant = this.getSaml1().getIssueInstant();
        }
        if (issueInstant != null) {
            DateTime currentTime = new DateTime().plusSeconds(futureTTL);
            if (issueInstant.isAfter(currentTime)) {
                LOG.debug("SAML Token IssueInstant not met");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
            if (validTill == null && issueInstant.isBefore(currentTime = new DateTime().minusSeconds(ttl))) {
                LOG.debug("SAML Token IssueInstant not met. The assertion was created too long ago.");
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
        }
    }

    public void checkAudienceRestrictions(List<String> audienceRestrictions) throws WSSecurityException {
        Conditions conditions;
        if (audienceRestrictions == null || audienceRestrictions.isEmpty()) {
            return;
        }
        if (this.getSamlVersion().equals(SAMLVersion.VERSION_20) && this.getSaml2().getConditions() != null) {
            org.opensaml.saml.saml2.core.Conditions conditions2 = this.getSaml2().getConditions();
            if (conditions2 != null && conditions2.getAudienceRestrictions() != null && !conditions2.getAudienceRestrictions().isEmpty()) {
                boolean foundAddress = false;
                block0: for (AudienceRestriction audienceRestriction : conditions2.getAudienceRestrictions()) {
                    if (audienceRestriction.getAudiences() == null) continue;
                    List<Audience> audiences = audienceRestriction.getAudiences();
                    for (Audience audience : audiences) {
                        String audienceURI = audience.getAudienceURI();
                        if (!audienceRestrictions.contains(audienceURI)) continue;
                        foundAddress = true;
                        continue block0;
                    }
                }
                if (!foundAddress) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
                }
            }
        } else if (this.getSamlVersion().equals(SAMLVersion.VERSION_11) && this.getSaml1().getConditions() != null && (conditions = this.getSaml1().getConditions()) != null && conditions.getAudienceRestrictionConditions() != null && !conditions.getAudienceRestrictionConditions().isEmpty()) {
            boolean foundAddress = false;
            block2: for (AudienceRestrictionCondition audienceRestriction : conditions.getAudienceRestrictionConditions()) {
                if (audienceRestriction.getAudiences() == null) continue;
                List<org.opensaml.saml.saml1.core.Audience> audiences = audienceRestriction.getAudiences();
                for (org.opensaml.saml.saml1.core.Audience audience : audiences) {
                    String audienceURI = audience.getUri();
                    if (!audienceRestrictions.contains(audienceURI)) continue;
                    foundAddress = true;
                    continue block2;
                }
            }
            if (!foundAddress) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
            }
        }
    }

    public void checkAuthnStatements(int futureTTL) throws WSSecurityException {
        block5: {
            block4: {
                if (!this.getSamlVersion().equals(SAMLVersion.VERSION_20) || this.getSaml2().getAuthnStatements() == null) break block4;
                List<AuthnStatement> authnStatements = this.getSaml2().getAuthnStatements();
                for (AuthnStatement authnStatement : authnStatements) {
                    DateTime authnInstant = authnStatement.getAuthnInstant();
                    DateTime sessionNotOnOrAfter = authnStatement.getSessionNotOnOrAfter();
                    String subjectLocalityAddress = null;
                    if (authnStatement.getSubjectLocality() != null && authnStatement.getSubjectLocality().getAddress() != null) {
                        subjectLocalityAddress = authnStatement.getSubjectLocality().getAddress();
                    }
                    this.validateAuthnStatement(authnInstant, sessionNotOnOrAfter, subjectLocalityAddress, futureTTL);
                }
                break block5;
            }
            if (!this.getSamlVersion().equals(SAMLVersion.VERSION_11) || this.getSaml1().getAuthenticationStatements() == null) break block5;
            List<AuthenticationStatement> authnStatements = this.getSaml1().getAuthenticationStatements();
            for (AuthenticationStatement authnStatement : authnStatements) {
                DateTime authnInstant = authnStatement.getAuthenticationInstant();
                String subjectLocalityAddress = null;
                if (authnStatement.getSubjectLocality() != null && authnStatement.getSubjectLocality().getIPAddress() != null) {
                    subjectLocalityAddress = authnStatement.getSubjectLocality().getIPAddress();
                }
                this.validateAuthnStatement(authnInstant, null, subjectLocalityAddress, futureTTL);
            }
        }
    }

    private void validateAuthnStatement(DateTime authnInstant, DateTime sessionNotOnOrAfter, String subjectLocalityAddress, int futureTTL) throws WSSecurityException {
        DateTime currentTime = new DateTime();
        if (authnInstant.isAfter(currentTime = currentTime.plusSeconds(futureTTL))) {
            LOG.debug("SAML Token AuthnInstant not met");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
        }
        if (sessionNotOnOrAfter != null && sessionNotOnOrAfter.isBeforeNow()) {
            LOG.debug("SAML Token SessionNotOnOrAfter not met");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
        }
        if (subjectLocalityAddress != null && !InetAddressUtils.isIPv4Address(subjectLocalityAddress) && !InetAddressUtils.isIPv6Address(subjectLocalityAddress)) {
            LOG.debug("SAML Token SubjectLocality address is not valid: " + subjectLocalityAddress);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity");
        }
    }

    private void parseElement(Element element) throws WSSecurityException {
        XMLObject xmlObject = OpenSAMLUtil.fromDom(element);
        if (xmlObject instanceof Assertion) {
            this.samlObject = (SAMLObject)xmlObject;
            this.samlVersion = SAMLVersion.VERSION_11;
        } else if (xmlObject instanceof org.opensaml.saml.saml2.core.Assertion) {
            this.samlObject = (SAMLObject)xmlObject;
            this.samlVersion = SAMLVersion.VERSION_20;
        } else {
            LOG.error("SamlAssertionWrapper: found unexpected type " + xmlObject.getClass().getName());
        }
        this.assertionElement = element;
    }

    private void parseCallback(SAMLCallback samlCallback) throws WSSecurityException {
        this.samlVersion = samlCallback.getSamlVersion();
        if (this.samlVersion == null) {
            this.samlVersion = SAMLVersion.VERSION_20;
        }
        String issuer = samlCallback.getIssuer();
        String issuerFormat = samlCallback.getIssuerFormat();
        String issuerQualifier = samlCallback.getIssuerQualifier();
        if (this.samlVersion.equals(SAMLVersion.VERSION_11)) {
            Assertion saml1 = SAML1ComponentBuilder.createSamlv1Assertion(issuer);
            try {
                List<AuthenticationStatement> authenticationStatements = SAML1ComponentBuilder.createSamlv1AuthenticationStatement(samlCallback.getAuthenticationStatementData());
                saml1.getAuthenticationStatements().addAll(authenticationStatements);
                List<org.opensaml.saml.saml1.core.AttributeStatement> attributeStatements = SAML1ComponentBuilder.createSamlv1AttributeStatement(samlCallback.getAttributeStatementData());
                saml1.getAttributeStatements().addAll(attributeStatements);
                List<AuthorizationDecisionStatement> authDecisionStatements = SAML1ComponentBuilder.createSamlv1AuthorizationDecisionStatement(samlCallback.getAuthDecisionStatementData());
                saml1.getAuthorizationDecisionStatements().addAll(authDecisionStatements);
                Conditions conditions = SAML1ComponentBuilder.createSamlv1Conditions(samlCallback.getConditions());
                saml1.setConditions(conditions);
                if (samlCallback.getAdvice() != null) {
                    Advice advice = SAML1ComponentBuilder.createAdvice(samlCallback.getAdvice());
                    saml1.setAdvice(advice);
                }
            }
            catch (SecurityException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error generating KeyInfo from signing credential"});
            }
            this.samlObject = saml1;
        } else if (this.samlVersion.equals(SAMLVersion.VERSION_20)) {
            org.opensaml.saml.saml2.core.Assertion saml2 = SAML2ComponentBuilder.createAssertion();
            Issuer samlIssuer = SAML2ComponentBuilder.createIssuer(issuer, issuerFormat, issuerQualifier);
            List<AuthnStatement> authnStatements = SAML2ComponentBuilder.createAuthnStatement(samlCallback.getAuthenticationStatementData());
            saml2.getAuthnStatements().addAll(authnStatements);
            List<AttributeStatement> attributeStatements = SAML2ComponentBuilder.createAttributeStatement(samlCallback.getAttributeStatementData());
            saml2.getAttributeStatements().addAll(attributeStatements);
            List<AuthzDecisionStatement> authDecisionStatements = SAML2ComponentBuilder.createAuthorizationDecisionStatement(samlCallback.getAuthDecisionStatementData());
            saml2.getAuthzDecisionStatements().addAll(authDecisionStatements);
            saml2.setIssuer(samlIssuer);
            try {
                org.opensaml.saml.saml2.core.Subject subject = SAML2ComponentBuilder.createSaml2Subject(samlCallback.getSubject());
                saml2.setSubject(subject);
            }
            catch (SecurityException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error generating KeyInfo from signing credential"});
            }
            org.opensaml.saml.saml2.core.Conditions conditions = SAML2ComponentBuilder.createConditions(samlCallback.getConditions());
            saml2.setConditions(conditions);
            if (samlCallback.getAdvice() != null) {
                org.opensaml.saml.saml2.core.Advice advice = SAML2ComponentBuilder.createAdvice(samlCallback.getAdvice());
                saml2.setAdvice(advice);
            }
            this.samlObject = saml2;
        }
    }
}

