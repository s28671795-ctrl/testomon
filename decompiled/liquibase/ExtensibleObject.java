/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.util.List;
import java.util.SortedSet;
import liquibase.ObjectMetaData;

public interface ExtensibleObject
extends Cloneable {
    public SortedSet<String> getAttributes();

    public ObjectMetaData getObjectMetaData();

    public boolean has(String var1);

    public List getValuePath(String var1, Class var2);

    public <T> T get(String var1, Class<T> var2);

    public <T> T get(String var1, T var2);

    public ExtensibleObject set(String var1, Object var2);

    public String describe();

    public Object clone();
}

