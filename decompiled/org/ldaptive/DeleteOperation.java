/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractOperation;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DeleteRequest;
import org.ldaptive.DeleteResponse;
import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;

public class DeleteOperation
extends AbstractOperation<DeleteRequest, DeleteResponse> {
    public DeleteOperation() {
    }

    public DeleteOperation(ConnectionFactory factory2) {
        super(factory2);
    }

    @Override
    public OperationHandle<DeleteRequest, DeleteResponse> send(DeleteRequest request) throws LdapException {
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

    public static OperationHandle<DeleteRequest, DeleteResponse> send(ConnectionFactory factory2, DeleteRequest request) throws LdapException {
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
    public DeleteResponse execute(DeleteRequest request) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            DeleteResponse deleteResponse = this.configureHandle(conn.operation(this.configureRequest(request))).execute();
            return deleteResponse;
        }
    }

    public static DeleteResponse execute(ConnectionFactory factory2, DeleteRequest request) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            DeleteResponse deleteResponse = conn.operation(request).execute();
            return deleteResponse;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, DeleteOperation> {
        protected Builder() {
            super(new DeleteOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

