/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.hardware;

import com.license4j.hardware.HDDInfo;
import com.license4j.hardware.HardwareID;
import com.license4j.util.PowerShellUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;

public class HDDSerial {
    public static final String UNKNOWN = "unknown";
    private static final Pattern PATTERN = Pattern.compile("^/dev/([a-zA-Z0-9]*)");
    private static final Pattern PATTERN_2 = Pattern.compile("^(/dev/mapper/[a-z0-9_-]*)");
    private static final Map<String, Function<String, String>> serialExtractors = new HashMap<String, Function<String, String>>();

    public static String getHardwareIDFromVolumeSerialNumber() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return HDDSerial.getWindowsSystemVolume();
        }
        if (SystemUtils.IS_OS_LINUX) {
            return HDDSerial.getLinuxSystemVolume();
        }
        if (SystemUtils.IS_OS_MAC) {
            return null;
        }
        return null;
    }

    public static String getHardwareIDFromHDDSerial() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return HDDSerial.getWindowsHardwareIDFromHDDSerial();
        }
        if (SystemUtils.IS_OS_LINUX) {
            return HDDSerial.getSerialForLinux();
        }
        if (SystemUtils.IS_OS_MAC) {
            return HDDSerial.getMacOSHDDSerial();
        }
        return null;
    }

    public static String getSerialForLinux() {
        String volume = HDDSerial.getLinuxHDDVolume();
        for (Map.Entry<String, Function<String, String>> entry : serialExtractors.entrySet()) {
            Matcher matcher;
            String key = entry.getKey();
            if (!volume.matches(key) || !(matcher = Pattern.compile(key).matcher(volume)).find()) continue;
            return entry.getValue().apply(matcher.group(1));
        }
        return volume.equals("overlay") ? "4" + String.valueOf(UUID.nameUUIDFromBytes(HDDSerial.getDockerVolumeId().getBytes(StandardCharsets.UTF_8))) : null;
    }

    private static String googleCloudHDDSerial(String volume) {
        String partuuid = (String)HardwareID.getLines("findmnt -n -o PARTUUID /").stream().findFirst().orElseThrow();
        return "4" + String.valueOf(UUID.fromString(partuuid));
    }

    private static String sdaHDDSerial(String disk) {
        String serial = HDDSerial.processCommandAndExtractSerial("hdparm -i /dev/" + disk, "Model=", "SerialNo=");
        if (serial != null) {
            return serial;
        }
        serial = HDDSerial.mmcHDDSerial(disk);
        return Optional.ofNullable(serial).orElse("4" + String.valueOf(UUID.nameUUIDFromBytes("no-serial".getBytes(StandardCharsets.UTF_8))));
    }

    private static String vdaHDDSerial(String disk) {
        String serial = HDDSerial.processCommandAndExtractSerial("ls -l /dev/disk/by-id/ | grep " + disk, "virtio-", "->", "->");
        if (StringUtils.isNotEmpty(serial)) {
            return serial;
        }
        return HDDSerial.googleCloudHDDSerial(disk);
    }

    private static String nvmeHDDSerial(String disk) {
        return HDDSerial.processCommandAndExtractSerial("ls -l /dev/disk/by-id/ | grep " + disk, "nvme-", "->", "->");
    }

    private static String scsiHddSerial(String disk) {
        String command = "ls -l /dev/disk/by-id/ | grep " + disk;
        for (String idType : new String[]{"scsi-SATA", "scsi", "VBOX_HARDDISK", "ata-"}) {
            String result = null;
            try {
                result = HDDSerial.processCommandAndExtractSerial(command, idType + "-", "->");
            }
            catch (Exception e) {
                result = HDDSerial.mmcHDDSerial(disk);
            }
            if (result == null) continue;
            return result;
        }
        return null;
    }

    private static String raidHDDSerial(String disk) {
        return HDDSerial.processCommandAndExtractSerial("ls -l /dev/disk/by-id/ | grep " + disk, "cciss-", "->", "->");
    }

    private static String mmcHDDSerial(String disk) {
        return HDDSerial.processCommandAndExtractSerial("ls -l /dev/disk/by-id/ | grep " + disk, "-", "->", "->");
    }

    private static String mdHDDSerial(String disk) {
        return HDDSerial.processCommandAndExtractSerial("mdadm --detail /dev/" + disk + " | grep UUID", "UUID :", ":", " ");
    }

    public static String getMacOSHDDSerial() {
        return HDDSerial.processCommandAndExtractSerial("system_profiler SPSerialATADataType SPSASDataType SPParallelSCSIDataType", "Model: ", "Serial Number: ");
    }

    private static String processCommandAndExtractSerial(String command, String modelIdentifier, String serialIdentifier) {
        return HDDSerial.processCommandAndExtractSerial(command, modelIdentifier, serialIdentifier, ",");
    }

    private static String processCommandAndExtractSerial(String command, String modelIdentifier, String serialIdentifier, String delimiter) {
        String model = null;
        String serial = null;
        for (String line : HardwareID.getLines(command)) {
            if (model == null && line.contains(modelIdentifier)) {
                int delimiterIndex = line.indexOf(delimiter) > 0 ? line.indexOf(delimiter) : line.length() - 1;
                model = line.substring(line.indexOf(modelIdentifier) + modelIdentifier.length(), delimiterIndex);
            }
            if (serial == null && line.contains(serialIdentifier)) {
                serial = line.substring(line.lastIndexOf(serialIdentifier) + serialIdentifier.length());
            }
            if (model == null || serial == null) continue;
            break;
        }
        if (model != null && model.length() > 1 && serial != null && serial.length() > 1) {
            UUID uuid = UUID.nameUUIDFromBytes((model + " " + serial).getBytes(StandardCharsets.UTF_8));
            return "4" + String.valueOf(uuid);
        }
        return null;
    }

    private static String macOsGetVolumeSerial() {
        String serial = HDDSerial.extractSerialFromDiskUtil();
        if (serial != null && serial.length() > 1) {
            return "3" + String.valueOf(UUID.nameUUIDFromBytes(serial.getBytes(StandardCharsets.UTF_8)));
        }
        return serial;
    }

    private static String extractSerialFromDiskUtil() {
        String serial = null;
        try {
            Process process = Runtime.getRuntime().exec("diskutil info /");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));){
                String line;
                while ((line = in.readLine()) != null) {
                    if (!line.contains("Volume UUID:")) continue;
                    serial = line.substring(line.length() - 36);
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    @NotNull
    private static String getDockerVolumeId() {
        List<String> s2 = List.of("10:blkio:/kubepods/besteffort/pod0f21dd2d-7340-40d2-966d-f2faf73323b1/49e18afd47fe92c709ebe6e4dd03bd77494ebd197435dfe544b8554d5e7e1912");
        if (!s2.get(0).contains("blkio:/docker") && !s2.get(0).contains("blkio:/kubepods")) {
            return "";
        }
        return s2.get(0);
    }

    public static String getWindowsHardwareIDFromHDDSerial() {
        HDDInfo data = HDDSerial.extractModelAndSerialNumberFromPowerShell();
        UUID uuid = UUID.nameUUIDFromBytes((data.getModel() + " " + data.getSerial()).getBytes(StandardCharsets.UTF_8));
        return "4" + String.valueOf(uuid);
    }

    private static HDDInfo extractModelAndSerialNumberFromPowerShell() {
        String serialNumberCommand;
        String serialNumber;
        String diskLetter = HDDSerial.getWindowsSystemDrive();
        String diskNumberCommand = String.format("$ErrorActionPreference = 'silentlycontinue'; Get-Partition -DriveLetter \"%s\"| Get-Disk | Select-Object Number | ForEach {$_.Number}", diskLetter);
        String modelCommand = String.format("$ErrorActionPreference = 'silentlycontinue'; Get-CimInstance -ClassName Win32_DiskDrive | Where-Object { $_.Index -eq %s } | Select-Object Model | ForEach {$_.Model}", PowerShellUtils.execCommand(diskNumberCommand));
        String model = PowerShellUtils.execCommand(modelCommand);
        if (model.length() < 2) {
            model = UNKNOWN;
        }
        if (!(serialNumber = PowerShellUtils.execCommand(serialNumberCommand = String.format("$ErrorActionPreference = 'silentlycontinue'; Get-Partition -DriveLetter \"%s\" | Get-Disk | Select-Object SerialNumber | ForEach {$_.SerialNumber}", diskLetter))).isEmpty()) {
            serialNumber = serialNumber.substring(0, serialNumber.length() - 1);
        }
        if (serialNumber.length() < 2) {
            serialNumber = UNKNOWN;
        }
        return new HDDInfo(model, serialNumber);
    }

    private static String getWindowsSystemDrive() {
        return PowerShellUtils.execCommand("(Get-CimInstance Win32_OperatingSystem).SystemDrive");
    }

    private static String getLinuxHDDVolume() {
        return HDDSerial.getVolumeFromLines(HardwareID.getLines("df | grep '/$'"));
    }

    private static String getVolumeFromLines(List<String> lines) {
        for (String line : lines) {
            if (line.startsWith("overlay")) {
                return "overlay";
            }
            if (!line.contains("mapper")) {
                Matcher m4 = PATTERN.matcher(line);
                if (!m4.find()) continue;
                return m4.group(1);
            }
            Matcher m22 = PATTERN_2.matcher(line);
            if (!m22.find()) continue;
            return m22.group(1);
        }
        return null;
    }

    private static String getLinuxSystemVolume() {
        String volume = HDDSerial.extractVolume();
        Object serial = HDDSerial.extractSerial(volume);
        if (serial != null && !((String)serial).isEmpty()) {
            serial = "3" + UUID.nameUUIDFromBytes(((String)serial).getBytes(StandardCharsets.UTF_8)).toString();
        }
        return serial;
    }

    private static String extractVolume() {
        Pattern pattern = Pattern.compile("^/dev/([a-zA-Z0-9]*)");
        Pattern pattern2 = Pattern.compile("^(/dev/mapper/[a-z0-9_-]*)");
        String volume = null;
        for (String line : HardwareID.getLines("df | grep '/$'")) {
            if (line.startsWith("overlay")) {
                volume = "overlay";
            } else if (!line.contains("mapper")) {
                Matcher m4 = pattern.matcher(line);
                if (m4.find()) {
                    volume = m4.group(1);
                }
            } else {
                Matcher m22 = pattern2.matcher(line);
                if (m22.find()) {
                    volume = m22.group(1);
                }
            }
            if (volume == null) continue;
            break;
        }
        return volume;
    }

    private static String extractSerial(String volume) {
        String serial = null;
        if (volume != null) {
            if (volume.contains("mapper")) {
                serial = HDDSerial.extractUUID(volume);
                if (serial == null) {
                    serial = UNKNOWN;
                }
            } else if (volume.equals("overlay")) {
                serial = HDDSerial.getDockerVolumeId();
            } else {
                serial = HDDSerial.extractUUIDFromVolume(volume);
                if (serial == null) {
                    serial = UNKNOWN;
                }
            }
        }
        return serial;
    }

    private static String extractUUID(String volume) {
        String serial = HardwareID.getLines("blkid | grep " + volume).stream().filter(line -> line.contains("UUID")).findFirst().orElse(null);
        if (serial != null && serial.length() > 40) {
            serial = serial.substring(serial.indexOf("UUID=") + 6, serial.indexOf("UUID=") + 42);
        }
        return serial;
    }

    private static String extractUUIDFromVolume(String volume) {
        String serial = HardwareID.getLines("ls -l /dev/disk/by-uuid/ | grep -w " + volume).stream().filter(line -> line.endsWith(volume) && line.substring(line.indexOf("->") - 37, line.indexOf("->") - 1).length() > 30).findFirst().orElse(null);
        if (serial != null && serial.length() > 40 && (serial = serial.substring(serial.indexOf("->") - 37, serial.indexOf("->") - 1)).contains(":")) {
            serial = serial.substring(serial.length() - 16);
        }
        return serial;
    }

    private static String getWindowsSystemVolume() {
        Object serial = null;
        try {
            String systemDrive = HDDSerial.getWindowsSystemDrive();
            String command = String.format("cmd /c vol %s", systemDrive);
            Process process = Runtime.getRuntime().exec(command);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));){
                String line;
                while ((line = in.readLine()) != null) {
                    serial = line;
                }
                if (serial != null && ((String)serial).length() > 10) {
                    serial = ((String)serial).substring(((String)serial).length() - 9);
                }
                if (serial != null) {
                    serial = "3" + String.valueOf(UUID.nameUUIDFromBytes(((String)serial).getBytes(StandardCharsets.UTF_8)));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    static {
        serialExtractors.put("^((x?[sh]d)[a-z])[0-9]*$", HDDSerial::sdaHDDSerial);
        serialExtractors.put("^(nvme[0-9]*n[0-9]*)p[0-9]*$", HDDSerial::nvmeHDDSerial);
        serialExtractors.put("^(md[0-9]*)(p[0-9]*)?$", HDDSerial::mdHDDSerial);
        serialExtractors.put("^(mmcblk[0-9]*)p[0-9]*$", HDDSerial::mmcHDDSerial);
        serialExtractors.put("^(/dev/mapper/[a-z0-9_-]*)", s2 -> "4" + String.valueOf(UUID.nameUUIDFromBytes("LVM".getBytes(StandardCharsets.UTF_8))));
        serialExtractors.put("^(c[0-9]*d[0-9]*)p[0-9]*$", HDDSerial::raidHDDSerial);
        serialExtractors.put("^((x?vd)[a-z])[0-9]*$", HDDSerial::vdaHDDSerial);
        serialExtractors.put("^(root)$", HDDSerial::googleCloudHDDSerial);
    }
}

