/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import io.grpc.CallOptions;
import io.grpc.Internal;
import io.grpc.stub.ClientCalls;

@Internal
public final class InternalClientCalls {
    public static CallOptions.Key<ClientCalls.StubType> getStubTypeOption() {
        return ClientCalls.STUB_TYPE_OPTION;
    }

    public static StubType getStubType(CallOptions callOptions) {
        return StubType.of(callOptions.getOption(ClientCalls.STUB_TYPE_OPTION));
    }

    public static CallOptions setStubType(CallOptions callOptions, StubType stubType) {
        return callOptions.withOption(ClientCalls.STUB_TYPE_OPTION, stubType.internalType);
    }

    public static enum StubType {
        BLOCKING(ClientCalls.StubType.BLOCKING),
        ASYNC(ClientCalls.StubType.ASYNC),
        FUTURE(ClientCalls.StubType.FUTURE);

        private final ClientCalls.StubType internalType;

        private StubType(ClientCalls.StubType internalType) {
            this.internalType = internalType;
        }

        public static StubType of(ClientCalls.StubType internal) {
            for (StubType value : StubType.values()) {
                if (value.internalType != internal) continue;
                return value;
            }
            throw new AssertionError((Object)("Unknown StubType: " + internal.name()));
        }
    }
}

