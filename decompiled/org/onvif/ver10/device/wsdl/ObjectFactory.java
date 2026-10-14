/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.onvif.ver10.device.wsdl.AddIPAddressFilter;
import org.onvif.ver10.device.wsdl.AddIPAddressFilterResponse;
import org.onvif.ver10.device.wsdl.AddScopes;
import org.onvif.ver10.device.wsdl.AddScopesResponse;
import org.onvif.ver10.device.wsdl.CreateCertificate;
import org.onvif.ver10.device.wsdl.CreateCertificateResponse;
import org.onvif.ver10.device.wsdl.CreateDot1XConfiguration;
import org.onvif.ver10.device.wsdl.CreateDot1XConfigurationResponse;
import org.onvif.ver10.device.wsdl.CreateStorageConfiguration;
import org.onvif.ver10.device.wsdl.CreateStorageConfigurationResponse;
import org.onvif.ver10.device.wsdl.CreateUsers;
import org.onvif.ver10.device.wsdl.CreateUsersResponse;
import org.onvif.ver10.device.wsdl.DeleteCertificates;
import org.onvif.ver10.device.wsdl.DeleteCertificatesResponse;
import org.onvif.ver10.device.wsdl.DeleteDot1XConfiguration;
import org.onvif.ver10.device.wsdl.DeleteDot1XConfigurationResponse;
import org.onvif.ver10.device.wsdl.DeleteStorageConfiguration;
import org.onvif.ver10.device.wsdl.DeleteStorageConfigurationResponse;
import org.onvif.ver10.device.wsdl.DeleteUsers;
import org.onvif.ver10.device.wsdl.DeleteUsersResponse;
import org.onvif.ver10.device.wsdl.DeviceServiceCapabilities;
import org.onvif.ver10.device.wsdl.GetAccessPolicy;
import org.onvif.ver10.device.wsdl.GetAccessPolicyResponse;
import org.onvif.ver10.device.wsdl.GetCACertificates;
import org.onvif.ver10.device.wsdl.GetCACertificatesResponse;
import org.onvif.ver10.device.wsdl.GetCapabilities;
import org.onvif.ver10.device.wsdl.GetCapabilitiesResponse;
import org.onvif.ver10.device.wsdl.GetCertificateInformation;
import org.onvif.ver10.device.wsdl.GetCertificateInformationResponse;
import org.onvif.ver10.device.wsdl.GetCertificates;
import org.onvif.ver10.device.wsdl.GetCertificatesResponse;
import org.onvif.ver10.device.wsdl.GetCertificatesStatus;
import org.onvif.ver10.device.wsdl.GetCertificatesStatusResponse;
import org.onvif.ver10.device.wsdl.GetClientCertificateMode;
import org.onvif.ver10.device.wsdl.GetClientCertificateModeResponse;
import org.onvif.ver10.device.wsdl.GetDNS;
import org.onvif.ver10.device.wsdl.GetDNSResponse;
import org.onvif.ver10.device.wsdl.GetDPAddresses;
import org.onvif.ver10.device.wsdl.GetDPAddressesResponse;
import org.onvif.ver10.device.wsdl.GetDeviceInformation;
import org.onvif.ver10.device.wsdl.GetDeviceInformationResponse;
import org.onvif.ver10.device.wsdl.GetDiscoveryMode;
import org.onvif.ver10.device.wsdl.GetDiscoveryModeResponse;
import org.onvif.ver10.device.wsdl.GetDot11Capabilities;
import org.onvif.ver10.device.wsdl.GetDot11CapabilitiesResponse;
import org.onvif.ver10.device.wsdl.GetDot11Status;
import org.onvif.ver10.device.wsdl.GetDot11StatusResponse;
import org.onvif.ver10.device.wsdl.GetDot1XConfiguration;
import org.onvif.ver10.device.wsdl.GetDot1XConfigurationResponse;
import org.onvif.ver10.device.wsdl.GetDot1XConfigurations;
import org.onvif.ver10.device.wsdl.GetDot1XConfigurationsResponse;
import org.onvif.ver10.device.wsdl.GetDynamicDNS;
import org.onvif.ver10.device.wsdl.GetDynamicDNSResponse;
import org.onvif.ver10.device.wsdl.GetEndpointReference;
import org.onvif.ver10.device.wsdl.GetEndpointReferenceResponse;
import org.onvif.ver10.device.wsdl.GetHostname;
import org.onvif.ver10.device.wsdl.GetHostnameResponse;
import org.onvif.ver10.device.wsdl.GetIPAddressFilter;
import org.onvif.ver10.device.wsdl.GetIPAddressFilterResponse;
import org.onvif.ver10.device.wsdl.GetNTP;
import org.onvif.ver10.device.wsdl.GetNTPResponse;
import org.onvif.ver10.device.wsdl.GetNetworkDefaultGateway;
import org.onvif.ver10.device.wsdl.GetNetworkDefaultGatewayResponse;
import org.onvif.ver10.device.wsdl.GetNetworkInterfaces;
import org.onvif.ver10.device.wsdl.GetNetworkInterfacesResponse;
import org.onvif.ver10.device.wsdl.GetNetworkProtocols;
import org.onvif.ver10.device.wsdl.GetNetworkProtocolsResponse;
import org.onvif.ver10.device.wsdl.GetPkcs10Request;
import org.onvif.ver10.device.wsdl.GetPkcs10RequestResponse;
import org.onvif.ver10.device.wsdl.GetRelayOutputs;
import org.onvif.ver10.device.wsdl.GetRelayOutputsResponse;
import org.onvif.ver10.device.wsdl.GetRemoteDiscoveryMode;
import org.onvif.ver10.device.wsdl.GetRemoteDiscoveryModeResponse;
import org.onvif.ver10.device.wsdl.GetRemoteUser;
import org.onvif.ver10.device.wsdl.GetRemoteUserResponse;
import org.onvif.ver10.device.wsdl.GetScopes;
import org.onvif.ver10.device.wsdl.GetScopesResponse;
import org.onvif.ver10.device.wsdl.GetServiceCapabilities;
import org.onvif.ver10.device.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.device.wsdl.GetServices;
import org.onvif.ver10.device.wsdl.GetServicesResponse;
import org.onvif.ver10.device.wsdl.GetStorageConfiguration;
import org.onvif.ver10.device.wsdl.GetStorageConfigurationResponse;
import org.onvif.ver10.device.wsdl.GetStorageConfigurations;
import org.onvif.ver10.device.wsdl.GetStorageConfigurationsResponse;
import org.onvif.ver10.device.wsdl.GetSystemBackup;
import org.onvif.ver10.device.wsdl.GetSystemBackupResponse;
import org.onvif.ver10.device.wsdl.GetSystemDateAndTime;
import org.onvif.ver10.device.wsdl.GetSystemDateAndTimeResponse;
import org.onvif.ver10.device.wsdl.GetSystemLog;
import org.onvif.ver10.device.wsdl.GetSystemLogResponse;
import org.onvif.ver10.device.wsdl.GetSystemSupportInformation;
import org.onvif.ver10.device.wsdl.GetSystemSupportInformationResponse;
import org.onvif.ver10.device.wsdl.GetSystemUris;
import org.onvif.ver10.device.wsdl.GetSystemUrisResponse;
import org.onvif.ver10.device.wsdl.GetUsers;
import org.onvif.ver10.device.wsdl.GetUsersResponse;
import org.onvif.ver10.device.wsdl.GetWsdlUrl;
import org.onvif.ver10.device.wsdl.GetWsdlUrlResponse;
import org.onvif.ver10.device.wsdl.GetZeroConfiguration;
import org.onvif.ver10.device.wsdl.GetZeroConfigurationResponse;
import org.onvif.ver10.device.wsdl.LoadCACertificates;
import org.onvif.ver10.device.wsdl.LoadCACertificatesResponse;
import org.onvif.ver10.device.wsdl.LoadCertificateWithPrivateKey;
import org.onvif.ver10.device.wsdl.LoadCertificateWithPrivateKeyResponse;
import org.onvif.ver10.device.wsdl.LoadCertificates;
import org.onvif.ver10.device.wsdl.LoadCertificatesResponse;
import org.onvif.ver10.device.wsdl.MiscCapabilities;
import org.onvif.ver10.device.wsdl.NetworkCapabilities;
import org.onvif.ver10.device.wsdl.RemoveIPAddressFilter;
import org.onvif.ver10.device.wsdl.RemoveIPAddressFilterResponse;
import org.onvif.ver10.device.wsdl.RemoveScopes;
import org.onvif.ver10.device.wsdl.RemoveScopesResponse;
import org.onvif.ver10.device.wsdl.RestoreSystem;
import org.onvif.ver10.device.wsdl.RestoreSystemResponse;
import org.onvif.ver10.device.wsdl.ScanAvailableDot11Networks;
import org.onvif.ver10.device.wsdl.ScanAvailableDot11NetworksResponse;
import org.onvif.ver10.device.wsdl.SecurityCapabilities;
import org.onvif.ver10.device.wsdl.SendAuxiliaryCommand;
import org.onvif.ver10.device.wsdl.SendAuxiliaryCommandResponse;
import org.onvif.ver10.device.wsdl.Service;
import org.onvif.ver10.device.wsdl.SetAccessPolicy;
import org.onvif.ver10.device.wsdl.SetAccessPolicyResponse;
import org.onvif.ver10.device.wsdl.SetCertificatesStatus;
import org.onvif.ver10.device.wsdl.SetCertificatesStatusResponse;
import org.onvif.ver10.device.wsdl.SetClientCertificateMode;
import org.onvif.ver10.device.wsdl.SetClientCertificateModeResponse;
import org.onvif.ver10.device.wsdl.SetDNS;
import org.onvif.ver10.device.wsdl.SetDNSResponse;
import org.onvif.ver10.device.wsdl.SetDPAddresses;
import org.onvif.ver10.device.wsdl.SetDPAddressesResponse;
import org.onvif.ver10.device.wsdl.SetDiscoveryMode;
import org.onvif.ver10.device.wsdl.SetDiscoveryModeResponse;
import org.onvif.ver10.device.wsdl.SetDot1XConfiguration;
import org.onvif.ver10.device.wsdl.SetDot1XConfigurationResponse;
import org.onvif.ver10.device.wsdl.SetDynamicDNS;
import org.onvif.ver10.device.wsdl.SetDynamicDNSResponse;
import org.onvif.ver10.device.wsdl.SetHostname;
import org.onvif.ver10.device.wsdl.SetHostnameFromDHCP;
import org.onvif.ver10.device.wsdl.SetHostnameFromDHCPResponse;
import org.onvif.ver10.device.wsdl.SetHostnameResponse;
import org.onvif.ver10.device.wsdl.SetIPAddressFilter;
import org.onvif.ver10.device.wsdl.SetIPAddressFilterResponse;
import org.onvif.ver10.device.wsdl.SetNTP;
import org.onvif.ver10.device.wsdl.SetNTPResponse;
import org.onvif.ver10.device.wsdl.SetNetworkDefaultGateway;
import org.onvif.ver10.device.wsdl.SetNetworkDefaultGatewayResponse;
import org.onvif.ver10.device.wsdl.SetNetworkInterfaces;
import org.onvif.ver10.device.wsdl.SetNetworkInterfacesResponse;
import org.onvif.ver10.device.wsdl.SetNetworkProtocols;
import org.onvif.ver10.device.wsdl.SetNetworkProtocolsResponse;
import org.onvif.ver10.device.wsdl.SetRelayOutputSettings;
import org.onvif.ver10.device.wsdl.SetRelayOutputSettingsResponse;
import org.onvif.ver10.device.wsdl.SetRelayOutputState;
import org.onvif.ver10.device.wsdl.SetRelayOutputStateResponse;
import org.onvif.ver10.device.wsdl.SetRemoteDiscoveryMode;
import org.onvif.ver10.device.wsdl.SetRemoteDiscoveryModeResponse;
import org.onvif.ver10.device.wsdl.SetRemoteUser;
import org.onvif.ver10.device.wsdl.SetRemoteUserResponse;
import org.onvif.ver10.device.wsdl.SetScopes;
import org.onvif.ver10.device.wsdl.SetScopesResponse;
import org.onvif.ver10.device.wsdl.SetStorageConfiguration;
import org.onvif.ver10.device.wsdl.SetStorageConfigurationResponse;
import org.onvif.ver10.device.wsdl.SetSystemDateAndTime;
import org.onvif.ver10.device.wsdl.SetSystemDateAndTimeResponse;
import org.onvif.ver10.device.wsdl.SetSystemFactoryDefault;
import org.onvif.ver10.device.wsdl.SetSystemFactoryDefaultResponse;
import org.onvif.ver10.device.wsdl.SetUser;
import org.onvif.ver10.device.wsdl.SetUserResponse;
import org.onvif.ver10.device.wsdl.SetZeroConfiguration;
import org.onvif.ver10.device.wsdl.SetZeroConfigurationResponse;
import org.onvif.ver10.device.wsdl.StartFirmwareUpgrade;
import org.onvif.ver10.device.wsdl.StartFirmwareUpgradeResponse;
import org.onvif.ver10.device.wsdl.StartSystemRestore;
import org.onvif.ver10.device.wsdl.StartSystemRestoreResponse;
import org.onvif.ver10.device.wsdl.StorageConfiguration;
import org.onvif.ver10.device.wsdl.StorageConfigurationData;
import org.onvif.ver10.device.wsdl.SystemCapabilities;
import org.onvif.ver10.device.wsdl.SystemReboot;
import org.onvif.ver10.device.wsdl.SystemRebootResponse;
import org.onvif.ver10.device.wsdl.UpgradeSystemFirmware;
import org.onvif.ver10.device.wsdl.UpgradeSystemFirmwareResponse;
import org.onvif.ver10.device.wsdl.UserCredential;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/device/wsdl", "Capabilities");

    public GetSystemUrisResponse createGetSystemUrisResponse() {
        return new GetSystemUrisResponse();
    }

    public UserCredential createUserCredential() {
        return new UserCredential();
    }

    public StorageConfigurationData createStorageConfigurationData() {
        return new StorageConfigurationData();
    }

    public Service createService() {
        return new Service();
    }

    public GetServices createGetServices() {
        return new GetServices();
    }

    public GetServicesResponse createGetServicesResponse() {
        return new GetServicesResponse();
    }

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public DeviceServiceCapabilities createDeviceServiceCapabilities() {
        return new DeviceServiceCapabilities();
    }

    public GetDeviceInformation createGetDeviceInformation() {
        return new GetDeviceInformation();
    }

    public GetDeviceInformationResponse createGetDeviceInformationResponse() {
        return new GetDeviceInformationResponse();
    }

    public SetSystemDateAndTime createSetSystemDateAndTime() {
        return new SetSystemDateAndTime();
    }

    public SetSystemDateAndTimeResponse createSetSystemDateAndTimeResponse() {
        return new SetSystemDateAndTimeResponse();
    }

    public GetSystemDateAndTime createGetSystemDateAndTime() {
        return new GetSystemDateAndTime();
    }

    public GetSystemDateAndTimeResponse createGetSystemDateAndTimeResponse() {
        return new GetSystemDateAndTimeResponse();
    }

    public SetSystemFactoryDefault createSetSystemFactoryDefault() {
        return new SetSystemFactoryDefault();
    }

    public SetSystemFactoryDefaultResponse createSetSystemFactoryDefaultResponse() {
        return new SetSystemFactoryDefaultResponse();
    }

    public UpgradeSystemFirmware createUpgradeSystemFirmware() {
        return new UpgradeSystemFirmware();
    }

    public UpgradeSystemFirmwareResponse createUpgradeSystemFirmwareResponse() {
        return new UpgradeSystemFirmwareResponse();
    }

    public SystemReboot createSystemReboot() {
        return new SystemReboot();
    }

    public SystemRebootResponse createSystemRebootResponse() {
        return new SystemRebootResponse();
    }

    public RestoreSystem createRestoreSystem() {
        return new RestoreSystem();
    }

    public RestoreSystemResponse createRestoreSystemResponse() {
        return new RestoreSystemResponse();
    }

    public GetSystemBackup createGetSystemBackup() {
        return new GetSystemBackup();
    }

    public GetSystemBackupResponse createGetSystemBackupResponse() {
        return new GetSystemBackupResponse();
    }

    public GetSystemSupportInformation createGetSystemSupportInformation() {
        return new GetSystemSupportInformation();
    }

    public GetSystemSupportInformationResponse createGetSystemSupportInformationResponse() {
        return new GetSystemSupportInformationResponse();
    }

    public GetSystemLog createGetSystemLog() {
        return new GetSystemLog();
    }

    public GetSystemLogResponse createGetSystemLogResponse() {
        return new GetSystemLogResponse();
    }

    public GetScopes createGetScopes() {
        return new GetScopes();
    }

    public GetScopesResponse createGetScopesResponse() {
        return new GetScopesResponse();
    }

    public SetScopes createSetScopes() {
        return new SetScopes();
    }

    public SetScopesResponse createSetScopesResponse() {
        return new SetScopesResponse();
    }

    public AddScopes createAddScopes() {
        return new AddScopes();
    }

    public AddScopesResponse createAddScopesResponse() {
        return new AddScopesResponse();
    }

    public RemoveScopes createRemoveScopes() {
        return new RemoveScopes();
    }

    public RemoveScopesResponse createRemoveScopesResponse() {
        return new RemoveScopesResponse();
    }

    public GetDiscoveryMode createGetDiscoveryMode() {
        return new GetDiscoveryMode();
    }

    public GetDiscoveryModeResponse createGetDiscoveryModeResponse() {
        return new GetDiscoveryModeResponse();
    }

    public SetDiscoveryMode createSetDiscoveryMode() {
        return new SetDiscoveryMode();
    }

    public SetDiscoveryModeResponse createSetDiscoveryModeResponse() {
        return new SetDiscoveryModeResponse();
    }

    public GetRemoteDiscoveryMode createGetRemoteDiscoveryMode() {
        return new GetRemoteDiscoveryMode();
    }

    public GetRemoteDiscoveryModeResponse createGetRemoteDiscoveryModeResponse() {
        return new GetRemoteDiscoveryModeResponse();
    }

    public SetRemoteDiscoveryMode createSetRemoteDiscoveryMode() {
        return new SetRemoteDiscoveryMode();
    }

    public SetRemoteDiscoveryModeResponse createSetRemoteDiscoveryModeResponse() {
        return new SetRemoteDiscoveryModeResponse();
    }

    public GetDPAddresses createGetDPAddresses() {
        return new GetDPAddresses();
    }

    public GetDPAddressesResponse createGetDPAddressesResponse() {
        return new GetDPAddressesResponse();
    }

    public SetDPAddresses createSetDPAddresses() {
        return new SetDPAddresses();
    }

    public SetDPAddressesResponse createSetDPAddressesResponse() {
        return new SetDPAddressesResponse();
    }

    public GetEndpointReference createGetEndpointReference() {
        return new GetEndpointReference();
    }

    public GetEndpointReferenceResponse createGetEndpointReferenceResponse() {
        return new GetEndpointReferenceResponse();
    }

    public GetRemoteUser createGetRemoteUser() {
        return new GetRemoteUser();
    }

    public GetRemoteUserResponse createGetRemoteUserResponse() {
        return new GetRemoteUserResponse();
    }

    public SetRemoteUser createSetRemoteUser() {
        return new SetRemoteUser();
    }

    public SetRemoteUserResponse createSetRemoteUserResponse() {
        return new SetRemoteUserResponse();
    }

    public GetUsers createGetUsers() {
        return new GetUsers();
    }

    public GetUsersResponse createGetUsersResponse() {
        return new GetUsersResponse();
    }

    public CreateUsers createCreateUsers() {
        return new CreateUsers();
    }

    public CreateUsersResponse createCreateUsersResponse() {
        return new CreateUsersResponse();
    }

    public DeleteUsers createDeleteUsers() {
        return new DeleteUsers();
    }

    public DeleteUsersResponse createDeleteUsersResponse() {
        return new DeleteUsersResponse();
    }

    public SetUser createSetUser() {
        return new SetUser();
    }

    public SetUserResponse createSetUserResponse() {
        return new SetUserResponse();
    }

    public GetWsdlUrl createGetWsdlUrl() {
        return new GetWsdlUrl();
    }

    public GetWsdlUrlResponse createGetWsdlUrlResponse() {
        return new GetWsdlUrlResponse();
    }

    public GetCapabilities createGetCapabilities() {
        return new GetCapabilities();
    }

    public GetCapabilitiesResponse createGetCapabilitiesResponse() {
        return new GetCapabilitiesResponse();
    }

    public GetHostname createGetHostname() {
        return new GetHostname();
    }

    public GetHostnameResponse createGetHostnameResponse() {
        return new GetHostnameResponse();
    }

    public SetHostname createSetHostname() {
        return new SetHostname();
    }

    public SetHostnameResponse createSetHostnameResponse() {
        return new SetHostnameResponse();
    }

    public SetHostnameFromDHCP createSetHostnameFromDHCP() {
        return new SetHostnameFromDHCP();
    }

    public SetHostnameFromDHCPResponse createSetHostnameFromDHCPResponse() {
        return new SetHostnameFromDHCPResponse();
    }

    public GetDNS createGetDNS() {
        return new GetDNS();
    }

    public GetDNSResponse createGetDNSResponse() {
        return new GetDNSResponse();
    }

    public SetDNS createSetDNS() {
        return new SetDNS();
    }

    public SetDNSResponse createSetDNSResponse() {
        return new SetDNSResponse();
    }

    public GetNTP createGetNTP() {
        return new GetNTP();
    }

    public GetNTPResponse createGetNTPResponse() {
        return new GetNTPResponse();
    }

    public SetNTP createSetNTP() {
        return new SetNTP();
    }

    public SetNTPResponse createSetNTPResponse() {
        return new SetNTPResponse();
    }

    public GetDynamicDNS createGetDynamicDNS() {
        return new GetDynamicDNS();
    }

    public GetDynamicDNSResponse createGetDynamicDNSResponse() {
        return new GetDynamicDNSResponse();
    }

    public SetDynamicDNS createSetDynamicDNS() {
        return new SetDynamicDNS();
    }

    public SetDynamicDNSResponse createSetDynamicDNSResponse() {
        return new SetDynamicDNSResponse();
    }

    public GetNetworkInterfaces createGetNetworkInterfaces() {
        return new GetNetworkInterfaces();
    }

    public GetNetworkInterfacesResponse createGetNetworkInterfacesResponse() {
        return new GetNetworkInterfacesResponse();
    }

    public SetNetworkInterfaces createSetNetworkInterfaces() {
        return new SetNetworkInterfaces();
    }

    public SetNetworkInterfacesResponse createSetNetworkInterfacesResponse() {
        return new SetNetworkInterfacesResponse();
    }

    public GetNetworkProtocols createGetNetworkProtocols() {
        return new GetNetworkProtocols();
    }

    public GetNetworkProtocolsResponse createGetNetworkProtocolsResponse() {
        return new GetNetworkProtocolsResponse();
    }

    public SetNetworkProtocols createSetNetworkProtocols() {
        return new SetNetworkProtocols();
    }

    public SetNetworkProtocolsResponse createSetNetworkProtocolsResponse() {
        return new SetNetworkProtocolsResponse();
    }

    public GetNetworkDefaultGateway createGetNetworkDefaultGateway() {
        return new GetNetworkDefaultGateway();
    }

    public GetNetworkDefaultGatewayResponse createGetNetworkDefaultGatewayResponse() {
        return new GetNetworkDefaultGatewayResponse();
    }

    public SetNetworkDefaultGateway createSetNetworkDefaultGateway() {
        return new SetNetworkDefaultGateway();
    }

    public SetNetworkDefaultGatewayResponse createSetNetworkDefaultGatewayResponse() {
        return new SetNetworkDefaultGatewayResponse();
    }

    public GetZeroConfiguration createGetZeroConfiguration() {
        return new GetZeroConfiguration();
    }

    public GetZeroConfigurationResponse createGetZeroConfigurationResponse() {
        return new GetZeroConfigurationResponse();
    }

    public SetZeroConfiguration createSetZeroConfiguration() {
        return new SetZeroConfiguration();
    }

    public SetZeroConfigurationResponse createSetZeroConfigurationResponse() {
        return new SetZeroConfigurationResponse();
    }

    public GetIPAddressFilter createGetIPAddressFilter() {
        return new GetIPAddressFilter();
    }

    public GetIPAddressFilterResponse createGetIPAddressFilterResponse() {
        return new GetIPAddressFilterResponse();
    }

    public SetIPAddressFilter createSetIPAddressFilter() {
        return new SetIPAddressFilter();
    }

    public SetIPAddressFilterResponse createSetIPAddressFilterResponse() {
        return new SetIPAddressFilterResponse();
    }

    public AddIPAddressFilter createAddIPAddressFilter() {
        return new AddIPAddressFilter();
    }

    public AddIPAddressFilterResponse createAddIPAddressFilterResponse() {
        return new AddIPAddressFilterResponse();
    }

    public RemoveIPAddressFilter createRemoveIPAddressFilter() {
        return new RemoveIPAddressFilter();
    }

    public RemoveIPAddressFilterResponse createRemoveIPAddressFilterResponse() {
        return new RemoveIPAddressFilterResponse();
    }

    public GetAccessPolicy createGetAccessPolicy() {
        return new GetAccessPolicy();
    }

    public GetAccessPolicyResponse createGetAccessPolicyResponse() {
        return new GetAccessPolicyResponse();
    }

    public SetAccessPolicy createSetAccessPolicy() {
        return new SetAccessPolicy();
    }

    public SetAccessPolicyResponse createSetAccessPolicyResponse() {
        return new SetAccessPolicyResponse();
    }

    public CreateCertificate createCreateCertificate() {
        return new CreateCertificate();
    }

    public CreateCertificateResponse createCreateCertificateResponse() {
        return new CreateCertificateResponse();
    }

    public GetCertificates createGetCertificates() {
        return new GetCertificates();
    }

    public GetCertificatesResponse createGetCertificatesResponse() {
        return new GetCertificatesResponse();
    }

    public GetCertificatesStatus createGetCertificatesStatus() {
        return new GetCertificatesStatus();
    }

    public GetCertificatesStatusResponse createGetCertificatesStatusResponse() {
        return new GetCertificatesStatusResponse();
    }

    public SetCertificatesStatus createSetCertificatesStatus() {
        return new SetCertificatesStatus();
    }

    public SetCertificatesStatusResponse createSetCertificatesStatusResponse() {
        return new SetCertificatesStatusResponse();
    }

    public DeleteCertificates createDeleteCertificates() {
        return new DeleteCertificates();
    }

    public DeleteCertificatesResponse createDeleteCertificatesResponse() {
        return new DeleteCertificatesResponse();
    }

    public GetPkcs10Request createGetPkcs10Request() {
        return new GetPkcs10Request();
    }

    public GetPkcs10RequestResponse createGetPkcs10RequestResponse() {
        return new GetPkcs10RequestResponse();
    }

    public LoadCertificates createLoadCertificates() {
        return new LoadCertificates();
    }

    public LoadCertificatesResponse createLoadCertificatesResponse() {
        return new LoadCertificatesResponse();
    }

    public GetClientCertificateMode createGetClientCertificateMode() {
        return new GetClientCertificateMode();
    }

    public GetClientCertificateModeResponse createGetClientCertificateModeResponse() {
        return new GetClientCertificateModeResponse();
    }

    public SetClientCertificateMode createSetClientCertificateMode() {
        return new SetClientCertificateMode();
    }

    public SetClientCertificateModeResponse createSetClientCertificateModeResponse() {
        return new SetClientCertificateModeResponse();
    }

    public GetCACertificates createGetCACertificates() {
        return new GetCACertificates();
    }

    public GetCACertificatesResponse createGetCACertificatesResponse() {
        return new GetCACertificatesResponse();
    }

    public LoadCertificateWithPrivateKey createLoadCertificateWithPrivateKey() {
        return new LoadCertificateWithPrivateKey();
    }

    public LoadCertificateWithPrivateKeyResponse createLoadCertificateWithPrivateKeyResponse() {
        return new LoadCertificateWithPrivateKeyResponse();
    }

    public GetCertificateInformation createGetCertificateInformation() {
        return new GetCertificateInformation();
    }

    public GetCertificateInformationResponse createGetCertificateInformationResponse() {
        return new GetCertificateInformationResponse();
    }

    public LoadCACertificates createLoadCACertificates() {
        return new LoadCACertificates();
    }

    public LoadCACertificatesResponse createLoadCACertificatesResponse() {
        return new LoadCACertificatesResponse();
    }

    public CreateDot1XConfiguration createCreateDot1XConfiguration() {
        return new CreateDot1XConfiguration();
    }

    public CreateDot1XConfigurationResponse createCreateDot1XConfigurationResponse() {
        return new CreateDot1XConfigurationResponse();
    }

    public SetDot1XConfiguration createSetDot1XConfiguration() {
        return new SetDot1XConfiguration();
    }

    public SetDot1XConfigurationResponse createSetDot1XConfigurationResponse() {
        return new SetDot1XConfigurationResponse();
    }

    public GetDot1XConfiguration createGetDot1XConfiguration() {
        return new GetDot1XConfiguration();
    }

    public GetDot1XConfigurationResponse createGetDot1XConfigurationResponse() {
        return new GetDot1XConfigurationResponse();
    }

    public GetDot1XConfigurations createGetDot1XConfigurations() {
        return new GetDot1XConfigurations();
    }

    public GetDot1XConfigurationsResponse createGetDot1XConfigurationsResponse() {
        return new GetDot1XConfigurationsResponse();
    }

    public DeleteDot1XConfiguration createDeleteDot1XConfiguration() {
        return new DeleteDot1XConfiguration();
    }

    public DeleteDot1XConfigurationResponse createDeleteDot1XConfigurationResponse() {
        return new DeleteDot1XConfigurationResponse();
    }

    public GetRelayOutputs createGetRelayOutputs() {
        return new GetRelayOutputs();
    }

    public GetRelayOutputsResponse createGetRelayOutputsResponse() {
        return new GetRelayOutputsResponse();
    }

    public SetRelayOutputSettings createSetRelayOutputSettings() {
        return new SetRelayOutputSettings();
    }

    public SetRelayOutputSettingsResponse createSetRelayOutputSettingsResponse() {
        return new SetRelayOutputSettingsResponse();
    }

    public SetRelayOutputState createSetRelayOutputState() {
        return new SetRelayOutputState();
    }

    public SetRelayOutputStateResponse createSetRelayOutputStateResponse() {
        return new SetRelayOutputStateResponse();
    }

    public SendAuxiliaryCommand createSendAuxiliaryCommand() {
        return new SendAuxiliaryCommand();
    }

    public SendAuxiliaryCommandResponse createSendAuxiliaryCommandResponse() {
        return new SendAuxiliaryCommandResponse();
    }

    public GetDot11Capabilities createGetDot11Capabilities() {
        return new GetDot11Capabilities();
    }

    public GetDot11CapabilitiesResponse createGetDot11CapabilitiesResponse() {
        return new GetDot11CapabilitiesResponse();
    }

    public GetDot11Status createGetDot11Status() {
        return new GetDot11Status();
    }

    public GetDot11StatusResponse createGetDot11StatusResponse() {
        return new GetDot11StatusResponse();
    }

    public ScanAvailableDot11Networks createScanAvailableDot11Networks() {
        return new ScanAvailableDot11Networks();
    }

    public ScanAvailableDot11NetworksResponse createScanAvailableDot11NetworksResponse() {
        return new ScanAvailableDot11NetworksResponse();
    }

    public GetSystemUris createGetSystemUris() {
        return new GetSystemUris();
    }

    public GetSystemUrisResponse.Extension createGetSystemUrisResponseExtension() {
        return new GetSystemUrisResponse.Extension();
    }

    public StartFirmwareUpgrade createStartFirmwareUpgrade() {
        return new StartFirmwareUpgrade();
    }

    public StartFirmwareUpgradeResponse createStartFirmwareUpgradeResponse() {
        return new StartFirmwareUpgradeResponse();
    }

    public StartSystemRestore createStartSystemRestore() {
        return new StartSystemRestore();
    }

    public StartSystemRestoreResponse createStartSystemRestoreResponse() {
        return new StartSystemRestoreResponse();
    }

    public GetStorageConfigurations createGetStorageConfigurations() {
        return new GetStorageConfigurations();
    }

    public GetStorageConfigurationsResponse createGetStorageConfigurationsResponse() {
        return new GetStorageConfigurationsResponse();
    }

    public StorageConfiguration createStorageConfiguration() {
        return new StorageConfiguration();
    }

    public CreateStorageConfiguration createCreateStorageConfiguration() {
        return new CreateStorageConfiguration();
    }

    public CreateStorageConfigurationResponse createCreateStorageConfigurationResponse() {
        return new CreateStorageConfigurationResponse();
    }

    public GetStorageConfiguration createGetStorageConfiguration() {
        return new GetStorageConfiguration();
    }

    public GetStorageConfigurationResponse createGetStorageConfigurationResponse() {
        return new GetStorageConfigurationResponse();
    }

    public SetStorageConfiguration createSetStorageConfiguration() {
        return new SetStorageConfiguration();
    }

    public SetStorageConfigurationResponse createSetStorageConfigurationResponse() {
        return new SetStorageConfigurationResponse();
    }

    public DeleteStorageConfiguration createDeleteStorageConfiguration() {
        return new DeleteStorageConfiguration();
    }

    public DeleteStorageConfigurationResponse createDeleteStorageConfigurationResponse() {
        return new DeleteStorageConfigurationResponse();
    }

    public NetworkCapabilities createNetworkCapabilities() {
        return new NetworkCapabilities();
    }

    public SecurityCapabilities createSecurityCapabilities() {
        return new SecurityCapabilities();
    }

    public SystemCapabilities createSystemCapabilities() {
        return new SystemCapabilities();
    }

    public MiscCapabilities createMiscCapabilities() {
        return new MiscCapabilities();
    }

    public UserCredential.Extension createUserCredentialExtension() {
        return new UserCredential.Extension();
    }

    public StorageConfigurationData.Extension createStorageConfigurationDataExtension() {
        return new StorageConfigurationData.Extension();
    }

    public Service.Capabilities createServiceCapabilities() {
        return new Service.Capabilities();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/device/wsdl", name="Capabilities")
    public JAXBElement<DeviceServiceCapabilities> createCapabilities(DeviceServiceCapabilities value) {
        return new JAXBElement<DeviceServiceCapabilities>(_Capabilities_QNAME, DeviceServiceCapabilities.class, null, value);
    }
}

