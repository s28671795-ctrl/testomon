/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import javax.security.auth.x500.X500Principal;

public interface X500DNHandler {
    public static final String FORMAT_RFC1779 = "RFC1779";
    public static final String FORMAT_RFC2253 = "RFC2253";

    @Nonnull
    public X500Principal parse(@Nonnull String var1);

    @Nonnull
    public X500Principal parse(@Nonnull byte[] var1);

    @Nonnull
    public String getName(@Nonnull X500Principal var1);

    @Nonnull
    public String getName(@Nonnull X500Principal var1, @Nonnull String var2);

    @Nonnull
    public byte[] getEncoded(@Nonnull X500Principal var1);

    @Nonnull
    public X500DNHandler clone();
}

