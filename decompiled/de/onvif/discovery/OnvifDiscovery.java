/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.discovery;

import de.onvif.discovery.DeviceDiscovery;
import java.net.URL;
import java.util.Collection;

public class OnvifDiscovery {
    public static Collection<URL> discoverOnvifURLs() {
        return DeviceDiscovery.discoverWsDevicesAsUrls("^http$", ".*onvif.*");
    }
}

