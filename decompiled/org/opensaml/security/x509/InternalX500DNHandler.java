/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.x509.X500DNHandler;

public class InternalX500DNHandler
implements X500DNHandler {
    @Override
    @Nonnull
    public byte[] getEncoded(@Nonnull X500Principal principal) {
        Constraint.isNotNull(principal, "X500Principal cannot be null");
        return principal.getEncoded();
    }

    @Override
    @Nonnull
    public String getName(@Nonnull X500Principal principal) {
        Constraint.isNotNull(principal, "X500Principal cannot be null");
        return principal.getName();
    }

    @Override
    @Nonnull
    public String getName(@Nonnull X500Principal principal, @Nonnull String format) {
        Constraint.isNotNull(principal, "X500Principal cannot be null");
        return principal.getName(format);
    }

    @Override
    @Nonnull
    public X500Principal parse(@Nonnull String name) {
        Constraint.isNotNull(name, "X.500 name string cannot be null");
        return new X500Principal(name);
    }

    @Override
    @Nonnull
    public X500Principal parse(@Nonnull byte[] name) {
        Constraint.isNotNull(name, "X.500 DER-encoded name cannot be null");
        return new X500Principal(name);
    }

    @Override
    @Nonnull
    public X500DNHandler clone() {
        return new InternalX500DNHandler();
    }
}

