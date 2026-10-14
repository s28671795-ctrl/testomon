/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.hardware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.SystemUtils;

public class Network {
    static final List<String> allowedDevices = List.of("eth", "enp", "eno", "ens");
    private static final AtomicReference<String> WINDOWS_MAC_ADDRESS = new AtomicReference();

    public static String getHardwareIDFromEthernetAddress() {
        String mac = null;
        try {
            mac = SystemUtils.IS_OS_WINDOWS ? Network.getMACAddressWindows() : Network.getMACAddressLinux();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(mac).filter(m4 -> m4.length() > 11).map(m4 -> "2" + String.valueOf(UUID.nameUUIDFromBytes(m4.getBytes(StandardCharsets.UTF_8)))).orElse("2" + String.valueOf(UUID.nameUUIDFromBytes("no ethernet".getBytes(StandardCharsets.UTF_8))));
    }

    static String getMACAddressLinux() {
        ArrayList<String> devices = new ArrayList<String>();
        Pattern pattern = Pattern.compile("^ *(.*):");
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/net/dev"));){
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m4 = pattern.matcher(line);
                if (!m4.find()) continue;
                devices.add(m4.group(1));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Network.getMACAddresses(devices);
    }

    public static String getMACAddressWindows() throws SocketException {
        if (WINDOWS_MAC_ADDRESS.get() == null) {
            WINDOWS_MAC_ADDRESS.set(Network.retrieveWindowsMacAddress());
        }
        return WINDOWS_MAC_ADDRESS.get();
    }

    private static String retrieveWindowsMacAddress() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        List<String> devices = Collections.list(nets).stream().map(NetworkInterface::getName).collect(Collectors.toList());
        return Network.getMACAddresses(devices);
    }

    private static String getMACAddresses(List<String> devices) {
        List list = devices.stream().filter(Network::checkAllowedDevices).sorted().collect(Collectors.toList());
        return list.isEmpty() ? null : String.join((CharSequence)"", list);
    }

    private static boolean checkAllowedDevices(String device) {
        return allowedDevices.stream().anyMatch(device.toLowerCase()::startsWith);
    }
}

