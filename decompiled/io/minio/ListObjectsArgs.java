/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BucketArgs;
import java.util.Objects;

public class ListObjectsArgs
extends BucketArgs {
    private String delimiter = "";
    private boolean useUrlEncodingType = true;
    private String keyMarker;
    private int maxKeys = 1000;
    private String prefix = "";
    private String continuationToken;
    private boolean fetchOwner;
    private String versionIdMarker;
    private boolean includeUserMetadata;
    private boolean recursive;
    private boolean useApiVersion1;
    private boolean includeVersions;

    public String delimiter() {
        if (this.recursive) {
            return "";
        }
        return this.delimiter.isEmpty() ? "/" : this.delimiter;
    }

    public boolean useUrlEncodingType() {
        return this.useUrlEncodingType;
    }

    public String keyMarker() {
        return this.keyMarker;
    }

    public String marker() {
        return this.keyMarker;
    }

    public String startAfter() {
        return this.keyMarker;
    }

    public int maxKeys() {
        return this.maxKeys;
    }

    public String prefix() {
        return this.prefix;
    }

    public String continuationToken() {
        return this.continuationToken;
    }

    public boolean fetchOwner() {
        return this.fetchOwner;
    }

    public String versionIdMarker() {
        return this.versionIdMarker;
    }

    public boolean includeUserMetadata() {
        return this.includeUserMetadata;
    }

    public boolean recursive() {
        return this.recursive;
    }

    public boolean useApiVersion1() {
        return this.useApiVersion1;
    }

    public boolean includeVersions() {
        return this.includeVersions;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListObjectsArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ListObjectsArgs that = (ListObjectsArgs)o;
        return this.useUrlEncodingType == that.useUrlEncodingType && this.maxKeys == that.maxKeys && this.fetchOwner == that.fetchOwner && this.includeUserMetadata == that.includeUserMetadata && this.recursive == that.recursive && this.useApiVersion1 == that.useApiVersion1 && this.includeVersions == that.includeVersions && Objects.equals(this.delimiter, that.delimiter) && Objects.equals(this.keyMarker, that.keyMarker) && Objects.equals(this.prefix, that.prefix) && Objects.equals(this.continuationToken, that.continuationToken) && Objects.equals(this.versionIdMarker, that.versionIdMarker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.delimiter, this.useUrlEncodingType, this.keyMarker, this.maxKeys, this.prefix, this.continuationToken, this.fetchOwner, this.versionIdMarker, this.includeUserMetadata, this.recursive, this.useApiVersion1, this.includeVersions);
    }

    public static final class Builder
    extends BucketArgs.Builder<Builder, ListObjectsArgs> {
        @Override
        protected void validate(ListObjectsArgs args2) {
            super.validate(args2);
            if ((args2.useApiVersion1() || args2.includeVersions()) && (args2.continuationToken() != null || args2.fetchOwner() || args2.includeUserMetadata())) {
                throw new IllegalArgumentException("continuation token/fetch owner/include metadata are supported only for list objects version 2");
            }
            if (args2.versionIdMarker != null && args2.useApiVersion1()) {
                throw new IllegalArgumentException("version ID marker is not supported for list objects version 1");
            }
        }

        public Builder delimiter(String delimiter) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).delimiter = delimiter == null ? "" : delimiter);
            return this;
        }

        public Builder useUrlEncodingType(boolean flag) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).useUrlEncodingType = flag);
            return this;
        }

        public Builder keyMarker(String keyMarker) {
            this.validateNullOrNotEmptyString(keyMarker, "key marker");
            this.operations.add(args2 -> ((ListObjectsArgs)args2).keyMarker = keyMarker);
            return this;
        }

        public Builder marker(String marker) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).keyMarker = marker);
            return this;
        }

        public Builder startAfter(String startAfter) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).keyMarker = startAfter);
            return this;
        }

        public Builder maxKeys(int maxKeys) {
            if (maxKeys < 1 || maxKeys > 1000) {
                throw new IllegalArgumentException("max keys must be between 1 and 1000");
            }
            this.operations.add(args2 -> ((ListObjectsArgs)args2).maxKeys = maxKeys);
            return this;
        }

        public Builder prefix(String prefix) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).prefix = prefix == null ? "" : prefix);
            return this;
        }

        public Builder continuationToken(String continuationToken) {
            this.validateNullOrNotEmptyString(continuationToken, "continuation token");
            this.operations.add(args2 -> ((ListObjectsArgs)args2).continuationToken = continuationToken);
            return this;
        }

        public Builder fetchOwner(boolean fetchOwner) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).fetchOwner = fetchOwner);
            return this;
        }

        public Builder versionIdMarker(String versionIdMarker) {
            this.validateNullOrNotEmptyString(versionIdMarker, "version ID marker");
            this.operations.add(args2 -> ((ListObjectsArgs)args2).versionIdMarker = versionIdMarker);
            return this;
        }

        public Builder includeUserMetadata(boolean includeUserMetadata) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).includeUserMetadata = includeUserMetadata);
            return this;
        }

        public Builder recursive(boolean recursive) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).recursive = recursive);
            return this;
        }

        public Builder useApiVersion1(boolean useApiVersion1) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).useApiVersion1 = useApiVersion1);
            return this;
        }

        public Builder includeVersions(boolean includeVersions) {
            this.operations.add(args2 -> ((ListObjectsArgs)args2).includeVersions = includeVersions);
            return this;
        }
    }
}

