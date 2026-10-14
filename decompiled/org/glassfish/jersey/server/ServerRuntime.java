/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.Closure;
import org.glassfish.jersey.internal.util.Producer;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.internal.util.collection.Refs;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.message.internal.HeaderValueException;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.process.internal.RequestContext;
import org.glassfish.jersey.process.internal.RequestScope;
import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.process.internal.Stages;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.BackgroundSchedulerLiteral;
import org.glassfish.jersey.server.ChunkedOutput;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.ManagedAsyncExecutorLiteral;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.TracingUtils;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.ProcessingProviders;
import org.glassfish.jersey.server.internal.ServerTraceEvent;
import org.glassfish.jersey.server.internal.monitoring.EmptyRequestEventBuilder;
import org.glassfish.jersey.server.internal.monitoring.RequestEventBuilder;
import org.glassfish.jersey.server.internal.monitoring.RequestEventImpl;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.process.MappableException;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;
import org.glassfish.jersey.server.spi.ExternalRequestContext;
import org.glassfish.jersey.server.spi.ExternalRequestScope;
import org.glassfish.jersey.server.spi.ResponseErrorMapper;
import org.glassfish.jersey.spi.ExceptionMappers;

public class ServerRuntime {
    private final Stage<RequestProcessingContext> requestProcessingRoot;
    private final ProcessingProviders processingProviders;
    private final InjectionManager injectionManager;
    private final ScheduledExecutorService backgroundScheduler;
    private final Provider<ExecutorService> managedAsyncExecutor;
    private final RequestScope requestScope;
    private final ExceptionMappers exceptionMappers;
    private final ApplicationEventListener applicationEventListener;
    private final Configuration configuration;
    private final ExternalRequestScope externalRequestScope;
    private final TracingConfig tracingConfig;
    private final TracingLogger.Level tracingThreshold;
    private final boolean processResponseErrors;
    private final boolean disableLocationHeaderRelativeUriResolution;
    private final boolean rfc7231LocationHeaderRelativeUriResolution;

    static ServerRuntime createServerRuntime(InjectionManager injectionManager, ServerBootstrapBag bootstrapBag, Stage<RequestProcessingContext> processingRoot, ApplicationEventListener eventListener, ProcessingProviders processingProviders) {
        ScheduledExecutorService scheduledExecutorServiceSupplier = injectionManager.getInstance(ScheduledExecutorService.class, BackgroundSchedulerLiteral.INSTANCE);
        Provider<ExecutorService> asyncExecutorServiceSupplier = () -> injectionManager.getInstance(ExecutorService.class, ManagedAsyncExecutorLiteral.INSTANCE);
        return new ServerRuntime(processingRoot, processingProviders, injectionManager, scheduledExecutorServiceSupplier, asyncExecutorServiceSupplier, bootstrapBag.getRequestScope(), bootstrapBag.getExceptionMappers(), eventListener, injectionManager.getInstance(ExternalRequestScope.class), bootstrapBag.getConfiguration());
    }

    private ServerRuntime(Stage<RequestProcessingContext> requestProcessingRoot, ProcessingProviders processingProviders, InjectionManager injectionManager, ScheduledExecutorService backgroundScheduler, Provider<ExecutorService> managedAsyncExecutorProvider, RequestScope requestScope, ExceptionMappers exceptionMappers, ApplicationEventListener applicationEventListener, ExternalRequestScope externalScope, Configuration configuration) {
        this.requestProcessingRoot = requestProcessingRoot;
        this.processingProviders = processingProviders;
        this.injectionManager = injectionManager;
        this.backgroundScheduler = backgroundScheduler;
        this.managedAsyncExecutor = managedAsyncExecutorProvider;
        this.requestScope = requestScope;
        this.exceptionMappers = exceptionMappers;
        this.applicationEventListener = applicationEventListener;
        this.externalRequestScope = externalScope;
        this.configuration = configuration;
        this.tracingConfig = TracingUtils.getTracingConfig(configuration);
        this.tracingThreshold = TracingUtils.getTracingThreshold(configuration);
        this.processResponseErrors = PropertiesHelper.isProperty(configuration.getProperty("jersey.config.server.exception.processResponseErrors"));
        this.disableLocationHeaderRelativeUriResolution = ServerProperties.getValue(configuration.getProperties(), "jersey.config.server.headers.location.relative.resolution.disabled", Boolean.FALSE, Boolean.class);
        this.rfc7231LocationHeaderRelativeUriResolution = ServerProperties.getValue(configuration.getProperties(), "jersey.config.server.headers.location.relative.resolution.rfc7231", Boolean.FALSE, Boolean.class);
    }

    public void process(final ContainerRequest request) {
        TracingUtils.initTracingSupport(this.tracingConfig, this.tracingThreshold, request);
        TracingUtils.logStart(request);
        UriRoutingContext routingContext = request.getUriRoutingContext();
        RequestEventBuilder monitoringEventBuilder = EmptyRequestEventBuilder.INSTANCE;
        RequestEventListener monitoringEventListener = null;
        if (this.applicationEventListener != null) {
            monitoringEventBuilder = new RequestEventImpl.Builder().setContainerRequest(request).setExtendedUriInfo(routingContext);
            monitoringEventListener = this.applicationEventListener.onRequest(monitoringEventBuilder.build(RequestEvent.Type.START));
        }
        request.setProcessingProviders(this.processingProviders);
        final RequestProcessingContext context = new RequestProcessingContext(this.injectionManager, request, routingContext, monitoringEventBuilder, monitoringEventListener);
        request.checkState();
        final Responder responder = new Responder(context, this);
        RequestContext requestScopeInstance = this.requestScope.createContext();
        final AsyncResponderHolder asyncResponderHolder = new AsyncResponderHolder(responder, this.externalRequestScope, requestScopeInstance, this.externalRequestScope.open(this.injectionManager));
        context.initAsyncContext(asyncResponderHolder);
        this.requestScope.runInScope(requestScopeInstance, new Runnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                try {
                    if (!ServerRuntime.this.disableLocationHeaderRelativeUriResolution) {
                        URI uriToUse = ServerRuntime.this.rfc7231LocationHeaderRelativeUriResolution ? request.getRequestUri() : request.getBaseUri();
                        OutboundJaxrsResponse.Builder.setBaseUri(uriToUse);
                    }
                    Ref endpointRef = Refs.emptyRef();
                    RequestProcessingContext data = Stages.process(context, ServerRuntime.this.requestProcessingRoot, endpointRef);
                    Endpoint endpoint = (Endpoint)endpointRef.get();
                    if (endpoint == null) {
                        throw new NotFoundException();
                    }
                    ContainerResponse response = (ContainerResponse)endpoint.apply(data);
                    if (!asyncResponderHolder.isAsync()) {
                        responder.process(response);
                    } else {
                        ServerRuntime.this.externalRequestScope.suspend(asyncResponderHolder.externalContext, ServerRuntime.this.injectionManager);
                    }
                }
                catch (Throwable throwable) {
                    responder.process(throwable);
                }
                finally {
                    asyncResponderHolder.release();
                    OutboundJaxrsResponse.Builder.clearBaseUri();
                }
            }
        });
    }

    ScheduledExecutorService getBackgroundScheduler() {
        return this.backgroundScheduler;
    }

    private static void ensureAbsolute(URI location, MultivaluedMap<String, Object> headers, ContainerRequest request, boolean incompatible) {
        if (location == null || location.isAbsolute()) {
            return;
        }
        URI uri = incompatible ? request.getRequestUri() : request.getBaseUri();
        headers.putSingle("Location", uri.resolve(location));
    }

    private static class ThrowableWrap {
        private final Throwable original;
        private Throwable wrapped = null;
        private Throwable current;
        private boolean inMappable = false;

        private ThrowableWrap(Throwable original) {
            this.original = original;
            this.current = original;
        }

        private Throwable getOriginal() {
            return this.original;
        }

        private Throwable getWrappedOrCurrent() {
            return this.wrapped != null ? this.wrapped : this.current;
        }

        private Throwable getCurrent() {
            return this.current;
        }

        private boolean isWrapped() {
            boolean isConcurrentWrap = CompletionException.class.isInstance(this.current) || ExecutionException.class.isInstance(this.current);
            return isConcurrentWrap;
        }

        private Throwable unwrap() {
            if (this.wrapped == null) {
                this.wrapped = this.current;
            }
            this.current = this.current.getCause();
            return this.current;
        }

        private boolean tryMappableException() {
            if (MappableException.class.isInstance(this.original)) {
                this.inMappable = true;
                this.current = this.original.getCause();
                return true;
            }
            return false;
        }

        private boolean isInMappable() {
            return this.inMappable;
        }
    }

    private static class ConnectionCallbackRunner
    extends AbstractCallbackRunner<ConnectionCallback>
    implements ConnectionCallback {
        private static final Logger LOGGER = Logger.getLogger(ConnectionCallbackRunner.class.getName());

        private ConnectionCallbackRunner() {
            super(LOGGER);
        }

        @Override
        public Class<?> getCallbackContract() {
            return ConnectionCallback.class;
        }

        @Override
        public void onDisconnect(final AsyncResponse disconnected) {
            this.executeCallbacks(new Closure<ConnectionCallback>(){

                @Override
                public void invoke(ConnectionCallback callback) {
                    callback.onDisconnect(disconnected);
                }
            });
        }
    }

    private static class CompletionCallbackRunner
    extends AbstractCallbackRunner<CompletionCallback>
    implements CompletionCallback {
        private static final Logger LOGGER = Logger.getLogger(CompletionCallbackRunner.class.getName());

        private CompletionCallbackRunner() {
            super(LOGGER);
        }

        @Override
        public Class<?> getCallbackContract() {
            return CompletionCallback.class;
        }

        @Override
        public void onComplete(final Throwable throwable) {
            this.executeCallbacks(new Closure<CompletionCallback>(){

                @Override
                public void invoke(CompletionCallback callback) {
                    callback.onComplete(throwable);
                }
            });
        }
    }

    static abstract class AbstractCallbackRunner<T> {
        private final Queue<T> callbacks = new ConcurrentLinkedQueue<T>();
        private final Logger logger;

        protected AbstractCallbackRunner(Logger logger2) {
            this.logger = logger2;
        }

        public final boolean supports(Class<?> callbackClass) {
            return this.getCallbackContract().isAssignableFrom(callbackClass);
        }

        public abstract Class<?> getCallbackContract();

        public boolean register(Object callback) {
            return this.callbacks.offer(callback);
        }

        protected final void executeCallbacks(Closure<T> invoker) {
            for (Object callback : this.callbacks) {
                try {
                    invoker.invoke(callback);
                }
                catch (Throwable t2) {
                    this.logger.log(Level.WARNING, LocalizationMessages.ERROR_ASYNC_CALLBACK_FAILED(callback.getClass().getName()), t2);
                }
            }
        }
    }

    private static class AsyncResponder
    implements AsyncContext,
    ContainerResponseWriter.TimeoutHandler,
    CompletionCallback {
        private static final Logger LOGGER = Logger.getLogger(AsyncResponder.class.getName());
        private static final TimeoutHandler DEFAULT_TIMEOUT_HANDLER = new TimeoutHandler(){

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                throw new ServiceUnavailableException();
            }
        };
        private final Object stateLock = new Object();
        private AsyncContext.State state = AsyncContext.State.RUNNING;
        private boolean cancelled = false;
        private final Responder responder;
        private final RequestContext requestContext;
        private final ExternalRequestContext<?> foreignScopeInstance;
        private final ExternalRequestScope requestScopeListener;
        private volatile TimeoutHandler timeoutHandler = DEFAULT_TIMEOUT_HANDLER;
        private final List<AbstractCallbackRunner<?>> callbackRunners;

        public AsyncResponder(Responder responder, RequestContext requestContext, ExternalRequestScope requestScopeListener, ExternalRequestContext<?> foreignScopeInstance) {
            this.responder = responder;
            this.requestContext = requestContext;
            this.foreignScopeInstance = foreignScopeInstance;
            this.requestScopeListener = requestScopeListener;
            this.callbackRunners = Collections.unmodifiableList(Arrays.asList(responder.completionCallbackRunner, responder.connectionCallbackRunner));
            responder.completionCallbackRunner.register(this);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onTimeout(ContainerResponseWriter responseWriter) {
            TimeoutHandler handler = this.timeoutHandler;
            try {
                Object object = this.stateLock;
                synchronized (object) {
                    if (this.state == AsyncContext.State.SUSPENDED) {
                        handler.handleTimeout(this);
                    }
                }
            }
            catch (Throwable throwable) {
                this.resume(throwable);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onComplete(Throwable throwable) {
            Object object = this.stateLock;
            synchronized (object) {
                this.state = AsyncContext.State.COMPLETED;
            }
        }

        @Override
        public void invokeManaged(final Producer<Response> producer) {
            ((ExecutorService)this.responder.runtime.managedAsyncExecutor.get()).submit(new Runnable(){

                @Override
                public void run() {
                    responder.runtime.requestScope.runInScope(requestContext, new Runnable(){

                        @Override
                        public void run() {
                            try {
                                requestScopeListener.resume(foreignScopeInstance, responder.runtime.injectionManager);
                                Response response = (Response)producer.call();
                                if (response != null) {
                                    this.resume(response);
                                }
                            }
                            catch (Throwable t2) {
                                this.resume(t2);
                            }
                        }
                    });
                }
            });
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean suspend() {
            Object object = this.stateLock;
            synchronized (object) {
                if (this.state == AsyncContext.State.RUNNING && this.responder.processingContext.request().getResponseWriter().suspend(0L, TimeUnit.SECONDS, this)) {
                    this.state = AsyncContext.State.SUSPENDED;
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean resume(final Object response) {
            return this.resume(new Runnable(){

                @Override
                public void run() {
                    try {
                        Response jaxrsResponse;
                        requestScopeListener.resume(foreignScopeInstance, responder.runtime.injectionManager);
                        Response response2 = jaxrsResponse = response instanceof Response ? (Response)response : Response.ok(response).build();
                        if (!responder.runtime.disableLocationHeaderRelativeUriResolution) {
                            ServerRuntime.ensureAbsolute(jaxrsResponse.getLocation(), jaxrsResponse.getHeaders(), responder.processingContext.request(), responder.runtime.rfc7231LocationHeaderRelativeUriResolution);
                        }
                        responder.process(new ContainerResponse(responder.processingContext.request(), jaxrsResponse));
                    }
                    catch (Throwable t2) {
                        responder.process(t2);
                    }
                }
            });
        }

        @Override
        public boolean resume(final Throwable error) {
            return this.resume(new Runnable(){

                @Override
                public void run() {
                    try {
                        requestScopeListener.resume(foreignScopeInstance, responder.runtime.injectionManager);
                        responder.process(new MappableException(error));
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
            });
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean resume(Runnable handler) {
            Object object = this.stateLock;
            synchronized (object) {
                if (this.state != AsyncContext.State.SUSPENDED) {
                    return false;
                }
                this.state = AsyncContext.State.RESUMED;
            }
            try {
                this.responder.runtime.requestScope.runInScope(this.requestContext, handler);
            }
            finally {
                this.requestContext.release();
            }
            return true;
        }

        @Override
        public boolean cancel() {
            return this.cancel(new Value<Response>(){

                @Override
                public Response get() {
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                }
            });
        }

        @Override
        public boolean cancel(final int retryAfter) {
            return this.cancel(new Value<Response>(){

                @Override
                public Response get() {
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).header("Retry-After", retryAfter).build();
                }
            });
        }

        @Override
        public boolean cancel(final Date retryAfter) {
            return this.cancel(new Value<Response>(){

                @Override
                public Response get() {
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).header("Retry-After", retryAfter).build();
                }
            });
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean cancel(final Value<Response> responseValue) {
            Object object = this.stateLock;
            synchronized (object) {
                if (this.cancelled) {
                    return true;
                }
                if (this.state != AsyncContext.State.SUSPENDED) {
                    return false;
                }
                this.state = AsyncContext.State.RESUMED;
                this.cancelled = true;
            }
            this.responder.runtime.requestScope.runInScope(this.requestContext, new Runnable(){

                @Override
                public void run() {
                    try {
                        requestScopeListener.resume(foreignScopeInstance, responder.runtime.injectionManager);
                        Response response = (Response)responseValue.get();
                        responder.process(new ContainerResponse(responder.processingContext.request(), response));
                    }
                    catch (Throwable t2) {
                        responder.process(t2);
                    }
                }
            });
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean isRunning() {
            Object object = this.stateLock;
            synchronized (object) {
                return this.state == AsyncContext.State.RUNNING;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isSuspended() {
            Object object = this.stateLock;
            synchronized (object) {
                return this.state == AsyncContext.State.SUSPENDED;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isCancelled() {
            Object object = this.stateLock;
            synchronized (object) {
                return this.cancelled;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isDone() {
            Object object = this.stateLock;
            synchronized (object) {
                return this.state == AsyncContext.State.COMPLETED;
            }
        }

        @Override
        public boolean setTimeout(long time, TimeUnit unit) {
            try {
                this.responder.processingContext.request().getResponseWriter().setSuspendTimeout(time, unit);
                return true;
            }
            catch (IllegalStateException ex) {
                LOGGER.log(Level.FINER, "Unable to set timeout on the AsyncResponse.", ex);
                return false;
            }
        }

        @Override
        public void setTimeoutHandler(TimeoutHandler handler) {
            this.timeoutHandler = handler;
        }

        @Override
        public Collection<Class<?>> register(Class<?> callback) {
            Preconditions.checkNotNull(callback, LocalizationMessages.PARAM_NULL("callback"));
            return this.register(Injections.getOrCreate(this.responder.runtime.injectionManager, callback));
        }

        @Override
        public Map<Class<?>, Collection<Class<?>>> register(Class<?> callback, Class<?> ... callbacks) {
            Preconditions.checkNotNull(callback, LocalizationMessages.PARAM_NULL("callback"));
            Preconditions.checkNotNull(callbacks, LocalizationMessages.CALLBACK_ARRAY_NULL());
            for (Class<?> additionalCallback : callbacks) {
                Preconditions.checkNotNull(additionalCallback, LocalizationMessages.CALLBACK_ARRAY_ELEMENT_NULL());
            }
            HashMap results = new HashMap();
            results.put(callback, this.register(callback));
            for (Class<?> c : callbacks) {
                results.put(c, this.register(c));
            }
            return results;
        }

        @Override
        public Collection<Class<?>> register(Object callback) {
            Preconditions.checkNotNull(callback, LocalizationMessages.PARAM_NULL("callback"));
            LinkedList result = new LinkedList();
            for (AbstractCallbackRunner<?> runner : this.callbackRunners) {
                if (!runner.supports(callback.getClass()) || !runner.register(callback)) continue;
                result.add(runner.getCallbackContract());
            }
            return result;
        }

        @Override
        public Map<Class<?>, Collection<Class<?>>> register(Object callback, Object ... callbacks) {
            Preconditions.checkNotNull(callback, LocalizationMessages.PARAM_NULL("callback"));
            Preconditions.checkNotNull(callbacks, LocalizationMessages.CALLBACK_ARRAY_NULL());
            for (Object additionalCallback : callbacks) {
                Preconditions.checkNotNull(additionalCallback, LocalizationMessages.CALLBACK_ARRAY_ELEMENT_NULL());
            }
            HashMap results = new HashMap();
            results.put(callback.getClass(), this.register(callback));
            for (Object c : callbacks) {
                results.put(c.getClass(), this.register(c));
            }
            return results;
        }
    }

    private static class Responder {
        private static final Logger LOGGER = Logger.getLogger(Responder.class.getName());
        private final RequestProcessingContext processingContext;
        private final ServerRuntime runtime;
        private final CompletionCallbackRunner completionCallbackRunner = new CompletionCallbackRunner();
        private final ConnectionCallbackRunner connectionCallbackRunner = new ConnectionCallbackRunner();
        private final TracingLogger tracingLogger;

        public Responder(RequestProcessingContext processingContext, ServerRuntime runtime) {
            this.processingContext = processingContext;
            this.runtime = runtime;
            this.tracingLogger = TracingLogger.getInstance(processingContext.request());
        }

        public void process(ContainerResponse response) {
            this.processingContext.monitoringEventBuilder().setContainerResponse(response);
            response = this.processResponse(response);
            this.release(response);
        }

        private ContainerResponse processResponse(ContainerResponse response) {
            Stage<ContainerResponse> respondingRoot = this.processingContext.createRespondingRoot();
            if (respondingRoot != null) {
                response = Stages.process(response, respondingRoot);
            }
            this.writeResponse(response);
            this.completionCallbackRunner.onComplete(null);
            return response;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void process(Throwable throwable) {
            ContainerRequest request = this.processingContext.request();
            this.processingContext.monitoringEventBuilder().setException(throwable, RequestEvent.ExceptionCause.ORIGINAL);
            this.processingContext.triggerEvent(RequestEvent.Type.ON_EXCEPTION);
            ContainerResponse response = null;
            try {
                Response exceptionResponse = this.mapException(throwable);
                try {
                    try {
                        response = this.convertResponse(exceptionResponse);
                        if (!this.runtime.disableLocationHeaderRelativeUriResolution) {
                            ServerRuntime.ensureAbsolute(response.getLocation(), response.getHeaders(), request, this.runtime.rfc7231LocationHeaderRelativeUriResolution);
                        }
                        this.processingContext.monitoringEventBuilder().setContainerResponse(response).setResponseSuccessfullyMapped(true);
                    }
                    finally {
                        this.processingContext.triggerEvent(RequestEvent.Type.EXCEPTION_MAPPING_FINISHED);
                    }
                    this.processResponse(response);
                }
                catch (Throwable respError) {
                    LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_PROCESSING_RESPONSE_FROM_ALREADY_MAPPED_EXCEPTION());
                    this.processingContext.monitoringEventBuilder().setException(respError, RequestEvent.ExceptionCause.MAPPED_RESPONSE);
                    this.processingContext.triggerEvent(RequestEvent.Type.ON_EXCEPTION);
                    throw respError;
                }
                this.release(response);
            }
            catch (Throwable responseError) {
                try {
                    if (!(throwable == responseError || throwable instanceof MappableException && throwable.getCause() == responseError)) {
                        LOGGER.log(Level.FINE, LocalizationMessages.ERROR_EXCEPTION_MAPPING_ORIGINAL_EXCEPTION(), throwable);
                    }
                    if (!this.processResponseError(responseError)) {
                        LOGGER.log(Level.FINE, LocalizationMessages.ERROR_EXCEPTION_MAPPING_THROWN_TO_CONTAINER(), responseError);
                        try {
                            request.getResponseWriter().failure(responseError);
                        }
                        finally {
                            this.completionCallbackRunner.onComplete(responseError);
                        }
                    }
                    this.release(response);
                }
                catch (Throwable throwable2) {
                    this.release(response);
                    throw throwable2;
                }
            }
        }

        private boolean processResponseError(Throwable responseError) {
            boolean processed = false;
            if (this.runtime.processResponseErrors) {
                Iterable<ResponseErrorMapper> mappers = Providers.getAllProviders(this.runtime.injectionManager, ResponseErrorMapper.class);
                try {
                    ResponseErrorMapper mapper2;
                    Response processedError = null;
                    Iterator<ResponseErrorMapper> iterator2 = mappers.iterator();
                    while (iterator2.hasNext() && (processedError = (mapper2 = iterator2.next()).toResponse(responseError)) == null) {
                    }
                    if (processedError != null) {
                        this.processResponse(new ContainerResponse(this.processingContext.request(), processedError));
                        processed = true;
                    }
                }
                catch (Throwable throwable) {
                    LOGGER.log(Level.FINE, LocalizationMessages.ERROR_EXCEPTION_MAPPING_PROCESSED_RESPONSE_ERROR(), throwable);
                }
            }
            return processed;
        }

        private ContainerResponse convertResponse(Response exceptionResponse) {
            ContainerResponse containerResponse = new ContainerResponse(this.processingContext.request(), exceptionResponse);
            containerResponse.setMappedFromException(true);
            return containerResponse;
        }

        private Response mapException(Throwable originalThrowable) throws Throwable {
            LOGGER.log(Level.FINER, LocalizationMessages.EXCEPTION_MAPPING_START(), originalThrowable);
            ThrowableWrap wrap = new ThrowableWrap(originalThrowable);
            wrap.tryMappableException();
            do {
                Throwable throwable = wrap.getCurrent();
                if (wrap.isInMappable() || throwable instanceof WebApplicationException) {
                    if (this.runtime.processResponseErrors && throwable instanceof InternalServerErrorException && throwable.getCause() instanceof MessageBodyProviderNotFoundException) {
                        throw throwable;
                    }
                    Response waeResponse = null;
                    if (throwable instanceof WebApplicationException) {
                        WebApplicationException webApplicationException = (WebApplicationException)throwable;
                        this.processingContext.routingContext().setMappedThrowable(throwable);
                        waeResponse = webApplicationException.getResponse();
                        if (waeResponse.hasEntity()) {
                            LOGGER.log(Level.FINE, LocalizationMessages.EXCEPTION_MAPPING_WAE_ENTITY(waeResponse.getStatus()), throwable);
                            return waeResponse;
                        }
                    }
                    long timestamp = this.tracingLogger.timestamp(ServerTraceEvent.EXCEPTION_MAPPING);
                    ExceptionMapper<Throwable> mapper2 = this.runtime.exceptionMappers.findMapping(throwable);
                    if (mapper2 != null) {
                        this.processingContext.monitoringEventBuilder().setExceptionMapper(mapper2);
                        this.processingContext.triggerEvent(RequestEvent.Type.EXCEPTION_MAPPER_FOUND);
                        try {
                            Response mappedResponse = mapper2.toResponse(throwable);
                            if (this.tracingLogger.isLogEnabled(ServerTraceEvent.EXCEPTION_MAPPING)) {
                                this.tracingLogger.logDuration(ServerTraceEvent.EXCEPTION_MAPPING, timestamp, mapper2, throwable, throwable.getLocalizedMessage(), mappedResponse != null ? mappedResponse.getStatusInfo() : "-no-response-");
                            }
                            this.processingContext.routingContext().setMappedThrowable(throwable);
                            if (mappedResponse != null) {
                                if (LOGGER.isLoggable(Level.FINER)) {
                                    String message = String.format("Exception '%s' has been mapped by '%s' to response '%s' (%s:%s).", new Object[]{throwable.getLocalizedMessage(), mapper2.getClass().getName(), mappedResponse.getStatusInfo().getReasonPhrase(), mappedResponse.getStatusInfo().getStatusCode(), mappedResponse.getStatusInfo().getFamily()});
                                    LOGGER.log(Level.FINER, message);
                                }
                                return mappedResponse;
                            }
                            return Response.noContent().build();
                        }
                        catch (Throwable mapperThrowable) {
                            LOGGER.log(Level.SEVERE, LocalizationMessages.EXCEPTION_MAPPER_THROWS_EXCEPTION(mapper2.getClass()), mapperThrowable);
                            LOGGER.log(Level.SEVERE, LocalizationMessages.EXCEPTION_MAPPER_FAILED_FOR_EXCEPTION(), throwable);
                            return Response.serverError().build();
                        }
                    }
                    if (waeResponse != null) {
                        LOGGER.log(Level.FINE, LocalizationMessages.EXCEPTION_MAPPING_WAE_NO_ENTITY(waeResponse.getStatus()), throwable);
                        return waeResponse;
                    }
                }
                if (throwable instanceof HeaderValueException && ((HeaderValueException)throwable).getContext() == HeaderValueException.Context.INBOUND) {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
                if (wrap.isInMappable() && wrap.isWrapped()) continue;
                throw wrap.getWrappedOrCurrent();
            } while (wrap.unwrap() != null);
            throw originalThrowable;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Loose catch block
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private ContainerResponse writeResponse(final ContainerResponse response) {
            boolean close;
            ContainerRequest request = this.processingContext.request();
            final ContainerResponseWriter writer = request.getResponseWriter();
            if (!this.runtime.disableLocationHeaderRelativeUriResolution) {
                ServerRuntime.ensureAbsolute(response.getLocation(), response.getHeaders(), response.getRequestContext(), this.runtime.rfc7231LocationHeaderRelativeUriResolution);
            }
            if (!response.hasEntity()) {
                this.tracingLogger.log(ServerTraceEvent.FINISHED, response.getStatusInfo());
                this.tracingLogger.flush(response.getHeaders());
                writer.writeResponseStatusAndHeaders(0L, response);
                this.setWrittenResponse(response);
                return response;
            }
            Object entity = response.getEntity();
            boolean skipFinally = false;
            final boolean isHead = request.getMethod().equals("HEAD");
            response.setStreamProvider(new OutboundMessageContext.StreamProvider(){

                @Override
                public OutputStream getOutputStream(int contentLength) throws IOException {
                    if (!runtime.disableLocationHeaderRelativeUriResolution) {
                        ServerRuntime.ensureAbsolute(response.getLocation(), response.getHeaders(), response.getRequestContext(), runtime.rfc7231LocationHeaderRelativeUriResolution);
                    }
                    OutputStream outputStream2 = writer.writeResponseStatusAndHeaders(contentLength, response);
                    return isHead ? null : outputStream2;
                }
            });
            if ((writer.enableResponseBuffering() || isHead) && !response.isChunked()) {
                response.enableBuffering(this.runtime.configuration);
            }
            try {
                response.setEntityStream(request.getWorkers().writeTo(entity, entity.getClass(), response.getEntityType(), response.getEntityAnnotations(), response.getMediaType(), response.getHeaders(), request.getPropertiesDelegate(), response.getEntityStream(), request.getWriterInterceptors()));
            }
            catch (MappableException mpe) {
                if (!(mpe.getCause() instanceof IOException)) throw mpe;
                this.connectionCallbackRunner.onDisconnect(this.processingContext.asyncContext());
                throw mpe;
            }
            this.tracingLogger.log(ServerTraceEvent.FINISHED, response.getStatusInfo());
            this.tracingLogger.flush(response.getHeaders());
            this.setWrittenResponse(response);
            if (skipFinally) return response;
            boolean bl = close = !response.isChunked();
            if (response.isChunked()) {
                try {
                    response.commitStream();
                }
                catch (Exception e) {
                    LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), e);
                    close = true;
                }
                ChunkedOutput chunked = (ChunkedOutput)entity;
                try {
                    chunked.setContext(this.runtime.requestScope, this.runtime.requestScope.referenceCurrent(), request, response, this.connectionCallbackRunner);
                }
                catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_WRITING_RESPONSE_ENTITY_CHUNK(), ex);
                    close = true;
                }
                if (!chunked.isClosed() && !writer.suspend(0L, TimeUnit.SECONDS, null)) {
                    LOGGER.fine(LocalizationMessages.ERROR_SUSPENDING_CHUNKED_OUTPUT_RESPONSE());
                }
            }
            if (!close) return response;
            try {
                response.close();
                return response;
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_CLOSING_COMMIT_OUTPUT_STREAM(), e);
            }
            return response;
            catch (Throwable ex) {
                try {
                    if (!response.isCommitted()) {
                        skipFinally = true;
                        if (!(ex instanceof RuntimeException)) throw new MappableException(ex);
                        throw (RuntimeException)ex;
                    }
                    LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_WRITING_RESPONSE_ENTITY(), ex);
                    if (skipFinally) return response;
                    boolean bl2 = close = !response.isChunked();
                }
                catch (Throwable throwable) {
                    boolean close2;
                    if (skipFinally) throw throwable;
                    boolean bl3 = close2 = !response.isChunked();
                    if (response.isChunked()) {
                        try {
                            response.commitStream();
                        }
                        catch (Exception e) {
                            LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), e);
                            close2 = true;
                        }
                        ChunkedOutput chunked = (ChunkedOutput)entity;
                        try {
                            chunked.setContext(this.runtime.requestScope, this.runtime.requestScope.referenceCurrent(), request, response, this.connectionCallbackRunner);
                        }
                        catch (IOException ex2) {
                            LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_WRITING_RESPONSE_ENTITY_CHUNK(), ex2);
                            close2 = true;
                        }
                        if (!chunked.isClosed() && !writer.suspend(0L, TimeUnit.SECONDS, null)) {
                            LOGGER.fine(LocalizationMessages.ERROR_SUSPENDING_CHUNKED_OUTPUT_RESPONSE());
                        }
                    }
                    if (!close2) throw throwable;
                    try {
                        response.close();
                        throw throwable;
                    }
                    catch (Exception e) {
                        LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_CLOSING_COMMIT_OUTPUT_STREAM(), e);
                    }
                    throw throwable;
                }
                if (response.isChunked()) {
                    try {
                        response.commitStream();
                    }
                    catch (Exception e) {
                        LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), e);
                        close = true;
                    }
                    ChunkedOutput chunked = (ChunkedOutput)entity;
                    try {
                        chunked.setContext(this.runtime.requestScope, this.runtime.requestScope.referenceCurrent(), request, response, this.connectionCallbackRunner);
                    }
                    catch (IOException ex3) {
                        LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_WRITING_RESPONSE_ENTITY_CHUNK(), ex3);
                        close = true;
                    }
                    if (!chunked.isClosed() && !writer.suspend(0L, TimeUnit.SECONDS, null)) {
                        LOGGER.fine(LocalizationMessages.ERROR_SUSPENDING_CHUNKED_OUTPUT_RESPONSE());
                    }
                }
                if (!close) return response;
                try {
                    response.close();
                    return response;
                }
                catch (Exception e) {
                    LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_CLOSING_COMMIT_OUTPUT_STREAM(), e);
                }
                return response;
            }
        }

        private void setWrittenResponse(ContainerResponse response) {
            this.processingContext.monitoringEventBuilder().setContainerResponse(response).setSuccess(response.getStatus() < Response.Status.BAD_REQUEST.getStatusCode()).setResponseWritten(true);
        }

        private void release(ContainerResponse responseContext) {
            try {
                this.processingContext.closeableService().close();
                if (responseContext != null && !responseContext.isChunked()) {
                    responseContext.close();
                }
            }
            catch (Throwable throwable) {
                LOGGER.log(Level.WARNING, LocalizationMessages.RELEASING_REQUEST_PROCESSING_RESOURCES_FAILED(), throwable);
            }
            finally {
                this.runtime.externalRequestScope.close();
                this.processingContext.triggerEvent(RequestEvent.Type.FINISHED);
            }
        }
    }

    private static class AsyncResponderHolder
    implements Value<AsyncContext> {
        private final Responder responder;
        private final ExternalRequestScope externalScope;
        private final RequestContext requestContext;
        private final ExternalRequestContext<?> externalContext;
        private volatile AsyncResponder asyncResponder;

        private AsyncResponderHolder(Responder responder, ExternalRequestScope externalRequestScope, RequestContext requestContext, ExternalRequestContext<?> externalContext) {
            this.responder = responder;
            this.externalScope = externalRequestScope;
            this.requestContext = requestContext;
            this.externalContext = externalContext;
        }

        @Override
        public AsyncContext get() {
            AsyncResponder ar;
            this.asyncResponder = ar = new AsyncResponder(this.responder, this.requestContext, this.externalScope, this.externalContext);
            return ar;
        }

        public boolean isAsync() {
            AsyncResponder ar = this.asyncResponder;
            return ar != null && !ar.isRunning();
        }

        public void release() {
            if (this.asyncResponder == null) {
                this.requestContext.release();
            }
        }
    }
}

