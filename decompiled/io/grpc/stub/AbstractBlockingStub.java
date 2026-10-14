/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.ClientCalls;
import javax.annotation.CheckReturnValue;
import javax.annotation.concurrent.ThreadSafe;

@CheckReturnValue
@ThreadSafe
public abstract class AbstractBlockingStub<S extends AbstractBlockingStub<S>>
extends AbstractStub<S> {
    protected AbstractBlockingStub(Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory2, Channel channel) {
        return AbstractBlockingStub.newStub(factory2, channel, CallOptions.DEFAULT);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory2, Channel channel, CallOptions callOptions) {
        T stub = factory2.newStub(channel, callOptions.withOption(ClientCalls.STUB_TYPE_OPTION, ClientCalls.StubType.BLOCKING));
        assert (stub instanceof AbstractBlockingStub) : String.format("Expected AbstractBlockingStub, but got %s.", stub.getClass());
        return stub;
    }
}

