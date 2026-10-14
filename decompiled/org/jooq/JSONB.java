/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.JSONValue;
import org.jooq.tools.json.ParseException;

public final class JSONB
implements Serializable {
    private final String data;
    private transient Object parsed;

    private JSONB(String data) {
        this.data = String.valueOf(data);
    }

    @NotNull
    public final String data() {
        return this.data;
    }

    @NotNull
    public static final JSONB valueOf(String data) {
        return new JSONB(data);
    }

    @NotNull
    public static final JSONB jsonb(String data) {
        return new JSONB(data);
    }

    @Nullable
    public static final JSONB jsonbOrNull(String data) {
        return data == null ? null : JSONB.jsonb(data);
    }

    private final Object parsed() {
        if (this.parsed == null) {
            try {
                this.parsed = new JSONParser().parse(this.data);
            }
            catch (ParseException e) {
                this.parsed = this.data;
            }
        }
        return this.parsed;
    }

    public int hashCode() {
        Object p = this.parsed();
        return p == null ? 0 : p.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JSONB) {
            JSONB j = (JSONB)obj;
            return Objects.equals(this.parsed(), j.parsed());
        }
        return false;
    }

    public String toString() {
        return JSONValue.toJSONString(this.parsed());
    }
}

