/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.cxf.interceptor.security.AbstractAuthorizingInInterceptor;
import org.apache.cxf.security.SecurityContext;

public class SimpleAuthorizingInterceptor
extends AbstractAuthorizingInInterceptor {
    protected Map<String, List<String>> methodRolesMap = new HashMap<String, List<String>>();
    protected Map<String, List<String>> userRolesMap = Collections.emptyMap();
    protected List<String> globalRoles = Collections.emptyList();
    private boolean checkConfiguredRolesOnly;

    public SimpleAuthorizingInterceptor() {
        this(true);
    }

    public SimpleAuthorizingInterceptor(boolean uniqueId) {
        super(uniqueId);
    }

    @Override
    protected boolean isUserInRole(SecurityContext sc, List<String> roles, boolean deny) {
        if (!this.checkConfiguredRolesOnly && !super.isUserInRole(sc, roles, deny)) {
            return false;
        }
        if (!this.userRolesMap.isEmpty()) {
            List<String> userRoles = this.userRolesMap.get(sc.getUserPrincipal().getName());
            if (userRoles == null) {
                return false;
            }
            for (String role : roles) {
                if (!userRoles.contains(role)) continue;
                return true;
            }
            return false;
        }
        return !this.checkConfiguredRolesOnly;
    }

    protected String createMethodSig(Method method2) {
        StringBuilder b = new StringBuilder(method2.getReturnType().getName());
        b.append(' ').append(method2.getName()).append('(');
        boolean first = true;
        for (Class<?> cls : method2.getParameterTypes()) {
            if (!first) {
                b.append(", ");
                first = false;
            }
            b.append(cls.getName());
        }
        b.append(')');
        return b.toString();
    }

    @Override
    protected List<String> getExpectedRoles(Method method2) {
        List<String> roles = this.methodRolesMap.get(this.createMethodSig(method2));
        if (roles == null) {
            roles = this.methodRolesMap.get(method2.getName());
        }
        if (roles != null) {
            return roles;
        }
        return this.globalRoles;
    }

    public void setMethodRolesMap(Map<String, String> rolesMap) {
        this.methodRolesMap.putAll(SimpleAuthorizingInterceptor.parseRolesMap(rolesMap));
    }

    public void setUserRolesMap(Map<String, String> rolesMap) {
        this.userRolesMap = SimpleAuthorizingInterceptor.parseRolesMap(rolesMap);
    }

    public void setGlobalRoles(String roles) {
        this.globalRoles = Arrays.asList(roles.split(" "));
    }

    public void setCheckConfiguredRolesOnly(boolean checkConfiguredRolesOnly) {
        this.checkConfiguredRolesOnly = checkConfiguredRolesOnly;
    }

    private static Map<String, List<String>> parseRolesMap(Map<String, String> rolesMap) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (Map.Entry<String, String> entry : rolesMap.entrySet()) {
            map.put(entry.getKey(), Arrays.asList(entry.getValue().split(" ")));
        }
        return map;
    }
}

