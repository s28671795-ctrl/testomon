/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.windowsshare;

import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.smbj.common.SmbPath;
import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import com.incoresoft.plugin.commons.windowsshare.SmbFileInputStream;
import com.incoresoft.plugin.commons.windowsshare.WindowsShareUtils;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public class WindowsShareClient {
    private static final Set<String> VIDEO_FILE_FORMATS = Set.of("264", "3GP", "ASF", "AVI", "DAV", "DIVX", "DVR", "FLV", "GE5", "MKV", "MOV", "MP4", "MPEG4", "RT4", "TS", "WMV", "XBA");
    private final String hostname;
    private final String username;
    private final String password;
    private final String domain;
    private final String shareName;

    public WindowsShareClient(@NotNull WindowsShareConfig config) {
        this(config.getHostname(), config.getUsername(), config.getPassword(), config.getDomain(), config.getShareName());
    }

    public WindowsShareClient(@NotNull SmbPath path, @NotNull String username, @NotNull String password, @NotNull String domain) {
        this(path.getHostname(), username, password, domain, path.getShareName());
    }

    public WindowsShareClient(@NotNull String hostname, @NotNull String username, @NotNull String password, @NotNull String domain, @NotNull String shareName) {
        this.hostname = Objects.requireNonNull(hostname);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.domain = Objects.requireNonNull(domain);
        this.shareName = Objects.requireNonNull(shareName);
    }

    public List<FileIdBothDirectoryInformation> getDirectories(@NotNull String path) {
        return this.getFiles(path, file -> !file.getFileName().equals(".") && !file.getFileName().equals("..") && WindowsShareUtils.isDirectory(file));
    }

    public List<FileIdBothDirectoryInformation> getVideoFiles(@NotNull String path) {
        return this.getFiles(path, it -> WindowsShareUtils.ofFormat(it, VIDEO_FILE_FORMATS));
    }

    public List<FileIdBothDirectoryInformation> getFiles(@NotNull String path) {
        return this.getFiles(path, it -> true);
    }

    /*
     * Exception decompiling
     */
    public List<FileIdBothDirectoryInformation> getFiles(@NotNull String path, Predicate<FileIdBothDirectoryInformation> filter) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 24[DOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
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

    public InputStream open(String path) {
        return new SmbFileInputStream(new WindowsShareConfigImpl(), path);
    }

    private class WindowsShareConfigImpl
    implements WindowsShareConfig {
        private WindowsShareConfigImpl() {
        }

        @Override
        public Integer getId() {
            return null;
        }

        @Override
        public String getHostname() {
            return WindowsShareClient.this.hostname;
        }

        @Override
        public String getUsername() {
            return WindowsShareClient.this.username;
        }

        @Override
        public String getPassword() {
            return WindowsShareClient.this.password;
        }

        @Override
        public String getDomain() {
            return WindowsShareClient.this.domain;
        }

        @Override
        public String getShareName() {
            return WindowsShareClient.this.shareName;
        }
    }
}

