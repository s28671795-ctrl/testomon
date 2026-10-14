/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.persist.XMLObjectLoadSaveManager;

@NotThreadSafe
public class MapLoadSaveManager<T extends XMLObject>
implements XMLObjectLoadSaveManager<T> {
    private Map<String, T> backingMap;

    public MapLoadSaveManager() {
        this(new HashMap());
    }

    public MapLoadSaveManager(@Nonnull Map<String, T> map) {
        this.backingMap = Constraint.isNotNull(map, "Backing map was null");
    }

    @Override
    public Set<String> listKeys() throws IOException {
        return this.backingMap.keySet();
    }

    @Override
    public Iterable<Pair<String, T>> listAll() throws IOException {
        ArrayList<Pair<String, T>> list = new ArrayList<Pair<String, T>>();
        for (String key : this.listKeys()) {
            list.add(new Pair<String, T>(key, this.load(key)));
        }
        return list;
    }

    @Override
    public boolean exists(String key) throws IOException {
        return this.backingMap.containsKey(key);
    }

    @Override
    public T load(String key) throws IOException {
        return (T)((XMLObject)this.backingMap.get(key));
    }

    @Override
    public void save(String key, T xmlObject) throws IOException {
        this.save(key, xmlObject, false);
    }

    @Override
    public void save(String key, T xmlObject, boolean overwrite) throws IOException {
        if (!overwrite && this.exists(key)) {
            throw new IOException(String.format("Value already exists for key '%s'", key));
        }
        this.backingMap.put(key, xmlObject);
    }

    @Override
    public boolean remove(String key) throws IOException {
        return this.backingMap.remove(key) != null;
    }

    @Override
    public boolean updateKey(String currentKey, String newKey) throws IOException {
        T value = this.load(currentKey);
        if (value != null) {
            this.save(newKey, value, false);
            this.remove(currentKey);
            return true;
        }
        return false;
    }
}

