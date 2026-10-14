/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.users.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Set;

public class UserSearchParams {
    @JSONField(name="name")
    private String name;
    @JSONField(name="role_ids")
    private Set<Integer> roleIds;

    public void setName(String name) {
        this.name = name;
    }

    public void setRoleIds(Set<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getName() {
        return this.name;
    }

    public Set<Integer> getRoleIds() {
        return this.roleIds;
    }

    public String toString() {
        return "UserSearchParams(name=" + this.getName() + ", roleIds=" + String.valueOf(this.getRoleIds()) + ")";
    }
}

