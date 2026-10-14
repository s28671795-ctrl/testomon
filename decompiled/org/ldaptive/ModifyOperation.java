/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractOperation;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.ModifyRequest;
import org.ldaptive.ModifyResponse;
import org.ldaptive.OperationHandle;

public class ModifyOperation
extends AbstractOperation<ModifyRequest, ModifyResponse> {
    public ModifyOperation() {
    }

    public ModifyOperation(ConnectionFactory factory2) {
        super(factory2);
    }

    @Override
    public OperationHandle<ModifyRequest, ModifyResponse> send(ModifyRequest request) throws LdapException {
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

    public static OperationHandle<ModifyRequest, ModifyResponse> send(ConnectionFactory factory2, ModifyRequest request) throws LdapException {
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
    public ModifyResponse execute(ModifyRequest request) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            ModifyResponse modifyResponse = this.configureHandle(conn.operation(this.configureRequest(request))).execute();
            return modifyResponse;
        }
    }

    public static ModifyResponse execute(ConnectionFactory factory2, ModifyRequest request) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            ModifyResponse modifyResponse = conn.operation(request).execute();
            return modifyResponse;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, ModifyOperation> {
        protected Builder() {
            super(new ModifyOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

