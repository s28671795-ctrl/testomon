/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public final class Constants {
    public static final String ATTR_NAME = "Name";
    public static final String ATTR_ID = "Id";
    public static final String ATTR_WSP = "wsp";
    public static final String ATTR_WSU = "wsu";
    public static final String ATTR_URI = "URI";
    public static final String URI_POLICY_13_NS = "http://schemas.xmlsoap.org/ws/2004/09/policy";
    public static final String URI_POLICY_15_DEPRECATED_NS = "http://www.w3.org/2006/07/ws-policy";
    public static final String URI_POLICY_15_NS = "http://www.w3.org/ns/ws-policy";
    public static final String URI_POLICY_NS = "http://www.w3.org/ns/ws-policy";
    public static final String URI_WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    public static final String ELEM_POLICY = "Policy";
    public static final String ELEM_EXACTLYONE = "ExactlyOne";
    public static final String ELEM_ALL = "All";
    public static final String ELEM_POLICY_REF = "PolicyReference";
    public static final String ELEM_POLICY_ATTACHMENT = "PolicyAttachment";
    public static final short TYPE_POLICY = 1;
    public static final short TYPE_EXACTLYONE = 2;
    public static final short TYPE_ALL = 3;
    public static final short TYPE_POLICY_REF = 4;
    public static final short TYPE_ASSERTION = 5;
    public static final String ATTR_OPTIONAL = "Optional";
    public static final String ATTR_IGNORABLE = "Ignorable";
    public static final String ATTR_POLICYURIS = "PolicyURIs";
    public static final String ELEM_POLICY_APPLIES_TO = "AppliesTo";
    public static final QName Q_ELEM_POLICY = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "Policy", "wsp");
    public static final QName Q_ELEM_POLICY_15 = new QName("http://www.w3.org/ns/ws-policy", "Policy", "wsp");
    public static final QName Q_ELEM_OPTIONAL_ATTR = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "Optional", "wsp");
    public static final QName Q_ELEM_OPTIONAL_15_ATTR = new QName("http://www.w3.org/ns/ws-policy", "Optional", "wsp");
    public static final QName Q_ELEM_IGNORABLE_15_ATTR = new QName("http://www.w3.org/ns/ws-policy", "Ignorable", "wsp");

    private Constants() {
    }

    public static boolean isPolicyNS(String ns) {
        return URI_POLICY_13_NS.equals(ns) || URI_POLICY_15_DEPRECATED_NS.equals(ns) || "http://www.w3.org/ns/ws-policy".equals(ns);
    }

    public static boolean isInPolicyNS(QName q) {
        return Constants.isPolicyNS(q.getNamespaceURI());
    }

    public static boolean isPolicyElement(String ns, String local) {
        return Constants.isPolicyNS(ns) && ELEM_POLICY.equals(local);
    }

    public static boolean isPolicyElement(QName q) {
        return Constants.isInPolicyNS(q) && ELEM_POLICY.equals(q.getLocalPart());
    }

    public static boolean isPolicyRef(String ns, String local) {
        return Constants.isPolicyNS(ns) && ELEM_POLICY_REF.equals(local);
    }

    public static boolean isPolicyRef(QName q) {
        return Constants.isInPolicyNS(q) && ELEM_POLICY_REF.equals(q.getLocalPart());
    }

    public static boolean isOptionalAttribute(QName qn) {
        return ATTR_OPTIONAL.equals(qn.getLocalPart()) && Constants.isInPolicyNS(qn);
    }

    public static boolean isIgnorableAttribute(QName qn) {
        return ATTR_IGNORABLE.equals(qn.getLocalPart()) && Constants.isInPolicyNS(qn);
    }

    public static boolean isAppliesToElem(QName qn) {
        return ELEM_POLICY_APPLIES_TO.equals(qn.getLocalPart()) && Constants.isInPolicyNS(qn);
    }

    public static boolean isPolicyURIsAttr(QName qn) {
        return ATTR_POLICYURIS.equals(qn.getLocalPart()) && Constants.isInPolicyNS(qn);
    }

    public static String findPolicyNamespace(XMLStreamWriter writer) throws XMLStreamException {
        String prefix = writer.getPrefix("http://www.w3.org/ns/ws-policy");
        if (prefix == null || "".equals(prefix)) {
            prefix = writer.getPrefix(URI_POLICY_15_DEPRECATED_NS);
        }
        if (prefix == null || "".equals(prefix)) {
            return URI_POLICY_13_NS;
        }
        return "http://www.w3.org/ns/ws-policy";
    }
}

