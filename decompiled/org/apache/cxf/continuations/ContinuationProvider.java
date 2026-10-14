/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.continuations;

import org.apache.cxf.continuations.Continuation;

public interface ContinuationProvider {
    public Continuation getContinuation();

    public void complete();
}

