/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.WebFault;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.NullConduitSelector;
import org.apache.cxf.endpoint.PreexistingConduitSelector;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.OutgoingChainInterceptor;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.Extensible;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.workqueue.AutomaticWorkQueue;
import org.apache.cxf.workqueue.OneShotAsyncExecutor;
import org.apache.cxf.workqueue.SynchronousExecutor;
import org.apache.cxf.workqueue.WorkQueueManager;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.ContextUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.ws.addressing.FaultAction;
import org.apache.cxf.ws.addressing.JAXWSAConstants;
import org.apache.cxf.ws.addressing.Names;

final class InternalContextUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(InternalContextUtils.class);

    private InternalContextUtils() {
    }

    public static void rebaseResponse(EndpointReferenceType reference, AddressingProperties inMAPs, final Message inMessage) {
        String namespaceURI = inMAPs.getNamespaceURI();
        if (!ContextUtils.retrievePartialResponseSent(inMessage)) {
            ContextUtils.storePartialResponseSent(inMessage);
            Exchange exchange = inMessage.getExchange();
            Message fullResponse = exchange.getOutMessage();
            Message partialResponse = ContextUtils.createMessage(exchange);
            InternalContextUtils.ensurePartialResponseMAPs(partialResponse, namespaceURI);
            ContextUtils.propogateReceivedMAPs(inMAPs, partialResponse);
            Destination target = inMessage.getDestination();
            if (target == null) {
                return;
            }
            try {
                if (reference == null) {
                    reference = ContextUtils.getNoneEndpointReference();
                }
                Conduit backChannel = target.getBackChannel(inMessage);
                Exception exception = inMessage.getContent(Exception.class);
                if (backChannel != null && !inMessage.getExchange().isOneWay() && ContextUtils.isFault(inMessage)) {
                    exchange.setOutMessage(ContextUtils.createMessage(exchange));
                    exchange.put(ConduitSelector.class, new NullConduitSelector());
                    exchange.put("org.apache.cxf.http.no_io_exceptions", true);
                    Destination destination = InternalContextUtils.createDecoupledDestination(exchange, reference);
                    exchange.setDestination(destination);
                    if (ContextUtils.retrieveAsyncPostResponseDispatch(inMessage)) {
                        DelegatingInputStream in = inMessage.getContent(DelegatingInputStream.class);
                        if (in != null) {
                            in.cacheInput();
                        }
                        inMessage.getInterceptorChain().reset();
                        if (inMessage.get(Message.PATH_INFO) != null) {
                            inMessage.remove(Message.PATH_INFO);
                        }
                        inMessage.getInterceptorChain().doIntercept(inMessage);
                    }
                    partialResponse.put("forced.faultstring", "The server sent HTTP status code :" + inMessage.getExchange().get(Message.RESPONSE_CODE));
                    partialResponse.setContent(Exception.class, exception);
                    partialResponse.put(Message.PROTOCOL_HEADERS, inMessage.get(Message.PROTOCOL_HEADERS));
                    partialResponse.put(Message.ENCODING, inMessage.get(Message.ENCODING));
                    partialResponse.put(ContextUtils.ACTION, inMessage.get(ContextUtils.ACTION));
                    partialResponse.put("javax.xml.ws.addressing.context.inbound", inMessage.get("javax.xml.ws.addressing.context.inbound"));
                    partialResponse.put("javax.xml.ws.addressing.context.outbound", inMessage.get("javax.xml.ws.addressing.context.outbound"));
                    exchange.setOutMessage(partialResponse);
                    PhaseInterceptorChain newChian = ((PhaseInterceptorChain)inMessage.getInterceptorChain()).cloneChain();
                    partialResponse.setInterceptorChain(newChian);
                    exchange.setDestination(target);
                    exchange.setOneWay(false);
                    exchange.put(ConduitSelector.class, new PreexistingConduitSelector(backChannel, exchange.getEndpoint()));
                    if (newChian != null && !newChian.doIntercept(partialResponse) && partialResponse.getContent(Exception.class) != null) {
                        if (partialResponse.getContent(Exception.class) instanceof Fault) {
                            throw (Fault)partialResponse.getContent(Exception.class);
                        }
                        throw new Fault(partialResponse.getContent(Exception.class));
                    }
                    return;
                }
                if (backChannel != null) {
                    DelegatingInputStream in;
                    partialResponse.put("org.apache.cxf.partial.response", Boolean.TRUE);
                    partialResponse.put("org.apache.cxf.partial.response.empty", Boolean.TRUE);
                    boolean robust = MessageUtils.getContextualBoolean(inMessage, "org.apache.cxf.oneway.robust", false);
                    if (robust) {
                        BindingOperationInfo boi = exchange.getBindingOperationInfo();
                        exchange.put(Executor.class, InternalContextUtils.getExecutor(inMessage));
                        inMessage.getInterceptorChain().pause();
                        inMessage.getInterceptorChain().resume();
                        MessageObserver faultObserver = inMessage.getInterceptorChain().getFaultObserver();
                        if (null != inMessage.getContent(Exception.class) && null != faultObserver) {
                            inMessage.getExchange().setOneWay(false);
                            faultObserver.onMessage(inMessage);
                            return;
                        }
                        exchange.put(BindingOperationInfo.class, boi);
                    }
                    InterceptorChain chain = fullResponse != null ? fullResponse.getInterceptorChain() : OutgoingChainInterceptor.getOutInterceptorChain(exchange);
                    exchange.setOutMessage(partialResponse);
                    partialResponse.setInterceptorChain(chain);
                    exchange.put(ConduitSelector.class, new PreexistingConduitSelector(backChannel, exchange.getEndpoint()));
                    if (ContextUtils.retrieveAsyncPostResponseDispatch(inMessage) && !robust && (in = inMessage.getContent(DelegatingInputStream.class)) != null) {
                        in.cacheInput();
                    }
                    if (chain != null && !chain.doIntercept(partialResponse) && partialResponse.getContent(Exception.class) != null) {
                        if (partialResponse.getContent(Exception.class) instanceof Fault) {
                            throw (Fault)partialResponse.getContent(Exception.class);
                        }
                        throw new Fault(partialResponse.getContent(Exception.class));
                    }
                    if (chain != null) {
                        chain.reset();
                    }
                    exchange.put(ConduitSelector.class, new NullConduitSelector());
                    if (fullResponse == null) {
                        fullResponse = ContextUtils.createMessage(exchange);
                    }
                    exchange.setOutMessage(fullResponse);
                    Destination destination = InternalContextUtils.createDecoupledDestination(exchange, reference);
                    exchange.setDestination(destination);
                    if (ContextUtils.retrieveAsyncPostResponseDispatch(inMessage) && !robust) {
                        if (inMessage.get(Message.PATH_INFO) != null) {
                            inMessage.remove(Message.PATH_INFO);
                        }
                        inMessage.getInterceptorChain().pause();
                        try {
                            InternalContextUtils.getExecutor(inMessage).execute(new Runnable(){

                                @Override
                                public void run() {
                                    inMessage.getInterceptorChain().resume();
                                }
                            });
                        }
                        catch (RejectedExecutionException e) {
                            LOG.warning("Executor queue is full, use the caller thread.  Users can specify a larger executor queue to avoid this.");
                            if (!MessageUtils.getContextualBoolean(inMessage, "org.apache.cxf.oneway.rejected_execution_exception")) {
                                inMessage.getInterceptorChain().resume();
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                LOG.log(Level.WARNING, "SERVER_TRANSPORT_REBASE_FAILURE_MSG", e);
            }
        }
    }

    public static Destination createDecoupledDestination(Exchange exchange, EndpointReferenceType reference) {
        EndpointInfo ei = exchange.getEndpoint().getEndpointInfo();
        return new DecoupledDestination(ei, reference);
    }

    private static void ensurePartialResponseMAPs(Message partialResponse, String namespaceURI) {
        AddressingProperties maps = new AddressingProperties();
        maps.setTo(EndpointReferenceUtils.getAnonymousEndpointReference());
        maps.setReplyTo(ContextUtils.WSA_OBJECT_FACTORY.createEndpointReferenceType());
        maps.getReplyTo().setAddress(ContextUtils.getAttributedURI("http://www.w3.org/2005/08/addressing/none"));
        maps.setAction(ContextUtils.getAttributedURI(""));
        maps.exposeAs(namespaceURI);
        ContextUtils.storeMAPs(maps, partialResponse, true, true, false);
    }

    public static AttributedURIType getAction(Message message) {
        String action = null;
        LOG.fine("Determining action");
        Exception fault = message.getContent(Exception.class);
        if (fault instanceof Fault && "http://www.w3.org/2005/08/addressing".equals(((Fault)fault).getFaultCode().getNamespaceURI())) {
            action = "http://www.w3.org/2005/08/addressing/fault";
        } else {
            FaultAction annotation = null;
            if (fault != null) {
                annotation = fault.getClass().getAnnotation(FaultAction.class);
            }
            action = annotation != null && annotation.value() != null ? annotation.value() : InternalContextUtils.getActionFromServiceModel(message, fault);
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("action: " + action);
        }
        return action != null ? ContextUtils.getAttributedURI(action) : null;
    }

    private static String getActionFromServiceModel(Message message, Exception fault) {
        String action;
        block5: {
            BindingOperationInfo bindingOpInfo;
            block6: {
                MessageInfo msgInfo;
                String cachedAction;
                action = null;
                bindingOpInfo = message.getExchange().getBindingOperationInfo();
                if (bindingOpInfo == null) break block5;
                if (bindingOpInfo.isUnwrappedCapable()) {
                    bindingOpInfo = bindingOpInfo.getUnwrappedOperation();
                }
                if (fault != null) break block6;
                action = (String)message.get(ContextUtils.ACTION);
                if (StringUtils.isEmpty(action)) {
                    action = (String)message.get("SOAPAction");
                }
                if (action != null && !"".equals(action) || (action = (cachedAction = (String)(msgInfo = ContextUtils.isRequestor(message) ? bindingOpInfo.getOperationInfo().getInput() : bindingOpInfo.getOperationInfo().getOutput()).getProperty(ContextUtils.ACTION)) == null ? InternalContextUtils.getActionFromMessageAttributes(msgInfo) : cachedAction) != null || !ContextUtils.isRequestor(message)) break block5;
                SoapOperationInfo soi = InternalContextUtils.getSoapOperationInfo(bindingOpInfo);
                action = soi == null ? null : soi.getAction();
                action = StringUtils.isEmpty(action) ? null : action;
                break block5;
            }
            Throwable t2 = fault.getCause();
            for (BindingFaultInfo bfi : bindingOpInfo.getFaults()) {
                FaultInfo fi = bfi.getFaultInfo();
                if (fi.size() == 0 || t2 == null || !InternalContextUtils.matchFault(t2, fi) || fi.getExtensionAttributes() == null) continue;
                String attr = (String)fi.getExtensionAttributes().get(Names.WSAW_ACTION_QNAME);
                if (attr == null) {
                    attr = (String)fi.getExtensionAttributes().get(new QName("http://www.w3.org/2005/02/addressing/wsdl", "Action"));
                }
                if (attr == null) continue;
                action = attr;
                break;
            }
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("action determined from service model: " + action);
        }
        return action;
    }

    private static boolean matchFault(Throwable t2, FaultInfo fi) {
        MessagePartInfo fmpi = fi.getFirstMessagePart();
        Class<?> fiTypeClass = fmpi.getTypeClass();
        if (fiTypeClass != null && t2.getClass().isAssignableFrom(fiTypeClass)) {
            return true;
        }
        QName fiName = fmpi.getConcreteName();
        WebFault wf = t2.getClass().getAnnotation(WebFault.class);
        return wf != null && fiName != null && wf.targetNamespace() != null && wf.targetNamespace().equals(fiName.getNamespaceURI()) && wf.name() != null && wf.name().equals(fiName.getLocalPart());
    }

    public static SoapOperationInfo getSoapOperationInfo(BindingOperationInfo bindingOpInfo) {
        SoapOperationInfo soi = bindingOpInfo.getExtensor(SoapOperationInfo.class);
        if (soi == null && bindingOpInfo.isUnwrapped()) {
            soi = bindingOpInfo.getWrappedOperation().getExtensor(SoapOperationInfo.class);
        }
        return soi;
    }

    private static String getActionFromMessageAttributes(MessageInfo msgInfo) {
        String attr;
        String action = null;
        if (msgInfo != null && msgInfo.getExtensionAttributes() != null && !StringUtils.isEmpty(attr = InternalContextUtils.getAction(msgInfo))) {
            action = attr;
            msgInfo.setProperty(ContextUtils.ACTION, action);
        }
        return action;
    }

    public static String getAction(Extensible ext) {
        Object o = ext.getExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME);
        if (o == null) {
            o = ext.getExtensionAttributes().get(new QName("http://www.w3.org/2007/05/addressing/metadata", "Action"));
        }
        if (o == null) {
            o = ext.getExtensionAttributes().get(new QName("http://www.w3.org/2005/08/addressing", "Action"));
        }
        if (o == null) {
            o = ext.getExtensionAttributes().get(new QName("http://www.w3.org/2005/02/addressing/wsdl", "Action"));
        }
        if (o instanceof QName) {
            return ((QName)o).getLocalPart();
        }
        return o == null ? null : o.toString();
    }

    private static Executor getExecutor(Message message) {
        Endpoint endpoint = message.getExchange().getEndpoint();
        Executor executor2 = endpoint.getService().getExecutor();
        if (executor2 == null || SynchronousExecutor.isA(executor2)) {
            WorkQueueManager workQueueManager;
            AutomaticWorkQueue autoWorkQueue;
            Bus bus = message.getExchange().getBus();
            executor2 = bus != null ? ((autoWorkQueue = (workQueueManager = bus.getExtension(WorkQueueManager.class)).getNamedWorkQueue("ws-addressing")) != null ? autoWorkQueue : workQueueManager.getAutomaticWorkQueue()) : OneShotAsyncExecutor.getInstance();
        }
        message.getExchange().put(Executor.class, executor2);
        return executor2;
    }

    private static final class DecoupledDestination
    implements Destination {
        private final EndpointInfo ei;
        private final EndpointReferenceType reference;

        private DecoupledDestination(EndpointInfo ei, EndpointReferenceType reference) {
            this.ei = ei;
            this.reference = reference;
        }

        @Override
        public EndpointReferenceType getAddress() {
            return this.reference;
        }

        @Override
        public Conduit getBackChannel(Message inMessage) throws IOException {
            if (ContextUtils.isNoneAddress(this.reference)) {
                return null;
            }
            Bus bus = inMessage.getExchange().getBus();
            inMessage.getExchange().setOneWay(true);
            ConduitInitiator conduitInitiator = bus.getExtension(ConduitInitiatorManager.class).getConduitInitiatorForUri(this.reference.getAddress().getValue());
            if (conduitInitiator != null) {
                Conduit c = conduitInitiator.getConduit(this.ei, this.reference, bus);
                c.setMessageObserver(new MessageObserver(){

                    @Override
                    public void onMessage(Message m4) {
                        InputStream is = m4.getContent(InputStream.class);
                        if (is != null) {
                            try {
                                is.close();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                    }
                });
                return c;
            }
            return null;
        }

        @Override
        public MessageObserver getMessageObserver() {
            return null;
        }

        @Override
        public void shutdown() {
        }

        @Override
        public void setMessageObserver(MessageObserver observer) {
        }
    }
}

