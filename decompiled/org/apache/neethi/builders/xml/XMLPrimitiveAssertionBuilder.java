/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.xml;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Constants;
import org.apache.neethi.Policy;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.neethi.builders.PolicyContainingPrimitiveAssertion;
import org.apache.neethi.builders.PrimitiveAssertion;
import org.apache.neethi.builders.xml.XmlPrimitiveAssertion;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XMLPrimitiveAssertionBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        int count = 0;
        int policyCount = 0;
        Element policyEl = null;
        for (Node nd = element.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
            if (!(nd instanceof Element)) continue;
            ++count;
            Element el = (Element)nd;
            if (!Constants.isPolicyElement(el.getNamespaceURI(), el.getLocalName())) continue;
            policyEl = el;
            ++policyCount;
        }
        HashMap<QName, String> atts = new HashMap<QName, String>();
        NamedNodeMap attrs = element.getAttributes();
        if (attrs != null) {
            for (int x = 0; x < attrs.getLength(); ++x) {
                Attr attr = (Attr)attrs.item(x);
                atts.put(new QName(attr.getNamespaceURI(), attr.getLocalName()), attr.getValue());
            }
        }
        if (count == 0) {
            return this.newPrimitiveAssertion(element, atts.isEmpty() ? null : atts);
        }
        if (policyCount == 1 && count == 1) {
            Policy policy = factory2.getPolicyEngine().getPolicy(policyEl);
            return this.newPolicyContainingAssertion(element, atts.isEmpty() ? null : atts, policy);
        }
        return new XmlPrimitiveAssertion(element);
    }

    protected QName getQName(Element element) {
        if (element.getPrefix() == null) {
            return new QName(element.getNamespaceURI(), element.getLocalName());
        }
        return new QName(element.getNamespaceURI(), element.getLocalName(), element.getPrefix());
    }

    public Assertion newPrimitiveAssertion(Element element, Map<QName, String> atts) {
        return new PrimitiveAssertion(this.getQName(element), XMLPrimitiveAssertionBuilder.isOptional(element), XMLPrimitiveAssertionBuilder.isIgnorable(element), atts, element.getTextContent());
    }

    public Assertion newPolicyContainingAssertion(Element element, Map<QName, String> atts, Policy policy) {
        return new PolicyContainingPrimitiveAssertion(this.getQName(element), XMLPrimitiveAssertionBuilder.isOptional(element), XMLPrimitiveAssertionBuilder.isIgnorable(element), policy);
    }

    public static boolean isOptional(Element el) {
        Attr optional = el.getAttributeNodeNS("http://schemas.xmlsoap.org/ws/2004/09/policy", "Optional");
        if (optional == null) {
            optional = el.getAttributeNodeNS("http://www.w3.org/ns/ws-policy", "Optional");
        }
        if (optional == null) {
            optional = el.getAttributeNodeNS("http://www.w3.org/2006/07/ws-policy", "Optional");
        }
        return optional == null ? false : Boolean.parseBoolean(optional.getValue());
    }

    public static boolean isIgnorable(Element el) {
        Attr ignorable = el.getAttributeNodeNS("http://www.w3.org/ns/ws-policy", "Ignorable");
        if (ignorable == null) {
            ignorable = el.getAttributeNodeNS("http://www.w3.org/2006/07/ws-policy", "Ignorable");
        }
        return ignorable == null ? false : Boolean.parseBoolean(ignorable.getValue());
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{new QName("UnknownElement")};
    }
}

