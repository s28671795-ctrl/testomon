/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Context;
import io.grpc.Internal;
import io.grpc.Server;

@Internal
public class InternalServer {
    public static final Context.Key<Server> SERVER_CONTEXT_KEY = Server.SERVER_CONTEXT_KEY;

    private InternalServer() {
    }
}

