/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control.util;

import java.util.EnumSet;
import java.util.function.Consumer;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.Result;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.control.PersistentSearchChangeType;
import org.ldaptive.control.PersistentSearchRequestControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistentSearchClient {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConnectionFactory factory;
    private final EnumSet<PersistentSearchChangeType> changeTypes;
    private final boolean changesOnly;
    private final boolean returnEcs;
    private SearchOperationHandle handle;
    private Consumer<LdapEntry> onEntry;
    private Consumer<Result> onResult;
    private Consumer<Exception> onException;

    public PersistentSearchClient(ConnectionFactory cf, EnumSet<PersistentSearchChangeType> types, boolean co, boolean re) {
        this.factory = cf;
        this.changeTypes = types;
        this.changesOnly = co;
        this.returnEcs = re;
    }

    public void setOnEntry(Consumer<LdapEntry> consumer) {
        this.onEntry = consumer;
    }

    public void setOnResult(Consumer<Result> consumer) {
        this.onResult = consumer;
    }

    public void setOnException(Consumer<Exception> consumer) {
        this.onException = consumer;
    }

    public SearchOperationHandle send(SearchRequest request) throws LdapException {
        request.setControls(new PersistentSearchRequestControl(this.changeTypes, this.changesOnly, this.returnEcs, true));
        SearchOperation search = new SearchOperation(this.factory, request);
        search.setResultHandlers(result -> {
            this.logger.debug("Received {}", result);
            try {
                this.onResult.accept((Result)result);
            }
            catch (Exception e) {
                this.logger.warn("Unable to process result {}", result);
                try {
                    this.onException.accept(e);
                }
                catch (Exception ex) {
                    this.logger.warn("Unable to process result exception", ex);
                }
            }
        });
        search.setExceptionHandler(e -> {
            this.logger.debug("Received exception:", (Throwable)e);
            try {
                this.onException.accept((Exception)e);
            }
            catch (Exception ex) {
                this.logger.warn("Unable to process exception", ex);
            }
        });
        search.setEntryHandlers(entry -> {
            this.logger.debug("Received {}", entry);
            try {
                this.onEntry.accept((LdapEntry)entry);
            }
            catch (Exception e) {
                this.logger.warn("Unable to process entry {}", entry);
                try {
                    this.onException.accept(e);
                }
                catch (Exception ex) {
                    this.logger.warn("Unable to process entry exception", ex);
                }
            }
            return null;
        });
        this.handle = search.send();
        return this.handle;
    }

    public void abandon() {
        this.handle.abandon();
    }
}

