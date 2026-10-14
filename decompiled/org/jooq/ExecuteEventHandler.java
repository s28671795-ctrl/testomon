/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.ExecuteContext;

@FunctionalInterface
public interface ExecuteEventHandler {
    public void fire(ExecuteContext var1);
}

