/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

public class OpenOptions {
    private boolean truncate = true;
    private boolean createIfNeeded = true;

    public boolean isTruncate() {
        return this.truncate;
    }

    public OpenOptions setTruncate(boolean truncate) {
        this.truncate = truncate;
        return this;
    }

    public boolean isAppend() {
        return !this.isTruncate();
    }

    public OpenOptions setAppend(boolean append) {
        this.truncate = !append;
        return this;
    }

    public boolean isCreateIfNeeded() {
        return this.createIfNeeded;
    }

    public OpenOptions setCreateIfNeeded(boolean createIfNeeded) {
        this.createIfNeeded = createIfNeeded;
        return this;
    }
}

