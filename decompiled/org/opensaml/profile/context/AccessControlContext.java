/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.profile.context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;

public class AccessControlContext
extends BaseContext {
    @Nullable
    private String operation;
    @Nullable
    private String resource;

    @Nullable
    public String getOperation() {
        return this.operation;
    }

    @Nonnull
    public AccessControlContext setOperation(@Nullable String op) {
        this.operation = op;
        return this;
    }

    @Nullable
    public String getResource() {
        return this.resource;
    }

    @Nonnull
    public AccessControlContext setResource(@Nullable String res) {
        this.resource = res;
        return this;
    }
}

