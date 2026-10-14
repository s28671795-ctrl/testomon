/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.DateDays;
import io.minio.messages.ResponseDate;
import java.time.ZonedDateTime;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Transition")
public class Transition
extends DateDays {
    @Element(name="StorageClass")
    private String storageClass;

    public Transition(@Nullable @Element(name="Date", required=false) ResponseDate date, @Nullable @Element(name="Days", required=false) Integer days, @Nonnull @Element(name="StorageClass", required=false) String storageClass) {
        if (!(date != null ^ days != null)) {
            throw new IllegalArgumentException("Only one of date or days must be set");
        }
        this.date = date;
        this.days = days;
        if (storageClass == null || storageClass.isEmpty()) {
            throw new IllegalArgumentException("StorageClass must be provided");
        }
        this.storageClass = storageClass;
    }

    public Transition(ZonedDateTime date, Integer days, String storageClass) {
        this(date == null ? null : new ResponseDate(date), days, storageClass);
    }

    public String storageClass() {
        return this.storageClass;
    }
}

