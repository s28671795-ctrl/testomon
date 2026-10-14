/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Attribute;

abstract class EventAttribute
implements Attribute {
    EventAttribute() {
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public String getReference() {
        return null;
    }

    @Override
    public Object getSource() {
        return null;
    }

    @Override
    public boolean isReserved() {
        return false;
    }
}

