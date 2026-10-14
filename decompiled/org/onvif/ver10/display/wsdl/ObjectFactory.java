/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.display.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.display.wsdl.Capabilities;
import org.onvif.ver10.display.wsdl.CreatePaneConfiguration;
import org.onvif.ver10.display.wsdl.CreatePaneConfigurationResponse;
import org.onvif.ver10.display.wsdl.DeletePaneConfiguration;
import org.onvif.ver10.display.wsdl.DeletePaneConfigurationResponse;
import org.onvif.ver10.display.wsdl.GetDisplayOptions;
import org.onvif.ver10.display.wsdl.GetDisplayOptionsResponse;
import org.onvif.ver10.display.wsdl.GetLayout;
import org.onvif.ver10.display.wsdl.GetLayoutResponse;
import org.onvif.ver10.display.wsdl.GetPaneConfiguration;
import org.onvif.ver10.display.wsdl.GetPaneConfigurationResponse;
import org.onvif.ver10.display.wsdl.GetPaneConfigurations;
import org.onvif.ver10.display.wsdl.GetPaneConfigurationsResponse;
import org.onvif.ver10.display.wsdl.GetServiceCapabilities;
import org.onvif.ver10.display.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.display.wsdl.SetLayout;
import org.onvif.ver10.display.wsdl.SetLayoutResponse;
import org.onvif.ver10.display.wsdl.SetPaneConfiguration;
import org.onvif.ver10.display.wsdl.SetPaneConfigurationResponse;
import org.onvif.ver10.display.wsdl.SetPaneConfigurations;
import org.onvif.ver10.display.wsdl.SetPaneConfigurationsResponse;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/display/wsdl", "Capabilities");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetLayout createGetLayout() {
        return new GetLayout();
    }

    public GetLayoutResponse createGetLayoutResponse() {
        return new GetLayoutResponse();
    }

    public SetLayout createSetLayout() {
        return new SetLayout();
    }

    public SetLayoutResponse createSetLayoutResponse() {
        return new SetLayoutResponse();
    }

    public GetDisplayOptions createGetDisplayOptions() {
        return new GetDisplayOptions();
    }

    public GetDisplayOptionsResponse createGetDisplayOptionsResponse() {
        return new GetDisplayOptionsResponse();
    }

    public GetPaneConfigurations createGetPaneConfigurations() {
        return new GetPaneConfigurations();
    }

    public GetPaneConfigurationsResponse createGetPaneConfigurationsResponse() {
        return new GetPaneConfigurationsResponse();
    }

    public GetPaneConfiguration createGetPaneConfiguration() {
        return new GetPaneConfiguration();
    }

    public GetPaneConfigurationResponse createGetPaneConfigurationResponse() {
        return new GetPaneConfigurationResponse();
    }

    public SetPaneConfigurations createSetPaneConfigurations() {
        return new SetPaneConfigurations();
    }

    public SetPaneConfigurationsResponse createSetPaneConfigurationsResponse() {
        return new SetPaneConfigurationsResponse();
    }

    public SetPaneConfiguration createSetPaneConfiguration() {
        return new SetPaneConfiguration();
    }

    public SetPaneConfigurationResponse createSetPaneConfigurationResponse() {
        return new SetPaneConfigurationResponse();
    }

    public CreatePaneConfiguration createCreatePaneConfiguration() {
        return new CreatePaneConfiguration();
    }

    public CreatePaneConfigurationResponse createCreatePaneConfigurationResponse() {
        return new CreatePaneConfigurationResponse();
    }

    public DeletePaneConfiguration createDeletePaneConfiguration() {
        return new DeletePaneConfiguration();
    }

    public DeletePaneConfigurationResponse createDeletePaneConfigurationResponse() {
        return new DeletePaneConfigurationResponse();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/display/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }
}

