/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ServantObject;

public interface LocalClientRequestDispatcher {
    public boolean useLocalInvocation(Object var1);

    public boolean is_local(Object var1);

    public ServantObject servant_preinvoke(Object var1, String var2, Class<?> var3);

    public void servant_postinvoke(Object var1, ServantObject var2);
}

