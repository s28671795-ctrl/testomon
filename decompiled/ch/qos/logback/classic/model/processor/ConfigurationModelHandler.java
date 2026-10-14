/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.classic.model.processor;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.model.ConfigurationModel;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.processor.ModelHandlerBase;
import ch.qos.logback.core.model.processor.ModelHandlerException;
import ch.qos.logback.core.model.processor.ModelInterpretationContext;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.util.ContextUtil;
import ch.qos.logback.core.util.Duration;
import ch.qos.logback.core.util.OptionHelper;
import ch.qos.logback.core.util.StatusListenerConfigHelper;

public class ConfigurationModelHandler
extends ModelHandlerBase {
    static final Duration SCAN_PERIOD_DEFAULT = Duration.buildByMinutes(1.0);

    public ConfigurationModelHandler(Context context) {
        super(context);
    }

    public static ModelHandlerBase makeInstance(Context context, ModelInterpretationContext mic) {
        return new ConfigurationModelHandler(context);
    }

    protected Class<ConfigurationModel> getSupportedModelClass() {
        return ConfigurationModel.class;
    }

    @Override
    public void handle(ModelInterpretationContext mic, Model model) {
        ConfigurationModel configurationModel = (ConfigurationModel)model;
        String debugAttrib = OptionHelper.getSystemProperty("logback.debug", null);
        if (debugAttrib == null) {
            debugAttrib = mic.subst(configurationModel.getDebugStr());
        }
        if (!(OptionHelper.isNullOrEmptyOrAllSpaces(debugAttrib) || debugAttrib.equalsIgnoreCase(Boolean.FALSE.toString()) || debugAttrib.equalsIgnoreCase("null"))) {
            StatusListenerConfigHelper.addOnConsoleListenerInstance(this.context, new OnConsoleStatusListener());
        }
        this.processScanAttrib(mic, configurationModel);
        LoggerContext lc = (LoggerContext)this.context;
        boolean packagingData = OptionHelper.toBoolean(mic.subst(configurationModel.getPackagingDataStr()), false);
        lc.setPackagingDataEnabled(packagingData);
        ContextUtil contextUtil = new ContextUtil(this.context);
        contextUtil.addGroovyPackages(lc.getFrameworkPackages());
    }

    protected void processScanAttrib(ModelInterpretationContext mic, ConfigurationModel configurationModel) {
        String scanStr = mic.subst(configurationModel.getScanStr());
        if (!OptionHelper.isNullOrEmptyOrAllSpaces(scanStr) && !"false".equalsIgnoreCase(scanStr)) {
            this.addInfo("Skipping ReconfigureOnChangeTask registration");
        }
    }

    protected void postProcessScanAttrib(ModelInterpretationContext mic, ConfigurationModel configurationModel) {
    }

    @Override
    public void postHandle(ModelInterpretationContext mic, Model model) throws ModelHandlerException {
        ConfigurationModel configurationModel = (ConfigurationModel)model;
    }
}

