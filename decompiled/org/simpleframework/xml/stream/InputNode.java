/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Node;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.Position;

public interface InputNode
extends Node {
    public boolean isRoot();

    public boolean isElement();

    public String getPrefix();

    public String getReference();

    public Position getPosition();

    public InputNode getAttribute(String var1);

    public NodeMap<InputNode> getAttributes();

    @Override
    public InputNode getParent();

    public Object getSource();

    public InputNode getNext() throws Exception;

    public InputNode getNext(String var1) throws Exception;

    public void skip() throws Exception;

    public boolean isEmpty() throws Exception;
}

