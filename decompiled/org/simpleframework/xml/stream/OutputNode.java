/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.NamespaceMap;
import org.simpleframework.xml.stream.Node;
import org.simpleframework.xml.stream.NodeMap;

public interface OutputNode
extends Node {
    public boolean isRoot();

    public NodeMap<OutputNode> getAttributes();

    public Mode getMode();

    public void setMode(Mode var1);

    public void setData(boolean var1);

    public String getPrefix();

    public String getPrefix(boolean var1);

    public String getReference();

    public void setReference(String var1);

    public NamespaceMap getNamespaces();

    public String getComment();

    public void setComment(String var1);

    public void setValue(String var1);

    public void setName(String var1);

    public OutputNode setAttribute(String var1, String var2);

    @Override
    public OutputNode getParent();

    public OutputNode getChild(String var1) throws Exception;

    public void remove() throws Exception;

    public void commit() throws Exception;

    public boolean isCommitted();
}

