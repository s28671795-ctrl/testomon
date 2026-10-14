/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import java.util.function.Function;
import org.ldaptive.LdapEntry;

public interface LdapEntryHandler
extends Function<LdapEntry, LdapEntry> {
}

