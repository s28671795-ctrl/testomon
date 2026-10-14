/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.saml;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.common.saml.OpenSAMLUtil;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public final class DOMSAMLUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DOMSAMLUtil.class);

    private DOMSAMLUtil() {
    }

    public static void validateSAMLResults(WSHandlerResult handlerResults, Certificate[] tlsCerts, Element body2) throws WSSecurityException {
        ArrayList samlResults = new ArrayList();
        if (handlerResults.getActionResults().containsKey(16)) {
            samlResults.addAll(handlerResults.getActionResults().get(16));
        }
        if (handlerResults.getActionResults().containsKey(8)) {
            samlResults.addAll(handlerResults.getActionResults().get(8));
        }
        if (samlResults.isEmpty()) {
            return;
        }
        ArrayList<WSSecurityEngineResult> signedResults = new ArrayList<WSSecurityEngineResult>();
        if (handlerResults.getActionResults().containsKey(2)) {
            signedResults.addAll((Collection)handlerResults.getActionResults().get(2));
        }
        if (handlerResults.getActionResults().containsKey(64)) {
            signedResults.addAll((Collection)handlerResults.getActionResults().get(64));
        }
        for (WSSecurityEngineResult samlResult : samlResults) {
            SamlAssertionWrapper assertionWrapper = (SamlAssertionWrapper)samlResult.get("saml-assertion");
            if (!DOMSAMLUtil.checkHolderOfKey(assertionWrapper, signedResults, tlsCerts)) {
                LOG.warn("Assertion fails holder-of-key requirements");
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
            if (DOMSAMLUtil.checkSenderVouches(assertionWrapper, tlsCerts, body2, signedResults)) continue;
            LOG.warn("Assertion fails sender-vouches requirements");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
    }

    public static boolean checkHolderOfKey(SamlAssertionWrapper assertionWrapper, List<WSSecurityEngineResult> signedResults, Certificate[] tlsCerts) {
        List<String> confirmationMethods = assertionWrapper.getConfirmationMethods();
        boolean isHolderOfKey = false;
        for (String confirmationMethod : confirmationMethods) {
            if (!OpenSAMLUtil.isMethodHolderOfKey(confirmationMethod)) continue;
            isHolderOfKey = true;
            break;
        }
        if (isHolderOfKey) {
            if (tlsCerts == null && (signedResults == null || signedResults.isEmpty())) {
                return false;
            }
            SAMLKeyInfo subjectKeyInfo = assertionWrapper.getSubjectKeyInfo();
            if (!DOMSAMLUtil.compareCredentials(subjectKeyInfo, signedResults, tlsCerts)) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareCredentials(SAMLKeyInfo subjectKeyInfo, List<WSSecurityEngineResult> signedResults, Certificate[] tlsCerts) {
        X509Certificate[] subjectCerts = subjectKeyInfo.getCerts();
        PublicKey subjectPublicKey = subjectKeyInfo.getPublicKey();
        byte[] subjectSecretKey = subjectKeyInfo.getSecret();
        if (tlsCerts != null && tlsCerts.length > 0 && subjectCerts != null && subjectCerts.length > 0 && tlsCerts[0].equals(subjectCerts[0])) {
            return true;
        }
        if (tlsCerts != null && tlsCerts.length > 0 && subjectPublicKey != null && tlsCerts[0].getPublicKey().equals(subjectPublicKey)) {
            return true;
        }
        if (subjectPublicKey == null && subjectCerts != null && subjectCerts.length > 0) {
            subjectPublicKey = subjectCerts[0].getPublicKey();
        }
        for (WSSecurityEngineResult signedResult : signedResults) {
            X509Certificate[] certs = (X509Certificate[])signedResult.get("x509-certificates");
            PublicKey publicKey = (PublicKey)signedResult.get("public-key");
            byte[] secretKey = (byte[])signedResult.get("secret");
            if (certs != null && certs.length > 0 && subjectCerts != null && subjectCerts.length > 0 && certs[0].equals(subjectCerts[0])) {
                return true;
            }
            if (publicKey != null && publicKey.equals(subjectPublicKey)) {
                return true;
            }
            if (!DOMSAMLUtil.checkSecretKey(secretKey, subjectSecretKey, signedResult)) continue;
            return true;
        }
        return false;
    }

    private static boolean checkSecretKey(byte[] secretKey, byte[] subjectSecretKey, WSSecurityEngineResult signedResult) {
        if (secretKey != null && subjectSecretKey != null) {
            if (Arrays.equals(secretKey, subjectSecretKey)) {
                return true;
            }
            Principal principal = (Principal)signedResult.get("principal");
            if (principal instanceof WSDerivedKeyTokenPrincipal && Arrays.equals(secretKey = ((WSDerivedKeyTokenPrincipal)principal).getSecret(), subjectSecretKey)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkSenderVouches(SamlAssertionWrapper assertionWrapper, Certificate[] tlsCerts, Element body2, List<WSSecurityEngineResult> signed) {
        if (tlsCerts != null && tlsCerts.length > 0) {
            return true;
        }
        List<String> confirmationMethods = assertionWrapper.getConfirmationMethods();
        boolean isSenderVouches = false;
        for (String confirmationMethod : confirmationMethods) {
            if (!OpenSAMLUtil.isMethodSenderVouches(confirmationMethod)) continue;
            isSenderVouches = true;
            break;
        }
        if (isSenderVouches) {
            if (signed == null || signed.isEmpty()) {
                return false;
            }
            if (!DOMSAMLUtil.checkAssertionAndBodyAreSigned(assertionWrapper, body2, signed)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkAssertionAndBodyAreSigned(SamlAssertionWrapper assertionWrapper, Element body2, List<WSSecurityEngineResult> signed) {
        for (WSSecurityEngineResult signedResult : signed) {
            List sl = (List)signedResult.get("data-ref-uris");
            boolean assertionIsSigned = false;
            boolean bodyIsSigned = false;
            if (sl == null) continue;
            for (WSDataRef dataRef : sl) {
                Element se = dataRef.getProtectedElement();
                if (se == assertionWrapper.getElement()) {
                    assertionIsSigned = true;
                }
                if (se == body2) {
                    bodyIsSigned = true;
                }
                if (!assertionIsSigned || !bodyIsSigned) continue;
                return true;
            }
        }
        return false;
    }
}

