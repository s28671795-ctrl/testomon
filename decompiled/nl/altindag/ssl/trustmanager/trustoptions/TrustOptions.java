/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager.trustoptions;

import javax.net.ssl.ManagerFactoryParameters;

@FunctionalInterface
public interface TrustOptions<T, R extends ManagerFactoryParameters> {
    public R apply(T var1) throws Exception;
}

