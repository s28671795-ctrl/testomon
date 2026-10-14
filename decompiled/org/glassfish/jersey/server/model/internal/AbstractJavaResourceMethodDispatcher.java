/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.PrivilegedAction;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import javax.validation.ValidationException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.SubjectSecurityContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.ServerTraceEvent;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.glassfish.jersey.server.internal.process.MappableException;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;

abstract class AbstractJavaResourceMethodDispatcher
implements ResourceMethodDispatcher {
    private final Method method;
    private final InvocationHandler methodHandler;
    private final Invocable resourceMethod;
    private final ConfiguredValidator validator;

    AbstractJavaResourceMethodDispatcher(Invocable resourceMethod, InvocationHandler methodHandler, ConfiguredValidator validator) {
        this.method = resourceMethod.getDefinitionMethod();
        this.methodHandler = methodHandler;
        this.resourceMethod = resourceMethod;
        this.validator = validator;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final Response dispatch(Object resource, ContainerRequest request) throws ProcessingException {
        Response response = null;
        try {
            response = this.doDispatch(resource, request);
        }
        catch (Throwable throwable) {
            TracingLogger.getInstance(request).log(ServerTraceEvent.DISPATCH_RESPONSE, response);
            throw throwable;
        }
        TracingLogger.getInstance(request).log(ServerTraceEvent.DISPATCH_RESPONSE, response);
        return response;
    }

    protected abstract Response doDispatch(Object var1, ContainerRequest var2) throws ProcessingException;

    final Object invoke(final ContainerRequest containerRequest, final Object resource, final Object ... args2) throws ProcessingException {
        try {
            Object invocationResult;
            if (this.validator != null) {
                this.validator.validateResourceAndInputParams(resource, this.resourceMethod, args2);
            }
            PrivilegedAction invokeMethodAction = new PrivilegedAction(){

                /*
                 * Unable to fully structure code
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public Object run() {
                    block12: {
                        tracingLogger = TracingLogger.getInstance(containerRequest);
                        timestamp = tracingLogger.timestamp(ServerTraceEvent.METHOD_INVOKE);
                        result = AbstractJavaResourceMethodDispatcher.access$100(AbstractJavaResourceMethodDispatcher.this).invoke(resource, AbstractJavaResourceMethodDispatcher.access$000(AbstractJavaResourceMethodDispatcher.this), args2);
                        if (!(result instanceof CompletionStage)) ** GOTO lbl26
                        try {
                            resultFuture = ((CompletionStage)result).toCompletableFuture();
                        }
                        catch (UnsupportedOperationException e) {
                            var7_12 = result;
                            tracingLogger.logDuration(ServerTraceEvent.METHOD_INVOKE, timestamp, new Object[]{resource, AbstractJavaResourceMethodDispatcher.access$000(AbstractJavaResourceMethodDispatcher.this)});
                            return var7_12;
                        }
                        if (resultFuture == null || !resultFuture.isDone()) ** GOTO lbl26
                        if (!resultFuture.isCancelled()) break block12;
                        e = Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                        tracingLogger.logDuration(ServerTraceEvent.METHOD_INVOKE, timestamp, new Object[]{resource, AbstractJavaResourceMethodDispatcher.access$000(AbstractJavaResourceMethodDispatcher.this)});
                        return e;
                    }
                    try {
                        e = resultFuture.get();
                    }
                    catch (ExecutionException e) {
                        try {
                            throw new InvocationTargetException(e.getCause());
lbl26:
                            // 2 sources

                            var5_7 = result;
                        }
                        catch (IllegalAccessException | IllegalArgumentException | UndeclaredThrowableException ex) {
                            try {
                                throw new ProcessingException(LocalizationMessages.ERROR_RESOURCE_JAVA_METHOD_INVOCATION(), ex);
                                catch (InvocationTargetException ex) {
                                    throw AbstractJavaResourceMethodDispatcher.access$200(ex.getCause());
                                }
                                catch (Throwable t) {
                                    throw new ProcessingException(t);
                                }
                            }
                            catch (Throwable var8_13) {
                                tracingLogger.logDuration(ServerTraceEvent.METHOD_INVOKE, timestamp, new Object[]{resource, AbstractJavaResourceMethodDispatcher.access$000(AbstractJavaResourceMethodDispatcher.this)});
                                throw var8_13;
                            }
                        }
                        tracingLogger.logDuration(ServerTraceEvent.METHOD_INVOKE, timestamp, new Object[]{resource, AbstractJavaResourceMethodDispatcher.access$000(AbstractJavaResourceMethodDispatcher.this)});
                        return var5_7;
                    }
                    tracingLogger.logDuration(ServerTraceEvent.METHOD_INVOKE, timestamp, new Object[]{resource, AbstractJavaResourceMethodDispatcher.access$000(AbstractJavaResourceMethodDispatcher.this)});
                    return e;
                }
            };
            SecurityContext securityContext = containerRequest.getSecurityContext();
            Object object = invocationResult = securityContext instanceof SubjectSecurityContext ? ((SubjectSecurityContext)securityContext).doAsSubject(invokeMethodAction) : invokeMethodAction.run();
            if (this.validator != null) {
                this.validator.validateResult(resource, this.resourceMethod, invocationResult);
            }
            return invocationResult;
        }
        catch (ValidationException ex) {
            throw new MappableException(ex);
        }
    }

    private static RuntimeException mapTargetToRuntimeEx(Throwable throwable) {
        if (throwable instanceof WebApplicationException) {
            return (WebApplicationException)throwable;
        }
        return new MappableException(throwable);
    }

    public String toString() {
        return this.method.toString();
    }

    static /* synthetic */ Method access$000(AbstractJavaResourceMethodDispatcher x0) {
        return x0.method;
    }

    static /* synthetic */ InvocationHandler access$100(AbstractJavaResourceMethodDispatcher x0) {
        return x0.methodHandler;
    }

    static /* synthetic */ RuntimeException access$200(Throwable x0) {
        return AbstractJavaResourceMethodDispatcher.mapTargetToRuntimeEx(x0);
    }
}

