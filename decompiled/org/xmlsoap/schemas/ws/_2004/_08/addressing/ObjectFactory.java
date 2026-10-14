/*
 * Decompiled with CFR 0.152.
 */
package org.xmlsoap.schemas.ws._2004._08.addressing;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedQName;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedURI;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferenceParametersType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferencePropertiesType;
import org.xmlsoap.schemas.ws._2004._08.addressing.Relationship;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReplyAfterType;
import org.xmlsoap.schemas.ws._2004._08.addressing.RetryAfterType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ServiceNameType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _EndpointReference_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "EndpointReference");
    private static final QName _MessageID_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "MessageID");
    private static final QName _RelatesTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "RelatesTo");
    private static final QName _To_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "To");
    private static final QName _Action_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "Action");
    private static final QName _From_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "From");
    private static final QName _ReplyTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "ReplyTo");
    private static final QName _FaultTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "FaultTo");
    private static final QName _ReplyAfter_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "ReplyAfter");
    private static final QName _RetryAfter_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "RetryAfter");

    public EndpointReferenceType createEndpointReferenceType() {
        return new EndpointReferenceType();
    }

    public AttributedURI createAttributedURI() {
        return new AttributedURI();
    }

    public Relationship createRelationship() {
        return new Relationship();
    }

    public ReplyAfterType createReplyAfterType() {
        return new ReplyAfterType();
    }

    public RetryAfterType createRetryAfterType() {
        return new RetryAfterType();
    }

    public ReferencePropertiesType createReferencePropertiesType() {
        return new ReferencePropertiesType();
    }

    public ReferenceParametersType createReferenceParametersType() {
        return new ReferenceParametersType();
    }

    public ServiceNameType createServiceNameType() {
        return new ServiceNameType();
    }

    public AttributedQName createAttributedQName() {
        return new AttributedQName();
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="EndpointReference")
    public JAXBElement<EndpointReferenceType> createEndpointReference(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_EndpointReference_QNAME, EndpointReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="MessageID")
    public JAXBElement<AttributedURI> createMessageID(AttributedURI value) {
        return new JAXBElement<AttributedURI>(_MessageID_QNAME, AttributedURI.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="RelatesTo")
    public JAXBElement<Relationship> createRelatesTo(Relationship value) {
        return new JAXBElement<Relationship>(_RelatesTo_QNAME, Relationship.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="To")
    public JAXBElement<AttributedURI> createTo(AttributedURI value) {
        return new JAXBElement<AttributedURI>(_To_QNAME, AttributedURI.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="Action")
    public JAXBElement<AttributedURI> createAction(AttributedURI value) {
        return new JAXBElement<AttributedURI>(_Action_QNAME, AttributedURI.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="From")
    public JAXBElement<EndpointReferenceType> createFrom(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_From_QNAME, EndpointReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="ReplyTo")
    public JAXBElement<EndpointReferenceType> createReplyTo(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_ReplyTo_QNAME, EndpointReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="FaultTo")
    public JAXBElement<EndpointReferenceType> createFaultTo(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_FaultTo_QNAME, EndpointReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="ReplyAfter")
    public JAXBElement<ReplyAfterType> createReplyAfter(ReplyAfterType value) {
        return new JAXBElement<ReplyAfterType>(_ReplyAfter_QNAME, ReplyAfterType.class, null, value);
    }

    @XmlElementDecl(namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", name="RetryAfter")
    public JAXBElement<RetryAfterType> createRetryAfter(RetryAfterType value) {
        return new JAXBElement<RetryAfterType>(_RetryAfter_QNAME, RetryAfterType.class, null, value);
    }
}

