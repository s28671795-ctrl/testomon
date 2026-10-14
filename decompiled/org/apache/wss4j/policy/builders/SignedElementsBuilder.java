/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.XPath;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class SignedElementsBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        String xPathVersion = this.getXPathVersion(element);
        List<XPath> xPaths = this.getXPathExpressions(element, spVersion);
        List<XPath> xPaths2 = this.getXPath2Expressions(element, spVersion);
        xPaths.addAll(xPaths2);
        SignedElements signedElements = new SignedElements(spVersion, xPathVersion, xPaths);
        signedElements.setOptional(SPUtils.isOptional(element));
        signedElements.setIgnorable(SPUtils.isIgnorable(element));
        return signedElements;
    }

    protected List<XPath> getXPathExpressions(Element element, SPConstants.SPVersion spVersion) {
        ArrayList<XPath> xPaths = new ArrayList<XPath>();
        Element child = SPUtils.getFirstChildElement(element);
        while (child != null) {
            QName xpathExpression = spVersion.getSPConstants().getXPathExpression();
            if ("XPath".equals(child.getLocalName()) && xpathExpression.getNamespaceURI().equals(child.getNamespaceURI())) {
                HashMap<String, String> declaredNamespaces = new HashMap<String, String>();
                this.addDeclaredNamespaces(child, declaredNamespaces);
                xPaths.add(new XPath(child.getTextContent().trim(), XPath.Version.V1, null, declaredNamespaces));
            }
            child = SPUtils.getNextSiblingElement(child);
        }
        return xPaths;
    }

    protected List<XPath> getXPath2Expressions(Element element, SPConstants.SPVersion spVersion) {
        ArrayList<XPath> xPaths = new ArrayList<XPath>();
        Element child = SPUtils.getFirstChildElement(element);
        while (child != null) {
            QName xpathExpression = spVersion.getSPConstants().getXPath2Expression();
            if ("Xpath2".equals(child.getLocalName()) && xpathExpression.getNamespaceURI().equals(child.getNamespaceURI())) {
                HashMap<String, String> declaredNamespaces = new HashMap<String, String>();
                this.addDeclaredNamespaces(child, declaredNamespaces);
                String filter2 = child.getAttributeNS(null, "Filter");
                if (filter2 == null || "".equals(filter2)) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                xPaths.add(new XPath(child.getTextContent().trim(), XPath.Version.V2, filter2, declaredNamespaces));
            }
            child = SPUtils.getNextSiblingElement(child);
        }
        return xPaths;
    }

    protected String getXPathVersion(Element element) {
        String xPathVersion = element.getAttributeNS(null, "XPathVersion");
        if (xPathVersion == null || "".equals(xPathVersion)) {
            xPathVersion = "1.0";
        }
        return xPathVersion;
    }

    protected void addDeclaredNamespaces(Element element, Map<String, String> declaredNamespaces) {
        if (element.getParentNode() != null && element.getParentNode() instanceof Element) {
            this.addDeclaredNamespaces((Element)element.getParentNode(), declaredNamespaces);
        }
        NamedNodeMap map = element.getAttributes();
        for (int x = 0; x < map.getLength(); ++x) {
            Attr attr = (Attr)map.item(x);
            if (!"xmlns".equals(attr.getPrefix())) continue;
            declaredNamespaces.put(attr.getLocalName(), attr.getValue());
        }
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.SIGNED_ELEMENTS, SP11Constants.SIGNED_ELEMENTS};
    }
}

