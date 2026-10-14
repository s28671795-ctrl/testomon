/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.util.Map;
import org.apache.cxf.binding.soap.interceptor.EndpointSelectionInterceptor;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.UrlUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.WSDLGetOutInterceptor;
import org.apache.cxf.frontend.WSDLGetUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.MessageSenderInterceptor;
import org.apache.cxf.interceptor.OutgoingChainInterceptor;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;
import org.w3c.dom.Document;

public class WSDLGetInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final WSDLGetInterceptor INSTANCE = new WSDLGetInterceptor();
    public static final String DOCUMENT_HOLDER = WSDLGetInterceptor.class.getName() + ".documentHolder";
    private static final String TRANSFORM_SKIP = "transform.skip";
    private Interceptor<Message> wsdlGetOutInterceptor = WSDLGetOutInterceptor.INSTANCE;

    public WSDLGetInterceptor() {
        super("read");
        this.getAfter().add(EndpointSelectionInterceptor.class.getName());
    }

    public WSDLGetInterceptor(Interceptor<Message> outInterceptor) {
        this();
        this.wsdlGetOutInterceptor = outInterceptor;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Map<String, String> map;
        String method2 = (String)message.get("org.apache.cxf.request.method");
        String query = (String)message.get(Message.QUERY_STRING);
        if (!"GET".equals(method2) || StringUtils.isEmpty(query)) {
            return;
        }
        String baseUri = (String)message.get("org.apache.cxf.request.url");
        String ctx = (String)message.get(Message.PATH_INFO);
        WSDLGetUtils utils = (WSDLGetUtils)message.getContextualProperty(WSDLGetUtils.class.getName());
        if (utils == null) {
            utils = new WSDLGetUtils();
            message.put(WSDLGetUtils.class, utils);
        }
        if (this.isRecognizedQuery(map = UrlUtils.parseQueryString(query))) {
            Document doc = this.getDocument(utils, message, baseUri, map, ctx);
            Endpoint e = message.getExchange().getEndpoint();
            Message mout = new MessageImpl();
            mout.setExchange(message.getExchange());
            mout = e.getBinding().createMessage(mout);
            mout.setInterceptorChain(OutgoingChainInterceptor.getOutInterceptorChain(message.getExchange()));
            message.getExchange().setOutMessage(mout);
            mout.put(DOCUMENT_HOLDER, doc);
            mout.put("Content-Type", "text/xml");
            this.cleanUpOutInterceptors(mout);
            mout.getInterceptorChain().add(this.wsdlGetOutInterceptor);
            message.getExchange().put(TRANSFORM_SKIP, Boolean.TRUE);
            message.getInterceptorChain().doInterceptStartingAt(message, OutgoingChainInterceptor.class.getName());
        }
    }

    protected void cleanUpOutInterceptors(Message outMessage) {
        for (Interceptor inInterceptor : outMessage.getInterceptorChain()) {
            if (inInterceptor.getClass().equals(StaxOutInterceptor.class) || inInterceptor.getClass().equals(GZIPOutInterceptor.class) || inInterceptor.getClass().equals(MessageSenderInterceptor.class)) continue;
            outMessage.getInterceptorChain().remove(inInterceptor);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Document getDocument(WSDLGetUtils utils, Message message, String base, Map<String, String> params, String ctxUri) {
        Endpoint endpoint = message.getExchange().getEndpoint();
        synchronized (endpoint) {
            return utils.getDocument(message, base, params, ctxUri, message.getExchange().getEndpoint().getEndpointInfo());
        }
    }

    private boolean isRecognizedQuery(Map<String, String> map) {
        return map.containsKey("wsdl") || map.containsKey("xsd");
    }
}

