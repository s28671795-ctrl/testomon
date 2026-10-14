/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util.dag;

import java.util.Iterator;
import java.util.List;

public class CycleDetectedException
extends Exception {
    private List<String> cycle;

    public CycleDetectedException(String message, List<String> cycle) {
        super(message);
        this.cycle = cycle;
    }

    public List<String> getCycle() {
        return this.cycle;
    }

    public String cycleToString() {
        StringBuilder buffer = new StringBuilder();
        Iterator<String> iterator2 = this.cycle.iterator();
        while (iterator2.hasNext()) {
            buffer.append(iterator2.next());
            if (!iterator2.hasNext()) continue;
            buffer.append(" --> ");
        }
        return buffer.toString();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " " + this.cycleToString();
    }
}

