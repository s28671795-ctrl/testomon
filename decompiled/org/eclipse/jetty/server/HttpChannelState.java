/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.UnavailableException;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.QuietException;
import org.eclipse.jetty.server.AsyncContextEvent;
import org.eclipse.jetty.server.AsyncContextState;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpChannelState {
    private static final Logger LOG = LoggerFactory.getLogger(HttpChannelState.class);
    private static final long DEFAULT_TIMEOUT = Long.getLong("org.eclipse.jetty.server.HttpChannelState.DEFAULT_TIMEOUT", 30000L);
    private final AutoLock _lock = new AutoLock();
    private final HttpChannel _channel;
    private List<AsyncListener> _asyncListeners;
    private State _state = State.IDLE;
    private RequestState _requestState = RequestState.BLOCKING;
    private OutputState _outputState = OutputState.OPEN;
    private InputState _inputState = InputState.IDLE;
    private boolean _initial = true;
    private boolean _sendError;
    private boolean _asyncWritePossible;
    private long _timeoutMs = DEFAULT_TIMEOUT;
    private AsyncContextEvent _event;
    private Thread _onTimeoutThread;

    protected HttpChannelState(HttpChannel channel) {
        this._channel = channel;
    }

    AutoLock lock() {
        return this._lock.lock();
    }

    public State getState() {
        try (AutoLock l = this.lock();){
            State state = this._state;
            return state;
        }
    }

    public void addListener(AsyncListener listener) {
        try (AutoLock l = this.lock();){
            if (this._asyncListeners == null) {
                this._asyncListeners = new ArrayList<AsyncListener>();
            }
            this._asyncListeners.add(listener);
        }
    }

    public boolean hasListener(AsyncListener listener) {
        try (AutoLock ignored = this.lock();){
            if (this._asyncListeners == null) {
                boolean bl = false;
                return bl;
            }
            for (AsyncListener l : this._asyncListeners) {
                if (l == listener) {
                    boolean bl = true;
                    return bl;
                }
                if (!(l instanceof AsyncContextState.WrappedAsyncListener) || ((AsyncContextState.WrappedAsyncListener)l).getListener() != listener) continue;
                boolean bl = true;
                return bl;
            }
            boolean bl = false;
            return bl;
        }
    }

    public boolean isSendError() {
        try (AutoLock l = this.lock();){
            boolean bl = this._sendError;
            return bl;
        }
    }

    public void setTimeout(long ms) {
        try (AutoLock l = this.lock();){
            this._timeoutMs = ms;
        }
    }

    public long getTimeout() {
        try (AutoLock l = this.lock();){
            long l2 = this._timeoutMs;
            return l2;
        }
    }

    public AsyncContextEvent getAsyncContextEvent() {
        try (AutoLock l = this.lock();){
            AsyncContextEvent asyncContextEvent = this._event;
            return asyncContextEvent;
        }
    }

    public String toString() {
        try (AutoLock l = this.lock();){
            String string = this.toStringLocked();
            return string;
        }
    }

    private String toStringLocked() {
        return String.format("%s@%x{%s}", this.getClass().getSimpleName(), this.hashCode(), this.getStatusStringLocked());
    }

    private String getStatusStringLocked() {
        return String.format("s=%s rs=%s os=%s is=%s awp=%b se=%b i=%b al=%d", new Object[]{this._state, this._requestState, this._outputState, this._inputState, this._asyncWritePossible, this._sendError, this._initial, this._asyncListeners == null ? 0 : this._asyncListeners.size()});
    }

    public String getStatusString() {
        try (AutoLock l = this.lock();){
            String string = this.getStatusStringLocked();
            return string;
        }
    }

    public boolean commitResponse() {
        try (AutoLock l = this.lock();){
            switch (this._outputState) {
                case OPEN: {
                    this._outputState = OutputState.COMMITTED;
                    boolean bl = true;
                    return bl;
                }
            }
            boolean bl = false;
            return bl;
        }
    }

    public boolean partialResponse() {
        try (AutoLock l = this.lock();){
            switch (this._outputState) {
                case COMMITTED: {
                    this._outputState = OutputState.OPEN;
                    boolean bl = true;
                    return bl;
                }
            }
            boolean bl = false;
            return bl;
        }
    }

    public boolean completeResponse() {
        try (AutoLock l = this.lock();){
            switch (this._outputState) {
                case OPEN: 
                case COMMITTED: {
                    this._outputState = OutputState.COMPLETED;
                    boolean bl = true;
                    return bl;
                }
            }
            boolean bl = false;
            return bl;
        }
    }

    public boolean isResponseCommitted() {
        try (AutoLock l = this.lock();){
            switch (this._outputState) {
                case OPEN: {
                    boolean bl = false;
                    return bl;
                }
            }
            boolean bl = true;
            return bl;
        }
    }

    public boolean isResponseCompleted() {
        try (AutoLock l = this.lock();){
            boolean bl = this._outputState == OutputState.COMPLETED;
            return bl;
        }
    }

    public boolean abortResponse() {
        try (AutoLock l = this.lock();){
            switch (this._outputState) {
                case ABORTED: {
                    boolean bl = false;
                    return bl;
                }
                case OPEN: {
                    this._channel.getResponse().setStatus(500);
                    this._outputState = OutputState.ABORTED;
                    boolean bl = true;
                    return bl;
                }
            }
            this._outputState = OutputState.ABORTED;
            boolean bl = true;
            return bl;
        }
    }

    public Action handling() {
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("handling {}", (Object)this.toStringLocked());
            }
            switch (this._state) {
                case IDLE: {
                    if (this._requestState != RequestState.BLOCKING) {
                        throw new IllegalStateException(this.getStatusStringLocked());
                    }
                    this._initial = true;
                    this._state = State.HANDLING;
                    Action action = Action.DISPATCH;
                    return action;
                }
                case WOKEN: {
                    if (this._event != null && this._event.getThrowable() != null && !this._sendError) {
                        this._state = State.HANDLING;
                        Action action = Action.ASYNC_ERROR;
                        return action;
                    }
                    Action action = this.nextAction(true);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("nextAction(true) {} {}", (Object)action, (Object)this.toStringLocked());
                    }
                    Action action2 = action;
                    return action2;
                }
            }
            throw new IllegalStateException(this.getStatusStringLocked());
        }
    }

    protected Action unhandle() {
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("unhandle {}", (Object)this.toStringLocked());
            }
            if (this._state != State.HANDLING) {
                throw new IllegalStateException(this.getStatusStringLocked());
            }
            this._initial = false;
            Action action = this.nextAction(false);
            if (LOG.isDebugEnabled()) {
                LOG.debug("nextAction(false) {} {}", (Object)action, (Object)this.toStringLocked());
            }
            Action action2 = action;
            return action2;
        }
    }

    private Action nextAction(boolean handling) {
        this._state = State.HANDLING;
        if (this._sendError) {
            switch (this._requestState) {
                case BLOCKING: 
                case ASYNC: 
                case COMPLETE: 
                case DISPATCH: 
                case COMPLETING: {
                    this._requestState = RequestState.BLOCKING;
                    this._sendError = false;
                    return Action.SEND_ERROR;
                }
            }
        }
        switch (this._requestState) {
            case BLOCKING: {
                if (handling) {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
                this._requestState = RequestState.COMPLETING;
                return Action.COMPLETE;
            }
            case ASYNC: {
                switch (this._inputState) {
                    case IDLE: 
                    case UNREADY: {
                        break;
                    }
                    case READY: {
                        this._inputState = InputState.IDLE;
                        return Action.READ_CALLBACK;
                    }
                    default: {
                        throw new IllegalStateException(this.getStatusStringLocked());
                    }
                }
                if (this._asyncWritePossible) {
                    this._asyncWritePossible = false;
                    return Action.WRITE_CALLBACK;
                }
                Scheduler scheduler = this._channel.getScheduler();
                if (scheduler != null && this._timeoutMs > 0L && !this._event.hasTimeoutTask()) {
                    this._event.setTimeoutTask(scheduler.schedule(this._event, this._timeoutMs, TimeUnit.MILLISECONDS));
                }
                this._state = State.WAITING;
                return Action.WAIT;
            }
            case DISPATCH: {
                this._requestState = RequestState.BLOCKING;
                return Action.ASYNC_DISPATCH;
            }
            case EXPIRE: {
                this._requestState = RequestState.EXPIRING;
                return Action.ASYNC_TIMEOUT;
            }
            case EXPIRING: {
                if (handling) {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
                this.sendError(500, "AsyncContext timeout");
                this._requestState = RequestState.BLOCKING;
                this._sendError = false;
                return Action.SEND_ERROR;
            }
            case COMPLETE: {
                this._requestState = RequestState.COMPLETING;
                return Action.COMPLETE;
            }
            case COMPLETING: {
                this._state = State.WAITING;
                return Action.WAIT;
            }
            case COMPLETED: {
                this._state = State.IDLE;
                return Action.TERMINATED;
            }
        }
        throw new IllegalStateException(this.getStatusStringLocked());
    }

    public void startAsync(final AsyncContextEvent event) {
        List<AsyncListener> lastAsyncListeners;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("startAsync {}", (Object)this.toStringLocked());
            }
            if (this._state != State.HANDLING || this._requestState != RequestState.BLOCKING) {
                throw new IllegalStateException(this.getStatusStringLocked());
            }
            this._requestState = RequestState.ASYNC;
            this._event = event;
            lastAsyncListeners = this._asyncListeners;
            this._asyncListeners = null;
        }
        if (lastAsyncListeners != null) {
            Runnable callback = new Runnable(){

                @Override
                public void run() {
                    for (AsyncListener listener : lastAsyncListeners) {
                        try {
                            listener.onStartAsync(event);
                        }
                        catch (Throwable e) {
                            LOG.warn("Async dispatch error", e);
                        }
                    }
                }

                public String toString() {
                    return "startAsync";
                }
            };
            this.runInContext(event, callback);
        }
    }

    public void dispatch(ServletContext context, String path) {
        AsyncContextEvent event;
        boolean dispatch = false;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("dispatch {} -> {}", (Object)this.toStringLocked(), (Object)path);
            }
            switch (this._requestState) {
                case ASYNC: {
                    break;
                }
                case EXPIRING: {
                    if (Thread.currentThread() == this._onTimeoutThread) break;
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
                default: {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
            }
            if (context != null) {
                this._event.setDispatchContext(context);
            }
            if (path != null) {
                this._event.setDispatchPath(path);
            }
            if (this._requestState == RequestState.ASYNC && this._state == State.WAITING) {
                this._state = State.WOKEN;
                dispatch = true;
            }
            this._requestState = RequestState.DISPATCH;
            event = this._event;
        }
        this.cancelTimeout(event);
        if (dispatch) {
            this.scheduleDispatch();
        }
    }

    protected void timeout() {
        boolean dispatch = false;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("Timeout {}", (Object)this.toStringLocked());
            }
            if (this._requestState != RequestState.ASYNC) {
                return;
            }
            this._requestState = RequestState.EXPIRE;
            if (this._state == State.WAITING) {
                this._state = State.WOKEN;
                dispatch = true;
            }
        }
        if (dispatch) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Dispatch after async timeout {}", (Object)this);
            }
            this.scheduleDispatch();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void onTimeout() {
        List<AsyncListener> listeners;
        AsyncContextEvent event;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("onTimeout {}", (Object)this.toStringLocked());
            }
            if (this._requestState != RequestState.EXPIRING || this._state != State.HANDLING) {
                throw new IllegalStateException(this.toStringLocked());
            }
            event = this._event;
            listeners = this._asyncListeners;
            this._onTimeoutThread = Thread.currentThread();
        }
        try {
            if (listeners != null) {
                Runnable task = new Runnable(){

                    @Override
                    public void run() {
                        for (AsyncListener listener : listeners) {
                            try {
                                listener.onTimeout(event);
                            }
                            catch (Throwable x) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.warn("{} while invoking onTimeout listener {}", x.toString(), listener, x);
                                    continue;
                                }
                                LOG.warn("{} while invoking onTimeout listener {}", (Object)x.toString(), (Object)listener);
                            }
                        }
                    }

                    public String toString() {
                        return "onTimeout";
                    }
                };
                this.runInContext(event, task);
            }
        }
        finally {
            l = this.lock();
            try {
                this._onTimeoutThread = null;
            }
            finally {
                if (l != null) {
                    l.close();
                }
            }
        }
    }

    public void complete() {
        AsyncContextEvent event;
        boolean handle2 = false;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("complete {}", (Object)this.toStringLocked());
            }
            event = this._event;
            switch (this._requestState) {
                case EXPIRING: {
                    if (Thread.currentThread() != this._onTimeoutThread) {
                        throw new IllegalStateException(this.getStatusStringLocked());
                    }
                    this._requestState = this._sendError ? RequestState.BLOCKING : RequestState.COMPLETE;
                    break;
                }
                case ASYNC: {
                    this._requestState = this._sendError ? RequestState.BLOCKING : RequestState.COMPLETE;
                    break;
                }
                case COMPLETE: {
                    return;
                }
                default: {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
            }
            if (this._state == State.WAITING) {
                handle2 = true;
                this._state = State.WOKEN;
            }
        }
        this.cancelTimeout(event);
        if (handle2) {
            this.runInContext(event, this._channel);
        }
    }

    public void asyncError(Throwable failure) {
        AsyncContextEvent event = null;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("asyncError {}", (Object)this.toStringLocked(), (Object)failure);
            }
            if (this._state == State.WAITING && this._requestState == RequestState.ASYNC) {
                this._state = State.WOKEN;
                this._event.addThrowable(failure);
                event = this._event;
            } else {
                if (!(failure instanceof QuietException)) {
                    LOG.warn(failure.toString());
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Async error", failure);
                }
            }
        }
        if (event != null) {
            this.cancelTimeout(event);
            this.runInContext(event, this._channel);
        }
    }

    /*
     * Unable to fully structure code
     */
    protected void onError(Throwable th) {
        l = this.lock();
        try {
            if (HttpChannelState.LOG.isDebugEnabled()) {
                HttpChannelState.LOG.debug("thrownException {}", (Object)this.getStatusStringLocked(), (Object)th);
            }
            if (this._state != State.HANDLING) {
                throw new IllegalStateException(this.getStatusStringLocked());
            }
            if (this._sendError) {
                HttpChannelState.LOG.warn("unhandled due to prior sendError", th);
                return;
            }
            switch (3.$SwitchMap$org$eclipse$jetty$server$HttpChannelState$RequestState[this._requestState.ordinal()]) {
                case 1: {
                    this.sendError(th);
                    return;
                }
                case 2: 
                case 3: 
                case 4: {
                    if (this._asyncListeners == null || this._asyncListeners.isEmpty()) {
                        this.sendError(th);
                        return;
                    }
                    asyncEvent = this._event;
                    asyncEvent.addThrowable(th);
                    asyncListeners = this._asyncListeners;
                    ** break;
lbl22:
                    // 1 sources

                    break;
                }
                default: {
                    HttpChannelState.LOG.warn("unhandled in state {}", (Object)this._requestState, (Object)new IllegalStateException(th));
                    return;
                }
            }
        }
        finally {
            if (l != null) {
                l.close();
            }
        }
        this.runInContext(asyncEvent, (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onError$0(java.util.List org.eclipse.jetty.server.AsyncContextEvent ), ()V)(asyncListeners, (AsyncContextEvent)asyncEvent));
        l = this.lock();
        try {
            if (this._requestState == RequestState.ASYNC && !this._sendError) {
                this.sendError(th);
            } else if (this._requestState != RequestState.COMPLETE) {
                HttpChannelState.LOG.warn("unhandled in state {}", (Object)this._requestState, (Object)new IllegalStateException(th));
            }
        }
        finally {
            if (l != null) {
                l.close();
            }
        }
    }

    private void sendError(Throwable th) {
        String message;
        int code;
        Request request = this._channel.getRequest();
        Throwable cause = this._channel.unwrap(th, BadMessageException.class, UnavailableException.class);
        if (cause == null) {
            code = 500;
            message = th.toString();
        } else if (cause instanceof BadMessageException) {
            BadMessageException bme = (BadMessageException)cause;
            code = bme.getCode();
            message = bme.getReason();
        } else if (cause instanceof UnavailableException) {
            message = cause.toString();
            code = ((UnavailableException)cause).isPermanent() ? 404 : 503;
        } else {
            code = 500;
            message = null;
        }
        this.sendError(code, message);
        request.setAttribute("jakarta.servlet.error.exception", th);
        request.setAttribute("jakarta.servlet.error.exception_type", th.getClass());
        this._requestState = RequestState.BLOCKING;
    }

    public void sendError(int code, String message) {
        Request request = this._channel.getRequest();
        Response response = this._channel.getResponse();
        if (message == null) {
            message = HttpStatus.getMessage(code);
        }
        try (AutoLock l = this.lock();){
            Throwable cause;
            if (LOG.isDebugEnabled()) {
                LOG.debug("sendError {}", (Object)this.toStringLocked());
            }
            if (this._outputState != OutputState.OPEN) {
                throw new IllegalStateException(this._outputState.toString());
            }
            switch (this._state) {
                case WOKEN: 
                case HANDLING: 
                case WAITING: {
                    break;
                }
                default: {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
            }
            response.setStatus(code);
            response.errorClose();
            request.setAttribute("org.eclipse.jetty.server.error_context", request.getErrorContext());
            request.setAttribute("jakarta.servlet.error.request_uri", request.getRequestURI());
            request.setAttribute("jakarta.servlet.error.servlet_name", request.getServletName());
            request.setAttribute("jakarta.servlet.error.status_code", code);
            request.setAttribute("jakarta.servlet.error.message", message);
            this._sendError = true;
            if (this._event != null && (cause = (Throwable)request.getAttribute("jakarta.servlet.error.exception")) != null) {
                this._event.addThrowable(cause);
            }
        }
    }

    protected void completing() {
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("completing {}", (Object)this.toStringLocked());
            }
            switch (this._requestState) {
                case COMPLETED: {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
            }
            this._requestState = RequestState.COMPLETING;
        }
    }

    protected void completed(Throwable failure) {
        AsyncContextEvent event;
        List<AsyncListener> aListeners;
        boolean handle2 = false;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("completed {}", (Object)this.toStringLocked());
            }
            if (this._requestState != RequestState.COMPLETING) {
                throw new IllegalStateException(this.getStatusStringLocked());
            }
            if (this._event == null) {
                this._requestState = RequestState.COMPLETED;
                aListeners = null;
                event = null;
                if (this._state == State.WAITING) {
                    this._state = State.WOKEN;
                    handle2 = true;
                }
            } else {
                aListeners = this._asyncListeners;
                event = this._event;
            }
        }
        this._channel.getResponse().getHttpOutput().completed(failure);
        if (event != null) {
            this.cancelTimeout(event);
            if (aListeners != null) {
                this.runInContext(event, () -> {
                    for (AsyncListener listener : aListeners) {
                        try {
                            listener.onComplete(event);
                        }
                        catch (Throwable x) {
                            if (LOG.isDebugEnabled()) {
                                LOG.warn("{} while invoking onComplete listener {}", x.toString(), listener, x);
                                continue;
                            }
                            LOG.warn("{} while invoking onComplete listener {}", (Object)x.toString(), (Object)listener);
                        }
                    }
                });
            }
            event.completed();
            l = this.lock();
            try {
                this._requestState = RequestState.COMPLETED;
                if (this._state == State.WAITING) {
                    this._state = State.WOKEN;
                    handle2 = true;
                }
            }
            finally {
                if (l != null) {
                    l.close();
                }
            }
        }
        if (handle2) {
            this._channel.handle();
        }
    }

    protected void recycle() {
        this.cancelTimeout();
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("recycle {}", (Object)this.toStringLocked());
            }
            switch (this._state) {
                case HANDLING: {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
                case UPGRADED: {
                    return;
                }
            }
            this._asyncListeners = null;
            this._state = State.IDLE;
            this._requestState = RequestState.BLOCKING;
            this._outputState = OutputState.OPEN;
            this._initial = true;
            this._inputState = InputState.IDLE;
            this._asyncWritePossible = false;
            this._timeoutMs = DEFAULT_TIMEOUT;
            this._event = null;
        }
    }

    public void upgrade() {
        this.cancelTimeout();
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("upgrade {}", (Object)this.toStringLocked());
            }
            switch (this._state) {
                case IDLE: {
                    break;
                }
                default: {
                    throw new IllegalStateException(this.getStatusStringLocked());
                }
            }
            this._asyncListeners = null;
            this._state = State.UPGRADED;
            this._requestState = RequestState.BLOCKING;
            this._initial = true;
            this._inputState = InputState.IDLE;
            this._asyncWritePossible = false;
            this._timeoutMs = DEFAULT_TIMEOUT;
            this._event = null;
        }
    }

    protected void scheduleDispatch() {
        this._channel.execute(this._channel);
    }

    protected void cancelTimeout() {
        this.cancelTimeout(this.getAsyncContextEvent());
    }

    protected void cancelTimeout(AsyncContextEvent event) {
        if (event != null) {
            event.cancelTimeoutTask();
        }
    }

    public boolean isIdle() {
        try (AutoLock l = this.lock();){
            boolean bl = this._state == State.IDLE;
            return bl;
        }
    }

    public boolean isExpired() {
        try (AutoLock l = this.lock();){
            boolean bl = this._requestState == RequestState.EXPIRE || this._requestState == RequestState.EXPIRING;
            return bl;
        }
    }

    public boolean isInitial() {
        try (AutoLock l = this.lock();){
            boolean bl = this._initial;
            return bl;
        }
    }

    public boolean isSuspended() {
        try (AutoLock l = this.lock();){
            boolean bl = this._state == State.WAITING || this._state == State.HANDLING && this._requestState == RequestState.ASYNC;
            return bl;
        }
    }

    boolean isCompleted() {
        try (AutoLock l = this.lock();){
            boolean bl = this._requestState == RequestState.COMPLETED;
            return bl;
        }
    }

    public boolean isAsyncStarted() {
        try (AutoLock l = this.lock();){
            if (this._state == State.HANDLING) {
                boolean bl = this._requestState != RequestState.BLOCKING;
                return bl;
            }
            boolean bl = this._requestState == RequestState.ASYNC || this._requestState == RequestState.EXPIRING;
            return bl;
        }
    }

    public boolean isAsync() {
        try (AutoLock l = this.lock();){
            boolean bl = !this._initial || this._requestState != RequestState.BLOCKING;
            return bl;
        }
    }

    public Request getBaseRequest() {
        return this._channel.getRequest();
    }

    public HttpChannel getHttpChannel() {
        return this._channel;
    }

    public ContextHandler getContextHandler() {
        return this.getContextHandler(this.getAsyncContextEvent());
    }

    ContextHandler getContextHandler(AsyncContextEvent event) {
        ContextHandler.Context context;
        if (event != null && (context = (ContextHandler.Context)event.getServletContext()) != null) {
            return context.getContextHandler();
        }
        return null;
    }

    public ServletResponse getServletResponse() {
        return this.getServletResponse(this.getAsyncContextEvent());
    }

    public ServletResponse getServletResponse(AsyncContextEvent event) {
        if (event != null && event.getSuppliedResponse() != null) {
            return event.getSuppliedResponse();
        }
        return this._channel.getResponse();
    }

    void runInContext(AsyncContextEvent event, Runnable runnable2) {
        ContextHandler contextHandler = this.getContextHandler(event);
        if (contextHandler == null) {
            runnable2.run();
        } else {
            contextHandler.handle(this._channel.getRequest(), runnable2);
        }
    }

    public Object getAttribute(String name) {
        return this._channel.getRequest().getAttribute(name);
    }

    public void removeAttribute(String name) {
        this._channel.getRequest().removeAttribute(name);
    }

    public void setAttribute(String name, Object attribute) {
        this._channel.getRequest().setAttribute(name, attribute);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onReadReady() {
        boolean woken = false;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("onReadReady {}", (Object)this.toStringLocked());
            }
            switch (this._inputState) {
                case READY: {
                    this._inputState = InputState.READY;
                    return woken;
                }
                case IDLE: 
                case UNREADY: {
                    this._inputState = InputState.READY;
                    if (this._state != State.WAITING) return woken;
                    woken = true;
                    this._state = State.WOKEN;
                    return woken;
                }
                default: {
                    throw new IllegalStateException(this.toStringLocked());
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onReadEof() {
        boolean woken = false;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("onReadEof {}", (Object)this.toStringLocked());
            }
            switch (this._inputState) {
                case IDLE: 
                case UNREADY: 
                case READY: {
                    this._inputState = InputState.READY;
                    if (this._state != State.WAITING) return woken;
                    woken = true;
                    this._state = State.WOKEN;
                    return woken;
                }
                default: {
                    throw new IllegalStateException(this.toStringLocked());
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onContentAdded() {
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("onContentAdded {}", (Object)this.toStringLocked());
            }
            switch (this._inputState) {
                case IDLE: 
                case UNREADY: 
                case READY: {
                    this._inputState = InputState.READY;
                    return;
                }
                default: {
                    throw new IllegalStateException(this.toStringLocked());
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onReadIdle() {
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("onReadIdle {}", (Object)this.toStringLocked());
            }
            switch (this._inputState) {
                case IDLE: 
                case UNREADY: 
                case READY: {
                    this._inputState = InputState.IDLE;
                    return;
                }
                default: {
                    throw new IllegalStateException(this.toStringLocked());
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onReadUnready() {
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("onReadUnready {}", (Object)this.toStringLocked());
            }
            switch (this._inputState) {
                case IDLE: 
                case UNREADY: 
                case READY: {
                    this._inputState = InputState.UNREADY;
                    return;
                }
                default: {
                    throw new IllegalStateException(this.toStringLocked());
                }
            }
        }
    }

    public boolean isInputUnready() {
        try (AutoLock l = this.lock();){
            boolean bl = this._inputState == InputState.UNREADY;
            return bl;
        }
    }

    public boolean onWritePossible() {
        boolean wake = false;
        try (AutoLock l = this.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("onWritePossible {}", (Object)this.toStringLocked());
            }
            this._asyncWritePossible = true;
            if (this._state == State.WAITING) {
                this._state = State.WOKEN;
                wake = true;
            }
        }
        return wake;
    }

    private static /* synthetic */ void lambda$onError$0(List asyncListeners, AsyncContextEvent asyncEvent) {
        for (AsyncListener listener : asyncListeners) {
            try {
                listener.onError(asyncEvent);
            }
            catch (Throwable x) {
                if (LOG.isDebugEnabled()) {
                    LOG.warn("{} while invoking onError listener {}", x.toString(), listener, x);
                    continue;
                }
                LOG.warn("{} while invoking onError listener {}", (Object)x.toString(), (Object)listener);
            }
        }
    }

    public static enum State {
        IDLE,
        HANDLING,
        WAITING,
        WOKEN,
        UPGRADED;

    }

    private static enum RequestState {
        BLOCKING,
        ASYNC,
        DISPATCH,
        EXPIRE,
        EXPIRING,
        COMPLETE,
        COMPLETING,
        COMPLETED;

    }

    private static enum OutputState {
        OPEN,
        COMMITTED,
        COMPLETED,
        ABORTED;

    }

    private static enum InputState {
        IDLE,
        UNREADY,
        READY;

    }

    public static enum Action {
        DISPATCH,
        ASYNC_DISPATCH,
        SEND_ERROR,
        ASYNC_ERROR,
        ASYNC_TIMEOUT,
        WRITE_CALLBACK,
        READ_CALLBACK,
        COMPLETE,
        TERMINATED,
        WAIT;

    }
}

