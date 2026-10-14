/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dns;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

public interface DNSContextFactory {
    public DirContext create() throws NamingException;
}

