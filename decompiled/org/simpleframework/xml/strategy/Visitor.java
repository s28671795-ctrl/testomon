/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;

public interface Visitor {
    public void read(Type var1, NodeMap<InputNode> var2) throws Exception;

    public void write(Type var1, NodeMap<OutputNode> var2) throws Exception;
}

