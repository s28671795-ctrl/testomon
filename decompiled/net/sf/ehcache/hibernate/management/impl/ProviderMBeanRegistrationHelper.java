/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.SessionFactory
 *  org.hibernate.cache.CacheException
 *  org.hibernate.impl.SessionFactoryObjectFactory
 */
package net.sf.ehcache.hibernate.management.impl;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.hibernate.management.impl.EhcacheHibernateMBeanRegistrationImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cache.CacheException;
import org.hibernate.impl.SessionFactoryObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderMBeanRegistrationHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ProviderMBeanRegistrationHelper.class);
    private static final int MILLIS_PER_SECOND = 1000;
    private static final int SLEEP_MILLIS = 500;
    private volatile EhcacheHibernateMBeanRegistrationImpl ehcacheHibernateMBeanRegistration;

    public void registerMBean(CacheManager manager, Properties properties) {
        if (Boolean.getBoolean("tc.active")) {
            this.ehcacheHibernateMBeanRegistration = new EhcacheHibernateMBeanRegistrationImpl();
            manager.getTimer().scheduleAtFixedRate((TimerTask)new RegisterMBeansTask(this.ehcacheHibernateMBeanRegistration, manager, properties), 500L, 500L);
        }
    }

    public void unregisterMBean() {
        if (this.ehcacheHibernateMBeanRegistration != null) {
            this.ehcacheHibernateMBeanRegistration.dispose();
            this.ehcacheHibernateMBeanRegistration = null;
        }
    }

    private static Field getField(Class c, String fieldName) {
        for (Field field2 : c.getDeclaredFields()) {
            if (!field2.getName().equals(fieldName)) continue;
            return field2;
        }
        throw new NoSuchFieldError("Type '" + c + "' has no field '" + fieldName + "'");
    }

    private static class RegisterMBeansTask
    extends TimerTask {
        private static final int NUM_SECONDS = 30;
        private long startTime;
        private final AtomicBoolean mbeanRegistered = new AtomicBoolean(false);
        private final EhcacheHibernateMBeanRegistrationImpl ehcacheHibernateMBeanRegistration;
        private final CacheManager manager;
        private final Properties properties;

        public RegisterMBeansTask(EhcacheHibernateMBeanRegistrationImpl ehcacheHibernateMBeanRegistration, CacheManager manager, Properties properties) {
            this.ehcacheHibernateMBeanRegistration = ehcacheHibernateMBeanRegistration;
            this.manager = manager;
            this.properties = properties;
        }

        @Override
        public void run() {
            SessionFactory sessionFactory;
            LOG.debug("Running mbean initializer task for ehcache hibernate...");
            this.startTime = System.currentTimeMillis();
            if (this.mbeanRegistered.compareAndSet(false, true)) {
                try {
                    this.ehcacheHibernateMBeanRegistration.registerMBeanForCacheManager(this.manager, this.properties);
                    LOG.debug("Successfully registered bean");
                }
                catch (Exception e) {
                    throw new CacheException((Throwable)e);
                }
            }
            if ((sessionFactory = this.locateSessionFactory()) == null) {
                LOG.debug("SessionFactory is probably still being initialized... waiting for it to complete before enabling hibernate statistics monitoring via JMX");
                if (System.currentTimeMillis() > this.startTime + 30000L) {
                    LOG.info("Hibernate statistics monitoring through JMX is DISABLED.");
                    LOG.info("Failed to look up SessionFactory after 30 seconds using session-factory properties '" + this.properties + "'");
                    this.cancel();
                }
                return;
            }
            this.ehcacheHibernateMBeanRegistration.enableHibernateStatisticsSupport(sessionFactory);
            LOG.info("Hibernate statistics monitoring through JMX is ENABLED. ");
            this.cancel();
        }

        private SessionFactory locateSessionFactory() {
            String jndiName = this.properties.getProperty("hibernate.session_factory_name");
            if (jndiName != null) {
                return (SessionFactory)SessionFactoryObjectFactory.getNamedInstance((String)jndiName);
            }
            try {
                Class<SessionFactoryObjectFactory> factoryType = SessionFactoryObjectFactory.class;
                Field instancesField = ProviderMBeanRegistrationHelper.getField(factoryType, "INSTANCES");
                if (instancesField == null) {
                    throw new RuntimeException("Expected INSTANCES field on " + SessionFactoryObjectFactory.class.getName());
                }
                instancesField.setAccessible(true);
                Map map = (Map)instancesField.get(null);
                if (map == null) {
                    return null;
                }
                for (SessionFactory sessionFactory : map.values()) {
                    Class<?> sessionFactoryType = sessionFactory.getClass();
                    Field propertiesField = ProviderMBeanRegistrationHelper.getField(sessionFactoryType, "properties");
                    if (propertiesField == null) continue;
                    propertiesField.setAccessible(true);
                    Properties props = (Properties)propertiesField.get(sessionFactory);
                    if (props == null || !props.equals(this.properties)) continue;
                    return sessionFactory;
                }
            }
            catch (RuntimeException re) {
                LOG.error("Error locating Hibernate Session Factory", re);
            }
            catch (IllegalAccessException iae) {
                LOG.error("Error locating Hibernate Session Factory", iae);
            }
            return null;
        }
    }
}

