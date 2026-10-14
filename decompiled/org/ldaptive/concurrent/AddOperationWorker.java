/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import org.ldaptive.AddOperation;
import org.ldaptive.AddRequest;
import org.ldaptive.AddResponse;
import org.ldaptive.concurrent.AbstractOperationWorker;

public class AddOperationWorker
extends AbstractOperationWorker<AddOperation, AddRequest, AddResponse> {
    public AddOperationWorker() {
        super(new AddOperation());
    }

    public AddOperationWorker(AddOperation op) {
        super(op);
    }
}

