/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.function.Predicate;
import org.ldaptive.LdapURL;
import org.ldaptive.LdapURLSet;

public interface ConnectionStrategy
extends Iterable<LdapURL> {
    public void populate(String var1, LdapURLSet var2);

    public void initialize(String var1, Predicate<LdapURL> var2);

    public boolean isInitialized();

    public Predicate<LdapURL> getActivateCondition();

    public Predicate<LdapURL> getRetryCondition();

    public void success(LdapURL var1);

    public void failure(LdapURL var1);

    public ConnectionStrategy newInstance();
}

