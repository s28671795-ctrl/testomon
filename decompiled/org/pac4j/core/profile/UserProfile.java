/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface UserProfile
extends Serializable {
    public String getId();

    public void setId(String var1);

    public String getTypedId();

    public String getUsername();

    public Object getAttribute(String var1);

    public Map<String, Object> getAttributes();

    public boolean containsAttribute(String var1);

    public void addAttribute(String var1, Object var2);

    public void removeAttribute(String var1);

    public void addAuthenticationAttribute(String var1, Object var2);

    public void removeAuthenticationAttribute(String var1);

    public void addRole(String var1);

    public void addRoles(Collection<String> var1);

    public Set<String> getRoles();

    @Deprecated
    public void addPermission(String var1);

    @Deprecated
    public void addPermissions(Collection<String> var1);

    @Deprecated
    public Set<String> getPermissions();

    public boolean isRemembered();

    public void setRemembered(boolean var1);

    public String getClientName();

    public void setClientName(String var1);

    public String getLinkedId();

    public void setLinkedId(String var1);

    public boolean isExpired();

    public Principal asPrincipal();
}

