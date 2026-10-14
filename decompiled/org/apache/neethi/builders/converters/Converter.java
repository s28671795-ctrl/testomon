/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;

public interface Converter<S, T> {
    public QName getQName(S var1);

    public Map<QName, String> getAttributes(S var1);

    public Iterator<S> getChildren(S var1);

    public T convert(S var1);
}

