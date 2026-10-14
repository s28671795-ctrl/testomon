/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

public class AuthenticationUser {
    String username;
    String password;
    String groups;

    public AuthenticationUser() {
    }

    public AuthenticationUser(String username, String password, String groups2) {
        this.username = username;
        this.password = password;
        this.groups = groups2;
    }

    public String getGroups() {
        return this.groups;
    }

    public void setGroups(String groups2) {
        this.groups = groups2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

