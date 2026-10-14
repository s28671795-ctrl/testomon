/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

public class OnvifServices {
    public static final String TAG = OnvifServices.class.getSimpleName();
    public static final String ONVIF_PATH_SERVICES = "/onvif/device_service";
    public static final String ONVIF_PATH_DEVICE_INFORMATION = "/onvif/device_service";
    public static final String ONVIF_PATH_PROFILES = "/onvif/device_service";
    public static final String ONVIF_PATH_STREAM_URI = "/onvif/device_service";
    private String servicesPath = "/onvif/device_service";
    private String deviceInformationPath = "/onvif/device_service";
    private String profilesPath = "/onvif/device_service";
    private String streamURIPath = "/onvif/device_service";

    public String getServicesPath() {
        return this.servicesPath;
    }

    public void setServicesPath(String servicesPath) {
        this.servicesPath = servicesPath;
    }

    public String getDeviceInformationPath() {
        return this.deviceInformationPath;
    }

    public void setDeviceInformationPath(String deviceInformationPath) {
        this.deviceInformationPath = deviceInformationPath;
    }

    public String getProfilesPath() {
        return this.profilesPath;
    }

    public void setProfilesPath(String profilesPath) {
        this.profilesPath = profilesPath;
    }

    public String getStreamURIPath() {
        return this.streamURIPath;
    }

    public void setStreamURIPath(String streamURIPath) {
        this.streamURIPath = streamURIPath;
    }
}

