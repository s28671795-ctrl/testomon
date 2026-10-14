/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.EventNode;
import org.simpleframework.xml.stream.Position;

class InputPosition
implements Position {
    private EventNode source;

    public InputPosition(EventNode source2) {
        this.source = source2;
    }

    @Override
    public int getLine() {
        return this.source.getLine();
    }

    @Override
    public String toString() {
        return String.format("line %s", this.getLine());
    }
}

