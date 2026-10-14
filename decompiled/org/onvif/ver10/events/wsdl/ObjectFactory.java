/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.events.wsdl.Capabilities;
import org.onvif.ver10.events.wsdl.CreatePullPointSubscription;
import org.onvif.ver10.events.wsdl.CreatePullPointSubscriptionResponse;
import org.onvif.ver10.events.wsdl.GetEventProperties;
import org.onvif.ver10.events.wsdl.GetEventPropertiesResponse;
import org.onvif.ver10.events.wsdl.GetServiceCapabilities;
import org.onvif.ver10.events.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.events.wsdl.PullMessages;
import org.onvif.ver10.events.wsdl.PullMessagesFaultResponse;
import org.onvif.ver10.events.wsdl.PullMessagesResponse;
import org.onvif.ver10.events.wsdl.Seek;
import org.onvif.ver10.events.wsdl.SeekResponse;
import org.onvif.ver10.events.wsdl.SetSynchronizationPoint;
import org.onvif.ver10.events.wsdl.SetSynchronizationPointResponse;
import org.onvif.ver10.events.wsdl.SubscriptionPolicy;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/events/wsdl", "Capabilities");
    private static final QName _CreatePullPointSubscriptionInitialTerminationTime_QNAME = new QName("http://www.onvif.org/ver10/events/wsdl", "InitialTerminationTime");

    public CreatePullPointSubscription createCreatePullPointSubscription() {
        return new CreatePullPointSubscription();
    }

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public CreatePullPointSubscription.SubscriptionPolicy createCreatePullPointSubscriptionSubscriptionPolicy() {
        return new CreatePullPointSubscription.SubscriptionPolicy();
    }

    public CreatePullPointSubscriptionResponse createCreatePullPointSubscriptionResponse() {
        return new CreatePullPointSubscriptionResponse();
    }

    public PullMessages createPullMessages() {
        return new PullMessages();
    }

    public PullMessagesResponse createPullMessagesResponse() {
        return new PullMessagesResponse();
    }

    public PullMessagesFaultResponse createPullMessagesFaultResponse() {
        return new PullMessagesFaultResponse();
    }

    public Seek createSeek() {
        return new Seek();
    }

    public SeekResponse createSeekResponse() {
        return new SeekResponse();
    }

    public SetSynchronizationPoint createSetSynchronizationPoint() {
        return new SetSynchronizationPoint();
    }

    public SetSynchronizationPointResponse createSetSynchronizationPointResponse() {
        return new SetSynchronizationPointResponse();
    }

    public GetEventProperties createGetEventProperties() {
        return new GetEventProperties();
    }

    public GetEventPropertiesResponse createGetEventPropertiesResponse() {
        return new GetEventPropertiesResponse();
    }

    public SubscriptionPolicy createSubscriptionPolicy() {
        return new SubscriptionPolicy();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/events/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/events/wsdl", name="InitialTerminationTime", scope=CreatePullPointSubscription.class)
    public JAXBElement<String> createCreatePullPointSubscriptionInitialTerminationTime(String value) {
        return new JAXBElement<String>(_CreatePullPointSubscriptionInitialTerminationTime_QNAME, String.class, CreatePullPointSubscription.class, value);
    }
}

