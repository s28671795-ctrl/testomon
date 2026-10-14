/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.Iterator;

public interface NamespaceMap
extends Iterable<String> {
    public String getPrefix();

    public String getPrefix(String var1);

    public String getReference(String var1);

    @Override
    public Iterator<String> iterator();

    public String setReference(String var1);

    public String setReference(String var1, String var2);
}

