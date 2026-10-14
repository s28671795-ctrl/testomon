/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public interface Converter<T> {
    public T read(InputNode var1) throws Exception;

    public void write(OutputNode var1, T var2) throws Exception;
}

