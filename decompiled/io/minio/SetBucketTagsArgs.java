/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import io.minio.messages.Tags;
import java.util.Map;
import java.util.Objects;

public class SetBucketTagsArgs
extends BucketArgs {
    private Tags tags;

    public Tags tags() {
        return this.tags;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetBucketTagsArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SetBucketTagsArgs that = (SetBucketTagsArgs)o;
        return Objects.equals(this.tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.tags);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, SetBucketTagsArgs> {
        private void validateTags(Tags tags) {
            this.validateNotNull(tags, "tags");
        }

        @Override
        protected void validate(SetBucketTagsArgs args2) {
            super.validate(args2);
            this.validateTags(args2.tags);
        }

        public Builder tags(Map<String, String> map) {
            this.validateNotNull(map, "map for tags");
            this.operations.add(args2 -> ((SetBucketTagsArgs)args2).tags = Tags.newBucketTags(map));
            return this;
        }

        public Builder tags(Tags tags) {
            this.validateTags(tags);
            this.operations.add(args2 -> ((SetBucketTagsArgs)args2).tags = tags);
            return this;
        }
    }
}

