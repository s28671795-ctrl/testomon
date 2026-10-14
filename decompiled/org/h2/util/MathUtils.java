/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {
    static SecureRandom secureRandom;
    static volatile boolean seeded;

    private MathUtils() {
    }

    public static int roundUpInt(int n, int n2) {
        return n + n2 - 1 & -n2;
    }

    public static long roundUpLong(long l, long l2) {
        return l + l2 - 1L & -l2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static synchronized SecureRandom getSecureRandom() {
        block10: {
            if (secureRandom != null) {
                return secureRandom;
            }
            try {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
                Runnable runnable2 = () -> {
                    try {
                        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                        byte[] byArray = secureRandom.generateSeed(20);
                        SecureRandom secureRandom2 = secureRandom;
                        synchronized (secureRandom2) {
                            secureRandom.setSeed(byArray);
                            seeded = true;
                        }
                    }
                    catch (Exception exception) {
                        MathUtils.warn("SecureRandom", exception);
                    }
                };
                try {
                    Thread thread2 = new Thread(runnable2, "Generate Seed");
                    thread2.setDaemon(true);
                    thread2.start();
                    Thread.yield();
                    try {
                        thread2.join(400L);
                    }
                    catch (InterruptedException interruptedException) {
                        MathUtils.warn("InterruptedException", interruptedException);
                    }
                    if (seeded) break block10;
                    byte[] byArray = MathUtils.generateAlternativeSeed();
                    SecureRandom secureRandom = MathUtils.secureRandom;
                    synchronized (secureRandom) {
                        MathUtils.secureRandom.setSeed(byArray);
                    }
                }
                catch (SecurityException securityException) {
                    runnable2.run();
                    MathUtils.generateAlternativeSeed();
                }
            }
            catch (Exception exception) {
                MathUtils.warn("SecureRandom", exception);
                secureRandom = new SecureRandom();
            }
        }
        return secureRandom;
    }

    public static byte[] generateAlternativeSeed() {
        try {
            Object object;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeLong(System.currentTimeMillis());
            dataOutputStream.writeLong(System.nanoTime());
            dataOutputStream.writeInt(new Object().hashCode());
            Runtime runtime = Runtime.getRuntime();
            dataOutputStream.writeLong(runtime.freeMemory());
            dataOutputStream.writeLong(runtime.maxMemory());
            dataOutputStream.writeLong(runtime.totalMemory());
            try {
                object = System.getProperties().toString();
                dataOutputStream.writeInt(((String)object).length());
                dataOutputStream.write(((String)object).getBytes(StandardCharsets.UTF_8));
            }
            catch (Exception exception) {
                MathUtils.warn("generateAlternativeSeed", exception);
            }
            try {
                object = Class.forName("java.net.InetAddress");
                Object object2 = ((Class)object).getMethod("getLocalHost", new Class[0]).invoke(null, new Object[0]);
                String string = ((Class)object).getMethod("getHostName", new Class[0]).invoke(object2, new Object[0]).toString();
                dataOutputStream.writeUTF(string);
                Object[] objectArray = (Object[])((Class)object).getMethod("getAllByName", String.class).invoke(null, string);
                Method method2 = ((Class)object).getMethod("getAddress", new Class[0]);
                for (Object object3 : objectArray) {
                    dataOutputStream.write((byte[])method2.invoke(object3, new Object[0]));
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            for (int i = 0; i < 16; ++i) {
                int n = 0;
                long l = System.currentTimeMillis();
                while (l == System.currentTimeMillis()) {
                    ++n;
                }
                dataOutputStream.writeInt(n);
            }
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            MathUtils.warn("generateAlternativeSeed", iOException);
            return new byte[1];
        }
    }

    static void warn(String string, Throwable throwable) {
        System.out.println("Warning: " + string);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    public static int nextPowerOf2(int n) throws IllegalArgumentException {
        if (n + Integer.MIN_VALUE > -1073741824) {
            throw new IllegalArgumentException("Argument out of range [0x0-0x40000000]. Argument was: " + n);
        }
        return n <= 1 ? 1 : (-1 >>> Integer.numberOfLeadingZeros(n - 1)) + 1;
    }

    public static int convertLongToInt(long l) {
        if (l <= Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if (l >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)l;
    }

    public static short convertIntToShort(int n) {
        if (n <= Short.MIN_VALUE) {
            return Short.MIN_VALUE;
        }
        if (n >= Short.MAX_VALUE) {
            return Short.MAX_VALUE;
        }
        return (short)n;
    }

    public static long secureRandomLong() {
        return MathUtils.getSecureRandom().nextLong();
    }

    public static void randomBytes(byte[] byArray) {
        ThreadLocalRandom.current().nextBytes(byArray);
    }

    public static byte[] secureRandomBytes(int n) {
        if (n <= 0) {
            n = 1;
        }
        byte[] byArray = new byte[n];
        MathUtils.getSecureRandom().nextBytes(byArray);
        return byArray;
    }

    public static int randomInt(int n) {
        return ThreadLocalRandom.current().nextInt(n);
    }

    public static int secureRandomInt(int n) {
        return MathUtils.getSecureRandom().nextInt(n);
    }
}

