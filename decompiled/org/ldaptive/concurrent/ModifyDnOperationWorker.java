/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import org.ldaptive.ModifyDnOperation;
import org.ldaptive.ModifyDnRequest;
import org.ldaptive.ModifyDnResponse;
import org.ldaptive.concurrent.AbstractOperationWorker;

public class ModifyDnOperationWorker
extends AbstractOperationWorker<ModifyDnOperation, ModifyDnRequest, ModifyDnResponse> {
    public ModifyDnOperationWorker() {
        super(new ModifyDnOperation());
    }

    public ModifyDnOperationWorker(ModifyDnOperation op) {
        super(op);
    }
}

