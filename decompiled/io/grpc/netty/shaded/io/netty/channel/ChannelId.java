/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import java.io.Serializable;

public interface ChannelId
extends Serializable,
Comparable<ChannelId> {
    public String asShortText();

    public String asLongText();
}

