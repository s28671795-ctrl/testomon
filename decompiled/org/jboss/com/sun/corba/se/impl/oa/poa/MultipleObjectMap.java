/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jboss.com.sun.corba.se.impl.oa.poa.AOMEntry;
import org.jboss.com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.omg.PortableServer.POAPackage.WrongPolicy;

class MultipleObjectMap
extends ActiveObjectMap {
    private Map<AOMEntry, Set<ActiveObjectMap.Key>> entryToKeys = new HashMap<AOMEntry, Set<ActiveObjectMap.Key>>();

    public MultipleObjectMap(POAImpl poa) {
        super(poa);
    }

    @Override
    public ActiveObjectMap.Key getKey(AOMEntry value) throws WrongPolicy {
        throw new WrongPolicy();
    }

    @Override
    protected void putEntry(ActiveObjectMap.Key key, AOMEntry value) {
        super.putEntry(key, value);
        Set<ActiveObjectMap.Key> set = this.entryToKeys.get(value);
        if (set == null) {
            set = new HashSet<ActiveObjectMap.Key>();
            this.entryToKeys.put(value, set);
        }
        set.add(key);
    }

    @Override
    public boolean hasMultipleIDs(AOMEntry value) {
        Set<ActiveObjectMap.Key> set = this.entryToKeys.get(value);
        if (set == null) {
            return false;
        }
        return set.size() > 1;
    }

    @Override
    protected void removeEntry(AOMEntry entry, ActiveObjectMap.Key key) {
        Set<ActiveObjectMap.Key> keys2 = this.entryToKeys.get(entry);
        if (keys2 != null) {
            keys2.remove(key);
            if (keys2.isEmpty()) {
                this.entryToKeys.remove(entry);
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        this.entryToKeys.clear();
    }
}

