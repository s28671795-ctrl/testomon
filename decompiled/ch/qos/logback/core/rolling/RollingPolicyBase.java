/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.rolling;

import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.helper.CompressionMode;
import ch.qos.logback.core.rolling.helper.FileNamePattern;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.util.Loader;

public abstract class RollingPolicyBase
extends ContextAwareBase
implements RollingPolicy {
    protected CompressionMode compressionMode = CompressionMode.NONE;
    FileNamePattern fileNamePattern;
    protected String fileNamePatternStr;
    private FileAppender<?> parent;
    FileNamePattern zipEntryFileNamePattern;
    private boolean started;

    protected void determineCompressionMode() {
        if (this.fileNamePatternStr.endsWith(CompressionMode.GZ_SUFFIX)) {
            this.addInfo("Will use gz compression");
            this.compressionMode = CompressionMode.GZ;
        } else if (this.fileNamePatternStr.endsWith(CompressionMode.ZIP_SUFFIX)) {
            this.addInfo("Will use zip compression");
            this.compressionMode = CompressionMode.ZIP;
        } else if (this.fileNamePatternStr.endsWith(CompressionMode.XZ_SUFFIX)) {
            this.addInfo("Will use xz compression");
            this.compressionMode = CompressionMode.XZ;
        } else {
            this.addInfo("No compression will be used");
            this.compressionMode = CompressionMode.NONE;
        }
    }

    protected void adjustCompressionModeAndFileNamePatternStrIfNecessary() {
        boolean xzLibraryLoadable;
        if (this.compressionMode == CompressionMode.XZ && !(xzLibraryLoadable = Loader.isClassLoadable("org.tukaani.xz.XZOutputStream", this.getContext()))) {
            this.addWarn("XZ library missing, falling back to GZ compression");
            this.compressionMode = CompressionMode.GZ;
            this.fileNamePatternStr = this.replaceSuffix(this.fileNamePatternStr, CompressionMode.XZ_SUFFIX, CompressionMode.GZ_SUFFIX);
        }
    }

    private String replaceSuffix(String input, String existingSuffix, String newSuffix) {
        int existingSuffixLen = existingSuffix.length();
        if (input.endsWith(existingSuffix)) {
            return input.substring(0, input.length() - existingSuffixLen) + newSuffix;
        }
        throw new IllegalArgumentException("[" + input + "] should end with " + existingSuffix);
    }

    public void setFileNamePattern(String fnp) {
        this.fileNamePatternStr = fnp;
    }

    public String getFileNamePattern() {
        return this.fileNamePatternStr;
    }

    @Override
    public CompressionMode getCompressionMode() {
        return this.compressionMode;
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public void start() {
        this.started = true;
    }

    @Override
    public void stop() {
        this.started = false;
    }

    @Override
    public void setParent(FileAppender<?> appender) {
        this.parent = appender;
    }

    public boolean isParentPrudent() {
        return this.parent.isPrudent();
    }

    public String getParentsRawFileProperty() {
        return this.parent.rawFileProperty();
    }
}

