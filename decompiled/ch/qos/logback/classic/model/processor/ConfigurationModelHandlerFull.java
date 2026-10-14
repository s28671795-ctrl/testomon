/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.classic.model.processor;

import ch.qos.logback.classic.joran.ReconfigureOnChangeTask;
import ch.qos.logback.classic.model.ConfigurationModel;
import ch.qos.logback.classic.model.processor.ConfigurationModelHandler;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.processor.ModelHandlerBase;
import ch.qos.logback.core.model.processor.ModelHandlerException;
import ch.qos.logback.core.model.processor.ModelInterpretationContext;
import ch.qos.logback.core.spi.ConfigurationEvent;
import ch.qos.logback.core.util.Duration;
import ch.qos.logback.core.util.OptionHelper;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ConfigurationModelHandlerFull
extends ConfigurationModelHandler {
    public static String FAILED_WATCH_PREDICATE_MESSAGE_1 = "Missing watchable .xml or .properties files.";
    public static String FAILED_WATCH_PREDICATE_MESSAGE_2 = "Watching .xml files requires that the main configuration file is reachable as a URL";

    public ConfigurationModelHandlerFull(Context context) {
        super(context);
    }

    public static ModelHandlerBase makeInstance2(Context context, ModelInterpretationContext mic) {
        return new ConfigurationModelHandlerFull(context);
    }

    @Override
    protected void processScanAttrib(ModelInterpretationContext mic, ConfigurationModel configurationModel) {
    }

    @Override
    public void postHandle(ModelInterpretationContext mic, Model model) throws ModelHandlerException {
        ConfigurationModel configurationModel = (ConfigurationModel)model;
        this.postProcessScanAttrib(mic, configurationModel);
    }

    @Override
    protected void postProcessScanAttrib(ModelInterpretationContext mic, ConfigurationModel configurationModel) {
        String scanStr = mic.subst(configurationModel.getScanStr());
        String scanPeriodStr = mic.subst(configurationModel.getScanPeriodStr());
        this.detachedPostProcess(scanStr, scanPeriodStr);
    }

    public void detachedPostProcess(String scanStr, String scanPeriodStr) {
        if (!OptionHelper.isNullOrEmptyOrAllSpaces(scanStr) && !"false".equalsIgnoreCase(scanStr)) {
            ScheduledExecutorService scheduledExecutorService = this.context.getScheduledExecutorService();
            boolean watchPredicateFulfilled = ConfigurationWatchListUtil.watchPredicateFulfilled(this.context);
            if (!watchPredicateFulfilled) {
                this.addWarn(FAILED_WATCH_PREDICATE_MESSAGE_1);
                this.addWarn(FAILED_WATCH_PREDICATE_MESSAGE_2);
                return;
            }
            ReconfigureOnChangeTask rocTask = new ReconfigureOnChangeTask();
            rocTask.setContext(this.context);
            this.addInfo("Registering a new ReconfigureOnChangeTask " + String.valueOf(rocTask));
            this.context.fireConfigurationEvent(ConfigurationEvent.newConfigurationChangeDetectorRegisteredEvent(rocTask));
            Duration duration = this.getDurationOfScanPeriodAttribute(scanPeriodStr, SCAN_PERIOD_DEFAULT);
            this.addInfo("Will scan for changes in [" + String.valueOf(ConfigurationWatchListUtil.getConfigurationWatchList(this.context)) + "] ");
            this.addInfo("Setting ReconfigureOnChangeTask scanning period to " + String.valueOf(duration));
            ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(rocTask, duration.getMilliseconds(), duration.getMilliseconds(), TimeUnit.MILLISECONDS);
            rocTask.setScheduredFuture(scheduledFuture);
            this.context.addScheduledFuture(scheduledFuture);
        }
    }

    private Duration getDurationOfScanPeriodAttribute(String scanPeriodAttrib, Duration defaultDuration) {
        Duration duration = null;
        if (!OptionHelper.isNullOrEmptyOrAllSpaces(scanPeriodAttrib)) {
            try {
                duration = Duration.valueOf(scanPeriodAttrib);
            }
            catch (IllegalArgumentException | IllegalStateException e) {
                this.addWarn("Failed to parse 'scanPeriod' attribute [" + scanPeriodAttrib + "]", e);
            }
        }
        if (duration == null) {
            this.addInfo("No 'scanPeriod' specified. Defaulting to " + defaultDuration.toString());
            duration = defaultDuration;
        }
        return duration;
    }
}

