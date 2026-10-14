/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.BitUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

public class FileUtils {
    public static final byte[] DEFAULT_POSIX_FILE_ATTRIBUTES = new byte[]{0, 0, -92, -127};
    public static final byte[] DEFAULT_POSIX_FOLDER_ATTRIBUTES = new byte[]{0, 0, -19, 65};

    public static void setFileAttributes(Path file, byte[] fileAttributes) {
        if (fileAttributes == null || fileAttributes.length == 0) {
            return;
        }
        if (FileUtils.isWindows()) {
            FileUtils.applyWindowsFileAttributes(file, fileAttributes);
        } else if (FileUtils.isMac() || FileUtils.isUnix()) {
            FileUtils.applyPosixFileAttributes(file, fileAttributes);
        }
    }

    public static void setFileLastModifiedTime(Path file, long lastModifiedTime) {
        if (lastModifiedTime <= 0L || !Files.exists(file, new LinkOption[0])) {
            return;
        }
        try {
            Files.setLastModifiedTime(file, FileTime.fromMillis(Zip4jUtil.dosToExtendedEpochTme(lastModifiedTime)));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void setFileLastModifiedTimeWithoutNio(File file, long lastModifiedTime) {
        file.setLastModified(Zip4jUtil.dosToExtendedEpochTme(lastModifiedTime));
    }

    public static byte[] getFileAttributes(File file) {
        try {
            if (file == null || !Files.isSymbolicLink(file.toPath()) && !file.exists()) {
                return new byte[4];
            }
            Path path = file.toPath();
            if (FileUtils.isWindows()) {
                return FileUtils.getWindowsFileAttributes(path);
            }
            if (FileUtils.isMac() || FileUtils.isUnix()) {
                return FileUtils.getPosixFileAttributes(path);
            }
            return new byte[4];
        }
        catch (NoSuchMethodError e) {
            return new byte[4];
        }
    }

    public static List<File> getFilesInDirectoryRecursive(File path, ZipParameters zipParameters) throws ZipException {
        if (path == null) {
            throw new ZipException("input path is null, cannot read files in the directory");
        }
        ArrayList<File> result = new ArrayList<File>();
        File[] filesAndDirs = path.listFiles();
        if (!path.isDirectory() || !path.canRead() || filesAndDirs == null) {
            return result;
        }
        for (File file : filesAndDirs) {
            if (zipParameters.getExcludeFileFilter() != null && zipParameters.getExcludeFileFilter().isExcluded(file) || file.isHidden() && !zipParameters.isReadHiddenFiles()) continue;
            result.add(file);
            ZipParameters.SymbolicLinkAction symbolicLinkAction = zipParameters.getSymbolicLinkAction();
            boolean isSymLink = FileUtils.isSymbolicLink(file);
            if ((!isSymLink || ZipParameters.SymbolicLinkAction.INCLUDE_LINK_ONLY.equals((Object)symbolicLinkAction)) && (isSymLink || !file.isDirectory())) continue;
            result.addAll(FileUtils.getFilesInDirectoryRecursive(file, zipParameters));
        }
        return result;
    }

    public static String getFileNameWithoutExtension(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) {
            return fileName;
        }
        return fileName.substring(0, pos);
    }

    public static String getZipFileNameWithoutExtension(String zipFile) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(zipFile)) {
            throw new ZipException("zip file name is empty or null, cannot determine zip file name");
        }
        String tmpFileName = zipFile;
        if (zipFile.contains(System.getProperty("file.separator"))) {
            tmpFileName = zipFile.substring(zipFile.lastIndexOf(System.getProperty("file.separator")) + 1);
        }
        if (tmpFileName.endsWith(".zip")) {
            tmpFileName = tmpFileName.substring(0, tmpFileName.lastIndexOf("."));
        }
        return tmpFileName;
    }

    public static List<File> getSplitZipFiles(ZipModel zipModel) throws ZipException {
        if (zipModel == null) {
            throw new ZipException("cannot get split zip files: zipmodel is null");
        }
        if (zipModel.getEndOfCentralDirectoryRecord() == null) {
            return null;
        }
        if (!zipModel.getZipFile().exists()) {
            throw new ZipException("zip file does not exist");
        }
        ArrayList<File> splitZipFiles = new ArrayList<File>();
        File currZipFile = zipModel.getZipFile();
        if (!zipModel.isSplitArchive()) {
            splitZipFiles.add(currZipFile);
            return splitZipFiles;
        }
        int numberOfThisDisk = zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk();
        if (numberOfThisDisk == 0) {
            splitZipFiles.add(currZipFile);
            return splitZipFiles;
        }
        for (int i = 0; i <= numberOfThisDisk; ++i) {
            if (i == numberOfThisDisk) {
                splitZipFiles.add(zipModel.getZipFile());
                continue;
            }
            String fileExt = ".z0";
            if (i >= 9) {
                fileExt = ".z";
            }
            String partFile = currZipFile.getName().contains(".") ? currZipFile.getPath().substring(0, currZipFile.getPath().lastIndexOf(".")) : currZipFile.getPath();
            partFile = partFile + fileExt + (i + 1);
            splitZipFiles.add(new File(partFile));
        }
        return splitZipFiles;
    }

    public static String getRelativeFileName(File fileToAdd, ZipParameters zipParameters) throws ZipException {
        String fileName;
        try {
            String fileCanonicalPath = fileToAdd.getCanonicalPath();
            if (Zip4jUtil.isStringNotNullAndNotEmpty(zipParameters.getDefaultFolderPath())) {
                File tmpFile;
                String tmpFileName;
                File rootFolderFile = new File(zipParameters.getDefaultFolderPath());
                String rootFolderFileRef = rootFolderFile.getCanonicalPath();
                if (!rootFolderFileRef.endsWith(InternalZipConstants.FILE_SEPARATOR)) {
                    rootFolderFileRef = rootFolderFileRef + InternalZipConstants.FILE_SEPARATOR;
                }
                if (FileUtils.isSymbolicLink(fileToAdd)) {
                    String rootPath = new File(fileToAdd.getParentFile().getCanonicalFile().getPath() + File.separator + fileToAdd.getCanonicalFile().getName()).getPath();
                    tmpFileName = rootPath.substring(rootFolderFileRef.length());
                } else {
                    tmpFileName = !fileToAdd.getCanonicalFile().getPath().startsWith(rootFolderFileRef) ? fileToAdd.getCanonicalFile().getParentFile().getName() + InternalZipConstants.FILE_SEPARATOR + fileToAdd.getCanonicalFile().getName() : fileCanonicalPath.substring(rootFolderFileRef.length());
                }
                if (tmpFileName.startsWith(System.getProperty("file.separator"))) {
                    tmpFileName = tmpFileName.substring(1);
                }
                if ((tmpFile = new File(fileCanonicalPath)).isDirectory()) {
                    tmpFileName = tmpFileName.replaceAll("\\\\", "/");
                    tmpFileName = tmpFileName + "/";
                } else {
                    String bkFileName = tmpFileName.substring(0, tmpFileName.lastIndexOf(tmpFile.getName()));
                    bkFileName = bkFileName.replaceAll("\\\\", "/");
                    tmpFileName = bkFileName + FileUtils.getNameOfFileInZip(tmpFile, zipParameters.getFileNameInZip());
                }
                fileName = tmpFileName;
            } else {
                File relFile = new File(fileCanonicalPath);
                fileName = FileUtils.getNameOfFileInZip(relFile, zipParameters.getFileNameInZip());
                if (relFile.isDirectory()) {
                    fileName = fileName + "/";
                }
            }
        }
        catch (IOException e) {
            throw new ZipException(e);
        }
        String rootFolderNameInZip = zipParameters.getRootFolderNameInZip();
        if (Zip4jUtil.isStringNotNullAndNotEmpty(rootFolderNameInZip)) {
            if (!rootFolderNameInZip.endsWith("\\") && !rootFolderNameInZip.endsWith("/")) {
                rootFolderNameInZip = rootFolderNameInZip + InternalZipConstants.FILE_SEPARATOR;
            }
            rootFolderNameInZip = rootFolderNameInZip.replaceAll("\\\\", "/");
            fileName = rootFolderNameInZip + fileName;
        }
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
            String errorMessage = "fileName to add to zip is empty or null. fileName: '" + fileName + "' DefaultFolderPath: '" + zipParameters.getDefaultFolderPath() + "' FileNameInZip: " + zipParameters.getFileNameInZip();
            if (FileUtils.isSymbolicLink(fileToAdd)) {
                errorMessage = errorMessage + "isSymlink: true ";
            }
            if (Zip4jUtil.isStringNotNullAndNotEmpty(rootFolderNameInZip)) {
                errorMessage = "rootFolderNameInZip: '" + rootFolderNameInZip + "' ";
            }
            throw new ZipException(errorMessage);
        }
        return fileName;
    }

    private static String getNameOfFileInZip(File fileToAdd, String fileNameInZip) throws IOException {
        if (Zip4jUtil.isStringNotNullAndNotEmpty(fileNameInZip)) {
            return fileNameInZip;
        }
        if (FileUtils.isSymbolicLink(fileToAdd)) {
            return fileToAdd.toPath().toRealPath(LinkOption.NOFOLLOW_LINKS).getFileName().toString();
        }
        return fileToAdd.getName();
    }

    public static boolean isZipEntryDirectory(String fileNameInZip) {
        return fileNameInZip.endsWith("/") || fileNameInZip.endsWith("\\");
    }

    public static void copyFile(RandomAccessFile randomAccessFile, OutputStream outputStream2, long start2, long end, ProgressMonitor progressMonitor, int bufferSize) throws ZipException {
        if (start2 < 0L || end < 0L || start2 > end) {
            throw new ZipException("invalid offsets");
        }
        if (start2 == end) {
            return;
        }
        try {
            int readLen;
            randomAccessFile.seek(start2);
            long bytesRead = 0L;
            long bytesToRead = end - start2;
            byte[] buff = end - start2 < (long)bufferSize ? new byte[(int)bytesToRead] : new byte[bufferSize];
            while ((readLen = randomAccessFile.read(buff)) != -1) {
                outputStream2.write(buff, 0, readLen);
                progressMonitor.updateWorkCompleted(readLen);
                if (progressMonitor.isCancelAllTasks()) {
                    progressMonitor.setResult(ProgressMonitor.Result.CANCELLED);
                    return;
                }
                if ((bytesRead += (long)readLen) != bytesToRead) {
                    if (bytesRead + (long)buff.length <= bytesToRead) continue;
                    buff = new byte[(int)(bytesToRead - bytesRead)];
                    continue;
                }
                break;
            }
        }
        catch (IOException e) {
            throw new ZipException(e);
        }
    }

    public static void assertFilesExist(List<File> files, ZipParameters.SymbolicLinkAction symLinkAction) throws ZipException {
        for (File file : files) {
            if (FileUtils.isSymbolicLink(file)) {
                if (!symLinkAction.equals((Object)ZipParameters.SymbolicLinkAction.INCLUDE_LINK_AND_LINKED_FILE) && !symLinkAction.equals((Object)ZipParameters.SymbolicLinkAction.INCLUDE_LINKED_FILE_ONLY)) continue;
                FileUtils.assertSymbolicLinkTargetExists(file);
                continue;
            }
            FileUtils.assertFileExists(file);
        }
    }

    public static boolean isNumberedSplitFile(File file) {
        return file.getName().endsWith(".zip.001");
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (!fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static File[] getAllSortedNumberedSplitFiles(File firstNumberedFile) {
        final String zipFileNameWithoutExtension = FileUtils.getFileNameWithoutExtension(firstNumberedFile.getName());
        Object[] allSplitFiles = firstNumberedFile.getParentFile().listFiles(new FilenameFilter(){

            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(zipFileNameWithoutExtension + ".");
            }
        });
        if (allSplitFiles == null) {
            return new File[0];
        }
        Arrays.sort(allSplitFiles);
        return allSplitFiles;
    }

    public static String getNextNumberedSplitFileCounterAsExtension(int index) {
        return "." + FileUtils.getExtensionZerosPrefix(index) + (index + 1);
    }

    public static boolean isSymbolicLink(File file) {
        try {
            return Files.isSymbolicLink(file.toPath());
        }
        catch (Error | Exception e) {
            return false;
        }
    }

    public static String readSymbolicLink(File file) {
        try {
            return Files.readSymbolicLink(file.toPath()).toString();
        }
        catch (Error | Exception e) {
            return "";
        }
    }

    public static byte[] getDefaultFileAttributes(boolean isDirectory) {
        byte[] permissions = new byte[4];
        if (FileUtils.isUnix() || FileUtils.isMac()) {
            if (isDirectory) {
                System.arraycopy(DEFAULT_POSIX_FOLDER_ATTRIBUTES, 0, permissions, 0, permissions.length);
            } else {
                System.arraycopy(DEFAULT_POSIX_FILE_ATTRIBUTES, 0, permissions, 0, permissions.length);
            }
        } else if (FileUtils.isWindows() && isDirectory) {
            permissions[0] = BitUtils.setBit(permissions[0], 4);
        }
        return permissions;
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac");
    }

    public static boolean isUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("nux");
    }

    private static String getExtensionZerosPrefix(int index) {
        if (index < 9) {
            return "00";
        }
        if (index < 99) {
            return "0";
        }
        return "";
    }

    private static void applyWindowsFileAttributes(Path file, byte[] fileAttributes) {
        if (fileAttributes[0] == 0) {
            return;
        }
        DosFileAttributeView fileAttributeView = Files.getFileAttributeView(file, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        if (fileAttributeView == null) {
            return;
        }
        try {
            fileAttributeView.setReadOnly(BitUtils.isBitSet(fileAttributes[0], 0));
            fileAttributeView.setHidden(BitUtils.isBitSet(fileAttributes[0], 1));
            fileAttributeView.setSystem(BitUtils.isBitSet(fileAttributes[0], 2));
            fileAttributeView.setArchive(BitUtils.isBitSet(fileAttributes[0], 5));
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private static void applyPosixFileAttributes(Path file, byte[] fileAttributes) {
        if (fileAttributes[2] == 0 && fileAttributes[3] == 0) {
            return;
        }
        try {
            HashSet<PosixFilePermission> posixFilePermissions = new HashSet<PosixFilePermission>();
            FileUtils.addIfBitSet(fileAttributes[3], 0, posixFilePermissions, PosixFilePermission.OWNER_READ);
            FileUtils.addIfBitSet(fileAttributes[2], 7, posixFilePermissions, PosixFilePermission.OWNER_WRITE);
            FileUtils.addIfBitSet(fileAttributes[2], 6, posixFilePermissions, PosixFilePermission.OWNER_EXECUTE);
            FileUtils.addIfBitSet(fileAttributes[2], 5, posixFilePermissions, PosixFilePermission.GROUP_READ);
            FileUtils.addIfBitSet(fileAttributes[2], 4, posixFilePermissions, PosixFilePermission.GROUP_WRITE);
            FileUtils.addIfBitSet(fileAttributes[2], 3, posixFilePermissions, PosixFilePermission.GROUP_EXECUTE);
            FileUtils.addIfBitSet(fileAttributes[2], 2, posixFilePermissions, PosixFilePermission.OTHERS_READ);
            FileUtils.addIfBitSet(fileAttributes[2], 1, posixFilePermissions, PosixFilePermission.OTHERS_WRITE);
            FileUtils.addIfBitSet(fileAttributes[2], 0, posixFilePermissions, PosixFilePermission.OTHERS_EXECUTE);
            Files.setPosixFilePermissions(file, posixFilePermissions);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private static byte[] getWindowsFileAttributes(Path file) {
        byte[] fileAttributes = new byte[4];
        try {
            DosFileAttributeView dosFileAttributeView = Files.getFileAttributeView(file, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            if (dosFileAttributeView == null) {
                return fileAttributes;
            }
            DosFileAttributes dosFileAttributes = dosFileAttributeView.readAttributes();
            byte windowsAttribute = 0;
            windowsAttribute = FileUtils.setBitIfApplicable(dosFileAttributes.isReadOnly(), windowsAttribute, 0);
            windowsAttribute = FileUtils.setBitIfApplicable(dosFileAttributes.isHidden(), windowsAttribute, 1);
            windowsAttribute = FileUtils.setBitIfApplicable(dosFileAttributes.isSystem(), windowsAttribute, 2);
            windowsAttribute = FileUtils.setBitIfApplicable(dosFileAttributes.isDirectory(), windowsAttribute, 4);
            fileAttributes[0] = windowsAttribute = FileUtils.setBitIfApplicable(dosFileAttributes.isArchive(), windowsAttribute, 5);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return fileAttributes;
    }

    private static void assertFileExists(File file) throws ZipException {
        if (!file.exists()) {
            throw new ZipException("File does not exist: " + file);
        }
    }

    private static void assertSymbolicLinkTargetExists(File file) throws ZipException {
        if (!file.exists()) {
            throw new ZipException("Symlink target '" + FileUtils.readSymbolicLink(file) + "' does not exist for link '" + file + "'");
        }
    }

    private static byte[] getPosixFileAttributes(Path file) {
        byte[] fileAttributes = new byte[4];
        try {
            PosixFileAttributeView posixFileAttributeView = Files.getFileAttributeView(file, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            Set<PosixFilePermission> posixFilePermissions = posixFileAttributeView.readAttributes().permissions();
            boolean isSymlink = Files.isSymbolicLink(file);
            if (isSymlink) {
                fileAttributes[3] = BitUtils.setBit(fileAttributes[3], 7);
                fileAttributes[3] = BitUtils.unsetBit(fileAttributes[3], 6);
            } else {
                fileAttributes[3] = FileUtils.setBitIfApplicable(Files.isRegularFile(file, new LinkOption[0]), fileAttributes[3], 7);
                fileAttributes[3] = FileUtils.setBitIfApplicable(Files.isDirectory(file, new LinkOption[0]), fileAttributes[3], 6);
            }
            fileAttributes[3] = FileUtils.setBitIfApplicable(isSymlink, fileAttributes[3], 5);
            fileAttributes[3] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.OWNER_READ), fileAttributes[3], 0);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.OWNER_WRITE), fileAttributes[2], 7);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.OWNER_EXECUTE), fileAttributes[2], 6);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.GROUP_READ), fileAttributes[2], 5);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.GROUP_WRITE), fileAttributes[2], 4);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.GROUP_EXECUTE), fileAttributes[2], 3);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.OTHERS_READ), fileAttributes[2], 2);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.OTHERS_WRITE), fileAttributes[2], 1);
            fileAttributes[2] = FileUtils.setBitIfApplicable(posixFilePermissions.contains((Object)PosixFilePermission.OTHERS_EXECUTE), fileAttributes[2], 0);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return fileAttributes;
    }

    private static byte setBitIfApplicable(boolean applicable, byte b, int pos) {
        if (applicable) {
            b = BitUtils.setBit(b, pos);
        }
        return b;
    }

    private static void addIfBitSet(byte b, int pos, Set<PosixFilePermission> posixFilePermissions, PosixFilePermission posixFilePermissionToAdd) {
        if (BitUtils.isBitSet(b, pos)) {
            posixFilePermissions.add(posixFilePermissionToAdd);
        }
    }
}

