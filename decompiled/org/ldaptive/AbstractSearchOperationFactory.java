/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.ConnectionFactory;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.SearchOperation;
import org.ldaptive.handler.ExceptionHandler;
import org.ldaptive.handler.LdapEntryHandler;
import org.ldaptive.handler.ResultHandler;
import org.ldaptive.handler.SearchResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSearchOperationFactory
implements ConnectionFactoryManager {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ConnectionFactory factory;
    private LdapEntryHandler[] entryHandlers;
    private ExceptionHandler exceptionHandler;
    private ResultHandler[] resultHandlers;
    private SearchResultHandler[] searchResultHandlers;

    @Override
    public ConnectionFactory getConnectionFactory() {
        return this.factory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory cf) {
        this.factory = cf;
    }

    public LdapEntryHandler[] getEntryHandlers() {
        return this.entryHandlers;
    }

    public void setEntryHandlers(LdapEntryHandler ... handlers) {
        this.entryHandlers = handlers;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler handler) {
        this.exceptionHandler = handler;
    }

    public ResultHandler[] getResultHandlers() {
        return this.resultHandlers;
    }

    public void setResultHandlers(ResultHandler ... handlers) {
        this.resultHandlers = handlers;
    }

    public SearchResultHandler[] getSearchResultHandlers() {
        return this.searchResultHandlers;
    }

    public void setSearchResultHandlers(SearchResultHandler ... handlers) {
        this.searchResultHandlers = handlers;
    }

    protected SearchOperation createSearchOperation() {
        return this.createSearchOperation(this.factory);
    }

    protected SearchOperation createSearchOperation(ConnectionFactory cf) {
        SearchOperation op = new SearchOperation(cf);
        if (this.entryHandlers != null) {
            op.setEntryHandlers(this.entryHandlers);
        }
        if (this.exceptionHandler != null) {
            op.setExceptionHandler(this.exceptionHandler);
        }
        if (this.resultHandlers != null) {
            op.setResultHandlers(this.resultHandlers);
        }
        if (this.searchResultHandlers != null) {
            op.setSearchResultHandlers(this.searchResultHandlers);
        }
        return op;
    }
}

