/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilenameUtil {
    public static String normalize(String filename) {
        if (filename == null) {
            return null;
        }
        int size = filename.length();
        if (size == 0) {
            return filename;
        }
        filename = filename.replaceFirst("^//", "/");
        try {
            String returnPath = Paths.get(filename, new String[0]).normalize().toString();
            returnPath = returnPath.replace('\\', '/');
            returnPath = returnPath.replaceAll("/\\./", "/");
            returnPath = returnPath.replaceAll("//", "/");
            return returnPath;
        }
        catch (InvalidPathException e) {
            return null;
        }
    }

    public static String concat(String basePath, String fullFilenameToAdd) {
        if (basePath == null) {
            return FilenameUtil.normalize(fullFilenameToAdd);
        }
        return FilenameUtil.normalize(Paths.get(basePath, fullFilenameToAdd).toString());
    }

    public static String getDirectory(String filename) {
        if (filename == null) {
            return null;
        }
        if (filename.endsWith("/") || filename.endsWith("\\")) {
            return FilenameUtil.normalize(filename);
        }
        Path path = Paths.get(filename, new String[0]);
        Path fileName = path.getFileName();
        if (fileName.toString().contains(".")) {
            Path parent = path.getParent();
            if (parent == null) {
                return "";
            }
            return FilenameUtil.normalize(parent.toString());
        }
        return FilenameUtil.normalize(filename);
    }

    public static String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return null;
        }
        fileName = fileName.replaceAll(" ", "_").replaceAll("/", "_").replaceAll(":", "_").replaceAll("\"", "_").replaceAll("\\|", "_").replaceAll("\\*", "_").replaceAll("\\?", "_").replaceAll("<", "_").replaceAll(">", "_").replaceAll("@", "_");
        boolean done = false;
        while (!done) {
            String replacedString = fileName.replace('\\', '_');
            done = fileName.equals(replacedString);
            if (done) continue;
            fileName = replacedString;
            break;
        }
        return fileName;
    }
}

