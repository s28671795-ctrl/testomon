/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiLookupFactory {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T lookup(String name, Class<T> clazz) throws NamingException {
        try (InitialContext ctx = new InitialContext();){
            T t2 = clazz.cast(ctx.lookup(name));
            return t2;
        }
    }
}

