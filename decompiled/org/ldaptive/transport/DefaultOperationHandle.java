/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.ldaptive.AbandonRequest;
import org.ldaptive.BindRequest;
import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;
import org.ldaptive.Request;
import org.ldaptive.Result;
import org.ldaptive.ResultCode;
import org.ldaptive.UnbindRequest;
import org.ldaptive.control.ResponseControl;
import org.ldaptive.extended.CancelRequest;
import org.ldaptive.extended.ExtendedOperationHandle;
import org.ldaptive.extended.IntermediateResponse;
import org.ldaptive.extended.StartTLSRequest;
import org.ldaptive.extended.UnsolicitedNotification;
import org.ldaptive.handler.CompleteHandler;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.IntermediateResponseHandler;
import org.ldaptive.handler.ReferralHandler;
import org.ldaptive.handler.ResponseControlHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.ResultPredicate;
import org.ldaptive.handler.UnsolicitedNotificationHandler;
import org.ldaptive.transport.MessageFunctional;
import org.ldaptive.transport.TransportConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultOperationHandle<Q extends Request, S extends Result>
implements OperationHandle<Q, S> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Request request;
    private TransportConnection connection;
    private Duration responseTimeout;
    private Integer messageID;
    private ResultHandler[] onResult;
    private ResponseControlHandler[] onControl;
    private ReferralHandler[] onReferral;
    private IntermediateResponseHandler[] onIntermediate;
    private ExceptionHandler onException;
    private UnsolicitedNotificationHandler[] onUnsolicitedNotification;
    private CompleteHandler onComplete;
    private ResultPredicate throwCondition;
    private final CountDownLatch responseDone = new CountDownLatch(1);
    private final Instant creationTime = Instant.now();
    private Instant sentTime;
    private Instant receivedTime;
    private boolean consumedMessage;
    private S result;
    private LdapException exception;

    public DefaultOperationHandle(Q req, TransportConnection conn, Duration timeout2) {
        if (req == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.request = req;
        this.connection = conn;
        this.responseTimeout = timeout2;
    }

    @Override
    public DefaultOperationHandle<Q, S> send() {
        if (this.sentTime != null) {
            throw new IllegalStateException("Request for handle " + this + " has already been sent");
        }
        if (this.connection == null) {
            throw new IllegalStateException("Cannot execute request for handle " + this + " , connection is null");
        }
        this.connection.write(this);
        return this;
    }

    @Override
    public S await() throws LdapException {
        try {
            if (Duration.ZERO.equals(this.responseTimeout)) {
                this.responseDone.await();
                if (this.result != null && this.exception == null) {
                    this.logger.trace("await received result {} for handle {}", (Object)this.result, (Object)this);
                    if (this.throwCondition != null) {
                        this.throwCondition.testAndThrow((Result)this.result);
                    }
                    return this.result;
                }
            } else if (!this.responseDone.await(this.responseTimeout.toMillis(), TimeUnit.MILLISECONDS)) {
                this.abandon(new LdapException(ResultCode.LDAP_TIMEOUT, "No response received in " + this.responseTimeout.toMillis() + "ms for handle " + this));
                this.logger.trace("await abandoned handle {}", (Object)this);
            } else if (this.result != null && this.exception == null) {
                this.logger.trace("await received result {} for handle {}", (Object)this.result, (Object)this);
                if (this.throwCondition != null) {
                    this.throwCondition.testAndThrow((Result)this.result);
                }
                return this.result;
            }
        }
        catch (InterruptedException e) {
            this.logger.trace("await interrupted for handle {} waiting for response", (Object)this, (Object)e);
            this.exception(new LdapException(ResultCode.LOCAL_ERROR, (Throwable)e));
        }
        if (this.exception == null) {
            throw new LdapException(ResultCode.LOCAL_ERROR, "Response completed for handle " + this + " without a result or exception");
        }
        throw this.exception;
    }

    @Override
    public DefaultOperationHandle<Q, S> onResult(ResultHandler ... function) {
        this.onResult = function;
        this.initializeMessageFunctional(this.onResult);
        return this;
    }

    @Override
    public DefaultOperationHandle<Q, S> onControl(ResponseControlHandler ... function) {
        this.onControl = function;
        this.initializeMessageFunctional(this.onControl);
        return this;
    }

    @Override
    public DefaultOperationHandle<Q, S> onReferral(ReferralHandler ... function) {
        this.onReferral = function;
        this.initializeMessageFunctional(this.onReferral);
        return this;
    }

    @Override
    public DefaultOperationHandle<Q, S> onIntermediate(IntermediateResponseHandler ... function) {
        this.onIntermediate = function;
        this.initializeMessageFunctional(this.onIntermediate);
        return this;
    }

    @Override
    public DefaultOperationHandle<Q, S> onUnsolicitedNotification(UnsolicitedNotificationHandler ... function) {
        this.onUnsolicitedNotification = function;
        this.initializeMessageFunctional(this.onUnsolicitedNotification);
        return this;
    }

    @Override
    public DefaultOperationHandle<Q, S> onException(ExceptionHandler function) {
        this.onException = function;
        this.initializeMessageFunctional(this.onException);
        return this;
    }

    @Override
    public DefaultOperationHandle<Q, S> onComplete(CompleteHandler function) {
        this.onComplete = function;
        return this;
    }

    @Override
    public DefaultOperationHandle<Q, S> throwIf(ResultPredicate function) {
        this.throwCondition = function;
        return this;
    }

    protected void initializeMessageFunctional(Object ... functions) {
        if (functions != null) {
            for (Object o : functions) {
                if (!(o instanceof MessageFunctional)) continue;
                ((MessageFunctional)o).setConnection(this.connection);
                ((MessageFunctional)o).setRequest(this.request);
                ((MessageFunctional)o).setHandle(this);
            }
        }
    }

    @Override
    public void abandon() {
        this.abandon(new LdapException(ResultCode.USER_CANCELLED, "Request abandoned"));
    }

    public void abandon(LdapException cause) {
        if (this.sentTime == null) {
            this.logger.warn("Request has not been sent for {}.", (Object)this);
        }
        if (!(this.request instanceof BindRequest || this.request instanceof UnbindRequest || this.request instanceof StartTLSRequest || this.request instanceof CancelRequest)) {
            if (this.receivedTime == null) {
                try {
                    this.connection.operation(new AbandonRequest(this.messageID));
                }
                finally {
                    this.exception(cause);
                }
            } else {
                this.exception(cause);
            }
        } else {
            this.exception(cause);
        }
    }

    @Override
    public ExtendedOperationHandle cancel() {
        if (this.sentTime == null) {
            throw new IllegalStateException("Request has not been sent for handle " + this + ". Invoke send before calling this method.");
        }
        if (this.receivedTime != null) {
            throw new IllegalStateException("Operation completed for handle " + this + ". Cancel cannot be invoked.");
        }
        CompleteHandler completeHandler = this.onComplete;
        this.onComplete = null;
        ExtendedOperationHandle handle2 = this.connection.operation(new CancelRequest(this.messageID));
        if (completeHandler != null) {
            handle2.onComplete(completeHandler);
        }
        return handle2;
    }

    public Integer getMessageID() {
        return this.messageID;
    }

    @Override
    public Instant getSentTime() {
        return this.sentTime;
    }

    @Override
    public Instant getReceivedTime() {
        return this.receivedTime;
    }

    public ResultHandler[] getOnResult() {
        return this.onResult;
    }

    public ResponseControlHandler[] getOnControl() {
        return this.onControl;
    }

    public ReferralHandler[] getOnReferral() {
        return this.onReferral;
    }

    public IntermediateResponseHandler[] getOnIntermediate() {
        return this.onIntermediate;
    }

    public ExceptionHandler getOnException() {
        return this.onException;
    }

    public CompleteHandler getOnComplete() {
        return this.onComplete;
    }

    public ResultPredicate getThrowCondition() {
        return this.throwCondition;
    }

    public UnsolicitedNotificationHandler[] getOnUnsolicitedNotification() {
        return this.onUnsolicitedNotification;
    }

    public boolean hasConsumedMessage() {
        return this.consumedMessage;
    }

    public Request getRequest() {
        return this.request;
    }

    public void messageID(int id) {
        this.messageID = id;
    }

    public void sent() {
        this.sentTime = Instant.now();
    }

    public void result(S r) {
        if (r == null) {
            throw new IllegalArgumentException("Result cannot be null for handle " + this);
        }
        if (this.onResult != null) {
            for (ResultHandler func : this.onResult) {
                try {
                    func.accept(r);
                }
                catch (Exception ex) {
                    this.logger.warn("Result function {} in handle {} threw an exception", func, this, ex);
                }
            }
            this.consumedMessage();
        }
        this.result = r;
        this.complete();
    }

    public void control(ResponseControl c) {
        if (this.onControl != null) {
            for (ResponseControlHandler func : this.onControl) {
                try {
                    func.accept(c);
                }
                catch (Exception ex) {
                    this.logger.warn("Control consumer {} in handle {} threw an exception", func, this, ex);
                }
            }
        }
    }

    public void referral(String ... url2) {
        if (this.onReferral != null) {
            for (ReferralHandler func : this.onReferral) {
                try {
                    func.accept(url2);
                }
                catch (Exception ex) {
                    this.logger.warn("Referral consumer {} in handle {} threw an exception", func, this, ex);
                }
            }
        }
    }

    public void intermediate(IntermediateResponse r) {
        if (this.onIntermediate != null) {
            for (IntermediateResponseHandler func : this.onIntermediate) {
                try {
                    func.accept(r);
                }
                catch (Exception ex) {
                    this.logger.warn("Intermediate response consumer {} in handle {} threw an exception", func, this, ex);
                }
            }
            this.consumedMessage();
        }
    }

    public void unsolicitedNotification(UnsolicitedNotification u) {
        if (this.onUnsolicitedNotification != null) {
            for (UnsolicitedNotificationHandler func : this.onUnsolicitedNotification) {
                try {
                    func.accept(u);
                }
                catch (Exception ex) {
                    this.logger.warn("Unsolicited notification consumer {} in handle {} threw an exception", func, this, ex);
                }
            }
            this.consumedMessage();
        }
    }

    public void exception(LdapException e) {
        if (e == null) {
            throw new IllegalArgumentException("Exception cannot be null for handle " + this);
        }
        if (this.onException != null) {
            try {
                this.onException.accept(e);
            }
            catch (Exception ex) {
                this.logger.warn("Exception consumer {} in handle {} threw an exception", this.onException, this, ex);
            }
        }
        this.exception = e;
        this.complete();
    }

    protected void consumedMessage() {
        this.consumedMessage = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void complete() {
        block20: {
            try {
                if (this.receivedTime == null) break block20;
                this.logger.warn("Operation already complete for handle {}", (Object)this);
            }
            catch (Throwable throwable) {
                try {
                    if (this.connection != null) {
                        this.connection.complete(this);
                    }
                }
                catch (Exception e) {
                    this.logger.warn("Connection {} complete threw an exception for handle {}", this.connection, this, e);
                }
                this.connection = null;
                throw throwable;
            }
            try {
                if (this.connection != null) {
                    this.connection.complete(this);
                }
            }
            catch (Exception e) {
                this.logger.warn("Connection {} complete threw an exception for handle {}", this.connection, this, e);
            }
            this.connection = null;
            return;
        }
        try {
            this.responseDone.countDown();
        }
        catch (Throwable throwable) {
            this.receivedTime = Instant.now();
            if (this.onComplete != null) {
                try {
                    this.onComplete.execute();
                }
                catch (Exception e) {
                    this.logger.warn("Complete consumer {} in handle {} threw an exception", this.onComplete, this, e);
                }
            }
            throw throwable;
        }
        this.receivedTime = Instant.now();
        if (this.onComplete != null) {
            try {
                this.onComplete.execute();
            }
            catch (Exception e) {
                this.logger.warn("Complete consumer {} in handle {} threw an exception", this.onComplete, this, e);
            }
        }
        try {
            if (this.connection != null) {
                this.connection.complete(this);
            }
        }
        catch (Exception e) {
            this.logger.warn("Connection {} complete threw an exception for handle {}", this.connection, this, e);
        }
        this.connection = null;
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "messageID=" + this.messageID + ", " + "request=" + this.request + ", " + "connection=" + this.connection + ", " + "responseTimeout=" + this.responseTimeout + ", " + "creationTime=" + this.creationTime + ", " + "sentTime=" + this.sentTime + ", " + "receivedTime=" + this.receivedTime + ", " + "consumedMessage=" + this.consumedMessage + ", " + "result=" + this.result + ", " + "exception=" + this.exception;
    }
}

