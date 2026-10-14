/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.http;

import org.apache.hc.core5.util.Args;

public enum URIScheme {
    HTTP("http"),
    HTTPS("https");

    public final String id;

    private URIScheme(String id) {
        this.id = Args.notBlank(id, "id");
    }

    public String getId() {
        return this.id;
    }

    public boolean same(String scheme2) {
        return this.id.equalsIgnoreCase(scheme2);
    }

    public String toString() {
        return this.id;
    }
}

