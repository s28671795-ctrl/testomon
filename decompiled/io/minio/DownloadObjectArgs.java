/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectReadArgs;
import java.util.Objects;

public class DownloadObjectArgs
extends ObjectReadArgs {
    private String filename;
    private boolean overwrite;

    public String filename() {
        return this.filename;
    }

    public boolean overwrite() {
        return this.overwrite;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DownloadObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DownloadObjectArgs that = (DownloadObjectArgs)o;
        if (!Objects.equals(this.filename, that.filename)) {
            return false;
        }
        return this.overwrite == that.overwrite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.filename, this.overwrite);
    }

    public static final class Builder
    extends ObjectReadArgs.Builder<Builder, DownloadObjectArgs> {
        private void validateFilename(String filename) {
            this.validateNotEmptyString(filename, "filename");
        }

        public Builder filename(String filename) {
            this.validateFilename(filename);
            this.operations.add(args2 -> ((DownloadObjectArgs)args2).filename = filename);
            return this;
        }

        public Builder overwrite(boolean flag) {
            this.operations.add(args2 -> ((DownloadObjectArgs)args2).overwrite = flag);
            return this;
        }
    }
}

