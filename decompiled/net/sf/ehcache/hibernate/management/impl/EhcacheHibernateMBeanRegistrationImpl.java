/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.SessionFactory
 */
package net.sf.ehcache.hibernate.management.impl;

import java.lang.management.ManagementFactory;
import java.util.Properties;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.hibernate.management.impl.EhcacheHibernate;
import net.sf.ehcache.hibernate.management.impl.EhcacheHibernateMBeanRegistration;
import net.sf.ehcache.hibernate.management.impl.EhcacheHibernateMbeanNames;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhcacheHibernateMBeanRegistrationImpl
implements EhcacheHibernateMBeanRegistration,
CacheManagerEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheHibernateMBeanRegistrationImpl.class.getName());
    private static final int MAX_MBEAN_REGISTRATION_RETRIES = 50;
    private String cacheManagerClusterUUID;
    private String registeredCacheManagerName;
    private Status status = Status.STATUS_UNINITIALISED;
    private volatile EhcacheHibernate ehcacheHibernate;
    private volatile ObjectName cacheManagerObjectName;

    @Override
    public synchronized void registerMBeanForCacheManager(CacheManager manager, Properties properties) throws Exception {
        String sessionFactoryName = properties.getProperty("hibernate.session_factory_name");
        String name = null;
        name = sessionFactoryName == null ? manager.getName() : ("".equals(sessionFactoryName.trim()) ? manager.getName() : sessionFactoryName);
        this.registerBean(name, manager);
    }

    private void registerBean(String name, CacheManager manager) throws Exception {
        this.ehcacheHibernate = new EhcacheHibernate(manager);
        int tries = 0;
        boolean success = false;
        InstanceAlreadyExistsException exception = null;
        this.cacheManagerClusterUUID = manager.getClusterUUID();
        while (true) {
            this.registeredCacheManagerName = name;
            if (tries != 0) {
                this.registeredCacheManagerName = this.registeredCacheManagerName + "_" + tries;
            }
            try {
                MBeanServer mBeanServer = this.getMBeanServer();
                this.cacheManagerObjectName = EhcacheHibernateMbeanNames.getCacheManagerObjectName(this.cacheManagerClusterUUID, this.registeredCacheManagerName);
                mBeanServer.registerMBean(this.ehcacheHibernate, this.cacheManagerObjectName);
                success = true;
            }
            catch (InstanceAlreadyExistsException e) {
                success = false;
                exception = e;
                if (++tries < 50) continue;
            }
            break;
        }
        if (!success) {
            throw new Exception("Cannot register mbean for CacheManager with name" + manager.getName() + " after " + 50 + " retries. Last tried name=" + this.registeredCacheManagerName, exception);
        }
        this.status = Status.STATUS_ALIVE;
    }

    private MBeanServer getMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

    @Override
    public void enableHibernateStatisticsSupport(SessionFactory sessionFactory) {
        this.ehcacheHibernate.enableHibernateStatistics(sessionFactory);
    }

    @Override
    public synchronized void dispose() throws CacheException {
        if (this.status == Status.STATUS_SHUTDOWN) {
            return;
        }
        try {
            this.getMBeanServer().unregisterMBean(this.cacheManagerObjectName);
        }
        catch (Exception e) {
            LOG.warn("Error unregistering object instance " + this.cacheManagerObjectName + " . Error was " + e.getMessage(), e);
        }
        this.ehcacheHibernate = null;
        this.cacheManagerObjectName = null;
        this.status = Status.STATUS_SHUTDOWN;
    }

    @Override
    public synchronized Status getStatus() {
        return this.status;
    }

    @Override
    public void init() throws CacheException {
    }

    @Override
    public void notifyCacheAdded(String cacheName) {
    }

    @Override
    public void notifyCacheRemoved(String cacheName) {
    }
}

