/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 */
package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;

public interface AccessControl {
    public boolean checkAccess(@Nonnull ServletRequest var1, @Nullable String var2, @Nullable String var3);
}

