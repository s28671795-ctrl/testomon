/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.time.Clock;
import javax.validation.ClockProvider;

public class DefaultClockProvider
implements ClockProvider {
    public static final DefaultClockProvider INSTANCE = new DefaultClockProvider();

    private DefaultClockProvider() {
    }

    @Override
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}

