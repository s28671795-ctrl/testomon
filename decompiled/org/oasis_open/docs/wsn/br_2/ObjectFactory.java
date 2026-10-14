/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.br_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.br_2.DestroyRegistration;
import org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse;
import org.oasis_open.docs.wsn.br_2.NotificationBrokerRP;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationRP;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType;
import org.oasis_open.docs.wsn.br_2.RegisterPublisher;
import org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse;
import org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFaultType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _RequiresRegistration_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "RequiresRegistration");
    private static final QName _PublisherReference_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "PublisherReference");
    private static final QName _ConsumerReference_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "ConsumerReference");
    private static final QName _Topic_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "Topic");
    private static final QName _Demand_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "Demand");
    private static final QName _CreationTime_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "CreationTime");
    private static final QName _PublisherRegistrationRejectedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "PublisherRegistrationRejectedFault");
    private static final QName _PublisherRegistrationFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "PublisherRegistrationFailedFault");
    private static final QName _ResourceNotDestroyedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/br-2", "ResourceNotDestroyedFault");

    public NotificationBrokerRP createNotificationBrokerRP() {
        return new NotificationBrokerRP();
    }

    public PublisherRegistrationRP createPublisherRegistrationRP() {
        return new PublisherRegistrationRP();
    }

    public RegisterPublisher createRegisterPublisher() {
        return new RegisterPublisher();
    }

    public RegisterPublisherResponse createRegisterPublisherResponse() {
        return new RegisterPublisherResponse();
    }

    public PublisherRegistrationRejectedFaultType createPublisherRegistrationRejectedFaultType() {
        return new PublisherRegistrationRejectedFaultType();
    }

    public PublisherRegistrationFailedFaultType createPublisherRegistrationFailedFaultType() {
        return new PublisherRegistrationFailedFaultType();
    }

    public DestroyRegistration createDestroyRegistration() {
        return new DestroyRegistration();
    }

    public DestroyRegistrationResponse createDestroyRegistrationResponse() {
        return new DestroyRegistrationResponse();
    }

    public ResourceNotDestroyedFaultType createResourceNotDestroyedFaultType() {
        return new ResourceNotDestroyedFaultType();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="RequiresRegistration")
    public JAXBElement<Boolean> createRequiresRegistration(Boolean value) {
        return new JAXBElement<Boolean>(_RequiresRegistration_QNAME, Boolean.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="PublisherReference")
    public JAXBElement<W3CEndpointReference> createPublisherReference(W3CEndpointReference value) {
        return new JAXBElement<W3CEndpointReference>(_PublisherReference_QNAME, W3CEndpointReference.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="ConsumerReference")
    public JAXBElement<W3CEndpointReference> createConsumerReference(W3CEndpointReference value) {
        return new JAXBElement<W3CEndpointReference>(_ConsumerReference_QNAME, W3CEndpointReference.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="Topic")
    public JAXBElement<TopicExpressionType> createTopic(TopicExpressionType value) {
        return new JAXBElement<TopicExpressionType>(_Topic_QNAME, TopicExpressionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="Demand")
    public JAXBElement<Boolean> createDemand(Boolean value) {
        return new JAXBElement<Boolean>(_Demand_QNAME, Boolean.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="CreationTime")
    public JAXBElement<XMLGregorianCalendar> createCreationTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CreationTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="PublisherRegistrationRejectedFault")
    public JAXBElement<PublisherRegistrationRejectedFaultType> createPublisherRegistrationRejectedFault(PublisherRegistrationRejectedFaultType value) {
        return new JAXBElement<PublisherRegistrationRejectedFaultType>(_PublisherRegistrationRejectedFault_QNAME, PublisherRegistrationRejectedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="PublisherRegistrationFailedFault")
    public JAXBElement<PublisherRegistrationFailedFaultType> createPublisherRegistrationFailedFault(PublisherRegistrationFailedFaultType value) {
        return new JAXBElement<PublisherRegistrationFailedFaultType>(_PublisherRegistrationFailedFault_QNAME, PublisherRegistrationFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/br-2", name="ResourceNotDestroyedFault")
    public JAXBElement<ResourceNotDestroyedFaultType> createResourceNotDestroyedFault(ResourceNotDestroyedFaultType value) {
        return new JAXBElement<ResourceNotDestroyedFaultType>(_ResourceNotDestroyedFault_QNAME, ResourceNotDestroyedFaultType.class, null, value);
    }
}

