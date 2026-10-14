/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.LdapException;
import org.ldaptive.OperationHandle;
import org.ldaptive.Request;
import org.ldaptive.Result;

public interface Operation<Q extends Request, S extends Result> {
    public OperationHandle<Q, S> send(Q var1) throws LdapException;

    public S execute(Q var1) throws LdapException;
}

