/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import org.eclipse.jetty.io.EndPoint;

public interface RateControl {
    public static final RateControl NO_RATE_CONTROL = event -> true;

    public boolean onEvent(Object var1);

    public static interface Factory {
        default public RateControl newRateControl(EndPoint endPoint) {
            return NO_RATE_CONTROL;
        }
    }
}

