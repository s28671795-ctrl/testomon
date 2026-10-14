/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.MethodDescriptor;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class Channel {
    public abstract <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> var1, CallOptions var2);

    public abstract String authority();
}

