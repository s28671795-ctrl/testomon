/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SignatureAlgorithmValidator {
    private static final QName ELEMENT_NAME_SIGNED_INFO = new QName("http://www.w3.org/2000/09/xmldsig#", "SignedInfo");
    private static final QName ELEMENT_NAME_SIGNATURE_METHOD = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod");
    private static final QName ELEMENT_NAME_REFERENCE = new QName("http://www.w3.org/2000/09/xmldsig#", "Reference");
    private static final QName ELEMENT_NAME_DIGEST_METHOD = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
    private static final String ATTR_NAME_ALGORTHM = "Algorithm";
    private Logger log = LoggerFactory.getLogger(SignatureAlgorithmValidator.class);
    private Collection<String> whitelistedAlgorithmURIs;
    private Collection<String> blacklistedAlgorithmURIs;

    public SignatureAlgorithmValidator(@Nonnull @ParameterName(name="params") SignatureValidationParameters params) {
        Constraint.isNotNull(params, "SignatureValidationParameters may not be null");
        this.whitelistedAlgorithmURIs = params.getWhitelistedAlgorithms();
        this.blacklistedAlgorithmURIs = params.getBlacklistedAlgorithms();
    }

    public SignatureAlgorithmValidator(@Nullable @ParameterName(name="whitelistAlgos") Collection<String> whitelistAlgos, @Nullable @ParameterName(name="blacklistAlgos") Collection<String> blacklistAlgos) {
        this.whitelistedAlgorithmURIs = whitelistAlgos;
        this.blacklistedAlgorithmURIs = blacklistAlgos;
    }

    public void validate(@Nonnull Signature signature) throws SignatureException {
        Constraint.isNotNull(signature, "Signature was null");
        this.checkDOM(signature);
        String signatureAlgorithm = this.getSignatureAlgorithm(signature);
        this.log.debug("Validating SignedInfo/SignatureMethod/@Algorithm against whitelist/blacklist: {}", (Object)signatureAlgorithm);
        this.validateAlgorithmURI(signatureAlgorithm);
        for (String digestMethod : this.getDigestMethods(signature)) {
            this.log.debug("Validating SignedInfo/Reference/DigestMethod/@Algorithm against whitelist/blacklist: {}", (Object)digestMethod);
            this.validateAlgorithmURI(digestMethod);
        }
    }

    protected void checkDOM(@Nonnull Signature signature) throws SignatureException {
        if (signature.getDOM() == null) {
            this.log.warn("Signgaure does not have a cached DOM Element.");
            throw new SignatureException("Signature does not have a cached DOM Element.");
        }
    }

    @Nonnull
    protected String getSignatureAlgorithm(@Nonnull Signature signatureXMLObject) throws SignatureException {
        Element signature = signatureXMLObject.getDOM();
        Element signedInfo = ElementSupport.getFirstChildElement(signature, ELEMENT_NAME_SIGNED_INFO);
        Element signatureMethod = ElementSupport.getFirstChildElement(signedInfo, ELEMENT_NAME_SIGNATURE_METHOD);
        String signatureMethodAlgorithm = StringSupport.trimOrNull(AttributeSupport.getAttributeValue(signatureMethod, null, ATTR_NAME_ALGORTHM));
        if (signatureMethodAlgorithm != null) {
            return signatureMethodAlgorithm;
        }
        throw new SignatureException("SignatureMethod Algorithm was null");
    }

    @Nonnull
    protected List<String> getDigestMethods(@Nonnull Signature signatureXMLObject) throws SignatureException {
        ArrayList<String> digestMethodAlgorithms = new ArrayList<String>();
        Element signature = signatureXMLObject.getDOM();
        Element signedInfo = ElementSupport.getFirstChildElement(signature, ELEMENT_NAME_SIGNED_INFO);
        for (Element reference : ElementSupport.getChildElements(signedInfo, ELEMENT_NAME_REFERENCE)) {
            Element digestMethod = ElementSupport.getFirstChildElement(reference, ELEMENT_NAME_DIGEST_METHOD);
            String digestMethodAlgorithm = StringSupport.trimOrNull(AttributeSupport.getAttributeValue(digestMethod, null, ATTR_NAME_ALGORTHM));
            if (digestMethodAlgorithm != null) {
                digestMethodAlgorithms.add(digestMethodAlgorithm);
                continue;
            }
            throw new SignatureException("Saw null DigestMethod Algorithm");
        }
        return digestMethodAlgorithms;
    }

    protected void validateAlgorithmURI(@Nonnull String algorithmURI) throws SignatureException {
        this.log.debug("Validating algorithm URI against whitelist and blacklist: algorithm: {}, whitelist: {}, blacklist: {}", algorithmURI, this.whitelistedAlgorithmURIs, this.blacklistedAlgorithmURIs);
        if (!AlgorithmSupport.validateAlgorithmURI(algorithmURI, this.whitelistedAlgorithmURIs, this.blacklistedAlgorithmURIs)) {
            throw new SignatureException("Algorithm failed whitelist/blacklist validation: " + algorithmURI);
        }
    }
}

