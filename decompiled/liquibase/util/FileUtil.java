/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.util.StreamUtil;

public class FileUtil {
    private FileUtil() {
        throw new IllegalStateException("This utility class must not be instantiated. Sorry.");
    }

    public static String getContents(File file) throws IOException {
        String string;
        block9: {
            if (!file.exists()) {
                return null;
            }
            InputStream fileInputStream = Files.newInputStream(file.toPath(), new OpenOption[0]);
            try {
                string = StreamUtil.readStreamAsString(fileInputStream);
                if (fileInputStream == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (FileNotFoundException e) {
                    return null;
                }
            }
            fileInputStream.close();
        }
        return string;
    }

    public static void write(String contents, File file) throws IOException {
        FileUtil.write(contents, file, false);
    }

    public static void write(String contents, File file, boolean append) throws IOException {
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream output = new FileOutputStream(file, append);){
            StreamUtil.copy(new ByteArrayInputStream(contents.getBytes(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())), output);
        }
    }

    public static String getFileNotFoundMessage(String physicalChangeLogLocation) {
        if (physicalChangeLogLocation.matches("^\\w\\w+:.*") && !physicalChangeLogLocation.startsWith("file:")) {
            return "ERROR: The file '" + physicalChangeLogLocation + "' was not found." + System.lineSeparator() + "The file property cannot be configured with a fully qualified path, but must be a relative path on the property," + System.lineSeparator() + "and any local or remote base of the path set on the search path.";
        }
        String message = "The file " + physicalChangeLogLocation + " was not found in the configured search path:" + System.lineSeparator();
        StringBuilder builder = new StringBuilder(message);
        for (String location : Scope.getCurrentScope().getResourceAccessor().describeLocations()) {
            builder.append("    - ").append(location).append(System.lineSeparator());
        }
        builder.append("More locations can be added with the 'searchPath' parameter.");
        return builder.toString();
    }

    public static boolean isAbsolute(String path) {
        if (path == null) {
            return false;
        }
        return new File(path).isAbsolute();
    }
}

