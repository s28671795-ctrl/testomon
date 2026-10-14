/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.util.function.Consumer;
import org.ldaptive.AbstractConnectionValidator;
import org.ldaptive.CompareOperationHandle;
import org.ldaptive.CompareRequest;
import org.ldaptive.Connection;

public class CompareConnectionValidator
extends AbstractConnectionValidator {
    private CompareRequest compareRequest;

    public CompareConnectionValidator() {
        this((CompareRequest)CompareRequest.builder().dn("").name("objectClass").value("top").build());
    }

    public CompareConnectionValidator(CompareRequest cr) {
        this(DEFAULT_VALIDATE_PERIOD, DEFAULT_VALIDATE_TIMEOUT, cr);
    }

    public CompareConnectionValidator(Duration period, Duration timeout2, CompareRequest request) {
        this.setValidatePeriod(period);
        this.setValidateTimeout(timeout2);
        this.setCompareRequest(request);
    }

    public CompareRequest getCompareRequest() {
        return this.compareRequest;
    }

    public void setCompareRequest(CompareRequest cr) {
        this.compareRequest = cr;
    }

    @Override
    public void applyAsync(Connection conn, Consumer<Boolean> function) {
        if (conn == null) {
            function.accept(false);
        } else {
            CompareOperationHandle h2 = conn.operation(this.compareRequest);
            h2.onResult(r -> function.accept(r.getResultCode() != null));
            h2.onException(e -> {
                this.logger.debug("Connection validator failed for {}", (Object)conn, e);
                function.accept(false);
            });
            h2.send();
        }
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "validatePeriod=" + this.getValidatePeriod() + ", " + "validateTimeout=" + this.getValidateTimeout() + ", " + "compareRequest=" + this.compareRequest + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractConnectionValidator.AbstractBuilder<Builder, CompareConnectionValidator> {
        protected Builder() {
            super(new CompareConnectionValidator());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder request(CompareRequest request) {
            ((CompareConnectionValidator)this.object).setCompareRequest(request);
            return this.self();
        }
    }
}

