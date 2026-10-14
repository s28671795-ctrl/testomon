/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import java.util.function.Supplier;
import org.jooq.Internal;

@FunctionalInterface
@Internal
public interface LazySupplier<T>
extends Serializable,
Supplier<T> {
}

