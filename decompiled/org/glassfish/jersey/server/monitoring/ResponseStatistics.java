/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Map;

public interface ResponseStatistics {
    public Integer getLastResponseCode();

    public Map<Integer, Long> getResponseCodes();

    @Deprecated
    public ResponseStatistics snapshot();
}

