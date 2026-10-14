/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.ArrayList;
import org.simpleframework.xml.stream.Attribute;
import org.simpleframework.xml.stream.EventNode;

abstract class EventElement
extends ArrayList<Attribute>
implements EventNode {
    EventElement() {
    }

    @Override
    public int getLine() {
        return -1;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean isStart() {
        return true;
    }

    @Override
    public boolean isText() {
        return false;
    }
}

