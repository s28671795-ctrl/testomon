/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import java.util.Map;
import javax.wsdl.extensions.ExtensionRegistry;
import org.apache.cxf.wsdl.WSDLExtensibilityPlugin;

public abstract class AbstractWSDLPlugin
implements WSDLExtensibilityPlugin {
    protected ExtensionRegistry registry;

    @Override
    public void setExtensionRegistry(ExtensionRegistry r) {
        this.registry = r;
    }

    public ExtensionRegistry getExtenstionRegistry() {
        return this.registry;
    }

    public boolean optionSet(Map<String, Object> args2, String key) {
        return args2.containsKey(key);
    }

    public String getOption(Map<String, Object> args2, String key) {
        return args2.get(key).toString();
    }

    public <T> T getOption(Map<String, Object> args2, String key, Class<T> clz) {
        return clz.cast(args2.get(key));
    }

    public <T> T getOption(Map<String, Object> args2, Class<T> clz) {
        return clz.cast(args2.get(clz.getName()));
    }

    public String getOption(Map<String, Object> args2, String key, String defaultValue) {
        if (!this.optionSet(args2, key)) {
            return defaultValue;
        }
        return this.getOption(args2, key);
    }
}

