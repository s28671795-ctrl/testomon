/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.util.MD5Util;
import liquibase.util.StringUtil;

public final class CheckSum {
    private int version;
    private String storedCheckSum;
    private static final char DELIMITER = ':';
    private static final String CHECKSUM_REGEX = "(^\\d++):([a-zA-Z0-9]++)";
    private static final Pattern CHECKSUM_PATTERN = Pattern.compile("(^\\d++):([a-zA-Z0-9]++)");

    private CheckSum(String checksum, int version) {
        this.storedCheckSum = checksum;
        this.version = version;
    }

    public static CheckSum parse(String checksumValue) {
        if (StringUtil.isEmpty(checksumValue)) {
            return null;
        }
        Matcher matcher = CHECKSUM_PATTERN.matcher(checksumValue);
        if (matcher.find()) {
            return new CheckSum(matcher.group(2), Integer.parseInt(matcher.group(1)));
        }
        return new CheckSum(checksumValue, 1);
    }

    @Deprecated
    public static int getCurrentVersion() {
        return ChecksumVersion.latest().getVersion();
    }

    public static CheckSum compute(String valueToChecksum) {
        return new CheckSum(MD5Util.computeMD5(Normalizer.normalize(StringUtil.standardizeLineEndings(valueToChecksum).replace("\ufffd", ""), Normalizer.Form.NFC)), Scope.getCurrentScope().getChecksumVersion().getVersion());
    }

    public static CheckSum compute(final InputStream stream, boolean standardizeLineEndings) {
        InputStream newStream = stream;
        if (standardizeLineEndings) {
            newStream = new InputStream(){
                private boolean isPrevR = false;

                @Override
                public int read() throws IOException {
                    int read = stream.read();
                    if (read == 13) {
                        this.isPrevR = true;
                        return 10;
                    }
                    if (read == 10 && this.isPrevR) {
                        this.isPrevR = false;
                        return this.read();
                    }
                    this.isPrevR = false;
                    return read;
                }
            };
        }
        return new CheckSum(MD5Util.computeMD5(newStream), Scope.getCurrentScope().getChecksumVersion().getVersion());
    }

    public String toString() {
        return this.version + String.valueOf(':') + this.storedCheckSum;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof CheckSum && this.toString().equals(obj.toString());
    }

    public CheckSum() {
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setStoredCheckSum(String storedCheckSum) {
        this.storedCheckSum = storedCheckSum;
    }

    public int getVersion() {
        return this.version;
    }
}

