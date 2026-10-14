/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import java.util.Arrays;
import org.ldaptive.AbstractOperation;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.extended.ExtendedOperationHandle;
import org.ldaptive.extended.ExtendedRequest;
import org.ldaptive.extended.ExtendedResponse;
import org.ldaptive.handler.ExtendedValueHandler;

public class ExtendedOperation
extends AbstractOperation<ExtendedRequest, ExtendedResponse> {
    private ExtendedValueHandler[] extendedValueHandlers;

    public ExtendedOperation() {
    }

    public ExtendedOperation(ConnectionFactory factory2) {
        super(factory2);
    }

    public ExtendedValueHandler[] getExtendedValueHandlers() {
        return this.extendedValueHandlers;
    }

    public void setExtendedValueHandlers(ExtendedValueHandler ... handlers) {
        this.extendedValueHandlers = handlers;
    }

    public ExtendedOperationHandle send(ExtendedRequest request) throws LdapException {
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

    public static ExtendedOperationHandle send(ConnectionFactory factory2, ExtendedRequest request) throws LdapException {
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
    public ExtendedResponse execute(ExtendedRequest request) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            ExtendedResponse extendedResponse = this.configureHandle(conn.operation(this.configureRequest(request))).execute();
            return extendedResponse;
        }
    }

    public static ExtendedResponse execute(ConnectionFactory factory2, ExtendedRequest request) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            ExtendedResponse extendedResponse = conn.operation(request).execute();
            return extendedResponse;
        }
    }

    protected ExtendedOperationHandle configureHandle(ExtendedOperationHandle handle2) {
        return handle2.onExtended(this.getExtendedValueHandlers()).onControl(this.getControlHandlers()).onReferral(this.getReferralHandlers()).onIntermediate(this.getIntermediateResponseHandlers()).onException(this.getExceptionHandler()).throwIf(this.getThrowCondition()).onUnsolicitedNotification(this.getUnsolicitedNotificationHandlers()).onResult(this.getResultHandlers());
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "extendedValueHandlers=" + Arrays.toString(this.extendedValueHandlers);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, ExtendedOperation> {
        protected Builder() {
            super(new ExtendedOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder onExtended(ExtendedValueHandler ... handlers) {
            ((ExtendedOperation)this.object).setExtendedValueHandlers(handlers);
            return this.self();
        }
    }
}

