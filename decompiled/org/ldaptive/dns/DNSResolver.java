/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dns;

import java.util.Set;

public interface DNSResolver<T> {
    public Set<T> resolve(String var1);
}

