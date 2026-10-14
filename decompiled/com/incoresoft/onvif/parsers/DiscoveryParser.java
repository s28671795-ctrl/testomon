/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.parsers;

import com.incoresoft.onvif.DiscoveryMode;
import com.incoresoft.onvif.OnvifUtils;
import com.incoresoft.onvif.models.Device;
import com.incoresoft.onvif.models.DiscoveryType;
import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.UPnPDevice;
import com.incoresoft.onvif.parsers.OnvifParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public class DiscoveryParser
extends OnvifParser<List<Device>> {
    public static final String TAG = DiscoveryParser.class.getSimpleName();
    private static final String LINE_END = "\r\n";
    private static final String KEY_UPNP_LOCATION = "LOCATION: ";
    private static final String KEY_UPNP_SERVER = "SERVER: ";
    private static final String KEY_UPNP_USN = "USN: ";
    private static final String KEY_UPNP_ST = "ST: ";
    private final DiscoveryMode mode;
    private String hostName;

    public DiscoveryParser(DiscoveryMode mode) {
        this.mode = mode;
        this.hostName = "";
    }

    @Override
    public List<Device> parse(OnvifResponse response) {
        ArrayList<Device> devices = new ArrayList<Device>();
        switch (this.mode) {
            case ONVIF: {
                devices.addAll(this.parseOnvif(response));
                break;
            }
            case UPNP: {
                devices.add(this.parseUPnP(response));
            }
        }
        return devices;
    }

    private List<Device> parseOnvif(OnvifResponse response) {
        ArrayList<Device> devices = new ArrayList<Device>();
        try {
            this.getXpp().setInput(new StringReader(response.getXml()));
            this.eventType = this.getXpp().getEventType();
            while (this.eventType != 1) {
                if (this.eventType == 2 && this.getXpp().getName().equals("Types")) {
                    this.getXpp().next();
                    String type = this.getXpp().getText();
                    if (this.mode.equals((Object)DiscoveryMode.ONVIF) && type.contains(DiscoveryType.NETWORK_VIDEO_TRANSMITTER.type)) {
                        String uri = OnvifUtils.retrieveXAddrs(this.getXpp());
                        devices.addAll(this.parseDevicesFromUri(uri));
                    }
                }
                this.eventType = this.getXpp().next();
            }
        }
        catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return devices;
    }

    private Device parseUPnP(OnvifResponse response) {
        String header = response.getXml();
        String location = this.parseUPnPHeader(header, KEY_UPNP_LOCATION);
        String server = this.parseUPnPHeader(header, KEY_UPNP_SERVER);
        String usn = this.parseUPnPHeader(header, KEY_UPNP_USN);
        String st = this.parseUPnPHeader(header, KEY_UPNP_ST);
        return new UPnPDevice(this.getHostName(), header, location, server, usn, st);
    }

    private String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    private List<OnvifDevice> parseDevicesFromUri(String uri) {
        String[] uris;
        ArrayList<OnvifDevice> devices = new ArrayList<OnvifDevice>();
        for (String address : uris = uri.split("\\s+")) {
            OnvifDevice device = new OnvifDevice(this.getHostName());
            device.addAddress(address);
            devices.add(device);
        }
        return devices;
    }

    private String parseUPnPHeader(String header, String whatSearch) {
        String result = "";
        int searchLinePos = header.indexOf(whatSearch);
        if (searchLinePos != -1) {
            int locColon = header.indexOf(LINE_END, searchLinePos += whatSearch.length());
            result = header.substring(searchLinePos, locColon);
        }
        return result;
    }
}

