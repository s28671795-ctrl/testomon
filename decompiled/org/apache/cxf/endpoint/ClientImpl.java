/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.UncheckedException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientCallback;
import org.apache.cxf.endpoint.ClientLifeCycleManager;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.endpoint.EndpointImplFactory;
import org.apache.cxf.endpoint.PreexistingConduitSelector;
import org.apache.cxf.endpoint.Retryable;
import org.apache.cxf.endpoint.UpfrontConduitSelector;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.interceptor.ClientOutFaultObserver;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.phase.PhaseChainCache;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.phase.PhaseManager;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.workqueue.SynchronousExecutor;

public class ClientImpl
extends AbstractBasicInterceptorProvider
implements Client,
Retryable,
MessageObserver {
    public static final String THREAD_LOCAL_REQUEST_CONTEXT = "thread.local.request.context";
    public static final String SYNC_TIMEOUT = "cxf.synchronous.timeout";
    public static final String FINISHED = "exchange.finished";
    private static final Logger LOG = LogUtils.getL7dLogger(ClientImpl.class);
    protected Bus bus;
    protected ConduitSelector conduitSelector;
    protected ClientOutFaultObserver outFaultObserver;
    protected int synchronousTimeout = 60000;
    protected PhaseChainCache outboundChainCache = new PhaseChainCache();
    protected PhaseChainCache inboundChainCache = new PhaseChainCache();
    protected Map<String, Object> currentRequestContext = new ConcurrentHashMap<String, Object>(8, 0.75f, 4);
    protected Thread latestContextThread;
    protected Map<Thread, EchoContext> requestContext = Collections.synchronizedMap(new WeakHashMap());
    protected Map<Thread, ResponseContext> responseContext = Collections.synchronizedMap(new WeakHashMap());
    protected Executor executor;

    public ClientImpl(Bus b, Endpoint e) {
        this(b, e, (ConduitSelector)null);
    }

    public ClientImpl(Bus b, Endpoint e, Conduit c) {
        this(b, e, new PreexistingConduitSelector(c));
    }

    public ClientImpl(Bus b, Endpoint e, ConduitSelector sc) {
        this.bus = b;
        this.outFaultObserver = new ClientOutFaultObserver(this.bus);
        this.getConduitSelector(sc).setEndpoint(e);
        this.notifyLifecycleManager();
    }

    public ClientImpl(Bus bus, Service svc, QName port, EndpointImplFactory endpointImplFactory) {
        this.bus = bus;
        this.outFaultObserver = new ClientOutFaultObserver(bus);
        EndpointInfo epfo = this.findEndpoint(svc, port);
        try {
            if (endpointImplFactory != null) {
                this.getConduitSelector().setEndpoint(endpointImplFactory.newEndpointImpl(bus, svc, epfo));
            } else {
                this.getConduitSelector().setEndpoint(new EndpointImpl(bus, svc, epfo));
            }
        }
        catch (EndpointException epex) {
            throw new IllegalStateException("Unable to create endpoint: " + epex.getMessage(), epex);
        }
        this.notifyLifecycleManager();
    }

    @Override
    public Bus getBus() {
        return this.bus;
    }

    @Override
    public void destroy() {
        ClientLifeCycleManager mgr;
        if (this.bus == null) {
            return;
        }
        if (this.getEndpoint() != null) {
            for (Closeable c : this.getEndpoint().getCleanupHooks()) {
                try {
                    c.close();
                }
                catch (IOException iOException) {}
            }
        }
        if (null != (mgr = this.bus.getExtension(ClientLifeCycleManager.class))) {
            mgr.clientDestroyed(this);
        }
        if (this.conduitSelector != null) {
            if (this.conduitSelector instanceof Closeable) {
                try {
                    ((Closeable)((Object)this.conduitSelector)).close();
                }
                catch (IOException iOException) {}
            } else {
                this.getConduit().close();
            }
        }
        this.bus = null;
        this.conduitSelector = null;
        this.outFaultObserver = null;
        this.outboundChainCache = null;
        this.inboundChainCache = null;
        this.currentRequestContext = null;
        this.requestContext.clear();
        this.requestContext = null;
        this.responseContext.clear();
        this.responseContext = null;
        this.executor = null;
    }

    private void notifyLifecycleManager() {
        ClientLifeCycleManager mgr = this.bus.getExtension(ClientLifeCycleManager.class);
        if (null != mgr) {
            mgr.clientCreated(this);
        }
    }

    private EndpointInfo findEndpoint(Service svc, QName port) {
        if (port != null) {
            EndpointInfo epfo = svc.getEndpointInfo(port);
            if (epfo == null) {
                throw new IllegalArgumentException("The service " + svc.getName() + " does not have an endpoint " + port + ".");
            }
            return epfo;
        }
        for (ServiceInfo svcfo : svc.getServiceInfos()) {
            for (EndpointInfo e : svcfo.getEndpoints()) {
                BindingInfo bfo = e.getBinding();
                String bid = bfo.getBindingId();
                if (!"http://schemas.xmlsoap.org/wsdl/soap/".equals(bid) && !"http://schemas.xmlsoap.org/wsdl/soap12/".equals(bid)) continue;
                for (Object o : bfo.getExtensors().get()) {
                    try {
                        String s2 = (String)o.getClass().getMethod("getTransportURI", new Class[0]).invoke(o, new Object[0]);
                        if (s2 == null || !s2.endsWith("http")) continue;
                        return e;
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
            }
        }
        throw new UnsupportedOperationException("Only document-style SOAP 1.1 and 1.2 http are supported for auto-selection of endpoint; none were found.");
    }

    @Override
    public Endpoint getEndpoint() {
        return this.getConduitSelector().getEndpoint();
    }

    public void releaseThreadContexts() {
        Thread t2 = Thread.currentThread();
        this.requestContext.remove(t2);
        this.responseContext.remove(t2);
    }

    @Override
    public Client.Contexts getContexts() {
        return new Client.Contexts(){

            @Override
            public void close() throws Exception {
                ClientImpl.this.releaseThreadContexts();
            }

            @Override
            public Map<String, Object> getRequestContext() {
                return ClientImpl.this.getRequestContext();
            }

            @Override
            public Map<String, Object> getResponseContext() {
                return ClientImpl.this.getResponseContext();
            }
        };
    }

    @Override
    public Map<String, Object> getRequestContext() {
        if (this.isThreadLocalRequestContext()) {
            Thread t2 = Thread.currentThread();
            if (!this.requestContext.containsKey(t2)) {
                EchoContext freshRequestContext = new EchoContext(this.currentRequestContext);
                this.requestContext.put(t2, freshRequestContext);
            }
            this.latestContextThread = t2;
            return this.requestContext.get(t2);
        }
        return this.currentRequestContext;
    }

    @Override
    public Map<String, Object> getResponseContext() {
        if (!this.responseContext.containsKey(Thread.currentThread())) {
            Thread t2 = Thread.currentThread();
            this.responseContext.put(t2, new ResponseContext(this.responseContext));
        }
        return this.responseContext.get(Thread.currentThread());
    }

    protected Map<String, Object> setResponseContext(Map<String, Object> ctx) {
        if (ctx instanceof ResponseContext) {
            ResponseContext c = (ResponseContext)ctx;
            this.responseContext.put(Thread.currentThread(), c);
            return c;
        }
        ResponseContext c = new ResponseContext(ctx, this.responseContext);
        this.responseContext.put(Thread.currentThread(), c);
        return c;
    }

    @Override
    public boolean isThreadLocalRequestContext() {
        Object o = this.currentRequestContext.get(THREAD_LOCAL_REQUEST_CONTEXT);
        if (o != null) {
            boolean local = o instanceof Boolean ? (Boolean)o : Boolean.parseBoolean(o.toString());
            return local;
        }
        return false;
    }

    @Override
    public void setThreadLocalRequestContext(boolean b) {
        this.currentRequestContext.put(THREAD_LOCAL_REQUEST_CONTEXT, b);
    }

    @Override
    public Object[] invoke(BindingOperationInfo oi, Object ... params) throws Exception {
        return this.invoke(oi, params, (Exchange)null);
    }

    @Override
    public Object[] invoke(String operationName, Object ... params) throws Exception {
        QName q = new QName(this.getEndpoint().getService().getName().getNamespaceURI(), operationName);
        return this.invoke(q, params);
    }

    @Override
    public Object[] invoke(QName operationName, Object ... params) throws Exception {
        BindingOperationInfo op = this.getEndpoint().getEndpointInfo().getBinding().getOperation(operationName);
        if (op == null) {
            throw new UncheckedException(new org.apache.cxf.common.i18n.Message("NO_OPERATION", LOG, operationName));
        }
        if (op.isUnwrappedCapable()) {
            op = op.getUnwrappedOperation();
        }
        return this.invoke(op, params);
    }

    @Override
    public Object[] invokeWrapped(String operationName, Object ... params) throws Exception {
        QName q = new QName(this.getEndpoint().getService().getName().getNamespaceURI(), operationName);
        return this.invokeWrapped(q, params);
    }

    @Override
    public Object[] invokeWrapped(QName operationName, Object ... params) throws Exception {
        BindingOperationInfo op = this.getEndpoint().getEndpointInfo().getBinding().getOperation(operationName);
        if (op == null) {
            throw new UncheckedException(new org.apache.cxf.common.i18n.Message("NO_OPERATION", LOG, operationName));
        }
        return this.invoke(op, params);
    }

    public Object[] invoke(BindingOperationInfo oi, Object[] params, Exchange exchange) throws Exception {
        HashMap<String, Object> context = new HashMap<String, Object>();
        return this.invoke(oi, params, context, exchange);
    }

    @Override
    public Object[] invoke(BindingOperationInfo oi, Object[] params, Map<String, Object> context) throws Exception {
        return this.invoke(oi, params, context, (Exchange)null);
    }

    @Override
    public void invoke(ClientCallback callback, String operationName, Object ... params) throws Exception {
        QName q = new QName(this.getEndpoint().getService().getName().getNamespaceURI(), operationName);
        this.invoke(callback, q, params);
    }

    @Override
    public void invoke(ClientCallback callback, QName operationName, Object ... params) throws Exception {
        BindingOperationInfo op = this.getEndpoint().getEndpointInfo().getBinding().getOperation(operationName);
        if (op == null) {
            throw new UncheckedException(new org.apache.cxf.common.i18n.Message("NO_OPERATION", LOG, operationName));
        }
        if (op.isUnwrappedCapable()) {
            op = op.getUnwrappedOperation();
        }
        this.invoke(callback, op, params);
    }

    @Override
    public void invokeWrapped(ClientCallback callback, String operationName, Object ... params) throws Exception {
        QName q = new QName(this.getEndpoint().getService().getName().getNamespaceURI(), operationName);
        this.invokeWrapped(callback, q, params);
    }

    @Override
    public void invokeWrapped(ClientCallback callback, QName operationName, Object ... params) throws Exception {
        BindingOperationInfo op = this.getEndpoint().getEndpointInfo().getBinding().getOperation(operationName);
        if (op == null) {
            throw new UncheckedException(new org.apache.cxf.common.i18n.Message("NO_OPERATION", LOG, operationName));
        }
        this.invoke(callback, op, params);
    }

    @Override
    public void invoke(ClientCallback callback, BindingOperationInfo oi, Object ... params) throws Exception {
        this.invoke(callback, oi, params, null, null);
    }

    @Override
    public void invoke(ClientCallback callback, BindingOperationInfo oi, Object[] params, Map<String, Object> context) throws Exception {
        this.invoke(callback, oi, params, context, null);
    }

    @Override
    public void invoke(ClientCallback callback, BindingOperationInfo oi, Object[] params, Exchange exchange) throws Exception {
        this.invoke(callback, oi, params, null, exchange);
    }

    @Override
    public void invoke(ClientCallback callback, BindingOperationInfo oi, Object[] params, Map<String, Object> context, Exchange exchange) throws Exception {
        this.doInvoke(callback, oi, params, context, exchange);
    }

    @Override
    public Object[] invoke(BindingOperationInfo oi, Object[] params, Map<String, Object> context, Exchange exchange) throws Exception {
        return this.doInvoke(null, oi, params, context, exchange);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object[] doInvoke(final ClientCallback callback, BindingOperationInfo oi, Object[] params, Map<String, Object> context, Exchange exchange) throws Exception {
        Bus origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
        ClassLoaderUtils.ClassLoaderHolder origLoader = null;
        ResponseContext resContext = null;
        try {
            ClassLoader loader = this.bus.getExtension(ClassLoader.class);
            if (loader != null) {
                origLoader = ClassLoaderUtils.setThreadContextClassloader(loader);
            }
            if (exchange == null) {
                exchange = new ExchangeImpl();
            }
            exchange.setSynchronous(callback == null);
            Endpoint endpoint = this.getEndpoint();
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Invoke, operation info: " + oi + ", params: " + Arrays.toString(params));
            }
            Message message = endpoint.getBinding().createMessage();
            if (context == null) {
                context = new HashMap<String, Object>();
            }
            Map<String, Object> reqContext = CastUtils.cast((Map)context.get("RequestContext"));
            resContext = CastUtils.cast((Map)context.get("ResponseContext"));
            if (reqContext == null) {
                reqContext = new HashMap<String, Object>(this.getRequestContext());
                context.put("RequestContext", reqContext);
            }
            if (resContext == null) {
                resContext = new ResponseContext(this.responseContext);
                context.put("ResponseContext", resContext);
            }
            message.put("org.apache.cxf.invocation.context", context);
            this.setContext(reqContext, message);
            exchange.putAll(reqContext);
            this.setParameters(params, message);
            if (null != oi) {
                exchange.setOneWay(oi.getOutput() == null);
            }
            exchange.setOutMessage(message);
            exchange.put(ClientCallback.class, callback);
            this.setOutMessageProperties(message, oi);
            this.setExchangeProperties(exchange, endpoint, oi);
            PhaseInterceptorChain chain = this.setupInterceptorChain(endpoint);
            message.setInterceptorChain(chain);
            if (callback == null) {
                chain.setFaultObserver(this.outFaultObserver);
            } else {
                chain.setFaultObserver(new MessageObserver(){

                    @Override
                    public void onMessage(Message message) {
                        Exception ex = message.getContent(Exception.class);
                        if (ex != null) {
                            ClientImpl.this.completeExchange(message.getExchange());
                            if (message.getContent(Exception.class) == null) {
                                Message inMsg = message.getExchange().getInMessage();
                                Map ctx = ClientImpl.this.responseContext.get(Thread.currentThread());
                                List resList = CastUtils.cast(inMsg.getContent(List.class));
                                Object[] result = resList == null ? null : resList.toArray();
                                callback.handleResponse(ctx, result);
                                return;
                            }
                        }
                        ClientImpl.this.outFaultObserver.onMessage(message);
                    }
                });
            }
            this.prepareConduitSelector(message);
            this.modifyChain(chain, message, false);
            try {
                chain.doIntercept(message);
            }
            catch (Fault fault) {
                this.enrichFault(fault);
                throw fault;
            }
            if (callback != null) {
                Object[] objectArray = null;
                return objectArray;
            }
            Object[] objectArray = this.processResult(message, exchange, oi, resContext);
            return objectArray;
        }
        finally {
            Integer responseCode = (Integer)exchange.get(Message.RESPONSE_CODE);
            resContext.put("javax.xml.ws.http.response.code", responseCode);
            resContext.put(Message.RESPONSE_CODE, responseCode);
            this.setResponseContext(resContext);
            if (origLoader != null) {
                origLoader.reset();
            }
            if (origBus != this.bus) {
                BusFactory.setThreadDefaultBus(origBus);
            }
        }
    }

    private void completeExchange(Exchange exchange) {
        this.getConduitSelector().complete(exchange);
    }

    private void enrichFault(Fault fault) {
        if (fault.getCause().getCause() instanceof IOException || fault.getCause() instanceof IOException) {
            String soap11NS = "http://schemas.xmlsoap.org/soap/envelope/";
            String soap12NS = "http://www.w3.org/2003/05/soap-envelope";
            QName faultCode = fault.getFaultCode();
            if (faultCode.getNamespaceURI().equals(soap11NS) && "Client".equals(faultCode.getLocalPart())) {
                faultCode = new QName(soap11NS, "Server");
                fault.setFaultCode(faultCode);
            }
            if (faultCode.getNamespaceURI().equals(soap12NS) && "Sender".equals(faultCode.getLocalPart())) {
                faultCode = new QName(soap12NS, "Receiver");
                fault.setFaultCode(faultCode);
            }
        }
    }

    protected Object[] processResult(Message message, Exchange exchange, BindingOperationInfo oi, Map<String, Object> resContext) throws Exception {
        Boolean keepConduitAlive;
        Endpoint ep;
        Exception ex = null;
        if (!message.get("org.apache.cxf.message.inbound").equals(Boolean.TRUE)) {
            ex = message.getContent(Exception.class);
        }
        boolean mepCompleteCalled = false;
        if (ex != null) {
            this.completeExchange(exchange);
            mepCompleteCalled = true;
            if (message.getContent(Exception.class) != null) {
                throw ex;
            }
        }
        if ((ex = message.getExchange().get(Exception.class)) != null) {
            if (!mepCompleteCalled) {
                this.completeExchange(exchange);
            }
            throw ex;
        }
        Integer responseCode = (Integer)exchange.get(Message.RESPONSE_CODE);
        if (null != responseCode && 202 == responseCode && null != (ep = exchange.getEndpoint()) && null != ep.getEndpointInfo() && null == ep.getEndpointInfo().getProperty("org.apache.cxf.ws.addressing.MAPAggregator.decoupledDestination")) {
            return null;
        }
        if (oi != null && !oi.getOperationInfo().isOneWay()) {
            this.waitResponse(exchange);
        }
        if ((keepConduitAlive = (Boolean)exchange.get("KeepConduitAlive")) == null || !keepConduitAlive.booleanValue()) {
            this.completeExchange(exchange);
        }
        List resList = null;
        Message inMsg = exchange.getInMessage();
        if (inMsg != null) {
            if (null != resContext) {
                resContext.putAll(inMsg);
                resContext.remove("org.apache.cxf.invocation.context");
                this.setResponseContext(resContext);
            }
            resList = CastUtils.cast(inMsg.getContent(List.class));
        }
        if ((ex = this.getException(exchange)) != null) {
            throw ex;
        }
        if (resList == null && oi != null && !oi.getOperationInfo().isOneWay()) {
            BindingOperationInfo boi = oi;
            if (boi.isUnwrapped()) {
                boi = boi.getWrappedOperation();
            }
            if (!boi.getOutput().getMessageParts().isEmpty()) {
                throw new IllegalEmptyResponseException("Response message did not contain proper response data. Expected: " + boi.getOutput().getMessageParts().get(0).getConcreteName());
            }
        }
        if (resList != null) {
            return resList.toArray();
        }
        return null;
    }

    protected Exception getException(Exchange exchange) {
        if (exchange.getInFaultMessage() != null) {
            return exchange.getInFaultMessage().getContent(Exception.class);
        }
        if (exchange.getOutFaultMessage() != null) {
            return exchange.getOutFaultMessage().getContent(Exception.class);
        }
        if (exchange.getInMessage() != null) {
            return exchange.getInMessage().getContent(Exception.class);
        }
        return null;
    }

    protected void setContext(Map<String, Object> ctx, Message message) {
        if (ctx != null) {
            message.putAll(ctx);
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("set requestContext to message be" + ctx);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void waitResponse(Exchange exchange) throws IOException {
        Exchange exchange2 = exchange;
        synchronized (exchange2) {
            long remaining = this.synchronousTimeout;
            Long o = PropertyUtils.getLong(exchange.getOutMessage(), SYNC_TIMEOUT);
            if (o != null) {
                remaining = o;
            }
            while (!Boolean.TRUE.equals(exchange.get(FINISHED)) && remaining > 0L) {
                long start2 = System.currentTimeMillis();
                try {
                    exchange.wait(remaining);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                long end = System.currentTimeMillis();
                remaining -= (long)((int)(end - start2));
            }
            if (!Boolean.TRUE.equals(exchange.get(FINISHED))) {
                LogUtils.log(LOG, Level.WARNING, "RESPONSE_TIMEOUT", exchange.getBindingOperationInfo().getOperationInfo().getName().toString());
                String msg = new org.apache.cxf.common.i18n.Message("RESPONSE_TIMEOUT", LOG, exchange.getBindingOperationInfo().getOperationInfo().getName().toString()).toString();
                throw new IOException(msg);
            }
        }
    }

    protected void setParameters(Object[] params, Message message) {
        MessageContentsList contents = new MessageContentsList(params);
        message.setContent(List.class, contents);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onMessage(Message message) {
        PhaseInterceptorChain chain;
        if (this.bus == null) {
            throw new IllegalStateException("Message received on a Client that has been closed or destroyed.");
        }
        Endpoint endpoint = message.getExchange().getEndpoint();
        if (endpoint == null) {
            endpoint = this.getConduitSelector().getEndpoint();
            message.getExchange().put(Endpoint.class, endpoint);
        }
        message = endpoint.getBinding().createMessage(message);
        message.getExchange().setInMessage(message);
        message.put("org.apache.cxf.client", Boolean.TRUE);
        message.put("org.apache.cxf.message.inbound", Boolean.TRUE);
        PhaseManager pm = this.bus.getExtension(PhaseManager.class);
        List<Interceptor<? extends Message>> i1 = this.bus.getInInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by bus: " + i1);
        }
        List<Interceptor<? extends Message>> i2 = this.getInInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by client: " + i2);
        }
        List<Interceptor<? extends Message>> i3 = endpoint.getInInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by endpoint: " + i3);
        }
        List<Interceptor<? extends Message>> i4 = endpoint.getBinding().getInInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by binding: " + i4);
        }
        if (endpoint.getService().getDataBinding() instanceof InterceptorProvider) {
            InterceptorProvider p = (InterceptorProvider)((Object)endpoint.getService().getDataBinding());
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Interceptors contributed by databinding: " + p.getInInterceptors());
            }
            chain = this.inboundChainCache.get(pm.getInPhases(), i1, i2, i3, i4, p.getInInterceptors());
        } else {
            chain = this.inboundChainCache.get(pm.getInPhases(), i1, i2, i3, i4);
        }
        message.setInterceptorChain(chain);
        chain.setFaultObserver(this.outFaultObserver);
        this.modifyChain(chain, message, true);
        this.modifyChain(chain, message.getExchange().getOutMessage(), true);
        Bus origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
        ClientCallback callback = message.getExchange().get(ClientCallback.class);
        try {
            if (callback != null) {
                if (callback.isCancelled()) {
                    this.completeExchange(message.getExchange());
                    return;
                }
                callback.start(message);
            }
            String startingAfterInterceptorID = (String)message.get("starting_after_interceptor_id");
            String startingInterceptorID = (String)message.get("starting_at_interceptor_id");
            if (startingAfterInterceptorID != null) {
                chain.doInterceptStartingAfter(message, startingAfterInterceptorID);
            } else if (startingInterceptorID != null) {
                chain.doInterceptStartingAt(message, startingInterceptorID);
            } else if (message.getContent(Exception.class) != null) {
                this.outFaultObserver.onMessage(message);
            } else {
                callback = message.getExchange().get(ClientCallback.class);
                if (callback != null && !this.isPartialResponse(message)) {
                    try {
                        chain.doIntercept(message);
                    }
                    catch (Throwable error) {
                        message.getExchange().setInMessage(message);
                        Map<String, Object> resCtx = CastUtils.cast((Map)message.getExchange().getOutMessage().get("org.apache.cxf.invocation.context"));
                        resCtx = CastUtils.cast((Map)resCtx.get("ResponseContext"));
                        if (resCtx != null) {
                            this.setResponseContext(resCtx);
                        }
                        if ((callback = message.getExchange().remove(ClientCallback.class)) != null) {
                            callback.handleException(resCtx, error);
                        }
                    }
                } else {
                    chain.doIntercept(message);
                }
            }
            callback = message.getExchange().get(ClientCallback.class);
            if (callback == null || this.isPartialResponse(message)) {
                return;
            }
            callback = message.getExchange().remove(ClientCallback.class);
            if (callback != null) {
                message.getExchange().setInMessage(message);
                Map<String, Object> resCtx = CastUtils.cast((Map)message.getExchange().getOutMessage().get("org.apache.cxf.invocation.context"));
                resCtx = CastUtils.cast((Map)resCtx.get("ResponseContext"));
                if (resCtx != null && this.responseContext != null) {
                    this.setResponseContext(resCtx);
                }
                try {
                    Object[] obj = this.processResult(message, message.getExchange(), null, resCtx);
                    callback.handleResponse(resCtx, obj);
                }
                catch (Throwable ex) {
                    callback.handleException(resCtx, ex);
                }
            }
        }
        finally {
            if (origBus != this.bus) {
                BusFactory.setThreadDefaultBus(origBus);
            }
            Exchange exchange = message.getExchange();
            synchronized (exchange) {
                if (!this.isPartialResponse(message) || message.getContent(Exception.class) != null) {
                    message.getExchange().put(FINISHED, Boolean.TRUE);
                    message.getExchange().setInMessage(message);
                    message.getExchange().notifyAll();
                }
            }
        }
    }

    @Override
    public Conduit getConduit() {
        MessageImpl message = new MessageImpl();
        ExchangeImpl exchange = new ExchangeImpl();
        message.setExchange(exchange);
        message.putAll(this.getRequestContext());
        this.setExchangeProperties(exchange, this.getEndpoint(), null);
        return this.getConduitSelector().selectConduit(message);
    }

    protected void prepareConduitSelector(Message message) {
        this.getConduitSelector().prepare(message);
        message.getExchange().put(ConduitSelector.class, this.getConduitSelector());
    }

    protected void setOutMessageProperties(Message message, BindingOperationInfo boi) {
        message.put("org.apache.cxf.client", Boolean.TRUE);
        message.put("org.apache.cxf.message.inbound", Boolean.FALSE);
        if (null != boi) {
            message.put(BindingMessageInfo.class, boi.getInput());
            message.put(MessageInfo.class, boi.getOperationInfo().getInput());
        }
    }

    protected void setExchangeProperties(Exchange exchange, Endpoint endpoint, BindingOperationInfo boi) {
        if (endpoint != null) {
            exchange.put(Endpoint.class, endpoint);
            exchange.put(Service.class, endpoint.getService());
            exchange.put(Binding.class, endpoint.getBinding());
        }
        if (boi != null) {
            exchange.put(BindingOperationInfo.class, boi);
        }
        if (exchange.isSynchronous() || this.executor == null) {
            exchange.put(MessageObserver.class, this);
        } else {
            exchange.put(Executor.class, this.executor);
            exchange.put(MessageObserver.class, new MessageObserver(){

                @Override
                public void onMessage(final Message message) {
                    if (!message.getExchange().containsKey(Executor.class.getName() + ".USING_SPECIFIED")) {
                        ClientImpl.this.executor.execute(new Runnable(){

                            @Override
                            public void run() {
                                ClientImpl.this.onMessage(message);
                            }
                        });
                    } else {
                        ClientImpl.this.onMessage(message);
                    }
                }
            });
        }
        exchange.put(Retryable.class, this);
        exchange.put(Client.class, this);
        exchange.put(Bus.class, this.bus);
        if (endpoint != null) {
            EndpointInfo endpointInfo = endpoint.getEndpointInfo();
            if (boi != null) {
                exchange.put("javax.xml.ws.wsdl.operation", boi.getName());
            }
            QName serviceQName = endpointInfo.getService().getName();
            exchange.put("javax.xml.ws.wsdl.service", serviceQName);
            QName interfaceQName = endpointInfo.getService().getInterface().getName();
            exchange.put("javax.xml.ws.wsdl.interface", interfaceQName);
            QName portQName = endpointInfo.getName();
            exchange.put("javax.xml.ws.wsdl.port", portQName);
            URI wsdlDescription = endpointInfo.getProperty("URI", URI.class);
            if (wsdlDescription == null) {
                String address = endpointInfo.getAddress();
                try {
                    wsdlDescription = new URI(address + "?wsdl");
                }
                catch (URISyntaxException uRISyntaxException) {
                    // empty catch block
                }
                endpointInfo.setProperty("URI", wsdlDescription);
            }
            exchange.put("javax.xml.ws.wsdl.description", wsdlDescription);
        }
    }

    protected PhaseInterceptorChain setupInterceptorChain(Endpoint endpoint) {
        PhaseManager pm = this.bus.getExtension(PhaseManager.class);
        List<Interceptor<? extends Message>> i1 = this.bus.getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by bus: " + i1);
        }
        List<Interceptor<? extends Message>> i2 = this.getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by client: " + i2);
        }
        List<Interceptor<? extends Message>> i3 = endpoint.getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by endpoint: " + i3);
        }
        List<Interceptor<? extends Message>> i4 = endpoint.getBinding().getOutInterceptors();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Interceptors contributed by binding: " + i4);
        }
        List<Interceptor<? extends Message>> i5 = null;
        if (endpoint.getService().getDataBinding() instanceof InterceptorProvider) {
            i5 = ((InterceptorProvider)((Object)endpoint.getService().getDataBinding())).getOutInterceptors();
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Interceptors contributed by databinding: " + i5);
            }
        }
        if (i5 != null) {
            return this.outboundChainCache.get(pm.getOutPhases(), i1, i2, i3, i4, i5);
        }
        return this.outboundChainCache.get(pm.getOutPhases(), i1, i2, i3, i4);
    }

    protected void modifyChain(InterceptorChain chain, Message ctx, boolean in) {
        String key;
        Collection<Interceptor<? extends Message>> is;
        if (ctx == null) {
            return;
        }
        Collection<InterceptorProvider> providers = CastUtils.cast((Collection)ctx.get(Message.INTERCEPTOR_PROVIDERS));
        if (providers != null) {
            for (InterceptorProvider p : providers) {
                if (in) {
                    chain.add(p.getInInterceptors());
                    continue;
                }
                chain.add(p.getOutInterceptors());
            }
        }
        if ((is = CastUtils.cast((Collection)ctx.get(key = in ? Message.IN_INTERCEPTORS : Message.OUT_INTERCEPTORS))) != null) {
            chain.add(is);
        }
    }

    protected void setEndpoint(Endpoint e) {
        this.getConduitSelector().setEndpoint(e);
    }

    public int getSynchronousTimeout() {
        return this.synchronousTimeout;
    }

    public void setSynchronousTimeout(int synchronousTimeout) {
        this.synchronousTimeout = synchronousTimeout;
    }

    @Override
    public final ConduitSelector getConduitSelector() {
        return this.getConduitSelector(null);
    }

    protected final ConduitSelector getConduitSelector(ConduitSelector override) {
        if (null == this.conduitSelector) {
            this.setConduitSelector(override);
        }
        return this.conduitSelector;
    }

    @Override
    public final synchronized void setConduitSelector(ConduitSelector selector) {
        this.conduitSelector = selector == null ? new UpfrontConduitSelector() : selector;
    }

    private boolean isPartialResponse(Message in) {
        return Boolean.TRUE.equals(in.get("org.apache.cxf.partial.response"));
    }

    @Override
    public void close() throws Exception {
        this.destroy();
    }

    @Override
    public void setExecutor(Executor executor2) {
        if (!SynchronousExecutor.isA(executor2)) {
            this.executor = executor2;
        }
    }

    public class IllegalEmptyResponseException
    extends IllegalStateException {
        private static final long serialVersionUID = 1L;

        public IllegalEmptyResponseException() {
        }

        public IllegalEmptyResponseException(String message) {
            super(message);
        }

        public IllegalEmptyResponseException(String message, Throwable cause) {
            super(message, cause);
        }

        public IllegalEmptyResponseException(Throwable cause) {
            super(cause);
        }
    }

    static class ResponseContext
    implements Map<String, Object>,
    Serializable {
        private static final long serialVersionUID = 2L;
        final Map<String, Object> wrapped;
        final Map<Thread, ResponseContext> responseContext;

        ResponseContext(Map<String, Object> origMap, Map<Thread, ResponseContext> rc) {
            this.wrapped = origMap;
            this.responseContext = rc;
        }

        ResponseContext(Map<Thread, ResponseContext> rc) {
            this.wrapped = new HashMap<String, Object>();
            this.responseContext = rc;
        }

        @Override
        public void clear() {
            this.wrapped.clear();
            try {
                for (Map.Entry<Thread, ResponseContext> ent : this.responseContext.entrySet()) {
                    if (ent.getValue() != this) continue;
                    this.responseContext.remove(ent.getKey());
                    return;
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }

        @Override
        public int size() {
            return this.wrapped.size();
        }

        @Override
        public boolean isEmpty() {
            return this.wrapped.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return this.wrapped.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return this.wrapped.containsKey(value);
        }

        @Override
        public Object get(Object key) {
            return this.wrapped.get(key);
        }

        @Override
        public Object put(String key, Object value) {
            return this.wrapped.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return this.wrapped.remove(key);
        }

        @Override
        public void putAll(Map<? extends String, ? extends Object> m4) {
            this.wrapped.putAll(m4);
        }

        @Override
        public Set<String> keySet() {
            return this.wrapped.keySet();
        }

        @Override
        public Collection<Object> values() {
            return this.wrapped.values();
        }

        @Override
        public Set<Map.Entry<String, Object>> entrySet() {
            return this.wrapped.entrySet();
        }
    }

    public class EchoContext
    extends ConcurrentHashMap<String, Object> {
        private static final long serialVersionUID = 1L;

        public EchoContext(Map<String, Object> sharedMap) {
            super(8, 0.75f, 4);
            this.putAll(sharedMap);
        }

        public void reload() {
            super.clear();
            super.putAll(ClientImpl.this.requestContext.get(ClientImpl.this.latestContextThread));
        }

        @Override
        public void clear() {
            super.clear();
            try {
                for (Map.Entry<Thread, EchoContext> ent : ClientImpl.this.requestContext.entrySet()) {
                    if (ent.getValue() != this) continue;
                    ClientImpl.this.requestContext.remove(ent.getKey());
                    return;
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }
}

