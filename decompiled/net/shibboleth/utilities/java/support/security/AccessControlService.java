/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.component.UnmodifiableComponent;
import net.shibboleth.utilities.java.support.security.AccessControl;

@ThreadSafe
public interface AccessControlService
extends InitializableComponent,
IdentifiedComponent,
UnmodifiableComponent {
    @Nonnull
    public AccessControl getInstance(@Nonnull String var1);
}

