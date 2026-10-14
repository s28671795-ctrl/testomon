/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.hardware;

import com.license4j.util.PowerShellUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.SystemUtils;

public class Virtual {
    private static String virtualMachine = null;
    private static final Map<String, String> virtualMachinesMap = new HashMap<String, String>();

    public static String getVirtualizationSoftwareName() {
        if (virtualMachine == null) {
            Virtual.isVirtual();
        }
        return virtualMachine;
    }

    public static boolean isVirtualWindows() {
        Map<String, String> result = PowerShellUtils.queryAsList("Get-CimInstance -ClassName Win32_ComputerSystem | Select-Object -Property Model | Format-List");
        String modelLine = result.get("Model");
        if (modelLine == null) {
            return false;
        }
        return virtualMachinesMap.entrySet().stream().filter(entry -> modelLine.contains((CharSequence)entry.getKey())).peek(entry -> {
            virtualMachine = (String)entry.getValue();
        }).findFirst().isPresent();
    }

    public static boolean isVirtual() {
        boolean isVirtual = false;
        if (SystemUtils.IS_OS_WINDOWS) {
            isVirtual = Virtual.isVirtualWindows();
        } else if (SystemUtils.IS_OS_LINUX) {
            isVirtual = Virtual.isVirtualLinux();
        } else if (SystemUtils.IS_OS_MAC) {
            isVirtual = Virtual.isVirtualMacOS();
        }
        return isVirtual;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String execAndGetLine(String command, String lineStartsWith) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(command).getInputStream()));){
            String line;
            do {
                if ((line = in.readLine()) == null) return null;
            } while (!line.startsWith(lineStartsWith));
            String string = line;
            return string;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isVirtualLinux() {
        String modelLine = Virtual.execAndGetLine("dmesg | grep -i virtual", "Model=");
        if (modelLine == null) {
            return false;
        }
        return virtualMachinesMap.entrySet().stream().filter(entry -> modelLine.contains((CharSequence)entry.getKey())).peek(entry -> {
            virtualMachine = (String)entry.getValue();
        }).findFirst().isPresent();
    }

    private static boolean isVirtualMacOS() {
        String modelLine = Virtual.execAndGetLine("system_profiler SPSerialATADataType SPSASDataType SPParallelSCSIDataType", "Model:");
        if (modelLine == null) {
            return false;
        }
        return virtualMachinesMap.entrySet().stream().filter(entry -> modelLine.contains((CharSequence)entry.getKey())).peek(entry -> {
            virtualMachine = (String)entry.getValue();
        }).findFirst().isPresent();
    }

    static {
        virtualMachinesMap.put("VirtualBox", "VirtualBox");
        virtualMachinesMap.put("Virtual Machine", "Hyper-V");
        virtualMachinesMap.put("VMware Virtual Platform", "VMware");
        virtualMachinesMap.put("vServer", "QEMU");
        virtualMachinesMap.put("xen", "Xen");
    }
}

