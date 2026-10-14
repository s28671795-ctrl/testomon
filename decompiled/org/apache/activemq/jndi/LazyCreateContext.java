/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.activemq.jndi.ReadOnlyContext;

public abstract class LazyCreateContext
extends ReadOnlyContext {
    @Override
    public Object lookup(String name) throws NamingException {
        try {
            return super.lookup(name);
        }
        catch (NameNotFoundException e) {
            Object answer = this.createEntry(name);
            if (answer == null) {
                throw e;
            }
            this.internalBind(name, answer);
            return answer;
        }
    }

    protected abstract Object createEntry(String var1);
}

