/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.activation.DataHandler;
import org.apache.cxf.attachment.AttachmentDeserializer;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.message.Attachment;

public class LazyAttachmentCollection
implements Collection<Attachment> {
    private AttachmentDeserializer deserializer;
    private final List<Attachment> attachments = new ArrayList<Attachment>();
    private final int maxAttachmentCount;

    public LazyAttachmentCollection(AttachmentDeserializer deserializer, int maxAttachmentCount) {
        this.deserializer = deserializer;
        this.maxAttachmentCount = maxAttachmentCount;
    }

    public List<Attachment> getLoadedAttachments() {
        return this.attachments;
    }

    private void loadAll() {
        try {
            AttachmentImpl a = this.deserializer.readNext();
            int count = 0;
            while (a != null) {
                this.attachments.add(a);
                if (++count > this.maxAttachmentCount) {
                    throw new IOException("The message contains more attachments than are permitted");
                }
                a = this.deserializer.readNext();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasNext(boolean shouldLoadNew) throws IOException {
        if (shouldLoadNew) {
            AttachmentImpl a = this.deserializer.readNext();
            if (a != null) {
                this.attachments.add(a);
                return true;
            }
            return false;
        }
        return this.deserializer.hasNext();
    }

    public boolean hasNext() throws IOException {
        return this.hasNext(true);
    }

    @Override
    public Iterator<Attachment> iterator() {
        return new Iterator<Attachment>(){
            int current;
            boolean removed;

            @Override
            public boolean hasNext() {
                if (LazyAttachmentCollection.this.attachments.size() > this.current) {
                    return true;
                }
                try {
                    AttachmentImpl a = LazyAttachmentCollection.this.deserializer.readNext();
                    if (a == null) {
                        return false;
                    }
                    LazyAttachmentCollection.this.attachments.add(a);
                    return true;
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Attachment next() {
                Attachment a = (Attachment)LazyAttachmentCollection.this.attachments.get(this.current);
                ++this.current;
                this.removed = false;
                return a;
            }

            @Override
            public void remove() {
                if (this.removed) {
                    throw new IllegalStateException();
                }
                LazyAttachmentCollection.this.attachments.remove(--this.current);
                this.removed = true;
            }
        };
    }

    @Override
    public int size() {
        this.loadAll();
        return this.attachments.size();
    }

    @Override
    public boolean add(Attachment arg0) {
        return this.attachments.add(arg0);
    }

    @Override
    public boolean addAll(Collection<? extends Attachment> arg0) {
        return this.attachments.addAll(arg0);
    }

    @Override
    public void clear() {
        this.attachments.clear();
    }

    @Override
    public boolean contains(Object arg0) {
        return this.attachments.contains(arg0);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return this.attachments.containsAll(arg0);
    }

    @Override
    public boolean isEmpty() {
        if (this.attachments.isEmpty()) {
            return !this.iterator().hasNext();
        }
        return this.attachments.isEmpty();
    }

    @Override
    public boolean remove(Object arg0) {
        return this.attachments.remove(arg0);
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        return this.attachments.removeAll(arg0);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        return this.attachments.retainAll(arg0);
    }

    @Override
    public Object[] toArray() {
        this.loadAll();
        return this.attachments.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        this.loadAll();
        return this.attachments.toArray(arg0);
    }

    public Map<String, DataHandler> createDataHandlerMap() {
        return new LazyAttachmentMap(this);
    }

    private static class LazyAttachmentMap
    implements Map<String, DataHandler> {
        LazyAttachmentCollection collection;

        LazyAttachmentMap(LazyAttachmentCollection c) {
            this.collection = c;
        }

        @Override
        public void clear() {
            this.collection.clear();
        }

        @Override
        public boolean containsKey(Object key) {
            for (Attachment at : this.collection) {
                if (!key.equals(at.getId())) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            for (Attachment at : this.collection) {
                if (!value.equals(at.getDataHandler())) continue;
                return true;
            }
            return false;
        }

        @Override
        public DataHandler get(Object key) {
            for (Attachment at : this.collection) {
                if (!key.equals(at.getId())) continue;
                return at.getDataHandler();
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.collection.isEmpty();
        }

        @Override
        public int size() {
            return this.collection.size();
        }

        @Override
        public DataHandler remove(Object key) {
            for (Attachment at : this.collection) {
                if (!key.equals(at.getId())) continue;
                this.collection.remove(at);
                return at.getDataHandler();
            }
            return null;
        }

        @Override
        public DataHandler put(String key, DataHandler value) {
            AttachmentImpl at = new AttachmentImpl(key, value);
            this.collection.add(at);
            return value;
        }

        @Override
        public void putAll(Map<? extends String, ? extends DataHandler> t2) {
            for (Map.Entry<? extends String, ? extends DataHandler> ent : t2.entrySet()) {
                this.put(ent.getKey(), ent.getValue());
            }
        }

        @Override
        public Set<Map.Entry<String, DataHandler>> entrySet() {
            return new AbstractSet<Map.Entry<String, DataHandler>>(){

                @Override
                public Iterator<Map.Entry<String, DataHandler>> iterator() {
                    return new Iterator<Map.Entry<String, DataHandler>>(){
                        Iterator<Attachment> it;
                        {
                            this.it = collection.iterator();
                        }

                        @Override
                        public boolean hasNext() {
                            return this.it.hasNext();
                        }

                        @Override
                        public Map.Entry<String, DataHandler> next() {
                            return new Map.Entry<String, DataHandler>(){
                                Attachment at;
                                {
                                    this.at = it.next();
                                }

                                @Override
                                public String getKey() {
                                    return this.at.getId();
                                }

                                @Override
                                public DataHandler getValue() {
                                    return this.at.getDataHandler();
                                }

                                @Override
                                public DataHandler setValue(DataHandler value) {
                                    if (this.at instanceof AttachmentImpl) {
                                        DataHandler h2 = this.at.getDataHandler();
                                        ((AttachmentImpl)this.at).setDataHandler(value);
                                        return h2;
                                    }
                                    throw new UnsupportedOperationException();
                                }
                            };
                        }

                        @Override
                        public void remove() {
                            this.it.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return collection.size();
                }
            };
        }

        @Override
        public Set<String> keySet() {
            return new AbstractSet<String>(){

                @Override
                public Iterator<String> iterator() {
                    return new Iterator<String>(){
                        Iterator<Attachment> it;
                        {
                            this.it = collection.iterator();
                        }

                        @Override
                        public boolean hasNext() {
                            return this.it.hasNext();
                        }

                        @Override
                        public String next() {
                            return this.it.next().getId();
                        }

                        @Override
                        public void remove() {
                            this.it.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return collection.size();
                }
            };
        }

        @Override
        public Collection<DataHandler> values() {
            return new AbstractCollection<DataHandler>(){

                @Override
                public Iterator<DataHandler> iterator() {
                    return new Iterator<DataHandler>(){
                        Iterator<Attachment> it;
                        {
                            this.it = collection.iterator();
                        }

                        @Override
                        public boolean hasNext() {
                            return this.it.hasNext();
                        }

                        @Override
                        public DataHandler next() {
                            return this.it.next().getDataHandler();
                        }

                        @Override
                        public void remove() {
                            this.it.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return collection.size();
                }
            };
        }
    }
}

