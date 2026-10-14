/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.HashMap;
import java.util.Map;
import org.jboss.com.sun.corba.se.impl.oa.poa.AOMEntry;
import org.jboss.com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.omg.PortableServer.POAPackage.WrongPolicy;

class SingleObjectMap
extends ActiveObjectMap {
    private Map<AOMEntry, ActiveObjectMap.Key> entryToKey = new HashMap<AOMEntry, ActiveObjectMap.Key>();

    public SingleObjectMap(POAImpl poa) {
        super(poa);
    }

    @Override
    public ActiveObjectMap.Key getKey(AOMEntry value) throws WrongPolicy {
        return this.entryToKey.get(value);
    }

    @Override
    protected void putEntry(ActiveObjectMap.Key key, AOMEntry value) {
        super.putEntry(key, value);
        this.entryToKey.put(value, key);
    }

    @Override
    public boolean hasMultipleIDs(AOMEntry value) {
        return false;
    }

    @Override
    protected void removeEntry(AOMEntry entry, ActiveObjectMap.Key key) {
        this.entryToKey.remove(entry);
    }

    @Override
    public void clear() {
        super.clear();
        this.entryToKey.clear();
    }
}

