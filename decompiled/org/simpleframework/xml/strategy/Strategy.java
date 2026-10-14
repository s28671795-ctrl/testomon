/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import java.util.Map;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;

public interface Strategy {
    public Value read(Type var1, NodeMap<InputNode> var2, Map var3) throws Exception;

    public boolean write(Type var1, Object var2, NodeMap<OutputNode> var3, Map var4) throws Exception;
}

