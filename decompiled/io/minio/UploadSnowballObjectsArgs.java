/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectWriteArgs;
import io.minio.SnowballObject;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

public class UploadSnowballObjectsArgs
extends ObjectWriteArgs {
    private static final Random random = new Random(new SecureRandom().nextLong());
    private Iterable<SnowballObject> objects;
    private String stagingFilename;
    private boolean compression;

    public Iterable<SnowballObject> objects() {
        return this.objects;
    }

    public String stagingFilename() {
        return this.stagingFilename;
    }

    public boolean compression() {
        return this.compression;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadSnowballObjectsArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        UploadSnowballObjectsArgs that = (UploadSnowballObjectsArgs)o;
        return Objects.equals(this.objects, that.objects) && Objects.equals(this.stagingFilename, that.stagingFilename) && this.compression == that.compression;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.objects, this.stagingFilename, this.compression);
    }

    public static final class Builder
    extends ObjectWriteArgs.Builder<Builder, UploadSnowballObjectsArgs> {
        private void validateObjects(Iterable<SnowballObject> objects) {
            this.validateNotNull(objects, "objects");
        }

        @Override
        protected void validate(UploadSnowballObjectsArgs args2) {
            args2.objectName = "snowball." + random.nextLong() + ".tar";
            this.validateObjects(args2.objects);
            super.validate(args2);
        }

        public Builder objects(Iterable<SnowballObject> objects) {
            this.validateObjects(objects);
            this.operations.add(args2 -> ((UploadSnowballObjectsArgs)args2).objects = objects);
            return this;
        }

        public Builder stagingFilename(String stagingFilename) {
            if (stagingFilename != null && stagingFilename.isEmpty()) {
                throw new IllegalArgumentException("staging filename must not be empty");
            }
            this.operations.add(args2 -> ((UploadSnowballObjectsArgs)args2).stagingFilename = stagingFilename);
            return this;
        }

        public Builder compression(boolean compression) {
            this.operations.add(args2 -> ((UploadSnowballObjectsArgs)args2).compression = compression);
            return this;
        }
    }
}

