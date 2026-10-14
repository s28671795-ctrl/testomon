/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import java.util.Arrays;
import java.util.Objects;

public class ListenBucketNotificationArgs
extends BucketArgs {
    private String prefix;
    private String suffix;
    private String[] events = null;

    public String prefix() {
        return this.prefix;
    }

    public String suffix() {
        return this.suffix;
    }

    public String[] events() {
        return Arrays.copyOf(this.events, this.events.length);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListenBucketNotificationArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ListenBucketNotificationArgs that = (ListenBucketNotificationArgs)o;
        return Objects.equals(this.prefix, that.prefix) && Objects.equals(this.suffix, that.suffix) && Arrays.equals(this.events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.prefix, this.suffix, this.events);
    }

    static /* synthetic */ String[] access$002(ListenBucketNotificationArgs x0, String[] x1) {
        x0.events = x1;
        return x1;
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, ListenBucketNotificationArgs> {
        private void validateEvents(String[] events) {
            this.validateNotNull(events, "events");
        }

        @Override
        protected void validate(ListenBucketNotificationArgs args2) {
            if (args2.bucketName != null) {
                super.validate(args2);
            }
            this.validateEvents(args2.events);
        }

        public Builder prefix(String prefix) {
            this.operations.add(args2 -> ((ListenBucketNotificationArgs)args2).prefix = prefix);
            return this;
        }

        public Builder suffix(String suffix) {
            this.operations.add(args2 -> ((ListenBucketNotificationArgs)args2).suffix = suffix);
            return this;
        }

        public Builder events(String[] events) {
            this.validateEvents(events);
            String[] eventsCopy = Arrays.copyOf(events, events.length);
            this.operations.add(args2 -> ListenBucketNotificationArgs.access$002(args2, eventsCopy));
            return this;
        }
    }
}

