/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.pac4j.core.adapter.DefaultJEEAdapter;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JEEAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JEEAdapter.class);
    public static final JEEAdapter INSTANCE;

    public abstract int compareManagers(Object var1, Object var2);

    static {
        Constructor constructor = null;
        try {
            constructor = CommonHelper.getConstructor("org.pac4j.jee.adapter.JEEAdapterImpl");
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            LOGGER.debug("Cannot find a JEE adapter: {}", (Object)e.getMessage());
        }
        if (constructor == null) {
            LOGGER.warn("No JEEAdapterImpl found. Using DefaultJEEAdapter...");
            INSTANCE = new DefaultJEEAdapter();
        } else {
            try {
                INSTANCE = (JEEAdapter)constructor.newInstance(new Object[0]);
                LOGGER.info("Using JEE adapter: {}", (Object)INSTANCE);
            }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new TechnicalException(e);
            }
        }
    }
}

