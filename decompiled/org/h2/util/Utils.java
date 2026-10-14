/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.h2.util.IOUtils;
import org.h2.util.MathUtils;

public class Utils {
    public static final byte[] EMPTY_BYTES = new byte[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final HashMap<String, byte[]> RESOURCES = new HashMap();

    private Utils() {
    }

    public static int indexOf(byte[] byArray, byte[] byArray2, int n) {
        if (byArray2.length == 0) {
            return n;
        }
        if (n > byArray.length) {
            return -1;
        }
        int n2 = byArray.length - byArray2.length + 1;
        int n3 = byArray2.length;
        while (n < n2) {
            block5: {
                for (int i = 0; i < n3; ++i) {
                    if (byArray[n + i] == byArray2[i]) {
                        continue;
                    }
                    break block5;
                }
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int getByteArrayHash(byte[] byArray) {
        int n;
        int n2 = n = byArray.length;
        if (n < 50) {
            for (int i = 0; i < n; ++i) {
                n2 = 31 * n2 + byArray[i];
            }
        } else {
            int n3;
            int n4 = n / 16;
            for (n3 = 0; n3 < 4; ++n3) {
                n2 = 31 * n2 + byArray[n3];
                n2 = 31 * n2 + byArray[--n];
            }
            for (n3 = 4 + n4; n3 < n; n3 += n4) {
                n2 = 31 * n2 + byArray[n3];
            }
        }
        return n2;
    }

    public static boolean compareSecure(byte[] byArray, byte[] byArray2) {
        if (byArray == null || byArray2 == null) {
            return byArray == null && byArray2 == null;
        }
        int n = byArray.length;
        if (n != byArray2.length) {
            return false;
        }
        if (n == 0) {
            return true;
        }
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 |= byArray[i] ^ byArray2[i];
        }
        return n2 == 0;
    }

    public static byte[] copy(byte[] byArray, byte[] byArray2) {
        int n = byArray.length;
        if (n > byArray2.length) {
            byArray2 = new byte[n];
        }
        System.arraycopy(byArray, 0, byArray2, 0, n);
        return byArray2;
    }

    public static byte[] newBytes(int n) {
        if (n == 0) {
            return EMPTY_BYTES;
        }
        try {
            return new byte[n];
        }
        catch (OutOfMemoryError outOfMemoryError) {
            OutOfMemoryError outOfMemoryError2 = new OutOfMemoryError("Requested memory: " + n);
            outOfMemoryError2.initCause(outOfMemoryError);
            throw outOfMemoryError2;
        }
    }

    public static byte[] copyBytes(byte[] byArray, int n) {
        if (n == 0) {
            return EMPTY_BYTES;
        }
        try {
            return Arrays.copyOf(byArray, n);
        }
        catch (OutOfMemoryError outOfMemoryError) {
            OutOfMemoryError outOfMemoryError2 = new OutOfMemoryError("Requested memory: " + n);
            outOfMemoryError2.initCause(outOfMemoryError);
            throw outOfMemoryError2;
        }
    }

    public static byte[] cloneByteArray(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        int n = byArray.length;
        if (n == 0) {
            return EMPTY_BYTES;
        }
        return Arrays.copyOf(byArray, n);
    }

    public static long getMemoryUsed() {
        Utils.collectGarbage();
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory() >> 10;
    }

    public static long getMemoryFree() {
        Utils.collectGarbage();
        return Runtime.getRuntime().freeMemory() >> 10;
    }

    public static long getMemoryMax() {
        return Runtime.getRuntime().maxMemory() >> 10;
    }

    public static long getGarbageCollectionTime() {
        long l = 0L;
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            long l2 = garbageCollectorMXBean.getCollectionTime();
            if (l2 <= 0L) continue;
            l += l2;
        }
        return l;
    }

    public static long getGarbageCollectionCount() {
        long l = 0L;
        int n = 0;
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            long l2 = garbageCollectorMXBean.getCollectionTime();
            if (l2 <= 0L) continue;
            l += l2;
            n += garbageCollectorMXBean.getMemoryPoolNames().length;
        }
        n = Math.max(n, 1);
        return (l + (long)(n >> 1)) / (long)n;
    }

    public static synchronized void collectGarbage() {
        Runtime runtime = Runtime.getRuntime();
        long l = Utils.getGarbageCollectionCount();
        while (l == Utils.getGarbageCollectionCount()) {
            runtime.gc();
            Thread.yield();
        }
    }

    public static <T> ArrayList<T> newSmallArrayList() {
        return new ArrayList(4);
    }

    public static <X> void sortTopN(X[] XArray, int n, int n2, Comparator<? super X> comparator) {
        int n3 = XArray.length - 1;
        if (n3 > 0 && n2 > n) {
            Utils.partialQuickSort(XArray, 0, n3, comparator, n, n2 - 1);
            Arrays.sort(XArray, n, n2, comparator);
        }
    }

    private static <X> void partialQuickSort(X[] XArray, int n, int n2, Comparator<? super X> comparator, int n3, int n4) {
        if (n >= n3 && n2 <= n4) {
            return;
        }
        int n5 = n;
        int n6 = n2;
        int n7 = n + MathUtils.randomInt(n2 - n);
        X x = XArray[n7];
        int n8 = n + n2 >>> 1;
        X x2 = XArray[n8];
        XArray[n8] = x;
        XArray[n7] = x2;
        while (n5 <= n6) {
            while (comparator.compare(XArray[n5], x) < 0) {
                ++n5;
            }
            while (comparator.compare(XArray[n6], x) > 0) {
                --n6;
            }
            if (n5 > n6) continue;
            x2 = XArray[n5];
            XArray[n5++] = XArray[n6];
            XArray[n6--] = x2;
        }
        if (n < n6 && n3 <= n6) {
            Utils.partialQuickSort(XArray, n, n6, comparator, n3, n4);
        }
        if (n5 < n2 && n5 <= n4) {
            Utils.partialQuickSort(XArray, n5, n2, comparator, n3, n4);
        }
    }

    public static byte[] getResource(String string) throws IOException {
        byte[] byArray = RESOURCES.get(string);
        if (byArray == null && (byArray = Utils.loadResource(string)) != null) {
            RESOURCES.put(string, byArray);
        }
        return byArray;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] loadResource(String string) throws IOException {
        InputStream inputStream2 = Utils.class.getResourceAsStream("data.zip");
        if (inputStream2 == null) {
            inputStream2 = Utils.class.getResourceAsStream(string);
            if (inputStream2 != null) return IOUtils.readBytesAndClose(inputStream2, 0);
            return null;
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream2);){
            while (true) {
                ZipEntry zipEntry;
                if ((zipEntry = zipInputStream.getNextEntry()) == null) {
                    return null;
                }
                String string2 = zipEntry.getName();
                if (!string2.startsWith("/")) {
                    string2 = "/" + string2;
                }
                if (string2.equals(string)) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    IOUtils.copy(zipInputStream, (OutputStream)byteArrayOutputStream);
                    zipInputStream.closeEntry();
                    byte[] byArray = byteArrayOutputStream.toByteArray();
                    return byArray;
                }
                zipInputStream.closeEntry();
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return null;
    }

    public static Object callStaticMethod(String string, Object ... objectArray) throws Exception {
        int n = string.lastIndexOf(46);
        String string2 = string.substring(0, n);
        String string3 = string.substring(n + 1);
        return Utils.callMethod(null, Class.forName(string2), string3, objectArray);
    }

    public static Object callMethod(Object object, String string, Object ... objectArray) throws Exception {
        return Utils.callMethod(object, object.getClass(), string, objectArray);
    }

    private static Object callMethod(Object object, Class<?> clazz, String string, Object ... objectArray) throws Exception {
        Method method2 = null;
        int n = 0;
        boolean bl = object == null;
        for (Method method3 : clazz.getMethods()) {
            int n2;
            if (Modifier.isStatic(method3.getModifiers()) != bl || !method3.getName().equals(string) || (n2 = Utils.match(method3.getParameterTypes(), objectArray)) <= n) continue;
            n = n2;
            method2 = method3;
        }
        if (method2 == null) {
            throw new NoSuchMethodException(string);
        }
        return method2.invoke(object, objectArray);
    }

    public static Object newInstance(String string, Object ... objectArray) throws Exception {
        Constructor<?> constructor = null;
        int n = 0;
        for (Constructor<?> constructor2 : Class.forName(string).getConstructors()) {
            int n2 = Utils.match(constructor2.getParameterTypes(), objectArray);
            if (n2 <= n) continue;
            n = n2;
            constructor = constructor2;
        }
        if (constructor == null) {
            throw new NoSuchMethodException(string);
        }
        return constructor.newInstance(objectArray);
    }

    private static int match(Class<?>[] classArray, Object[] objectArray) {
        int n = classArray.length;
        if (n == objectArray.length) {
            int n2 = 1;
            for (int i = 0; i < n; ++i) {
                Class<?> clazz;
                Class<?> clazz2 = Utils.getNonPrimitiveClass(classArray[i]);
                Object object = objectArray[i];
                Class<?> clazz3 = clazz = object == null ? null : object.getClass();
                if (clazz2 == clazz) {
                    ++n2;
                    continue;
                }
                if (clazz == null || clazz2.isAssignableFrom(clazz)) continue;
                return 0;
            }
            return n2;
        }
        return 0;
    }

    public static Class<?> getNonPrimitiveClass(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return clazz;
        }
        if (clazz == Boolean.TYPE) {
            return Boolean.class;
        }
        if (clazz == Byte.TYPE) {
            return Byte.class;
        }
        if (clazz == Character.TYPE) {
            return Character.class;
        }
        if (clazz == Double.TYPE) {
            return Double.class;
        }
        if (clazz == Float.TYPE) {
            return Float.class;
        }
        if (clazz == Integer.TYPE) {
            return Integer.class;
        }
        if (clazz == Long.TYPE) {
            return Long.class;
        }
        if (clazz == Short.TYPE) {
            return Short.class;
        }
        if (clazz == Void.TYPE) {
            return Void.class;
        }
        return clazz;
    }

    public static boolean parseBoolean(String string, boolean bl, boolean bl2) {
        if (string == null) {
            return bl;
        }
        switch (string.length()) {
            case 1: {
                if (string.equals("1") || string.equalsIgnoreCase("t") || string.equalsIgnoreCase("y")) {
                    return true;
                }
                if (!string.equals("0") && !string.equalsIgnoreCase("f") && !string.equalsIgnoreCase("n")) break;
                return false;
            }
            case 2: {
                if (!string.equalsIgnoreCase("no")) break;
                return false;
            }
            case 3: {
                if (!string.equalsIgnoreCase("yes")) break;
                return true;
            }
            case 4: {
                if (!string.equalsIgnoreCase("true")) break;
                return true;
            }
            case 5: {
                if (!string.equalsIgnoreCase("false")) break;
                return false;
            }
        }
        if (bl2) {
            throw new IllegalArgumentException(string);
        }
        return bl;
    }

    public static String getProperty(String string, String string2) {
        try {
            return System.getProperty(string, string2);
        }
        catch (SecurityException securityException) {
            return string2;
        }
    }

    public static int getProperty(String string, int n) {
        String string2 = Utils.getProperty(string, null);
        if (string2 != null) {
            try {
                return Integer.decode(string2);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return n;
    }

    public static boolean getProperty(String string, boolean bl) {
        return Utils.parseBoolean(Utils.getProperty(string, null), bl, false);
    }

    public static int scaleForAvailableMemory(int n) {
        long l = Runtime.getRuntime().maxMemory();
        if (l != Long.MAX_VALUE) {
            return (int)((long)n * l / 0x40000000L);
        }
        try {
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
            Method method2 = Class.forName("com.sun.management.OperatingSystemMXBean").getMethod("getTotalPhysicalMemorySize", new Class[0]);
            long l2 = ((Number)method2.invoke((Object)operatingSystemMXBean, new Object[0])).longValue();
            return (int)((long)n * l2 / 0x40000000L);
        }
        catch (Exception exception) {
        }
        catch (Error error) {
            // empty catch block
        }
        return n;
    }

    public static long currentNanoTime() {
        long l = System.nanoTime();
        if (l == 0L) {
            l = 1L;
        }
        return l;
    }

    public static long currentNanoTimePlusMillis(int n) {
        return Utils.nanoTimePlusMillis(System.nanoTime(), n);
    }

    public static long nanoTimePlusMillis(long l, int n) {
        long l2 = l + (long)n * 1000000L;
        if (l2 == 0L) {
            l2 = 1L;
        }
        return l2;
    }

    public static ThreadPoolExecutor createSingleThreadExecutor(String string) {
        return Utils.createSingleThreadExecutor(string, new LinkedBlockingQueue<Runnable>());
    }

    public static ThreadPoolExecutor createSingleThreadExecutor(String string, BlockingQueue<Runnable> blockingQueue) {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, blockingQueue, runnable2 -> {
            Thread thread2 = new Thread(runnable2, string);
            thread2.setDaemon(true);
            return thread2;
        });
    }

    public static void flushExecutor(ThreadPoolExecutor threadPoolExecutor) {
        if (threadPoolExecutor != null) {
            try {
                threadPoolExecutor.submit(() -> {}).get();
            }
            catch (InterruptedException interruptedException) {
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                Utils.shutdownExecutor(threadPoolExecutor);
            }
            catch (ExecutionException executionException) {
                throw new RuntimeException(executionException);
            }
        }
    }

    public static void shutdownExecutor(ThreadPoolExecutor threadPoolExecutor) {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            try {
                threadPoolExecutor.awaitTermination(1L, TimeUnit.DAYS);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    public static interface ClassFactory {
        public boolean match(String var1);

        public Class<?> loadClass(String var1) throws ClassNotFoundException;
    }
}

