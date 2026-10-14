/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jboss.com.sun.corba.se.impl.oa.poa.AOMEntry;
import org.jboss.com.sun.corba.se.impl.oa.poa.MultipleObjectMap;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.SingleObjectMap;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;

public abstract class ActiveObjectMap {
    protected POAImpl poa;
    private Map<Key, AOMEntry> keyToEntry = new HashMap<Key, AOMEntry>();
    private Map<AOMEntry, Servant> entryToServant = new HashMap<AOMEntry, Servant>();
    private Map<Servant, AOMEntry> servantToEntry = new HashMap<Servant, AOMEntry>();

    protected ActiveObjectMap(POAImpl poa) {
        this.poa = poa;
    }

    public static ActiveObjectMap create(POAImpl poa, boolean multipleIDsAllowed) {
        if (multipleIDsAllowed) {
            return new MultipleObjectMap(poa);
        }
        return new SingleObjectMap(poa);
    }

    public final boolean contains(Servant value) {
        return this.servantToEntry.containsKey(value);
    }

    public final boolean containsKey(Key key) {
        return this.keyToEntry.containsKey(key);
    }

    public final AOMEntry get(Key key) {
        AOMEntry result = this.keyToEntry.get(key);
        if (result == null) {
            result = new AOMEntry(this.poa);
            this.putEntry(key, result);
        }
        return result;
    }

    public final Servant getServant(AOMEntry entry) {
        return this.entryToServant.get(entry);
    }

    public abstract Key getKey(AOMEntry var1) throws WrongPolicy;

    public Key getKey(Servant value) throws WrongPolicy {
        AOMEntry entry = this.servantToEntry.get(value);
        return this.getKey(entry);
    }

    protected void putEntry(Key key, AOMEntry value) {
        this.keyToEntry.put(key, value);
    }

    public final void putServant(Servant servant, AOMEntry value) {
        this.entryToServant.put(value, servant);
        this.servantToEntry.put(servant, value);
    }

    protected abstract void removeEntry(AOMEntry var1, Key var2);

    public final void remove(Key key) {
        AOMEntry entry = this.keyToEntry.remove(key);
        Servant servant = this.entryToServant.remove(entry);
        if (servant != null) {
            this.servantToEntry.remove(servant);
        }
        this.removeEntry(entry, key);
    }

    public abstract boolean hasMultipleIDs(AOMEntry var1);

    protected void clear() {
        this.keyToEntry.clear();
    }

    public final Set<Key> keySet() {
        return this.keyToEntry.keySet();
    }

    public static class Key {
        public byte[] id;

        Key(byte[] id) {
            this.id = id;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < this.id.length; ++i) {
                buffer.append(Integer.toString(this.id[i], 16));
                if (i == this.id.length - 1) continue;
                buffer.append(":");
            }
            return buffer.toString();
        }

        public boolean equals(Object key) {
            if (!(key instanceof Key)) {
                return false;
            }
            Key k = (Key)key;
            if (k.id.length != this.id.length) {
                return false;
            }
            for (int i = 0; i < this.id.length; ++i) {
                if (this.id[i] == k.id[i]) continue;
                return false;
            }
            return true;
        }

        public int hashCode() {
            int h2 = 0;
            for (int i = 0; i < this.id.length; ++i) {
                h2 = 31 * h2 + this.id[i];
            }
            return h2;
        }
    }
}

