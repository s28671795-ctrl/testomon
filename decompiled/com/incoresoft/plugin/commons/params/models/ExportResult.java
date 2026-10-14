/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params.models;

import java.io.InputStream;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class ExportResult {
    private static final ExportResult emptyExportResult = new ExportResult("", InputStream.nullInputStream());
    private final String name;
    private final InputStream data;

    public ExportResult(@NotNull String name, @NotNull InputStream data) {
        this.name = Objects.requireNonNull(name, "name should not be null");
        this.data = Objects.requireNonNull(data, "data should not be null");
    }

    public static ExportResult emptyExportResult() {
        return emptyExportResult;
    }

    public String getName() {
        return this.name;
    }

    public InputStream getData() {
        return this.data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExportResult)) {
            return false;
        }
        ExportResult other = (ExportResult)o;
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        InputStream this$data = this.getData();
        InputStream other$data = other.getData();
        return !(this$data == null ? other$data != null : !this$data.equals(other$data));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        InputStream $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ExportResult(name=" + this.getName() + ", data=" + String.valueOf(this.getData()) + ")";
    }
}

