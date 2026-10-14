/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Objects;
import java.util.Properties;

public class PropertyUtils {
    public static Properties loadProperties(URL url2) throws IOException {
        return PropertyUtils.loadProperties(Objects.requireNonNull(url2, "url").openStream());
    }

    public static Properties loadProperties(File file) throws IOException {
        return PropertyUtils.loadProperties(Files.newInputStream(Objects.requireNonNull(file, "file").toPath(), new OpenOption[0]));
    }

    public static Properties loadProperties(InputStream is) throws IOException {
        Properties properties = new Properties();
        if (is != null) {
            try (InputStream in = is;){
                properties.load(in);
            }
        }
        return properties;
    }
}

