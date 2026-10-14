/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.display.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
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
import org.onvif.ver10.display.wsdl.ObjectFactory;
import org.onvif.ver10.display.wsdl.SetLayout;
import org.onvif.ver10.display.wsdl.SetLayoutResponse;
import org.onvif.ver10.display.wsdl.SetPaneConfiguration;
import org.onvif.ver10.display.wsdl.SetPaneConfigurationResponse;
import org.onvif.ver10.display.wsdl.SetPaneConfigurations;
import org.onvif.ver10.display.wsdl.SetPaneConfigurationsResponse;

@WebService(targetNamespace="http://www.onvif.org/ver10/display/wsdl", name="DisplayPort")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface DisplayPort {
    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/display/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/display/wsdl", className="org.onvif.ver10.display.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", className="org.onvif.ver10.display.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/display/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="GetPaneConfiguration", action="http://www.onvif.org/ver10/display/wsdl/GetPaneConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetPaneConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public GetPaneConfigurationResponse getPaneConfiguration(@WebParam(partName="parameters", name="GetPaneConfiguration", targetNamespace="http://www.onvif.org/ver10/display/wsdl") GetPaneConfiguration var1);

    @WebMethod(operationName="GetPaneConfigurations", action="http://www.onvif.org/ver10/display/wsdl/GetPaneConfigurations")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetPaneConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public GetPaneConfigurationsResponse getPaneConfigurations(@WebParam(partName="parameters", name="GetPaneConfigurations", targetNamespace="http://www.onvif.org/ver10/display/wsdl") GetPaneConfigurations var1);

    @WebMethod(operationName="GetLayout", action="http://www.onvif.org/ver10/display/wsdl/GetLayout")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetLayoutResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public GetLayoutResponse getLayout(@WebParam(partName="parameters", name="GetLayout", targetNamespace="http://www.onvif.org/ver10/display/wsdl") GetLayout var1);

    @WebMethod(operationName="SetLayout", action="http://www.onvif.org/ver10/display/wsdl/SetLayout")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetLayoutResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public SetLayoutResponse setLayout(@WebParam(partName="parameters", name="SetLayout", targetNamespace="http://www.onvif.org/ver10/display/wsdl") SetLayout var1);

    @WebMethod(operationName="DeletePaneConfiguration", action="http://www.onvif.org/ver10/display/wsdl/DeletePaneConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="DeletePaneConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public DeletePaneConfigurationResponse deletePaneConfiguration(@WebParam(partName="parameters", name="DeletePaneConfiguration", targetNamespace="http://www.onvif.org/ver10/display/wsdl") DeletePaneConfiguration var1);

    @WebMethod(operationName="CreatePaneConfiguration", action="http://www.onvif.org/ver10/display/wsdl/CreatePaneConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="CreatePaneConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public CreatePaneConfigurationResponse createPaneConfiguration(@WebParam(partName="parameters", name="CreatePaneConfiguration", targetNamespace="http://www.onvif.org/ver10/display/wsdl") CreatePaneConfiguration var1);

    @WebMethod(operationName="SetPaneConfigurations", action="http://www.onvif.org/ver10/display/wsdl/SetPaneConfigurations")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetPaneConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public SetPaneConfigurationsResponse setPaneConfigurations(@WebParam(partName="parameters", name="SetPaneConfigurations", targetNamespace="http://www.onvif.org/ver10/display/wsdl") SetPaneConfigurations var1);

    @WebMethod(operationName="SetPaneConfiguration", action="http://www.onvif.org/ver10/display/wsdl/SetPaneConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetPaneConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public SetPaneConfigurationResponse setPaneConfiguration(@WebParam(partName="parameters", name="SetPaneConfiguration", targetNamespace="http://www.onvif.org/ver10/display/wsdl") SetPaneConfiguration var1);

    @WebMethod(operationName="GetDisplayOptions", action="http://www.onvif.org/ver10/display/wsdl/GetDisplayOptions")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetDisplayOptionsResponse", targetNamespace="http://www.onvif.org/ver10/display/wsdl", partName="parameters")
    public GetDisplayOptionsResponse getDisplayOptions(@WebParam(partName="parameters", name="GetDisplayOptions", targetNamespace="http://www.onvif.org/ver10/display/wsdl") GetDisplayOptions var1);
}

