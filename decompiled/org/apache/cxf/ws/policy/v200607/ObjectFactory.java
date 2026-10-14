/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.v200607;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.v200607.AppliesTo;
import org.apache.cxf.ws.policy.v200607.OperatorContentType;
import org.apache.cxf.ws.policy.v200607.Policy;
import org.apache.cxf.ws.policy.v200607.PolicyAttachment;
import org.apache.cxf.ws.policy.v200607.PolicyReference;
import org.apache.cxf.ws.policy.v200607.URI;

@XmlRegistry
public class ObjectFactory {
    private static final QName _All_QNAME = new QName("http://www.w3.org/2006/07/ws-policy", "All");
    private static final QName _ExactlyOne_QNAME = new QName("http://www.w3.org/2006/07/ws-policy", "ExactlyOne");

    public Policy createPolicy() {
        return new Policy();
    }

    public OperatorContentType createOperatorContentType() {
        return new OperatorContentType();
    }

    public PolicyReference createPolicyReference() {
        return new PolicyReference();
    }

    public PolicyAttachment createPolicyAttachment() {
        return new PolicyAttachment();
    }

    public AppliesTo createAppliesTo() {
        return new AppliesTo();
    }

    public URI createURI() {
        return new URI();
    }

    @XmlElementDecl(namespace="http://www.w3.org/2006/07/ws-policy", name="All")
    public JAXBElement<OperatorContentType> createAll(OperatorContentType value) {
        return new JAXBElement<OperatorContentType>(_All_QNAME, OperatorContentType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2006/07/ws-policy", name="ExactlyOne")
    public JAXBElement<OperatorContentType> createExactlyOne(OperatorContentType value) {
        return new JAXBElement<OperatorContentType>(_ExactlyOne_QNAME, OperatorContentType.class, null, value);
    }
}

