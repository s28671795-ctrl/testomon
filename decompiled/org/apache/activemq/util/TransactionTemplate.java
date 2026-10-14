/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.util.Callback;
import org.apache.activemq.util.IOExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionTemplate.class);
    private PersistenceAdapter persistenceAdapter;
    private ConnectionContext context;

    public TransactionTemplate(PersistenceAdapter persistenceAdapter, ConnectionContext context) {
        this.persistenceAdapter = persistenceAdapter;
        this.context = context;
    }

    public void run(Callback task) throws IOException {
        this.persistenceAdapter.beginTransaction(this.context);
        Throwable throwable = null;
        try {
            task.execute();
        }
        catch (IOException t2) {
            throwable = t2;
            throw t2;
        }
        catch (RuntimeException t3) {
            throwable = t3;
            throw t3;
        }
        catch (Throwable t4) {
            throwable = t4;
            throw IOExceptionSupport.create("Persistence task failed: " + t4, t4);
        }
        finally {
            if (throwable == null) {
                this.persistenceAdapter.commitTransaction(this.context);
            } else {
                LOG.error("Having to Rollback - caught an exception: " + throwable);
                this.persistenceAdapter.rollbackTransaction(this.context);
            }
        }
    }

    public ConnectionContext getContext() {
        return this.context;
    }

    public PersistenceAdapter getPersistenceAdapter() {
        return this.persistenceAdapter;
    }
}

