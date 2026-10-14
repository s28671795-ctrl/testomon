/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.NioFiles;
import org.codehaus.plexus.util.Os;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.io.InputStreamFacade;
import org.codehaus.plexus.util.io.URLInputStreamFacade;

public class FileUtils {
    public static final int ONE_KB = 1024;
    public static final int ONE_MB = 0x100000;
    public static final int ONE_GB = 0x40000000;
    public static String FS = File.separator;
    private static final String[] INVALID_CHARACTERS_FOR_WINDOWS_FILE_NAME = new String[]{":", "*", "?", "\"", "<", ">", "|"};

    public static String[] getDefaultExcludes() {
        return DirectoryScanner.DEFAULTEXCLUDES;
    }

    public static List<String> getDefaultExcludesAsList() {
        return Arrays.asList(FileUtils.getDefaultExcludes());
    }

    public static String getDefaultExcludesAsString() {
        return StringUtils.join(DirectoryScanner.DEFAULTEXCLUDES, ",");
    }

    public static String byteCountToDisplaySize(int size) {
        String displaySize = size / 0x40000000 > 0 ? String.valueOf(size / 0x40000000) + " GB" : (size / 0x100000 > 0 ? String.valueOf(size / 0x100000) + " MB" : (size / 1024 > 0 ? String.valueOf(size / 1024) + " KB" : String.valueOf(size) + " bytes"));
        return displaySize;
    }

    public static String dirname(String filename) {
        int i = filename.lastIndexOf(File.separator);
        return i >= 0 ? filename.substring(0, i) : "";
    }

    public static String filename(String filename) {
        int i = filename.lastIndexOf(File.separator);
        return i >= 0 ? filename.substring(i + 1) : filename;
    }

    public static String basename(String filename) {
        return FileUtils.basename(filename, FileUtils.extension(filename));
    }

    public static String basename(String filename, String suffix) {
        int lastDot;
        int i = filename.lastIndexOf(File.separator) + 1;
        int n = lastDot = suffix != null && suffix.length() > 0 ? filename.lastIndexOf(suffix) : -1;
        if (lastDot >= 0) {
            return filename.substring(i, lastDot);
        }
        if (i > 0) {
            return filename.substring(i);
        }
        return filename;
    }

    public static String extension(String filename) {
        int lastDot;
        int lastSep = filename.lastIndexOf(File.separatorChar);
        if (lastSep < 0) {
            lastDot = filename.lastIndexOf(46);
        } else {
            lastDot = filename.substring(lastSep + 1).lastIndexOf(46);
            if (lastDot >= 0) {
                lastDot += lastSep + 1;
            }
        }
        if (lastDot >= 0 && lastDot > lastSep) {
            return filename.substring(lastDot + 1);
        }
        return "";
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static String fileRead(String file) throws IOException {
        return FileUtils.fileRead(file, null);
    }

    public static String fileRead(String file, String encoding) throws IOException {
        return FileUtils.fileRead(new File(file), encoding);
    }

    public static String fileRead(File file) throws IOException {
        return FileUtils.fileRead(file, null);
    }

    public static String fileRead(File file, String encoding) throws IOException {
        StringBuilder buf = new StringBuilder();
        try (InputStreamReader reader = FileUtils.getInputStreamReader(file, encoding);){
            int count;
            char[] b = new char[512];
            while ((count = reader.read(b)) >= 0) {
                buf.append(b, 0, count);
            }
        }
        return buf.toString();
    }

    private static InputStreamReader getInputStreamReader(File file, String encoding) throws IOException {
        if (encoding != null) {
            return new InputStreamReader(Files.newInputStream(file.toPath(), new OpenOption[0]), encoding);
        }
        return new InputStreamReader(Files.newInputStream(file.toPath(), new OpenOption[0]));
    }

    public static void fileAppend(String fileName, String data) throws IOException {
        FileUtils.fileAppend(fileName, null, data);
    }

    public static void fileAppend(String fileName, String encoding, String data) throws IOException {
        try (OutputStream out = Files.newOutputStream(Paths.get(fileName, new String[0]), StandardOpenOption.APPEND, StandardOpenOption.CREATE);){
            if (encoding != null) {
                out.write(data.getBytes(encoding));
            } else {
                out.write(data.getBytes());
            }
        }
    }

    public static void fileWrite(String fileName, String data) throws IOException {
        FileUtils.fileWrite(fileName, null, data);
    }

    public static void fileWrite(String fileName, String encoding, String data) throws IOException {
        File file = fileName == null ? null : new File(fileName);
        FileUtils.fileWrite(file, encoding, data);
    }

    public static void fileWrite(File file, String data) throws IOException {
        FileUtils.fileWrite(file, null, data);
    }

    public static void fileWrite(File file, String encoding, String data) throws IOException {
        try (OutputStreamWriter writer = FileUtils.getOutputStreamWriter(file, encoding);){
            writer.write(data);
        }
    }

    private static OutputStreamWriter getOutputStreamWriter(File file, String encoding) throws IOException {
        OutputStream out = Files.newOutputStream(file.toPath(), new OpenOption[0]);
        if (encoding != null) {
            return new OutputStreamWriter(out, encoding);
        }
        return new OutputStreamWriter(out);
    }

    public static void fileDelete(String fileName) {
        File file = new File(fileName);
        try {
            NioFiles.deleteIfExists(file);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean waitFor(String fileName, int seconds) {
        return FileUtils.waitFor(new File(fileName), seconds);
    }

    public static boolean waitFor(File file, int seconds) {
        int timeout2 = 0;
        int tick = 0;
        while (!file.exists()) {
            if (tick++ >= 10) {
                tick = 0;
                if (timeout2++ > seconds) {
                    return false;
                }
            }
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException interruptedException) {}
        }
        return true;
    }

    public static File getFile(String fileName) {
        return new File(fileName);
    }

    public static String[] getFilesFromExtension(String directory, String[] extensions) {
        List<String> files = new ArrayList<String>();
        File currentDir = new File(directory);
        String[] unknownFiles = currentDir.list();
        if (unknownFiles == null) {
            return new String[0];
        }
        for (String unknownFile : unknownFiles) {
            String currentFileName = directory + System.getProperty("file.separator") + unknownFile;
            File currentFile = new File(currentFileName);
            if (currentFile.isDirectory()) {
                if (currentFile.getName().equals("CVS")) continue;
                String[] fetchFiles = FileUtils.getFilesFromExtension(currentFileName, extensions);
                files = FileUtils.blendFilesToVector(files, fetchFiles);
                continue;
            }
            String add = currentFile.getAbsolutePath();
            if (!FileUtils.isValidFile(add, extensions)) continue;
            files.add(add);
        }
        return files.toArray(new String[0]);
    }

    private static List<String> blendFilesToVector(List<String> v, String[] files) {
        for (String file : files) {
            v.add(file);
        }
        return v;
    }

    private static boolean isValidFile(String file, String[] extensions) {
        String extension = FileUtils.extension(file);
        if (extension == null) {
            extension = "";
        }
        for (String extension1 : extensions) {
            if (!extension1.equals(extension)) continue;
            return true;
        }
        return false;
    }

    public static void mkdir(String dir) {
        File file = new File(dir);
        if (Os.isFamily("windows") && !FileUtils.isValidWindowsFileName(file)) {
            throw new IllegalArgumentException("The file (" + dir + ") cannot contain any of the following characters: \n" + StringUtils.join(INVALID_CHARACTERS_FOR_WINDOWS_FILE_NAME, " "));
        }
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean contentEquals(File file1, File file2) throws IOException {
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }
        if (!file1Exists) {
            return true;
        }
        if (file1.isDirectory() || file2.isDirectory()) {
            return false;
        }
        try (InputStream input1 = Files.newInputStream(file1.toPath(), new OpenOption[0]);){
            boolean bl;
            block15: {
                InputStream input2 = Files.newInputStream(file2.toPath(), new OpenOption[0]);
                try {
                    bl = IOUtil.contentEquals(input1, input2);
                    if (input2 == null) break block15;
                }
                catch (Throwable throwable) {
                    if (input2 != null) {
                        try {
                            input2.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                input2.close();
            }
            return bl;
        }
    }

    public static File toFile(URL url2) {
        if (url2 == null || !url2.getProtocol().equalsIgnoreCase("file")) {
            return null;
        }
        String filename = url2.getFile().replace('/', File.separatorChar);
        int pos = -1;
        while ((pos = filename.indexOf(37, pos + 1)) >= 0) {
            if (pos + 2 >= filename.length()) continue;
            String hexStr = filename.substring(pos + 1, pos + 3);
            char ch = (char)Integer.parseInt(hexStr, 16);
            filename = filename.substring(0, pos) + ch + filename.substring(pos + 3);
        }
        return new File(filename);
    }

    public static URL[] toURLs(File[] files) throws IOException {
        URL[] urls2 = new URL[files.length];
        for (int i = 0; i < urls2.length; ++i) {
            urls2[i] = files[i].toURI().toURL();
        }
        return urls2;
    }

    public static String removeExtension(String filename) {
        String ext = FileUtils.extension(filename);
        if ("".equals(ext)) {
            return filename;
        }
        int index = filename.lastIndexOf(ext) - 1;
        return filename.substring(0, index);
    }

    public static String getExtension(String filename) {
        return FileUtils.extension(filename);
    }

    public static String removePath(String filepath) {
        return FileUtils.removePath(filepath, File.separatorChar);
    }

    public static String removePath(String filepath, char fileSeparatorChar) {
        int index = filepath.lastIndexOf(fileSeparatorChar);
        if (-1 == index) {
            return filepath;
        }
        return filepath.substring(index + 1);
    }

    public static String getPath(String filepath) {
        return FileUtils.getPath(filepath, File.separatorChar);
    }

    public static String getPath(String filepath, char fileSeparatorChar) {
        int index = filepath.lastIndexOf(fileSeparatorChar);
        if (-1 == index) {
            return "";
        }
        return filepath.substring(0, index);
    }

    public static void copyFileToDirectory(String source2, String destinationDirectory) throws IOException {
        FileUtils.copyFileToDirectory(new File(source2), new File(destinationDirectory));
    }

    public static void copyFileToDirectoryIfModified(String source2, String destinationDirectory) throws IOException {
        FileUtils.copyFileToDirectoryIfModified(new File(source2), new File(destinationDirectory));
    }

    public static void copyFileToDirectory(File source2, File destinationDirectory) throws IOException {
        if (destinationDirectory.exists() && !destinationDirectory.isDirectory()) {
            throw new IllegalArgumentException("Destination is not a directory");
        }
        FileUtils.copyFile(source2, new File(destinationDirectory, source2.getName()));
    }

    public static void copyFileToDirectoryIfModified(File source2, File destinationDirectory) throws IOException {
        if (destinationDirectory.exists() && !destinationDirectory.isDirectory()) {
            throw new IllegalArgumentException("Destination is not a directory");
        }
        FileUtils.copyFileIfModified(source2, new File(destinationDirectory, source2.getName()));
    }

    public static void mkDirs(File sourceBase, String[] dirs, File destination) throws IOException {
        for (String dir : dirs) {
            File src = new File(sourceBase, dir);
            File dst = new File(destination, dir);
            if (NioFiles.isSymbolicLink(src)) {
                File target = NioFiles.readSymbolicLink(src);
                NioFiles.createSymbolicLink(dst, target);
                continue;
            }
            dst.mkdirs();
        }
    }

    public static void copyFile(File source2, File destination) throws IOException {
        if (!source2.exists()) {
            String message = "File " + source2 + " does not exist";
            throw new IOException(message);
        }
        if (source2.getCanonicalPath().equals(destination.getCanonicalPath())) {
            return;
        }
        FileUtils.mkdirsFor(destination);
        FileUtils.doCopyFile(source2, destination);
        if (source2.length() != destination.length()) {
            String message = "Failed to copy full contents from " + source2 + " to " + destination;
            throw new IOException(message);
        }
    }

    private static void doCopyFile(File source2, File destination) throws IOException {
        FileUtils.doCopyFileUsingNewIO(source2, destination);
    }

    private static void doCopyFileUsingNewIO(File source2, File destination) throws IOException {
        NioFiles.copy(source2, destination);
    }

    public static void linkFile(File source2, File destination) throws IOException {
        if (!source2.exists()) {
            String message = "File " + source2 + " does not exist";
            throw new IOException(message);
        }
        if (source2.getCanonicalPath().equals(destination.getCanonicalPath())) {
            return;
        }
        FileUtils.mkdirsFor(destination);
        NioFiles.createSymbolicLink(destination, source2);
    }

    public static boolean copyFileIfModified(File source2, File destination) throws IOException {
        if (FileUtils.isSourceNewerThanDestination(source2, destination)) {
            FileUtils.copyFile(source2, destination);
            return true;
        }
        return false;
    }

    public static void copyURLToFile(URL source2, File destination) throws IOException {
        FileUtils.copyStreamToFile(new URLInputStreamFacade(source2), destination);
    }

    public static void copyStreamToFile(InputStreamFacade source2, File destination) throws IOException {
        FileUtils.mkdirsFor(destination);
        FileUtils.checkCanWrite(destination);
        try (InputStream input = source2.getInputStream();
             OutputStream output = Files.newOutputStream(destination.toPath(), new OpenOption[0]);){
            IOUtil.copy(input, output);
        }
    }

    private static void checkCanWrite(File destination) throws IOException {
        if (destination.exists() && !destination.canWrite()) {
            String message = "Unable to open file " + destination + " for writing.";
            throw new IOException(message);
        }
    }

    private static void mkdirsFor(File destination) {
        File parentFile = destination.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    public static String normalize(String path) {
        int index;
        String normalized = path;
        while ((index = normalized.indexOf("//")) >= 0) {
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }
        while ((index = normalized.indexOf("/./")) >= 0) {
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        }
        while ((index = normalized.indexOf("/../")) >= 0) {
            if (index == 0) {
                return null;
            }
            int index2 = normalized.lastIndexOf(47, index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }
        return normalized;
    }

    public static String catPath(String lookupPath, String path) {
        int index = lookupPath.lastIndexOf("/");
        String lookup = lookupPath.substring(0, index);
        String pth = path;
        while (pth.startsWith("../")) {
            if (lookup.length() <= 0) {
                return null;
            }
            index = lookup.lastIndexOf("/");
            lookup = lookup.substring(0, index);
            index = pth.indexOf("../") + 3;
            pth = pth.substring(index);
        }
        return new StringBuffer(lookup).append("/").append(pth).toString();
    }

    public static File resolveFile(File baseFile, String filename) {
        String filenm = filename;
        if ('/' != File.separatorChar) {
            filenm = filename.replace('/', File.separatorChar);
        }
        if ('\\' != File.separatorChar) {
            filenm = filename.replace('\\', File.separatorChar);
        }
        if (filenm.startsWith(File.separator) || Os.isFamily("windows") && filenm.indexOf(":") > 0) {
            File file = new File(filenm);
            try {
                file = file.getCanonicalFile();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return file;
        }
        char[] chars = filename.toCharArray();
        StringBuilder sb = new StringBuilder();
        int start2 = 0;
        if ('\\' == File.separatorChar) {
            sb.append(filenm.charAt(0));
            ++start2;
        }
        for (int i = start2; i < chars.length; ++i) {
            boolean doubleSeparator;
            boolean bl = doubleSeparator = File.separatorChar == chars[i] && File.separatorChar == chars[i - 1];
            if (doubleSeparator) continue;
            sb.append(chars[i]);
        }
        filenm = sb.toString();
        File file = new File(baseFile, filenm).getAbsoluteFile();
        try {
            file = file.getCanonicalFile();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return file;
    }

    public static void forceDelete(String file) throws IOException {
        FileUtils.forceDelete(new File(file));
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            FileUtils.deleteDirectory(file);
        } else {
            boolean filePresent = file.getCanonicalFile().exists();
            if (!FileUtils.deleteFile(file) && filePresent) {
                String message = "File " + file + " unable to be deleted.";
                throw new IOException(message);
            }
        }
    }

    private static boolean deleteFile(File file) throws IOException {
        if (file.isDirectory()) {
            throw new IOException("File " + file + " isn't a file.");
        }
        if (!file.delete()) {
            if (Os.isFamily("windows")) {
                file = file.getCanonicalFile();
                System.gc();
            }
            try {
                Thread.sleep(10L);
                return file.delete();
            }
            catch (InterruptedException ignore) {
                return file.delete();
            }
        }
        return true;
    }

    public static void forceDeleteOnExit(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            FileUtils.deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    private static void deleteDirectoryOnExit(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        directory.deleteOnExit();
        FileUtils.cleanDirectoryOnExit(directory);
    }

    private static void cleanDirectoryOnExit(File directory) throws IOException {
        File[] files;
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        IOException exception = null;
        for (File file : files = directory.listFiles()) {
            try {
                FileUtils.forceDeleteOnExit(file);
            }
            catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (null != exception) {
            throw exception;
        }
    }

    public static void forceMkdir(File file) throws IOException {
        if (Os.isFamily("windows") && !FileUtils.isValidWindowsFileName(file)) {
            throw new IllegalArgumentException("The file (" + file.getAbsolutePath() + ") cannot contain any of the following characters: \n" + StringUtils.join(INVALID_CHARACTERS_FOR_WINDOWS_FILE_NAME, " "));
        }
        if (file.exists()) {
            if (file.isFile()) {
                String message = "File " + file + " exists and is not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else if (!file.mkdirs()) {
            String message = "Unable to create directory " + file;
            throw new IOException(message);
        }
    }

    public static void deleteDirectory(String directory) throws IOException {
        FileUtils.deleteDirectory(new File(directory));
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        if (directory.delete()) {
            return;
        }
        FileUtils.cleanDirectory(directory);
        if (!directory.delete()) {
            String message = "Directory " + directory + " unable to be deleted.";
            throw new IOException(message);
        }
    }

    public static void cleanDirectory(String directory) throws IOException {
        FileUtils.cleanDirectory(new File(directory));
    }

    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        IOException exception = null;
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            try {
                FileUtils.forceDelete(file);
            }
            catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (null != exception) {
            throw exception;
        }
    }

    public static long sizeOfDirectory(String directory) {
        return FileUtils.sizeOfDirectory(new File(directory));
    }

    public static long sizeOfDirectory(File directory) {
        File[] files;
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        long size = 0L;
        for (File file : files = directory.listFiles()) {
            if (file.isDirectory()) {
                size += FileUtils.sizeOfDirectory(file);
                continue;
            }
            size += file.length();
        }
        return size;
    }

    public static List<File> getFiles(File directory, String includes, String excludes) throws IOException {
        return FileUtils.getFiles(directory, includes, excludes, true);
    }

    public static List<File> getFiles(File directory, String includes, String excludes, boolean includeBasedir) throws IOException {
        List<String> fileNames = FileUtils.getFileNames(directory, includes, excludes, includeBasedir);
        ArrayList<File> files = new ArrayList<File>();
        for (String filename : fileNames) {
            files.add(new File(filename));
        }
        return files;
    }

    public static List<String> getFileNames(File directory, String includes, String excludes, boolean includeBasedir) throws IOException {
        return FileUtils.getFileNames(directory, includes, excludes, includeBasedir, true);
    }

    public static List<String> getFileNames(File directory, String includes, String excludes, boolean includeBasedir, boolean isCaseSensitive) throws IOException {
        return FileUtils.getFileAndDirectoryNames(directory, includes, excludes, includeBasedir, isCaseSensitive, true, false);
    }

    public static List<String> getDirectoryNames(File directory, String includes, String excludes, boolean includeBasedir) throws IOException {
        return FileUtils.getDirectoryNames(directory, includes, excludes, includeBasedir, true);
    }

    public static List<String> getDirectoryNames(File directory, String includes, String excludes, boolean includeBasedir, boolean isCaseSensitive) throws IOException {
        return FileUtils.getFileAndDirectoryNames(directory, includes, excludes, includeBasedir, isCaseSensitive, false, true);
    }

    public static List<String> getFileAndDirectoryNames(File directory, String includes, String excludes, boolean includeBasedir, boolean isCaseSensitive, boolean getFiles, boolean getDirectories) throws IOException {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(directory);
        if (includes != null) {
            scanner.setIncludes(StringUtils.split(includes, ","));
        }
        if (excludes != null) {
            scanner.setExcludes(StringUtils.split(excludes, ","));
        }
        scanner.setCaseSensitive(isCaseSensitive);
        scanner.scan();
        ArrayList<String> list = new ArrayList<String>();
        if (getFiles) {
            String[] files;
            for (String file : files = scanner.getIncludedFiles()) {
                if (includeBasedir) {
                    list.add(directory + FS + file);
                    continue;
                }
                list.add(file);
            }
        }
        if (getDirectories) {
            String[] directories = scanner.getIncludedDirectories();
            for (String directory1 : directories) {
                if (includeBasedir) {
                    list.add(directory + FS + directory1);
                    continue;
                }
                list.add(directory1);
            }
        }
        return list;
    }

    public static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory, "**", null);
    }

    public static void copyDirectory(File sourceDirectory, File destinationDirectory, String includes, String excludes) throws IOException {
        if (!sourceDirectory.exists()) {
            return;
        }
        List<File> files = FileUtils.getFiles(sourceDirectory, includes, excludes);
        for (File file : files) {
            FileUtils.copyFileToDirectory(file, destinationDirectory);
        }
    }

    public static void copyDirectoryLayout(File sourceDirectory, File destinationDirectory, String[] includes, String[] excludes) throws IOException {
        if (sourceDirectory == null) {
            throw new IOException("source directory can't be null.");
        }
        if (destinationDirectory == null) {
            throw new IOException("destination directory can't be null.");
        }
        if (sourceDirectory.equals(destinationDirectory)) {
            throw new IOException("source and destination are the same directory.");
        }
        if (!sourceDirectory.exists()) {
            throw new IOException("Source directory doesn't exists (" + sourceDirectory.getAbsolutePath() + ").");
        }
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(sourceDirectory);
        if (includes != null && includes.length >= 1) {
            scanner.setIncludes(includes);
        } else {
            scanner.setIncludes(new String[]{"**"});
        }
        if (excludes != null && excludes.length >= 1) {
            scanner.setExcludes(excludes);
        }
        scanner.addDefaultExcludes();
        scanner.scan();
        List<String> includedDirectories = Arrays.asList(scanner.getIncludedDirectories());
        for (String name : includedDirectories) {
            File source2 = new File(sourceDirectory, name);
            if (source2.equals(sourceDirectory)) continue;
            File destination = new File(destinationDirectory, name);
            destination.mkdirs();
        }
    }

    public static void copyDirectoryStructure(File sourceDirectory, File destinationDirectory) throws IOException {
        FileUtils.copyDirectoryStructure(sourceDirectory, destinationDirectory, destinationDirectory, false);
    }

    public static void copyDirectoryStructureIfModified(File sourceDirectory, File destinationDirectory) throws IOException {
        FileUtils.copyDirectoryStructure(sourceDirectory, destinationDirectory, destinationDirectory, true);
    }

    private static void copyDirectoryStructure(File sourceDirectory, File destinationDirectory, File rootDestinationDirectory, boolean onlyModifiedFiles) throws IOException {
        if (sourceDirectory == null) {
            throw new IOException("source directory can't be null.");
        }
        if (destinationDirectory == null) {
            throw new IOException("destination directory can't be null.");
        }
        if (sourceDirectory.equals(destinationDirectory)) {
            throw new IOException("source and destination are the same directory.");
        }
        if (!sourceDirectory.exists()) {
            throw new IOException("Source directory doesn't exists (" + sourceDirectory.getAbsolutePath() + ").");
        }
        File[] files = sourceDirectory.listFiles();
        String sourcePath = sourceDirectory.getAbsolutePath();
        for (File file : files) {
            if (file.equals(rootDestinationDirectory)) continue;
            String dest = file.getAbsolutePath();
            dest = dest.substring(sourcePath.length() + 1);
            File destination = new File(destinationDirectory, dest);
            if (file.isFile()) {
                destination = destination.getParentFile();
                if (onlyModifiedFiles) {
                    FileUtils.copyFileToDirectoryIfModified(file, destination);
                    continue;
                }
                FileUtils.copyFileToDirectory(file, destination);
                continue;
            }
            if (file.isDirectory()) {
                if (!destination.exists() && !destination.mkdirs()) {
                    throw new IOException("Could not create destination directory '" + destination.getAbsolutePath() + "'.");
                }
                FileUtils.copyDirectoryStructure(file, destination, rootDestinationDirectory, onlyModifiedFiles);
                continue;
            }
            throw new IOException("Unknown file type: " + file.getAbsolutePath());
        }
    }

    public static void rename(File from, File to) throws IOException {
        if (to.exists() && !to.delete()) {
            throw new IOException("Failed to delete " + to + " while trying to rename " + from);
        }
        File parent = to.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Failed to create directory " + parent + " while trying to rename " + from);
        }
        if (!from.renameTo(to)) {
            FileUtils.copyFile(from, to);
            if (!from.delete()) {
                throw new IOException("Failed to delete " + from + " while trying to rename it.");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static File createTempFile(String prefix, String suffix, File parentDir) {
        Random rand;
        File result = null;
        String parent = System.getProperty("java.io.tmpdir");
        if (parentDir != null) {
            parent = parentDir.getPath();
        }
        DecimalFormat fmt = new DecimalFormat("#####");
        SecureRandom secureRandom = new SecureRandom();
        long secureInitializer = secureRandom.nextLong();
        Random random = rand = new Random(secureInitializer + Runtime.getRuntime().freeMemory());
        synchronized (random) {
            while ((result = new File(parent, prefix + fmt.format(Math.abs(rand.nextInt())) + suffix)).exists()) {
            }
        }
        return result;
    }

    public static void copyFile(File from, File to, String encoding, FilterWrapper[] wrappers) throws IOException {
        FileUtils.copyFile(from, to, encoding, wrappers, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copyFile(File from, File to, String encoding, FilterWrapper[] wrappers, boolean overwrite) throws IOException {
        block7: {
            block6: {
                if (wrappers == null || wrappers.length <= 0) break block6;
                BufferedReader fileReader = null;
                Writer fileWriter = null;
                try {
                    if (encoding == null || encoding.length() < 1) {
                        fileReader = Files.newBufferedReader(from.toPath());
                        fileWriter = Files.newBufferedWriter(to.toPath(), new OpenOption[0]);
                    } else {
                        OutputStream outstream = Files.newOutputStream(to.toPath(), new OpenOption[0]);
                        fileReader = Files.newBufferedReader(from.toPath(), Charset.forName(encoding));
                        fileWriter = new OutputStreamWriter(outstream, encoding);
                    }
                    Reader reader = fileReader;
                    for (FilterWrapper wrapper : wrappers) {
                        reader = wrapper.getReader(reader);
                    }
                    IOUtil.copy(reader, fileWriter);
                    fileWriter.close();
                    fileWriter = null;
                    ((Reader)fileReader).close();
                    fileReader = null;
                }
                catch (Throwable throwable) {
                    IOUtil.close(fileReader);
                    IOUtil.close(fileWriter);
                    throw throwable;
                }
                IOUtil.close(fileReader);
                IOUtil.close(fileWriter);
                break block7;
            }
            if (FileUtils.isSourceNewerThanDestination(from, to) || overwrite) {
                FileUtils.copyFile(from, to);
            }
        }
    }

    private static boolean isSourceNewerThanDestination(File source2, File destination) {
        return destination.lastModified() == 0L && source2.lastModified() == 0L || destination.lastModified() < source2.lastModified();
    }

    public static List<String> loadFile(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        if (file.exists()) {
            try (BufferedReader reader = Files.newBufferedReader(file.toPath());){
                String line = reader.readLine();
                while (line != null) {
                    if (!(line = line.trim()).startsWith("#") && line.length() != 0) {
                        lines.add(line);
                    }
                    line = reader.readLine();
                }
            }
        }
        return lines;
    }

    public static boolean isValidWindowsFileName(File f) {
        if (Os.isFamily("windows")) {
            if (StringUtils.indexOfAny(f.getName(), INVALID_CHARACTERS_FOR_WINDOWS_FILE_NAME) != -1) {
                return false;
            }
            File parentFile = f.getParentFile();
            if (parentFile != null) {
                return FileUtils.isValidWindowsFileName(parentFile);
            }
        }
        return true;
    }

    public static abstract class FilterWrapper {
        public abstract Reader getReader(Reader var1);
    }
}

