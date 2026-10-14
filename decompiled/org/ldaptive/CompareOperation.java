/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import org.ldaptive.AbstractOperation;
import org.ldaptive.CompareOperationHandle;
import org.ldaptive.CompareRequest;
import org.ldaptive.CompareResponse;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.handler.CompareValueHandler;

public class CompareOperation
extends AbstractOperation<CompareRequest, CompareResponse> {
    private CompareValueHandler[] compareValueHandlers;

    public CompareOperation() {
    }

    public CompareOperation(ConnectionFactory factory2) {
        super(factory2);
    }

    public CompareValueHandler[] getCompareValueHandlers() {
        return this.compareValueHandlers;
    }

    public void setCompareValueHandlers(CompareValueHandler ... handlers) {
        this.compareValueHandlers = handlers;
    }

    public CompareOperationHandle send(CompareRequest request) throws LdapException {
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

    public static CompareOperationHandle send(ConnectionFactory factory2, CompareRequest request) throws LdapException {
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
    public CompareResponse execute(CompareRequest request) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            CompareResponse compareResponse = this.configureHandle(conn.operation(this.configureRequest(request))).execute();
            return compareResponse;
        }
    }

    public static CompareResponse execute(ConnectionFactory factory2, CompareRequest request) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            CompareResponse compareResponse = conn.operation(request).execute();
            return compareResponse;
        }
    }

    protected CompareOperationHandle configureHandle(CompareOperationHandle handle2) {
        return handle2.onCompare(this.getCompareValueHandlers()).onControl(this.getControlHandlers()).onReferral(this.getReferralHandlers()).onIntermediate(this.getIntermediateResponseHandlers()).onException(this.getExceptionHandler()).throwIf(this.getThrowCondition()).onUnsolicitedNotification(this.getUnsolicitedNotificationHandlers()).onResult(this.getResultHandlers());
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "compareValueHandlers=" + Arrays.toString(this.compareValueHandlers);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, CompareOperation> {
        protected Builder() {
            super(new CompareOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder onCompare(CompareValueHandler ... handlers) {
            ((CompareOperation)this.object).setCompareValueHandlers(handlers);
            return this.self();
        }
    }
}

