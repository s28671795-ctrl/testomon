/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.deviceio.wsdl.Capabilities;
import org.onvif.ver10.deviceio.wsdl.DigitalInputConfigurationInputOptions;
import org.onvif.ver10.deviceio.wsdl.Get;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetAudioOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetAudioSourceConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.GetDigitalInputConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetDigitalInputConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetDigitalInputs;
import org.onvif.ver10.deviceio.wsdl.GetDigitalInputsResponse;
import org.onvif.ver10.deviceio.wsdl.GetRelayOutputOptions;
import org.onvif.ver10.deviceio.wsdl.GetRelayOutputOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetResponse;
import org.onvif.ver10.deviceio.wsdl.GetSerialPortConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetSerialPortConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetSerialPortConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetSerialPortConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.GetSerialPorts;
import org.onvif.ver10.deviceio.wsdl.GetSerialPortsResponse;
import org.onvif.ver10.deviceio.wsdl.GetServiceCapabilities;
import org.onvif.ver10.deviceio.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputs;
import org.onvif.ver10.deviceio.wsdl.GetVideoOutputsResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfigurationOptionsResponse;
import org.onvif.ver10.deviceio.wsdl.GetVideoSourceConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.ParityBitList;
import org.onvif.ver10.deviceio.wsdl.RelayOutputOptions;
import org.onvif.ver10.deviceio.wsdl.RelayOutputOptionsExtension;
import org.onvif.ver10.deviceio.wsdl.SendReceiveSerialCommand;
import org.onvif.ver10.deviceio.wsdl.SendReceiveSerialCommandResponse;
import org.onvif.ver10.deviceio.wsdl.SerialData;
import org.onvif.ver10.deviceio.wsdl.SerialPort;
import org.onvif.ver10.deviceio.wsdl.SerialPortConfiguration;
import org.onvif.ver10.deviceio.wsdl.SerialPortConfigurationOptions;
import org.onvif.ver10.deviceio.wsdl.SetAudioOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetAudioOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.SetAudioSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetAudioSourceConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.SetDigitalInputConfigurations;
import org.onvif.ver10.deviceio.wsdl.SetDigitalInputConfigurationsResponse;
import org.onvif.ver10.deviceio.wsdl.SetRelayOutputSettings;
import org.onvif.ver10.deviceio.wsdl.SetRelayOutputSettingsResponse;
import org.onvif.ver10.deviceio.wsdl.SetSerialPortConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetSerialPortConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.SetVideoOutputConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetVideoOutputConfigurationResponse;
import org.onvif.ver10.deviceio.wsdl.SetVideoSourceConfiguration;
import org.onvif.ver10.deviceio.wsdl.SetVideoSourceConfigurationResponse;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "Capabilities");
    private static final QName _GetVideoSources_QNAME = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "GetVideoSources");
    private static final QName _GetVideoSourcesResponse_QNAME = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "GetVideoSourcesResponse");
    private static final QName _GetAudioSources_QNAME = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "GetAudioSources");
    private static final QName _GetAudioSourcesResponse_QNAME = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "GetAudioSourcesResponse");
    private static final QName _GetAudioOutputs_QNAME = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "GetAudioOutputs");
    private static final QName _GetAudioOutputsResponse_QNAME = new QName("http://www.onvif.org/ver10/deviceIO/wsdl", "GetAudioOutputsResponse");

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetRelayOutputOptions createGetRelayOutputOptions() {
        return new GetRelayOutputOptions();
    }

    public GetRelayOutputOptionsResponse createGetRelayOutputOptionsResponse() {
        return new GetRelayOutputOptionsResponse();
    }

    public RelayOutputOptions createRelayOutputOptions() {
        return new RelayOutputOptions();
    }

    public Get createGet() {
        return new Get();
    }

    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    public GetVideoOutputs createGetVideoOutputs() {
        return new GetVideoOutputs();
    }

    public GetVideoOutputsResponse createGetVideoOutputsResponse() {
        return new GetVideoOutputsResponse();
    }

    public GetAudioSourceConfiguration createGetAudioSourceConfiguration() {
        return new GetAudioSourceConfiguration();
    }

    public GetAudioSourceConfigurationResponse createGetAudioSourceConfigurationResponse() {
        return new GetAudioSourceConfigurationResponse();
    }

    public GetAudioOutputConfiguration createGetAudioOutputConfiguration() {
        return new GetAudioOutputConfiguration();
    }

    public GetAudioOutputConfigurationResponse createGetAudioOutputConfigurationResponse() {
        return new GetAudioOutputConfigurationResponse();
    }

    public GetVideoSourceConfiguration createGetVideoSourceConfiguration() {
        return new GetVideoSourceConfiguration();
    }

    public GetVideoSourceConfigurationResponse createGetVideoSourceConfigurationResponse() {
        return new GetVideoSourceConfigurationResponse();
    }

    public GetVideoOutputConfiguration createGetVideoOutputConfiguration() {
        return new GetVideoOutputConfiguration();
    }

    public GetVideoOutputConfigurationResponse createGetVideoOutputConfigurationResponse() {
        return new GetVideoOutputConfigurationResponse();
    }

    public SetAudioSourceConfiguration createSetAudioSourceConfiguration() {
        return new SetAudioSourceConfiguration();
    }

    public SetAudioSourceConfigurationResponse createSetAudioSourceConfigurationResponse() {
        return new SetAudioSourceConfigurationResponse();
    }

    public SetAudioOutputConfiguration createSetAudioOutputConfiguration() {
        return new SetAudioOutputConfiguration();
    }

    public SetAudioOutputConfigurationResponse createSetAudioOutputConfigurationResponse() {
        return new SetAudioOutputConfigurationResponse();
    }

    public SetVideoSourceConfiguration createSetVideoSourceConfiguration() {
        return new SetVideoSourceConfiguration();
    }

    public SetVideoSourceConfigurationResponse createSetVideoSourceConfigurationResponse() {
        return new SetVideoSourceConfigurationResponse();
    }

    public SetVideoOutputConfiguration createSetVideoOutputConfiguration() {
        return new SetVideoOutputConfiguration();
    }

    public SetVideoOutputConfigurationResponse createSetVideoOutputConfigurationResponse() {
        return new SetVideoOutputConfigurationResponse();
    }

    public GetVideoSourceConfigurationOptions createGetVideoSourceConfigurationOptions() {
        return new GetVideoSourceConfigurationOptions();
    }

    public GetVideoSourceConfigurationOptionsResponse createGetVideoSourceConfigurationOptionsResponse() {
        return new GetVideoSourceConfigurationOptionsResponse();
    }

    public GetVideoOutputConfigurationOptions createGetVideoOutputConfigurationOptions() {
        return new GetVideoOutputConfigurationOptions();
    }

    public GetVideoOutputConfigurationOptionsResponse createGetVideoOutputConfigurationOptionsResponse() {
        return new GetVideoOutputConfigurationOptionsResponse();
    }

    public GetAudioSourceConfigurationOptions createGetAudioSourceConfigurationOptions() {
        return new GetAudioSourceConfigurationOptions();
    }

    public GetAudioSourceConfigurationOptionsResponse createGetAudioSourceConfigurationOptionsResponse() {
        return new GetAudioSourceConfigurationOptionsResponse();
    }

    public GetAudioOutputConfigurationOptions createGetAudioOutputConfigurationOptions() {
        return new GetAudioOutputConfigurationOptions();
    }

    public GetAudioOutputConfigurationOptionsResponse createGetAudioOutputConfigurationOptionsResponse() {
        return new GetAudioOutputConfigurationOptionsResponse();
    }

    public SetRelayOutputSettings createSetRelayOutputSettings() {
        return new SetRelayOutputSettings();
    }

    public SetRelayOutputSettingsResponse createSetRelayOutputSettingsResponse() {
        return new SetRelayOutputSettingsResponse();
    }

    public GetDigitalInputs createGetDigitalInputs() {
        return new GetDigitalInputs();
    }

    public GetDigitalInputsResponse createGetDigitalInputsResponse() {
        return new GetDigitalInputsResponse();
    }

    public GetDigitalInputConfigurationOptions createGetDigitalInputConfigurationOptions() {
        return new GetDigitalInputConfigurationOptions();
    }

    public GetDigitalInputConfigurationOptionsResponse createGetDigitalInputConfigurationOptionsResponse() {
        return new GetDigitalInputConfigurationOptionsResponse();
    }

    public DigitalInputConfigurationInputOptions createDigitalInputConfigurationInputOptions() {
        return new DigitalInputConfigurationInputOptions();
    }

    public SetDigitalInputConfigurations createSetDigitalInputConfigurations() {
        return new SetDigitalInputConfigurations();
    }

    public SetDigitalInputConfigurationsResponse createSetDigitalInputConfigurationsResponse() {
        return new SetDigitalInputConfigurationsResponse();
    }

    public GetSerialPorts createGetSerialPorts() {
        return new GetSerialPorts();
    }

    public GetSerialPortsResponse createGetSerialPortsResponse() {
        return new GetSerialPortsResponse();
    }

    public SerialPort createSerialPort() {
        return new SerialPort();
    }

    public GetSerialPortConfiguration createGetSerialPortConfiguration() {
        return new GetSerialPortConfiguration();
    }

    public GetSerialPortConfigurationResponse createGetSerialPortConfigurationResponse() {
        return new GetSerialPortConfigurationResponse();
    }

    public SerialPortConfiguration createSerialPortConfiguration() {
        return new SerialPortConfiguration();
    }

    public SetSerialPortConfiguration createSetSerialPortConfiguration() {
        return new SetSerialPortConfiguration();
    }

    public SetSerialPortConfigurationResponse createSetSerialPortConfigurationResponse() {
        return new SetSerialPortConfigurationResponse();
    }

    public GetSerialPortConfigurationOptions createGetSerialPortConfigurationOptions() {
        return new GetSerialPortConfigurationOptions();
    }

    public GetSerialPortConfigurationOptionsResponse createGetSerialPortConfigurationOptionsResponse() {
        return new GetSerialPortConfigurationOptionsResponse();
    }

    public SerialPortConfigurationOptions createSerialPortConfigurationOptions() {
        return new SerialPortConfigurationOptions();
    }

    public SendReceiveSerialCommand createSendReceiveSerialCommand() {
        return new SendReceiveSerialCommand();
    }

    public SerialData createSerialData() {
        return new SerialData();
    }

    public SendReceiveSerialCommandResponse createSendReceiveSerialCommandResponse() {
        return new SendReceiveSerialCommandResponse();
    }

    public RelayOutputOptionsExtension createRelayOutputOptionsExtension() {
        return new RelayOutputOptionsExtension();
    }

    public ParityBitList createParityBitList() {
        return new ParityBitList();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="GetVideoSources")
    public JAXBElement<Get> createGetVideoSources(Get value) {
        return new JAXBElement<Get>(_GetVideoSources_QNAME, Get.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="GetVideoSourcesResponse")
    public JAXBElement<GetResponse> createGetVideoSourcesResponse(GetResponse value) {
        return new JAXBElement<GetResponse>(_GetVideoSourcesResponse_QNAME, GetResponse.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="GetAudioSources")
    public JAXBElement<Get> createGetAudioSources(Get value) {
        return new JAXBElement<Get>(_GetAudioSources_QNAME, Get.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="GetAudioSourcesResponse")
    public JAXBElement<GetResponse> createGetAudioSourcesResponse(GetResponse value) {
        return new JAXBElement<GetResponse>(_GetAudioSourcesResponse_QNAME, GetResponse.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="GetAudioOutputs")
    public JAXBElement<Get> createGetAudioOutputs(Get value) {
        return new JAXBElement<Get>(_GetAudioOutputs_QNAME, Get.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/deviceIO/wsdl", name="GetAudioOutputsResponse")
    public JAXBElement<GetResponse> createGetAudioOutputsResponse(GetResponse value) {
        return new JAXBElement<GetResponse>(_GetAudioOutputsResponse_QNAME, GetResponse.class, null, value);
    }
}

