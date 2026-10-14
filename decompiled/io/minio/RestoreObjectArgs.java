/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectVersionArgs;
import io.minio.messages.RestoreRequest;
import java.util.Objects;

public class RestoreObjectArgs
extends ObjectVersionArgs {
    private RestoreRequest request;

    public RestoreRequest request() {
        return this.request;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestoreObjectArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RestoreObjectArgs that = (RestoreObjectArgs)o;
        return Objects.equals(this.request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.request);
    }

    public static final class Builder
    extends ObjectVersionArgs.Builder<Builder, RestoreObjectArgs> {
        private void validateRequest(RestoreRequest request) {
            this.validateNotNull(request, "request");
        }

        public Builder request(RestoreRequest request) {
            this.validateRequest(request);
            this.operations.add(args2 -> ((RestoreObjectArgs)args2).request = request);
            return this;
        }

        @Override
        protected void validate(RestoreObjectArgs args2) {
            super.validate(args2);
            this.validateRequest(args2.request());
        }
    }
}

