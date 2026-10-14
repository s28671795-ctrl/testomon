/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.replay.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.replay.wsdl.Capabilities;
import org.onvif.ver10.replay.wsdl.GetReplayConfiguration;
import org.onvif.ver10.replay.wsdl.GetReplayConfigurationResponse;
import org.onvif.ver10.replay.wsdl.GetReplayUri;
import org.onvif.ver10.replay.wsdl.GetReplayUriResponse;
import org.onvif.ver10.replay.wsdl.GetServiceCapabilities;
import org.onvif.ver10.replay.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.replay.wsdl.SetReplayConfiguration;
import org.onvif.ver10.replay.wsdl.SetReplayConfigurationResponse;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/replay/wsdl", "Capabilities");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetReplayUri createGetReplayUri() {
        return new GetReplayUri();
    }

    public GetReplayUriResponse createGetReplayUriResponse() {
        return new GetReplayUriResponse();
    }

    public SetReplayConfiguration createSetReplayConfiguration() {
        return new SetReplayConfiguration();
    }

    public SetReplayConfigurationResponse createSetReplayConfigurationResponse() {
        return new SetReplayConfigurationResponse();
    }

    public GetReplayConfiguration createGetReplayConfiguration() {
        return new GetReplayConfiguration();
    }

    public GetReplayConfigurationResponse createGetReplayConfigurationResponse() {
        return new GetReplayConfigurationResponse();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/replay/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

