/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.h2.util.StringUtils;
import org.h2.util.Utils10;

public class Profiler
implements Runnable {
    private static Instrumentation instrumentation;
    private static final String LINE_SEPARATOR;
    private static final int MAX_ELEMENTS = 1000;
    public int interval = 2;
    public int depth = 48;
    public boolean paused;
    public boolean sumClasses;
    public boolean sumMethods;
    private int pid;
    private final String[] ignoreLines = "java,sun,com.sun.,com.google.common.,com.mongodb.,org.bson.,".split(",");
    private final String[] ignorePackages = "java,sun,com.sun.,com.google.common.,com.mongodb.,org.bson".split(",");
    private final String[] ignoreThreads = "java.lang.Object.wait,java.lang.Thread.dumpThreads,java.lang.Thread.getThreads,java.lang.Thread.sleep,java.lang.UNIXProcess.waitForProcessExit,java.net.PlainDatagramSocketImpl.receive0,java.net.PlainSocketImpl.accept,java.net.PlainSocketImpl.socketAccept,java.net.SocketInputStream.socketRead,java.net.SocketOutputStream.socketWrite,org.eclipse.jetty.io.nio.SelectorManager$SelectSet.doSelect,sun.awt.windows.WToolkit.eventLoop,sun.misc.Unsafe.park,sun.nio.ch.EPollArrayWrapper.epollWait,sun.nio.ch.KQueueArrayWrapper.kevent0,sun.nio.ch.ServerSocketChannelImpl.accept,dalvik.system.VMStack.getThreadStackTrace,dalvik.system.NativeStart.run".split(",");
    private volatile boolean stop;
    private final HashMap<String, Integer> counts = new HashMap();
    private final HashMap<String, Integer> summary = new HashMap();
    private int minCount = 1;
    private int total;
    private Thread thread;
    private long start;
    private long time;
    private int threadDumps;

    public static void premain(String string, Instrumentation instrumentation) {
        Profiler.instrumentation = instrumentation;
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public static void main(String ... stringArray) {
        new Profiler().run(stringArray);
    }

    private void run(String ... stringArray) {
        if (stringArray.length == 0) {
            System.out.println("Show profiling data");
            System.out.println("Usage: java " + this.getClass().getName() + " <pid> | <stackTraceFileNames>");
            System.out.println("Processes:");
            String string = Profiler.exec("jps", "-l");
            System.out.println(string);
            return;
        }
        this.start = System.nanoTime();
        if (stringArray[0].matches("\\d+")) {
            this.pid = Integer.parseInt(stringArray[0]);
            long l = 0L;
            while (true) {
                this.tick();
                long l2 = System.nanoTime();
                if (l2 - l <= TimeUnit.SECONDS.toNanos(5L)) continue;
                this.time = System.nanoTime() - this.start;
                System.out.println(this.getTopTraces(3));
                l = l2;
            }
        }
        try {
            for (String string : stringArray) {
                if (string.startsWith("-")) {
                    if ("-classes".equals(string)) {
                        this.sumClasses = true;
                        continue;
                    }
                    if ("-methods".equals(string)) {
                        this.sumMethods = true;
                        continue;
                    }
                    if ("-packages".equals(string)) {
                        this.sumClasses = false;
                        this.sumMethods = false;
                        continue;
                    }
                    throw new IllegalArgumentException(string);
                }
                Path path = Paths.get(string, new String[0]);
                try (BufferedReader bufferedReader = Files.newBufferedReader(path);){
                    String string2;
                    LineNumberReader lineNumberReader = new LineNumberReader(bufferedReader);
                    while ((string2 = lineNumberReader.readLine()) != null) {
                        if (!string2.startsWith("Full thread dump")) continue;
                        ++this.threadDumps;
                    }
                }
                bufferedReader = Files.newBufferedReader(path);
                var8_12 = null;
                try {
                    this.processList(Profiler.readStackTrace(new LineNumberReader(bufferedReader)));
                }
                catch (Throwable throwable) {
                    var8_12 = throwable;
                    throw throwable;
                }
                finally {
                    if (bufferedReader != null) {
                        if (var8_12 != null) {
                            try {
                                ((Reader)bufferedReader).close();
                            }
                            catch (Throwable throwable) {
                                var8_12.addSuppressed(throwable);
                            }
                        } else {
                            ((Reader)bufferedReader).close();
                        }
                    }
                }
            }
            System.out.println(this.getTopTraces(5));
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static List<Object[]> getRunnableStackTraces() {
        ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
            StackTraceElement[] stackTraceElementArray;
            Thread thread2 = entry.getKey();
            if (thread2.getState() != Thread.State.RUNNABLE || (stackTraceElementArray = entry.getValue()) == null || stackTraceElementArray.length == 0) continue;
            arrayList.add(stackTraceElementArray);
        }
        return arrayList;
    }

    private static List<Object[]> readRunnableStackTraces(int n) {
        try {
            String string = Profiler.exec("jstack", Integer.toString(n));
            LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(string));
            return Profiler.readStackTrace(lineNumberReader);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static List<Object[]> readStackTrace(LineNumberReader lineNumberReader) throws IOException {
        String string;
        ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
        while ((string = lineNumberReader.readLine()) != null) {
            if (!string.startsWith("\"")) continue;
            string = lineNumberReader.readLine();
            if (string == null) break;
            if (!(string = string.trim()).startsWith("java.lang.Thread.State: RUNNABLE")) continue;
            ArrayList<String> arrayList2 = new ArrayList<String>();
            while ((string = lineNumberReader.readLine()) != null) {
                if ((string = string.trim()).startsWith("- ")) continue;
                if (!string.startsWith("at ")) break;
                string = StringUtils.trimSubstring(string, 3);
                arrayList2.add(string);
            }
            if (arrayList2.isEmpty()) continue;
            String[] stringArray = arrayList2.toArray(new String[0]);
            arrayList.add(stringArray);
        }
        return arrayList;
    }

    private static String exec(String ... stringArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        try {
            Process process = Runtime.getRuntime().exec(stringArray);
            Profiler.copyInThread(process.getInputStream(), byteArrayOutputStream2);
            Profiler.copyInThread(process.getErrorStream(), byteArrayOutputStream);
            process.waitFor();
            String string = Utils10.byteArrayOutputStreamToString(byteArrayOutputStream, StandardCharsets.UTF_8);
            if (string.length() > 0) {
                throw new RuntimeException(string);
            }
            return Utils10.byteArrayOutputStreamToString(byteArrayOutputStream2, StandardCharsets.UTF_8);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static void copyInThread(final InputStream inputStream2, final OutputStream outputStream2) {
        new Thread("Profiler stream copy"){

            @Override
            public void run() {
                byte[] byArray = new byte[4096];
                try {
                    int n;
                    while ((n = inputStream2.read(byArray, 0, byArray.length)) >= 0) {
                        outputStream2.write(byArray, 0, n);
                    }
                }
                catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        }.start();
    }

    public Profiler startCollecting() {
        this.thread = new Thread(this, "Profiler");
        this.thread.setDaemon(true);
        this.thread.start();
        return this;
    }

    public Profiler stopCollecting() {
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
        this.start = System.nanoTime();
        while (!this.stop) {
            try {
                this.tick();
            }
            catch (Throwable throwable) {
                // empty catch block
                break;
            }
        }
        this.time = System.nanoTime() - this.start;
    }

    private void tick() {
        if (this.interval > 0) {
            if (this.paused) {
                return;
            }
            try {
                Thread.sleep(this.interval, 0);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        List<Object[]> list = this.pid != 0 ? Profiler.readRunnableStackTraces(this.pid) : Profiler.getRunnableStackTraces();
        ++this.threadDumps;
        this.processList(list);
    }

    private void processList(List<Object[]> list) {
        for (Object[] objectArray : list) {
            if (Profiler.startsWithAny(objectArray[0].toString(), this.ignoreThreads)) continue;
            StringBuilder stringBuilder = new StringBuilder();
            String string = null;
            boolean bl = false;
            int n = 0;
            for (int i = 0; i < objectArray.length && n < this.depth; ++i) {
                String string2 = objectArray[i].toString();
                if (string2.equals(string) || Profiler.startsWithAny(string2, this.ignoreLines)) continue;
                string = string2;
                stringBuilder.append("at ").append(string2).append(LINE_SEPARATOR);
                if (!bl && !Profiler.startsWithAny(string2, this.ignorePackages)) {
                    int n2;
                    int n3;
                    bl = true;
                    for (n3 = 0; n3 < string2.length() && (n2 = string2.charAt(n3)) != 40 && !Character.isUpperCase((char)n2); ++n3) {
                    }
                    if (n3 > 0 && string2.charAt(n3 - 1) == '.') {
                        --n3;
                    }
                    if (this.sumClasses) {
                        n2 = string2.indexOf(46, n3 + 1);
                        int n4 = n3 = n2 >= 0 ? n2 : n3;
                    }
                    if (this.sumMethods) {
                        n2 = string2.indexOf(40, n3 + 1);
                        n3 = n2 >= 0 ? n2 : n3;
                    }
                    String string3 = string2.substring(0, n3);
                    Profiler.increment(this.summary, string3, 0);
                }
                ++n;
            }
            if (stringBuilder.length() <= 0) continue;
            this.minCount = Profiler.increment(this.counts, stringBuilder.toString().trim(), this.minCount);
            ++this.total;
        }
    }

    private static boolean startsWithAny(String string, String[] stringArray) {
        for (String string2 : stringArray) {
            if (string2.length() <= 0 || !string.startsWith(string2)) continue;
            return true;
        }
        return false;
    }

    private static int increment(HashMap<String, Integer> hashMap, String string, int n) {
        Integer n2 = hashMap.get(string);
        if (n2 == null) {
            hashMap.put(string, 1);
        } else {
            hashMap.put(string, n2 + 1);
        }
        while (hashMap.size() > 1000) {
            Iterator<Map.Entry<String, Integer>> iterator2 = hashMap.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<String, Integer> entry = iterator2.next();
                if (entry.getValue() > n) continue;
                iterator2.remove();
            }
            if (hashMap.size() <= 1000) continue;
            ++n;
        }
        return n;
    }

    public String getTop(int n) {
        this.stopCollecting();
        return this.getTopTraces(n);
    }

    private String getTopTraces(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Profiler: top ").append(n).append(" stack trace(s) of ");
        if (this.time > 0L) {
            stringBuilder.append(" of ").append(TimeUnit.NANOSECONDS.toMillis(this.time)).append(" ms");
        }
        if (this.threadDumps > 0) {
            stringBuilder.append(" of ").append(this.threadDumps).append(" thread dumps");
        }
        stringBuilder.append(":").append(LINE_SEPARATOR);
        if (this.counts.size() == 0) {
            stringBuilder.append("(none)").append(LINE_SEPARATOR);
        }
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>(this.counts);
        Profiler.appendTop(stringBuilder, hashMap, n, this.total, false);
        stringBuilder.append("summary:").append(LINE_SEPARATOR);
        hashMap = new HashMap<String, Integer>(this.summary);
        Profiler.appendTop(stringBuilder, hashMap, n, this.total, true);
        stringBuilder.append('.');
        return stringBuilder.toString();
    }

    private static void appendTop(StringBuilder stringBuilder, HashMap<String, Integer> hashMap, int n, int n2, boolean bl) {
        int n3 = 0;
        int n4 = 0;
        while (true) {
            int n5 = 0;
            Map.Entry<String, Integer> entry = null;
            for (Map.Entry<String, Integer> entry2 : hashMap.entrySet()) {
                if (entry2.getValue() <= n5) continue;
                entry = entry2;
                n5 = entry2.getValue();
            }
            if (entry == null) break;
            hashMap.remove(entry.getKey());
            if (++n3 >= n) {
                if (entry.getValue() < n4) break;
                n4 = entry.getValue();
            }
            int n6 = entry.getValue();
            int n7 = 100 * n6 / Math.max(n2, 1);
            if (bl) {
                if (n7 <= 1) continue;
                stringBuilder.append(n7).append("%: ").append(entry.getKey()).append(LINE_SEPARATOR);
                continue;
            }
            stringBuilder.append(n6).append('/').append(n2).append(" (").append(n7).append("%):").append(LINE_SEPARATOR).append(entry.getKey()).append(LINE_SEPARATOR);
        }
    }

    static {
        LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    }
}

