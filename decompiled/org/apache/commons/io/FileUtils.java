/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.NullOutputStream;

public class FileUtils {
    public static final long ONE_KB = 1024L;
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf(1024L);
    public static final long ONE_MB = 0x100000L;
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);
    public static final long ONE_GB = 0x40000000L;
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);
    public static final long ONE_TB = 0x10000000000L;
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);
    public static final long ONE_PB = 0x4000000000000L;
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);
    public static final long ONE_EB = 0x1000000000000000L;
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);
    public static final BigInteger ONE_ZB = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(0x1000000000000000L));
    public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);
    public static final File[] EMPTY_FILE_ARRAY = new File[0];

    public static String byteCountToDisplaySize(BigInteger size) {
        String displaySize = size.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_EB_BI)) + " EB" : (size.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_PB_BI)) + " PB" : (size.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_TB_BI)) + " TB" : (size.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_GB_BI)) + " GB" : (size.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_MB_BI)) + " MB" : (size.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_KB_BI)) + " KB" : String.valueOf(size) + " bytes")))));
        return displaySize;
    }

    public static String byteCountToDisplaySize(long size) {
        return FileUtils.byteCountToDisplaySize(BigInteger.valueOf(size));
    }

    private static void checkDirectory(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
    }

    private static void checkEqualSizes(File srcFile, File destFile, long srcLen, long dstLen) throws IOException {
        if (srcLen != dstLen) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "' Expected length: " + srcLen + " Actual: " + dstLen);
        }
    }

    private static void checkFileRequirements(File source2, File destination) throws FileNotFoundException {
        Objects.requireNonNull(source2, "source");
        Objects.requireNonNull(destination, "target");
        if (!source2.exists()) {
            throw new FileNotFoundException("Source '" + source2 + "' does not exist");
        }
    }

    public static Checksum checksum(File file, Checksum checksum) throws IOException {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Checksums can't be computed on directories");
        }
        try (CheckedInputStream in = new CheckedInputStream(new FileInputStream(file), checksum);){
            IOUtils.copy((InputStream)in, (OutputStream)NullOutputStream.NULL_OUTPUT_STREAM);
        }
        return checksum;
    }

    public static long checksumCRC32(File file) throws IOException {
        return FileUtils.checksum(file, new CRC32()).getValue();
    }

    public static void cleanDirectory(File directory) throws IOException {
        File[] files = FileUtils.verifiedListFiles(directory);
        IOException exception = null;
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

    private static void cleanDirectoryOnExit(File directory) throws IOException {
        File[] files = FileUtils.verifiedListFiles(directory);
        IOException exception = null;
        for (File file : files) {
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

    /*
     * Exception decompiling
     */
    public static boolean contentEquals(File file1, File file2) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static boolean contentEqualsIgnoreEOL(File file1, File file2, String charsetName) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static File[] convertFileCollectionToFileArray(Collection<File> files) {
        return files.toArray(new File[files.size()]);
    }

    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        FileUtils.copyDirectory(srcDir, destDir, true);
    }

    public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws IOException {
        FileUtils.copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    public static void copyDirectory(File srcDir, File destDir, FileFilter filter2) throws IOException {
        FileUtils.copyDirectory(srcDir, destDir, filter2, true);
    }

    public static void copyDirectory(File srcDir, File destDir, FileFilter filter2, boolean preserveFileDate) throws IOException {
        FileUtils.checkFileRequirements(srcDir, destDir);
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }
        ArrayList<String> exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
            File[] srcFiles;
            File[] fileArray = srcFiles = filter2 == null ? srcDir.listFiles() : srcDir.listFiles(filter2);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<String>(srcFiles.length);
                for (File srcFile : srcFiles) {
                    File copiedFile = new File(destDir, srcFile.getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        FileUtils.doCopyDirectory(srcDir, destDir, filter2, preserveFileDate, exclusionList);
    }

    public static void copyDirectoryToDirectory(File sourceDir, File destinationDir) throws IOException {
        Objects.requireNonNull(sourceDir, "sourceDir");
        if (sourceDir.exists() && !sourceDir.isDirectory()) {
            throw new IllegalArgumentException("Source '" + sourceDir + "' is not a directory");
        }
        Objects.requireNonNull(destinationDir, "destinationDir");
        if (destinationDir.exists() && !destinationDir.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + destinationDir + "' is not a directory");
        }
        FileUtils.copyDirectory(sourceDir, new File(destinationDir, sourceDir.getName()), true);
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        FileUtils.copyFile(srcFile, destFile, true);
    }

    public static void copyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        FileUtils.checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        File parentFile = destFile.getParentFile();
        if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("Destination '" + parentFile + "' directory cannot be created");
        }
        if (destFile.exists() && !destFile.canWrite()) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        FileUtils.doCopyFile(srcFile, destFile, preserveFileDate);
    }

    public static long copyFile(File input, OutputStream output) throws IOException {
        try (FileInputStream fis = new FileInputStream(input);){
            long l = IOUtils.copyLarge(fis, output);
            return l;
        }
    }

    public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
        FileUtils.copyFileToDirectory(srcFile, destDir, true);
    }

    public static void copyFileToDirectory(File sourceFile, File destinationDir, boolean preserveFileDate) throws IOException {
        Objects.requireNonNull(destinationDir, "destinationDir");
        if (destinationDir.exists() && !destinationDir.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + destinationDir + "' is not a directory");
        }
        File destFile = new File(destinationDir, sourceFile.getName());
        FileUtils.copyFile(sourceFile, destFile, preserveFileDate);
    }

    public static void copyInputStreamToFile(InputStream source2, File destination) throws IOException {
        try (InputStream in = source2;){
            FileUtils.copyToFile(in, destination);
        }
    }

    public static void copyToDirectory(File sourceFile, File destinationDir) throws IOException {
        Objects.requireNonNull(sourceFile, "sourceFile");
        if (sourceFile.isFile()) {
            FileUtils.copyFileToDirectory(sourceFile, destinationDir);
        } else if (sourceFile.isDirectory()) {
            FileUtils.copyDirectoryToDirectory(sourceFile, destinationDir);
        } else {
            throw new IOException("The source " + sourceFile + " does not exist");
        }
    }

    public static void copyToDirectory(Iterable<File> sourceIterable, File destinationDir) throws IOException {
        Objects.requireNonNull(sourceIterable, "sourceIterable");
        for (File src : sourceIterable) {
            FileUtils.copyFileToDirectory(src, destinationDir);
        }
    }

    public static void copyToFile(InputStream source2, File destination) throws IOException {
        try (FileOutputStream out = FileUtils.openOutputStream(destination);){
            IOUtils.copy(source2, (OutputStream)out);
        }
    }

    public static void copyURLToFile(URL source2, File destination) throws IOException {
        try (InputStream stream = source2.openStream();){
            FileUtils.copyInputStreamToFile(stream, destination);
        }
    }

    public static void copyURLToFile(URL source2, File destination, int connectionTimeout, int readTimeout) throws IOException {
        URLConnection connection = source2.openConnection();
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);
        try (InputStream stream = connection.getInputStream();){
            FileUtils.copyInputStreamToFile(stream, destination);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static String decodeUrl(String url2) {
        String decoded = url2;
        if (url2 != null && url2.indexOf(37) >= 0) {
            int n = url2.length();
            StringBuilder buffer = new StringBuilder();
            ByteBuffer bytes = ByteBuffer.allocate(n);
            int i = 0;
            while (i < n) {
                if (url2.charAt(i) == '%') {
                    try {
                        do {
                            byte octet = (byte)Integer.parseInt(url2.substring(i + 1, i + 3), 16);
                            bytes.put(octet);
                        } while ((i += 3) < n && url2.charAt(i) == '%');
                        continue;
                    }
                    catch (RuntimeException runtimeException) {
                    }
                    finally {
                        if (bytes.position() <= 0) continue;
                        bytes.flip();
                        buffer.append(StandardCharsets.UTF_8.decode(bytes).toString());
                        bytes.clear();
                        continue;
                    }
                }
                buffer.append(url2.charAt(i++));
            }
            decoded = buffer.toString();
        }
        return decoded;
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        if (!FileUtils.isSymlink(directory)) {
            FileUtils.cleanDirectory(directory);
        }
        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    private static void deleteDirectoryOnExit(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        directory.deleteOnExit();
        if (!FileUtils.isSymlink(directory)) {
            FileUtils.cleanDirectoryOnExit(directory);
        }
    }

    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                FileUtils.cleanDirectory(file);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            return file.delete();
        }
        catch (Exception ignored) {
            return false;
        }
    }

    public static boolean directoryContains(File directory, File child) throws IOException {
        if (directory == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + directory);
        }
        if (child == null) {
            return false;
        }
        if (!directory.exists() || !child.exists()) {
            return false;
        }
        String canonicalParent = directory.getCanonicalPath();
        String canonicalChild = child.getCanonicalPath();
        return FilenameUtils.directoryContains(canonicalParent, canonicalChild);
    }

    private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter2, boolean preserveFileDate, List<String> exclusionList) throws IOException {
        File[] srcFiles;
        File[] fileArray = srcFiles = filter2 == null ? srcDir.listFiles() : srcDir.listFiles(filter2);
        if (srcFiles == null) {
            throw new IOException("Failed to list contents of " + srcDir);
        }
        if (destDir.exists()) {
            if (!destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else if (!destDir.mkdirs() && !destDir.isDirectory()) {
            throw new IOException("Destination '" + destDir + "' directory cannot be created");
        }
        if (!destDir.canWrite()) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        for (File srcFile : srcFiles) {
            File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList != null && exclusionList.contains(srcFile.getCanonicalPath())) continue;
            if (srcFile.isDirectory()) {
                FileUtils.doCopyDirectory(srcFile, dstFile, filter2, preserveFileDate, exclusionList);
                continue;
            }
            FileUtils.doCopyFile(srcFile, dstFile, preserveFileDate);
        }
        if (preserveFileDate) {
            destDir.setLastModified(srcDir.lastModified());
        }
    }

    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }
        Path srcPath = srcFile.toPath();
        Path destPath = destFile.toPath();
        long newLastModifed = preserveFileDate ? srcFile.lastModified() : destFile.lastModified();
        Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
        FileUtils.checkEqualSizes(srcFile, destFile, Files.size(srcPath), Files.size(destPath));
        FileUtils.checkEqualSizes(srcFile, destFile, srcFile.length(), destFile.length());
        destFile.setLastModified(newLastModifed);
    }

    public static void forceDelete(File file) throws IOException {
        Counters.PathCounters deleteCounters;
        try {
            deleteCounters = PathUtils.delete(file.toPath());
        }
        catch (IOException e) {
            throw new IOException("Unable to delete file: " + file, e);
        }
        if (deleteCounters.getFileCounter().get() < 1L && deleteCounters.getDirectoryCounter().get() < 1L) {
            throw new FileNotFoundException("File does not exist: " + file);
        }
    }

    public static void forceDeleteOnExit(File file) throws IOException {
        if (file.isDirectory()) {
            FileUtils.deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                String message = "File " + directory + " exists and is not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            String message = "Unable to create directory " + directory;
            throw new IOException(message);
        }
    }

    public static void forceMkdirParent(File file) throws IOException {
        File parent = file.getParentFile();
        if (parent == null) {
            return;
        }
        FileUtils.forceMkdir(parent);
    }

    public static File getFile(File directory, String ... names) {
        Objects.requireNonNull(directory, "directory");
        Objects.requireNonNull(names, "names");
        File file = directory;
        for (String name : names) {
            file = new File(file, name);
        }
        return file;
    }

    public static File getFile(String ... names) {
        Objects.requireNonNull(names, "names");
        File file = null;
        for (String name : names) {
            file = file == null ? new File(name) : new File(file, name);
        }
        return file;
    }

    public static File getTempDirectory() {
        return new File(FileUtils.getTempDirectoryPath());
    }

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getUserDirectory() {
        return new File(FileUtils.getUserDirectoryPath());
    }

    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }

    private static void innerListFiles(Collection<File> files, File directory, IOFileFilter filter2, boolean includeSubDirectories) {
        File[] found = directory.listFiles(filter2);
        if (found != null) {
            for (File file : found) {
                if (file.isDirectory()) {
                    if (includeSubDirectories) {
                        files.add(file);
                    }
                    FileUtils.innerListFiles(files, file, filter2, includeSubDirectories);
                    continue;
                }
                files.add(file);
            }
        }
    }

    private static Collection<File> innerListFilesOrDirectories(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter, boolean includeSubDirectories) {
        FileUtils.validateListFilesParameters(directory, fileFilter);
        IOFileFilter effFileFilter = FileUtils.setUpEffectiveFileFilter(fileFilter);
        IOFileFilter effDirFilter = FileUtils.setUpEffectiveDirFilter(dirFilter);
        LinkedList<File> files = new LinkedList<File>();
        if (includeSubDirectories) {
            files.add(directory);
        }
        FileUtils.innerListFiles(files, directory, FileFilterUtils.or(effFileFilter, effDirFilter), includeSubDirectories);
        return files;
    }

    public static boolean isFileNewer(File file, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return FileUtils.isFileNewer(file, date.getTime());
    }

    public static boolean isFileNewer(File file, File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!reference.exists()) {
            throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
        }
        return FileUtils.isFileNewer(file, reference.lastModified());
    }

    public static boolean isFileNewer(File file, long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return false;
        }
        return file.lastModified() > timeMillis;
    }

    public static boolean isFileOlder(File file, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return FileUtils.isFileOlder(file, date.getTime());
    }

    public static boolean isFileOlder(File file, File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!reference.exists()) {
            throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
        }
        return FileUtils.isFileOlder(file, reference.lastModified());
    }

    public static boolean isFileOlder(File file, long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return false;
        }
        return file.lastModified() < timeMillis;
    }

    public static boolean isSymlink(File file) {
        Objects.requireNonNull(file, "file");
        return Files.isSymbolicLink(file.toPath());
    }

    public static Iterator<File> iterateFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return FileUtils.listFiles(directory, fileFilter, dirFilter).iterator();
    }

    public static Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive) {
        return FileUtils.listFiles(directory, extensions, recursive).iterator();
    }

    public static Iterator<File> iterateFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return FileUtils.listFilesAndDirs(directory, fileFilter, dirFilter).iterator();
    }

    public static LineIterator lineIterator(File file) throws IOException {
        return FileUtils.lineIterator(file, null);
    }

    public static LineIterator lineIterator(File file, String charsetName) throws IOException {
        FileInputStream inputStream2 = null;
        try {
            inputStream2 = FileUtils.openInputStream(file);
            return IOUtils.lineIterator((InputStream)inputStream2, charsetName);
        }
        catch (IOException | RuntimeException ex) {
            IOUtils.closeQuietly((Closeable)inputStream2, e -> ex.addSuppressed((Throwable)e));
            throw ex;
        }
    }

    public static Collection<File> listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return FileUtils.innerListFilesOrDirectories(directory, fileFilter, dirFilter, false);
    }

    public static Collection<File> listFiles(File directory, String[] extensions, boolean recursive) {
        IOFileFilter filter2;
        if (extensions == null) {
            filter2 = TrueFileFilter.INSTANCE;
        } else {
            String[] suffixes = FileUtils.toSuffixes(extensions);
            filter2 = new SuffixFileFilter(suffixes);
        }
        return FileUtils.listFiles(directory, filter2, recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
    }

    public static Collection<File> listFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return FileUtils.innerListFilesOrDirectories(directory, fileFilter, dirFilter, true);
    }

    public static void moveDirectory(File srcDir, File destDir) throws IOException {
        FileUtils.validateMoveParameters(srcDir, destDir);
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' is not a directory");
        }
        if (destDir.exists()) {
            throw new FileExistsException("Destination '" + destDir + "' already exists");
        }
        boolean rename = srcDir.renameTo(destDir);
        if (!rename) {
            if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath() + File.separator)) {
                throw new IOException("Cannot move directory: " + srcDir + " to a subdirectory of itself: " + destDir);
            }
            FileUtils.copyDirectory(srcDir, destDir);
            FileUtils.deleteDirectory(srcDir);
            if (srcDir.exists()) {
                throw new IOException("Failed to delete original directory '" + srcDir + "' after copy to '" + destDir + "'");
            }
        }
    }

    public static void moveDirectoryToDirectory(File src, File destDir, boolean createDestDir) throws IOException {
        FileUtils.validateMoveParameters(src, destDir);
        if (!destDir.exists() && createDestDir) {
            destDir.mkdirs();
        }
        if (!destDir.exists()) {
            throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
        }
        if (!destDir.isDirectory()) {
            throw new IOException("Destination '" + destDir + "' is not a directory");
        }
        FileUtils.moveDirectory(src, new File(destDir, src.getName()));
    }

    public static void moveFile(File srcFile, File destFile) throws IOException {
        FileUtils.validateMoveParameters(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
        if (destFile.exists()) {
            throw new FileExistsException("Destination '" + destFile + "' already exists");
        }
        if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }
        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            FileUtils.copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                FileUtils.deleteQuietly(destFile);
                throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
            }
        }
    }

    public static void moveFileToDirectory(File srcFile, File destDir, boolean createDestDir) throws IOException {
        FileUtils.validateMoveParameters(srcFile, destDir);
        if (!destDir.exists() && createDestDir) {
            destDir.mkdirs();
        }
        if (!destDir.exists()) {
            throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
        }
        if (!destDir.isDirectory()) {
            throw new IOException("Destination '" + destDir + "' is not a directory");
        }
        FileUtils.moveFile(srcFile, new File(destDir, srcFile.getName()));
    }

    public static void moveToDirectory(File src, File destDir, boolean createDestDir) throws IOException {
        FileUtils.validateMoveParameters(src, destDir);
        if (src.isDirectory()) {
            FileUtils.moveDirectoryToDirectory(src, destDir, createDestDir);
        } else {
            FileUtils.moveFileToDirectory(src, destDir, createDestDir);
        }
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return FileUtils.openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }
        return new FileOutputStream(file, append);
    }

    public static byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream in = FileUtils.openInputStream(file);){
            long fileLength = file.length();
            byte[] byArray = fileLength > 0L ? IOUtils.toByteArray((InputStream)in, fileLength) : IOUtils.toByteArray(in);
            return byArray;
        }
    }

    @Deprecated
    public static String readFileToString(File file) throws IOException {
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }

    public static String readFileToString(File file, Charset charsetName) throws IOException {
        try (FileInputStream in = FileUtils.openInputStream(file);){
            String string = IOUtils.toString((InputStream)in, Charsets.toCharset(charsetName));
            return string;
        }
    }

    public static String readFileToString(File file, String charsetName) throws IOException {
        return FileUtils.readFileToString(file, Charsets.toCharset(charsetName));
    }

    @Deprecated
    public static List<String> readLines(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static List<String> readLines(File file, Charset charset) throws IOException {
        try (FileInputStream in = FileUtils.openInputStream(file);){
            List<String> list = IOUtils.readLines((InputStream)in, Charsets.toCharset(charset));
            return list;
        }
    }

    public static List<String> readLines(File file, String charsetName) throws IOException {
        return FileUtils.readLines(file, Charsets.toCharset(charsetName));
    }

    private static IOFileFilter setUpEffectiveDirFilter(IOFileFilter dirFilter) {
        return dirFilter == null ? FalseFileFilter.INSTANCE : FileFilterUtils.and(dirFilter, DirectoryFileFilter.INSTANCE);
    }

    private static IOFileFilter setUpEffectiveFileFilter(IOFileFilter fileFilter) {
        return FileFilterUtils.and(fileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
    }

    public static long sizeOf(File file) {
        if (!file.exists()) {
            String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (file.isDirectory()) {
            return FileUtils.sizeOfDirectory0(file);
        }
        return file.length();
    }

    private static long sizeOf0(File file) {
        if (file.isDirectory()) {
            return FileUtils.sizeOfDirectory0(file);
        }
        return file.length();
    }

    public static BigInteger sizeOfAsBigInteger(File file) {
        if (!file.exists()) {
            String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (file.isDirectory()) {
            return FileUtils.sizeOfDirectoryBig0(file);
        }
        return BigInteger.valueOf(file.length());
    }

    private static BigInteger sizeOfBig0(File fileOrDir) {
        if (fileOrDir.isDirectory()) {
            return FileUtils.sizeOfDirectoryBig0(fileOrDir);
        }
        return BigInteger.valueOf(fileOrDir.length());
    }

    public static long sizeOfDirectory(File directory) {
        FileUtils.checkDirectory(directory);
        return FileUtils.sizeOfDirectory0(directory);
    }

    private static long sizeOfDirectory0(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return 0L;
        }
        long size = 0L;
        for (File file : files) {
            if (!FileUtils.isSymlink(file) && (size += FileUtils.sizeOf0(file)) < 0L) break;
        }
        return size;
    }

    public static BigInteger sizeOfDirectoryAsBigInteger(File directory) {
        FileUtils.checkDirectory(directory);
        return FileUtils.sizeOfDirectoryBig0(directory);
    }

    private static BigInteger sizeOfDirectoryBig0(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return BigInteger.ZERO;
        }
        BigInteger size = BigInteger.ZERO;
        for (File file : files) {
            if (FileUtils.isSymlink(file)) continue;
            size = size.add(FileUtils.sizeOfBig0(file));
        }
        return size;
    }

    public static File toFile(URL url2) {
        if (url2 == null || !"file".equalsIgnoreCase(url2.getProtocol())) {
            return null;
        }
        String filename = url2.getFile().replace('/', File.separatorChar);
        filename = FileUtils.decodeUrl(filename);
        return new File(filename);
    }

    public static File[] toFiles(URL ... urls2) {
        if (urls2 == null || urls2.length == 0) {
            return EMPTY_FILE_ARRAY;
        }
        File[] files = new File[urls2.length];
        for (int i = 0; i < urls2.length; ++i) {
            URL url2 = urls2[i];
            if (url2 == null) continue;
            if (!url2.getProtocol().equals("file")) {
                throw new IllegalArgumentException("URL could not be converted to a File: " + url2);
            }
            files[i] = FileUtils.toFile(url2);
        }
        return files;
    }

    private static String[] toSuffixes(String ... extensions) {
        String[] suffixes = new String[extensions.length];
        for (int i = 0; i < extensions.length; ++i) {
            suffixes[i] = "." + extensions[i];
        }
        return suffixes;
    }

    public static void touch(File file) throws IOException {
        boolean success;
        if (!file.exists()) {
            FileUtils.openOutputStream(file).close();
        }
        if (!(success = file.setLastModified(System.currentTimeMillis()))) {
            throw new IOException("Unable to set the last modification time for " + file);
        }
    }

    public static URL[] toURLs(File ... files) throws IOException {
        URL[] urls2 = new URL[files.length];
        for (int i = 0; i < urls2.length; ++i) {
            urls2[i] = files[i].toURI().toURL();
        }
        return urls2;
    }

    private static void validateListFilesParameters(File directory, IOFileFilter fileFilter) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Parameter 'directory' is not a directory: " + directory);
        }
        Objects.requireNonNull(fileFilter, "fileFilter");
    }

    private static void validateMoveParameters(File source2, File destination) throws FileNotFoundException {
        Objects.requireNonNull(source2, "source");
        Objects.requireNonNull(destination, "destination");
        if (!source2.exists()) {
            throw new FileNotFoundException("Source '" + source2 + "' does not exist");
        }
    }

    private static File[] verifiedListFiles(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean waitFor(File file, int seconds) {
        long finishAt = System.currentTimeMillis() + (long)seconds * 1000L;
        boolean wasInterrupted = false;
        try {
            while (!file.exists()) {
                long remaining = finishAt - System.currentTimeMillis();
                if (remaining < 0L) {
                    boolean bl = false;
                    return bl;
                }
                try {
                    Thread.sleep(Math.min(100L, remaining));
                }
                catch (InterruptedException ignore) {
                    wasInterrupted = true;
                }
                catch (Exception ex) {
                    break;
                }
            }
        }
        finally {
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }

    @Deprecated
    public static void write(File file, CharSequence data) throws IOException {
        FileUtils.write(file, data, Charset.defaultCharset(), false);
    }

    @Deprecated
    public static void write(File file, CharSequence data, boolean append) throws IOException {
        FileUtils.write(file, data, Charset.defaultCharset(), append);
    }

    public static void write(File file, CharSequence data, Charset charset) throws IOException {
        FileUtils.write(file, data, charset, false);
    }

    public static void write(File file, CharSequence data, Charset charset, boolean append) throws IOException {
        String str = data == null ? null : data.toString();
        FileUtils.writeStringToFile(file, str, charset, append);
    }

    public static void write(File file, CharSequence data, String charsetName) throws IOException {
        FileUtils.write(file, data, charsetName, false);
    }

    public static void write(File file, CharSequence data, String charsetName, boolean append) throws IOException {
        FileUtils.write(file, data, Charsets.toCharset(charsetName), append);
    }

    public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
        FileUtils.writeByteArrayToFile(file, data, false);
    }

    public static void writeByteArrayToFile(File file, byte[] data, boolean append) throws IOException {
        FileUtils.writeByteArrayToFile(file, data, 0, data.length, append);
    }

    public static void writeByteArrayToFile(File file, byte[] data, int off, int len) throws IOException {
        FileUtils.writeByteArrayToFile(file, data, off, len, false);
    }

    public static void writeByteArrayToFile(File file, byte[] data, int off, int len, boolean append) throws IOException {
        try (FileOutputStream out = FileUtils.openOutputStream(file, append);){
            ((OutputStream)out).write(data, off, len);
        }
    }

    public static void writeLines(File file, Collection<?> lines) throws IOException {
        FileUtils.writeLines(file, null, lines, null, false);
    }

    public static void writeLines(File file, Collection<?> lines, boolean append) throws IOException {
        FileUtils.writeLines(file, null, lines, null, append);
    }

    public static void writeLines(File file, Collection<?> lines, String lineEnding) throws IOException {
        FileUtils.writeLines(file, null, lines, lineEnding, false);
    }

    public static void writeLines(File file, Collection<?> lines, String lineEnding, boolean append) throws IOException {
        FileUtils.writeLines(file, null, lines, lineEnding, append);
    }

    public static void writeLines(File file, String charsetName, Collection<?> lines) throws IOException {
        FileUtils.writeLines(file, charsetName, lines, null, false);
    }

    public static void writeLines(File file, String charsetName, Collection<?> lines, boolean append) throws IOException {
        FileUtils.writeLines(file, charsetName, lines, null, append);
    }

    public static void writeLines(File file, String charsetName, Collection<?> lines, String lineEnding) throws IOException {
        FileUtils.writeLines(file, charsetName, lines, lineEnding, false);
    }

    public static void writeLines(File file, String charsetName, Collection<?> lines, String lineEnding, boolean append) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(FileUtils.openOutputStream(file, append));){
            IOUtils.writeLines(lines, lineEnding, (OutputStream)out, charsetName);
        }
    }

    @Deprecated
    public static void writeStringToFile(File file, String data) throws IOException {
        FileUtils.writeStringToFile(file, data, Charset.defaultCharset(), false);
    }

    @Deprecated
    public static void writeStringToFile(File file, String data, boolean append) throws IOException {
        FileUtils.writeStringToFile(file, data, Charset.defaultCharset(), append);
    }

    public static void writeStringToFile(File file, String data, Charset charset) throws IOException {
        FileUtils.writeStringToFile(file, data, charset, false);
    }

    public static void writeStringToFile(File file, String data, Charset charset, boolean append) throws IOException {
        try (FileOutputStream out = FileUtils.openOutputStream(file, append);){
            IOUtils.write(data, (OutputStream)out, charset);
        }
    }

    public static void writeStringToFile(File file, String data, String charsetName) throws IOException {
        FileUtils.writeStringToFile(file, data, charsetName, false);
    }

    public static void writeStringToFile(File file, String data, String charsetName, boolean append) throws IOException {
        FileUtils.writeStringToFile(file, data, Charsets.toCharset(charsetName), append);
    }
}

