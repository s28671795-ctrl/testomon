/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.model.util;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.ContextAwarePropertyContainer;
import ch.qos.logback.core.spi.ScanException;
import ch.qos.logback.core.util.OptionHelper;
import java.util.HashMap;
import java.util.Map;

public class VariableSubstitutionsHelper
extends ContextAwareBase
implements ContextAwarePropertyContainer {
    protected Map<String, String> propertiesMap;

    public VariableSubstitutionsHelper(Context context) {
        this.setContext(context);
        this.propertiesMap = new HashMap<String, String>();
    }

    public VariableSubstitutionsHelper(Context context, Map<String, String> otherMap) {
        this.setContext(context);
        this.propertiesMap = new HashMap<String, String>(otherMap);
    }

    @Override
    public String subst(String ref) {
        if (ref == null) {
            return null;
        }
        try {
            return OptionHelper.substVars(ref, this, this.context);
        }
        catch (ScanException | IllegalArgumentException e) {
            this.addError("Problem while parsing [" + ref + "]", e);
            return ref;
        }
    }

    @Override
    public void addSubstitutionProperty(String key, String value) {
        if (key == null || value == null) {
            return;
        }
        value = value.trim();
        this.propertiesMap.put(key, value);
    }

    @Override
    public String getProperty(String key) {
        return this.propertiesMap.get(key);
    }

    @Override
    public Map<String, String> getCopyOfPropertyMap() {
        return new HashMap<String, String>(this.propertiesMap);
    }
}

