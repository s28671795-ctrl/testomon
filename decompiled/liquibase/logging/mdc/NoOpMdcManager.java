/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import liquibase.logging.mdc.CustomMdcObject;
import liquibase.logging.mdc.MdcManager;
import liquibase.logging.mdc.MdcObject;

public class NoOpMdcManager
implements MdcManager {
    @Override
    public MdcObject put(String key, String value) {
        return new MdcObject(key, value);
    }

    @Override
    public MdcObject put(String key, String value, boolean removeWhenScopeExits) {
        return new MdcObject(key, value);
    }

    @Override
    public MdcObject put(String key, Map<String, Object> values2) {
        return new MdcObject(key, values2);
    }

    @Override
    public MdcObject put(String key, Map<String, Object> values2, boolean removeWhenScopeExits) {
        return new MdcObject(key, values2);
    }

    @Override
    public MdcObject put(String key, List<? extends CustomMdcObject> values2) {
        return new MdcObject(key, values2);
    }

    @Override
    public MdcObject put(String key, CustomMdcObject customMdcObject) {
        return new MdcObject(key, customMdcObject);
    }

    @Override
    public MdcObject put(String key, CustomMdcObject customMdcObject, boolean removeWhenScopeExits) {
        return new MdcObject(key, customMdcObject);
    }

    @Override
    public void remove(String key) {
    }

    @Override
    public void clear() {
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public Map<String, Object> getAll() {
        return Collections.emptyMap();
    }
}

