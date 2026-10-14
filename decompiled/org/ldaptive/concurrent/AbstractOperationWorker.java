/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import org.ldaptive.LdapException;
import org.ldaptive.Operation;
import org.ldaptive.OperationHandle;
import org.ldaptive.Request;
import org.ldaptive.Result;
import org.ldaptive.concurrent.OperationWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOperationWorker<T extends Operation<Q, S>, Q extends Request, S extends Result>
implements OperationWorker<Q, S> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private T operation;

    public AbstractOperationWorker(T op) {
        this.setOperation(op);
    }

    public T getOperation() {
        return this.operation;
    }

    public void setOperation(T op) {
        this.operation = op;
    }

    @Override
    public Collection<OperationHandle<Q, S>> send(Q[] requests) {
        ArrayList<OperationHandle<Q, S>> results = new ArrayList<OperationHandle<Q, S>>(requests.length);
        for (Q request : requests) {
            try {
                results.add(this.operation.send(request));
            }
            catch (LdapException e) {
                this.logger.warn("Error occurred attempting to execute request {}", (Object)request, (Object)e);
            }
        }
        return results;
    }

    @Override
    public Collection<S> execute(Q[] requests) {
        ArrayList responses = new ArrayList(requests.length);
        Collection handles = this.send((Request[])requests);
        for (OperationHandle handle2 : handles) {
            try {
                responses.add(handle2.await());
            }
            catch (LdapException e) {
                this.logger.warn("Error occurred waiting on handle {}", (Object)handle2, (Object)e);
            }
        }
        return responses;
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "operation=" + this.operation;
    }
}

