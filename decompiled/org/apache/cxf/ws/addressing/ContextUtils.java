/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.ObjectFactory;
import org.apache.cxf.ws.addressing.RelatesToType;

public final class ContextUtils {
    public static final ObjectFactory WSA_OBJECT_FACTORY = new ObjectFactory();
    public static final String ACTION = ContextUtils.class.getName() + ".ACTION";
    private static final EndpointReferenceType NONE_ENDPOINT_REFERENCE = new EndpointReferenceType();
    private static final Logger LOG = LogUtils.getL7dLogger(ContextUtils.class);
    private static final String URN_UUID = "urn:uuid:";
    private static final String MAP_FAULT_NAME_PROPERTY = "org.apache.cxf.ws.addressing.map.fault.name";
    private static final String MAP_FAULT_REASON_PROPERTY = "org.apache.cxf.ws.addressing.map.fault.reason";
    private static final String PARTIAL_RESPONSE_SENT_PROPERTY = "org.apache.cxf.ws.addressing.partial.response.sent";

    private ContextUtils() {
    }

    public static boolean isOutbound(Message message) {
        return message != null && message.getExchange() != null && (message == message.getExchange().getOutMessage() || message == message.getExchange().getOutFaultMessage());
    }

    public static boolean isFault(Message message) {
        return message != null && message.getExchange() != null && (message == message.getExchange().getInFaultMessage() || message == message.getExchange().getOutFaultMessage());
    }

    public static boolean isRequestor(Message message) {
        Boolean requestor = (Boolean)message.get("org.apache.cxf.client");
        return requestor != null && requestor != false;
    }

    public static String getMAPProperty(boolean isRequestor, boolean isProviderContext, boolean isOutbound) {
        if (isRequestor && isProviderContext) {
            return "javax.xml.ws.addressing.context";
        }
        return isOutbound ? "javax.xml.ws.addressing.context.outbound" : "javax.xml.ws.addressing.context.inbound";
    }

    public static void storeMAPs(AddressingProperties maps, Message message, boolean isOutbound) {
        ContextUtils.storeMAPs(maps, message, isOutbound, ContextUtils.isRequestor(message), false);
    }

    public static void storeMAPs(AddressingProperties maps, Message message, boolean isOutbound, boolean isRequestor) {
        ContextUtils.storeMAPs(maps, message, isOutbound, isRequestor, false);
    }

    public static void storeMAPs(AddressingProperties maps, Message message, boolean isOutbound, boolean isRequestor, boolean isProviderContext) {
        if (maps != null) {
            String mapProperty = ContextUtils.getMAPProperty(isRequestor, isProviderContext, isOutbound);
            LOG.log(Level.FINE, "associating MAPs with context property {0}", mapProperty);
            message.put(mapProperty, maps);
        }
    }

    public static AddressingProperties retrieveMAPs(Message message, boolean isProviderContext, boolean isOutbound) {
        return ContextUtils.retrieveMAPs(message, isProviderContext, isOutbound, true);
    }

    public static AddressingProperties retrieveMAPs(Message message, boolean isProviderContext, boolean isOutbound, boolean warnIfMissing) {
        boolean isRequestor = ContextUtils.isRequestor(message);
        String mapProperty = ContextUtils.getMAPProperty(isRequestor, isProviderContext, isOutbound);
        LOG.log(Level.FINE, "retrieving MAPs from context property {0}", mapProperty);
        AddressingProperties maps = (AddressingProperties)message.get(mapProperty);
        if (maps == null && isOutbound && !isRequestor && message.getExchange() != null && message.getExchange().getInMessage() != null) {
            maps = (AddressingProperties)message.getExchange().getInMessage().get(mapProperty);
        }
        if (maps != null) {
            LOG.log(Level.FINE, "current MAPs {0}", maps);
        } else if (!isProviderContext) {
            LogUtils.log(LOG, warnIfMissing ? Level.WARNING : Level.FINE, "MAPS_RETRIEVAL_FAILURE_MSG");
        }
        return maps;
    }

    public static AttributedURIType getAttributedURI(String uri) {
        AttributedURIType attributedURI = WSA_OBJECT_FACTORY.createAttributedURIType();
        attributedURI.setValue(uri);
        return attributedURI;
    }

    public static RelatesToType getRelatesTo(String uri) {
        RelatesToType relatesTo = WSA_OBJECT_FACTORY.createRelatesToType();
        relatesTo.setValue(uri);
        return relatesTo;
    }

    private static boolean startsWith(String value, String ref) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return value.startsWith(ref);
    }

    public static boolean isGenericAddress(EndpointReferenceType ref) {
        return ref == null || ref.getAddress() == null || ContextUtils.startsWith(ref.getAddress().getValue(), "http://www.w3.org/2005/08/addressing/anonymous") || ContextUtils.startsWith(ref.getAddress().getValue(), "http://www.w3.org/2005/08/addressing/none");
    }

    public static boolean isAnonymousAddress(EndpointReferenceType ref) {
        return ref == null || ref.getAddress() == null || ContextUtils.startsWith(ref.getAddress().getValue(), "http://www.w3.org/2005/08/addressing/anonymous");
    }

    public static boolean isNoneAddress(EndpointReferenceType ref) {
        return ref != null && ref.getAddress() != null && "http://www.w3.org/2005/08/addressing/none".equals(ref.getAddress().getValue());
    }

    public static boolean hasEmptyAction(AddressingProperties maps) {
        boolean empty;
        boolean bl = empty = maps.getAction() == null;
        if (maps.getAction() != null && maps.getAction().getValue().isEmpty()) {
            maps.setAction(null);
            empty = false;
        }
        return empty;
    }

    public static void propogateReceivedMAPs(AddressingProperties inMAPs, Exchange exchange) {
        if (exchange.getOutMessage() == null) {
            exchange.setOutMessage(ContextUtils.createMessage(exchange));
        }
        ContextUtils.propogateReceivedMAPs(inMAPs, exchange.getOutMessage());
        if (exchange.getOutFaultMessage() == null) {
            exchange.setOutFaultMessage(ContextUtils.createMessage(exchange));
        }
        ContextUtils.propogateReceivedMAPs(inMAPs, exchange.getOutFaultMessage());
    }

    public static void propogateReceivedMAPs(AddressingProperties inMAPs, Message responseMessage) {
        if (responseMessage != null) {
            ContextUtils.storeMAPs(inMAPs, responseMessage, false, false, false);
        }
    }

    public static void storeMAPFaultName(String faultName, Message message) {
        message.put(MAP_FAULT_NAME_PROPERTY, faultName);
    }

    public static String retrieveMAPFaultName(Message message) {
        return (String)message.get(MAP_FAULT_NAME_PROPERTY);
    }

    public static void storeMAPFaultReason(String reason, Message message) {
        message.put(MAP_FAULT_REASON_PROPERTY, reason);
    }

    public static String retrieveMAPFaultReason(Message message) {
        return (String)message.get(MAP_FAULT_REASON_PROPERTY);
    }

    public static void storePartialResponseSent(Message message) {
        message.put(PARTIAL_RESPONSE_SENT_PROPERTY, Boolean.TRUE);
    }

    public static boolean retrievePartialResponseSent(Message message) {
        Boolean ret = (Boolean)message.get(PARTIAL_RESPONSE_SENT_PROPERTY);
        return ret != null && ret != false;
    }

    public static void storeDeferUncorrelatedMessageAbort(Message message) {
        if (message.getExchange() != null) {
            message.getExchange().put("defer.uncorrelated.message.abort", Boolean.TRUE);
        }
    }

    public static boolean retrieveDeferUncorrelatedMessageAbort(Message message) {
        Boolean ret = message.getExchange() != null ? (Boolean)message.getExchange().get("defer.uncorrelated.message.abort") : null;
        return ret != null && ret != false;
    }

    public static void storeDeferredUncorrelatedMessageAbort(Message message) {
        if (message.getExchange() != null) {
            message.getExchange().put("deferred.uncorrelated.message.abort", Boolean.TRUE);
        }
    }

    public static boolean retrieveDeferredUncorrelatedMessageAbort(Message message) {
        Boolean ret = message.getExchange() != null ? (Boolean)message.getExchange().get("deferred.uncorrelated.message.abort") : null;
        return ret != null && ret != false;
    }

    public static boolean retrieveAsyncPostResponseDispatch(Message message) {
        Boolean ret = (Boolean)message.get("org.apache.cxf.async.post.response.dispatch");
        return ret != null && ret != false;
    }

    public static String generateUUID() {
        return URN_UUID + UUID.randomUUID();
    }

    public static Conduit getConduit(Conduit conduit, Message message) {
        if (conduit == null) {
            Exchange exchange = message.getExchange();
            conduit = exchange != null ? exchange.getConduit(message) : null;
        }
        return conduit;
    }

    public static EndpointReferenceType getNoneEndpointReference() {
        return NONE_ENDPOINT_REFERENCE;
    }

    public static void applyReferenceParam(EndpointReferenceType toEpr, Object el) {
        if (null == toEpr.getReferenceParameters()) {
            toEpr.setReferenceParameters(WSA_OBJECT_FACTORY.createReferenceParametersType());
        }
        toEpr.getReferenceParameters().getAny().add(el);
    }

    public static Message createMessage(Exchange exchange) {
        Endpoint ep = exchange.getEndpoint();
        Message msg = null;
        if (ep != null) {
            msg = new MessageImpl();
            msg.setExchange(exchange);
            if (ep.getBinding() != null) {
                msg = ep.getBinding().createMessage(msg);
            }
        }
        return msg;
    }

    public static Destination createDecoupledDestination(Exchange exchange, final EndpointReferenceType reference) {
        final EndpointInfo ei = exchange.getEndpoint().getEndpointInfo();
        return new Destination(){

            @Override
            public EndpointReferenceType getAddress() {
                return reference;
            }

            @Override
            public Conduit getBackChannel(Message inMessage) throws IOException {
                Bus bus = inMessage.getExchange().getBus();
                inMessage.getExchange().setOneWay(true);
                ConduitInitiator conduitInitiator = bus.getExtension(ConduitInitiatorManager.class).getConduitInitiatorForUri(reference.getAddress().getValue());
                if (conduitInitiator != null) {
                    Conduit c = conduitInitiator.getConduit(ei, reference, bus);
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
        };
    }

    static {
        NONE_ENDPOINT_REFERENCE.setAddress(new AttributedURIType());
        NONE_ENDPOINT_REFERENCE.getAddress().setValue("http://www.w3.org/2005/08/addressing/none");
    }
}

