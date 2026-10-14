/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

import io.grpc.netty.shaded.io.netty.internal.tcnative.CertificateCallback;
import io.grpc.netty.shaded.io.netty.internal.tcnative.CertificateCallbackTask;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSL;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLPrivateKeyMethodDecryptTask;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLPrivateKeyMethodSignTask;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLPrivateKeyMethodTask;
import io.grpc.netty.shaded.io.netty.internal.tcnative.SSLTask;
import java.io.File;

public final class Library {
    private static final String[] NAMES = new String[]{"netty_tcnative", "libnetty_tcnative"};
    private static final String PROVIDED = "provided";
    private static Library _instance = null;

    private static void tryLoadClasses(ClassLoader classLoader, Class<?> ... classes) {
        for (Class<?> clazz : classes) {
            Library.tryLoadClass(classLoader, clazz.getName());
        }
    }

    private static void tryLoadClass(ClassLoader classLoader, String className) {
        try {
            Class.forName(className, true, classLoader);
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
    }

    private Library() throws Exception {
        boolean loaded = false;
        String path = System.getProperty("java.library.path");
        String[] paths = path.split(File.pathSeparator);
        StringBuilder err = new StringBuilder();
        for (int i = 0; i < NAMES.length; ++i) {
            try {
                Library.loadLibrary(NAMES[i]);
                loaded = true;
            }
            catch (ThreadDeath t2) {
                throw t2;
            }
            catch (VirtualMachineError t3) {
                throw t3;
            }
            catch (Throwable t4) {
                String name = System.mapLibraryName(NAMES[i]);
                for (int j = 0; j < paths.length; ++j) {
                    File fd = new File(paths[j], name);
                    if (!fd.exists()) continue;
                    throw new RuntimeException(t4);
                }
                if (i > 0) {
                    err.append(", ");
                }
                err.append(t4.getMessage());
            }
            if (loaded) break;
        }
        if (!loaded) {
            throw new UnsatisfiedLinkError(err.toString());
        }
    }

    private Library(String libraryName) {
        if (!PROVIDED.equals(libraryName)) {
            Library.loadLibrary(libraryName);
        }
    }

    private static void loadLibrary(String libraryName) {
        System.loadLibrary(Library.calculatePackagePrefix().replace('.', '_') + libraryName);
    }

    private static String calculatePackagePrefix() {
        String expected;
        String maybeShaded = Library.class.getName();
        if (!maybeShaded.endsWith(expected = "io!netty!internal!tcnative!Library".replace('!', '.'))) {
            throw new UnsatisfiedLinkError(String.format("Could not find prefix added to %s to get %s. When shading, only adding a package prefix is supported", expected, maybeShaded));
        }
        return maybeShaded.substring(0, maybeShaded.length() - expected.length());
    }

    public static boolean initialize() throws Exception {
        return Library.initialize(PROVIDED, null);
    }

    public static boolean initialize(String libraryName, String engine) throws Exception {
        if (_instance == null) {
            _instance = libraryName == null ? new Library() : new Library(libraryName);
        }
        SSL.initialize(engine);
        return true;
    }

    static {
        Library.tryLoadClasses(ClassLoader.getSystemClassLoader(), Exception.class, NullPointerException.class, IllegalArgumentException.class, OutOfMemoryError.class, String.class, byte[].class, SSLTask.class, CertificateCallbackTask.class, CertificateCallback.class, SSLPrivateKeyMethodTask.class, SSLPrivateKeyMethodSignTask.class, SSLPrivateKeyMethodDecryptTask.class);
    }
}

