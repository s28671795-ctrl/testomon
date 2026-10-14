/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.extended;

import org.ldaptive.Connection;
import org.ldaptive.ConnectionInitializer;
import org.ldaptive.LdapException;
import org.ldaptive.Result;
import org.ldaptive.ad.extended.FastBindRequest;

public class FastBindConnectionInitializer
implements ConnectionInitializer {
    @Override
    public Result initialize(Connection c) throws LdapException {
        return c.operation(new FastBindRequest()).execute();
    }
}

