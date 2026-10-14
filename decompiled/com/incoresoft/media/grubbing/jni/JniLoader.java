/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.media.grubbing.jni;

import com.incoresoft.plugin.commons.utils.OsUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import net.lingala.zip4j.ZipFile;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JniLoader {
    private static final Logger log = LoggerFactory.getLogger(JniLoader.class);
    private static final Path LIB_DIR = Path.of("", new String[0]);
    private static final String JNI_GRABBER_TEMP_DIR_PREFIX = "jni-grabber-";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void loadFrameGrabberLib() throws IOException {
        URL url2 = JniLoader.class.getResource("/jni/grabber/" + JniLoader.getLibZip());
        Files.createDirectories(LIB_DIR, new FileAttribute[0]);
        File tempZip = Files.createTempFile(JNI_GRABBER_TEMP_DIR_PREFIX, "", new FileAttribute[0]).toFile();
        try {
            try (InputStream in = url2.openStream();){
                Files.copy(in, tempZip.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            try (ZipFile zipFile = new ZipFile(tempZip);){
                zipFile.extractAll(LIB_DIR.toAbsolutePath().toString());
            }
        }
        finally {
            if (tempZip.exists()) {
                tempZip.delete();
            }
        }
        System.load(LIB_DIR.resolve(JniLoader.getLibName()).toAbsolutePath().toString());
    }

    @NotNull
    private static String getLibZip() {
        OsUtils.OSType operatingSystemType = OsUtils.getOperatingSystemType();
        return switch (operatingSystemType) {
            case OsUtils.OSType.Linux -> "jni_frame_grab_linux.zip";
            case OsUtils.OSType.Windows -> "jni_frame_grab_windows.zip";
            default -> throw new IllegalArgumentException(String.format("Frame grabbing lib for OS \"%s\" not found", new Object[]{operatingSystemType}));
        };
    }

    @NotNull
    private static String getLibName() {
        OsUtils.OSType operatingSystemType = OsUtils.getOperatingSystemType();
        return switch (operatingSystemType) {
            case OsUtils.OSType.Linux -> "libframegrabber.so";
            case OsUtils.OSType.Windows -> "framegrabber.dll";
            default -> throw new IllegalArgumentException(String.format("Frame grabbing lib for OS \"%s\" not found", new Object[]{operatingSystemType}));
        };
    }
}

