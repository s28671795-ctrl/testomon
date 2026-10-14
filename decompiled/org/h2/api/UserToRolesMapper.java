/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

import java.util.Collection;
import org.h2.security.auth.AuthenticationException;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.Configurable;

public interface UserToRolesMapper
extends Configurable {
    public Collection<String> mapUserToRoles(AuthenticationInfo var1) throws AuthenticationException;
}

