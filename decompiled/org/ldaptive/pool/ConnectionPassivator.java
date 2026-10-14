/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.util.function.Function;
import org.ldaptive.Connection;

public interface ConnectionPassivator
extends Function<Connection, Boolean> {
}

