/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.soap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.ContextUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.ws.addressing.MAPAggregator;
import org.apache.cxf.ws.addressing.Names;
import org.apache.cxf.ws.addressing.ReferenceParametersType;
import org.apache.cxf.ws.addressing.RelatesToType;
import org.apache.cxf.ws.addressing.soap.DecoupledFaultHandler;
import org.apache.cxf.ws.addressing.soap.VersionTransformer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MAPCodec
extends AbstractSoapInterceptor {
    public static final MAPCodec INSTANCE = new MAPCodec();
    private static final Logger LOG = LogUtils.getL7dLogger(MAPCodec.class);
    private static final String IS_REFERENCE_PARAM_ATTR_NAME = "IsReferenceParameter";
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private static final String DECOUPLED_FAULT_SUPPORT = "org.apache.cxf.ws.addressing.decoupled_fault_support";
    protected final Map<String, Exchange> uncorrelatedExchanges = new ConcurrentHashMap<String, Exchange>();
    private VersionTransformer transformer = new VersionTransformer(this);
    private HeaderFactory headerFactory;

    public MAPCodec() {
        super("pre-protocol");
    }

    public static synchronized MAPCodec getInstance(Bus bus) {
        MAPCodec mc = bus.getExtension(MAPCodec.class);
        if (mc == null) {
            return MAPCodec.createMAPCodec(bus);
        }
        return mc;
    }

    private static synchronized MAPCodec createMAPCodec(Bus bus) {
        MAPCodec mc = bus.getExtension(MAPCodec.class);
        if (mc == null) {
            bus.setExtension(new MAPCodec(), MAPCodec.class);
            mc = bus.getExtension(MAPCodec.class);
        }
        return mc;
    }

    public Map<String, Exchange> getUncorrelatedExchanges() {
        return this.uncorrelatedExchanges;
    }

    @Override
    public Set<QName> getUnderstoodHeaders() {
        return VersionTransformer.HEADERS;
    }

    @Override
    public void handleMessage(SoapMessage message) {
        this.mediate(message);
    }

    @Override
    public void handleFault(SoapMessage message) {
        if (!message.getExchange().isOneWay()) {
            AddressingProperties maps = ContextUtils.retrieveMAPs(message, false, true, false);
            if (ContextUtils.isRequestor(message) && maps != null) {
                this.uncorrelatedExchanges.remove(maps.getMessageID().getValue());
            } else if (!ContextUtils.isRequestor(message) && maps == null && !message.containsKey(MAPAggregator.class.getName())) {
                for (Interceptor i : message.getInterceptorChain()) {
                    if (!(i instanceof MAPAggregator)) continue;
                    try {
                        MAPAggregator agg = (MAPAggregator)i;
                        agg.handleMessage(message);
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                    return;
                }
            }
        }
        if (MessageUtils.getContextualBoolean(message, DECOUPLED_FAULT_SUPPORT, false)) {
            new DecoupledFaultHandler().handleFault(message);
        }
    }

    private void mediate(SoapMessage message) {
        if (!MessageUtils.getContextualBoolean(message, MAPAggregator.ADDRESSING_DISABLED, false)) {
            if (ContextUtils.isOutbound(message)) {
                this.encode(message, ContextUtils.retrieveMAPs(message, false, true));
            } else if (null == ContextUtils.retrieveMAPs(message, false, false, false)) {
                String action;
                AddressingProperties maps = this.decode(message);
                ContextUtils.storeMAPs(maps, message, false);
                this.markPartialResponse(message, maps);
                this.restoreExchange(message, maps);
                if (maps != null && !MessageUtils.isRequestor(message) && message.getExchange().getBindingOperationInfo() == null && !MessageUtils.isOutbound(message) && maps.getAction() != null && (action = maps.getAction().getValue()) != null) {
                    boolean strict = MessageUtils.getContextualBoolean(message, "ws-addressing.strict.action.checking", false);
                    SoapActionInInterceptor.getAndSetOperation(message, action, strict);
                }
            }
        }
    }

    private void encode(SoapMessage message, AddressingProperties maps) {
        if (maps != null) {
            this.cacheExchange(message, maps);
            LOG.log(Level.FINE, "Outbound WS-Addressing headers");
            try {
                List<Header> header = message.getHeaders();
                this.discardMAPs(header, maps);
                JAXBContext jaxbContext = org.apache.cxf.ws.addressing.VersionTransformer.getExposedJAXBContext(maps.getNamespaceURI());
                QName duplicate = maps.getDuplicate();
                this.encodeAsExposed(maps, message, maps.getAction(), Names.WSA_ACTION_QNAME, AttributedURIType.class, jaxbContext);
                if (Names.WSA_ACTION_QNAME.equals(duplicate)) {
                    this.encodeAsExposed(maps, message, maps.getAction(), Names.WSA_ACTION_QNAME, AttributedURIType.class, jaxbContext);
                }
                this.encodeAsExposed(maps, message, maps.getMessageID(), Names.WSA_MESSAGEID_QNAME, AttributedURIType.class, jaxbContext);
                if (Names.WSA_MESSAGEID_QNAME.equals(duplicate)) {
                    this.encodeAsExposed(maps, message, maps.getMessageID(), Names.WSA_MESSAGEID_QNAME, AttributedURIType.class, jaxbContext);
                }
                this.encodeAsExposed(maps, message, maps.getTo(), Names.WSA_TO_QNAME, AttributedURIType.class, jaxbContext);
                if (Names.WSA_TO_QNAME.equals(duplicate)) {
                    this.encodeAsExposed(maps, message, maps.getTo(), Names.WSA_TO_QNAME, AttributedURIType.class, jaxbContext);
                }
                if (this.needsReplyTo(maps, message)) {
                    this.encodeAsExposed(maps, message, maps.getReplyTo(), Names.WSA_REPLYTO_QNAME, EndpointReferenceType.class, jaxbContext);
                    if (Names.WSA_REPLYTO_QNAME.equals(duplicate)) {
                        this.encodeAsExposed(maps, message, maps.getReplyTo(), Names.WSA_REPLYTO_QNAME, EndpointReferenceType.class, jaxbContext);
                    }
                }
                this.encodeAsExposed(maps, message, maps.getRelatesTo(), Names.WSA_RELATESTO_QNAME, RelatesToType.class, jaxbContext);
                if (Names.WSA_RELATESTO_QNAME.equals(duplicate)) {
                    this.encodeAsExposed(maps, message, maps.getRelatesTo(), Names.WSA_RELATESTO_QNAME, RelatesToType.class, jaxbContext);
                }
                this.encodeAsExposed(maps, message, maps.getFrom(), Names.WSA_FROM_QNAME, EndpointReferenceType.class, jaxbContext);
                if (Names.WSA_FROM_QNAME.equals(duplicate)) {
                    this.encodeAsExposed(maps, message, maps.getFrom(), Names.WSA_FROM_QNAME, EndpointReferenceType.class, jaxbContext);
                }
                if (this.needsFaultTo(maps)) {
                    this.encodeAsExposed(maps, message, maps.getFaultTo(), Names.WSA_FAULTTO_QNAME, EndpointReferenceType.class, jaxbContext);
                    if (Names.WSA_FAULTTO_QNAME.equals(duplicate)) {
                        this.encodeAsExposed(maps, message, maps.getFaultTo(), Names.WSA_FAULTTO_QNAME, EndpointReferenceType.class, jaxbContext);
                    }
                }
                this.encodeReferenceParameters(maps, message, jaxbContext);
                maps.setDuplicate(null);
                this.propogateAction(maps.getAction(), message);
                this.applyMAPValidation(message);
            }
            catch (JAXBException je) {
                LOG.log(Level.WARNING, "SOAP_HEADER_ENCODE_FAILURE_MSG", je);
            }
        }
    }

    private boolean needsReplyTo(AddressingProperties maps, SoapMessage m4) {
        if (!MessageUtils.getContextualBoolean(m4, "ws-addressing.write.optional.replyto", true)) {
            if (ContextUtils.isNoneAddress(maps.getReplyTo()) && m4.getExchange().isOneWay()) {
                return false;
            }
            if (ContextUtils.isAnonymousAddress(maps.getReplyTo())) {
                return false;
            }
        }
        return maps.getReplyTo() != null && maps.getReplyTo().getAddress() != null && maps.getReplyTo().getAddress().getValue() != null && (!"http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(maps.getNamespaceURI()) || !maps.getReplyTo().getAddress().getValue().equals(ContextUtils.getNoneEndpointReference().getAddress().getValue()));
    }

    private boolean needsFaultTo(AddressingProperties maps) {
        return maps.getFaultTo() != null && maps.getFaultTo().getAddress() != null && maps.getFaultTo().getAddress().getValue() != null && !maps.getFaultTo().getAddress().getValue().equals(maps.getReplyTo().getAddress().getValue());
    }

    private void encodeReferenceParameters(AddressingProperties maps, SoapMessage msg, JAXBContext ctx) throws JAXBException {
        ReferenceParametersType params;
        Element header = null;
        EndpointReferenceType toEpr = maps.getToEndpointReference();
        if (null != toEpr && null != (params = toEpr.getReferenceParameters())) {
            for (Object o : params.getAny()) {
                if (o instanceof Element || o instanceof JAXBElement) {
                    if (header == null) {
                        header = this.getHeaderFactory().getHeader(msg.getVersion());
                    }
                    JAXBElement jaxbEl = null;
                    if (o instanceof Element) {
                        Element e = (Element)o;
                        Node importedNode = header.getOwnerDocument().importNode(e, true);
                        header.appendChild(importedNode);
                    } else {
                        jaxbEl = (JAXBElement)o;
                        ctx.createMarshaller().marshal((Object)jaxbEl, header);
                    }
                    Element lastAdded = (Element)header.getLastChild();
                    header.removeChild(lastAdded);
                    this.addIsReferenceParameterMarkerAttribute(lastAdded, maps.getNamespaceURI());
                    Header holder = new Header(new QName(lastAdded.getNamespaceURI(), lastAdded.getLocalName()), lastAdded);
                    msg.getHeaders().add(holder);
                    continue;
                }
                LOG.log(Level.WARNING, "IGNORE_NON_ELEMENT_REF_PARAM_MSG", o);
            }
        }
    }

    private void addIsReferenceParameterMarkerAttribute(Element lastAdded, String namespaceURI) {
        String pfx = lastAdded.lookupPrefix(namespaceURI);
        if (StringUtils.isEmpty(pfx)) {
            if (lastAdded.lookupNamespaceURI("wsa") == null) {
                pfx = "wsa";
                Attr attr = lastAdded.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsa");
                attr.setValue(namespaceURI);
                lastAdded.setAttributeNodeNS(attr);
            } else if (lastAdded.lookupNamespaceURI("wsa").equals(namespaceURI)) {
                pfx = "wsa";
            } else {
                int cnt = 1;
                while (lastAdded.lookupNamespaceURI("wsa" + cnt) != null) {
                    ++cnt;
                }
                pfx = "wsa" + cnt;
                Attr attr = lastAdded.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsa" + cnt);
                attr.setValue(namespaceURI);
                lastAdded.setAttributeNodeNS(attr);
            }
        }
        Attr isRefParamAttr = lastAdded.getOwnerDocument().createAttributeNS(namespaceURI, pfx + ":" + IS_REFERENCE_PARAM_ATTR_NAME);
        isRefParamAttr.setTextContent("1");
        lastAdded.setAttributeNodeNS(isRefParamAttr);
    }

    private <T> void encodeAsExposed(AddressingProperties maps, SoapMessage message, T value, QName name, Class<T> clz, JAXBContext context) throws JAXBException {
        if (value != null) {
            LOG.log(Level.FINE, "{0} : {1}", new Object[]{name.getLocalPart(), this.getLogText(value)});
            boolean mu = maps.isRequired() || maps.getMustUnderstand().contains(name);
            this.transformer.encodeAsExposed(message, maps.getNamespaceURI(), value, name.getLocalPart(), clz, context, mu);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AddressingProperties unmarshalMAPs(SoapMessage message) {
        AddressingProperties maps = null;
        Unmarshaller unmarshaller = null;
        try {
            List<Header> header = message.getHeaders();
            if (header != null) {
                LOG.log(Level.FINE, "Inbound WS-Addressing headers");
                HashSet<Element> referenceParameterHeaders = null;
                QName invalidCardinalityQName = null;
                for (Header hdr2 : header) {
                    if (!(hdr2.getObject() instanceof Element)) continue;
                    Element headerElement = (Element)hdr2.getObject();
                    String headerURI = headerElement.getNamespaceURI();
                    if (org.apache.cxf.ws.addressing.VersionTransformer.isSupported(headerURI)) {
                        String localName;
                        if (unmarshaller == null) {
                            JAXBContext jaxbContext = org.apache.cxf.ws.addressing.VersionTransformer.getExposedJAXBContext(headerURI);
                            unmarshaller = jaxbContext.createUnmarshaller();
                            unmarshaller.setEventHandler(null);
                        }
                        if (maps == null) {
                            maps = new AddressingProperties();
                            maps.exposeAs(headerURI);
                        }
                        if ("MessageID".equals(localName = headerElement.getLocalName())) {
                            invalidCardinalityQName = maps.getMessageID() != null ? Names.WSA_MESSAGEID_QNAME : null;
                            maps.setMessageID(this.decodeAsNative(headerURI, AttributedURIType.class, headerElement, unmarshaller));
                            continue;
                        }
                        if ("To".equals(localName)) {
                            invalidCardinalityQName = maps.getTo() != null ? Names.WSA_TO_QNAME : null;
                            AttributedURIType addr = this.decodeAsNative(headerURI, AttributedURIType.class, headerElement, unmarshaller);
                            maps.setTo(EndpointReferenceUtils.getEndpointReference(addr));
                            continue;
                        }
                        if ("From".equals(localName)) {
                            invalidCardinalityQName = maps.getFrom() != null ? Names.WSA_FROM_QNAME : null;
                            maps.setFrom(this.decodeAsNative(headerURI, EndpointReferenceType.class, headerElement, unmarshaller));
                            continue;
                        }
                        if ("ReplyTo".equals(localName)) {
                            invalidCardinalityQName = maps.getReplyTo() != null ? Names.WSA_REPLYTO_QNAME : null;
                            maps.setReplyTo(this.decodeAsNative(headerURI, EndpointReferenceType.class, headerElement, unmarshaller));
                            continue;
                        }
                        if ("FaultTo".equals(localName)) {
                            invalidCardinalityQName = maps.getFaultTo() != null ? Names.WSA_FAULTTO_QNAME : null;
                            maps.setFaultTo(this.decodeAsNative(headerURI, EndpointReferenceType.class, headerElement, unmarshaller));
                            continue;
                        }
                        if ("RelatesTo".equals(localName)) {
                            maps.setRelatesTo(this.decodeAsNative(headerURI, RelatesToType.class, headerElement, unmarshaller));
                            continue;
                        }
                        if (!"Action".equals(localName)) continue;
                        invalidCardinalityQName = maps.getAction() != null ? Names.WSA_ACTION_QNAME : null;
                        maps.setAction(this.decodeAsNative(headerURI, AttributedURIType.class, headerElement, unmarshaller));
                        continue;
                    }
                    if (null != headerElement.getAttribute(IS_REFERENCE_PARAM_ATTR_NAME)) {
                        if (null == referenceParameterHeaders) {
                            referenceParameterHeaders = new HashSet<Element>();
                        }
                        referenceParameterHeaders.add(headerElement);
                        continue;
                    }
                    if (!headerURI.contains("/addressing")) continue;
                    LOG.log(Level.WARNING, "UNSUPPORTED_VERSION_MSG", headerURI);
                }
                if (maps != null && !MessageUtils.isRequestor(message) && maps.getReplyTo() == null) {
                    AttributedURIType address = ContextUtils.getAttributedURI("http://www.w3.org/2005/08/addressing/anonymous");
                    EndpointReferenceType replyTo = ContextUtils.WSA_OBJECT_FACTORY.createEndpointReferenceType();
                    replyTo.setAddress(address);
                    maps.setReplyTo(replyTo);
                }
                if (null != referenceParameterHeaders && null != maps) {
                    this.decodeReferenceParameters(referenceParameterHeaders, maps, unmarshaller);
                }
                if (invalidCardinalityQName != null) {
                    this.storeInvalidCardinalityFault(message, invalidCardinalityQName);
                }
            }
        }
        catch (JAXBException je) {
            LOG.log(Level.WARNING, "SOAP_HEADER_DECODE_FAILURE_MSG", je);
        }
        finally {
            JAXBUtils.closeUnmarshaller(unmarshaller);
        }
        return maps;
    }

    private void storeInvalidCardinalityFault(SoapMessage message, QName wsaHeaderName) {
        LOG.log(Level.WARNING, "INVALID_CARDINALITY_MESSAGE", wsaHeaderName);
        String reason = BUNDLE.getString("INVALID_ADDRESSING_PROPERTY_MESSAGE");
        ContextUtils.storeMAPFaultName("InvalidCardinality", message);
        ContextUtils.storeMAPFaultReason(reason, message);
    }

    private void decodeReferenceParameters(Set<Element> referenceParameterHeaders, AddressingProperties maps, Unmarshaller unmarshaller) throws JAXBException {
        EndpointReferenceType toEpr = maps.getToEndpointReference();
        if (null != toEpr) {
            for (Element e : referenceParameterHeaders) {
                if (DOMUtils.getChild((Node)e, 1) == null) {
                    JAXBElement<String> el = unmarshaller.unmarshal(e, String.class);
                    ContextUtils.applyReferenceParam(toEpr, el);
                    continue;
                }
                ContextUtils.applyReferenceParam(toEpr, e);
            }
        }
    }

    public <T> T decodeAsNative(String encodedAs, Class<T> clz, Element headerElement, Unmarshaller unmarshaller) throws JAXBException {
        T value = clz.cast(this.transformer.decodeAsNative(encodedAs, clz, headerElement, unmarshaller));
        LOG.log(Level.FINE, "{0} : {1}", new Object[]{headerElement.getLocalName(), this.getLogText(value)});
        return value;
    }

    private <T> String getLogText(T value) {
        String text = "unknown";
        if (value == null) {
            text = "null";
        } else if (value instanceof AttributedURIType) {
            text = ((AttributedURIType)value).getValue();
        } else if (value instanceof EndpointReferenceType) {
            text = ((EndpointReferenceType)value).getAddress() != null ? ((EndpointReferenceType)value).getAddress().getValue() : "null";
        } else if (value instanceof RelatesToType) {
            text = ((RelatesToType)value).getValue();
        }
        return text;
    }

    private AddressingProperties decode(SoapMessage message) {
        return this.unmarshalMAPs(message);
    }

    protected <T> void encodeMAP(SoapMessage message, T value, QName qname, Class<T> clz, JAXBContext ctx, boolean mustUnderstand) throws JAXBException {
        JAXBDataBinding jaxbDataBinding = new JAXBDataBinding(ctx);
        SoapHeader h2 = new SoapHeader(qname, new JAXBElement<T>(qname, clz, value), jaxbDataBinding);
        h2.setMustUnderstand(mustUnderstand);
        message.getHeaders().add(h2);
    }

    protected <T> T decodeMAP(Class<T> clz, Element headerElement, Unmarshaller unmarshaller) throws JAXBException {
        JAXBElement<T> element = unmarshaller.unmarshal(headerElement, clz);
        return element.getValue();
    }

    private void discardMAPs(List<Header> header, AddressingProperties maps) {
        Iterator<Header> iter = header.iterator();
        while (iter.hasNext()) {
            Header hdr2 = iter.next();
            if (!"http://www.w3.org/2005/08/addressing".equals(hdr2.getName().getNamespaceURI()) && !"http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(hdr2.getName().getNamespaceURI())) continue;
            iter.remove();
        }
    }

    private void propogateAction(AttributedURIType action, SoapMessage message) {
        List soapActionHeaders;
        Map mimeHeaders;
        if (!(action == null || "".equals(action.getValue()) || (mimeHeaders = CastUtils.cast((Map)message.get("org.apache.cxf.mime.headers"))) == null || (soapActionHeaders = (List)mimeHeaders.get("SOAPAction")) == null || soapActionHeaders.isEmpty() || "".equals(soapActionHeaders.get(0)))) {
            LOG.log(Level.FINE, "encoding wsa:Action in SOAPAction header {0}", action.getValue());
            soapActionHeaders.clear();
            soapActionHeaders.add("\"" + action.getValue() + "\"");
        }
    }

    private void applyMAPValidation(SoapMessage message) {
        String faultName = ContextUtils.retrieveMAPFaultName(message);
        if (faultName != null) {
            String reason = ContextUtils.retrieveMAPFaultReason(message);
            throw this.createSOAPFaut(faultName, "http://www.w3.org/2005/08/addressing", reason);
        }
    }

    private SoapFault createSOAPFaut(String localName, String namespace, String reason) {
        return new SoapFault(reason, new QName(namespace, localName));
    }

    private void cacheExchange(SoapMessage message, AddressingProperties maps) {
        if (ContextUtils.isRequestor(message) && !message.getExchange().isOneWay()) {
            this.uncorrelatedExchanges.put(maps.getMessageID().getValue(), message.getExchange());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void restoreExchange(SoapMessage message, AddressingProperties maps) {
        if (maps != null && maps.getRelatesTo() != null && !"http://www.w3.org/2005/08/addressing/unspecified".equals(maps.getRelatesTo().getValue()) && this.isRelationshipReply(maps.getRelatesTo())) {
            Exchange correlatedExchange = this.uncorrelatedExchanges.remove(maps.getRelatesTo().getValue());
            if (correlatedExchange != null) {
                Exchange exchange = correlatedExchange;
                synchronized (exchange) {
                    message.setExchange(correlatedExchange);
                }
            } else if (ContextUtils.isRequestor(message) && !message.getExchange().isOneWay()) {
                if (ContextUtils.retrieveDeferUncorrelatedMessageAbort(message)) {
                    LOG.fine("deferring uncorrelated message abort");
                    ContextUtils.storeDeferredUncorrelatedMessageAbort(message);
                } else if (!MessageUtils.getContextualBoolean(message, "org.apache.cxf.ws.addressing.MAPAggregator.addressingDisabled", false)) {
                    AddressingProperties outp;
                    Message outmsg = message.getExchange().getOutMessage();
                    AddressingProperties addressingProperties = outp = outmsg != null ? ContextUtils.retrieveMAPs(outmsg, false, true, false) : null;
                    if (outp == null || !outp.getMessageID().getValue().equals(maps.getRelatesTo().getValue())) {
                        LOG.log(Level.WARNING, "CORRELATION_FAILURE_MSG");
                        message.getInterceptorChain().abort();
                    }
                }
            }
        } else if (this.isRequestor(message)) {
            Message m4;
            if (maps == null) {
                Exchange ex;
                Message m5 = message.getExchange().getOutMessage();
                maps = ContextUtils.retrieveMAPs(m5, false, true, false);
                if (maps != null && (ex = this.uncorrelatedExchanges.get(maps.getMessageID().getValue())) == message.getExchange()) {
                    this.uncorrelatedExchanges.remove(maps.getMessageID().getValue());
                    LOG.log(Level.WARNING, "RESPONSE_NOT_USING_WSADDRESSING");
                }
            } else if (maps.getRelatesTo() == null && maps.getAction() != null && ("http://www.w3.org/2005/08/addressing/fault".equals(maps.getAction().getValue()) || "http://www.w3.org/2005/08/addressing/soap/fault".equals(maps.getAction().getValue()) || "http://docs.oasis-open.org/wsrf/fault".equals(maps.getAction().getValue())) && (maps = ContextUtils.retrieveMAPs(m4 = message.getExchange().getOutMessage(), false, true, false)) != null) {
                this.uncorrelatedExchanges.remove(maps.getMessageID().getValue());
            }
        }
    }

    private boolean isRelationshipReply(RelatesToType relatesTo) {
        return "http://www.w3.org/2005/08/addressing/reply".equals(relatesTo.getRelationshipType());
    }

    private void markPartialResponse(SoapMessage message, AddressingProperties maps) {
        if (ContextUtils.isRequestor(message) && null != maps && (null == maps.getRelatesTo() || null != maps.getRelatesTo() && "http://www.w3.org/2005/08/addressing/unspecified".equals(maps.getRelatesTo().getValue()))) {
            message.put("org.apache.cxf.partial.response", (Object)Boolean.TRUE);
        }
    }

    protected HeaderFactory getHeaderFactory() {
        if (this.headerFactory == null) {
            this.headerFactory = new HeaderFactory(){

                @Override
                public Element getHeader(SoapVersion soapversion) {
                    Document doc = DOMUtils.getEmptyDocument();
                    return doc.createElementNS(soapversion.getHeader().getNamespaceURI(), soapversion.getHeader().getLocalPart());
                }
            };
        }
        return this.headerFactory;
    }

    protected void setHeaderFactory(HeaderFactory factory2) {
        this.headerFactory = factory2;
    }

    public static interface HeaderFactory {
        public Element getHeader(SoapVersion var1);
    }
}

