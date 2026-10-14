/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.LinkedList;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.jvnet.hk2.internal.NarrowResults;

public class ImmediateResults {
    private final NarrowResults timelessResults;
    private final List<ActiveDescriptor<?>> validatedImmediateResults = new LinkedList();

    ImmediateResults(NarrowResults cachedResults) {
        this.timelessResults = cachedResults == null ? new NarrowResults() : cachedResults;
    }

    NarrowResults getTimelessResults() {
        return this.timelessResults;
    }

    List<ActiveDescriptor<?>> getImmediateResults() {
        return this.validatedImmediateResults;
    }

    void addValidatedResult(ActiveDescriptor<?> addMe) {
        this.validatedImmediateResults.add(addMe);
    }

    public String toString() {
        return "ImmediateResults(" + this.timelessResults + "," + this.validatedImmediateResults + "," + System.identityHashCode(this) + ")";
    }
}

