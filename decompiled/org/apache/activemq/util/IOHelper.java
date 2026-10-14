/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.apache.activemq.util.HexSupport;

public final class IOHelper {
    protected static final int MAX_DIR_NAME_LENGTH = Integer.getInteger("MaximumDirNameLength", 200);
    protected static final int MAX_FILE_NAME_LENGTH = Integer.getInteger("MaximumFileNameLength", 64);
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private IOHelper() {
    }

    public static String getDefaultDataDirectory() {
        return IOHelper.getDefaultDirectoryPrefix() + "activemq-data";
    }

    public static String getDefaultStoreDirectory() {
        return IOHelper.getDefaultDirectoryPrefix() + "amqstore";
    }

    public static String getDefaultDirectoryPrefix() {
        try {
            return System.getProperty("org.apache.activemq.default.directory.prefix", "");
        }
        catch (Exception e) {
            return "";
        }
    }

    public static String toFileSystemDirectorySafeName(String name) {
        return IOHelper.toFileSystemSafeName(name, true, MAX_DIR_NAME_LENGTH);
    }

    public static String toFileSystemSafeName(String name) {
        return IOHelper.toFileSystemSafeName(name, false, MAX_FILE_NAME_LENGTH);
    }

    public static String toFileSystemSafeName(String name, boolean dirSeparators, int maxFileLength) {
        int size = name.length();
        StringBuffer rc = new StringBuffer(size * 2);
        for (int i = 0; i < size; ++i) {
            char c = name.charAt(i);
            boolean valid = c >= 'a' && c <= 'z';
            valid = valid || c >= 'A' && c <= 'Z';
            valid = valid || c >= '0' && c <= '9';
            boolean bl = valid = valid || c == '_' || c == '-' || c == '.' || c == '#' || dirSeparators && (c == '/' || c == '\\');
            if (valid) {
                rc.append(c);
                continue;
            }
            rc.append('#');
            rc.append(HexSupport.toHexFromInt(c, true));
        }
        String result = rc.toString();
        if (result.length() > maxFileLength) {
            result = result.substring(result.length() - maxFileLength, result.length());
        }
        return result;
    }

    public static boolean delete(File top) {
        boolean result = true;
        Stack<File> files = new Stack<File>();
        files.push(top);
        while (!files.isEmpty()) {
            File file = (File)files.pop();
            if (file.isDirectory()) {
                File[] list = file.listFiles();
                if (list == null || list.length == 0) {
                    result &= file.delete();
                    continue;
                }
                files.push(file);
                for (File dirFile : list) {
                    if (dirFile.isDirectory()) {
                        files.push(dirFile);
                        continue;
                    }
                    result &= dirFile.delete();
                }
                continue;
            }
            result &= file.delete();
        }
        return result;
    }

    public static boolean deleteFile(File fileToDelete) {
        if (fileToDelete == null || !fileToDelete.exists()) {
            return true;
        }
        boolean result = IOHelper.deleteChildren(fileToDelete);
        return result &= fileToDelete.delete();
    }

    public static boolean deleteChildren(File parent) {
        if (parent == null || !parent.exists()) {
            return false;
        }
        boolean result = true;
        if (parent.isDirectory()) {
            File[] files = parent.listFiles();
            if (files == null) {
                result = false;
            } else {
                for (int i = 0; i < files.length; ++i) {
                    File file = files[i];
                    if (file.getName().equals(".") || file.getName().equals("..")) continue;
                    if (file.isDirectory()) {
                        result &= IOHelper.deleteFile(file);
                        continue;
                    }
                    result &= file.delete();
                }
            }
        }
        return result;
    }

    public static void moveFile(File src, File targetDirectory) throws IOException {
        if (!src.renameTo(new File(targetDirectory, src.getName()))) {
            Path sourcePath = src.toPath();
            Path targetDirPath = targetDirectory.toPath();
            try {
                Files.move(sourcePath, targetDirPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex) {
                throw new IOException("Failed to move " + src + " to " + targetDirectory + " - " + ex.getMessage());
            }
        }
    }

    public static void moveFiles(File srcDirectory, File targetDirectory, FilenameFilter filter2) throws IOException {
        if (!srcDirectory.isDirectory()) {
            throw new IOException("source is not a directory");
        }
        if (targetDirectory.exists() && !targetDirectory.isDirectory()) {
            throw new IOException("target exists and is not a directory");
        }
        IOHelper.mkdirs(targetDirectory);
        ArrayList<File> filesToMove = new ArrayList<File>();
        IOHelper.getFiles(srcDirectory, filesToMove, filter2);
        for (File file : filesToMove) {
            if (file.isDirectory()) continue;
            IOHelper.moveFile(file, targetDirectory);
        }
    }

    public static void copyFile(File src, File dest) throws IOException {
        IOHelper.copyFile(src, dest, null);
    }

    public static void copyFile(File src, File dest, FilenameFilter filter2) throws IOException {
        if (!src.getCanonicalPath().equals(dest.getCanonicalPath())) {
            if (src.isDirectory()) {
                IOHelper.mkdirs(dest);
                List<File> list = IOHelper.getFiles(src, filter2);
                for (File f : list) {
                    if (!f.isFile()) continue;
                    File target = new File(IOHelper.getCopyParent(src, dest, f), f.getName());
                    IOHelper.copySingleFile(f, target);
                }
            } else if (dest.isDirectory()) {
                IOHelper.mkdirs(dest);
                File target = new File(dest, src.getName());
                IOHelper.copySingleFile(src, target);
            } else {
                IOHelper.copySingleFile(src, dest);
            }
        }
    }

    static File getCopyParent(File from, File to, File src) {
        File result = null;
        File parent = src.getParentFile();
        String fromPath = from.getAbsolutePath();
        if (parent.getAbsolutePath().equals(fromPath)) {
            result = to;
        } else {
            String parentPath = parent.getAbsolutePath();
            String path = parentPath.substring(fromPath.length());
            result = new File(to.getAbsolutePath() + File.separator + path);
        }
        return result;
    }

    static List<File> getFiles(File dir, FilenameFilter filter2) {
        ArrayList<File> result = new ArrayList<File>();
        IOHelper.getFiles(dir, result, filter2);
        return result;
    }

    static void getFiles(File dir, List<File> list, FilenameFilter filter2) {
        if (!list.contains(dir)) {
            list.add(dir);
            String[] fileNames = dir.list(filter2);
            for (int i = 0; i < fileNames.length; ++i) {
                File f = new File(dir, fileNames[i]);
                if (f.isFile()) {
                    list.add(f);
                    continue;
                }
                IOHelper.getFiles(dir, list, filter2);
            }
        }
    }

    public static void copySingleFile(File src, File dest) throws IOException {
        FileInputStream fileSrc = new FileInputStream(src);
        FileOutputStream fileDest = new FileOutputStream(dest);
        IOHelper.copyInputStream(fileSrc, fileDest);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[4096];
            int len = in.read(buffer);
            while (len >= 0) {
                out.write(buffer, 0, len);
                len = in.read(buffer);
            }
        }
        finally {
            in.close();
            out.close();
        }
    }

    public static int getMaxDirNameLength() {
        return MAX_DIR_NAME_LENGTH;
    }

    public static int getMaxFileNameLength() {
        return MAX_FILE_NAME_LENGTH;
    }

    public static void mkdirs(File dir) throws IOException {
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IOException("Failed to create directory '" + dir + "', regular file already existed with that name");
            }
        } else if (!dir.mkdirs()) {
            throw new IOException("Failed to create directory '" + dir + "'");
        }
    }
}

