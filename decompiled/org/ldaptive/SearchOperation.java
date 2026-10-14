/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import org.ldaptive.AbstractOperation;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapException;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterParser;
import org.ldaptive.handler.LdapEntryHandler;
import org.ldaptive.handler.SearchReferenceHandler;
import org.ldaptive.handler.SearchResultHandler;

public class SearchOperation
extends AbstractOperation<SearchRequest, SearchResponse> {
    private SearchRequest request;
    private FilterTemplate filterTemplate;
    private LdapEntryHandler[] entryHandlers;
    private SearchReferenceHandler[] referenceHandlers;
    private SearchResultHandler[] searchResultHandlers;

    public SearchOperation() {
        this.setRequest(new SearchRequest());
    }

    public SearchOperation(ConnectionFactory factory2) {
        super(factory2);
        this.setRequest(new SearchRequest());
    }

    public SearchOperation(ConnectionFactory factory2, SearchRequest req) {
        super(factory2);
        this.setRequest(req);
    }

    public SearchOperation(ConnectionFactory factory2, String baseDN) {
        super(factory2);
        this.setRequest(new SearchRequest(baseDN));
    }

    public SearchRequest getRequest() {
        return this.request;
    }

    public void setRequest(SearchRequest req) {
        this.request = req;
    }

    public FilterTemplate getTemplate() {
        return this.filterTemplate;
    }

    public void setTemplate(FilterTemplate template) {
        this.filterTemplate = template;
    }

    public LdapEntryHandler[] getEntryHandlers() {
        return this.entryHandlers;
    }

    public void setEntryHandlers(LdapEntryHandler ... handlers) {
        this.entryHandlers = handlers;
    }

    public SearchReferenceHandler[] getReferenceHandlers() {
        return this.referenceHandlers;
    }

    public void setReferenceHandlers(SearchReferenceHandler ... handlers) {
        this.referenceHandlers = handlers;
    }

    public SearchResultHandler[] getSearchResultHandlers() {
        return this.searchResultHandlers;
    }

    public void setSearchResultHandlers(SearchResultHandler ... handlers) {
        this.searchResultHandlers = handlers;
    }

    public SearchOperationHandle send(String filter2) throws LdapException {
        return this.send(null, FilterParser.parse(filter2), null, (LdapEntryHandler[])null);
    }

    public SearchOperationHandle send(FilterTemplate template) throws LdapException {
        return this.send(null, FilterParser.parse(template.format()), null, (LdapEntryHandler[])null);
    }

    public SearchOperationHandle send(Filter filter2) throws LdapException {
        return this.send(null, filter2, null, (LdapEntryHandler[])null);
    }

    public SearchOperationHandle send(String filter2, String ... returnAttributes) throws LdapException {
        return this.send(null, FilterParser.parse(filter2), returnAttributes, (LdapEntryHandler[])null);
    }

    public SearchOperationHandle send(FilterTemplate template, String ... returnAttributes) throws LdapException {
        return this.send(null, FilterParser.parse(template.format()), returnAttributes, (LdapEntryHandler[])null);
    }

    public SearchOperationHandle send(Filter filter2, String ... returnAttributes) throws LdapException {
        return this.send(null, filter2, returnAttributes, (LdapEntryHandler[])null);
    }

    public SearchOperationHandle send(String filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.send(null, FilterParser.parse(filter2), returnAttributes, handlers);
    }

    public SearchOperationHandle send(FilterTemplate template, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.send(null, FilterParser.parse(template.format()), returnAttributes, handlers);
    }

    public SearchOperationHandle send(Filter filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.send(null, filter2, returnAttributes, handlers);
    }

    public SearchOperationHandle send(String baseDN, String filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.send(baseDN, FilterParser.parse(filter2), returnAttributes, handlers);
    }

    public SearchOperationHandle send(String baseDN, FilterTemplate template, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.send(baseDN, FilterParser.parse(template.format()), returnAttributes, handlers);
    }

    public SearchOperationHandle send(String baseDN, Filter filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        Connection conn = this.getConnectionFactory().getConnection();
        try {
            conn.open();
        }
        catch (Exception e) {
            conn.close();
            throw e;
        }
        SearchRequest req = this.configureRequest(baseDN, filter2, returnAttributes);
        if (handlers != null) {
            return this.configureHandle(conn.operation(req)).onEntry(handlers).onComplete(conn::close).send();
        }
        return this.configureHandle(conn.operation(req)).onComplete(conn::close).send();
    }

    public SearchOperationHandle send(SearchRequest req) throws LdapException {
        Connection conn = this.getConnectionFactory().getConnection();
        try {
            conn.open();
        }
        catch (Exception e) {
            conn.close();
            throw e;
        }
        return this.configureHandle(conn.operation(this.configureRequest(req))).onComplete(conn::close).send();
    }

    public SearchOperationHandle send() throws LdapException {
        Connection conn = this.getConnectionFactory().getConnection();
        try {
            conn.open();
        }
        catch (Exception e) {
            conn.close();
            throw e;
        }
        SearchRequest req = this.configureRequest(null, null, null);
        return this.configureHandle(conn.operation(req)).onComplete(conn::close).send();
    }

    public static SearchOperationHandle send(ConnectionFactory factory2, SearchRequest req) throws LdapException {
        Connection conn = factory2.getConnection();
        try {
            conn.open();
        }
        catch (Exception e) {
            conn.close();
            throw e;
        }
        return conn.operation(req).onComplete(conn::close).send();
    }

    @Override
    public SearchResponse execute(String filter2) throws LdapException {
        return this.execute(FilterParser.parse(filter2), null, (LdapEntryHandler[])null);
    }

    @Override
    public SearchResponse execute(FilterTemplate template) throws LdapException {
        return this.execute(FilterParser.parse(template.format()), null, (LdapEntryHandler[])null);
    }

    @Override
    public SearchResponse execute(Filter filter2) throws LdapException {
        return this.execute(null, filter2, null, (LdapEntryHandler[])null);
    }

    public SearchResponse execute(String filter2, String ... returnAttributes) throws LdapException {
        return this.execute(FilterParser.parse(filter2), returnAttributes, (LdapEntryHandler[])null);
    }

    public SearchResponse execute(FilterTemplate template, String ... returnAttributes) throws LdapException {
        return this.execute(FilterParser.parse(template.format()), returnAttributes, (LdapEntryHandler[])null);
    }

    public SearchResponse execute(Filter filter2, String ... returnAttributes) throws LdapException {
        return this.execute(null, filter2, returnAttributes, (LdapEntryHandler[])null);
    }

    public SearchResponse execute(String filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.execute(FilterParser.parse(filter2), returnAttributes, handlers);
    }

    public SearchResponse execute(FilterTemplate template, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.execute(FilterParser.parse(template.format()), returnAttributes, handlers);
    }

    public SearchResponse execute(Filter filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.execute(null, filter2, returnAttributes, handlers);
    }

    public SearchResponse execute(String baseDN, String filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.execute(baseDN, FilterParser.parse(filter2), returnAttributes, handlers);
    }

    public SearchResponse execute(String baseDN, FilterTemplate template, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        return this.execute(baseDN, FilterParser.parse(template.format()), returnAttributes, handlers);
    }

    public SearchResponse execute(String baseDN, Filter filter2, String[] returnAttributes, LdapEntryHandler ... handlers) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            SearchRequest req = this.configureRequest(baseDN, filter2, returnAttributes);
            if (handlers != null) {
                SearchResponse searchResponse = this.configureHandle(conn.operation(req)).onEntry(handlers).execute();
                return searchResponse;
            }
            SearchResponse searchResponse = this.configureHandle(conn.operation(req)).execute();
            return searchResponse;
        }
    }

    @Override
    public SearchResponse execute(SearchRequest req) throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            SearchResponse searchResponse = this.configureHandle(conn.operation(this.configureRequest(req))).execute();
            return searchResponse;
        }
    }

    public SearchResponse execute() throws LdapException {
        try (Connection conn = this.getConnectionFactory().getConnection();){
            conn.open();
            SearchRequest req = this.configureRequest(null, null, null);
            SearchResponse searchResponse = this.configureHandle(conn.operation(req)).execute();
            return searchResponse;
        }
    }

    public static SearchResponse execute(ConnectionFactory factory2, SearchRequest req) throws LdapException {
        try (Connection conn = factory2.getConnection();){
            conn.open();
            SearchResponse searchResponse = conn.operation(req).execute();
            return searchResponse;
        }
    }

    private SearchRequest configureRequest(String baseDN, Filter filter2, String[] returnAttributes) {
        SearchRequest.Builder builder = SearchRequest.builder(SearchRequest.copy(this.getRequest()));
        if (baseDN != null) {
            builder.dn(baseDN);
        }
        if (filter2 != null) {
            builder.filter(filter2);
        } else if (this.getTemplate() != null) {
            builder.filter(this.getTemplate());
        }
        if (returnAttributes != null) {
            builder.returnAttributes(returnAttributes);
        }
        return this.configureRequest((SearchRequest)builder.build());
    }

    protected SearchOperationHandle configureHandle(SearchOperationHandle handle2) {
        return handle2.onEntry(this.getEntryHandlers()).onReference(this.getReferenceHandlers()).onResult(this.getResultHandlers()).onControl(this.getControlHandlers()).onReferral(this.getReferralHandlers()).onIntermediate(this.getIntermediateResponseHandlers()).onException(this.getExceptionHandler()).throwIf(this.getThrowCondition()).onUnsolicitedNotification(this.getUnsolicitedNotificationHandlers()).onSearchResult(this.getSearchResultHandlers());
    }

    public static SearchOperation copy(SearchOperation operation) {
        SearchOperation op = new SearchOperation();
        op.setRequestHandlers(operation.getRequestHandlers());
        op.setResultHandlers(operation.getResultHandlers());
        op.setControlHandlers(operation.getControlHandlers());
        op.setReferralHandlers(operation.getReferralHandlers());
        op.setIntermediateResponseHandlers(operation.getIntermediateResponseHandlers());
        op.setExceptionHandler(operation.getExceptionHandler());
        op.setThrowCondition(operation.getThrowCondition());
        op.setUnsolicitedNotificationHandlers(operation.getUnsolicitedNotificationHandlers());
        op.setEntryHandlers(operation.getEntryHandlers());
        op.setReferenceHandlers(operation.getReferenceHandlers());
        op.setSearchResultHandlers(operation.getSearchResultHandlers());
        op.setConnectionFactory(operation.getConnectionFactory());
        op.setRequest(operation.getRequest());
        op.setTemplate(operation.getTemplate());
        return op;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "request=" + this.request + ", " + "template=" + this.filterTemplate + ", " + "entryHandlers=" + Arrays.toString(this.entryHandlers) + ", " + "referenceHandlers=" + Arrays.toString(this.referenceHandlers) + ", " + "searchResultHandlers=" + Arrays.toString(this.searchResultHandlers);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractOperation.AbstractBuilder<Builder, SearchOperation> {
        protected Builder() {
            super(new SearchOperation());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder request(SearchRequest request) {
            ((SearchOperation)this.object).setRequest(request);
            return this.self();
        }

        public Builder template(FilterTemplate template) {
            ((SearchOperation)this.object).setTemplate(template);
            return this.self();
        }

        public Builder onEntry(LdapEntryHandler ... handlers) {
            ((SearchOperation)this.object).setEntryHandlers(handlers);
            return this.self();
        }

        public Builder onReference(SearchReferenceHandler ... handlers) {
            ((SearchOperation)this.object).setReferenceHandlers(handlers);
            return this.self();
        }

        public Builder onSearchResult(SearchResultHandler ... handlers) {
            ((SearchOperation)this.object).setSearchResultHandlers(handlers);
            return this.self();
        }
    }
}

