/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.RetentionDuration;
import io.minio.messages.RetentionDurationUnit;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name="Years")
public class RetentionDurationYears
implements RetentionDuration {
    @Text(required=false)
    private Integer years;

    public RetentionDurationYears() {
    }

    public RetentionDurationYears(int years) {
        this.years = years;
    }

    @Override
    public RetentionDurationUnit unit() {
        return RetentionDurationUnit.YEARS;
    }

    @Override
    public int duration() {
        return this.years;
    }

    public String toString() {
        if (this.years == null) {
            return "";
        }
        return this.years.toString() + (this.years == 1 ? " year" : " years");
    }
}

