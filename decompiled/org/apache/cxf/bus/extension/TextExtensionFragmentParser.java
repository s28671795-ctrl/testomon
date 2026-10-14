/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.extension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.bus.extension.Extension;
import org.apache.cxf.common.logging.LogUtils;

public class TextExtensionFragmentParser {
    private static final Logger LOG = LogUtils.getL7dLogger(TextExtensionFragmentParser.class);
    final ClassLoader loader;

    public TextExtensionFragmentParser(ClassLoader loader) {
        this.loader = loader;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<Extension> getExtensions(URL url2) {
        try (InputStream is = url2.openStream();){
            List<Extension> list = this.getExtensions(is);
            return list;
        }
        catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            return new ArrayList<Extension>();
        }
    }

    public List<Extension> getExtensions(InputStream is) throws IOException {
        ArrayList<Extension> extensions = new ArrayList<Extension>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line = reader.readLine();
        while (line != null) {
            Extension extension = this.getExtensionFromTextLine(line);
            if (extension != null) {
                extensions.add(extension);
            }
            line = reader.readLine();
        }
        return extensions;
    }

    private Extension getExtensionFromTextLine(String line) {
        if ((line = line.trim()).isEmpty() || line.charAt(0) == '#') {
            return null;
        }
        Extension ext = new Extension(this.loader);
        String[] parts = line.split(":");
        ext.setClassname(parts[0]);
        if (ext.getClassname() == null) {
            return null;
        }
        if (parts.length >= 2) {
            String interfaceName = parts[1];
            if (interfaceName != null && interfaceName.isEmpty()) {
                interfaceName = null;
            }
            ext.setInterfaceName(interfaceName);
        }
        if (parts.length >= 3) {
            ext.setDeferred(Boolean.parseBoolean(parts[2]));
        }
        if (parts.length >= 4) {
            ext.setOptional(Boolean.parseBoolean(parts[3]));
        }
        return ext;
    }
}

