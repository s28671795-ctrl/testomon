/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.buffer.search;

import io.grpc.netty.shaded.io.netty.buffer.search.AhoCorasicSearchProcessorFactory;
import io.grpc.netty.shaded.io.netty.buffer.search.MultiSearchProcessorFactory;

public abstract class AbstractMultiSearchProcessorFactory
implements MultiSearchProcessorFactory {
    public static AhoCorasicSearchProcessorFactory newAhoCorasicSearchProcessorFactory(byte[] ... needles) {
        return new AhoCorasicSearchProcessorFactory(needles);
    }
}

