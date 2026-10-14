/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.ExtensionFactory;
import org.pf4j.PluginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExtensionFactory
implements ExtensionFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultExtensionFactory.class);

    @Override
    public <T> T create(Class<T> extensionClass) {
        log.debug("Create instance for extension '{}'", (Object)extensionClass.getName());
        try {
            return extensionClass.newInstance();
        }
        catch (Exception e) {
            throw new PluginRuntimeException(e);
        }
    }
}

