/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.time.Instant;
import org.apache.wss4j.common.util.WSTimeSource;

public class WSCurrentTimeSource
implements WSTimeSource {
    @Override
    public Instant now() {
        return Instant.now();
    }
}

