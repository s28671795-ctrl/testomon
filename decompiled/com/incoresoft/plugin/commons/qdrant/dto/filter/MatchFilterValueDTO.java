/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.filter;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Collection;

public final class MatchFilterValueDTO<T> {
    @JSONField(name="value")
    private final T value;
    @JSONField(name="any")
    private final Collection<T> any;

    public static <T> MatchFilterValueDTO<T> of(T value) {
        return new MatchFilterValueDTO<T>(value, null);
    }

    public static <T> MatchFilterValueDTO<T> of(Collection<T> values2) {
        return new MatchFilterValueDTO<Object>(null, values2);
    }

    public MatchFilterValueDTO(T value, Collection<T> any) {
        this.value = value;
        this.any = any;
    }

    public T getValue() {
        return this.value;
    }

    public Collection<T> getAny() {
        return this.any;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MatchFilterValueDTO)) {
            return false;
        }
        MatchFilterValueDTO other = (MatchFilterValueDTO)o;
        T this$value = this.getValue();
        T other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
            return false;
        }
        Collection<T> this$any = this.getAny();
        Collection<T> other$any = other.getAny();
        return !(this$any == null ? other$any != null : !((Object)this$any).equals(other$any));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        T $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        Collection<T> $any = this.getAny();
        result = result * 59 + ($any == null ? 43 : ((Object)$any).hashCode());
        return result;
    }

    public String toString() {
        return "MatchFilterValueDTO(value=" + String.valueOf(this.getValue()) + ", any=" + String.valueOf(this.getAny()) + ")";
    }
}

