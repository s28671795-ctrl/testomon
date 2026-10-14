/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.util.TimeZone;
import org.simpleframework.xml.transform.Transform;

class TimeZoneTransform
implements Transform<TimeZone> {
    TimeZoneTransform() {
    }

    @Override
    public TimeZone read(String zone) {
        return TimeZone.getTimeZone(zone);
    }

    @Override
    public String write(TimeZone zone) {
        return zone.getID();
    }
}

