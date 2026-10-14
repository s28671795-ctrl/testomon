/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.context;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.activation.DataHandler;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.message.Attachment;

class WrappedAttachments
implements Set<Attachment> {
    private Map<String, DataHandler> attachments;
    private Map<String, Attachment> cache;

    WrappedAttachments(Map<String, DataHandler> attachments) {
        this.attachments = attachments;
        this.cache = new HashMap<String, Attachment>();
    }

    @Override
    public int size() {
        return this.attachments.size();
    }

    @Override
    public boolean isEmpty() {
        return this.attachments.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Attachment) {
            return this.attachments.containsKey(((Attachment)o).getId());
        }
        return false;
    }

    @Override
    public Iterator<Attachment> iterator() {
        return new WrappedAttachmentsIterator(this.attachments.entrySet().iterator());
    }

    @Override
    public Object[] toArray() {
        return this.toArray(new Object[0]);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] copy = a.length == this.attachments.size() ? a : (Object[])Array.newInstance(a.getClass().getComponentType(), this.attachments.size());
        int i = 0;
        for (Map.Entry<String, DataHandler> entry : this.attachments.entrySet()) {
            Attachment o = this.cache.get(entry.getKey());
            if (o == null) {
                o = new AttachmentImpl(entry.getKey(), entry.getValue());
                this.cache.put(entry.getKey(), o);
            }
            copy[i++] = o;
        }
        return copy;
    }

    @Override
    public boolean add(Attachment e) {
        if (!this.attachments.containsKey(e.getId())) {
            this.attachments.put(e.getId(), e.getDataHandler());
            this.cache.put(e.getId(), e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Attachment) {
            this.cache.remove(((Attachment)o).getId());
            return this.attachments.remove(((Attachment)o).getId()) != null;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean b = true;
        for (Object o : c) {
            if (o instanceof Attachment && this.attachments.containsKey(((Attachment)o).getId())) continue;
            b = false;
            break;
        }
        return b;
    }

    @Override
    public boolean addAll(Collection<? extends Attachment> c) {
        boolean b = false;
        for (Attachment attachment : c) {
            if (this.attachments.containsKey(attachment.getId())) continue;
            b = true;
            this.attachments.put(attachment.getId(), attachment.getDataHandler());
            this.cache.put(attachment.getId(), attachment);
        }
        return b;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean b = false;
        HashSet<String> ids = new HashSet<String>();
        for (Object o : c) {
            if (!(o instanceof Attachment)) continue;
            ids.add(((Attachment)o).getId());
        }
        Iterator<Object> it = this.attachments.keySet().iterator();
        while (it.hasNext()) {
            String k = (String)it.next();
            if (ids.contains(k)) continue;
            b = true;
            it.remove();
            this.cache.remove(k);
        }
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = false;
        for (Object o : c) {
            if (!(o instanceof Attachment) || !this.attachments.containsKey(((Attachment)o).getId())) continue;
            b = true;
            this.attachments.remove(((Attachment)o).getId());
            this.cache.remove(((Attachment)o).getId());
        }
        return b;
    }

    @Override
    public void clear() {
        this.attachments.clear();
        this.cache.clear();
    }

    Map<String, DataHandler> getAttachments() {
        return this.attachments;
    }

    class WrappedAttachmentsIterator
    implements Iterator<Attachment> {
        private Iterator<Map.Entry<String, DataHandler>> iterator;
        private String key;

        WrappedAttachmentsIterator(Iterator<Map.Entry<String, DataHandler>> iterator2) {
            this.iterator = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Attachment next() {
            Map.Entry<String, DataHandler> e = this.iterator.next();
            this.key = e.getKey();
            Attachment o = (Attachment)WrappedAttachments.this.cache.get(this.key);
            if (o == null) {
                o = new AttachmentImpl(this.key, e.getValue());
                WrappedAttachments.this.cache.put(this.key, o);
            }
            return o;
        }

        @Override
        public void remove() {
            this.iterator.remove();
            WrappedAttachments.this.cache.remove(this.key);
        }
    }
}

