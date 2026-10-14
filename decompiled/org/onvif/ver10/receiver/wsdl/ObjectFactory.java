/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.receiver.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.receiver.wsdl.Capabilities;
import org.onvif.ver10.receiver.wsdl.ConfigureReceiver;
import org.onvif.ver10.receiver.wsdl.ConfigureReceiverResponse;
import org.onvif.ver10.receiver.wsdl.CreateReceiver;
import org.onvif.ver10.receiver.wsdl.CreateReceiverResponse;
import org.onvif.ver10.receiver.wsdl.DeleteReceiver;
import org.onvif.ver10.receiver.wsdl.DeleteReceiverResponse;
import org.onvif.ver10.receiver.wsdl.GetReceiver;
import org.onvif.ver10.receiver.wsdl.GetReceiverResponse;
import org.onvif.ver10.receiver.wsdl.GetReceiverState;
import org.onvif.ver10.receiver.wsdl.GetReceiverStateResponse;
import org.onvif.ver10.receiver.wsdl.GetReceivers;
import org.onvif.ver10.receiver.wsdl.GetReceiversResponse;
import org.onvif.ver10.receiver.wsdl.GetServiceCapabilities;
import org.onvif.ver10.receiver.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.receiver.wsdl.SetReceiverMode;
import org.onvif.ver10.receiver.wsdl.SetReceiverModeResponse;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/receiver/wsdl", "Capabilities");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetReceivers createGetReceivers() {
        return new GetReceivers();
    }

    public GetReceiversResponse createGetReceiversResponse() {
        return new GetReceiversResponse();
    }

    public GetReceiver createGetReceiver() {
        return new GetReceiver();
    }

    public GetReceiverResponse createGetReceiverResponse() {
        return new GetReceiverResponse();
    }

    public CreateReceiver createCreateReceiver() {
        return new CreateReceiver();
    }

    public CreateReceiverResponse createCreateReceiverResponse() {
        return new CreateReceiverResponse();
    }

    public DeleteReceiver createDeleteReceiver() {
        return new DeleteReceiver();
    }

    public DeleteReceiverResponse createDeleteReceiverResponse() {
        return new DeleteReceiverResponse();
    }

    public ConfigureReceiver createConfigureReceiver() {
        return new ConfigureReceiver();
    }

    public ConfigureReceiverResponse createConfigureReceiverResponse() {
        return new ConfigureReceiverResponse();
    }

    public SetReceiverMode createSetReceiverMode() {
        return new SetReceiverMode();
    }

    public SetReceiverModeResponse createSetReceiverModeResponse() {
        return new SetReceiverModeResponse();
    }

    public GetReceiverState createGetReceiverState() {
        return new GetReceiverState();
    }

    public GetReceiverStateResponse createGetReceiverStateResponse() {
        return new GetReceiverStateResponse();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/receiver/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

