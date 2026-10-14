/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import java.util.function.Function;
import org.ldaptive.Request;

public interface RequestHandler<Q extends Request>
extends Function<Q, Q> {
}

