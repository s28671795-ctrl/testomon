/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.neethi.Constants;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class PolicyConstants {
    public static final String WSU_NAMESPACE_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    public static final String WSU_ID_ATTR_NAME = "Id";
    public static final String POLICY_OVERRIDE = "org.apache.cxf.ws.policy.override";
    public static final String POLICY_IN_INTERCEPTOR_ID = "org.apache.cxf.ws.policy.PolicyInInterceptor";
    public static final String POLICY_OUT_INTERCEPTOR_ID = "org.apache.cxf.ws.policy.PolicyOutInterceptor";
    public static final String CLIENT_POLICY_IN_FAULT_INTERCEPTOR_ID = "org.apache.cxf.ws.policy.ClientPolicyInFaultInterceptor";
    public static final String SERVER_POLICY_OUT_FAULT_INTERCEPTOR_ID = "org.apache.cxf.ws.policy.ServerPolicyOutFaultInterceptor";
    public static final String CLIENT_OUT_ASSERTIONS = "org.apache.cxf.ws.policy.client.out.assertions";
    public static final String CLIENT_IN_ASSERTIONS = "org.apache.cxf.ws.policy.client.in.assertions";
    public static final String CLIENT_INFAULT_ASSERTIONS = "org.apache.cxf.ws.policy.client.infault.assertions";
    public static final String SERVER_IN_ASSERTIONS = "org.apache.cxf.ws.policy.server.in.assertions";
    public static final String SERVER_OUT_ASSERTIONS = "org.apache.cxf.ws.policy.server.out.assertions";
    public static final String SERVER_OUTFAULT_ASSERTIONS = "org.apache.cxf.ws.policy.server.outfault.assertions";

    private PolicyConstants() {
    }

    public static List<Element> findAllPolicyElementsOfLocalName(Document doc, String localName) {
        return PolicyConstants.findAllPolicyElementsOfLocalName(doc.getDocumentElement(), localName);
    }

    public static List<Element> findAllPolicyElementsOfLocalName(Element el, String localName) {
        LinkedList<Element> ret = new LinkedList<Element>();
        PolicyConstants.findAllPolicyElementsOfLocalName(el, localName, ret);
        return ret;
    }

    public static void findAllPolicyElementsOfLocalName(Element el, String localName, List<Element> val) {
        QName qn = DOMUtils.getElementQName(el);
        if (localName.equals(qn.getLocalPart()) && Constants.isInPolicyNS(qn)) {
            val.add(el);
        }
        el = DOMUtils.getFirstElement(el);
        while (el != null) {
            PolicyConstants.findAllPolicyElementsOfLocalName(el, localName, val);
            el = DOMUtils.getNextElement(el);
        }
    }

    public static boolean isOptional(Element e) {
        Attr at = PolicyConstants.findOptionalAttribute(e);
        if (at != null) {
            String v = at.getValue();
            return "true".equalsIgnoreCase(v) || "1".equals(v);
        }
        return false;
    }

    public static Attr findOptionalAttribute(Element e) {
        NamedNodeMap atts = e.getAttributes();
        for (int x = 0; x < atts.getLength(); ++x) {
            Attr att = (Attr)atts.item(x);
            QName qn = new QName(att.getNamespaceURI(), att.getLocalName());
            if (!Constants.isOptionalAttribute(qn)) continue;
            return att;
        }
        return null;
    }

    public static boolean isIgnorable(Element e) {
        Attr at = PolicyConstants.findIgnorableAttribute(e);
        if (at != null) {
            String v = at.getValue();
            return "true".equalsIgnoreCase(v) || "1".equals(v);
        }
        return false;
    }

    public static Attr findIgnorableAttribute(Element e) {
        NamedNodeMap atts = e.getAttributes();
        for (int x = 0; x < atts.getLength(); ++x) {
            Attr att = (Attr)atts.item(x);
            QName qn = new QName(att.getNamespaceURI(), att.getLocalName());
            if (!Constants.isIgnorableAttribute(qn)) continue;
            return att;
        }
        return null;
    }

    public static Element findPolicyElement(Element parent) {
        for (Node nd = parent.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
            QName qn;
            if (!(nd instanceof Element) || !Constants.isPolicyElement(qn = DOMUtils.getElementQName((Element)nd))) continue;
            return (Element)nd;
        }
        return null;
    }
}

