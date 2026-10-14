/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.Connector;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.ConnectionStatistics;
import org.apache.activemq.broker.scheduler.Job;
import org.apache.activemq.broker.scheduler.JobListener;
import org.apache.activemq.broker.scheduler.JobScheduler;
import org.apache.activemq.broker.scheduler.JobSchedulerFacade;
import org.apache.activemq.broker.scheduler.JobSchedulerStore;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.security.SecurityContext;
import org.apache.activemq.state.ProducerState;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.usage.JobSchedulerUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.LongSequenceGenerator;
import org.apache.activemq.util.TypeConversionSupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerBroker
extends BrokerFilter
implements JobListener {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerBroker.class);
    private static final IdGenerator ID_GENERATOR = new IdGenerator();
    private static final LongSequenceGenerator longGenerator = new LongSequenceGenerator();
    private final LongSequenceGenerator messageIdGenerator = new LongSequenceGenerator();
    private final AtomicBoolean started = new AtomicBoolean();
    private final WireFormat wireFormat = new OpenWireFormat();
    private final ConnectionContext context = new ConnectionContext();
    private final ProducerId producerId = new ProducerId();
    private final SystemUsage systemUsage;
    private final JobSchedulerStore store;
    private JobScheduler scheduler;

    public SchedulerBroker(BrokerService brokerService, Broker next, JobSchedulerStore store) throws Exception {
        super(next);
        this.store = store;
        this.producerId.setConnectionId(ID_GENERATOR.generateId());
        this.context.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
        this.context.setConnection(new Connection(){

            @Override
            public Connector getConnector() {
                return null;
            }

            @Override
            public void dispatchSync(Command message) {
                if (message instanceof ExceptionResponse) {
                    LOG.warn("Unexpected response: " + message);
                }
            }

            @Override
            public void dispatchAsync(Command command) {
                if (command instanceof ExceptionResponse) {
                    LOG.warn("Unexpected response: " + command);
                }
            }

            @Override
            public Response service(Command command) {
                return null;
            }

            @Override
            public void serviceException(Throwable error) {
                LOG.warn("Unexpected exception: " + error, error);
            }

            @Override
            public boolean isSlow() {
                return false;
            }

            @Override
            public boolean isBlocked() {
                return false;
            }

            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public int getDispatchQueueSize() {
                return 0;
            }

            @Override
            public ConnectionStatistics getStatistics() {
                return null;
            }

            @Override
            public boolean isManageable() {
                return false;
            }

            @Override
            public String getRemoteAddress() {
                return null;
            }

            @Override
            public void serviceExceptionAsync(IOException e) {
                LOG.warn("Unexpected async ioexception: " + e, e);
            }

            @Override
            public String getConnectionId() {
                return null;
            }

            @Override
            public boolean isNetworkConnection() {
                return false;
            }

            @Override
            public boolean isFaultTolerantConnection() {
                return false;
            }

            @Override
            public void updateClient(ConnectionControl control) {
            }

            @Override
            public int getActiveTransactionCount() {
                return 0;
            }

            @Override
            public Long getOldestActiveTransactionDuration() {
                return null;
            }

            @Override
            public void start() throws Exception {
            }

            @Override
            public void stop() throws Exception {
            }
        });
        this.context.setBroker(next);
        this.systemUsage = brokerService.getSystemUsage();
        this.wireFormat.setVersion(brokerService.getStoreOpenWireVersion());
    }

    public synchronized JobScheduler getJobScheduler() throws Exception {
        return new JobSchedulerFacade(this);
    }

    @Override
    public void start() throws Exception {
        this.started.set(true);
        this.getInternalScheduler();
        super.start();
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            if (this.store != null) {
                this.store.stop();
            }
            if (this.scheduler != null) {
                this.scheduler.removeListener(this);
                this.scheduler = null;
            }
        }
        super.stop();
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, final Message messageSend) throws Exception {
        ConnectionContext context = producerExchange.getConnectionContext();
        String jobId = (String)messageSend.getProperty("scheduledJobId");
        final Object cronValue = messageSend.getProperty("AMQ_SCHEDULED_CRON");
        final Object periodValue = messageSend.getProperty("AMQ_SCHEDULED_PERIOD");
        final Object delayValue = messageSend.getProperty("AMQ_SCHEDULED_DELAY");
        String physicalName = messageSend.getDestination().getPhysicalName();
        boolean schedularManage = physicalName.regionMatches(true, 0, "ActiveMQ.Scheduler.Management", 0, "ActiveMQ.Scheduler.Management".length());
        if (schedularManage) {
            JobScheduler scheduler = this.getInternalScheduler();
            ActiveMQDestination replyTo = messageSend.getReplyTo();
            String action = (String)messageSend.getProperty("AMQ_SCHEDULER_ACTION");
            if (action != null) {
                long finish;
                long start2;
                Object startTime = messageSend.getProperty("ACTION_START_TIME");
                Object endTime = messageSend.getProperty("ACTION_END_TIME");
                if (replyTo != null && action.equals("BROWSE")) {
                    if (startTime != null && endTime != null) {
                        start2 = (Long)TypeConversionSupport.convert(startTime, Long.class);
                        finish = (Long)TypeConversionSupport.convert(endTime, Long.class);
                        for (Job job : scheduler.getAllJobs(start2, finish)) {
                            this.sendScheduledJob(producerExchange.getConnectionContext(), job, replyTo);
                        }
                    } else {
                        for (Job job : scheduler.getAllJobs()) {
                            this.sendScheduledJob(producerExchange.getConnectionContext(), job, replyTo);
                        }
                    }
                }
                if (jobId != null && action.equals("REMOVE")) {
                    scheduler.remove(jobId);
                } else if (action.equals("REMOVEALL")) {
                    if (startTime != null && endTime != null) {
                        start2 = (Long)TypeConversionSupport.convert(startTime, Long.class);
                        finish = (Long)TypeConversionSupport.convert(endTime, Long.class);
                        scheduler.removeAllJobs(start2, finish);
                    } else {
                        scheduler.removeAllJobs();
                    }
                }
            }
        } else if ((cronValue != null || periodValue != null || delayValue != null) && jobId == null) {
            JobSchedulerUsage usage;
            if (this.systemUsage.getJobSchedulerUsage() != null && (usage = this.systemUsage.getJobSchedulerUsage()).isFull()) {
                long start3;
                String logMessage = "Job Scheduler Store is Full (" + usage.getPercentUsage() + "% of " + usage.getLimit() + "). Stopping producer (" + messageSend.getProducerId() + ") to prevent flooding of the job scheduler store. See http://activemq.apache.org/producer-flow-control.html for more info";
                long nextWarn = start3 = System.currentTimeMillis();
                while (!usage.waitForSpace(1000L)) {
                    if (context.getStopping().get()) {
                        throw new IOException("Connection closed, send aborted.");
                    }
                    long now = System.currentTimeMillis();
                    if (now < nextWarn) continue;
                    LOG.info("" + usage + ": " + logMessage + " (blocking for: " + (now - start3) / 1000L + "s)");
                    nextWarn = now + 30000L;
                }
            }
            if (context.isInTransaction()) {
                context.getTransaction().addSynchronization(new Synchronization(){

                    @Override
                    public void afterCommit() throws Exception {
                        SchedulerBroker.this.doSchedule(messageSend, cronValue, periodValue, delayValue);
                    }
                });
            } else {
                this.doSchedule(messageSend, cronValue, periodValue, delayValue);
            }
        } else {
            super.send(producerExchange, messageSend);
        }
    }

    private void doSchedule(Message messageSend, Object cronValue, Object periodValue, Object delayValue) throws Exception {
        Object repeatValue;
        long delay = 0L;
        long period = 0L;
        int repeat = 0;
        String cronEntry = "";
        Message msg = messageSend.copy();
        msg.setTransactionId(null);
        ByteSequence packet = this.wireFormat.marshal(msg);
        if (cronValue != null) {
            cronEntry = cronValue.toString();
        }
        if (periodValue != null) {
            period = (Long)TypeConversionSupport.convert(periodValue, Long.class);
        }
        if (delayValue != null) {
            delay = (Long)TypeConversionSupport.convert(delayValue, Long.class);
        }
        if ((repeatValue = msg.getProperty("AMQ_SCHEDULED_REPEAT")) != null) {
            repeat = (Integer)TypeConversionSupport.convert(repeatValue, Integer.class);
        }
        MessageId jobId = new MessageId(messageSend.getMessageId().getProducerId(), longGenerator.getNextSequenceId());
        this.getInternalScheduler().schedule(jobId.toString(), new ByteSequence(packet.data, packet.offset, packet.length), cronEntry, delay, period, repeat);
    }

    @Override
    public void scheduledJob(String id, ByteSequence job) {
        ByteSequence packet = new ByteSequence(job.getData(), job.getOffset(), job.getLength());
        try {
            Message messageSend = (Message)this.wireFormat.unmarshal(packet);
            messageSend.setOriginalTransactionId(null);
            Object repeatValue = messageSend.getProperty("AMQ_SCHEDULED_REPEAT");
            Object cronValue = messageSend.getProperty("AMQ_SCHEDULED_CRON");
            String cronStr = cronValue != null ? cronValue.toString() : null;
            int repeat = 0;
            if (repeatValue != null) {
                repeat = (Integer)TypeConversionSupport.convert(repeatValue, Integer.class);
            }
            if (repeat != 0 || cronStr != null && cronStr.length() > 0) {
                messageSend.setMessageId(new MessageId(this.producerId, this.messageIdGenerator.getNextSequenceId()));
            }
            messageSend.setProperty("scheduledJobId", id);
            messageSend.removeProperty("AMQ_SCHEDULED_PERIOD");
            messageSend.removeProperty("AMQ_SCHEDULED_DELAY");
            messageSend.removeProperty("AMQ_SCHEDULED_REPEAT");
            messageSend.removeProperty("AMQ_SCHEDULED_CRON");
            if (messageSend.getTimestamp() > 0L && messageSend.getExpiration() > 0L) {
                long expiration;
                long oldExpiration = messageSend.getExpiration();
                long newTimeStamp = System.currentTimeMillis();
                long timeToLive = 0L;
                long oldTimestamp = messageSend.getTimestamp();
                if (oldExpiration > 0L) {
                    timeToLive = oldExpiration - oldTimestamp;
                }
                if ((expiration = timeToLive + newTimeStamp) > oldExpiration) {
                    if (timeToLive > 0L && expiration > 0L) {
                        messageSend.setExpiration(expiration);
                    }
                    messageSend.setTimestamp(newTimeStamp);
                    LOG.debug("Set message {} timestamp from {} to {}", messageSend.getMessageId(), oldTimestamp, newTimeStamp);
                }
            }
            messageSend.beforeMarshall(this.wireFormat);
            ProducerBrokerExchange producerExchange = new ProducerBrokerExchange();
            producerExchange.setConnectionContext(this.context);
            producerExchange.setMutable(true);
            producerExchange.setProducerState(new ProducerState(new ProducerInfo()));
            super.send(producerExchange, messageSend);
        }
        catch (Exception e) {
            LOG.error("Failed to send scheduled message {}", (Object)id, (Object)e);
        }
    }

    protected synchronized JobScheduler getInternalScheduler() throws Exception {
        if (this.started.get()) {
            if (this.scheduler == null && this.store != null) {
                this.scheduler = this.store.getJobScheduler("JMS");
                this.scheduler.addListener(this);
                this.scheduler.startDispatching();
            }
            return this.scheduler;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void sendScheduledJob(ConnectionContext context, Job job, ActiveMQDestination replyTo) throws Exception {
        ByteSequence packet = new ByteSequence(job.getPayload());
        try {
            Message msg = (Message)this.wireFormat.unmarshal(packet);
            msg.setOriginalTransactionId(null);
            msg.setPersistent(false);
            msg.setType("Advisory");
            msg.setMessageId(new MessageId(this.producerId, this.messageIdGenerator.getNextSequenceId()));
            msg.setOriginalDestination(msg.getDestination());
            msg.setDestination(replyTo);
            msg.setResponseRequired(false);
            msg.setProducerId(this.producerId);
            msg.setProperty("scheduledJobId", job.getJobId());
            boolean originalFlowControl = context.isProducerFlowControl();
            ProducerBrokerExchange producerExchange = new ProducerBrokerExchange();
            producerExchange.setConnectionContext(context);
            producerExchange.setMutable(true);
            producerExchange.setProducerState(new ProducerState(new ProducerInfo()));
            try {
                context.setProducerFlowControl(false);
                this.next.send(producerExchange, msg);
            }
            finally {
                context.setProducerFlowControl(originalFlowControl);
            }
        }
        catch (Exception e) {
            LOG.error("Failed to send scheduled message {}", (Object)job.getJobId(), (Object)e);
        }
    }
}

