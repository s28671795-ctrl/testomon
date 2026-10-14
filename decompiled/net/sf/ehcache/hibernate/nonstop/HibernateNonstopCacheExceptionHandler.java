/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.nonstop;

import net.sf.ehcache.constructs.nonstop.NonStopCacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HibernateNonstopCacheExceptionHandler {
    public static final String HIBERNATE_THROW_EXCEPTION_ON_TIMEOUT_PROPERTY = "ehcache.hibernate.propagateNonStopCacheException";
    public static final String HIBERNATE_LOG_EXCEPTION_STACK_TRACE_PROPERTY = "ehcache.hibernate.logNonStopCacheExceptionStackTrace";
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateNonstopCacheExceptionHandler.class);
    private static final HibernateNonstopCacheExceptionHandler INSTANCE = new HibernateNonstopCacheExceptionHandler();

    private HibernateNonstopCacheExceptionHandler() {
    }

    public static HibernateNonstopCacheExceptionHandler getInstance() {
        return INSTANCE;
    }

    public void handleNonstopCacheException(NonStopCacheException nonStopCacheException) {
        if (Boolean.getBoolean(HIBERNATE_THROW_EXCEPTION_ON_TIMEOUT_PROPERTY)) {
            throw nonStopCacheException;
        }
        if (Boolean.getBoolean(HIBERNATE_LOG_EXCEPTION_STACK_TRACE_PROPERTY)) {
            LOGGER.debug("Ignoring NonstopCacheException - " + nonStopCacheException.getMessage(), nonStopCacheException);
        } else {
            LOGGER.debug("Ignoring NonstopCacheException - " + nonStopCacheException.getMessage());
        }
    }
}

