/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import org.h2.store.fs.FileUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class SortedProperties
extends Properties {
    private static final long serialVersionUID = 1L;

    @Override
    public synchronized Enumeration<Object> keys() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Object object : this.keySet()) {
            arrayList.add(object.toString());
        }
        arrayList.sort(null);
        return Collections.enumeration(arrayList);
    }

    public static boolean getBooleanProperty(Properties properties, String string, boolean bl) {
        try {
            return Utils.parseBoolean(properties.getProperty(string, null), bl, true);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            return bl;
        }
    }

    public static int getIntProperty(Properties properties, String string, int n) {
        String string2 = properties.getProperty(string, Integer.toString(n));
        try {
            return Integer.decode(string2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return n;
        }
    }

    public static String getStringProperty(Properties properties, String string, String string2) {
        return properties.getProperty(string, string2);
    }

    public static synchronized SortedProperties loadProperties(String string) throws IOException {
        SortedProperties sortedProperties = new SortedProperties();
        if (FileUtils.exists(string)) {
            try (InputStream inputStream2 = FileUtils.newInputStream(string);){
                sortedProperties.load(new InputStreamReader(inputStream2, StandardCharsets.ISO_8859_1));
            }
        }
        return sortedProperties;
    }

    public synchronized void store(String string) throws IOException {
        OutputStreamWriter outputStreamWriter;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.store(byteArrayOutputStream, null);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        InputStreamReader inputStreamReader = new InputStreamReader((InputStream)byteArrayInputStream, StandardCharsets.ISO_8859_1);
        LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
        try {
            outputStreamWriter = new OutputStreamWriter(FileUtils.newOutputStream(string, false), StandardCharsets.ISO_8859_1);
        }
        catch (Exception exception) {
            throw new IOException(exception.toString(), exception);
        }
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(outputStreamWriter));){
            String string2;
            while ((string2 = lineNumberReader.readLine()) != null) {
                if (string2.startsWith("#")) continue;
                printWriter.print(string2 + "\n");
            }
        }
    }

    public synchronized String toLines() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : new TreeMap<Object, Object>(this).entrySet()) {
            stringBuilder.append(entry.getKey()).append('=').append(entry.getValue()).append('\n');
        }
        return stringBuilder.toString();
    }

    public static SortedProperties fromLines(String string) {
        SortedProperties sortedProperties = new SortedProperties();
        for (String string2 : StringUtils.arraySplit(string, '\n', true)) {
            int n = string2.indexOf(61);
            if (n <= 0) continue;
            sortedProperties.put(string2.substring(0, n), string2.substring(n + 1));
        }
        return sortedProperties;
    }
}

