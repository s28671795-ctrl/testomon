/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Instant;
import org.ldaptive.AbstractRetryMetadata;

public class InitialRetryMetadata
extends AbstractRetryMetadata {
    public InitialRetryMetadata(Instant time) {
        this.successTime = time;
    }
}

