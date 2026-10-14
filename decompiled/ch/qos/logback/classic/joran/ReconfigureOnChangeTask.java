/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.classic.joran;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.joran.PropertiesConfigurator;
import ch.qos.logback.classic.joran.ReconfigureOnChangeTaskListener;
import ch.qos.logback.core.joran.spi.ConfigurationWatchList;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.ModelUtil;
import ch.qos.logback.core.spi.ConfigurationEvent;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.status.StatusUtil;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class ReconfigureOnChangeTask
extends ContextAwareBase
implements Runnable {
    public static final String DETECTED_CHANGE_IN_CONFIGURATION_FILES = "Detected change in configuration files.";
    public static final String RE_REGISTERING_PREVIOUS_SAFE_CONFIGURATION = "Re-registering previous fallback configuration once more as a fallback configuration point";
    public static final String FALLING_BACK_TO_SAFE_CONFIGURATION = "Given previous errors, falling back to previously registered safe configuration.";
    long birthdate = System.currentTimeMillis();
    List<ReconfigureOnChangeTaskListener> listeners = null;
    ScheduledFuture<?> scheduledFuture;

    @Override
    public void run() {
        this.context.fireConfigurationEvent(ConfigurationEvent.newConfigurationChangeDetectorRunningEvent(this));
        ConfigurationWatchList configurationWatchList = ConfigurationWatchListUtil.getConfigurationWatchList(this.context);
        if (configurationWatchList == null) {
            this.addWarn("Empty ConfigurationWatchList in context");
            return;
        }
        if (configurationWatchList.emptyWatchLists()) {
            this.addInfo("Both watch lists are empty. Disabling ");
            return;
        }
        File changedFile = configurationWatchList.changeDetectedInFile();
        URL changedURL = configurationWatchList.changeDetectedInURL();
        if (changedFile == null && changedURL == null) {
            return;
        }
        this.context.fireConfigurationEvent(ConfigurationEvent.newConfigurationChangeDetectedEvent(this));
        this.addInfo(DETECTED_CHANGE_IN_CONFIGURATION_FILES);
        if (changedFile != null) {
            this.changeInFile(changedFile, configurationWatchList);
        }
        if (changedURL != null) {
            this.changeInURL(changedURL);
        }
    }

    private void changeInURL(URL url2) {
        String path = url2.getPath();
        if (path.endsWith(".properties")) {
            this.runPropertiesConfigurator(url2);
        }
    }

    private void changeInFile(File changedFile, ConfigurationWatchList configurationWatchList) {
        if (changedFile.getName().endsWith(".properties")) {
            this.runPropertiesConfigurator(changedFile);
            return;
        }
        this.addInfo("Will reset and reconfigure context named [" + this.context.getName() + "]");
        this.cancelFutureInvocationsOfThisTaskInstance();
        URL mainConfigurationURL = configurationWatchList.getMainURL();
        LoggerContext lc = (LoggerContext)this.context;
        if (mainConfigurationURL.toString().endsWith("xml")) {
            this.performXMLConfiguration(lc, mainConfigurationURL);
        } else if (mainConfigurationURL.toString().endsWith("groovy")) {
            this.addError("Groovy configuration disabled due to Java 9 compilation issues.");
        }
    }

    private void runPropertiesConfigurator(Object changedObject) {
        this.addInfo("Will run PropertyConfigurator on " + String.valueOf(changedObject));
        PropertiesConfigurator propertiesConfigurator = new PropertiesConfigurator();
        propertiesConfigurator.setContext(this.context);
        try {
            if (changedObject instanceof File) {
                File changedFile = (File)changedObject;
                propertiesConfigurator.doConfigure(changedFile);
            } else if (changedObject instanceof URL) {
                URL changedURL = (URL)changedObject;
                propertiesConfigurator.doConfigure(changedURL);
            }
            this.context.fireConfigurationEvent(ConfigurationEvent.newPartialConfigurationEndedSuccessfullyEvent(this));
        }
        catch (JoranException e) {
            this.addError("Failed to reload " + String.valueOf(changedObject));
        }
    }

    private void cancelFutureInvocationsOfThisTaskInstance() {
        boolean result = this.scheduledFuture.cancel(false);
        if (!result) {
            this.addWarn("could not cancel " + this.toString());
        }
    }

    private void performXMLConfiguration(LoggerContext lc, URL mainConfigurationURL) {
        JoranConfigurator jc = new JoranConfigurator();
        jc.setContext(this.context);
        StatusUtil statusUtil = new StatusUtil(this.context);
        Model failsafeTop = jc.recallSafeConfiguration();
        URL mainURL = ConfigurationWatchListUtil.getMainWatchURL(this.context);
        this.addInfo("Resetting loggerContext [" + lc.getName() + "]");
        lc.reset();
        long threshold = System.currentTimeMillis();
        try {
            jc.doConfigure(mainConfigurationURL);
            if (statusUtil.hasXMLParsingErrors(threshold)) {
                this.fallbackConfiguration(lc, failsafeTop, mainURL);
            }
        }
        catch (JoranException e) {
            this.addWarn("Exception occurred during reconfiguration", e);
            this.fallbackConfiguration(lc, failsafeTop, mainURL);
        }
    }

    private void fallbackConfiguration(LoggerContext lc, Model failsafeTop, URL mainURL) {
        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.setContext(this.context);
        ConfigurationWatchList oldCWL = ConfigurationWatchListUtil.getConfigurationWatchList(this.context);
        ConfigurationWatchList newCWL = oldCWL.buildClone();
        if (failsafeTop == null) {
            this.addWarn("No previous configuration to fall back on.");
            return;
        }
        this.addWarn(FALLING_BACK_TO_SAFE_CONFIGURATION);
        this.addInfo("Safe model " + String.valueOf(failsafeTop));
        try {
            lc.reset();
            ConfigurationWatchListUtil.registerConfigurationWatchList(this.context, newCWL);
            ModelUtil.resetForReuse(failsafeTop);
            joranConfigurator.processModel(failsafeTop);
            this.addInfo(RE_REGISTERING_PREVIOUS_SAFE_CONFIGURATION);
            joranConfigurator.registerSafeConfiguration(failsafeTop);
            this.context.fireConfigurationEvent(ConfigurationEvent.newConfigurationEndedSuccessfullyEvent(this));
        }
        catch (Exception e) {
            this.addError("Unexpected exception thrown by a configuration considered safe.", e);
        }
    }

    public String toString() {
        return "ReconfigureOnChangeTask(born:" + this.birthdate + ")";
    }

    public void setScheduredFuture(ScheduledFuture<?> aScheduledFuture) {
        this.scheduledFuture = aScheduledFuture;
    }
}

