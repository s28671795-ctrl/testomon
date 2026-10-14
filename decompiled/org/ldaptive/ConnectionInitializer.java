/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.Connection;
import org.ldaptive.LdapException;
import org.ldaptive.Result;

public interface ConnectionInitializer {
    public Result initialize(Connection var1) throws LdapException;
}

