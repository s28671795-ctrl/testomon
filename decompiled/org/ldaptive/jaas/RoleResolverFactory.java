/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.util.Map;
import org.ldaptive.SearchRequest;
import org.ldaptive.jaas.RoleResolver;

public interface RoleResolverFactory {
    public RoleResolver createRoleResolver(Map<String, ?> var1);

    public SearchRequest createSearchRequest(Map<String, ?> var1);
}

