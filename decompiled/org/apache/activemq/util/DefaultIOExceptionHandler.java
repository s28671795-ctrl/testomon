/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SuppressReplyException;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.util.IOExceptionHandler;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultIOExceptionHandler
implements IOExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultIOExceptionHandler.class);
    protected BrokerService broker;
    private boolean ignoreAllErrors = false;
    private boolean ignoreNoSpaceErrors = true;
    private boolean ignoreSQLExceptions = true;
    private boolean stopStartConnectors = false;
    private String noSpaceMessage = "space";
    private String sqlExceptionMessage = "";
    private long resumeCheckSleepPeriod = 5000L;
    private final AtomicBoolean handlingException = new AtomicBoolean(false);
    private boolean systemExitOnShutdown = false;

    @Override
    public void handle(IOException exception) {
        String message;
        Throwable cause;
        if (!this.broker.isStarted() || this.ignoreAllErrors) {
            this.allowIOResumption();
            LOG.info("Ignoring IO exception, " + exception, exception);
            return;
        }
        if (this.ignoreNoSpaceErrors) {
            for (cause = exception; cause != null && cause instanceof IOException; cause = cause.getCause()) {
                message = cause.getMessage();
                if (message == null || !message.contains(this.noSpaceMessage)) continue;
                LOG.info("Ignoring no space left exception, " + exception, exception);
                this.allowIOResumption();
                return;
            }
        }
        if (this.ignoreSQLExceptions) {
            for (cause = exception; cause != null; cause = cause.getCause()) {
                if (!(cause instanceof SQLException)) continue;
                message = cause.getMessage();
                if (message == null) {
                    message = "";
                }
                if (!message.contains(this.sqlExceptionMessage)) continue;
                LOG.info("Ignoring SQLException, " + exception, cause);
                return;
            }
        }
        if (this.stopStartConnectors) {
            if (this.handlingException.compareAndSet(false, true)) {
                LOG.info("Initiating stop/restart of transports on " + this.broker + " due to IO exception, " + exception, exception);
                new Thread("IOExceptionHandler: stop transports"){

                    @Override
                    public void run() {
                        try {
                            ServiceStopper stopper = new ServiceStopper();
                            DefaultIOExceptionHandler.this.broker.stopAllConnectors(stopper);
                            LOG.info("Successfully stopped transports on " + DefaultIOExceptionHandler.this.broker);
                        }
                        catch (Exception e) {
                            try {
                                LOG.warn("Failure occurred while stopping broker connectors", e);
                            }
                            catch (Throwable throwable) {
                                new Thread("IOExceptionHandler: restart transports"){

                                    /*
                                     * WARNING - Removed try catching itself - possible behaviour change.
                                     */
                                    @Override
                                    public void run() {
                                        try {
                                            DefaultIOExceptionHandler.this.allowIOResumption();
                                            while (DefaultIOExceptionHandler.this.hasLockOwnership() && this.isPersistenceAdapterDown()) {
                                                LOG.info("waiting for broker persistence adapter checkpoint to succeed before restarting transports");
                                                TimeUnit.MILLISECONDS.sleep(DefaultIOExceptionHandler.this.resumeCheckSleepPeriod);
                                            }
                                            if (DefaultIOExceptionHandler.this.hasLockOwnership()) {
                                                Map<ActiveMQDestination, Destination> destinations = ((RegionBroker)DefaultIOExceptionHandler.this.broker.getRegionBroker()).getDestinationMap();
                                                for (Destination destination : destinations.values()) {
                                                    Queue queue;
                                                    if (!(destination instanceof Queue) || !(queue = (Queue)destination).isResetNeeded()) continue;
                                                    queue.clearPendingMessages();
                                                }
                                                DefaultIOExceptionHandler.this.broker.startAllConnectors();
                                                LOG.info("Successfully restarted transports on " + DefaultIOExceptionHandler.this.broker);
                                            }
                                        }
                                        catch (Exception e) {
                                            LOG.warn("Stopping " + DefaultIOExceptionHandler.this.broker + " due to failure restarting transports", e);
                                            DefaultIOExceptionHandler.this.stopBroker(e);
                                        }
                                        finally {
                                            DefaultIOExceptionHandler.this.handlingException.compareAndSet(true, false);
                                        }
                                    }

                                    private boolean isPersistenceAdapterDown() {
                                        boolean checkpointSuccess = false;
                                        try {
                                            DefaultIOExceptionHandler.this.broker.getPersistenceAdapter().checkpoint(true);
                                            checkpointSuccess = true;
                                        }
                                        catch (Throwable throwable) {
                                            // empty catch block
                                        }
                                        return !checkpointSuccess;
                                    }
                                }.start();
                                throw throwable;
                            }
                            new /* invalid duplicate definition of identical inner class */.start();
                        }
                        new /* invalid duplicate definition of identical inner class */.start();
                    }
                }.start();
            }
            throw new SuppressReplyException("Stop/RestartTransportsInitiated", exception);
        }
        if (this.handlingException.compareAndSet(false, true)) {
            this.stopBroker(exception);
        }
        throw new SuppressReplyException("ShutdownBrokerInitiated", exception);
    }

    protected void allowIOResumption() {
        try {
            if (this.broker.getPersistenceAdapter() != null) {
                this.broker.getPersistenceAdapter().allowIOResumption();
            }
        }
        catch (IOException e) {
            LOG.warn("Failed to allow IO resumption", e);
        }
    }

    private void stopBroker(Exception exception) {
        LOG.info("Stopping " + this.broker + " due to exception, " + exception, exception);
        new Thread("IOExceptionHandler: stopping " + this.broker){

            @Override
            public void run() {
                try {
                    if (DefaultIOExceptionHandler.this.broker.isRestartAllowed()) {
                        DefaultIOExceptionHandler.this.broker.requestRestart();
                    }
                    DefaultIOExceptionHandler.this.broker.setSystemExitOnShutdown(DefaultIOExceptionHandler.this.isSystemExitOnShutdown());
                    DefaultIOExceptionHandler.this.broker.stop();
                }
                catch (Exception e) {
                    LOG.warn("Failure occurred while stopping broker", e);
                }
            }
        }.start();
    }

    protected boolean hasLockOwnership() throws IOException {
        return true;
    }

    @Override
    public void setBrokerService(BrokerService broker) {
        this.broker = broker;
    }

    public boolean isIgnoreAllErrors() {
        return this.ignoreAllErrors;
    }

    public void setIgnoreAllErrors(boolean ignoreAllErrors) {
        this.ignoreAllErrors = ignoreAllErrors;
    }

    public boolean isIgnoreNoSpaceErrors() {
        return this.ignoreNoSpaceErrors;
    }

    public void setIgnoreNoSpaceErrors(boolean ignoreNoSpaceErrors) {
        this.ignoreNoSpaceErrors = ignoreNoSpaceErrors;
    }

    public String getNoSpaceMessage() {
        return this.noSpaceMessage;
    }

    public void setNoSpaceMessage(String noSpaceMessage) {
        this.noSpaceMessage = noSpaceMessage;
    }

    public boolean isIgnoreSQLExceptions() {
        return this.ignoreSQLExceptions;
    }

    public void setIgnoreSQLExceptions(boolean ignoreSQLExceptions) {
        this.ignoreSQLExceptions = ignoreSQLExceptions;
    }

    public String getSqlExceptionMessage() {
        return this.sqlExceptionMessage;
    }

    public void setSqlExceptionMessage(String sqlExceptionMessage) {
        this.sqlExceptionMessage = sqlExceptionMessage;
    }

    public boolean isStopStartConnectors() {
        return this.stopStartConnectors;
    }

    public void setStopStartConnectors(boolean stopStartConnectors) {
        this.stopStartConnectors = stopStartConnectors;
    }

    public long getResumeCheckSleepPeriod() {
        return this.resumeCheckSleepPeriod;
    }

    public void setResumeCheckSleepPeriod(long resumeCheckSleepPeriod) {
        this.resumeCheckSleepPeriod = resumeCheckSleepPeriod;
    }

    public void setSystemExitOnShutdown(boolean systemExitOnShutdown) {
        this.systemExitOnShutdown = systemExitOnShutdown;
    }

    public boolean isSystemExitOnShutdown() {
        return this.systemExitOnShutdown;
    }
}

