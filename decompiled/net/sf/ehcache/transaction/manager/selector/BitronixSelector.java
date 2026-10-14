/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.transaction.manager.selector;

import java.lang.reflect.Method;
import javax.transaction.xa.XAResource;
import net.sf.ehcache.transaction.manager.selector.FactorySelector;
import net.sf.ehcache.transaction.xa.EhcacheXAResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitronixSelector
extends FactorySelector {
    private static final Logger LOG = LoggerFactory.getLogger(BitronixSelector.class);

    public BitronixSelector() {
        super("Bitronix", "bitronix.tm.TransactionManagerServices", "getTransactionManager");
    }

    @Override
    public void registerResource(EhcacheXAResource ehcacheXAResource, boolean forRecovery) {
        String uniqueName = ehcacheXAResource.getCacheName();
        try {
            Class<?> producerClass = Class.forName("bitronix.tm.resource.ehcache.EhCacheXAResourceProducer");
            Class[] signature = new Class[]{String.class, XAResource.class};
            Object[] args2 = new Object[]{uniqueName, ehcacheXAResource};
            Method method2 = producerClass.getMethod("registerXAResource", signature);
            method2.invoke(null, args2);
        }
        catch (Exception e) {
            LOG.error("unable to register resource of cache " + uniqueName + " with BTM", e);
        }
    }

    @Override
    public void unregisterResource(EhcacheXAResource ehcacheXAResource, boolean forRecovery) {
        String uniqueName = ehcacheXAResource.getCacheName();
        try {
            Class<?> producerClass = Class.forName("bitronix.tm.resource.ehcache.EhCacheXAResourceProducer");
            Class[] signature = new Class[]{String.class, XAResource.class};
            Object[] args2 = new Object[]{uniqueName, ehcacheXAResource};
            Method method2 = producerClass.getMethod("unregisterXAResource", signature);
            method2.invoke(null, args2);
        }
        catch (Exception e) {
            LOG.error("unable to unregister resource of cache " + uniqueName + " with BTM", e);
        }
    }
}

