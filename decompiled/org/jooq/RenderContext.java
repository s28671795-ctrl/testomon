/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Context;

public interface RenderContext
extends Context<RenderContext> {

    public static enum CastMode {
        ALWAYS,
        NEVER,
        DEFAULT;

    }
}

