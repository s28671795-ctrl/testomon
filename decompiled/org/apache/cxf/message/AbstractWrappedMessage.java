/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.transport.Destination;

public abstract class AbstractWrappedMessage
implements Message {
    private Message message;

    protected AbstractWrappedMessage(Message msg) {
        this.message = msg;
    }

    @Override
    public void clear() {
        this.message.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.message.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.message.containsValue(value);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.message.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return this.message.equals(o);
    }

    @Override
    public Object get(Object key) {
        return this.message.get(key);
    }

    @Override
    public Collection<Attachment> getAttachments() {
        return this.message.getAttachments();
    }

    @Override
    public void setAttachments(Collection<Attachment> attachments) {
        this.message.setAttachments(attachments);
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public Destination getDestination() {
        return this.message.getDestination();
    }

    @Override
    public Exchange getExchange() {
        return this.message.getExchange();
    }

    @Override
    public final void setExchange(Exchange exchange) {
        this.message.setExchange(exchange);
    }

    @Override
    public String getId() {
        return this.message.getId();
    }

    @Override
    public void setId(String id) {
        this.message.setId(id);
    }

    @Override
    public InterceptorChain getInterceptorChain() {
        return this.message.getInterceptorChain();
    }

    @Override
    public void setInterceptorChain(InterceptorChain chain) {
        this.message.setInterceptorChain(chain);
    }

    @Override
    public <T> T getContent(Class<T> format) {
        return this.message.getContent(format);
    }

    @Override
    public <T> void removeContent(Class<T> format) {
        this.message.removeContent(format);
    }

    @Override
    public Set<Class<?>> getContentFormats() {
        return this.message.getContentFormats();
    }

    @Override
    public int hashCode() {
        return this.message.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.message.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.message.keySet();
    }

    @Override
    public Object put(String key, Object value) {
        return this.message.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> t2) {
        this.message.putAll(t2);
    }

    @Override
    public Object remove(Object key) {
        return this.message.remove(key);
    }

    @Override
    public <T> void setContent(Class<T> format, Object content) {
        this.message.setContent(format, content);
    }

    @Override
    public int size() {
        return this.message.size();
    }

    @Override
    public Collection<Object> values() {
        return this.message.values();
    }

    @Override
    public <T> T get(Class<T> key) {
        return this.message.get(key);
    }

    @Override
    public <T> void put(Class<T> key, T value) {
        this.message.put(key, value);
    }

    @Override
    public <T> T remove(Class<T> key) {
        return this.message.remove(key);
    }

    @Override
    public Object getContextualProperty(String key) {
        return this.message.getContextualProperty(key);
    }

    void setContextualProperty(String key, Object v) {
        if (this.message instanceof MessageImpl) {
            ((MessageImpl)this.message).setContextualProperty(key, v);
        } else if (this.message instanceof AbstractWrappedMessage) {
            ((AbstractWrappedMessage)this.message).setContextualProperty(key, v);
        } else {
            this.message.resetContextCache();
        }
    }

    @Override
    public Set<String> getContextualPropertyKeys() {
        return this.message.getContextualPropertyKeys();
    }

    @Override
    public void resetContextCache() {
        this.message.resetContextCache();
    }
}

