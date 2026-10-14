/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.exceptionhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.exceptionhandler.CacheExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExceptionHandlingDynamicCacheProxy
implements InvocationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlingDynamicCacheProxy.class.getName());
    private Ehcache ehcache;

    public ExceptionHandlingDynamicCacheProxy(Ehcache ehcache) {
        this.ehcache = ehcache;
    }

    public static Ehcache createProxy(Ehcache ehcache) {
        return (Ehcache)Proxy.newProxyInstance(ehcache.getClass().getClassLoader(), new Class[]{Ehcache.class}, new ExceptionHandlingDynamicCacheProxy(ehcache));
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        Object invocationResult = null;
        try {
            invocationResult = method2.invoke((Object)this.ehcache, args2);
        }
        catch (Exception e) {
            CacheExceptionHandler cacheExceptionHandler = this.ehcache.getCacheExceptionHandler();
            if (cacheExceptionHandler != null) {
                String keyAsString = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    keyAsString = ExceptionHandlingDynamicCacheProxy.extractKey(cause.getMessage());
                }
                Exception causeAsException = null;
                try {
                    causeAsException = (Exception)cause;
                }
                catch (ClassCastException cce) {
                    LOG.debug("Underlying cause was not an Exception: {}", cce);
                }
                cacheExceptionHandler.onException(this.ehcache, keyAsString, causeAsException);
            }
            throw e.getCause();
        }
        return invocationResult;
    }

    static String extractKey(String message) {
        char character;
        if (message == null) {
            return null;
        }
        int beginIndex = message.lastIndexOf("key ");
        if (beginIndex < 0) {
            return null;
        }
        int endIndex = beginIndex += "key ".length();
        int i = beginIndex;
        while (i < message.length() && (character = message.charAt(i)) != ' ') {
            endIndex = i++;
        }
        if (++endIndex > message.length()) {
            endIndex = message.length();
        }
        String key = message.substring(beginIndex, endIndex);
        return key;
    }
}

