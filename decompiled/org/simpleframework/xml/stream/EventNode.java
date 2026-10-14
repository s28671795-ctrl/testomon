/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Attribute;

public interface EventNode
extends Iterable<Attribute> {
    public int getLine();

    public String getName();

    public String getValue();

    public String getReference();

    public String getPrefix();

    public Object getSource();

    public boolean isEnd();

    public boolean isStart();

    public boolean isText();
}

