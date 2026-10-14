/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import java.util.Collection;
import org.ldaptive.OperationHandle;
import org.ldaptive.Request;
import org.ldaptive.Result;

public interface OperationWorker<Q extends Request, S extends Result> {
    public Collection<OperationHandle<Q, S>> send(Q[] var1);

    public Collection<S> execute(Q[] var1);
}

