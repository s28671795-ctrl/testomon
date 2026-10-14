/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.context;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.activation.DataHandler;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.context.WrappedAttachments;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;

public class WrappedMessageContext
implements MessageContext {
    public static final String SCOPES = WrappedMessageContext.class.getName() + ".SCOPES";
    private static Map<String, String> cxf2jaxwsMap = new HashMap<String, String>();
    private static Map<String, String> jaxws2cxfMap = new HashMap<String, String>();
    private final Map<String, Object> message;
    private final Map<String, Object> reqMessage;
    private final Exchange exchange;
    private Map<String, MessageContext.Scope> scopes;
    private MessageContext.Scope defaultScope;

    public WrappedMessageContext(Message m4) {
        this(m4, MessageContext.Scope.HANDLER);
    }

    public WrappedMessageContext(Message m4, MessageContext.Scope defScope) {
        this(m4, m4.getExchange(), defScope);
    }

    public WrappedMessageContext(Map<String, Object> m4, Exchange ex, MessageContext.Scope defScope) {
        this.message = m4;
        this.exchange = ex;
        this.defaultScope = defScope;
        this.scopes = CastUtils.cast((Map)this.message.get(SCOPES));
        this.reqMessage = this.isResponse() && this.exchange != null ? (this.isRequestor() ? this.exchange.getOutMessage() : this.exchange.getInMessage()) : null;
        if (this.scopes == null && this.reqMessage != null) {
            this.scopes = CastUtils.cast((Map)this.reqMessage.get(SCOPES));
            if (this.scopes != null) {
                m4.put(SCOPES, this.scopes);
                this.copyScoped(this.reqMessage);
            }
        }
        if (this.scopes == null) {
            this.scopes = new HashMap<String, MessageContext.Scope>();
            this.message.put(SCOPES, this.scopes);
        }
    }

    private void copyScoped(Map<String, Object> msg) {
        for (String s2 : this.scopes.keySet()) {
            this.message.put(s2, msg.get(s2));
        }
    }

    private String mapKey(String key) {
        String k2 = jaxws2cxfMap.get(key);
        if (k2 != null) {
            return k2;
        }
        return key;
    }

    private String mapKeyReverse(String key) {
        String k2 = cxf2jaxwsMap.get(key);
        if (k2 != null) {
            return k2;
        }
        if (Message.PROTOCOL_HEADERS.equals(key)) {
            return this.isResponse() ? "javax.xml.ws.http.response.headers" : "javax.xml.ws.http.request.headers";
        }
        return key;
    }

    protected final boolean isResponse() {
        return this.isOutbound() ^ this.isRequestor();
    }

    protected final boolean isRequestor() {
        return Boolean.TRUE.equals(this.message.containsKey("org.apache.cxf.client"));
    }

    protected final boolean isOutbound() {
        return this.message != null && this.exchange != null && (this.message == this.exchange.getOutMessage() || this.message == this.exchange.getOutFaultMessage());
    }

    public final Message getWrappedMessage() {
        return this.message instanceof Message ? (Message)this.message : null;
    }

    public final Map<String, Object> getWrappedMap() {
        return this.message;
    }

    @Override
    public void clear() {
        if (this.message instanceof Message) {
            for (String key : jaxws2cxfMap.keySet()) {
                this.remove(key);
            }
        } else {
            this.message.clear();
        }
    }

    @Override
    public final boolean containsKey(Object key) {
        return this.message.containsKey(this.mapKey((String)key)) || this.get(key) != null;
    }

    @Override
    public final boolean containsValue(Object value) {
        return this.message.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        String mappedkey = this.mapKey((String)key);
        Object ret = this.message.get(mappedkey);
        if ("javax.xml.ws.http.request.method".equals(key) && this.isRequestor()) {
            return null;
        }
        if (ret == null) {
            EndpointInfo endpointInfo;
            if (Message.class.getName().equals(mappedkey)) {
                return this.message;
            }
            if (this.exchange != null && (ret = this.exchange.get(mappedkey)) != null) {
                return ret;
            }
            if ("javax.xml.ws.binding.attachments.inbound".equals(key)) {
                ret = this.isRequestor() && this.isOutbound() ? null : (this.isOutbound() ? this.createAttachments(this.reqMessage, "javax.xml.ws.binding.attachments.inbound") : this.createAttachments(this.message, "javax.xml.ws.binding.attachments.inbound"));
            } else if ("javax.xml.ws.binding.attachments.outbound".equals(key)) {
                ret = this.isRequestor() && !this.isOutbound() ? this.createAttachments(this.reqMessage, "javax.xml.ws.binding.attachments.outbound") : this.createAttachments(this.isRequestor() ? this.getWrappedMessage() : this.createResponseMessage(), "javax.xml.ws.binding.attachments.outbound");
            } else if ("javax.xml.ws.handler.message.outbound".equals(key)) {
                ret = this.isOutbound();
            } else if ("javax.xml.ws.http.request.headers".equals(key)) {
                if (!this.isResponse()) {
                    ret = this.message.get(Message.PROTOCOL_HEADERS);
                } else if (this.reqMessage != null && !this.isRequestor()) {
                    ret = this.reqMessage.get(Message.PROTOCOL_HEADERS);
                }
            } else if ("javax.xml.ws.http.response.headers".equals(key)) {
                Message tmp;
                Map mp = null;
                if (this.isResponse()) {
                    mp = (Map)this.message.get(Message.PROTOCOL_HEADERS);
                } else if (this.exchange != null && (tmp = this.createResponseMessage()) != null) {
                    ret = tmp.get(Message.PROTOCOL_HEADERS);
                }
                ret = mp;
            } else if ("javax.xml.ws.security.auth.username".equals(key)) {
                AuthorizationPolicy authPolicy = (AuthorizationPolicy)this.message.get(AuthorizationPolicy.class.getName());
                if (authPolicy != null) {
                    ret = authPolicy.getUserName();
                }
            } else if ("javax.xml.ws.security.auth.password".equals(key)) {
                AuthorizationPolicy authPolicy = (AuthorizationPolicy)this.message.get(AuthorizationPolicy.class.getName());
                if (authPolicy != null) {
                    ret = authPolicy.getPassword();
                }
            } else if ("javax.xml.ws.wsdl.operation".equals(key)) {
                BindingOperationInfo boi = WrappedMessageContext.getBindingOperationInfo(this.exchange);
                if (boi != null && !Boolean.TRUE.equals(boi.getProperty("operation.is.synthetic"))) {
                    ret = boi.getName();
                }
            } else if ("javax.xml.ws.wsdl.service".equals(key)) {
                BindingOperationInfo boi = WrappedMessageContext.getBindingOperationInfo(this.exchange);
                if (boi != null) {
                    ret = boi.getBinding().getService().getName();
                }
            } else if ("javax.xml.ws.wsdl.interface".equals(key)) {
                BindingOperationInfo boi = WrappedMessageContext.getBindingOperationInfo(this.exchange);
                if (boi != null) {
                    ret = boi.getBinding().getService().getInterface().getName();
                }
            } else if ("javax.xml.ws.wsdl.port".equals(key)) {
                EndpointInfo endpointInfo2 = WrappedMessageContext.getEndpointInfo(this.exchange);
                if (endpointInfo2 != null) {
                    ret = endpointInfo2.getName();
                }
            } else if ("javax.xml.ws.wsdl.description".equals(key) && (endpointInfo = WrappedMessageContext.getEndpointInfo(this.exchange)) != null) {
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
                ret = wsdlDescription;
            }
            if (ret == null && this.reqMessage != null) {
                ret = this.reqMessage.get(mappedkey);
            }
        }
        return ret;
    }

    private static BindingOperationInfo getBindingOperationInfo(Exchange exchange) {
        if (exchange != null && exchange.getBindingOperationInfo() != null) {
            return exchange.getBindingOperationInfo();
        }
        return null;
    }

    private static EndpointInfo getEndpointInfo(Exchange exchange) {
        Endpoint endpoint;
        if (exchange != null && (endpoint = exchange.getEndpoint()) != null) {
            return endpoint.getEndpointInfo();
        }
        return null;
    }

    private Message createResponseMessage() {
        if (this.exchange == null || this.exchange.isOneWay()) {
            return null;
        }
        if (this.isResponse()) {
            return this.getWrappedMessage();
        }
        Message m4 = null;
        if (this.isRequestor()) {
            m4 = this.exchange.getInFaultMessage();
            if (m4 == null) {
                m4 = this.exchange.getInMessage();
            }
            if (m4 == null) {
                Endpoint ep = this.exchange.getEndpoint();
                m4 = new MessageImpl();
                m4.setExchange(this.exchange);
                m4 = ep.getBinding().createMessage(m4);
                this.exchange.setInMessage(m4);
            }
        } else {
            m4 = this.exchange.getOutMessage();
            if (m4 == null) {
                m4 = this.exchange.getOutFaultMessage();
            }
            if (m4 == null) {
                Endpoint ep = this.exchange.getEndpoint();
                m4 = new MessageImpl();
                m4.setExchange(this.exchange);
                m4 = ep.getBinding().createMessage(m4);
                this.exchange.setOutMessage(m4);
            }
        }
        return m4;
    }

    private Object createAttachments(Map<String, Object> mc, String propertyName) {
        Map<String, DataHandler> dataHandlers;
        if (mc == null) {
            return null;
        }
        Collection<Attachment> attachments = CastUtils.cast((Collection)mc.get(Message.ATTACHMENTS));
        if (attachments instanceof WrappedAttachments) {
            dataHandlers = ((WrappedAttachments)attachments).getAttachments();
        } else {
            if (attachments == null) {
                attachments = new ArrayList<Attachment>();
                mc.put(Message.ATTACHMENTS, attachments);
            }
            dataHandlers = AttachmentUtil.getDHMap(attachments);
            mc.put(propertyName, dataHandlers);
            this.scopes.put(propertyName, MessageContext.Scope.APPLICATION);
        }
        return dataHandlers;
    }

    @Override
    public final boolean isEmpty() {
        return this.message.isEmpty();
    }

    @Override
    public final Set<String> keySet() {
        HashSet<String> set = new HashSet<String>();
        for (String s2 : this.message.keySet()) {
            set.add(s2);
            set.add(this.mapKeyReverse(s2));
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public final Set<Map.Entry<String, Object>> entrySet() {
        HashSet<Map.Entry<String, Object>> set = new HashSet<Map.Entry<String, Object>>();
        for (Map.Entry<String, Object> s2 : this.message.entrySet()) {
            set.add(s2);
            final String s22 = this.mapKeyReverse(s2.getKey());
            final Object o = s2.getValue();
            if (!s22.equals(s2.getKey())) continue;
            Map.Entry<String, Object> entry = new Map.Entry<String, Object>(){

                @Override
                public String getKey() {
                    return s22;
                }

                @Override
                public Object getValue() {
                    return o;
                }

                @Override
                public Object setValue(Object value) {
                    throw new UnsupportedOperationException();
                }
            };
            set.add(entry);
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public final Object put(String key, Object value) {
        return this.put(key, value, this.defaultScope);
    }

    public final Object put(String key, Object value, MessageContext.Scope scope) {
        String mappedKey = this.mapKey(key);
        if (!"javax.xml.ws.handler.message.outbound".equals(mappedKey)) {
            this.scopes.put(mappedKey, scope);
        }
        Object ret = null;
        if (("javax.xml.ws.http.response.headers".equals(key) || "javax.xml.ws.http.response.code".equals(key)) && !this.isResponse() && !this.isRequestor()) {
            Message tmp = this.createResponseMessage();
            if (tmp != null) {
                if ("javax.xml.ws.http.response.headers".equals(key)) {
                    return tmp.put(Message.PROTOCOL_HEADERS, value);
                }
                return tmp.put(mappedKey, value);
            }
        } else if ("javax.xml.ws.security.auth.username".equals(key)) {
            AuthorizationPolicy authPolicy = (AuthorizationPolicy)this.message.get(AuthorizationPolicy.class.getName());
            if (authPolicy == null) {
                authPolicy = new AuthorizationPolicy();
                this.message.put(AuthorizationPolicy.class.getName(), authPolicy);
            }
            ret = authPolicy.getUserName();
            authPolicy.setUserName((String)value);
        } else if ("javax.xml.ws.security.auth.password".equals(key)) {
            AuthorizationPolicy authPolicy = (AuthorizationPolicy)this.message.get(AuthorizationPolicy.class.getName());
            if (authPolicy == null) {
                authPolicy = new AuthorizationPolicy();
                this.message.put(AuthorizationPolicy.class.getName(), authPolicy);
            }
            ret = authPolicy.getPassword();
            authPolicy.setPassword((String)value);
        } else if ("javax.xml.ws.http.request.headers".equals(key)) {
            ret = this.message.put(Message.PROTOCOL_HEADERS, value);
        } else if ("javax.xml.ws.binding.attachments.outbound".equals(key)) {
            Map<String, DataHandler> attachments = CastUtils.cast((Map)value);
            ret = this.message.put(Message.ATTACHMENTS, new WrappedAttachments(attachments));
        } else if ("SOAPAction".equals(mappedKey) && !this.isRequestor() && this.exchange != null) {
            Message tmp = this.createResponseMessage();
            if (tmp != null) {
                tmp.put(mappedKey, value);
            }
        } else {
            ret = this.message.put(mappedKey, value);
        }
        return ret;
    }

    @Override
    public final void putAll(Map<? extends String, ? extends Object> t2) {
        for (Map.Entry<? extends String, ? extends Object> s2 : t2.entrySet()) {
            this.put(s2.getKey(), s2.getValue());
        }
    }

    @Override
    public final Object remove(Object key) {
        key = this.mapKey((String)key);
        this.scopes.remove(key);
        if ("javax.xml.ws.security.auth.password".equals(key) || "javax.xml.ws.security.auth.username".equals(key)) {
            this.message.remove(AuthorizationPolicy.class.getName());
        }
        return this.message.remove(key);
    }

    @Override
    public final int size() {
        return this.message.size();
    }

    @Override
    public final Collection<Object> values() {
        return this.message.values();
    }

    @Override
    public final void setScope(String key, MessageContext.Scope arg1) {
        String mappedKey = this.mapKey(key);
        if (!this.containsKey(mappedKey) && !this.scopes.containsKey(mappedKey)) {
            throw new IllegalArgumentException("non-existant property-" + key + "is specified");
        }
        this.scopes.put(mappedKey, arg1);
    }

    @Override
    public final MessageContext.Scope getScope(String key) {
        String mappedKey = this.mapKey(key);
        if (this.scopes.containsKey(mappedKey)) {
            return this.scopes.get(mappedKey);
        }
        if (this.containsKey(mappedKey)) {
            return this.defaultScope;
        }
        throw new IllegalArgumentException("non-existant property-" + key + "is specified");
    }

    public final Map<String, MessageContext.Scope> getScopes() {
        return this.scopes;
    }

    static {
        cxf2jaxwsMap.put(Message.ENDPOINT_ADDRESS, "javax.xml.ws.service.endpoint.address");
        cxf2jaxwsMap.put(Message.MAINTAIN_SESSION, "javax.xml.ws.session.maintain");
        cxf2jaxwsMap.put("org.apache.cxf.request.method", "javax.xml.ws.http.request.method");
        cxf2jaxwsMap.put(Message.RESPONSE_CODE, "javax.xml.ws.http.response.code");
        cxf2jaxwsMap.put(Message.PATH_INFO, "javax.xml.ws.http.request.pathinfo");
        cxf2jaxwsMap.put(Message.QUERY_STRING, "javax.xml.ws.http.request.querystring");
        cxf2jaxwsMap.put("HTTP.REQUEST", "javax.xml.ws.servlet.request");
        cxf2jaxwsMap.put("HTTP.RESPONSE", "javax.xml.ws.servlet.response");
        cxf2jaxwsMap.put("HTTP.CONTEXT", "javax.xml.ws.servlet.context");
        jaxws2cxfMap.put("javax.xml.ws.service.endpoint.address", Message.ENDPOINT_ADDRESS);
        jaxws2cxfMap.put("javax.xml.ws.session.maintain", Message.MAINTAIN_SESSION);
        jaxws2cxfMap.put("javax.xml.ws.http.request.method", "org.apache.cxf.request.method");
        jaxws2cxfMap.put("javax.xml.ws.http.response.code", Message.RESPONSE_CODE);
        jaxws2cxfMap.put("javax.xml.ws.http.request.pathinfo", Message.PATH_INFO);
        jaxws2cxfMap.put("javax.xml.ws.http.request.querystring", Message.QUERY_STRING);
        jaxws2cxfMap.put("javax.xml.ws.servlet.request", "HTTP.REQUEST");
        jaxws2cxfMap.put("javax.xml.ws.servlet.response", "HTTP.RESPONSE");
        jaxws2cxfMap.put("javax.xml.ws.servlet.context", "HTTP.CONTEXT");
        jaxws2cxfMap.put("javax.xml.ws.soap.http.soapaction.uri", "SOAPAction");
    }
}

