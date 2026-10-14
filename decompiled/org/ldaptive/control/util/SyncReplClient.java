/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control.util;

import java.util.function.Consumer;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.Result;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.control.SyncDoneControl;
import org.ldaptive.control.SyncRequestControl;
import org.ldaptive.control.SyncStateControl;
import org.ldaptive.control.util.CookieManager;
import org.ldaptive.control.util.DefaultCookieManager;
import org.ldaptive.extended.ExtendedOperationHandle;
import org.ldaptive.extended.SyncInfoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncReplClient {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConnectionFactory factory;
    private final boolean refreshAndPersist;
    private SearchOperationHandle handle;
    private Consumer<LdapEntry> onEntry;
    private Consumer<Result> onResult;
    private Consumer<SyncInfoMessage> onMessage;
    private Consumer<Exception> onException;
    private boolean receivedResult;

    public SyncReplClient(ConnectionFactory cf, boolean persist) {
        this.factory = cf;
        this.refreshAndPersist = persist;
    }

    public ConnectionFactory getConnectionFactory() {
        return this.factory;
    }

    public void setOnEntry(Consumer<LdapEntry> consumer) {
        this.onEntry = consumer;
    }

    public void setOnResult(Consumer<Result> consumer) {
        this.onResult = consumer;
    }

    public void setOnMessage(Consumer<SyncInfoMessage> consumer) {
        this.onMessage = consumer;
    }

    public void setOnException(Consumer<Exception> consumer) {
        this.onException = consumer;
    }

    public SearchOperationHandle send(SearchRequest request) throws LdapException {
        return this.send(request, new DefaultCookieManager());
    }

    public SearchOperationHandle send(SearchRequest request, CookieManager manager) throws LdapException {
        request.setControls(new SyncRequestControl(this.refreshAndPersist ? SyncRequestControl.Mode.REFRESH_AND_PERSIST : SyncRequestControl.Mode.REFRESH_ONLY, manager.readCookie(), true));
        SearchOperation search = new SearchOperation(this.factory, request);
        search.setResultHandlers(result -> {
            SyncDoneControl syncDoneControl;
            byte[] cookie;
            this.logger.debug("Received {}", result);
            this.receivedResult = true;
            if (result.getControl("1.3.6.1.4.1.4203.1.9.1.3") != null && (cookie = (syncDoneControl = (SyncDoneControl)result.getControl("1.3.6.1.4.1.4203.1.9.1.3")).getCookie()) != null) {
                try {
                    manager.writeCookie(cookie);
                }
                catch (Exception e) {
                    this.logger.warn("Unable to write cookie", e);
                }
            }
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
            this.logger.debug("Received exception", (Throwable)e);
            try {
                this.onException.accept((Exception)e);
            }
            catch (Exception ex) {
                this.logger.warn("Unable to process exception", ex);
            }
        });
        search.setEntryHandlers(entry -> {
            SyncStateControl syncStateControl;
            byte[] cookie;
            this.logger.debug("Received {}", entry);
            if (entry.getControl("1.3.6.1.4.1.4203.1.9.1.2") != null && (cookie = (syncStateControl = (SyncStateControl)entry.getControl("1.3.6.1.4.1.4203.1.9.1.2")).getCookie()) != null) {
                try {
                    manager.writeCookie(cookie);
                }
                catch (Exception e) {
                    this.logger.warn("Unable to write cookie", e);
                }
            }
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
        search.setIntermediateResponseHandlers(response -> {
            if ("1.3.6.1.4.1.4203.1.9.1.4".equals(response.getResponseName())) {
                this.logger.debug("Received {}", response);
                SyncInfoMessage message = (SyncInfoMessage)response;
                if (message.getCookie() != null) {
                    try {
                        manager.writeCookie(message.getCookie());
                    }
                    catch (Exception e) {
                        this.logger.warn("Unable to write cookie", e);
                    }
                }
                try {
                    this.onMessage.accept(message);
                }
                catch (Exception e) {
                    this.logger.warn("Unable to process intermediate response {}", response);
                    try {
                        this.onException.accept(e);
                    }
                    catch (Exception ex) {
                        this.logger.warn("Unable to process intermediate response exception", ex);
                    }
                }
            }
        });
        this.receivedResult = false;
        this.handle = search.send();
        return this.handle;
    }

    public boolean isComplete() {
        return this.receivedResult;
    }

    public ExtendedOperationHandle cancel() {
        return this.handle.cancel().send();
    }

    public void close() {
        this.factory.close();
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "factory=" + this.factory + ", " + "refreshAndPersist=" + this.refreshAndPersist + ", " + "onEntry=" + this.onEntry + ", " + "onResult=" + this.onResult + ", " + "onMessage=" + this.onMessage + ", " + "onException=" + this.onException + ", " + "handle=" + this.handle;
    }
}

