/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.util.XMLUtils;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.w3c.dom.Element;

public final class SAMLUtil {
    private static final String SIG_NS = "http://www.w3.org/2000/09/xmldsig#";

    private SAMLUtil() {
    }

    public static SAMLKeyInfo getCredentialFromSubject(SamlAssertionWrapper samlAssertion, SAMLKeyInfoProcessor keyInfoProcessor, Crypto sigCrypto, CallbackHandler callbackHandler) throws WSSecurityException {
        if (samlAssertion.getSaml1() != null) {
            return SAMLUtil.getCredentialFromSubject(samlAssertion.getSaml1(), keyInfoProcessor, sigCrypto, callbackHandler);
        }
        if (samlAssertion.getSaml2() != null) {
            return SAMLUtil.getCredentialFromSubject(samlAssertion.getSaml2(), keyInfoProcessor, sigCrypto, callbackHandler);
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Cannot get credentials from an unknown SAML Assertion"});
    }

    public static SAMLKeyInfo getCredentialFromSubject(Assertion assertion, SAMLKeyInfoProcessor keyInfoProcessor, Crypto sigCrypto, CallbackHandler callbackHandler) throws WSSecurityException {
        for (Statement stmt : assertion.getStatements()) {
            Element sub;
            Element keyInfoElement;
            Subject samlSubject = null;
            if (stmt instanceof AttributeStatement) {
                AttributeStatement attrStmt = (AttributeStatement)stmt;
                samlSubject = attrStmt.getSubject();
            } else if (stmt instanceof AuthenticationStatement) {
                AuthenticationStatement authStmt = (AuthenticationStatement)stmt;
                samlSubject = authStmt.getSubject();
            } else {
                AuthorizationDecisionStatement authzStmt = (AuthorizationDecisionStatement)stmt;
                samlSubject = authzStmt.getSubject();
            }
            if (samlSubject == null || samlSubject.getSubjectConfirmation() == null || (keyInfoElement = XMLUtils.getDirectChildElement(sub = samlSubject.getSubjectConfirmation().getDOM(), "KeyInfo", SIG_NS)) == null) continue;
            return SAMLUtil.getCredentialFromKeyInfo(keyInfoElement, keyInfoProcessor, sigCrypto);
        }
        return null;
    }

    public static SAMLKeyInfo getCredentialFromSubject(org.opensaml.saml.saml2.core.Assertion assertion, SAMLKeyInfoProcessor keyInfoProcessor, Crypto sigCrypto, CallbackHandler callbackHandler) throws WSSecurityException {
        org.opensaml.saml.saml2.core.Subject samlSubject = assertion.getSubject();
        if (samlSubject != null) {
            List<SubjectConfirmation> subjectConfList = samlSubject.getSubjectConfirmations();
            for (SubjectConfirmation subjectConfirmation : subjectConfList) {
                Element sub;
                Element keyInfoElement;
                SubjectConfirmationData subjConfData = subjectConfirmation.getSubjectConfirmationData();
                if (subjConfData == null || (keyInfoElement = XMLUtils.getDirectChildElement(sub = subjConfData.getDOM(), "KeyInfo", SIG_NS)) == null) continue;
                return SAMLUtil.getCredentialFromKeyInfo(keyInfoElement, keyInfoProcessor, sigCrypto);
            }
        }
        return null;
    }

    public static SAMLKeyInfo getCredentialFromKeyInfo(Element keyInfoElement, SAMLKeyInfoProcessor keyInfoProcessor, Crypto sigCrypto) throws WSSecurityException {
        SAMLKeyInfo samlKeyInfo;
        if (keyInfoProcessor != null && (samlKeyInfo = keyInfoProcessor.processSAMLKeyInfo(keyInfoElement)) != null) {
            return samlKeyInfo;
        }
        X509Certificate[] certs = null;
        KeyInfoFactory keyInfoFactory = null;
        try {
            keyInfoFactory = KeyInfoFactory.getInstance("DOM", "ApacheXMLDSig");
        }
        catch (NoSuchProviderException ex) {
            keyInfoFactory = KeyInfoFactory.getInstance("DOM");
        }
        DOMStructure keyInfoStructure = new DOMStructure(keyInfoElement);
        try {
            KeyInfo keyInfo = keyInfoFactory.unmarshalKeyInfo(keyInfoStructure);
            List<XMLStructure> list = keyInfo.getContent();
            for (int i = 0; i < list.size(); ++i) {
                XMLStructure xmlStructure = list.get(i);
                if (xmlStructure instanceof KeyValue) {
                    PublicKey publicKey = ((KeyValue)xmlStructure).getPublicKey();
                    return new SAMLKeyInfo(publicKey);
                }
                if (!(xmlStructure instanceof X509Data)) continue;
                List<?> x509Data = ((X509Data)xmlStructure).getContent();
                for (int j = 0; j < x509Data.size(); ++j) {
                    Object x509obj = x509Data.get(j);
                    if (x509obj instanceof X509Certificate) {
                        certs = new X509Certificate[]{(X509Certificate)x509obj};
                        return new SAMLKeyInfo(certs);
                    }
                    if (!(x509obj instanceof X509IssuerSerial)) continue;
                    if (sigCrypto == null) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSigCryptoFile");
                    }
                    CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ISSUER_SERIAL);
                    cryptoType.setIssuerSerial(((X509IssuerSerial)x509obj).getIssuerName(), ((X509IssuerSerial)x509obj).getSerialNumber());
                    certs = sigCrypto.getX509Certificates(cryptoType);
                    if (certs == null || certs.length < 1) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
                    }
                    return new SAMLKeyInfo(certs);
                }
            }
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
        }
        return null;
    }

    public static void doSAMLCallback(CallbackHandler callbackHandler, SAMLCallback callback) {
        try {
            callbackHandler.handle(new SAMLCallback[]{callback});
        }
        catch (IOException | UnsupportedCallbackException e) {
            throw new IllegalStateException("Error while creating SAML assertion wrapper", e);
        }
    }
}

