/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.util.function.Consumer;
import org.ldaptive.AbstractConnectionValidator;
import org.ldaptive.Connection;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;

public class SearchConnectionValidator
extends AbstractConnectionValidator {
    private SearchRequest searchRequest;

    public SearchConnectionValidator() {
        this(SearchRequest.objectScopeSearchRequest("", ReturnAttributes.NONE.value()));
    }

    public SearchConnectionValidator(SearchRequest sr) {
        this(DEFAULT_VALIDATE_PERIOD, DEFAULT_VALIDATE_TIMEOUT, sr);
    }

    public SearchConnectionValidator(Duration period, Duration timeout2, SearchRequest request) {
        this.setValidatePeriod(period);
        this.setValidateTimeout(timeout2);
        this.setSearchRequest(request);
    }

    public SearchRequest getSearchRequest() {
        return this.searchRequest;
    }

    public void setSearchRequest(SearchRequest sr) {
        this.searchRequest = sr;
    }

    @Override
    public void applyAsync(Connection conn, Consumer<Boolean> function) {
        if (conn == null) {
            function.accept(false);
        } else {
            SearchOperationHandle h2 = conn.operation(this.searchRequest);
            h2.onResult(r -> function.accept(r.getResultCode() != null));
            h2.onException(e -> {
                this.logger.debug("Connection validator failed for {}", (Object)conn, e);
                function.accept(false);
            });
            h2.send();
        }
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "validatePeriod=" + this.getValidatePeriod() + ", " + "validateTimeout=" + this.getValidateTimeout() + ", " + "searchRequest=" + this.searchRequest + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractConnectionValidator.AbstractBuilder<Builder, SearchConnectionValidator> {
        protected Builder() {
            super(new SearchConnectionValidator());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder request(SearchRequest request) {
            ((SearchConnectionValidator)this.object).setSearchRequest(request);
            return this.self();
        }
    }
}

