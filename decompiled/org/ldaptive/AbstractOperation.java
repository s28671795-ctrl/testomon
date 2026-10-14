/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.Operation;
import org.ldaptive.OperationHandle;
import org.ldaptive.Request;
import org.ldaptive.Result;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.IntermediateResponseHandler;
import org.ldaptive.handler.ReferralHandler;
import org.ldaptive.handler.RequestHandler;
import org.ldaptive.handler.ResponseControlHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.UnsolicitedNotificationHandler;

public abstract class AbstractOperation<Q extends Request, S extends Result>
implements Operation<Q, S> {
    private ConnectionFactory connectionFactory;
    private RequestHandler<Q>[] requestHandlers;
    private ResultHandler[] resultHandlers;
    private ResponseControlHandler[] controlHandlers;
    private ReferralHandler[] referralHandlers;
    private IntermediateResponseHandler[] intermediateResponseHandlers;
    private ExceptionHandler exceptionHandler;
    private ResultPredicate throwCondition;
    private UnsolicitedNotificationHandler[] unsolicitedNotificationHandlers;

    public AbstractOperation() {
    }

    public AbstractOperation(ConnectionFactory factory2) {
        this.setConnectionFactory(factory2);
    }

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory factory2) {
        this.connectionFactory = factory2;
    }

    public RequestHandler<Q>[] getRequestHandlers() {
        return this.requestHandlers;
    }

    public void setRequestHandlers(RequestHandler<Q> ... handlers) {
        this.requestHandlers = handlers;
    }

    public ResultHandler[] getResultHandlers() {
        return this.resultHandlers;
    }

    public void setResultHandlers(ResultHandler ... handlers) {
        this.resultHandlers = handlers;
    }

    public ResponseControlHandler[] getControlHandlers() {
        return this.controlHandlers;
    }

    public void setControlHandlers(ResponseControlHandler ... handlers) {
        this.controlHandlers = handlers;
    }

    public ReferralHandler[] getReferralHandlers() {
        return this.referralHandlers;
    }

    public void setReferralHandlers(ReferralHandler ... handlers) {
        this.referralHandlers = handlers;
    }

    public IntermediateResponseHandler[] getIntermediateResponseHandlers() {
        return this.intermediateResponseHandlers;
    }

    public void setIntermediateResponseHandlers(IntermediateResponseHandler ... handlers) {
        this.intermediateResponseHandlers = handlers;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
    }

    public ResultPredicate getThrowCondition() {
        return this.throwCondition;
    }

    public void setThrowCondition(ResultPredicate function) {
        this.throwCondition = function;
    }

    public UnsolicitedNotificationHandler[] getUnsolicitedNotificationHandlers() {
        return this.unsolicitedNotificationHandlers;
    }

    public void setUnsolicitedNotificationHandlers(UnsolicitedNotificationHandler ... handlers) {
        this.unsolicitedNotificationHandlers = handlers;
    }

    protected Q configureRequest(Q request) {
        if (this.requestHandlers == null || this.requestHandlers.length == 0) {
            return request;
        }
        Object req = request;
        for (RequestHandler<Q> func : this.requestHandlers) {
            req = (Request)func.apply(req);
        }
        return req;
    }

    protected OperationHandle<Q, S> configureHandle(OperationHandle<Q, S> handle2) {
        return handle2.onControl(this.getControlHandlers()).onReferral(this.getReferralHandlers()).onIntermediate(this.getIntermediateResponseHandlers()).onException(this.getExceptionHandler()).throwIf(this.getThrowCondition()).onUnsolicitedNotification(this.getUnsolicitedNotificationHandlers()).onResult(this.getResultHandlers());
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "connectionFactory=" + this.connectionFactory + ", " + "requestHandlers=" + Arrays.toString(this.requestHandlers) + ", " + "resultHandlers=" + Arrays.toString(this.resultHandlers) + ", " + "controlHandlers=" + Arrays.toString(this.controlHandlers) + ", " + "referralHandlers=" + Arrays.toString(this.referralHandlers) + ", " + "intermediateResponseHandlers=" + Arrays.toString(this.intermediateResponseHandlers) + ", " + "exceptionHandler=" + this.exceptionHandler + ", " + "throwCondition=" + this.throwCondition + ", " + "unsolicitedNotificationHandlers=" + Arrays.toString(this.unsolicitedNotificationHandlers);
    }

    protected static abstract class AbstractBuilder<B, T extends AbstractOperation> {
        protected final T object;

        protected AbstractBuilder(T t2) {
            this.object = t2;
        }

        protected abstract B self();

        public B factory(ConnectionFactory factory2) {
            ((AbstractOperation)this.object).setConnectionFactory(factory2);
            return this.self();
        }

        public B onRequest(RequestHandler ... handlers) {
            ((AbstractOperation)this.object).setRequestHandlers(handlers);
            return this.self();
        }

        public B onResult(ResultHandler ... handlers) {
            ((AbstractOperation)this.object).setResultHandlers(handlers);
            return this.self();
        }

        public B onControl(ResponseControlHandler ... handlers) {
            ((AbstractOperation)this.object).setControlHandlers(handlers);
            return this.self();
        }

        public B onReferral(ReferralHandler ... handlers) {
            ((AbstractOperation)this.object).setReferralHandlers(handlers);
            return this.self();
        }

        public B onIntermediate(IntermediateResponseHandler ... handlers) {
            ((AbstractOperation)this.object).setIntermediateResponseHandlers(handlers);
            return this.self();
        }

        public B onUnsolicitedNotification(UnsolicitedNotificationHandler ... handlers) {
            ((AbstractOperation)this.object).setUnsolicitedNotificationHandlers(handlers);
            return this.self();
        }

        public B onException(ExceptionHandler handler) {
            ((AbstractOperation)this.object).setExceptionHandler(handler);
            return this.self();
        }

        public B throwIf(ResultPredicate function) {
            ((AbstractOperation)this.object).setThrowCondition(function);
            return this.self();
        }

        public T build() {
            return this.object;
        }
    }
}

