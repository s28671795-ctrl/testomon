/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.cert.Certificate;
import java.util.List;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.saml.DOMSAMLUtil;

public abstract class AbstractSamlPolicyValidator
extends AbstractSecurityPolicyValidator {
    public boolean checkHolderOfKey(SamlAssertionWrapper assertionWrapper, List<WSSecurityEngineResult> signedResults, Certificate[] tlsCerts) {
        return DOMSAMLUtil.checkHolderOfKey(assertionWrapper, signedResults, tlsCerts);
    }

    protected boolean compareCredentials(SAMLKeyInfo subjectKeyInfo, List<WSSecurityEngineResult> signedResults, Certificate[] tlsCerts) {
        return DOMSAMLUtil.compareCredentials(subjectKeyInfo, signedResults, tlsCerts);
    }
}

