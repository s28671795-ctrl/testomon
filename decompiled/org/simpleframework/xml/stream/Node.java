/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

public interface Node {
    public String getName();

    public String getValue() throws Exception;

    public Node getParent();
}

