/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

import com.incoresoft.onvif.DiscoveryMode;
import com.incoresoft.onvif.OnvifXMLBuilder;
import com.incoresoft.onvif.models.OnvifPacket;
import java.util.Locale;

public class DiscoveryPacket
extends OnvifPacket {
    public static final String TAG = DiscoveryPacket.class.getSimpleName();
    private static final String LINE_END = "\r\n";
    private static final String DEFAULT_QUERY = "M-SEARCH * HTTP/1.1\r\nHOST: 239.255.255.250:1900\r\nMAN: \"ssdp:discover\"\r\nMX: 1\r\nST: ssdp:all\r\n\r\n";
    private final String uuid;
    private final DiscoveryMode mode;

    public DiscoveryPacket(String uuid) {
        this(uuid, DiscoveryMode.ONVIF);
    }

    public DiscoveryPacket(String uuid, DiscoveryMode mode) {
        this.uuid = uuid;
        this.mode = mode;
    }

    @Override
    public byte[] getData() {
        String data = "";
        if (this.mode.equals((Object)DiscoveryMode.ONVIF)) {
            StringBuilder builder = new StringBuilder();
            String header = String.format(Locale.getDefault(), OnvifXMLBuilder.getDiscoverySoapHeader(), this.uuid);
            builder.append(header);
            builder.append(OnvifXMLBuilder.getDiscoverySoapBody());
            builder.append(OnvifXMLBuilder.getEnvelopeEnd());
            data = builder.toString();
        } else if (this.mode.equals((Object)DiscoveryMode.UPNP)) {
            data = DEFAULT_QUERY;
        }
        return data.getBytes();
    }
}

