/*
 * Decompiled with CFR 0.152.
 */
package io.perfmark;

import com.google.errorprone.annotations.DoNotCall;

public final class Link {
    final long linkId;

    Link(long linkId) {
        this.linkId = linkId;
    }

    @Deprecated
    @DoNotCall
    public void link() {
    }
}

