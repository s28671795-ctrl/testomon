/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.h2.engine.SysProperties;
import org.h2.mvstore.db.MVTable;

public class ThreadDeadlockDetector {
    private static final String INDENT = "    ";
    private static ThreadDeadlockDetector detector;
    private final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

    private ThreadDeadlockDetector() {
        Timer timer = new Timer("ThreadDeadlockDetector", true);
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                ThreadDeadlockDetector.this.checkForDeadlocks();
            }
        }, 10L, 10000L);
    }

    public static synchronized void init() {
        if (detector == null) {
            detector = new ThreadDeadlockDetector();
        }
    }

    void checkForDeadlocks() {
        long[] lArray = this.threadBean.findDeadlockedThreads();
        if (lArray == null) {
            return;
        }
        ThreadDeadlockDetector.dumpThreadsAndLocks("ThreadDeadlockDetector - deadlock found :", this.threadBean, lArray, System.out);
    }

    public static void dumpAllThreadsAndLocks(String string) {
        ThreadDeadlockDetector.dumpAllThreadsAndLocks(string, System.out);
    }

    public static void dumpAllThreadsAndLocks(String string, PrintStream printStream) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] lArray = threadMXBean.getAllThreadIds();
        ThreadDeadlockDetector.dumpThreadsAndLocks(string, threadMXBean, lArray, printStream);
    }

    private static void dumpThreadsAndLocks(String string, ThreadMXBean threadMXBean, long[] lArray, PrintStream printStream) {
        ThreadInfo[] threadInfoArray;
        HashMap<Long, ArrayList<String>> hashMap;
        HashMap<Long, ArrayList<String>> hashMap2;
        HashMap<Long, String> hashMap3;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println(string);
        if (SysProperties.THREAD_DEADLOCK_DETECTOR) {
            hashMap3 = MVTable.WAITING_FOR_LOCK.getSnapshotOfAllThreads();
            hashMap2 = MVTable.EXCLUSIVE_LOCKS.getSnapshotOfAllThreads();
            hashMap = MVTable.SHARED_LOCKS.getSnapshotOfAllThreads();
        } else {
            hashMap3 = new HashMap();
            hashMap2 = new HashMap();
            hashMap = new HashMap();
        }
        for (ThreadInfo threadInfo : threadInfoArray = threadMXBean.getThreadInfo(lArray, true, true)) {
            ThreadDeadlockDetector.printThreadInfo(printWriter, threadInfo);
            ThreadDeadlockDetector.printLockInfo(printWriter, threadInfo.getLockedSynchronizers(), hashMap3.get(threadInfo.getThreadId()), hashMap2.get(threadInfo.getThreadId()), hashMap.get(threadInfo.getThreadId()));
        }
        printWriter.flush();
        printStream.println(stringWriter.getBuffer());
        printStream.flush();
    }

    private static void printThreadInfo(PrintWriter printWriter, ThreadInfo threadInfo) {
        ThreadDeadlockDetector.printThread(printWriter, threadInfo);
        StackTraceElement[] stackTraceElementArray = threadInfo.getStackTrace();
        MonitorInfo[] monitorInfoArray = threadInfo.getLockedMonitors();
        for (int i = 0; i < stackTraceElementArray.length; ++i) {
            StackTraceElement stackTraceElement = stackTraceElementArray[i];
            printWriter.println("    at " + stackTraceElement.toString());
            for (MonitorInfo monitorInfo : monitorInfoArray) {
                if (monitorInfo.getLockedStackDepth() != i) continue;
                printWriter.println("      - locked " + monitorInfo);
            }
        }
        printWriter.println();
    }

    private static void printThread(PrintWriter printWriter, ThreadInfo threadInfo) {
        printWriter.print("\"" + threadInfo.getThreadName() + "\" Id=" + threadInfo.getThreadId() + " in " + (Object)((Object)threadInfo.getThreadState()));
        if (threadInfo.getLockName() != null) {
            printWriter.append(" on lock=").append(threadInfo.getLockName());
        }
        if (threadInfo.isSuspended()) {
            printWriter.append(" (suspended)");
        }
        if (threadInfo.isInNative()) {
            printWriter.append(" (running in native)");
        }
        printWriter.println();
        if (threadInfo.getLockOwnerName() != null) {
            printWriter.println("     owned by " + threadInfo.getLockOwnerName() + " Id=" + threadInfo.getLockOwnerId());
        }
    }

    private static void printLockInfo(PrintWriter printWriter, LockInfo[] lockInfoArray, String string, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        printWriter.println("    Locked synchronizers: count = " + lockInfoArray.length);
        for (LockInfo lockInfo : lockInfoArray) {
            printWriter.println("      - " + lockInfo);
        }
        if (string != null) {
            printWriter.println("    Waiting for table: " + string);
        }
        if (arrayList != null) {
            printWriter.println("    Exclusive table locks: count = " + arrayList.size());
            for (String string2 : arrayList) {
                printWriter.println("      - " + string2);
            }
        }
        if (arrayList2 != null) {
            printWriter.println("    Shared table locks: count = " + arrayList2.size());
            for (String string3 : arrayList2) {
                printWriter.println("      - " + string3);
            }
        }
        printWriter.println();
    }
}

