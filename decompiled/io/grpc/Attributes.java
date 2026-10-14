/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1764")
@Immutable
public final class Attributes {
    private final IdentityHashMap<Key<?>, Object> data;
    private static final IdentityHashMap<Key<?>, Object> EMPTY_MAP = new IdentityHashMap();
    public static final Attributes EMPTY = new Attributes(EMPTY_MAP);

    private Attributes(IdentityHashMap<Key<?>, Object> data) {
        assert (data != null);
        this.data = data;
    }

    @Nullable
    public <T> T get(Key<T> key) {
        return (T)this.data.get(key);
    }

    @Deprecated
    public Set<Key<?>> keys() {
        return Collections.unmodifiableSet(this.data.keySet());
    }

    Set<Key<?>> keysForTest() {
        return Collections.unmodifiableSet(this.data.keySet());
    }

    @Deprecated
    public static Builder newBuilder(Attributes base) {
        Preconditions.checkNotNull(base, "base");
        return new Builder(base);
    }

    public static Builder newBuilder() {
        return new Builder(EMPTY);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String toString() {
        return this.data.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Attributes that = (Attributes)o;
        if (this.data.size() != that.data.size()) {
            return false;
        }
        for (Map.Entry<Key<?>, Object> e : this.data.entrySet()) {
            if (!that.data.containsKey(e.getKey())) {
                return false;
            }
            if (Objects.equal(e.getValue(), that.data.get(e.getKey()))) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hashCode = 0;
        for (Map.Entry<Key<?>, Object> e : this.data.entrySet()) {
            hashCode += Objects.hashCode(e.getKey(), e.getValue());
        }
        return hashCode;
    }

    public static final class Builder {
        private Attributes base;
        private IdentityHashMap<Key<?>, Object> newdata;

        private Builder(Attributes base) {
            assert (base != null);
            this.base = base;
        }

        private IdentityHashMap<Key<?>, Object> data(int size) {
            if (this.newdata == null) {
                this.newdata = new IdentityHashMap(size);
            }
            return this.newdata;
        }

        public <T> Builder set(Key<T> key, T value) {
            this.data(1).put(key, value);
            return this;
        }

        public <T> Builder discard(Key<T> key) {
            if (this.base.data.containsKey(key)) {
                IdentityHashMap newBaseData = new IdentityHashMap(this.base.data);
                newBaseData.remove(key);
                this.base = new Attributes(newBaseData);
            }
            if (this.newdata != null) {
                this.newdata.remove(key);
            }
            return this;
        }

        public Builder setAll(Attributes other) {
            this.data(other.data.size()).putAll(other.data);
            return this;
        }

        public Attributes build() {
            if (this.newdata != null) {
                for (Map.Entry entry : this.base.data.entrySet()) {
                    if (this.newdata.containsKey(entry.getKey())) continue;
                    this.newdata.put((Key)entry.getKey(), entry.getValue());
                }
                this.base = new Attributes(this.newdata);
                this.newdata = null;
            }
            return this.base;
        }
    }

    @Immutable
    public static final class Key<T> {
        private final String debugString;

        private Key(String debugString) {
            this.debugString = debugString;
        }

        public String toString() {
            return this.debugString;
        }

        @Deprecated
        public static <T> Key<T> of(String debugString) {
            return new Key<T>(debugString);
        }

        public static <T> Key<T> create(String debugString) {
            return new Key<T>(debugString);
        }
    }
}

