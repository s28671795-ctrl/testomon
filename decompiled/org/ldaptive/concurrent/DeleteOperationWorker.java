/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import org.ldaptive.DeleteOperation;
import org.ldaptive.DeleteRequest;
import org.ldaptive.DeleteResponse;
import org.ldaptive.concurrent.AbstractOperationWorker;

public class DeleteOperationWorker
extends AbstractOperationWorker<DeleteOperation, DeleteRequest, DeleteResponse> {
    public DeleteOperationWorker() {
        super(new DeleteOperation());
    }

    public DeleteOperationWorker(DeleteOperation op) {
        super(op);
    }
}

