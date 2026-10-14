/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Response;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.saaj.SAAJFactoryResolver;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientCallback;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.EndpointReferenceBuilder;
import org.apache.cxf.jaxws.JaxwsClientCallback;
import org.apache.cxf.jaxws.JaxwsResponseCallback;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.w3c.dom.Node;

public class JaxWsClientProxy
extends ClientProxy
implements BindingProvider {
    public static final String THREAD_LOCAL_REQUEST_CONTEXT = "thread.local.request.context";
    private static final Logger LOG = LogUtils.getL7dLogger(JaxWsClientProxy.class);
    private Binding binding;
    private EndpointReferenceBuilder builder;

    public JaxWsClientProxy(Client c, Binding b) {
        super(c);
        this.binding = b;
        this.setupEndpointAddressContext(this.getClient().getEndpoint());
        this.builder = new EndpointReferenceBuilder((JaxWsEndpointImpl)this.getClient().getEndpoint());
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.binding = null;
        this.builder = null;
    }

    private void setupEndpointAddressContext(Endpoint endpoint) {
        if (null != endpoint && null != endpoint.getEndpointInfo().getAddress()) {
            this.getRequestContext().put("javax.xml.ws.service.endpoint.address", endpoint.getEndpointInfo().getAddress());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        Endpoint endpoint = this.getClient().getEndpoint();
        String address = endpoint.getEndpointInfo().getAddress();
        MethodDispatcher dispatcher = (MethodDispatcher)endpoint.getService().get(MethodDispatcher.class.getName());
        Object[] params = args2;
        if (null == params) {
            params = new Object[]{};
        }
        try {
            if (method2.getDeclaringClass().equals(BindingProvider.class) || method2.getDeclaringClass().equals(Object.class) || method2.getDeclaringClass().equals(Closeable.class) || method2.getDeclaringClass().equals(AutoCloseable.class)) {
                return method2.invoke((Object)this, params);
            }
            if (method2.getDeclaringClass().isInstance(this.client)) {
                return method2.invoke((Object)this.client, params);
            }
        }
        catch (InvocationTargetException e) {
            throw e.getCause();
        }
        BindingOperationInfo oi = dispatcher.getBindingOperation(method2, endpoint);
        if (oi == null) {
            Message msg = new Message("NO_BINDING_OPERATION_INFO", LOG, method2.getName());
            throw new WebServiceException(msg.toString());
        }
        this.client.getRequestContext().put(Method.class.getName(), method2);
        boolean isAsync = this.isAsync(method2);
        Object result = null;
        try {
            result = isAsync ? this.invokeAsync(method2, oi, params) : this.invokeSync(method2, oi, params);
        }
        catch (WebServiceException wex) {
            throw wex;
        }
        catch (Exception ex) {
            throw this.mapException(method2, oi, ex);
        }
        finally {
            if (this.addressChanged(address)) {
                this.setupEndpointAddressContext(this.getClient().getEndpoint());
            }
        }
        Map<String, Object> respContext = this.client.getResponseContext();
        Map scopes = CastUtils.cast((Map)respContext.get(WrappedMessageContext.SCOPES));
        if (scopes != null) {
            for (Map.Entry scope : scopes.entrySet()) {
                if (scope.getValue() != MessageContext.Scope.HANDLER) continue;
                respContext.remove(scope.getKey());
            }
        }
        return this.adjustObject(result);
    }

    Exception mapException(Method method2, BindingOperationInfo boi, Exception ex) {
        if (method2 != null) {
            for (Class<?> excls : method2.getExceptionTypes()) {
                if (!excls.isInstance(ex)) continue;
                return ex;
            }
        } else if (boi != null) {
            for (BindingFaultInfo fi : boi.getFaults()) {
                Class c = fi.getFaultInfo().getProperty(Class.class.getName(), Class.class);
                if (c == null || !c.isInstance(ex)) continue;
                return ex;
            }
            if (ex instanceof IOException) {
                return ex;
            }
        }
        if (ex instanceof Fault && ex.getCause() instanceof IOException) {
            return new WebServiceException(ex.getMessage(), ex.getCause());
        }
        if (this.getBinding() instanceof HTTPBinding) {
            HTTPException exception = new HTTPException(500);
            exception.initCause(ex);
            return exception;
        }
        if (this.getBinding() instanceof SOAPBinding) {
            try {
                SOAPFault soapFault = JaxWsClientProxy.createSoapFault((SOAPBinding)this.getBinding(), ex);
                if (soapFault == null) {
                    throw new WebServiceException(ex);
                }
                SOAPFaultException exception = new SOAPFaultException(soapFault);
                if (ex instanceof Fault && ex.getCause() != null) {
                    exception.initCause(ex.getCause());
                } else {
                    exception.initCause(ex);
                }
                return exception;
            }
            catch (SOAPException e) {
                return new WebServiceException(ex);
            }
        }
        return new WebServiceException(ex);
    }

    boolean isAsync(Method m4) {
        return m4.getName().endsWith("Async") && (Future.class.equals(m4.getReturnType()) || Response.class.equals(m4.getReturnType()));
    }

    static SOAPFault createSoapFault(SOAPBinding binding, Exception ex) throws SOAPException {
        SOAPFault soapFault;
        try {
            soapFault = binding.getSOAPFactory().createFault();
        }
        catch (Throwable t2) {
            try {
                soapFault = binding.getMessageFactory().createMessage().getSOAPPart().getEnvelope().getBody().addFault();
            }
            catch (Throwable t22) {
                return null;
            }
        }
        if (ex instanceof SoapFault) {
            if (!soapFault.getNamespaceURI().equals(((SoapFault)ex).getFaultCode().getNamespaceURI()) && "http://schemas.xmlsoap.org/soap/envelope/".equals(((SoapFault)ex).getFaultCode().getNamespaceURI())) {
                try {
                    soapFault = SAAJFactoryResolver.createSOAPFactory(null).createFault();
                }
                catch (Throwable t2) {
                    // empty catch block
                }
            }
            boolean isSoap11 = "http://schemas.xmlsoap.org/soap/envelope/".equals(soapFault.getNamespaceURI());
            if (StringUtils.isEmpty(((SoapFault)ex).getLang())) {
                soapFault.setFaultString(((SoapFault)ex).getReason());
            } else {
                soapFault.setFaultString(((SoapFault)ex).getReason(), JaxWsClientProxy.stringToLocale(((SoapFault)ex).getLang()));
            }
            SAAJUtils.setFaultCode(soapFault, ((SoapFault)ex).getFaultCode());
            String role = ((SoapFault)ex).getRole();
            if (role != null) {
                soapFault.setFaultActor(role);
            }
            if (((SoapFault)ex).getSubCodes() != null && !isSoap11) {
                for (QName fsc : ((SoapFault)ex).getSubCodes()) {
                    soapFault.appendFaultSubcode(fsc);
                }
            }
            if (((SoapFault)ex).hasDetails()) {
                Node nd = soapFault.getOwnerDocument().importNode(((SoapFault)ex).getDetail(), true);
                nd = nd.getFirstChild();
                soapFault.addDetail();
                while (nd != null) {
                    Node next = nd.getNextSibling();
                    soapFault.getDetail().appendChild(nd);
                    nd = next;
                }
            }
        } else {
            String msg = ex.getMessage();
            if (msg != null) {
                soapFault.setFaultString(msg);
            }
        }
        return soapFault;
    }

    private static Locale stringToLocale(String locale) {
        String[] parts = locale.split("-", 0);
        if (parts.length == 1) {
            return new Locale(parts[0]);
        }
        if (parts.length == 2) {
            return new Locale(parts[0], parts[1]);
        }
        return new Locale(parts[0], parts[1], parts[2]);
    }

    private boolean addressChanged(String address) {
        return address != null && this.getClient().getEndpoint().getEndpointInfo() != null && !address.equals(this.getClient().getEndpoint().getEndpointInfo().getAddress());
    }

    private Object invokeAsync(Method method2, final BindingOperationInfo oi, Object[] params) throws Exception {
        AsyncHandler handler;
        this.client.setExecutor(this.getClient().getEndpoint().getExecutor());
        if (params.length > 0 && params[params.length - 1] instanceof AsyncHandler) {
            handler = (AsyncHandler)params[params.length - 1];
            Object[] newParams = new Object[params.length - 1];
            System.arraycopy(params, 0, newParams, 0, newParams.length);
            params = newParams;
        } else {
            handler = null;
        }
        JaxwsClientCallback<Object> callback = new JaxwsClientCallback<Object>(handler, (Object)this){

            @Override
            protected Throwable mapThrowable(Throwable t2) {
                if (t2 instanceof Exception) {
                    t2 = JaxWsClientProxy.this.mapException(null, oi, (Exception)t2);
                }
                return t2;
            }
        };
        JaxwsResponseCallback ret = new JaxwsResponseCallback(callback);
        this.client.invoke((ClientCallback)callback, oi, params);
        return ret;
    }

    @Override
    public Map<String, Object> getRequestContext() {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        return new WrappedMessageContext(this.getClient().getRequestContext(), null, MessageContext.Scope.APPLICATION);
    }

    @Override
    public Map<String, Object> getResponseContext() {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        return new WrappedMessageContext(this.getClient().getResponseContext(), null, MessageContext.Scope.APPLICATION);
    }

    @Override
    public Binding getBinding() {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        return this.binding;
    }

    @Override
    public EndpointReference getEndpointReference() {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        return this.builder.getEndpointReference();
    }

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz) {
        if (this.client == null) {
            throw new IllegalStateException("The client has been closed.");
        }
        return this.builder.getEndpointReference(clazz);
    }
}

