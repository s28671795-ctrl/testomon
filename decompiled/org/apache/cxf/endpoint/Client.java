/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.util.Map;
import java.util.concurrent.Executor;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.ClientCallback;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.ConduitSelectorHolder;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.MessageObserver;

public interface Client
extends InterceptorProvider,
MessageObserver,
ConduitSelectorHolder,
AutoCloseable {
    public static final String REQUEST_CONTEXT = "RequestContext";
    public static final String RESPONSE_CONTEXT = "ResponseContext";
    public static final String KEEP_CONDUIT_ALIVE = "KeepConduitAlive";

    public Object[] invoke(String var1, Object ... var2) throws Exception;

    public Object[] invoke(QName var1, Object ... var2) throws Exception;

    public Object[] invokeWrapped(String var1, Object ... var2) throws Exception;

    public Object[] invokeWrapped(QName var1, Object ... var2) throws Exception;

    public Object[] invoke(BindingOperationInfo var1, Object ... var2) throws Exception;

    public Object[] invoke(BindingOperationInfo var1, Object[] var2, Map<String, Object> var3) throws Exception;

    public Object[] invoke(BindingOperationInfo var1, Object[] var2, Map<String, Object> var3, Exchange var4) throws Exception;

    public void invoke(ClientCallback var1, String var2, Object ... var3) throws Exception;

    public void invoke(ClientCallback var1, QName var2, Object ... var3) throws Exception;

    public void invokeWrapped(ClientCallback var1, String var2, Object ... var3) throws Exception;

    public void invokeWrapped(ClientCallback var1, QName var2, Object ... var3) throws Exception;

    public void invoke(ClientCallback var1, BindingOperationInfo var2, Object ... var3) throws Exception;

    public void invoke(ClientCallback var1, BindingOperationInfo var2, Object[] var3, Map<String, Object> var4) throws Exception;

    public void invoke(ClientCallback var1, BindingOperationInfo var2, Object[] var3, Exchange var4) throws Exception;

    public void invoke(ClientCallback var1, BindingOperationInfo var2, Object[] var3, Map<String, Object> var4, Exchange var5) throws Exception;

    public Map<String, Object> getRequestContext();

    public Map<String, Object> getResponseContext();

    public void setThreadLocalRequestContext(boolean var1);

    public boolean isThreadLocalRequestContext();

    default public Contexts getContexts() {
        return new Contexts(){

            @Override
            public void close() throws Exception {
                this.getRequestContext().clear();
                this.getResponseContext().clear();
            }

            @Override
            public Map<String, Object> getRequestContext() {
                return Client.this.getRequestContext();
            }

            @Override
            public Map<String, Object> getResponseContext() {
                return Client.this.getResponseContext();
            }
        };
    }

    public Endpoint getEndpoint();

    public Conduit getConduit();

    @Override
    public ConduitSelector getConduitSelector();

    @Override
    public void setConduitSelector(ConduitSelector var1);

    public void destroy();

    public void setExecutor(Executor var1);

    public Bus getBus();

    public static interface Contexts
    extends AutoCloseable {
        public Map<String, Object> getRequestContext();

        public Map<String, Object> getResponseContext();
    }
}

