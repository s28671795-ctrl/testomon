/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.buffer.search;

import io.grpc.netty.shaded.io.netty.buffer.search.MultiSearchProcessor;
import io.grpc.netty.shaded.io.netty.buffer.search.SearchProcessorFactory;

public interface MultiSearchProcessorFactory
extends SearchProcessorFactory {
    @Override
    public MultiSearchProcessor newSearchProcessor();
}

