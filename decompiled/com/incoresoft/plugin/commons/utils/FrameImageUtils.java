/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;

public class FrameImageUtils {
    public static String checkImagePath(String storagePath, String imagePath) {
        String filePath = FilenameUtils.separatorsToSystem(storagePath + "/storage/" + imagePath);
        return Files.exists(Path.of(filePath, new String[0]), new LinkOption[0]) ? imagePath : "stub_image.jpg";
    }
}

