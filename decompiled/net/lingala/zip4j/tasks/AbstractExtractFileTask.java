/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.regex.Matcher;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.BitUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.UnzipUtil;
import net.lingala.zip4j.util.Zip4jUtil;

public abstract class AbstractExtractFileTask<T>
extends AsyncZipTask<T> {
    private final ZipModel zipModel;
    private final UnzipParameters unzipParameters;

    public AbstractExtractFileTask(ZipModel zipModel, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.unzipParameters = unzipParameters;
    }

    protected void extractFile(ZipInputStream zipInputStream, FileHeader fileHeader, String outputPath, String newFileName, ProgressMonitor progressMonitor, byte[] readBuff) throws IOException {
        boolean isSymbolicLink = this.isSymbolicLink(fileHeader);
        if (isSymbolicLink && !this.unzipParameters.isExtractSymbolicLinks()) {
            return;
        }
        if (!outputPath.endsWith(InternalZipConstants.FILE_SEPARATOR)) {
            outputPath = outputPath + InternalZipConstants.FILE_SEPARATOR;
        }
        File outputFile = this.determineOutputFile(fileHeader, outputPath, newFileName);
        progressMonitor.setFileName(outputFile.getAbsolutePath());
        this.assertCanonicalPathsAreSame(outputFile, outputPath, fileHeader);
        this.verifyNextEntry(zipInputStream, fileHeader);
        if (fileHeader.isDirectory()) {
            if (!outputFile.exists() && !outputFile.mkdirs()) {
                throw new ZipException("Could not create directory: " + outputFile);
            }
        } else if (isSymbolicLink) {
            this.createSymLink(zipInputStream, fileHeader, outputFile, progressMonitor);
        } else {
            this.checkOutputDirectoryStructure(outputFile);
            this.unzipFile(zipInputStream, outputFile, progressMonitor, readBuff);
        }
        if (!isSymbolicLink) {
            UnzipUtil.applyFileAttributes(fileHeader, outputFile);
        }
    }

    private void assertCanonicalPathsAreSame(File outputFile, String outputPath, FileHeader fileHeader) throws IOException {
        String outputCanonicalPath;
        String outputFileCanonicalPath = outputFile.getCanonicalPath();
        if (outputFile.isDirectory() && !outputFileCanonicalPath.endsWith(InternalZipConstants.FILE_SEPARATOR)) {
            outputFileCanonicalPath = outputFileCanonicalPath + InternalZipConstants.FILE_SEPARATOR;
        }
        if (!(outputCanonicalPath = new File(outputPath).getCanonicalPath()).endsWith(InternalZipConstants.FILE_SEPARATOR)) {
            outputCanonicalPath = outputCanonicalPath + InternalZipConstants.FILE_SEPARATOR;
        }
        if (!outputFileCanonicalPath.startsWith(outputCanonicalPath)) {
            throw new ZipException("illegal file name that breaks out of the target directory: " + fileHeader.getFileName());
        }
    }

    private boolean isSymbolicLink(FileHeader fileHeader) {
        byte[] externalFileAttributes = fileHeader.getExternalFileAttributes();
        if (externalFileAttributes == null || externalFileAttributes.length < 4) {
            return false;
        }
        return BitUtils.isBitSet(externalFileAttributes[3], 5);
    }

    private void unzipFile(ZipInputStream inputStream2, File outputFile, ProgressMonitor progressMonitor, byte[] buff) throws IOException {
        try (FileOutputStream outputStream2 = new FileOutputStream(outputFile);){
            int readLength;
            while ((readLength = inputStream2.read(buff)) != -1) {
                ((OutputStream)outputStream2).write(buff, 0, readLength);
                progressMonitor.updateWorkCompleted(readLength);
                this.verifyIfTaskIsCancelled();
            }
        }
        catch (Exception e) {
            if (outputFile.exists()) {
                outputFile.delete();
            }
            throw e;
        }
    }

    private void createSymLink(ZipInputStream zipInputStream, FileHeader fileHeader, File outputFile, ProgressMonitor progressMonitor) throws IOException {
        String symLinkPath = new String(this.readCompleteEntry(zipInputStream, fileHeader, progressMonitor));
        if (!outputFile.getParentFile().exists() && !outputFile.getParentFile().mkdirs()) {
            throw new ZipException("Could not create parent directories");
        }
        try {
            Path linkTarget = Paths.get(symLinkPath, new String[0]);
            if (outputFile.exists() && !outputFile.delete()) {
                throw new ZipException("Could not delete existing symlink " + outputFile);
            }
            Files.createSymbolicLink(outputFile.toPath(), linkTarget, new FileAttribute[0]);
        }
        catch (NoSuchMethodError error) {
            try (FileOutputStream outputStream2 = new FileOutputStream(outputFile);){
                ((OutputStream)outputStream2).write(symLinkPath.getBytes());
            }
        }
    }

    private byte[] readCompleteEntry(ZipInputStream zipInputStream, FileHeader fileHeader, ProgressMonitor progressMonitor) throws IOException {
        byte[] b = new byte[(int)fileHeader.getUncompressedSize()];
        int readLength = zipInputStream.read(b);
        if (readLength != b.length) {
            throw new ZipException("Could not read complete entry");
        }
        progressMonitor.updateWorkCompleted(b.length);
        return b;
    }

    private void verifyNextEntry(ZipInputStream zipInputStream, FileHeader fileHeader) throws IOException {
        if (BitUtils.isBitSet(fileHeader.getGeneralPurposeFlag()[0], 6)) {
            throw new ZipException("Entry with name " + fileHeader.getFileName() + " is encrypted with Strong Encryption. Zip4j does not support Strong Encryption, as this is patented.");
        }
        LocalFileHeader localFileHeader = zipInputStream.getNextEntry(fileHeader, false);
        if (localFileHeader == null) {
            throw new ZipException("Could not read corresponding local file header for file header: " + fileHeader.getFileName());
        }
        if (!fileHeader.getFileName().equals(localFileHeader.getFileName())) {
            throw new ZipException("File header and local file header mismatch");
        }
    }

    private void checkOutputDirectoryStructure(File outputFile) throws ZipException {
        if (!outputFile.getParentFile().exists() && !outputFile.getParentFile().mkdirs()) {
            throw new ZipException("Unable to create parent directories: " + outputFile.getParentFile());
        }
    }

    private File determineOutputFile(FileHeader fileHeader, String outputPath, String newFileName) {
        String outputFileName = fileHeader.getFileName();
        if (Zip4jUtil.isStringNotNullAndNotEmpty(newFileName)) {
            outputFileName = newFileName;
        }
        return new File(outputPath, this.getFileNameWithSystemFileSeparators(outputFileName));
    }

    private String getFileNameWithSystemFileSeparators(String fileNameToReplace) {
        String formattedFileName = fileNameToReplace.replaceAll(":\\\\", "_");
        return formattedFileName.replaceAll("[/\\\\]", Matcher.quoteReplacement(InternalZipConstants.FILE_SEPARATOR));
    }

    @Override
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.EXTRACT_ENTRY;
    }

    public ZipModel getZipModel() {
        return this.zipModel;
    }
}

