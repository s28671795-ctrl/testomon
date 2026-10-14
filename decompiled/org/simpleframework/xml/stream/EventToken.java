/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.Iterator;
import org.simpleframework.xml.stream.Attribute;
import org.simpleframework.xml.stream.EventNode;

abstract class EventToken
implements EventNode {
    EventToken() {
    }

    @Override
    public int getLine() {
        return -1;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getReference() {
        return null;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public Object getSource() {
        return null;
    }

    @Override
    public Iterator<Attribute> iterator() {
        return null;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean isStart() {
        return false;
    }

    @Override
    public boolean isText() {
        return false;
    }
}

