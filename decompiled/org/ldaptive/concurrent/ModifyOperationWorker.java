/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import org.ldaptive.ModifyOperation;
import org.ldaptive.ModifyRequest;
import org.ldaptive.ModifyResponse;
import org.ldaptive.concurrent.AbstractOperationWorker;

public class ModifyOperationWorker
extends AbstractOperationWorker<ModifyOperation, ModifyRequest, ModifyResponse> {
    public ModifyOperationWorker() {
        super(new ModifyOperation());
    }

    public ModifyOperationWorker(ModifyOperation op) {
        super(op);
    }
}

