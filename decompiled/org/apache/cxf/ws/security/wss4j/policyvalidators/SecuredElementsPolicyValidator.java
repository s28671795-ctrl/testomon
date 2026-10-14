/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.wss4j.CryptoCoverageUtil;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.RequiredElements;
import org.apache.wss4j.policy.model.XPath;
import org.w3c.dom.Element;

public class SecuredElementsPolicyValidator
implements SecurityPolicyValidator {
    private CryptoCoverageUtil.CoverageType coverageType = CryptoCoverageUtil.CoverageType.ENCRYPTED;
    private CryptoCoverageUtil.CoverageScope coverageScope = CryptoCoverageUtil.CoverageScope.ELEMENT;

    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        if (this.coverageType == CryptoCoverageUtil.CoverageType.SIGNED) {
            return assertionInfo.getAssertion() != null && (SP12Constants.SIGNED_ELEMENTS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.SIGNED_ELEMENTS.equals(assertionInfo.getAssertion().getName()));
        }
        if (this.coverageScope == CryptoCoverageUtil.CoverageScope.CONTENT) {
            return assertionInfo.getAssertion() != null && (SP12Constants.CONTENT_ENCRYPTED_ELEMENTS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.CONTENT_ENCRYPTED_ELEMENTS.equals(assertionInfo.getAssertion().getName()));
        }
        return assertionInfo.getAssertion() != null && (SP12Constants.ENCRYPTED_ELEMENTS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.ENCRYPTED_ELEMENTS.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        XPathFactory factory2 = XPathFactory.newInstance();
        try {
            factory2.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
        }
        catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
            // empty catch block
        }
        javax.xml.xpath.XPath xpath = factory2.newXPath();
        Element soapEnvelope = parameters.getSoapHeader() != null ? parameters.getSoapHeader().getOwnerDocument().getDocumentElement() : null;
        Collection<WSDataRef> dataRefs = parameters.getEncrypted();
        if (this.coverageType == CryptoCoverageUtil.CoverageType.SIGNED) {
            dataRefs = parameters.getSigned();
        }
        for (AssertionInfo ai : ais) {
            RequiredElements elements = (RequiredElements)ai.getAssertion();
            ai.setAsserted(true);
            if (elements == null || elements.getXPaths() == null || elements.getXPaths().isEmpty()) continue;
            ArrayList<String> expressions = new ArrayList<String>();
            MapNamespaceContext namespaceContext = new MapNamespaceContext();
            for (XPath xPath : elements.getXPaths()) {
                expressions.add(xPath.getXPath());
                Map<String, String> namespaceMap = xPath.getPrefixNamespaceMap();
                if (namespaceMap == null) continue;
                namespaceContext.addNamespaces(namespaceMap);
            }
            if (parameters.getSoapHeader() == null) {
                ai.setNotAsserted("No " + (Object)((Object)this.coverageType) + " element found matching one of the XPaths " + Arrays.toString(expressions.toArray()));
                continue;
            }
            xpath.setNamespaceContext(namespaceContext);
            try {
                CryptoCoverageUtil.checkCoverage(soapEnvelope, dataRefs, xpath, expressions, this.coverageType, this.coverageScope);
            }
            catch (WSSecurityException e) {
                ai.setNotAsserted("No " + (Object)((Object)this.coverageType) + " element found matching one of the XPaths " + Arrays.toString(expressions.toArray()));
            }
        }
    }

    public CryptoCoverageUtil.CoverageType getCoverageType() {
        return this.coverageType;
    }

    public void setCoverageType(CryptoCoverageUtil.CoverageType coverageType) {
        this.coverageType = coverageType;
    }

    public CryptoCoverageUtil.CoverageScope getCoverageScope() {
        return this.coverageScope;
    }

    public void setCoverageScope(CryptoCoverageUtil.CoverageScope coverageScope) {
        this.coverageScope = coverageScope;
    }
}

