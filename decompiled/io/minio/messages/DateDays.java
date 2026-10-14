/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ResponseDate;
import java.time.ZonedDateTime;
import org.simpleframework.xml.Element;

public abstract class DateDays {
    @Element(name="Date", required=false)
    protected ResponseDate date;
    @Element(name="Days", required=false)
    protected Integer days;

    public ZonedDateTime date() {
        return this.date != null ? this.date.zonedDateTime() : null;
    }

    public Integer days() {
        return this.days;
    }
}

