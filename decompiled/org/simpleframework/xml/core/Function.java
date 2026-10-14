/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.reflect.Method;
import java.util.Map;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Session;

class Function {
    private final Method method;
    private final boolean contextual;

    public Function(Method method2) {
        this(method2, false);
    }

    public Function(Method method2, boolean contextual) {
        this.contextual = contextual;
        this.method = method2;
    }

    public Object call(Context context, Object source2) throws Exception {
        if (source2 != null) {
            Session session = context.getSession();
            Map table = session.getMap();
            if (this.contextual) {
                return this.method.invoke(source2, table);
            }
            return this.method.invoke(source2, new Object[0]);
        }
        return null;
    }
}

