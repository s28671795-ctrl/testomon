/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.RetentionDurationUnit;

public interface RetentionDuration {
    public RetentionDurationUnit unit();

    public int duration();
}

