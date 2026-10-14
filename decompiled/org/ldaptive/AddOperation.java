/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractOperation;
import org.ldaptive.AddRequest;
import org.ldaptive.AddResponse;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;

public class AddOperation
extends AbstractOperation<AddRequest, AddResponse> {
    public AddOperation() {
    }

    public AddOperation(ConnectionFactory factory2) {
        super(factory2);
    }

    @Override
    public OperationHandle<AddRequest, AddResponse> send(AddRequest request) throws LdapException {
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

    public static OperationHandle<AddRequest, AddResponse> send(ConnectionFactory factory2, AddRequest request) throws LdapException {
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
    public AddResponse execute(AddRequest request) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            AddResponse addResponse = this.configureHandle(conn.operation(this.configureRequest(request))).execute();
            return addResponse;
        }
    }

    public static AddResponse execute(ConnectionFactory factory2, AddRequest request) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            AddResponse addResponse = conn.operation(request).execute();
            return addResponse;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, AddOperation> {
        protected Builder() {
            super(new AddOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

