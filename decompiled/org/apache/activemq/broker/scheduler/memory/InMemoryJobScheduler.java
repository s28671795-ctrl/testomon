/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler.memory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.jms.MessageFormatException;
import org.apache.activemq.broker.scheduler.CronParser;
import org.apache.activemq.broker.scheduler.Job;
import org.apache.activemq.broker.scheduler.JobListener;
import org.apache.activemq.broker.scheduler.JobScheduler;
import org.apache.activemq.broker.scheduler.JobSupport;
import org.apache.activemq.broker.scheduler.memory.InMemoryJob;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryJobScheduler
implements JobScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryJobScheduler.class);
    private static final IdGenerator ID_GENERATOR = new IdGenerator();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final String name;
    private final TreeMap<Long, ScheduledTask> jobs = new TreeMap();
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean dispatchEnabled = new AtomicBoolean(false);
    private final List<JobListener> jobListeners = new CopyOnWriteArrayList<JobListener>();
    private final Timer timer = new Timer();

    public InMemoryJobScheduler(String name) {
        this.name = name;
    }

    @Override
    public String getName() throws Exception {
        return this.name;
    }

    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.startDispatching();
            LOG.trace("JobScheduler[{}] started", (Object)this.name);
        }
    }

    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            this.stopDispatching();
            this.timer.cancel();
            this.jobs.clear();
            LOG.trace("JobScheduler[{}] stopped", (Object)this.name);
        }
    }

    public boolean isStarted() {
        return this.started.get();
    }

    public boolean isDispatchEnabled() {
        return this.dispatchEnabled.get();
    }

    @Override
    public void startDispatching() throws Exception {
        this.dispatchEnabled.set(true);
    }

    @Override
    public void stopDispatching() throws Exception {
        this.dispatchEnabled.set(false);
    }

    @Override
    public void addListener(JobListener listener) throws Exception {
        this.jobListeners.add(listener);
    }

    @Override
    public void removeListener(JobListener listener) throws Exception {
        this.jobListeners.remove(listener);
    }

    @Override
    public void schedule(String jobId, ByteSequence payload, long delay) throws Exception {
        this.doSchedule(jobId, payload, "", 0L, delay, 0);
    }

    @Override
    public void schedule(String jobId, ByteSequence payload, String cronEntry) throws Exception {
        this.doSchedule(jobId, payload, cronEntry, 0L, 0L, 0);
    }

    @Override
    public void schedule(String jobId, ByteSequence payload, String cronEntry, long delay, long period, int repeat) throws Exception {
        this.doSchedule(jobId, payload, cronEntry, delay, period, repeat);
    }

    @Override
    public void remove(long time) throws Exception {
        this.doRemoveRange(time, time);
    }

    @Override
    public void remove(String jobId) throws Exception {
        this.doRemoveJob(jobId);
    }

    @Override
    public void removeAllJobs() throws Exception {
        this.doRemoveRange(0L, Long.MAX_VALUE);
    }

    @Override
    public void removeAllJobs(long start2, long finish) throws Exception {
        this.doRemoveRange(start2, finish);
    }

    @Override
    public long getNextScheduleTime() throws Exception {
        long nextExecutionTime = -1L;
        this.lock.readLock().lock();
        try {
            if (!this.jobs.isEmpty()) {
                nextExecutionTime = this.jobs.entrySet().iterator().next().getKey();
            }
        }
        finally {
            this.lock.readLock().unlock();
        }
        return nextExecutionTime;
    }

    @Override
    public List<Job> getNextScheduleJobs() throws Exception {
        ArrayList<Job> result = new ArrayList<Job>();
        this.lock.readLock().lock();
        try {
            if (!this.jobs.isEmpty()) {
                result.addAll(this.jobs.entrySet().iterator().next().getValue().getAllJobs());
            }
        }
        finally {
            this.lock.readLock().unlock();
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Job> getAllJobs() throws Exception {
        ArrayList<Job> result = new ArrayList<Job>();
        this.lock.readLock().lock();
        try {
            for (Map.Entry<Long, ScheduledTask> entry : this.jobs.entrySet()) {
                result.addAll(entry.getValue().getAllJobs());
            }
        }
        finally {
            this.lock.readLock().unlock();
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Job> getAllJobs(long start2, long finish) throws Exception {
        ArrayList<Job> result = new ArrayList<Job>();
        this.lock.readLock().lock();
        try {
            for (Map.Entry<Long, ScheduledTask> entry : this.jobs.entrySet()) {
                long jobTime = entry.getKey();
                if (start2 > jobTime || jobTime > finish) continue;
                result.addAll(entry.getValue().getAllJobs());
            }
        }
        finally {
            this.lock.readLock().unlock();
        }
        return result;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return "JobScheduler: " + this.name;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doSchedule(String jobId, ByteSequence payload, String cronEntry, long delay, long period, int repeat) throws IOException {
        long startTime = System.currentTimeMillis();
        long executionTime = 0L;
        startTime = (startTime + 500L) / 500L * 500L;
        if (cronEntry != null && cronEntry.length() > 0) {
            try {
                executionTime = CronParser.getNextScheduledTime(cronEntry, startTime);
            }
            catch (MessageFormatException e) {
                throw new IOException(e.getMessage());
            }
        }
        if (executionTime == 0L) {
            executionTime = startTime;
        }
        executionTime = delay > 0L ? (executionTime += delay) : (executionTime += period);
        InMemoryJob newJob = new InMemoryJob(jobId);
        newJob.setStart(startTime);
        newJob.setCronEntry(cronEntry);
        newJob.setDelay(delay);
        newJob.setPeriod(period);
        newJob.setRepeat(repeat);
        newJob.setNextTime(executionTime);
        newJob.setPayload(payload.getData());
        LOG.trace("JobScheduler adding job[{}] to fire at: {}", (Object)jobId, (Object)JobSupport.getDateTime(executionTime));
        this.lock.writeLock().lock();
        try {
            ScheduledTask task = this.jobs.get(executionTime);
            if (task == null) {
                task = new ScheduledTask(executionTime);
                task.add(newJob);
                this.jobs.put(task.getExecutionTime(), task);
                this.timer.schedule((TimerTask)task, new Date(newJob.getNextTime()));
            } else {
                task.add(newJob);
            }
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doReschedule(InMemoryJob job, long nextExecutionTime) {
        job.setNextTime(nextExecutionTime);
        job.incrementExecutionCount();
        if (!job.isCron()) {
            job.decrementRepeatCount();
        }
        LOG.trace("JobScheduler rescheduling job[{}] to fire at: {}", (Object)job.getJobId(), (Object)JobSupport.getDateTime(nextExecutionTime));
        this.lock.writeLock().lock();
        try {
            ScheduledTask task = this.jobs.get(nextExecutionTime);
            if (task == null) {
                task = new ScheduledTask(nextExecutionTime);
                task.add(job);
                this.jobs.put(task.getExecutionTime(), task);
                this.timer.schedule((TimerTask)task, new Date(task.getExecutionTime()));
            } else {
                task.add(job);
            }
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doRemoveJob(String jobId) throws IOException {
        this.lock.writeLock().lock();
        try {
            Iterator<Map.Entry<Long, ScheduledTask>> scheduled = this.jobs.entrySet().iterator();
            while (scheduled.hasNext()) {
                Map.Entry<Long, ScheduledTask> entry = scheduled.next();
                ScheduledTask task = entry.getValue();
                if (!task.remove(jobId)) continue;
                LOG.trace("JobScheduler removing job[{}]", (Object)jobId);
                if (task.isEmpty()) {
                    task.cancel();
                    scheduled.remove();
                }
                return;
            }
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doRemoveRange(long start2, long end) throws IOException {
        this.lock.writeLock().lock();
        try {
            Iterator<Map.Entry<Long, ScheduledTask>> scheduled = this.jobs.entrySet().iterator();
            while (scheduled.hasNext()) {
                Map.Entry<Long, ScheduledTask> entry = scheduled.next();
                long executionTime = entry.getKey();
                if (start2 <= executionTime && executionTime <= end) {
                    ScheduledTask task = entry.getValue();
                    task.cancel();
                    scheduled.remove();
                }
                if (end >= executionTime) continue;
                break;
            }
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    private boolean canDispatch() {
        return this.isStarted() && this.isDispatchEnabled();
    }

    private long calculateNextExecutionTime(InMemoryJob job, long currentTime, int repeat) throws MessageFormatException {
        long result = currentTime;
        String cron = job.getCronEntry();
        if (cron != null && cron.length() > 0) {
            result = CronParser.getNextScheduledTime(cron, result);
        } else if (job.getRepeat() != 0) {
            result += job.getPeriod();
        }
        return result;
    }

    private void dispatch(InMemoryJob job) throws IllegalStateException, IOException {
        if (this.canDispatch()) {
            LOG.debug("Firing: {}", (Object)job);
            for (JobListener l : this.jobListeners) {
                l.scheduledJob(job.getJobId(), new ByteSequence(job.getPayload()));
            }
        }
    }

    private class ScheduledTask
    extends TimerTask {
        private final Map<String, InMemoryJob> jobs = new TreeMap<String, InMemoryJob>();
        private final long executionTime;

        public ScheduledTask(long executionTime) {
            this.executionTime = executionTime;
        }

        public long getExecutionTime() {
            return this.executionTime;
        }

        public Collection<InMemoryJob> getAllJobs() {
            return new ArrayList<InMemoryJob>(this.jobs.values());
        }

        public boolean isEmpty() {
            return this.jobs.isEmpty();
        }

        public void add(InMemoryJob newJob) {
            this.jobs.put(newJob.getJobId(), newJob);
        }

        public boolean remove(String jobId) {
            return this.jobs.remove(jobId) != null;
        }

        @Override
        public void run() {
            if (!InMemoryJobScheduler.this.isStarted()) {
                return;
            }
            try {
                long currentTime = System.currentTimeMillis();
                InMemoryJobScheduler.this.lock.writeLock().lock();
                try {
                    InMemoryJobScheduler.this.jobs.remove(this.executionTime);
                }
                finally {
                    InMemoryJobScheduler.this.lock.writeLock().unlock();
                }
                long nextExecutionTime = 0L;
                for (InMemoryJob job : this.jobs.values()) {
                    if (InMemoryJobScheduler.this.isStarted()) {
                        int repeat = job.getRepeat();
                        nextExecutionTime = InMemoryJobScheduler.this.calculateNextExecutionTime(job, currentTime, repeat);
                        if (!job.isCron()) {
                            InMemoryJobScheduler.this.dispatch(job);
                            if (repeat == 0) continue;
                            InMemoryJobScheduler.this.doReschedule(job, nextExecutionTime);
                            continue;
                        }
                        if (repeat == 0) {
                            InMemoryJobScheduler.this.dispatch(job);
                        }
                        if (nextExecutionTime <= currentTime) continue;
                        InMemoryJobScheduler.this.doReschedule(job, nextExecutionTime);
                        if (repeat == 0) continue;
                        String jobId = ID_GENERATOR.generateId();
                        ByteSequence payload = new ByteSequence(job.getPayload());
                        InMemoryJobScheduler.this.schedule(jobId, payload, "", job.getDelay(), job.getPeriod(), job.getRepeat());
                        continue;
                    }
                    break;
                }
            }
            catch (Throwable e) {
                LOG.error("Error while processing scheduled job(s).", e);
            }
        }
    }
}

