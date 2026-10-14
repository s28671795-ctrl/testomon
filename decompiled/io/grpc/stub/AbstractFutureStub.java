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
public abstract class AbstractFutureStub<S extends AbstractFutureStub<S>>
extends AbstractStub<S> {
    protected AbstractFutureStub(Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory2, Channel channel) {
        return AbstractFutureStub.newStub(factory2, channel, CallOptions.DEFAULT);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory2, Channel channel, CallOptions callOptions) {
        T stub = factory2.newStub(channel, callOptions.withOption(ClientCalls.STUB_TYPE_OPTION, ClientCalls.StubType.FUTURE));
        assert (stub instanceof AbstractFutureStub) : String.format("Expected AbstractFutureStub, but got %s.", stub.getClass());
        return stub;
    }
}

