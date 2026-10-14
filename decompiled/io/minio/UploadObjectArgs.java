/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.PutObjectBaseArgs;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Objects;

public class UploadObjectArgs
extends PutObjectBaseArgs {
    private String filename;

    public String filename() {
        return this.filename;
    }

    @Override
    public String contentType() throws IOException {
        String contentType = super.contentType();
        if (contentType != null) {
            return contentType;
        }
        contentType = Files.probeContentType(Paths.get(this.filename, new String[0]));
        return contentType != null && !contentType.isEmpty() ? contentType : "application/octet-stream";
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        UploadObjectArgs that = (UploadObjectArgs)o;
        return Objects.equals(this.filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.filename);
    }

    public static final class Builder
    extends PutObjectBaseArgs.Builder<Builder, UploadObjectArgs> {
        @Override
        protected void validate(UploadObjectArgs args2) {
            super.validate(args2);
            this.validateFilename(args2.filename);
        }

        private void validateFilename(String filename) {
            this.validateNotEmptyString(filename, "filename");
            if (!Files.isRegularFile(Paths.get(filename, new String[0]), new LinkOption[0])) {
                throw new IllegalArgumentException(filename + " not a regular file");
            }
        }

        public Builder filename(String filename, long partSize) throws IOException {
            this.validateFilename(filename);
            long objectSize = Files.size(Paths.get(filename, new String[0]));
            long[] partinfo = this.getPartInfo(objectSize, partSize);
            long pSize = partinfo[0];
            int partCount = (int)partinfo[1];
            this.operations.add(args2 -> ((UploadObjectArgs)args2).filename = filename);
            this.operations.add(args2 -> {
                args2.objectSize = objectSize;
            });
            this.operations.add(args2 -> {
                args2.partSize = pSize;
            });
            this.operations.add(args2 -> {
                args2.partCount = partCount;
            });
            return this;
        }

        public Builder filename(String filename) throws IOException {
            return this.filename(filename, 0L);
        }

        public Builder contentType(String contentType) {
            this.validateNotEmptyString(contentType, "content type");
            this.operations.add(args2 -> {
                args2.contentType = contentType;
            });
            return this;
        }
    }
}

