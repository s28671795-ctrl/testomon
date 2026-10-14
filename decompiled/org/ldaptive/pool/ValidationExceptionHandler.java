/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.util.function.Function;
import org.ldaptive.Connection;
import org.ldaptive.pool.ValidationException;

public interface ValidationExceptionHandler
extends Function<ValidationException, Connection> {
}

