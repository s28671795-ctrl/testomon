/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import liquibase.GlobalConfiguration;
import liquibase.changelog.ChangeSet;
import liquibase.resource.ResourceAccessor;
import liquibase.util.BomAwareInputStream;
import liquibase.util.ObjectUtil;

public abstract class StreamUtil {
    public static String getLineSeparator() {
        return GlobalConfiguration.OUTPUT_LINE_SEPARATOR.getCurrentValue();
    }

    public static void copy(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        byte[] bytes = new byte[1024];
        int r = inputStream2.read(bytes);
        while (r > 0) {
            outputStream2.write(bytes, 0, r);
            r = inputStream2.read(bytes);
        }
    }

    public static byte[] readStream(InputStream stream) throws IOException {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();){
            StreamUtil.copy(stream, buffer);
            buffer.flush();
            byte[] byArray = buffer.toByteArray();
            return byArray;
        }
    }

    public static String readStreamAsString(InputStream stream) throws IOException {
        return StreamUtil.readStreamAsString(stream, null);
    }

    public static String readStreamAsString(InputStream stream, String encoding) throws IOException {
        StringBuilder result = new StringBuilder();
        try (Reader reader = StreamUtil.readStreamWithReader(stream, encoding);){
            int read;
            char[] buffer = new char[2048];
            while ((read = reader.read(buffer)) > -1) {
                result.append(buffer, 0, read);
            }
            String string = result.toString();
            return string;
        }
    }

    public static Reader readStreamWithReader(InputStream stream, String encoding) throws IOException {
        BomAwareInputStream encodingAwareStream = new BomAwareInputStream(stream);
        Charset detectedEncoding = encodingAwareStream.getDetectedCharset();
        if (encoding == null) {
            if (detectedEncoding != null) {
                encoding = detectedEncoding.name();
            }
        } else {
            String canonicalEncodingName = Charset.forName(encoding).name();
            if (detectedEncoding != null && canonicalEncodingName.startsWith("UTF") && !canonicalEncodingName.equals(detectedEncoding.name())) {
                throw new IllegalArgumentException("Expected encoding was '" + encoding + "' but a BOM was detected for '" + detectedEncoding + "'");
            }
        }
        return new InputStreamReader((InputStream)encodingAwareStream, ObjectUtil.defaultIfNull(encoding == null ? null : Charset.forName(encoding), GlobalConfiguration.FILE_ENCODING.getCurrentValue()));
    }

    @Deprecated
    public static InputStream openStream(String path, Boolean relativeToChangelogFile, ChangeSet changeSet, ResourceAccessor resourceAccessor) throws IOException {
        if (relativeToChangelogFile != null && relativeToChangelogFile.booleanValue()) {
            path = resourceAccessor.get(changeSet.getChangeLog().getPhysicalFilePath()).resolveSibling(path).getPath();
        }
        return resourceAccessor.getExisting(path).openInputStream();
    }
}

