/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.StringMapImpl;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Destination;

public class MessageImpl
extends StringMapImpl
implements Message {
    private static final long serialVersionUID = -3020763696429459865L;
    private Exchange exchange;
    private String id;
    private InterceptorChain interceptorChain;
    private Object[] contents = new Object[20];
    private int index;
    private Map<String, Object> contextCache;

    public MessageImpl() {
    }

    public MessageImpl(Message m4) {
        super(m4);
        if (!(m4 instanceof MessageImpl)) {
            throw new RuntimeException("Not a MessageImpl! " + m4.getClass());
        }
        MessageImpl impl = (MessageImpl)m4;
        this.exchange = impl.getExchange();
        this.id = impl.id;
        this.interceptorChain = impl.interceptorChain;
        this.contents = impl.contents;
        this.index = impl.index;
        this.contextCache = impl.contextCache;
    }

    @Override
    public Collection<Attachment> getAttachments() {
        return CastUtils.cast((Collection)this.get(ATTACHMENTS));
    }

    @Override
    public void setAttachments(Collection<Attachment> attachments) {
        this.put(ATTACHMENTS, (Object)attachments);
    }

    public String getAttachmentMimeType() {
        return null;
    }

    @Override
    public Destination getDestination() {
        return this.get(Destination.class);
    }

    @Override
    public Exchange getExchange() {
        return this.exchange;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public InterceptorChain getInterceptorChain() {
        return this.interceptorChain;
    }

    @Override
    public <T> T getContent(Class<T> format) {
        for (int x = 0; x < this.index; x += 2) {
            if (this.contents[x] != format) continue;
            return (T)this.contents[x + 1];
        }
        return null;
    }

    @Override
    public <T> void setContent(Class<T> format, Object content) {
        for (int x = 0; x < this.index; x += 2) {
            if (this.contents[x] != format) continue;
            this.contents[x + 1] = content;
            return;
        }
        if (this.index >= this.contents.length) {
            Object[] tmp = new Object[this.contents.length + 10];
            System.arraycopy(this.contents, 0, tmp, 0, this.contents.length);
            this.contents = tmp;
        }
        this.contents[this.index] = format;
        this.contents[this.index + 1] = content;
        this.index += 2;
    }

    @Override
    public <T> void removeContent(Class<T> format) {
        for (int x = 0; x < this.index; x += 2) {
            if (this.contents[x] != format) continue;
            this.index -= 2;
            if (x != this.index) {
                this.contents[x] = this.contents[this.index];
                this.contents[x + 1] = this.contents[this.index + 1];
            }
            this.contents[this.index] = null;
            this.contents[this.index + 1] = null;
            return;
        }
    }

    @Override
    public Set<Class<?>> getContentFormats() {
        HashSet c = new HashSet();
        for (int x = 0; x < this.index; x += 2) {
            c.add((Class)this.contents[x]);
        }
        return c;
    }

    public void setDestination(Destination d) {
        this.put(Destination.class, d);
    }

    @Override
    public void setExchange(Exchange e) {
        this.exchange = e;
    }

    @Override
    public void setId(String i) {
        this.id = i;
    }

    @Override
    public void setInterceptorChain(InterceptorChain ic) {
        this.interceptorChain = ic;
    }

    @Override
    public Object put(String key, Object value) {
        if (this.contextCache != null) {
            this.contextCache.put(key, value);
        }
        return super.put(key, value);
    }

    @Override
    public Object getContextualProperty(String key) {
        if (this.contextCache == null) {
            this.calcContextCache();
        }
        return this.contextCache.get(key);
    }

    @Override
    public Set<String> getContextualPropertyKeys() {
        return this.contextCache.keySet();
    }

    private void calcContextCache() {
        HashMap<String, Object> o = new HashMap<String, Object>();
        Exchange ex = this.getExchange();
        if (ex != null) {
            Endpoint ep;
            Service sv;
            Bus b = ex.getBus();
            if (b != null && b.getProperties() != null) {
                o.putAll(b.getProperties());
            }
            if ((sv = ex.getService()) != null) {
                o.putAll(sv);
            }
            if ((ep = ex.getEndpoint()) != null) {
                EndpointInfo ei = ep.getEndpointInfo();
                if (ei != null) {
                    Map<String, Object> p = ep.getEndpointInfo().getBinding().getProperties();
                    if (p != null) {
                        o.putAll(p);
                    }
                    if ((p = ep.getEndpointInfo().getProperties()) != null) {
                        o.putAll(p);
                    }
                }
                o.putAll(ep);
            }
            o.putAll(ex);
        }
        o.putAll(this);
        this.contextCache = o;
    }

    public static void copyContent(Message m1, Message m22) {
        for (Class<?> c : m1.getContentFormats()) {
            m22.setContent(c, m1.getContent(c));
        }
    }

    @Override
    public void resetContextCache() {
        if (this.contextCache != null) {
            this.contextCache = null;
        }
    }

    void setContextualProperty(String key, Object v) {
        if (this.contextCache != null && !this.containsKey(key)) {
            this.contextCache.put(key, v);
        }
    }
}

