/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import org.ldaptive.Connection;
import org.ldaptive.OperationHandle;
import org.ldaptive.Request;
import org.ldaptive.Result;
import org.ldaptive.transport.TransportConnection;

public abstract class MessageFunctional<Q extends Request, S extends Result> {
    private Connection connection;
    private Q request;
    private OperationHandle<Q, S> handle;

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(TransportConnection conn) {
        this.connection = conn;
    }

    public Q getRequest() {
        return this.request;
    }

    public void setRequest(Q req) {
        this.request = req;
    }

    public OperationHandle<Q, S> getHandle() {
        return this.handle;
    }

    public void setHandle(OperationHandle<Q, S> h2) {
        this.handle = h2;
    }

    public static abstract class BiConsumer<Q extends Request, S extends Result, T, U>
    extends MessageFunctional<Q, S>
    implements java.util.function.BiConsumer<T, U> {
    }

    public static abstract class Consumer<Q extends Request, S extends Result, T>
    extends MessageFunctional<Q, S>
    implements java.util.function.Consumer<T> {
    }

    public static abstract class Function<Q extends Request, S extends Result, T, R>
    extends MessageFunctional<Q, S>
    implements java.util.function.Function<T, R> {
    }
}

