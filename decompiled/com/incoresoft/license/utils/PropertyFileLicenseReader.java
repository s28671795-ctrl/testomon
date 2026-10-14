/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyFileLicenseReader {
    private static final Logger log = LoggerFactory.getLogger(PropertyFileLicenseReader.class);
    public static final String ACTIVATED_LICENSE_TEXT = "activated_license_text";
    public static final String LICENSE_KEY = "license_key";

    public static Map<String, String> read(Path filePath) {
        Properties properties = new Properties();
        File file = filePath.toFile();
        if (file.exists()) {
            try {
                properties.load(new FileInputStream(file));
            }
            catch (IOException ex) {
                log.error("Couldn't read properties from file.", ex);
            }
        }
        HashMap<String, String> licenseData = new HashMap<String, String>();
        licenseData.put(ACTIVATED_LICENSE_TEXT, properties.getProperty(ACTIVATED_LICENSE_TEXT));
        licenseData.put(LICENSE_KEY, properties.getProperty(LICENSE_KEY));
        return licenseData;
    }
}

