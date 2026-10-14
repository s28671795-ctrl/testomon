/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.device.wsdl.DeviceServiceCapabilities;
import org.onvif.ver10.device.wsdl.GetDot11Capabilities;
import org.onvif.ver10.device.wsdl.GetDot11CapabilitiesResponse;
import org.onvif.ver10.device.wsdl.GetEndpointReference;
import org.onvif.ver10.device.wsdl.GetEndpointReferenceResponse;
import org.onvif.ver10.device.wsdl.GetSystemUrisResponse;
import org.onvif.ver10.device.wsdl.ObjectFactory;
import org.onvif.ver10.device.wsdl.Service;
import org.onvif.ver10.device.wsdl.StorageConfiguration;
import org.onvif.ver10.device.wsdl.StorageConfigurationData;
import org.onvif.ver10.schema.AttachmentData;
import org.onvif.ver10.schema.BackupFile;
import org.onvif.ver10.schema.BinaryData;
import org.onvif.ver10.schema.Capabilities;
import org.onvif.ver10.schema.CapabilityCategory;
import org.onvif.ver10.schema.Certificate;
import org.onvif.ver10.schema.CertificateInformation;
import org.onvif.ver10.schema.CertificateStatus;
import org.onvif.ver10.schema.CertificateWithPrivateKey;
import org.onvif.ver10.schema.DNSInformation;
import org.onvif.ver10.schema.DateTime;
import org.onvif.ver10.schema.DiscoveryMode;
import org.onvif.ver10.schema.Dot11AvailableNetworks;
import org.onvif.ver10.schema.Dot11Status;
import org.onvif.ver10.schema.Dot1XConfiguration;
import org.onvif.ver10.schema.DynamicDNSInformation;
import org.onvif.ver10.schema.DynamicDNSType;
import org.onvif.ver10.schema.FactoryDefaultType;
import org.onvif.ver10.schema.HostnameInformation;
import org.onvif.ver10.schema.IPAddress;
import org.onvif.ver10.schema.IPAddressFilter;
import org.onvif.ver10.schema.NTPInformation;
import org.onvif.ver10.schema.NetworkGateway;
import org.onvif.ver10.schema.NetworkHost;
import org.onvif.ver10.schema.NetworkInterface;
import org.onvif.ver10.schema.NetworkInterfaceSetConfiguration;
import org.onvif.ver10.schema.NetworkProtocol;
import org.onvif.ver10.schema.NetworkZeroConfiguration;
import org.onvif.ver10.schema.RelayLogicalState;
import org.onvif.ver10.schema.RelayOutput;
import org.onvif.ver10.schema.RelayOutputSettings;
import org.onvif.ver10.schema.RemoteUser;
import org.onvif.ver10.schema.Scope;
import org.onvif.ver10.schema.SetDateTimeType;
import org.onvif.ver10.schema.SupportInformation;
import org.onvif.ver10.schema.SystemDateTime;
import org.onvif.ver10.schema.SystemLog;
import org.onvif.ver10.schema.SystemLogType;
import org.onvif.ver10.schema.SystemLogUriList;
import org.onvif.ver10.schema.TimeZone;
import org.onvif.ver10.schema.User;

@WebService(targetNamespace="http://www.onvif.org/ver10/device/wsdl", name="Device")
@XmlSeeAlso(value={ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.w3._2004._08.xop.include.ObjectFactory.class, org.onvif.ver10.schema.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class, org.w3._2005._05.xmlmime.ObjectFactory.class})
public interface Device {
    @WebMethod(operationName="StartFirmwareUpgrade", action="http://www.onvif.org/ver10/device/wsdl/StartFirmwareUpgrade")
    @RequestWrapper(localName="StartFirmwareUpgrade", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.StartFirmwareUpgrade")
    @ResponseWrapper(localName="StartFirmwareUpgradeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.StartFirmwareUpgradeResponse")
    public void startFirmwareUpgrade(@WebParam(mode=WebParam.Mode.OUT, name="UploadUri", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var1, @WebParam(mode=WebParam.Mode.OUT, name="UploadDelay", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<Duration> var2, @WebParam(mode=WebParam.Mode.OUT, name="ExpectedDownTime", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<Duration> var3);

    @WebMethod(operationName="GetZeroConfiguration", action="http://www.onvif.org/ver10/device/wsdl/GetZeroConfiguration")
    @RequestWrapper(localName="GetZeroConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetZeroConfiguration")
    @ResponseWrapper(localName="GetZeroConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetZeroConfigurationResponse")
    @WebResult(name="ZeroConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public NetworkZeroConfiguration getZeroConfiguration();

    @WebMethod(operationName="GetClientCertificateMode", action="http://www.onvif.org/ver10/device/wsdl/GetClientCertificateMode")
    @RequestWrapper(localName="GetClientCertificateMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetClientCertificateMode")
    @ResponseWrapper(localName="GetClientCertificateModeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetClientCertificateModeResponse")
    @WebResult(name="Enabled", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public boolean getClientCertificateMode();

    @WebMethod(operationName="SetClientCertificateMode", action="http://www.onvif.org/ver10/device/wsdl/SetClientCertificateMode")
    @RequestWrapper(localName="SetClientCertificateMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetClientCertificateMode")
    @ResponseWrapper(localName="SetClientCertificateModeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetClientCertificateModeResponse")
    public void setClientCertificateMode(@WebParam(name="Enabled", targetNamespace="http://www.onvif.org/ver10/device/wsdl") boolean var1);

    @WebMethod(operationName="SetRemoteDiscoveryMode", action="http://www.onvif.org/ver10/device/wsdl/SetRemoteDiscoveryMode")
    @RequestWrapper(localName="SetRemoteDiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRemoteDiscoveryMode")
    @ResponseWrapper(localName="SetRemoteDiscoveryModeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRemoteDiscoveryModeResponse")
    public void setRemoteDiscoveryMode(@WebParam(name="RemoteDiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl") DiscoveryMode var1);

    @WebMethod(operationName="CreateUsers", action="http://www.onvif.org/ver10/device/wsdl/CreateUsers")
    @RequestWrapper(localName="CreateUsers", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateUsers")
    @ResponseWrapper(localName="CreateUsersResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateUsersResponse")
    public void createUsers(@WebParam(name="User", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<User> var1);

    @WebMethod(operationName="GetNTP", action="http://www.onvif.org/ver10/device/wsdl/GetNTP")
    @RequestWrapper(localName="GetNTP", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNTP")
    @ResponseWrapper(localName="GetNTPResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNTPResponse")
    @WebResult(name="NTPInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public NTPInformation getNTP();

    @WebMethod(operationName="LoadCertificateWithPrivateKey", action="http://www.onvif.org/ver10/device/wsdl/LoadCertificateWithPrivateKey")
    @RequestWrapper(localName="LoadCertificateWithPrivateKey", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.LoadCertificateWithPrivateKey")
    @ResponseWrapper(localName="LoadCertificateWithPrivateKeyResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.LoadCertificateWithPrivateKeyResponse")
    public void loadCertificateWithPrivateKey(@WebParam(name="CertificateWithPrivateKey", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<CertificateWithPrivateKey> var1);

    @WebMethod(operationName="SetUser", action="http://www.onvif.org/ver10/device/wsdl/SetUser")
    @RequestWrapper(localName="SetUser", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetUser")
    @ResponseWrapper(localName="SetUserResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetUserResponse")
    public void setUser(@WebParam(name="User", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<User> var1);

    @WebMethod(operationName="SetScopes", action="http://www.onvif.org/ver10/device/wsdl/SetScopes")
    @RequestWrapper(localName="SetScopes", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetScopes")
    @ResponseWrapper(localName="SetScopesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetScopesResponse")
    public void setScopes(@WebParam(name="Scopes", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var1);

    @WebMethod(operationName="GetNetworkDefaultGateway", action="http://www.onvif.org/ver10/device/wsdl/GetNetworkDefaultGateway")
    @RequestWrapper(localName="GetNetworkDefaultGateway", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNetworkDefaultGateway")
    @ResponseWrapper(localName="GetNetworkDefaultGatewayResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNetworkDefaultGatewayResponse")
    @WebResult(name="NetworkGateway", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public NetworkGateway getNetworkDefaultGateway();

    @WebMethod(operationName="GetSystemDateAndTime", action="http://www.onvif.org/ver10/device/wsdl/GetSystemDateAndTime")
    @RequestWrapper(localName="GetSystemDateAndTime", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemDateAndTime")
    @ResponseWrapper(localName="GetSystemDateAndTimeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemDateAndTimeResponse")
    @WebResult(name="SystemDateAndTime", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public SystemDateTime getSystemDateAndTime();

    @WebMethod(operationName="SetNTP", action="http://www.onvif.org/ver10/device/wsdl/SetNTP")
    @RequestWrapper(localName="SetNTP", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNTP")
    @ResponseWrapper(localName="SetNTPResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNTPResponse")
    public void setNTP(@WebParam(name="FromDHCP", targetNamespace="http://www.onvif.org/ver10/device/wsdl") boolean var1, @WebParam(name="NTPManual", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<NetworkHost> var2);

    @WebMethod(operationName="SetNetworkInterfaces", action="http://www.onvif.org/ver10/device/wsdl/SetNetworkInterfaces")
    @RequestWrapper(localName="SetNetworkInterfaces", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNetworkInterfaces")
    @ResponseWrapper(localName="SetNetworkInterfacesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNetworkInterfacesResponse")
    @WebResult(name="RebootNeeded", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public boolean setNetworkInterfaces(@WebParam(name="InterfaceToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1, @WebParam(name="NetworkInterface", targetNamespace="http://www.onvif.org/ver10/device/wsdl") NetworkInterfaceSetConfiguration var2);

    @WebMethod(operationName="SetDPAddresses", action="http://www.onvif.org/ver10/device/wsdl/SetDPAddresses")
    @RequestWrapper(localName="SetDPAddresses", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDPAddresses")
    @ResponseWrapper(localName="SetDPAddressesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDPAddressesResponse")
    public void setDPAddresses(@WebParam(name="DPAddress", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<NetworkHost> var1);

    @WebMethod(operationName="GetDiscoveryMode", action="http://www.onvif.org/ver10/device/wsdl/GetDiscoveryMode")
    @RequestWrapper(localName="GetDiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDiscoveryMode")
    @ResponseWrapper(localName="GetDiscoveryModeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDiscoveryModeResponse")
    @WebResult(name="DiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public DiscoveryMode getDiscoveryMode();

    @WebMethod(operationName="GetSystemSupportInformation", action="http://www.onvif.org/ver10/device/wsdl/GetSystemSupportInformation")
    @RequestWrapper(localName="GetSystemSupportInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemSupportInformation")
    @ResponseWrapper(localName="GetSystemSupportInformationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemSupportInformationResponse")
    @WebResult(name="SupportInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public SupportInformation getSystemSupportInformation();

    @WebMethod(operationName="RemoveIPAddressFilter", action="http://www.onvif.org/ver10/device/wsdl/RemoveIPAddressFilter")
    @RequestWrapper(localName="RemoveIPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.RemoveIPAddressFilter")
    @ResponseWrapper(localName="RemoveIPAddressFilterResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.RemoveIPAddressFilterResponse")
    public void removeIPAddressFilter(@WebParam(name="IPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl") IPAddressFilter var1);

    @WebMethod(operationName="SetSystemDateAndTime", action="http://www.onvif.org/ver10/device/wsdl/SetSystemDateAndTime")
    @RequestWrapper(localName="SetSystemDateAndTime", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetSystemDateAndTime")
    @ResponseWrapper(localName="SetSystemDateAndTimeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetSystemDateAndTimeResponse")
    public void setSystemDateAndTime(@WebParam(name="DateTimeType", targetNamespace="http://www.onvif.org/ver10/device/wsdl") SetDateTimeType var1, @WebParam(name="DaylightSavings", targetNamespace="http://www.onvif.org/ver10/device/wsdl") boolean var2, @WebParam(name="TimeZone", targetNamespace="http://www.onvif.org/ver10/device/wsdl") TimeZone var3, @WebParam(name="UTCDateTime", targetNamespace="http://www.onvif.org/ver10/device/wsdl") DateTime var4);

    @WebMethod(operationName="CreateDot1XConfiguration", action="http://www.onvif.org/ver10/device/wsdl/CreateDot1XConfiguration")
    @RequestWrapper(localName="CreateDot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateDot1XConfiguration")
    @ResponseWrapper(localName="CreateDot1XConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateDot1XConfigurationResponse")
    public void createDot1XConfiguration(@WebParam(name="Dot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Dot1XConfiguration var1);

    @WebMethod(operationName="GetAccessPolicy", action="http://www.onvif.org/ver10/device/wsdl/GetAccessPolicy")
    @RequestWrapper(localName="GetAccessPolicy", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetAccessPolicy")
    @ResponseWrapper(localName="GetAccessPolicyResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetAccessPolicyResponse")
    @WebResult(name="PolicyFile", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public BinaryData getAccessPolicy();

    @WebMethod(operationName="GetSystemUris", action="http://www.onvif.org/ver10/device/wsdl/GetSystemUris")
    @RequestWrapper(localName="GetSystemUris", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemUris")
    @ResponseWrapper(localName="GetSystemUrisResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemUrisResponse")
    public void getSystemUris(@WebParam(mode=WebParam.Mode.OUT, name="SystemLogUris", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<SystemLogUriList> var1, @WebParam(mode=WebParam.Mode.OUT, name="SupportInfoUri", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var2, @WebParam(mode=WebParam.Mode.OUT, name="SystemBackupUri", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="Extension", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<GetSystemUrisResponse.Extension> var4);

    @WebMethod(operationName="RemoveScopes", action="http://www.onvif.org/ver10/device/wsdl/RemoveScopes")
    @RequestWrapper(localName="RemoveScopes", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.RemoveScopes")
    @ResponseWrapper(localName="RemoveScopesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.RemoveScopesResponse")
    public void removeScopes(@WebParam(mode=WebParam.Mode.INOUT, name="ScopeItem", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<List<String>> var1);

    @WebMethod(operationName="LoadCertificates", action="http://www.onvif.org/ver10/device/wsdl/LoadCertificates")
    @RequestWrapper(localName="LoadCertificates", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.LoadCertificates")
    @ResponseWrapper(localName="LoadCertificatesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.LoadCertificatesResponse")
    public void loadCertificates(@WebParam(name="NVTCertificate", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<Certificate> var1);

    @WebMethod(operationName="GetDynamicDNS", action="http://www.onvif.org/ver10/device/wsdl/GetDynamicDNS")
    @RequestWrapper(localName="GetDynamicDNS", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDynamicDNS")
    @ResponseWrapper(localName="GetDynamicDNSResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDynamicDNSResponse")
    @WebResult(name="DynamicDNSInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public DynamicDNSInformation getDynamicDNS();

    @WebMethod(operationName="GetCertificates", action="http://www.onvif.org/ver10/device/wsdl/GetCertificates")
    @RequestWrapper(localName="GetCertificates", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCertificates")
    @ResponseWrapper(localName="GetCertificatesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCertificatesResponse")
    @WebResult(name="NvtCertificate", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<Certificate> getCertificates();

    @WebMethod(operationName="GetDeviceInformation", action="http://www.onvif.org/ver10/device/wsdl/GetDeviceInformation")
    @RequestWrapper(localName="GetDeviceInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDeviceInformation")
    @ResponseWrapper(localName="GetDeviceInformationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDeviceInformationResponse")
    public void getDeviceInformation(@WebParam(mode=WebParam.Mode.OUT, name="Manufacturer", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var1, @WebParam(mode=WebParam.Mode.OUT, name="Model", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var2, @WebParam(mode=WebParam.Mode.OUT, name="FirmwareVersion", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="SerialNumber", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var4, @WebParam(mode=WebParam.Mode.OUT, name="HardwareId", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var5);

    @WebMethod(operationName="SetDiscoveryMode", action="http://www.onvif.org/ver10/device/wsdl/SetDiscoveryMode")
    @RequestWrapper(localName="SetDiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDiscoveryMode")
    @ResponseWrapper(localName="SetDiscoveryModeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDiscoveryModeResponse")
    public void setDiscoveryMode(@WebParam(name="DiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl") DiscoveryMode var1);

    @WebMethod(operationName="GetScopes", action="http://www.onvif.org/ver10/device/wsdl/GetScopes")
    @RequestWrapper(localName="GetScopes", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetScopes")
    @ResponseWrapper(localName="GetScopesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetScopesResponse")
    @WebResult(name="Scopes", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<Scope> getScopes();

    @WebMethod(operationName="CreateCertificate", action="http://www.onvif.org/ver10/device/wsdl/CreateCertificate")
    @RequestWrapper(localName="CreateCertificate", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateCertificate")
    @ResponseWrapper(localName="CreateCertificateResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateCertificateResponse")
    @WebResult(name="NvtCertificate", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public Certificate createCertificate(@WebParam(name="CertificateID", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1, @WebParam(name="Subject", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var2, @WebParam(name="ValidNotBefore", targetNamespace="http://www.onvif.org/ver10/device/wsdl") XMLGregorianCalendar var3, @WebParam(name="ValidNotAfter", targetNamespace="http://www.onvif.org/ver10/device/wsdl") XMLGregorianCalendar var4);

    @WebMethod(operationName="SetRemoteUser", action="http://www.onvif.org/ver10/device/wsdl/SetRemoteUser")
    @RequestWrapper(localName="SetRemoteUser", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRemoteUser")
    @ResponseWrapper(localName="SetRemoteUserResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRemoteUserResponse")
    public void setRemoteUser(@WebParam(name="RemoteUser", targetNamespace="http://www.onvif.org/ver10/device/wsdl") RemoteUser var1);

    @WebMethod(operationName="GetNetworkInterfaces", action="http://www.onvif.org/ver10/device/wsdl/GetNetworkInterfaces")
    @RequestWrapper(localName="GetNetworkInterfaces", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNetworkInterfaces")
    @ResponseWrapper(localName="GetNetworkInterfacesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNetworkInterfacesResponse")
    @WebResult(name="NetworkInterfaces", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<NetworkInterface> getNetworkInterfaces();

    @WebMethod(operationName="SetNetworkDefaultGateway", action="http://www.onvif.org/ver10/device/wsdl/SetNetworkDefaultGateway")
    @RequestWrapper(localName="SetNetworkDefaultGateway", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNetworkDefaultGateway")
    @ResponseWrapper(localName="SetNetworkDefaultGatewayResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNetworkDefaultGatewayResponse")
    public void setNetworkDefaultGateway(@WebParam(name="IPv4Address", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var1, @WebParam(name="IPv6Address", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var2);

    @WebMethod(operationName="SetStorageConfiguration", action="http://www.onvif.org/ver10/device/wsdl/SetStorageConfiguration")
    @RequestWrapper(localName="SetStorageConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetStorageConfiguration")
    @ResponseWrapper(localName="SetStorageConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetStorageConfigurationResponse")
    public void setStorageConfiguration(@WebParam(name="StorageConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl") StorageConfiguration var1);

    @WebMethod(operationName="SetHostnameFromDHCP", action="http://www.onvif.org/ver10/device/wsdl/SetHostnameFromDHCP")
    @RequestWrapper(localName="SetHostnameFromDHCP", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetHostnameFromDHCP")
    @ResponseWrapper(localName="SetHostnameFromDHCPResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetHostnameFromDHCPResponse")
    @WebResult(name="RebootNeeded", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public boolean setHostnameFromDHCP(@WebParam(name="FromDHCP", targetNamespace="http://www.onvif.org/ver10/device/wsdl") boolean var1);

    @WebMethod(operationName="SetSystemFactoryDefault", action="http://www.onvif.org/ver10/device/wsdl/SetSystemFactoryDefault")
    @RequestWrapper(localName="SetSystemFactoryDefault", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetSystemFactoryDefault")
    @ResponseWrapper(localName="SetSystemFactoryDefaultResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetSystemFactoryDefaultResponse")
    public void setSystemFactoryDefault(@WebParam(name="FactoryDefault", targetNamespace="http://www.onvif.org/ver10/device/wsdl") FactoryDefaultType var1);

    @WebMethod(operationName="DeleteUsers", action="http://www.onvif.org/ver10/device/wsdl/DeleteUsers")
    @RequestWrapper(localName="DeleteUsers", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteUsers")
    @ResponseWrapper(localName="DeleteUsersResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteUsersResponse")
    public void deleteUsers(@WebParam(name="Username", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var1);

    @WebMethod(operationName="UpgradeSystemFirmware", action="http://www.onvif.org/ver10/device/wsdl/UpgradeSystemFirmware")
    @RequestWrapper(localName="UpgradeSystemFirmware", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.UpgradeSystemFirmware")
    @ResponseWrapper(localName="UpgradeSystemFirmwareResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.UpgradeSystemFirmwareResponse")
    @WebResult(name="Message", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public String upgradeSystemFirmware(@WebParam(name="Firmware", targetNamespace="http://www.onvif.org/ver10/device/wsdl") AttachmentData var1);

    @WebMethod(operationName="AddScopes", action="http://www.onvif.org/ver10/device/wsdl/AddScopes")
    @RequestWrapper(localName="AddScopes", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.AddScopes")
    @ResponseWrapper(localName="AddScopesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.AddScopesResponse")
    public void addScopes(@WebParam(name="ScopeItem", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var1);

    @WebMethod(operationName="SetRelayOutputSettings", action="http://www.onvif.org/ver10/device/wsdl/SetRelayOutputSettings")
    @RequestWrapper(localName="SetRelayOutputSettings", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRelayOutputSettings")
    @ResponseWrapper(localName="SetRelayOutputSettingsResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRelayOutputSettingsResponse")
    public void setRelayOutputSettings(@WebParam(name="RelayOutputToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1, @WebParam(name="Properties", targetNamespace="http://www.onvif.org/ver10/device/wsdl") RelayOutputSettings var2);

    @WebMethod(operationName="GetDot1XConfigurations", action="http://www.onvif.org/ver10/device/wsdl/GetDot1XConfigurations")
    @RequestWrapper(localName="GetDot1XConfigurations", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDot1XConfigurations")
    @ResponseWrapper(localName="GetDot1XConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDot1XConfigurationsResponse")
    @WebResult(name="Dot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<Dot1XConfiguration> getDot1XConfigurations();

    @WebMethod(operationName="StartSystemRestore", action="http://www.onvif.org/ver10/device/wsdl/StartSystemRestore")
    @RequestWrapper(localName="StartSystemRestore", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.StartSystemRestore")
    @ResponseWrapper(localName="StartSystemRestoreResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.StartSystemRestoreResponse")
    public void startSystemRestore(@WebParam(mode=WebParam.Mode.OUT, name="UploadUri", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<String> var1, @WebParam(mode=WebParam.Mode.OUT, name="ExpectedDownTime", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Holder<Duration> var2);

    @WebMethod(operationName="SetHostname", action="http://www.onvif.org/ver10/device/wsdl/SetHostname")
    @RequestWrapper(localName="SetHostname", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetHostname")
    @ResponseWrapper(localName="SetHostnameResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetHostnameResponse")
    public void setHostname(@WebParam(name="Name", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="GetIPAddressFilter", action="http://www.onvif.org/ver10/device/wsdl/GetIPAddressFilter")
    @RequestWrapper(localName="GetIPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetIPAddressFilter")
    @ResponseWrapper(localName="GetIPAddressFilterResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetIPAddressFilterResponse")
    @WebResult(name="IPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public IPAddressFilter getIPAddressFilter();

    @WebMethod(operationName="GetRemoteUser", action="http://www.onvif.org/ver10/device/wsdl/GetRemoteUser")
    @RequestWrapper(localName="GetRemoteUser", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRemoteUser")
    @ResponseWrapper(localName="GetRemoteUserResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRemoteUserResponse")
    @WebResult(name="RemoteUser", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public RemoteUser getRemoteUser();

    @WebMethod(operationName="SendAuxiliaryCommand", action="http://www.onvif.org/ver10/device/wsdl/SendAuxiliaryCommand")
    @RequestWrapper(localName="SendAuxiliaryCommand", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SendAuxiliaryCommand")
    @ResponseWrapper(localName="SendAuxiliaryCommandResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SendAuxiliaryCommandResponse")
    @WebResult(name="AuxiliaryCommandResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public String sendAuxiliaryCommand(@WebParam(name="AuxiliaryCommand", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/device/wsdl/GetServiceCapabilities")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public DeviceServiceCapabilities getServiceCapabilities();

    @WebMethod(operationName="AddIPAddressFilter", action="http://www.onvif.org/ver10/device/wsdl/AddIPAddressFilter")
    @RequestWrapper(localName="AddIPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.AddIPAddressFilter")
    @ResponseWrapper(localName="AddIPAddressFilterResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.AddIPAddressFilterResponse")
    public void addIPAddressFilter(@WebParam(name="IPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl") IPAddressFilter var1);

    @WebMethod(operationName="GetCertificatesStatus", action="http://www.onvif.org/ver10/device/wsdl/GetCertificatesStatus")
    @RequestWrapper(localName="GetCertificatesStatus", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCertificatesStatus")
    @ResponseWrapper(localName="GetCertificatesStatusResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCertificatesStatusResponse")
    @WebResult(name="CertificateStatus", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<CertificateStatus> getCertificatesStatus();

    @WebMethod(operationName="GetPkcs10Request", action="http://www.onvif.org/ver10/device/wsdl/GetPkcs10Request")
    @RequestWrapper(localName="GetPkcs10Request", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetPkcs10Request")
    @ResponseWrapper(localName="GetPkcs10RequestResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetPkcs10RequestResponse")
    @WebResult(name="Pkcs10Request", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public BinaryData getPkcs10Request(@WebParam(name="CertificateID", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1, @WebParam(name="Subject", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var2, @WebParam(name="Attributes", targetNamespace="http://www.onvif.org/ver10/device/wsdl") BinaryData var3);

    @WebMethod(operationName="GetDNS", action="http://www.onvif.org/ver10/device/wsdl/GetDNS")
    @RequestWrapper(localName="GetDNS", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDNS")
    @ResponseWrapper(localName="GetDNSResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDNSResponse")
    @WebResult(name="DNSInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public DNSInformation getDNS();

    @WebMethod(operationName="GetStorageConfigurations", action="http://www.onvif.org/ver10/device/wsdl/GetStorageConfigurations")
    @RequestWrapper(localName="GetStorageConfigurations", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetStorageConfigurations")
    @ResponseWrapper(localName="GetStorageConfigurationsResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetStorageConfigurationsResponse")
    @WebResult(name="StorageConfigurations", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<StorageConfiguration> getStorageConfigurations();

    @WebMethod(operationName="GetSystemLog", action="http://www.onvif.org/ver10/device/wsdl/GetSystemLog")
    @RequestWrapper(localName="GetSystemLog", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemLog")
    @ResponseWrapper(localName="GetSystemLogResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemLogResponse")
    @WebResult(name="SystemLog", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public SystemLog getSystemLog(@WebParam(name="LogType", targetNamespace="http://www.onvif.org/ver10/device/wsdl") SystemLogType var1);

    @WebMethod(operationName="GetDPAddresses", action="http://www.onvif.org/ver10/device/wsdl/GetDPAddresses")
    @RequestWrapper(localName="GetDPAddresses", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDPAddresses")
    @ResponseWrapper(localName="GetDPAddressesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDPAddressesResponse")
    @WebResult(name="DPAddress", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<NetworkHost> getDPAddresses();

    @WebMethod(operationName="SetRelayOutputState", action="http://www.onvif.org/ver10/device/wsdl/SetRelayOutputState")
    @RequestWrapper(localName="SetRelayOutputState", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRelayOutputState")
    @ResponseWrapper(localName="SetRelayOutputStateResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetRelayOutputStateResponse")
    public void setRelayOutputState(@WebParam(name="RelayOutputToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1, @WebParam(name="LogicalState", targetNamespace="http://www.onvif.org/ver10/device/wsdl") RelayLogicalState var2);

    @WebMethod(operationName="RestoreSystem", action="http://www.onvif.org/ver10/device/wsdl/RestoreSystem")
    @RequestWrapper(localName="RestoreSystem", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.RestoreSystem")
    @ResponseWrapper(localName="RestoreSystemResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.RestoreSystemResponse")
    public void restoreSystem(@WebParam(name="BackupFiles", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<BackupFile> var1);

    @WebMethod(operationName="GetRemoteDiscoveryMode", action="http://www.onvif.org/ver10/device/wsdl/GetRemoteDiscoveryMode")
    @RequestWrapper(localName="GetRemoteDiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRemoteDiscoveryMode")
    @ResponseWrapper(localName="GetRemoteDiscoveryModeResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRemoteDiscoveryModeResponse")
    @WebResult(name="RemoteDiscoveryMode", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public DiscoveryMode getRemoteDiscoveryMode();

    @WebMethod(operationName="SetDNS", action="http://www.onvif.org/ver10/device/wsdl/SetDNS")
    @RequestWrapper(localName="SetDNS", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDNS")
    @ResponseWrapper(localName="SetDNSResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDNSResponse")
    public void setDNS(@WebParam(name="FromDHCP", targetNamespace="http://www.onvif.org/ver10/device/wsdl") boolean var1, @WebParam(name="SearchDomain", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var2, @WebParam(name="DNSManual", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<IPAddress> var3);

    @WebMethod(operationName="GetCertificateInformation", action="http://www.onvif.org/ver10/device/wsdl/GetCertificateInformation")
    @RequestWrapper(localName="GetCertificateInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCertificateInformation")
    @ResponseWrapper(localName="GetCertificateInformationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCertificateInformationResponse")
    @WebResult(name="CertificateInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public CertificateInformation getCertificateInformation(@WebParam(name="CertificateID", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="SystemReboot", action="http://www.onvif.org/ver10/device/wsdl/SystemReboot")
    @RequestWrapper(localName="SystemReboot", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SystemReboot")
    @ResponseWrapper(localName="SystemRebootResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SystemRebootResponse")
    @WebResult(name="Message", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public String systemReboot();

    @WebMethod(operationName="GetHostname", action="http://www.onvif.org/ver10/device/wsdl/GetHostname")
    @RequestWrapper(localName="GetHostname", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetHostname")
    @ResponseWrapper(localName="GetHostnameResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetHostnameResponse")
    @WebResult(name="HostnameInformation", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public HostnameInformation getHostname();

    @WebMethod(operationName="GetUsers", action="http://www.onvif.org/ver10/device/wsdl/GetUsers")
    @RequestWrapper(localName="GetUsers", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetUsers")
    @ResponseWrapper(localName="GetUsersResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetUsersResponse")
    @WebResult(name="User", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<User> getUsers();

    @WebMethod(operationName="DeleteDot1XConfiguration", action="http://www.onvif.org/ver10/device/wsdl/DeleteDot1XConfiguration")
    @RequestWrapper(localName="DeleteDot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteDot1XConfiguration")
    @ResponseWrapper(localName="DeleteDot1XConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteDot1XConfigurationResponse")
    public void deleteDot1XConfiguration(@WebParam(name="Dot1XConfigurationToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var1);

    @WebMethod(operationName="GetRelayOutputs", action="http://www.onvif.org/ver10/device/wsdl/GetRelayOutputs")
    @RequestWrapper(localName="GetRelayOutputs", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRelayOutputs")
    @ResponseWrapper(localName="GetRelayOutputsResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetRelayOutputsResponse")
    @WebResult(name="RelayOutputs", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<RelayOutput> getRelayOutputs();

    @WebMethod(operationName="GetCapabilities", action="http://www.onvif.org/ver10/device/wsdl/GetCapabilities")
    @RequestWrapper(localName="GetCapabilities", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCapabilities")
    @ResponseWrapper(localName="GetCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public Capabilities getCapabilities(@WebParam(name="Category", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<CapabilityCategory> var1);

    @WebMethod(operationName="SetNetworkProtocols", action="http://www.onvif.org/ver10/device/wsdl/SetNetworkProtocols")
    @RequestWrapper(localName="SetNetworkProtocols", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNetworkProtocols")
    @ResponseWrapper(localName="SetNetworkProtocolsResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetNetworkProtocolsResponse")
    public void setNetworkProtocols(@WebParam(name="NetworkProtocols", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<NetworkProtocol> var1);

    @WebMethod(operationName="GetStorageConfiguration", action="http://www.onvif.org/ver10/device/wsdl/GetStorageConfiguration")
    @RequestWrapper(localName="GetStorageConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetStorageConfiguration")
    @ResponseWrapper(localName="GetStorageConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetStorageConfigurationResponse")
    @WebResult(name="StorageConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public StorageConfiguration getStorageConfiguration(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="LoadCACertificates", action="http://www.onvif.org/ver10/device/wsdl/LoadCACertificates")
    @RequestWrapper(localName="LoadCACertificates", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.LoadCACertificates")
    @ResponseWrapper(localName="LoadCACertificatesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.LoadCACertificatesResponse")
    public void loadCACertificates(@WebParam(name="CACertificate", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<Certificate> var1);

    @WebMethod(operationName="GetDot1XConfiguration", action="http://www.onvif.org/ver10/device/wsdl/GetDot1XConfiguration")
    @RequestWrapper(localName="GetDot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDot1XConfiguration")
    @ResponseWrapper(localName="GetDot1XConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDot1XConfigurationResponse")
    @WebResult(name="Dot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public Dot1XConfiguration getDot1XConfiguration(@WebParam(name="Dot1XConfigurationToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="SetDot1XConfiguration", action="http://www.onvif.org/ver10/device/wsdl/SetDot1XConfiguration")
    @RequestWrapper(localName="SetDot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDot1XConfiguration")
    @ResponseWrapper(localName="SetDot1XConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDot1XConfigurationResponse")
    public void setDot1XConfiguration(@WebParam(name="Dot1XConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Dot1XConfiguration var1);

    @WebMethod(operationName="SetIPAddressFilter", action="http://www.onvif.org/ver10/device/wsdl/SetIPAddressFilter")
    @RequestWrapper(localName="SetIPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetIPAddressFilter")
    @ResponseWrapper(localName="SetIPAddressFilterResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetIPAddressFilterResponse")
    public void setIPAddressFilter(@WebParam(name="IPAddressFilter", targetNamespace="http://www.onvif.org/ver10/device/wsdl") IPAddressFilter var1);

    @WebMethod(operationName="GetNetworkProtocols", action="http://www.onvif.org/ver10/device/wsdl/GetNetworkProtocols")
    @RequestWrapper(localName="GetNetworkProtocols", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNetworkProtocols")
    @ResponseWrapper(localName="GetNetworkProtocolsResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetNetworkProtocolsResponse")
    @WebResult(name="NetworkProtocols", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<NetworkProtocol> getNetworkProtocols();

    @WebMethod(operationName="CreateStorageConfiguration", action="http://www.onvif.org/ver10/device/wsdl/CreateStorageConfiguration")
    @RequestWrapper(localName="CreateStorageConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateStorageConfiguration")
    @ResponseWrapper(localName="CreateStorageConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.CreateStorageConfigurationResponse")
    @WebResult(name="Token", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public String createStorageConfiguration(@WebParam(name="StorageConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl") StorageConfigurationData var1);

    @WebMethod(operationName="ScanAvailableDot11Networks", action="http://www.onvif.org/ver10/device/wsdl/ScanAvailableDot11Networks")
    @RequestWrapper(localName="ScanAvailableDot11Networks", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.ScanAvailableDot11Networks")
    @ResponseWrapper(localName="ScanAvailableDot11NetworksResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.ScanAvailableDot11NetworksResponse")
    @WebResult(name="Networks", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<Dot11AvailableNetworks> scanAvailableDot11Networks(@WebParam(name="InterfaceToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="SetAccessPolicy", action="http://www.onvif.org/ver10/device/wsdl/SetAccessPolicy")
    @RequestWrapper(localName="SetAccessPolicy", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetAccessPolicy")
    @ResponseWrapper(localName="SetAccessPolicyResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetAccessPolicyResponse")
    public void setAccessPolicy(@WebParam(name="PolicyFile", targetNamespace="http://www.onvif.org/ver10/device/wsdl") BinaryData var1);

    @WebMethod(operationName="DeleteCertificates", action="http://www.onvif.org/ver10/device/wsdl/DeleteCertificates")
    @RequestWrapper(localName="DeleteCertificates", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteCertificates")
    @ResponseWrapper(localName="DeleteCertificatesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteCertificatesResponse")
    public void deleteCertificates(@WebParam(name="CertificateID", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<String> var1);

    @WebMethod(operationName="GetWsdlUrl", action="http://www.onvif.org/ver10/device/wsdl/GetWsdlUrl")
    @RequestWrapper(localName="GetWsdlUrl", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetWsdlUrl")
    @ResponseWrapper(localName="GetWsdlUrlResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetWsdlUrlResponse")
    @WebResult(name="WsdlUrl", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public String getWsdlUrl();

    @WebMethod(operationName="DeleteStorageConfiguration", action="http://www.onvif.org/ver10/device/wsdl/DeleteStorageConfiguration")
    @RequestWrapper(localName="DeleteStorageConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteStorageConfiguration")
    @ResponseWrapper(localName="DeleteStorageConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.DeleteStorageConfigurationResponse")
    public void deleteStorageConfiguration(@WebParam(name="Token", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="GetDot11Capabilities", action="http://www.onvif.org/ver10/device/wsdl/GetDot11Capabilities")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetDot11CapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", partName="parameters")
    public GetDot11CapabilitiesResponse getDot11Capabilities(@WebParam(partName="parameters", name="GetDot11Capabilities", targetNamespace="http://www.onvif.org/ver10/device/wsdl") GetDot11Capabilities var1);

    @WebMethod(operationName="SetZeroConfiguration", action="http://www.onvif.org/ver10/device/wsdl/SetZeroConfiguration")
    @RequestWrapper(localName="SetZeroConfiguration", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetZeroConfiguration")
    @ResponseWrapper(localName="SetZeroConfigurationResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetZeroConfigurationResponse")
    public void setZeroConfiguration(@WebParam(name="InterfaceToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1, @WebParam(name="Enabled", targetNamespace="http://www.onvif.org/ver10/device/wsdl") boolean var2);

    @WebMethod(operationName="GetEndpointReference", action="http://www.onvif.org/ver10/device/wsdl/GetEndpointReference")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetEndpointReferenceResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", partName="parameters")
    public GetEndpointReferenceResponse getEndpointReference(@WebParam(partName="parameters", name="GetEndpointReference", targetNamespace="http://www.onvif.org/ver10/device/wsdl") GetEndpointReference var1);

    @WebMethod(operationName="SetCertificatesStatus", action="http://www.onvif.org/ver10/device/wsdl/SetCertificatesStatus")
    @RequestWrapper(localName="SetCertificatesStatus", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetCertificatesStatus")
    @ResponseWrapper(localName="SetCertificatesStatusResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetCertificatesStatusResponse")
    public void setCertificatesStatus(@WebParam(name="CertificateStatus", targetNamespace="http://www.onvif.org/ver10/device/wsdl") List<CertificateStatus> var1);

    @WebMethod(operationName="GetDot11Status", action="http://www.onvif.org/ver10/device/wsdl/GetDot11Status")
    @RequestWrapper(localName="GetDot11Status", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDot11Status")
    @ResponseWrapper(localName="GetDot11StatusResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetDot11StatusResponse")
    @WebResult(name="Status", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public Dot11Status getDot11Status(@WebParam(name="InterfaceToken", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var1);

    @WebMethod(operationName="GetServices", action="http://www.onvif.org/ver10/device/wsdl/GetServices")
    @RequestWrapper(localName="GetServices", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetServices")
    @ResponseWrapper(localName="GetServicesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetServicesResponse")
    @WebResult(name="Service", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<Service> getServices(@WebParam(name="IncludeCapability", targetNamespace="http://www.onvif.org/ver10/device/wsdl") boolean var1);

    @WebMethod(operationName="SetDynamicDNS", action="http://www.onvif.org/ver10/device/wsdl/SetDynamicDNS")
    @RequestWrapper(localName="SetDynamicDNS", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDynamicDNS")
    @ResponseWrapper(localName="SetDynamicDNSResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.SetDynamicDNSResponse")
    public void setDynamicDNS(@WebParam(name="Type", targetNamespace="http://www.onvif.org/ver10/device/wsdl") DynamicDNSType var1, @WebParam(name="Name", targetNamespace="http://www.onvif.org/ver10/device/wsdl") String var2, @WebParam(name="TTL", targetNamespace="http://www.onvif.org/ver10/device/wsdl") Duration var3);

    @WebMethod(operationName="GetSystemBackup", action="http://www.onvif.org/ver10/device/wsdl/GetSystemBackup")
    @RequestWrapper(localName="GetSystemBackup", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemBackup")
    @ResponseWrapper(localName="GetSystemBackupResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetSystemBackupResponse")
    @WebResult(name="BackupFiles", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<BackupFile> getSystemBackup();

    @WebMethod(operationName="GetCACertificates", action="http://www.onvif.org/ver10/device/wsdl/GetCACertificates")
    @RequestWrapper(localName="GetCACertificates", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCACertificates")
    @ResponseWrapper(localName="GetCACertificatesResponse", targetNamespace="http://www.onvif.org/ver10/device/wsdl", className="org.onvif.ver10.device.wsdl.GetCACertificatesResponse")
    @WebResult(name="CACertificate", targetNamespace="http://www.onvif.org/ver10/device/wsdl")
    public List<Certificate> getCACertificates();
}

