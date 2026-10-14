/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.RequiredElements;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RequiredElementsPolicyValidator
implements SecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.REQUIRED_ELEMENTS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.REQUIRED_ELEMENTS.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        for (AssertionInfo ai : ais) {
            RequiredElements rp = (RequiredElements)ai.getAssertion();
            ai.setAsserted(true);
            if (rp == null || rp.getXPaths() == null || rp.getXPaths().isEmpty()) continue;
            XPathFactory factory2 = XPathFactory.newInstance();
            try {
                factory2.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            }
            catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
                // empty catch block
            }
            for (org.apache.wss4j.policy.model.XPath xPath : rp.getXPaths()) {
                Map<String, String> namespaces = xPath.getPrefixNamespaceMap();
                String expression = xPath.getXPath();
                XPath xpath = factory2.newXPath();
                if (namespaces != null) {
                    xpath.setNamespaceContext(new MapNamespaceContext(namespaces));
                }
                Element header = parameters.getSoapHeader();
                if ((header = (Element)DOMUtils.getDomElement(header)) == null) {
                    ai.setNotAsserted("No header element matching XPath " + expression + " found.");
                    continue;
                }
                try {
                    NodeList list = (NodeList)xpath.evaluate(expression, header, XPathConstants.NODESET);
                    if (list.getLength() != 0) continue;
                    ai.setNotAsserted("No header element matching XPath " + expression + " found.");
                }
                catch (XPathExpressionException e) {
                    ai.setNotAsserted("Invalid XPath expression " + expression + " " + e.getMessage());
                }
            }
        }
    }
}

