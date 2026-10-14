/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.HibernateException
 *  org.hibernate.transaction.TransactionManagerLookup
 */
package net.sf.ehcache.hibernate;

import java.util.Properties;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import net.sf.ehcache.transaction.manager.DefaultTransactionManagerLookup;
import org.hibernate.HibernateException;
import org.hibernate.transaction.TransactionManagerLookup;

public class EhcacheJtaTransactionManagerLookup
extends DefaultTransactionManagerLookup
implements TransactionManagerLookup {
    public TransactionManager getTransactionManager(Properties properties) throws HibernateException {
        return this.getTransactionManager();
    }

    public String getUserTransactionName() {
        return null;
    }

    public Object getTransactionIdentifier(Transaction transaction) {
        return transaction;
    }
}

