/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.Invoker;

public class ServiceInvokerInterceptor
extends AbstractPhaseInterceptor<Message> {
    public ServiceInvokerInterceptor() {
        super("invoke");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleMessage(final Message message) {
        final Exchange exchange = message.getExchange();
        Endpoint endpoint = exchange.getEndpoint();
        Service service2 = endpoint.getService();
        final Invoker invoker = service2.getInvoker();
        Runnable invocation = new Runnable(){

            @Override
            public void run() {
                Exchange runableEx = message.getExchange();
                Object result = invoker.invoke(runableEx, ServiceInvokerInterceptor.this.getInvokee(message));
                if (!exchange.isOneWay()) {
                    Endpoint ep = exchange.getEndpoint();
                    Message outMessage = runableEx.getOutMessage();
                    if (outMessage == null) {
                        outMessage = new MessageImpl();
                        outMessage.setExchange(exchange);
                        outMessage = ep.getBinding().createMessage(outMessage);
                        exchange.setOutMessage(outMessage);
                    }
                    ServiceInvokerInterceptor.this.copyJaxwsProperties(message, outMessage);
                    if (result != null) {
                        MessageContentsList resList = null;
                        if (result instanceof MessageContentsList) {
                            resList = (MessageContentsList)result;
                        } else if (result instanceof List) {
                            resList = new MessageContentsList((List)result);
                        } else if (result.getClass().isArray()) {
                            resList = new MessageContentsList((Object[])result);
                        } else {
                            outMessage.setContent(Object.class, result);
                        }
                        if (resList != null) {
                            outMessage.setContent(List.class, resList);
                        }
                    }
                }
            }
        };
        Executor executor2 = this.getExecutor(endpoint);
        Executor executor22 = exchange.get(Executor.class);
        if (executor22 == executor2 || executor2 == null || !(message.getInterceptorChain() instanceof PhaseInterceptorChain)) {
            invocation.run();
        } else {
            exchange.put(Executor.class, executor2);
            final PhaseInterceptorChain chain = (PhaseInterceptorChain)message.getInterceptorChain();
            final AtomicBoolean contextSwitched = new AtomicBoolean();
            FutureTask<Object> o = new FutureTask<Object>(invocation, null){

                @Override
                protected void done() {
                    super.done();
                    if (contextSwitched.get()) {
                        PhaseInterceptorChain.setCurrentMessage(chain, null);
                        message.remove("thread.context.switched");
                    }
                    chain.releaseChain();
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void run() {
                    if (PhaseInterceptorChain.setCurrentMessage(chain, message)) {
                        contextSwitched.set(true);
                        message.put("thread.context.switched", true);
                    }
                    PhaseInterceptorChain phaseInterceptorChain = chain;
                    synchronized (phaseInterceptorChain) {
                        super.run();
                    }
                }
            };
            PhaseInterceptorChain phaseInterceptorChain = chain;
            synchronized (phaseInterceptorChain) {
                executor2.execute(o);
                chain.releaseAndAcquireChain();
            }
            try {
                o.get();
            }
            catch (InterruptedException e) {
                throw new Fault(e);
            }
            catch (ExecutionException e) {
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException)e.getCause();
                }
                throw new Fault(e.getCause());
            }
        }
    }

    private Object getInvokee(Message message) {
        Object invokee = message.getContent(List.class);
        if (invokee == null) {
            invokee = message.getContent(Object.class);
        }
        return invokee;
    }

    private Executor getExecutor(Endpoint endpoint) {
        return endpoint.getService().getExecutor();
    }

    private void copyJaxwsProperties(Message inMsg, Message outMsg) {
        outMsg.put("javax.xml.ws.wsdl.operation", inMsg.get("javax.xml.ws.wsdl.operation"));
        outMsg.put("javax.xml.ws.wsdl.service", inMsg.get("javax.xml.ws.wsdl.service"));
        outMsg.put("javax.xml.ws.wsdl.interface", inMsg.get("javax.xml.ws.wsdl.interface"));
        outMsg.put("javax.xml.ws.wsdl.port", inMsg.get("javax.xml.ws.wsdl.port"));
        outMsg.put("javax.xml.ws.wsdl.description", inMsg.get("javax.xml.ws.wsdl.description"));
    }
}

