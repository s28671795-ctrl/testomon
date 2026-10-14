/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import javax.ws.rs.ext.RuntimeDelegate;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface HeaderDelegateProvider<T>
extends RuntimeDelegate.HeaderDelegate<T> {
    public boolean supports(Class<?> var1);
}

