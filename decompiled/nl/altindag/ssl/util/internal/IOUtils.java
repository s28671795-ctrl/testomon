/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util.internal;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.stream.Collectors;
import nl.altindag.ssl.exception.GenericIOException;
import nl.altindag.ssl.util.internal.ValidationUtils;

public final class IOUtils {
    private IOUtils() {
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static String getContent(InputStream inputStream2) {
        try (InputStreamReader inputStreamReader = new InputStreamReader(ValidationUtils.requireNotNull(inputStream2, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("InputStream")), StandardCharsets.UTF_8);){
            String string;
            try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
                string = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
            return string;
        }
        catch (Exception e) {
            throw new GenericIOException(e);
        }
    }

    public static byte[] copyToByteArray(InputStream inputStream2) {
        byte[] byArray;
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        try {
            int length;
            byte[] buffer = new byte[1024];
            while ((length = inputStream2.read(buffer)) > -1) {
                outputStream2.write(buffer, 0, length);
            }
            outputStream2.flush();
            byArray = outputStream2.toByteArray();
        }
        catch (Throwable throwable) {
            try {
                try {
                    outputStream2.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (Exception e) {
                throw new GenericIOException(e);
            }
        }
        outputStream2.close();
        return byArray;
    }

    public static void closeSilently(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static InputStream getResourceAsStream(String name) {
        return IOUtils.class.getClassLoader().getResourceAsStream(name);
    }

    public static InputStream getFileAsStream(Path path) {
        try {
            return Files.newInputStream(path, StandardOpenOption.READ);
        }
        catch (IOException e) {
            throw new GenericIOException(e);
        }
    }

    public static void write(Path path, byte[] data) {
        try {
            IOUtils.createDirectoriesIfAbsent(path);
            Files.write(path, data, StandardOpenOption.CREATE);
        }
        catch (IOException e) {
            throw new GenericIOException(e);
        }
    }

    public static void write(Path path, Consumer<OutputStream> consumer) {
        try {
            IOUtils.createDirectoriesIfAbsent(path);
            try (OutputStream outputStream2 = Files.newOutputStream(path, StandardOpenOption.CREATE);){
                consumer.accept(outputStream2);
            }
        }
        catch (Exception e) {
            throw new GenericIOException(e);
        }
    }

    private static void createDirectoriesIfAbsent(Path absoluteFilePath) throws IOException {
        Path parentDirectories = absoluteFilePath.getParent();
        if (Files.notExists(parentDirectories, new LinkOption[0])) {
            Files.createDirectories(parentDirectories, new FileAttribute[0]);
        }
    }

    @FunctionalInterface
    public static interface Consumer<T> {
        public void accept(T var1) throws Exception;
    }
}

