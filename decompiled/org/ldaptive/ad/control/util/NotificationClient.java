/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.control.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.Result;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.ad.control.NotificationControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationClient {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConnectionFactory factory;
    private SearchOperationHandle handle;

    public NotificationClient(ConnectionFactory cf) {
        this.factory = cf;
    }

    public BlockingQueue<NotificationItem> execute(SearchRequest request) throws LdapException {
        return this.execute(request, Integer.MAX_VALUE);
    }

    public BlockingQueue<NotificationItem> execute(SearchRequest request, int capacity) throws LdapException {
        LinkedBlockingQueue<NotificationItem> queue = new LinkedBlockingQueue<NotificationItem>(capacity);
        request.setControls(new NotificationControl());
        SearchOperation search = new SearchOperation(this.factory, request);
        search.setResultHandlers(result -> {
            this.logger.debug("Received {}", result);
            try {
                queue.put(new NotificationItem((Result)result));
            }
            catch (InterruptedException e) {
                this.logger.warn("Unable to enqueue result {}", result);
            }
        });
        search.setExceptionHandler(e -> {
            this.logger.debug("Received exception", (Throwable)e);
            try {
                queue.put(new NotificationItem((Exception)e));
            }
            catch (InterruptedException ex) {
                this.logger.warn("Unable to enqueue exception", ex);
            }
        });
        search.setEntryHandlers(entry -> {
            this.logger.debug("Received {}", entry);
            try {
                queue.put(new NotificationItem((LdapEntry)entry));
            }
            catch (InterruptedException e) {
                this.logger.warn("Unable to enqueue entry {}", entry);
            }
            return entry;
        });
        this.handle = search.send();
        return queue;
    }

    public void abandon() {
        this.handle.abandon();
    }

    public static class NotificationItem {
        private final LdapEntry searchEntry;
        private final Result searchResult;
        private final Exception searchException;

        public NotificationItem(LdapEntry entry) {
            this.searchEntry = entry;
            this.searchResult = null;
            this.searchException = null;
        }

        public NotificationItem(Result result) {
            this.searchEntry = null;
            this.searchResult = result;
            this.searchException = null;
        }

        public NotificationItem(Exception exception) {
            this.searchEntry = null;
            this.searchResult = null;
            this.searchException = exception;
        }

        public boolean isEntry() {
            return this.searchEntry != null;
        }

        public LdapEntry getEntry() {
            return this.searchEntry;
        }

        public boolean isResult() {
            return this.searchResult != null;
        }

        public Result getResult() {
            return this.searchResult;
        }

        public boolean isException() {
            return this.searchException != null;
        }

        public Exception getException() {
            return this.searchException;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("[").append(this.getClass().getName()).append("@").append(this.hashCode());
            if (this.isEntry()) {
                sb.append("::searchEntry=").append(this.searchEntry).append("]");
            } else if (this.isResult()) {
                sb.append("::searchResult=").append(this.searchResult).append("]");
            } else if (this.isException()) {
                sb.append("::syncReplException=").append(this.searchException).append("]");
            } else {
                sb.append("]");
            }
            return sb.toString();
        }
    }
}

