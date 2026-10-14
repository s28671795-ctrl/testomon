/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap.devices;

import de.onvif.soap.OnvifDevice;
import de.onvif.soap.SOAP;
import java.net.ConnectException;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.soap.SOAPException;
import org.onvif.ver10.device.wsdl.GetCapabilities;
import org.onvif.ver10.device.wsdl.GetCapabilitiesResponse;
import org.onvif.ver10.device.wsdl.GetDeviceInformation;
import org.onvif.ver10.device.wsdl.GetDeviceInformationResponse;
import org.onvif.ver10.device.wsdl.GetHostname;
import org.onvif.ver10.device.wsdl.GetHostnameResponse;
import org.onvif.ver10.device.wsdl.GetScopes;
import org.onvif.ver10.device.wsdl.GetScopesResponse;
import org.onvif.ver10.device.wsdl.GetServices;
import org.onvif.ver10.device.wsdl.GetServicesResponse;
import org.onvif.ver10.device.wsdl.GetSystemDateAndTime;
import org.onvif.ver10.device.wsdl.GetSystemDateAndTimeResponse;
import org.onvif.ver10.device.wsdl.GetUsers;
import org.onvif.ver10.device.wsdl.GetUsersResponse;
import org.onvif.ver10.device.wsdl.Service;
import org.onvif.ver10.device.wsdl.SetHostname;
import org.onvif.ver10.device.wsdl.SetHostnameResponse;
import org.onvif.ver10.device.wsdl.SystemReboot;
import org.onvif.ver10.device.wsdl.SystemRebootResponse;
import org.onvif.ver10.media.wsdl.CreateProfile;
import org.onvif.ver10.media.wsdl.CreateProfileResponse;
import org.onvif.ver10.media.wsdl.GetProfile;
import org.onvif.ver10.media.wsdl.GetProfileResponse;
import org.onvif.ver10.media.wsdl.GetProfiles;
import org.onvif.ver10.media.wsdl.GetProfilesResponse;
import org.onvif.ver10.schema.Capabilities;
import org.onvif.ver10.schema.Date;
import org.onvif.ver10.schema.Profile;
import org.onvif.ver10.schema.Scope;
import org.onvif.ver10.schema.Time;
import org.onvif.ver10.schema.User;

public class InitialDevices {
    private final SOAP soap;

    public InitialDevices(OnvifDevice onvifDevice) {
        this.soap = onvifDevice.getSoap();
    }

    public java.util.Date getDate() {
        GetSystemDateAndTimeResponse response = this.soap.createDeviceRequest(new GetSystemDateAndTime(), new GetSystemDateAndTimeResponse(), false);
        if (response != null) {
            Date date = response.getSystemDateAndTime().getUTCDateTime().getDate();
            Time time = response.getSystemDateAndTime().getUTCDateTime().getTime();
            GregorianCalendar cal = new GregorianCalendar(date.getYear(), date.getMonth() - 1, date.getDay(), time.getHour(), time.getMinute(), time.getSecond());
            return cal.getTime();
        }
        return null;
    }

    public GetDeviceInformationResponse getDeviceInformation() {
        return this.soap.createDeviceRequest(new GetDeviceInformation(), new GetDeviceInformationResponse(), true);
    }

    public String getHostname() {
        GetHostnameResponse response = this.soap.createDeviceRequest(new GetHostname(), new GetHostnameResponse(), true);
        return response != null ? response.getHostnameInformation().getName() : null;
    }

    public boolean setHostname(String hostname) {
        SetHostname setHostname = new SetHostname();
        setHostname.setName(hostname);
        return this.soap.createDeviceRequest(setHostname, new SetHostnameResponse(), true) != null;
    }

    public List<User> getUsers() {
        GetUsersResponse response = this.soap.createDeviceRequest(new GetUsers(), new GetUsersResponse(), true);
        return response != null ? response.getUser() : null;
    }

    public Capabilities getCapabilities() throws ConnectException, SOAPException {
        GetCapabilitiesResponse response = this.soap.createDeviceRequest(new GetCapabilities(), new GetCapabilitiesResponse(), false);
        return response != null ? response.getCapabilities() : null;
    }

    public List<Profile> getProfiles() {
        GetProfilesResponse response = this.soap.createDeviceRequest(new GetProfiles(), new GetProfilesResponse(), true);
        return response != null ? response.getProfiles() : null;
    }

    public Profile getProfile(String profileToken) {
        GetProfile request = new GetProfile();
        request.setProfileToken(profileToken);
        GetProfileResponse response = this.soap.createDeviceRequest(request, new GetProfileResponse(), true);
        return response != null ? response.getProfile() : null;
    }

    public Profile createProfile(String name) {
        CreateProfile request = new CreateProfile();
        request.setName(name);
        CreateProfileResponse response = this.soap.createDeviceRequest(request, new CreateProfileResponse(), true);
        return response != null ? response.getProfile() : null;
    }

    public List<Service> getServices(boolean includeCapability) {
        GetServices request = new GetServices();
        request.setIncludeCapability(includeCapability);
        GetServicesResponse response = this.soap.createDeviceRequest(request, new GetServicesResponse(), true);
        return response != null ? response.getService() : null;
    }

    public List<Scope> getScopes() {
        GetScopesResponse response = this.soap.createDeviceRequest(new GetScopes(), new GetScopesResponse(), true);
        return response != null ? response.getScopes() : null;
    }

    public String reboot() throws ConnectException, SOAPException {
        SystemRebootResponse response = this.soap.createDeviceRequest(new SystemReboot(), new SystemRebootResponse(), true);
        return response != null ? response.getMessage() : null;
    }
}

