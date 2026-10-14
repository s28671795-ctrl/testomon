/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import org.ldaptive.CompareOperation;
import org.ldaptive.CompareRequest;
import org.ldaptive.CompareResponse;
import org.ldaptive.concurrent.AbstractOperationWorker;

public class CompareOperationWorker
extends AbstractOperationWorker<CompareOperation, CompareRequest, CompareResponse> {
    public CompareOperationWorker() {
        super(new CompareOperation());
    }

    public CompareOperationWorker(CompareOperation op) {
        super(op);
    }
}

