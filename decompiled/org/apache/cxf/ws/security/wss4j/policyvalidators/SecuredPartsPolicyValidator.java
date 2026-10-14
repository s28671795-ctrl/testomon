/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.CryptoCoverageUtil;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AbstractSecuredParts;
import org.apache.wss4j.policy.model.Attachments;
import org.apache.wss4j.policy.model.Header;
import org.w3c.dom.Element;

public class SecuredPartsPolicyValidator
implements SecurityPolicyValidator {
    private CryptoCoverageUtil.CoverageType coverageType = CryptoCoverageUtil.CoverageType.ENCRYPTED;

    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        if (this.coverageType == CryptoCoverageUtil.CoverageType.SIGNED) {
            return assertionInfo.getAssertion() != null && (SP12Constants.SIGNED_PARTS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.SIGNED_PARTS.equals(assertionInfo.getAssertion().getName()));
        }
        return assertionInfo.getAssertion() != null && (SP12Constants.ENCRYPTED_PARTS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.ENCRYPTED_PARTS.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        if (this.isTransportBinding(parameters.getAssertionInfoMap(), parameters.getMessage())) {
            return;
        }
        Message msg = parameters.getMessage();
        Element soapBody = parameters.getSoapBody();
        Element header = parameters.getSoapHeader();
        soapBody = (Element)DOMUtils.getDomElement(soapBody);
        header = (Element)DOMUtils.getDomElement(header);
        Collection<WSDataRef> dataRefs = parameters.getEncrypted();
        if (this.coverageType == CryptoCoverageUtil.CoverageType.SIGNED) {
            dataRefs = parameters.getSigned();
        }
        for (AssertionInfo ai : ais) {
            if (ai.isAsserted()) continue;
            AbstractSecuredParts p = (AbstractSecuredParts)ai.getAssertion();
            ai.setAsserted(true);
            if (p.isBody()) {
                try {
                    if (this.coverageType == CryptoCoverageUtil.CoverageType.SIGNED) {
                        CryptoCoverageUtil.checkBodyCoverage(soapBody, dataRefs, CryptoCoverageUtil.CoverageType.SIGNED, CryptoCoverageUtil.CoverageScope.ELEMENT);
                    } else {
                        CryptoCoverageUtil.checkBodyCoverage(soapBody, dataRefs, CryptoCoverageUtil.CoverageType.ENCRYPTED, CryptoCoverageUtil.CoverageScope.CONTENT);
                    }
                }
                catch (WSSecurityException e) {
                    ai.setNotAsserted("Soap Body is not " + (Object)((Object)this.coverageType));
                    continue;
                }
            }
            for (Header h2 : p.getHeaders()) {
                if (header == null) {
                    ai.setNotAsserted(h2.getNamespace() + ":" + h2.getName() + " not + " + (Object)((Object)this.coverageType));
                    continue;
                }
                try {
                    CryptoCoverageUtil.checkHeaderCoverage(header, dataRefs, h2.getNamespace(), h2.getName(), this.coverageType, CryptoCoverageUtil.CoverageScope.ELEMENT);
                }
                catch (WSSecurityException e) {
                    ai.setNotAsserted(h2.getNamespace() + ":" + h2.getName() + " not + " + (Object)((Object)this.coverageType));
                }
            }
            Attachments attachments = p.getAttachments();
            if (attachments == null) continue;
            try {
                CryptoCoverageUtil.CoverageScope scope = CryptoCoverageUtil.CoverageScope.ELEMENT;
                if (attachments.isContentSignatureTransform()) {
                    scope = CryptoCoverageUtil.CoverageScope.CONTENT;
                }
                CryptoCoverageUtil.checkAttachmentsCoverage(msg.getAttachments(), dataRefs, this.coverageType, scope);
            }
            catch (WSSecurityException e) {
                ai.setNotAsserted("An attachment was not signed/encrypted");
            }
        }
    }

    private boolean isTransportBinding(AssertionInfoMap aim, Message message) {
        AssertionInfo symAis = PolicyUtils.getFirstAssertionByLocalname(aim, "SymmetricBinding");
        if (symAis != null) {
            return false;
        }
        AssertionInfo asymAis = PolicyUtils.getFirstAssertionByLocalname(aim, "AsymmetricBinding");
        if (asymAis != null) {
            return false;
        }
        AssertionInfo transAis = PolicyUtils.getFirstAssertionByLocalname(aim, "TransportBinding");
        if (transAis != null) {
            return true;
        }
        TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
        if (tlsInfo != null) {
            PolicyUtils.assertPolicy(aim, SP12Constants.ENCRYPTED_PARTS);
            PolicyUtils.assertPolicy(aim, SP11Constants.ENCRYPTED_PARTS);
            PolicyUtils.assertPolicy(aim, SP12Constants.SIGNED_PARTS);
            PolicyUtils.assertPolicy(aim, SP11Constants.SIGNED_PARTS);
            return true;
        }
        return false;
    }

    public CryptoCoverageUtil.CoverageType getCoverageType() {
        return this.coverageType;
    }

    public void setCoverageType(CryptoCoverageUtil.CoverageType coverageType) {
        this.coverageType = coverageType;
    }
}

