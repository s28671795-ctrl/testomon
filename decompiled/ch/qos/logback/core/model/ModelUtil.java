/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.model;

import ch.qos.logback.core.joran.action.ActionUtil;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.processor.ModelInterpretationContext;
import ch.qos.logback.core.model.util.PropertyModelHandlerHelper;
import java.util.Properties;

public class ModelUtil {
    public static void resetForReuse(Model model) {
        if (model == null) {
            return;
        }
        model.resetForReuse();
    }

    @Deprecated
    public static void setProperty(ModelInterpretationContext mic, String key, String value, ActionUtil.Scope scope) {
        PropertyModelHandlerHelper.setProperty(mic, key, value, scope);
    }

    @Deprecated
    public static void setProperties(ModelInterpretationContext mic, Properties props, ActionUtil.Scope scope) {
        PropertyModelHandlerHelper.setProperties(mic, props, scope);
    }
}

