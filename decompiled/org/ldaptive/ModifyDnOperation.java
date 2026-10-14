/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractOperation;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.ModifyDnRequest;
import org.ldaptive.ModifyDnResponse;
import org.ldaptive.OperationHandle;

public class ModifyDnOperation
extends AbstractOperation<ModifyDnRequest, ModifyDnResponse> {
    public ModifyDnOperation() {
    }

    public ModifyDnOperation(ConnectionFactory factory2) {
        super(factory2);
    }

    @Override
    public OperationHandle<ModifyDnRequest, ModifyDnResponse> send(ModifyDnRequest request) throws LdapException {
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

    public static OperationHandle<ModifyDnRequest, ModifyDnResponse> send(ConnectionFactory factory2, ModifyDnRequest request) throws LdapException {
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
    public ModifyDnResponse execute(ModifyDnRequest request) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            ModifyDnResponse modifyDnResponse = this.configureHandle(conn.operation(this.configureRequest(request))).execute();
            return modifyDnResponse;
        }
    }

    public static ModifyDnResponse execute(ConnectionFactory factory2, ModifyDnRequest request) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            ModifyDnResponse modifyDnResponse = conn.operation(request).execute();
            return modifyDnResponse;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, ModifyDnOperation> {
        protected Builder() {
            super(new ModifyDnOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

