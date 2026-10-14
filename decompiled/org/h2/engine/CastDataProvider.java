/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.api.JavaObjectSerializer;
import org.h2.engine.Mode;
import org.h2.util.TimeZoneProvider;
import org.h2.value.ValueTimestampTimeZone;

public interface CastDataProvider {
    public ValueTimestampTimeZone currentTimestamp();

    public TimeZoneProvider currentTimeZone();

    public Mode getMode();

    public JavaObjectSerializer getJavaObjectSerializer();

    public boolean zeroBasedEnums();
}

