/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.windowsshare;

import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.smbj.common.SmbPath;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class WindowsShareUtils {
    public static boolean ofFormat(FileIdBothDirectoryInformation file, Set<String> formats) {
        if (formats.isEmpty()) {
            return true;
        }
        String fileName = file.getFileName();
        if (!fileName.matches("^.+\\..+$")) {
            return false;
        }
        String[] split = fileName.split("\\.");
        String fileFormat = split[split.length - 1];
        return StringUtils.equalsAnyIgnoreCase(fileFormat, formats.toArray(new String[0]));
    }

    public static boolean isDirectory(FileIdBothDirectoryInformation file) {
        return WindowsShareUtils.checkAttribute(file, FileAttributes.FILE_ATTRIBUTE_DIRECTORY);
    }

    public static boolean checkAttribute(FileIdBothDirectoryInformation file, FileAttributes attribute) {
        return EnumWithValue.EnumUtils.isSet(file.getFileAttributes(), attribute);
    }

    public static SmbPath create(@NotNull String host2, @NotNull String shareName, String ... parts) {
        return SmbPath.parse(String.format("\\\\%s\\%s\\%s", host2, shareName, String.join((CharSequence)"\\", parts)));
    }
}

