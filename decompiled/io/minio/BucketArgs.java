/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.BaseArgs;
import io.minio.http.HttpUtils;
import io.minio.org.apache.commons.validator.routines.InetAddressValidator;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class BucketArgs
extends BaseArgs {
    protected String bucketName;
    protected String region;

    public String bucket() {
        return this.bucketName;
    }

    public String region() {
        return this.region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BucketArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BucketArgs that = (BucketArgs)o;
        return Objects.equals(this.bucketName, that.bucketName) && Objects.equals(this.region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.bucketName, this.region);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends BucketArgs>
    extends BaseArgs.Builder<B, A> {
        private static final Pattern BUCKET_NAME_REGEX = Pattern.compile("^[a-z0-9][a-z0-9\\.\\-]{1,61}[a-z0-9]$");
        protected boolean skipValidation = false;

        protected void validateBucketName(String name) {
            this.validateNotNull(name, "bucket name");
            if (this.skipValidation) {
                return;
            }
            if (!BUCKET_NAME_REGEX.matcher(name).find()) {
                throw new IllegalArgumentException("bucket name '" + name + "' does not follow Amazon S3 standards. For more information refer https://docs.aws.amazon.com/AmazonS3/latest/userguide/bucketnamingrules.html");
            }
            if (InetAddressValidator.getInstance().isValidInet4Address(name)) {
                throw new IllegalArgumentException("bucket name '" + name + "' must not be formatted as an IP address");
            }
            if (name.contains("..") || name.contains(".-") || name.contains("-.")) {
                throw new IllegalArgumentException("bucket name '" + name + "' cannot contain successive characters '..', '.-' and '-.'");
            }
        }

        private void validateRegion(String region) {
            if (!this.skipValidation && region != null && !HttpUtils.REGION_REGEX.matcher(region).find()) {
                throw new IllegalArgumentException("invalid region " + region);
            }
        }

        @Override
        protected void validate(A args2) {
            this.validateBucketName(((BucketArgs)args2).bucketName);
        }

        public B bucket(String name) {
            this.validateBucketName(name);
            this.operations.add(args2 -> {
                args2.bucketName = name;
            });
            return (B)this;
        }

        public B skipValidation(boolean skipValidation) {
            this.skipValidation = skipValidation;
            return (B)this;
        }

        public B region(String region) {
            this.validateRegion(region);
            this.operations.add(args2 -> {
                args2.region = region;
            });
            return (B)this;
        }
    }
}

