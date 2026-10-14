/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.hardware;

import com.license4j.hardware.HDDSerial;
import com.license4j.hardware.Network;
import com.license4j.hardware.Virtual;
import com.license4j.licensetext.LicenseText;
import com.license4j.util.PowerShellUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import org.apache.commons.lang3.SystemUtils;

public class HardwareID {
    public static String getVirtualizationSoftwareName() {
        return Virtual.getVirtualizationSoftwareName();
    }

    public static String getHardwareIDFromHDDSerial() {
        return HDDSerial.getHardwareIDFromHDDSerial();
    }

    public static String getHardwareIDFromVolumeSerialNumber() {
        return HDDSerial.getHardwareIDFromVolumeSerialNumber();
    }

    public static String getHardwareIDFromHostName() {
        return SystemUtils.IS_OS_WINDOWS ? HardwareID.getWindowsCPUInfo() : HardwareID.getLinuxCPUInfo();
    }

    public static String getHardwareIDFromEthernetAddress() {
        return Network.getHardwareIDFromEthernetAddress();
    }

    public static String getWindowsCPUInfo() {
        Map<String, String> cpuInfo = PowerShellUtils.queryAsList("Get-CimInstance -Class Win32_Processor | Select-Object -Property ProcessorId, Name | Format-List");
        String model = cpuInfo.get("Name");
        String cpuid = cpuInfo.get("ProcessorId");
        Object result = "unknown";
        if (model != null && cpuid != null) {
            result = model + " " + cpuid;
        }
        return "1" + String.valueOf(UUID.nameUUIDFromBytes(((String)result).getBytes(StandardCharsets.UTF_8)));
    }

    public static String getLinuxCPUInfo() {
        String result = "unknown";
        String line = HardwareID.getLines("dmidecode --type processor | grep ID:").stream().filter(item -> item.contains("ID:")).findFirst().orElse(null);
        if (line != null) {
            result = line.substring(line.indexOf(":") + 1).trim();
        }
        return "1" + String.valueOf(UUID.nameUUIDFromBytes(result.getBytes(StandardCharsets.UTF_8)));
    }

    public static List<String> getLines(String command) {
        LinkedList<String> result = new LinkedList<String>();
        try {
            String[] cmd = new String[]{"/bin/sh", "-c", command};
            Process process = Runtime.getRuntime().exec(cmd);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));){
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getHardwareID(int hardwareIdValidation) {
        StringBuilder hardwareBuilder = new StringBuilder();
        String hardwareIdx = String.valueOf(hardwareIdValidation);
        Map<String, Supplier<String>> hardwareIdSuppliers = Map.of("1", HardwareID::getHardwareIDFromHostName, "2", HardwareID::getHardwareIDFromEthernetAddress, "3", HardwareID::getHardwareIDFromVolumeSerialNumber, "4", HardwareID::getHardwareIDFromHDDSerial);
        hardwareIdSuppliers.forEach((key, supplier) -> {
            if (hardwareIdx.contains((CharSequence)key)) {
                if (hardwareBuilder.length() > 0) {
                    hardwareBuilder.append("&&");
                }
                hardwareBuilder.append((String)supplier.get());
            }
        });
        return hardwareBuilder.toString();
    }

    public static String generateHardwareResult() {
        Supplier[] hardwareMethods = new Supplier[]{HardwareID::getHardwareIDFromHostName, HardwareID::getHardwareIDFromEthernetAddress, HardwareID::getHardwareIDFromVolumeSerialNumber, HardwareID::getHardwareIDFromHDDSerial};
        StringBuilder hardwareBuilder = new StringBuilder();
        for (Supplier hardwareMethod : hardwareMethods) {
            String hardware = (String)hardwareMethod.get();
            if (hardware == null) continue;
            if (hardwareBuilder.length() != 0) {
                hardwareBuilder.append("!");
            }
            hardwareBuilder.append(hardware);
        }
        return hardwareBuilder.toString();
    }

    public static boolean validate(LicenseText licenseText) {
        if (licenseText.getLicenseHardwareID() == null) {
            return true;
        }
        String licenseHardwareID = licenseText.getLicenseHardwareID();
        if (licenseHardwareID.contains("&&")) {
            return HardwareID.validateHardwareIDWithToken(licenseHardwareID);
        }
        if (licenseHardwareID.contains("||")) {
            return HardwareID.validateHardwareIDWithoutToken(licenseHardwareID);
        }
        return true;
    }

    private static String getHardwareIDByToken(int tokenValue) {
        switch (tokenValue) {
            case 1: {
                return HardwareID.getHardwareIDFromHostName();
            }
            case 2: {
                return HardwareID.getHardwareIDFromEthernetAddress();
            }
            case 3: {
                return HardwareID.getHardwareIDFromVolumeSerialNumber();
            }
            case 4: {
                return HardwareID.getHardwareIDFromHDDSerial();
            }
        }
        return null;
    }

    private static boolean validateHardwareIDWithToken(String licenseHardwareID) {
        String[] tokens;
        for (String id : tokens = licenseHardwareID.split("&&")) {
            if (HardwareID.wrongHardwareIDFirstLetter(id = id.trim())) {
                return false;
            }
            int value = Integer.parseInt(id.substring(0, 1));
            String idHardwareID = HardwareID.getHardwareIDByToken(value);
            if (HardwareID.equalsIgnoreCase(idHardwareID, id)) continue;
            return false;
        }
        return true;
    }

    private static boolean validateHardwareIDWithoutToken(String licenseHardwareID) {
        String[] tokens;
        for (String id : tokens = licenseHardwareID.split("\\|\\|")) {
            if (HardwareID.wrongHardwareIDFirstLetter(id = id.trim())) {
                return false;
            }
            int value = Integer.parseInt(id.substring(0, 1));
            String idHardwareID = HardwareID.getHardwareIDByToken(value);
            if (!HardwareID.equalsIgnoreCase(idHardwareID, id)) continue;
            return true;
        }
        return false;
    }

    private static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

    private static boolean wrongHardwareIDFirstLetter(String licenseHardwareID) {
        String first = licenseHardwareID.substring(0, 1);
        return !first.equals("1") && !first.equals("2") && !first.equals("3") && !first.equals("4");
    }
}

