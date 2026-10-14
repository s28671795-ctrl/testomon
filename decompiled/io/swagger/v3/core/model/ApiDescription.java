/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.model;

public class ApiDescription {
    private String path;
    private String method;

    public ApiDescription(String path, String method2) {
        this.setPath(path);
        this.setMethod(method2);
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method2) {
        this.method = method2;
    }
}

