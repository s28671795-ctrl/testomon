/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Map;

public interface ResponseMXBean {
    public Map<Integer, Long> getResponseCodesToCountMap();

    public Integer getLastResponseCode();
}

