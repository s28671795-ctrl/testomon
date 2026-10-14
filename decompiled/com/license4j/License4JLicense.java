/*
 * Decompiled with CFR 0.152.
 */
package com.license4j;

import com.license4j.hardware.HardwareID;

public class License4JLicense {
    public static void main(String[] args2) {
        System.out.println("0 -> " + HardwareID.getWindowsCPUInfo());
        System.out.println("1 -> " + HardwareID.getHardwareIDFromHostName());
        System.out.println("2 -> " + HardwareID.getHardwareIDFromEthernetAddress());
        System.out.println("3 -> " + HardwareID.getHardwareIDFromVolumeSerialNumber());
        System.out.println("4 -> " + HardwareID.getHardwareIDFromHDDSerial());
        System.out.println("5 -> " + HardwareID.getVirtualizationSoftwareName());
    }
}

