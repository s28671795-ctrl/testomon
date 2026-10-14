/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.RetentionDuration;
import io.minio.messages.RetentionDurationUnit;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name="Days")
public class RetentionDurationDays
implements RetentionDuration {
    @Text(required=false)
    private Integer days;

    public RetentionDurationDays() {
    }

    public RetentionDurationDays(int days) {
        this.days = days;
    }

    @Override
    public RetentionDurationUnit unit() {
        return RetentionDurationUnit.DAYS;
    }

    @Override
    public int duration() {
        return this.days;
    }

    public String toString() {
        if (this.days == null) {
            return "";
        }
        return this.days.toString() + (this.days == 1 ? " day" : " days");
    }
}

