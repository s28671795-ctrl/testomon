/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class AbbaLockingDetector
implements Runnable {
    private final int tickIntervalMs = 2;
    private volatile boolean stop;
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private Thread thread;
    private final Map<String, Map<String, String>> lockOrdering = new WeakHashMap<String, Map<String, String>>();
    private final Set<String> knownDeadlocks = new HashSet<String>();

    public AbbaLockingDetector startCollecting() {
        this.thread = new Thread(this, "AbbaLockingDetector");
        this.thread.setDaemon(true);
        this.thread.start();
        return this;
    }

    public synchronized void reset() {
        this.lockOrdering.clear();
        this.knownDeadlocks.clear();
    }

    public AbbaLockingDetector stopCollecting() {
        this.stop = true;
        if (this.thread != null) {
            try {
                this.thread.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            this.thread = null;
        }
        return this;
    }

    @Override
    public void run() {
        while (!this.stop) {
            try {
                this.tick();
            }
            catch (Throwable throwable) {
                break;
            }
        }
    }

    private void tick() {
        try {
            Thread.sleep(2L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        ThreadInfo[] threadInfoArray = this.threadMXBean.dumpAllThreads(true, false);
        this.processThreadList(threadInfoArray);
    }

    private void processThreadList(ThreadInfo[] threadInfoArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (ThreadInfo threadInfo : threadInfoArray) {
            arrayList.clear();
            AbbaLockingDetector.generateOrdering(arrayList, threadInfo);
            if (arrayList.size() <= 1) continue;
            this.markHigher(arrayList, threadInfo);
        }
    }

    private static void generateOrdering(List<String> list, ThreadInfo threadInfo) {
        MonitorInfo[] monitorInfoArray = threadInfo.getLockedMonitors();
        Arrays.sort(monitorInfoArray, (monitorInfo, monitorInfo2) -> monitorInfo2.getLockedStackDepth() - monitorInfo.getLockedStackDepth());
        for (MonitorInfo monitorInfo3 : monitorInfoArray) {
            String string = AbbaLockingDetector.getObjectName(monitorInfo3);
            if (string.equals("sun.misc.Launcher$AppClassLoader") || list.contains(string)) continue;
            list.add(string);
        }
    }

    private synchronized void markHigher(List<String> list, ThreadInfo threadInfo) {
        String string = list.get(list.size() - 1);
        Map<String, String> map = this.lockOrdering.get(string);
        if (map == null) {
            map = new WeakHashMap<String, String>();
            this.lockOrdering.put(string, map);
        }
        String string2 = null;
        for (int i = 0; i < list.size() - 1; ++i) {
            String string3;
            String string4 = list.get(i);
            Map<String, String> map2 = this.lockOrdering.get(string4);
            boolean bl = false;
            if (map2 != null && (string3 = map2.get(string)) != null) {
                bl = true;
                String string5 = string + " " + string4;
                if (!this.knownDeadlocks.contains(string5)) {
                    System.out.println(string + " synchronized after \n " + string4 + ", but in the past before\nAFTER\n" + AbbaLockingDetector.getStackTraceForThread(threadInfo) + "BEFORE\n" + string3);
                    this.knownDeadlocks.add(string5);
                }
            }
            if (bl || map.containsKey(string4)) continue;
            if (string2 == null) {
                string2 = AbbaLockingDetector.getStackTraceForThread(threadInfo);
            }
            map.put(string4, string2);
        }
    }

    private static String getStackTraceForThread(ThreadInfo threadInfo) {
        StringBuilder stringBuilder = new StringBuilder().append('\"').append(threadInfo.getThreadName()).append("\" Id=").append(threadInfo.getThreadId()).append(' ').append((Object)threadInfo.getThreadState());
        if (threadInfo.getLockName() != null) {
            stringBuilder.append(" on ").append(threadInfo.getLockName());
        }
        if (threadInfo.getLockOwnerName() != null) {
            stringBuilder.append(" owned by \"").append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
        }
        if (threadInfo.isSuspended()) {
            stringBuilder.append(" (suspended)");
        }
        if (threadInfo.isInNative()) {
            stringBuilder.append(" (in native)");
        }
        stringBuilder.append('\n');
        StackTraceElement[] stackTraceElementArray = threadInfo.getStackTrace();
        MonitorInfo[] monitorInfoArray = threadInfo.getLockedMonitors();
        boolean bl = false;
        for (int i = 0; i < stackTraceElementArray.length; ++i) {
            StackTraceElement stackTraceElement = stackTraceElementArray[i];
            if (bl) {
                AbbaLockingDetector.dumpStackTraceElement(threadInfo, stringBuilder, i, stackTraceElement);
            }
            for (MonitorInfo monitorInfo : monitorInfoArray) {
                if (monitorInfo.getLockedStackDepth() != i) continue;
                if (!bl) {
                    AbbaLockingDetector.dumpStackTraceElement(threadInfo, stringBuilder, i, stackTraceElement);
                    bl = true;
                }
                stringBuilder.append("\t-  locked ").append(monitorInfo);
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    private static void dumpStackTraceElement(ThreadInfo threadInfo, StringBuilder stringBuilder, int n, StackTraceElement stackTraceElement) {
        stringBuilder.append('\t').append("at ").append(stackTraceElement).append('\n');
        if (n == 0 && threadInfo.getLockInfo() != null) {
            Thread.State state = threadInfo.getThreadState();
            switch (state) {
                case BLOCKED: {
                    stringBuilder.append("\t-  blocked on ").append(threadInfo.getLockInfo()).append('\n');
                    break;
                }
                case WAITING: {
                    stringBuilder.append("\t-  waiting on ").append(threadInfo.getLockInfo()).append('\n');
                    break;
                }
                case TIMED_WAITING: {
                    stringBuilder.append("\t-  waiting on ").append(threadInfo.getLockInfo()).append('\n');
                    break;
                }
            }
        }
    }

    private static String getObjectName(MonitorInfo monitorInfo) {
        return monitorInfo.getClassName() + "@" + Integer.toHexString(monitorInfo.getIdentityHashCode());
    }
}

