/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.DateDays;
import io.minio.messages.ResponseDate;
import java.time.ZonedDateTime;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Expiration")
public class Expiration
extends DateDays {
    @Element(name="ExpiredObjectDeleteMarker", required=false)
    private Boolean expiredObjectDeleteMarker;

    public Expiration(@Nullable @Element(name="Date", required=false) ResponseDate date, @Nullable @Element(name="Days", required=false) Integer days, @Nullable @Element(name="ExpiredObjectDeleteMarker", required=false) Boolean expiredObjectDeleteMarker) {
        if (expiredObjectDeleteMarker != null) {
            if (date != null || days != null) {
                throw new IllegalArgumentException("ExpiredObjectDeleteMarker must not be provided along with Date and Days");
            }
        } else if (date != null ^ days != null) {
            this.date = date;
            this.days = days;
        } else {
            throw new IllegalArgumentException("Only one of date or days must be set");
        }
        this.expiredObjectDeleteMarker = expiredObjectDeleteMarker;
    }

    public Expiration(ZonedDateTime date, Integer days, Boolean expiredObjectDeleteMarker) {
        this(date == null ? null : new ResponseDate(date), days, expiredObjectDeleteMarker);
    }

    public Boolean expiredObjectDeleteMarker() {
        return this.expiredObjectDeleteMarker;
    }
}

