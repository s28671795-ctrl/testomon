/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Provider;

public final class ConscryptLoader {
    private static final Method NEW_PROVIDER_METHOD;
    private static final Method IS_CONSCRYPT_METHOD;

    public static boolean isPresent() {
        return NEW_PROVIDER_METHOD != null;
    }

    public static boolean isConscrypt(Provider provider) {
        if (!ConscryptLoader.isPresent()) {
            return false;
        }
        try {
            return (Boolean)IS_CONSCRYPT_METHOD.invoke(null, provider);
        }
        catch (IllegalAccessException ex) {
            throw new AssertionError((Object)ex);
        }
        catch (InvocationTargetException ex) {
            throw new AssertionError((Object)ex);
        }
    }

    public static Provider newProvider() throws Throwable {
        if (!ConscryptLoader.isPresent()) {
            Class.forName("org.conscrypt.Conscrypt");
            throw new AssertionError((Object)"Unexpected failure referencing Conscrypt class");
        }
        return (Provider)NEW_PROVIDER_METHOD.invoke(null, new Object[0]);
    }

    static {
        Method isConscrypt;
        Method newProvider;
        try {
            Class<?> conscryptClass = Class.forName("org.conscrypt.Conscrypt");
            newProvider = conscryptClass.getMethod("newProvider", new Class[0]);
            isConscrypt = conscryptClass.getMethod("isConscrypt", Provider.class);
        }
        catch (ClassNotFoundException ex) {
            newProvider = null;
            isConscrypt = null;
        }
        catch (NoSuchMethodException ex) {
            throw new AssertionError((Object)ex);
        }
        NEW_PROVIDER_METHOD = newProvider;
        IS_CONSCRYPT_METHOD = isConscrypt;
    }
}

