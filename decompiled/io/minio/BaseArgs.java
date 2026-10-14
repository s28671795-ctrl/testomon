/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class BaseArgs {
    protected Multimap<String, String> extraHeaders = Multimaps.unmodifiableMultimap(HashMultimap.create());
    protected Multimap<String, String> extraQueryParams = Multimaps.unmodifiableMultimap(HashMultimap.create());

    public Multimap<String, String> extraHeaders() {
        return this.extraHeaders;
    }

    public Multimap<String, String> extraQueryParams() {
        return this.extraQueryParams;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseArgs)) {
            return false;
        }
        BaseArgs baseArgs = (BaseArgs)o;
        return Objects.equals(this.extraHeaders, baseArgs.extraHeaders) && Objects.equals(this.extraQueryParams, baseArgs.extraQueryParams);
    }

    public int hashCode() {
        return Objects.hash(this.extraHeaders, this.extraQueryParams);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends BaseArgs> {
        protected List<Consumer<A>> operations = new ArrayList<Consumer<A>>();

        protected abstract void validate(A var1);

        protected void validateNotNull(Object arg, String argName) {
            if (arg == null) {
                throw new IllegalArgumentException(argName + " must not be null.");
            }
        }

        protected void validateNotEmptyString(String arg, String argName) {
            this.validateNotNull(arg, argName);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException(argName + " must be a non-empty string.");
            }
        }

        protected void validateNullOrNotEmptyString(String arg, String argName) {
            if (arg != null && arg.isEmpty()) {
                throw new IllegalArgumentException(argName + " must be a non-empty string.");
            }
        }

        protected void validateNullOrPositive(Number arg, String argName) {
            if (arg != null && arg.longValue() < 0L) {
                throw new IllegalArgumentException(argName + " cannot be non-negative.");
            }
        }

        protected Multimap<String, String> copyMultimap(Multimap<String, String> multimap) {
            HashMultimap<String, String> multimapCopy = HashMultimap.create();
            if (multimap != null) {
                multimapCopy.putAll(multimap);
            }
            return Multimaps.unmodifiableMultimap(multimapCopy);
        }

        protected Multimap<String, String> toMultimap(Map<String, String> map) {
            HashMultimap<String, String> multimap = HashMultimap.create();
            if (map != null) {
                multimap.putAll(Multimaps.forMap(map));
            }
            return Multimaps.unmodifiableMultimap(multimap);
        }

        public B extraHeaders(Multimap<String, String> headers) {
            Multimap<String, String> extraHeaders = this.copyMultimap(headers);
            this.operations.add(args2 -> {
                args2.extraHeaders = extraHeaders;
            });
            return (B)this;
        }

        public B extraQueryParams(Multimap<String, String> queryParams2) {
            Multimap<String, String> extraQueryParams = this.copyMultimap(queryParams2);
            this.operations.add(args2 -> {
                args2.extraQueryParams = extraQueryParams;
            });
            return (B)this;
        }

        public B extraHeaders(Map<String, String> headers) {
            Multimap<String, String> extraHeaders = this.toMultimap(headers);
            this.operations.add(args2 -> {
                args2.extraHeaders = extraHeaders;
            });
            return (B)this;
        }

        public B extraQueryParams(Map<String, String> queryParams2) {
            Multimap<String, String> extraQueryParams = this.toMultimap(queryParams2);
            this.operations.add(args2 -> {
                args2.extraQueryParams = extraQueryParams;
            });
            return (B)this;
        }

        private A newInstance() {
            try {
                for (Constructor<?> constructor : this.getClass().getEnclosingClass().getDeclaredConstructors()) {
                    if (constructor.getParameterCount() != 0) continue;
                    return (A)((BaseArgs)constructor.newInstance(new Object[0]));
                }
                throw new RuntimeException(this.getClass().getEnclosingClass() + " must have no argument constructor");
            }
            catch (IllegalAccessException | InstantiationException | SecurityException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public A build() throws IllegalArgumentException {
            Object args2 = this.newInstance();
            this.operations.forEach(operation -> operation.accept(args2));
            this.validate(args2);
            return args2;
        }
    }
}

