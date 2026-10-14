/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import java.util.function.Predicate;
import org.ldaptive.LdapException;
import org.ldaptive.Result;

@FunctionalInterface
public interface ResultPredicate
extends Predicate<Result> {
    public static final ResultPredicate NOT_SUCCESS = result -> !result.isSuccess();

    default public void testAndThrow(Result result) throws LdapException {
        if (this.test(result)) {
            if (result == null) {
                throw new LdapException("Predicate failed for null result");
            }
            throw new LdapException(result);
        }
    }
}

