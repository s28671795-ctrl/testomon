/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Holder;
import javax.xml.ws.Response;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.binding.soap.model.SoapBindingInfo;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientCallback;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.EndpointReferenceBuilder;
import org.apache.cxf.jaxws.JaxWsClientProxy;
import org.apache.cxf.jaxws.JaxwsClientCallback;
import org.apache.cxf.jaxws.JaxwsResponseCallback;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.jaxws.interceptors.MessageModeInInterceptor;
import org.apache.cxf.jaxws.interceptors.MessageModeOutInterceptor;
import org.apache.cxf.jaxws.support.JaxWsClientEndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxSource;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DispatchImpl<T>
implements Dispatch<T>,
BindingProvider,
Closeable {
    private static final Logger LOG = LogUtils.getL7dLogger(DispatchImpl.class);
    private static final String DISPATCH_NS = "http://cxf.apache.org/jaxws/dispatch";
    private static final String INVOKE_NAME = "Invoke";
    private static final String INVOKE_ONEWAY_NAME = "InvokeOneWay";
    private static final QName INVOKE_QNAME = new QName("http://cxf.apache.org/jaxws/dispatch", "Invoke");
    private static final QName INVOKE_ONEWAY_QNAME = new QName("http://cxf.apache.org/jaxws/dispatch", "InvokeOneWay");
    private final Binding binding;
    private final EndpointReferenceBuilder builder;
    private final Client client;
    private final Class<T> cl;
    private final JAXBContext context;
    private Message error;
    private Service.Mode mode;

    DispatchImpl(Client client, Service.Mode m4, JAXBContext ctx, Class<T> clazz) {
        this.binding = ((JaxWsEndpointImpl)client.getEndpoint()).getJaxwsBinding();
        this.builder = new EndpointReferenceBuilder((JaxWsEndpointImpl)client.getEndpoint());
        this.client = client;
        this.mode = m4;
        this.context = ctx;
        this.cl = clazz;
        this.setupEndpointAddressContext(client.getEndpoint());
        this.addInvokeOperation(false);
        this.addInvokeOperation(true);
        if (m4 == Service.Mode.MESSAGE && this.binding instanceof SOAPBinding) {
            if (DataSource.class.isAssignableFrom(clazz)) {
                this.error = new Message("DISPATCH_OBJECT_NOT_SUPPORTED", LOG, new Object[]{"DataSource", m4, "SOAP/HTTP"});
            } else if (m4 == Service.Mode.MESSAGE) {
                SAAJOutInterceptor saajOut = new SAAJOutInterceptor();
                client.getOutInterceptors().add(saajOut);
                client.getOutInterceptors().add(new MessageModeOutInterceptor(saajOut, client.getEndpoint().getBinding().getBindingInfo().getName()));
                client.getInInterceptors().add(new SAAJInInterceptor());
                client.getInInterceptors().add(new MessageModeInInterceptor(clazz, client.getEndpoint().getBinding().getBindingInfo().getName()));
            }
        } else if (m4 == Service.Mode.PAYLOAD && this.binding instanceof SOAPBinding && SOAPMessage.class.isAssignableFrom(clazz)) {
            this.error = new Message("DISPATCH_OBJECT_NOT_SUPPORTED", LOG, new Object[]{"SOAPMessage", m4, "SOAP/HTTP"});
        }
    }

    DispatchImpl(Client cl, Service.Mode m4, Class<T> clazz) {
        this(cl, m4, null, clazz);
    }

    private void addInvokeOperation(boolean oneWay) {
        String name = oneWay ? INVOKE_ONEWAY_NAME : INVOKE_NAME;
        ServiceInfo info = this.client.getEndpoint().getEndpointInfo().getService();
        OperationInfo opInfo = info.getInterface().addOperation(oneWay ? INVOKE_ONEWAY_QNAME : INVOKE_QNAME);
        MessageInfo mInfo = opInfo.createMessage(new QName(DISPATCH_NS, name + "Request"), MessageInfo.Type.INPUT);
        opInfo.setInput(name + "Request", mInfo);
        MessagePartInfo mpi = mInfo.addMessagePart("parameters");
        if (this.context == null) {
            mpi.setTypeClass(this.cl);
        }
        mpi.setElement(true);
        if (!oneWay) {
            mInfo = opInfo.createMessage(new QName(DISPATCH_NS, name + "Response"), MessageInfo.Type.OUTPUT);
            opInfo.setOutput(name + "Response", mInfo);
            mpi = mInfo.addMessagePart("parameters");
            mpi.setElement(true);
            if (this.context == null) {
                mpi.setTypeClass(this.cl);
            }
        }
        for (BindingInfo bind : this.client.getEndpoint().getEndpointInfo().getService().getBindings()) {
            BindingOperationInfo bo = new BindingOperationInfo(bind, opInfo);
            bind.addOperation(bo);
        }
    }

    private void addInvokeOperation(QName operationName, boolean oneWay) {
        ServiceInfo info = this.client.getEndpoint().getEndpointInfo().getService();
        OperationInfo invokeOpInfo = info.getInterface().getOperation(oneWay ? INVOKE_ONEWAY_QNAME : INVOKE_QNAME);
        OperationInfo opInfo = info.getInterface().addOperation(operationName);
        opInfo.setInput(invokeOpInfo.getInputName(), invokeOpInfo.getInput());
        if (!oneWay) {
            opInfo.setOutput(invokeOpInfo.getOutputName(), invokeOpInfo.getOutput());
        }
        for (BindingInfo bind : this.client.getEndpoint().getEndpointInfo().getService().getBindings()) {
            BindingOperationInfo bo = new BindingOperationInfo(bind, opInfo);
            bind.addOperation(bo);
        }
    }

    @Override
    public Map<String, Object> getRequestContext() {
        return new WrappedMessageContext(this.client.getRequestContext(), null, MessageContext.Scope.APPLICATION);
    }

    @Override
    public Map<String, Object> getResponseContext() {
        return new WrappedMessageContext(this.client.getResponseContext(), null, MessageContext.Scope.APPLICATION);
    }

    @Override
    public Binding getBinding() {
        return this.binding;
    }

    @Override
    public EndpointReference getEndpointReference() {
        return this.builder.getEndpointReference();
    }

    public <X extends EndpointReference> X getEndpointReference(Class<X> clazz) {
        return this.builder.getEndpointReference(clazz);
    }

    private void setupEndpointAddressContext(Endpoint endpoint) {
        if (null != endpoint && null != endpoint.getEndpointInfo().getAddress()) {
            WrappedMessageContext requestContext = new WrappedMessageContext(this.client.getRequestContext(), null, MessageContext.Scope.APPLICATION);
            requestContext.put("javax.xml.ws.service.endpoint.address", endpoint.getEndpointInfo().getAddress());
        }
    }

    @Override
    public T invoke(T obj) {
        return this.invoke(obj, false);
    }

    private void checkError() {
        if (this.error != null) {
            if (this.getBinding() instanceof SOAPBinding) {
                SOAPFault soapFault = null;
                try {
                    soapFault = JaxWsClientProxy.createSoapFault((SOAPBinding)this.getBinding(), new Exception(this.error.toString()));
                }
                catch (SOAPException sOAPException) {
                    // empty catch block
                }
                if (soapFault != null) {
                    throw new SOAPFaultException(soapFault);
                }
            } else if (this.getBinding() instanceof HTTPBinding) {
                HTTPException exception = new HTTPException(500);
                exception.initCause(new Exception(this.error.toString()));
                throw exception;
            }
            throw new WebServiceException(this.error.toString());
        }
    }

    private RuntimeException mapException(Exception ex) {
        if (ex instanceof Fault && ex.getCause() instanceof IOException) {
            throw new WebServiceException(ex.getMessage(), ex.getCause());
        }
        if (this.getBinding() instanceof HTTPBinding) {
            HTTPException exception = new HTTPException(500);
            exception.initCause(ex);
            return exception;
        }
        if (this.getBinding() instanceof SOAPBinding) {
            SOAPFault soapFault = null;
            try {
                soapFault = JaxWsClientProxy.createSoapFault((SOAPBinding)this.getBinding(), ex);
            }
            catch (SOAPException sOAPException) {
                // empty catch block
            }
            if (soapFault == null) {
                return new WebServiceException(ex);
            }
            SOAPFaultException exception = new SOAPFaultException(soapFault);
            exception.initCause(ex);
            return exception;
        }
        return new WebServiceException(ex);
    }

    public T invoke(T obj, boolean isOneWay) {
        this.checkError();
        try {
            boolean hasOpName;
            if (obj instanceof SOAPMessage) {
                SOAPMessage msg = (SOAPMessage)obj;
                if (msg.countAttachments() > 0) {
                    this.client.getRequestContext().put("write.attachments", Boolean.TRUE);
                }
            } else if (this.context != null) {
                Boolean unwrapProperty = obj instanceof JAXBElement ? Boolean.FALSE : Boolean.TRUE;
                this.getRequestContext().put("unwrap.jaxb.element", unwrapProperty);
            }
            QName opName = (QName)this.getRequestContext().get("javax.xml.ws.wsdl.operation");
            if (opName == null) {
                hasOpName = false;
                opName = isOneWay ? INVOKE_ONEWAY_QNAME : INVOKE_QNAME;
            } else {
                hasOpName = true;
                BindingOperationInfo bop = this.client.getEndpoint().getBinding().getBindingInfo().getOperation(opName);
                if (bop == null) {
                    this.addInvokeOperation(opName, isOneWay);
                }
            }
            Holder<T> holder = new Holder<T>(obj);
            opName = this.calculateOpName(holder, opName, hasOpName);
            Object[] ret = this.client.invokeWrapped(opName, holder.value);
            if (isOneWay || ret == null || ret.length == 0) {
                return null;
            }
            return (T)ret[0];
        }
        catch (ClientImpl.IllegalEmptyResponseException ie) {
            return null;
        }
        catch (Exception ex) {
            throw this.mapException(ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private QName calculateOpName(Holder<T> holder, QName opName, boolean hasOpName) throws XMLStreamException {
        boolean findDispatchOp = Boolean.TRUE.equals(this.getRequestContext().get("find.dispatch.operation"));
        if (!findDispatchOp) {
            List<Feature> allFeatures;
            List<Feature> endpointFeatures = ((JaxWsClientEndpointImpl)this.client.getEndpoint()).getFeatures();
            if (this.client.getBus().getFeatures() != null) {
                allFeatures = new ArrayList<Feature>(endpointFeatures.size() + this.client.getBus().getFeatures().size());
                allFeatures.addAll(endpointFeatures);
                allFeatures.addAll(this.client.getBus().getFeatures());
            } else {
                allFeatures = endpointFeatures;
            }
            for (Feature feature : allFeatures) {
                if (!(feature instanceof WSAddressingFeature)) continue;
                findDispatchOp = true;
            }
        }
        StaxSource createdSource = null;
        Map<String, QName> payloadOPMap = this.createPayloadEleOpNameMap(this.client.getEndpoint().getBinding().getBindingInfo(), hasOpName);
        if (findDispatchOp && !payloadOPMap.isEmpty()) {
            QName payloadElementName = null;
            if (holder.value instanceof Source) {
                XMLStreamReader reader = null;
                try {
                    reader = StaxUtils.createXMLStreamReader((Source)holder.value);
                    Document document = StaxUtils.read(reader);
                    createdSource = new StaxSource(StaxUtils.createXMLStreamReader(document));
                    payloadElementName = this.getPayloadElementName(document.getDocumentElement());
                }
                catch (Exception document) {
                    StaxUtils.close(reader);
                    catch (Throwable throwable) {
                        StaxUtils.close(reader);
                        throw throwable;
                    }
                }
                StaxUtils.close(reader);
            }
            if (holder.value instanceof SOAPMessage) {
                payloadElementName = this.getPayloadElementName((SOAPMessage)holder.value);
            }
            if (this.context != null) {
                payloadElementName = this.getPayloadElementName(holder.value);
            }
            if (payloadElementName != null) {
                BindingOperationInfo dbop;
                QName expectedElementName;
                if (hasOpName && ((expectedElementName = payloadOPMap.get(opName.toString())) == null || !expectedElementName.toString().equals(payloadElementName.toString()))) {
                    hasOpName = false;
                    payloadOPMap = this.createPayloadEleOpNameMap(this.client.getEndpoint().getBinding().getBindingInfo(), hasOpName);
                }
                QName dispatchedOpName = null;
                if (!hasOpName) {
                    dispatchedOpName = payloadOPMap.get(payloadElementName.toString());
                }
                if (null != dispatchedOpName && (dbop = this.client.getEndpoint().getBinding().getBindingInfo().getOperation(dispatchedOpName)) != null) {
                    opName = dispatchedOpName;
                }
            }
        }
        if (createdSource != null) {
            holder.value = createdSource;
        }
        return opName;
    }

    @Override
    public Future<?> invokeAsync(T obj, AsyncHandler<T> asyncHandler) {
        this.checkError();
        this.client.setExecutor(this.getClient().getEndpoint().getExecutor());
        JaxwsClientCallback callback = new JaxwsClientCallback<T>(asyncHandler, this){

            @Override
            protected Throwable mapThrowable(Throwable t2) {
                if (t2 instanceof IOException) {
                    return t2;
                }
                if (t2 instanceof Exception) {
                    t2 = DispatchImpl.this.mapException((Exception)t2);
                }
                return t2;
            }
        };
        JaxwsResponseCallback ret = new JaxwsResponseCallback(callback);
        try {
            boolean hasOpName;
            QName opName = (QName)this.getRequestContext().get("javax.xml.ws.wsdl.operation");
            if (opName == null) {
                hasOpName = false;
                opName = INVOKE_QNAME;
            } else {
                hasOpName = true;
                BindingOperationInfo bop = this.client.getEndpoint().getBinding().getBindingInfo().getOperation(opName);
                if (bop == null) {
                    this.addInvokeOperation(opName, false);
                }
            }
            Holder<T> holder = new Holder<T>(obj);
            opName = this.calculateOpName(holder, opName, hasOpName);
            this.client.invokeWrapped((ClientCallback)callback, opName, holder.value);
            return ret;
        }
        catch (Exception ex) {
            throw this.mapException(ex);
        }
    }

    @Override
    public Response<T> invokeAsync(T obj) {
        return (Response)this.invokeAsync(obj, null);
    }

    @Override
    public void invokeOneWay(T obj) {
        this.invoke(obj, true);
    }

    public Client getClient() {
        return this.client;
    }

    private QName getPayloadElementName(Element ele) {
        XMLStreamReader xmlreader = StaxUtils.createXMLStreamReader(ele);
        DepthXMLStreamReader reader = new DepthXMLStreamReader(xmlreader);
        try {
            if (this.mode == Service.Mode.PAYLOAD) {
                StaxUtils.skipToStartOfElement(reader);
                return reader.getName();
            }
            if (this.mode == Service.Mode.MESSAGE) {
                StaxUtils.skipToStartOfElement(reader);
                StaxUtils.toNextTag(reader, new QName(ele.getNamespaceURI(), "Body"));
                reader.nextTag();
                return reader.getName();
            }
        }
        catch (XMLStreamException xMLStreamException) {
            // empty catch block
        }
        return null;
    }

    private QName getPayloadElementName(SOAPMessage soapMessage) {
        try {
            Element element = DOMUtils.getFirstElement(SAAJUtils.getBody(soapMessage));
            if (element != null) {
                return DOMUtils.getElementQName(element);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private QName getPayloadElementName(Object object) {
        block13: {
            DepthXMLStreamReader reader;
            XMLStreamWriter resultWriter;
            block12: {
                JAXBDataBinding dataBinding = new JAXBDataBinding();
                dataBinding.setContext(this.context);
                DataWriter<XMLStreamWriter> dbwriter = dataBinding.createWriter(XMLStreamWriter.class);
                StringWriter stringWriter = new StringWriter();
                resultWriter = StaxUtils.createXMLStreamWriter(stringWriter);
                reader = null;
                dbwriter.write(object, resultWriter);
                resultWriter.flush();
                if (StringUtils.isEmpty(stringWriter.toString())) break block12;
                ByteArrayInputStream binput = new ByteArrayInputStream(stringWriter.getBuffer().toString().getBytes());
                XMLStreamReader xmlreader = StaxUtils.createXMLStreamReader(binput);
                reader = new DepthXMLStreamReader(xmlreader);
                StaxUtils.skipToStartOfElement(reader);
                QName qName = reader.getName();
                try {
                    StaxUtils.close(reader);
                }
                catch (XMLStreamException xMLStreamException) {
                    // empty catch block
                }
                StaxUtils.close(resultWriter);
                return qName;
            }
            try {
                StaxUtils.close(reader);
            }
            catch (XMLStreamException xMLStreamException) {
                // empty catch block
            }
            StaxUtils.close(resultWriter);
            break block13;
            catch (XMLStreamException xMLStreamException) {
                try {
                    StaxUtils.close(reader);
                }
                catch (XMLStreamException xMLStreamException2) {
                    // empty catch block
                }
                StaxUtils.close(resultWriter);
                catch (Throwable throwable) {
                    try {
                        StaxUtils.close(reader);
                    }
                    catch (XMLStreamException xMLStreamException3) {
                        // empty catch block
                    }
                    StaxUtils.close(resultWriter);
                    throw throwable;
                }
            }
        }
        return null;
    }

    private Map<String, QName> createPayloadEleOpNameMap(BindingInfo bindingInfo, boolean reverseMapping) {
        String tempStyle;
        HashMap<String, QName> payloadElementMap = new HashMap<String, QName>();
        String bindingStyle = "document";
        if (bindingInfo instanceof SoapBindingInfo && (tempStyle = ((SoapBindingInfo)bindingInfo).getStyle()) != null) {
            bindingStyle = tempStyle;
        }
        for (BindingOperationInfo bop : bindingInfo.getOperations()) {
            String operationStyle;
            SoapOperationInfo soi = bop.getExtensor(SoapOperationInfo.class);
            if (soi == null) continue;
            String string = operationStyle = soi.getStyle() != null ? soi.getStyle() : bindingStyle;
            if ("document".equals(operationStyle)) {
                if (bop.getOperationInfo().getInput() == null || bop.getOperationInfo().getInput().getMessagePartsNumber() <= 0) continue;
                QName qn = bop.getOperationInfo().getInput().getMessagePartByIndex(0).getElementQName();
                QName op = bop.getOperationInfo().getName();
                if (reverseMapping) {
                    payloadElementMap.put(op.toString(), qn);
                    continue;
                }
                payloadElementMap.put(qn.toString(), op);
                continue;
            }
            if (!"rpc".equals(operationStyle)) continue;
            QName op = bop.getOperationInfo().getName();
            payloadElementMap.put(op.toString(), op);
        }
        return payloadElementMap;
    }

    @Override
    public void close() throws IOException {
        this.client.destroy();
    }
}

