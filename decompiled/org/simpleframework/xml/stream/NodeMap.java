/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.Iterator;
import org.simpleframework.xml.stream.Node;

public interface NodeMap<T extends Node>
extends Iterable<String> {
    public T getNode();

    public String getName();

    public T get(String var1);

    public T remove(String var1);

    @Override
    public Iterator<String> iterator();

    public T put(String var1, String var2);
}

