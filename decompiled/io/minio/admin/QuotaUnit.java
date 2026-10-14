/*
 * Decompiled with CFR 0.152.
 */
package io.minio.admin;

public enum QuotaUnit {
    KB(1024L),
    MB(1024L * QuotaUnit.KB.unit),
    GB(1024L * QuotaUnit.MB.unit),
    TB(1024L * QuotaUnit.GB.unit);

    private final long unit;

    private QuotaUnit(long unit) {
        this.unit = unit;
    }

    public long toBytes(long size) {
        long totalSize = size * this.unit;
        if (totalSize < 0L) {
            throw new IllegalArgumentException("Quota size must be greater than zero.But actual is " + totalSize);
        }
        if (totalSize / this.unit != size) {
            throw new IllegalArgumentException("Quota size overflow");
        }
        return totalSize;
    }
}

