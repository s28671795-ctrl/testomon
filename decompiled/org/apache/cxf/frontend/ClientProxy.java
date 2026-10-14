/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;

public class ClientProxy
implements InvocationHandler,
Closeable {
    private static final Logger LOG = LogUtils.getL7dLogger(ClientProxy.class);
    protected Client client;
    private Endpoint endpoint;

    public ClientProxy(Client c) {
        this.endpoint = c.getEndpoint();
        this.client = c;
    }

    @Override
    public void close() throws IOException {
        if (this.client != null) {
            this.client.destroy();
            this.client = null;
            this.endpoint = null;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        if (method2.getDeclaringClass().equals(Object.class) || method2.getDeclaringClass().equals(Closeable.class)) {
            return method2.invoke((Object)this, new Object[0]);
        }
        if (method2.getDeclaringClass().isInstance(this.client)) {
            return method2.invoke((Object)this.client, args2);
        }
        MethodDispatcher dispatcher = (MethodDispatcher)this.endpoint.getService().get(MethodDispatcher.class.getName());
        BindingOperationInfo oi = dispatcher.getBindingOperation(method2, this.endpoint);
        if (oi == null) {
            throw new Fault(new Message("NO_OPERATION_INFO", LOG, method2.getName()));
        }
        Object[] params = args2;
        if (null == params) {
            params = new Object[]{};
        }
        Object o = this.invokeSync(method2, oi, params);
        return this.adjustObject(o);
    }

    protected Object adjustObject(Object o) {
        return o;
    }

    public Object invokeSync(Method method2, BindingOperationInfo oi, Object[] params) throws Exception {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        Object[] rawRet = this.client.invoke(oi, params);
        if (rawRet != null && rawRet.length > 0) {
            return rawRet[0];
        }
        return null;
    }

    public Map<String, Object> getRequestContext() {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        return this.client.getRequestContext();
    }

    public Map<String, Object> getResponseContext() {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        return this.client.getResponseContext();
    }

    public Client getClient() {
        return this.client;
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public static Client getClient(Object o) {
        return ((ClientProxy)Proxy.getInvocationHandler(o)).getClient();
    }
}

