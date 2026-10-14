/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.Stack;

class InputStack
extends Stack<InputNode> {
    public InputStack() {
        super(6);
    }

    public boolean isRelevant(InputNode value) {
        return this.contains(value) || this.isEmpty();
    }
}

