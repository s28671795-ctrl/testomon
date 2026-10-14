/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ObjectVersionArgs;
import io.minio.http.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GetPresignedObjectUrlArgs
extends ObjectVersionArgs {
    public static final int DEFAULT_EXPIRY_TIME = (int)TimeUnit.DAYS.toSeconds(7L);
    private Method method;
    private int expiry = DEFAULT_EXPIRY_TIME;

    public Method method() {
        return this.method;
    }

    public int expiry() {
        return this.expiry;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetPresignedObjectUrlArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        GetPresignedObjectUrlArgs that = (GetPresignedObjectUrlArgs)o;
        return this.expiry == that.expiry && this.method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{super.hashCode(), this.method, this.expiry});
    }

    public static final class Builder
    extends ObjectVersionArgs.Builder<Builder, GetPresignedObjectUrlArgs> {
        private void validateMethod(Method method2) {
            this.validateNotNull((Object)method2, "method");
        }

        private void validateExpiry(int expiry) {
            if (expiry < 1 || expiry > DEFAULT_EXPIRY_TIME) {
                throw new IllegalArgumentException("expiry must be minimum 1 second to maximum " + TimeUnit.SECONDS.toDays(DEFAULT_EXPIRY_TIME) + " days");
            }
        }

        public Builder method(Method method2) {
            this.validateMethod(method2);
            this.operations.add(args2 -> ((GetPresignedObjectUrlArgs)args2).method = method2);
            return this;
        }

        public Builder expiry(int expiry) {
            this.validateExpiry(expiry);
            this.operations.add(args2 -> ((GetPresignedObjectUrlArgs)args2).expiry = expiry);
            return this;
        }

        public Builder expiry(int duration, TimeUnit unit) {
            return this.expiry((int)unit.toSeconds(duration));
        }

        @Override
        protected void validate(GetPresignedObjectUrlArgs args2) {
            super.validate(args2);
            this.validateMethod(args2.method);
        }
    }
}

