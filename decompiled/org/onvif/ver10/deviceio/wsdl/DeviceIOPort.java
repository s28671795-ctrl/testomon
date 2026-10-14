/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.math.BigInteger;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.Duration;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.deviceio.wsdl.Capabilities;
import org.onvif.ver10.deviceio.wsdl.DigitalInputConfigurationInputOptions;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.ObjectFactory;
import org.onvif.ver10.deviceio.wsdl.RelayOutputOptions;
import org.onvif.ver10.deviceio.wsdl.SerialData;
import org.onvif.ver10.deviceio.wsdl.SerialPort;
import org.onvif.ver10.deviceio.wsdl.SerialPortConfiguration;
import org.onvif.ver10.deviceio.wsdl.SerialPortConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.SetAudioOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetAudioOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.SetAudioSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetAudioSourceConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.SetVideoOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetVideoOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.SetVideoSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetVideoSourceConfigurationResponse;
import org.onvif.ver10.schema.DigitalInput;
import org.onvif.ver10.schema.RelayLogicalState;
import org.onvif.ver10.schema.RelayOutput;
import org.onvif.ver10.schema.VideoOutput;

@WebService(targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="DeviceIOPort")
@XmlSeeAlso(value={ObjectFactory.class, org.onvif.ver10.device.wsdl.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface DeviceIOPort {
    @WebMethod(operationName="GetDigitalInputConfigurationOptions", action="http://www.onvif.org/ver10/deviceio/wsdl/GetDigitalInputConfigurationOptions")
    @RequestWrapper(localName="GetDigitalInputConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetDigitalInputConfigurationOptions")
    @ResponseWrapper(localName="GetDigitalInputConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetDigitalInputConfigurationOptionsResponse")
    @WebResult(name="DigitalInputOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public DigitalInputConfigurationInputOptions getDigitalInputConfigurationOptions(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") String var1);

    @WebMethod(operationName="GetDigitalInputs", action="http://www.onvif.org/ver10/deviceio/wsdl/GetDigitalInputs")
    @RequestWrapper(localName="GetDigitalInputs", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetDigitalInputs")
    @ResponseWrapper(localName="GetDigitalInputsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetDigitalInputsResponse")
    @WebResult(name="DigitalInputs", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public List<DigitalInput> getDigitalInputs();

    @WebMethod(operationName="GetSerialPortConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/GetSerialPortConfigurations")
    @RequestWrapper(localName="GetSerialPortConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetSerialPortConfiguration")
    @ResponseWrapper(localName="GetSerialPortConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetSerialPortConfigurationResponse")
    @WebResult(name="SerialPortConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public SerialPortConfiguration getSerialPortConfiguration(@WebParam(name="SerialPortToken", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") String var1);

    @WebMethod(operationName="GetVideoSourceConfigurationOptions", action="http://www.onvif.org/ver10/deviceio/wsdl/GetVideoSourceConfigurationOptions")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetVideoSourceConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetVideoSourceConfigurationOptionsResponse getVideoSourceConfigurationOptions(@WebParam(partName="parameters", name="GetVideoSourceConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetVideoSourceConfigurationOptions var1);

    @WebMethod(operationName="SetDigitalInputConfigurations", action="http://www.onvif.org/ver10/deviceio/wsdl/SetDigitalInputConfigurations")
    @RequestWrapper(localName="SetDigitalInputConfigurations", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SetDigitalInputConfigurations")
    @ResponseWrapper(localName="SetDigitalInputConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SetDigitalInputConfigurationsResponse")
    public void setDigitalInputConfigurations(@WebParam(name="DigitalInputs", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") List<DigitalInput> var1);

    @WebMethod(operationName="SendReceiveSerialCommand", action="http://www.onvif.org/ver10/deviceio/wsdl/SendReceiveSerialCommand")
    @RequestWrapper(localName="SendReceiveSerialCommand", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SendReceiveSerialCommand")
    @ResponseWrapper(localName="SendReceiveSerialCommandResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SendReceiveSerialCommandResponse")
    public void sendReceiveSerialCommand(@WebParam(mode=WebParam.Mode.INOUT, name="SerialData", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") Holder<SerialData> var1, @WebParam(name="TimeOut", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") Duration var2, @WebParam(name="DataLength", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") BigInteger var3, @WebParam(name="Delimiter", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") String var4);

    @WebMethod(operationName="GetVideoOutputConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/GetVideoOutputConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetVideoOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetVideoOutputConfigurationResponse getVideoOutputConfiguration(@WebParam(partName="parameters", name="GetVideoOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetVideoOutputConfiguration var1);

    @WebMethod(operationName="GetAudioOutputConfigurationOptions", action="http://www.onvif.org/ver10/deviceio/wsdl/GetAudioOutputConfigurationOptions")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetAudioOutputConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetAudioOutputConfigurationOptionsResponse getAudioOutputConfigurationOptions(@WebParam(partName="parameters", name="GetAudioOutputConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetAudioOutputConfigurationOptions var1);

    @WebMethod(operationName="SetRelayOutputSettings", action="http://www.onvif.org/ver10/deviceio/wsdl/SetRelayOutputSettings")
    @RequestWrapper(localName="SetRelayOutputSettings", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SetRelayOutputSettings")
    @ResponseWrapper(localName="SetRelayOutputSettingsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SetRelayOutputSettingsResponse")
    public void setRelayOutputSettings(@WebParam(name="RelayOutput", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") RelayOutput var1);

    @WebMethod(operationName="GetRelayOutputOptions", action="http://www.onvif.org/ver10/deviceio/wsdl/GetRelayOutputOptions")
    @RequestWrapper(localName="GetRelayOutputOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetRelayOutputOptions")
    @ResponseWrapper(localName="GetRelayOutputOptionsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetRelayOutputOptionsResponse")
    @WebResult(name="RelayOutputOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public List<RelayOutputOptions> getRelayOutputOptions(@WebParam(name="RelayOutputToken", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") String var1);

    @WebMethod(operationName="SetVideoSourceConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/SetVideoSourceConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetVideoSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public SetVideoSourceConfigurationResponse setVideoSourceConfiguration(@WebParam(partName="parameters", name="SetVideoSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") SetVideoSourceConfiguration var1);

    @WebMethod(operationName="GetVideoSourceConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/GetVideoSourceConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetVideoSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetVideoSourceConfigurationResponse getVideoSourceConfiguration(@WebParam(partName="parameters", name="GetVideoSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetVideoSourceConfiguration var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/deviceio/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public Capabilities getServiceCapabilities();

    @WebMethod(operationName="GetAudioSourceConfigurationOptions", action="http://www.onvif.org/ver10/deviceio/wsdl/GetAudioSourceConfigurationOptions")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetAudioSourceConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetAudioSourceConfigurationOptionsResponse getAudioSourceConfigurationOptions(@WebParam(partName="parameters", name="GetAudioSourceConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetAudioSourceConfigurationOptions var1);

    @WebMethod(operationName="SetVideoOutputConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/SetVideoOutputConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetVideoOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public SetVideoOutputConfigurationResponse setVideoOutputConfiguration(@WebParam(partName="parameters", name="SetVideoOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") SetVideoOutputConfiguration var1);

    @WebMethod(operationName="GetVideoOutputs", action="http://www.onvif.org/ver10/deviceio/wsdl/GetVideoOutputs")
    @RequestWrapper(localName="GetVideoOutputs", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetVideoOutputs")
    @ResponseWrapper(localName="GetVideoOutputsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetVideoOutputsResponse")
    @WebResult(name="VideoOutputs", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public List<VideoOutput> getVideoOutputs();

    @WebMethod(operationName="GetSerialPortConfigurationOptions", action="http://www.onvif.org/ver10/deviceio/wsdl/GetSerialPortConfigurationOptions")
    @RequestWrapper(localName="GetSerialPortConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetSerialPortConfigurationOptions")
    @ResponseWrapper(localName="GetSerialPortConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetSerialPortConfigurationOptionsResponse")
    @WebResult(name="SerialPortOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public SerialPortConfigurationOptions getSerialPortConfigurationOptions(@WebParam(name="SerialPortToken", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") String var1);

    @WebMethod(operationName="GetAudioOutputs", action="http://www.onvif.org/ver10/deviceio/wsdl/GetAudioOutputs")
    @RequestWrapper(localName="GetAudioOutputs", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.Get")
    @ResponseWrapper(localName="GetAudioOutputsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetResponse")
    @WebResult(name="Token", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public List<String> getAudioOutputs();

    @WebMethod(operationName="SetSerialPortConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/SetSerialPortConfiguration")
    @RequestWrapper(localName="SetSerialPortConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SetSerialPortConfiguration")
    @ResponseWrapper(localName="SetSerialPortConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.SetSerialPortConfigurationResponse")
    public void setSerialPortConfiguration(@WebParam(name="SerialPortConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") SerialPortConfiguration var1, @WebParam(name="ForcePersistance", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") boolean var2);

    @WebMethod(operationName="SetRelayOutputState", action="http://www.onvif.org/ver10/deviceio/wsdl/SetRelayOutputState")
    @RequestWrapper(localName="SetRelayOutputState", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRelayOutputState")
    @ResponseWrapper(localName="SetRelayOutputStateResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRelayOutputStateResponse")
    public void setRelayOutputState(@WebParam(name="RelayOutputToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1, @WebParam(name="LogicalState", targetNamespace="http://www.onvif.org/ver10/device/wsdl") RelayLogicalState var2);

    @WebMethod(operationName="SetAudioSourceConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/SetAudioSourceConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetAudioSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public SetAudioSourceConfigurationResponse setAudioSourceConfiguration(@WebParam(partName="parameters", name="SetAudioSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") SetAudioSourceConfiguration var1);

    @WebMethod(operationName="SetAudioOutputConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/SetAudioOutputConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="SetAudioOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public SetAudioOutputConfigurationResponse setAudioOutputConfiguration(@WebParam(partName="parameters", name="SetAudioOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") SetAudioOutputConfiguration var1);

    @WebMethod(operationName="GetSerialPorts", action="http://www.onvif.org/ver10/deviceio/wsdl/GetSerialPorts")
    @RequestWrapper(localName="GetSerialPorts", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetSerialPorts")
    @ResponseWrapper(localName="GetSerialPortsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetSerialPortsResponse")
    @WebResult(name="SerialPort", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public List<SerialPort> getSerialPorts();

    @WebMethod(operationName="GetAudioOutputConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/GetAudioOutputConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetAudioOutputConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetAudioOutputConfigurationResponse getAudioOutputConfiguration(@WebParam(partName="parameters", name="GetAudioOutputConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetAudioOutputConfiguration var1);

    @WebMethod(operationName="GetAudioSourceConfiguration", action="http://www.onvif.org/ver10/deviceio/wsdl/GetAudioSourceConfiguration")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetAudioSourceConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetAudioSourceConfigurationResponse getAudioSourceConfiguration(@WebParam(partName="parameters", name="GetAudioSourceConfiguration", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetAudioSourceConfiguration var1);

    @WebMethod(operationName="GetVideoSources", action="http://www.onvif.org/ver10/deviceio/wsdl/GetVideoSources")
    @RequestWrapper(localName="GetVideoSources", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.Get")
    @ResponseWrapper(localName="GetVideoSourcesResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetResponse")
    @WebResult(name="Token", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public List<String> getVideoSources();

    @WebMethod(operationName="GetAudioSources", action="http://www.onvif.org/ver10/deviceio/wsdl/GetAudioSources")
    @RequestWrapper(localName="GetAudioSources", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.Get")
    @ResponseWrapper(localName="GetAudioSourcesResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", className="org.onvif.ver10.deviceio.wsdl.GetResponse")
    @WebResult(name="Token", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl")
    public List<String> getAudioSources();

    @WebMethod(operationName="GetVideoOutputConfigurationOptions", action="http://www.onvif.org/ver10/deviceio/wsdl/GetVideoOutputConfigurationOptions")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetVideoOutputConfigurationOptionsResponse", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl", partName="parameters")
    public GetVideoOutputConfigurationOptionsResponse getVideoOutputConfigurationOptions(@WebParam(partName="parameters", name="GetVideoOutputConfigurationOptions", targetNamespace="http://www.onvif.org/ver10/deviceIO/wsdl") GetVideoOutputConfigurationOptions var1);

    @WebMethod(operationName="GetRelayOutputs", action="http://www.onvif.org/ver10/deviceio/wsdl/GetRelayOutputs")
    @RequestWrapper(localName="GetRelayOutputs", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRelayOutputs")
    @ResponseWrapper(localName="GetRelayOutputsResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRelayOutputsResponse")
    @WebResult(name="RelayOutputs", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<RelayOutput> getRelayOutputs();
}

