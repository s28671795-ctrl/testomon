/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractOperation;
import org.ldaptive.BindRequest;
import org.ldaptive.BindResponse;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;

public class BindOperation
extends AbstractOperation<BindRequest, BindResponse> {
    public BindOperation() {
    }

    public BindOperation(ConnectionFactory factory2) {
        super(factory2);
    }

    @Override
    public OperationHandle<BindRequest, BindResponse> send(BindRequest request) throws LdapException {
        Connection conn = this.getConnectionFactory().getConnection();
        try {
            conn.open();
        }
        catch (Exception e) {
            conn.close();
            throw e;
        }
        return this.configureHandle(conn.operation(this.configureRequest(request))).onComplete(conn::close).send();
    }

    public static OperationHandle<BindRequest, BindResponse> send(ConnectionFactory factory2, BindRequest request) throws LdapException {
        Connection conn = factory2.getConnection();
        try {
            conn.open();
        }
        catch (Exception e) {
            conn.close();
            throw e;
        }
        return conn.operation(request).onComplete(conn::close).send();
    }

    @Override
    public BindResponse execute(BindRequest request) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            BindResponse bindResponse = this.configureHandle(conn.operation(this.configureRequest(request))).execute();
            return bindResponse;
        }
    }

    public static BindResponse execute(ConnectionFactory factory2, BindRequest request) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            BindResponse bindResponse = conn.operation(request).execute();
            return bindResponse;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, BindOperation> {
        protected Builder() {
            super(new BindOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

