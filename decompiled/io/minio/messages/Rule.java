/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.RetentionDuration;
import io.minio.messages.RetentionDurationDays;
import io.minio.messages.RetentionDurationYears;
import io.minio.messages.RetentionMode;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name="Rule", strict=false)
public class Rule {
    @Path(value="DefaultRetention")
    @Element(name="Mode", required=false)
    private RetentionMode mode;
    @Path(value="DefaultRetention")
    @ElementUnion(value={@Element(name="Days", type=RetentionDurationDays.class, required=false), @Element(name="Years", type=RetentionDurationYears.class, required=false)})
    private RetentionDuration duration;

    public Rule(@Element(name="Mode", required=false) RetentionMode mode, @ElementUnion(value={@Element(name="Days", type=RetentionDurationDays.class, required=false), @Element(name="Years", type=RetentionDurationYears.class, required=false)}) RetentionDuration duration) {
        if (mode != null && duration != null) {
            this.mode = mode;
            this.duration = duration;
        } else if (mode != null || duration != null) {
            if (mode == null) {
                throw new IllegalArgumentException("mode is null");
            }
            throw new IllegalArgumentException("duration is null");
        }
    }

    public RetentionMode mode() {
        return this.mode;
    }

    public RetentionDuration duration() {
        return this.duration;
    }
}

