/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

public class User {
    private final String identifier;
    private final Object context;

    public User(String id) {
        this(id, null);
    }

    public User(String id, Object ctx) {
        this.identifier = id;
        this.context = ctx;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Object getContext() {
        return this.context;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "identifier=" + this.identifier + ", " + "context=" + this.context + "]";
    }
}

