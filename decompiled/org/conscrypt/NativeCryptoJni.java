/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.conscrypt.HostProperties;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeLibraryLoader;

final class NativeCryptoJni {
    private static final String STATIC_LIB_NAME = "conscrypt";
    private static final String DYNAMIC_LIB_NAME_PREFIX = "conscrypt_openjdk_jni";

    static void init() throws UnsatisfiedLinkError {
        ArrayList<NativeLibraryLoader.LoadResult> results = new ArrayList<NativeLibraryLoader.LoadResult>();
        if (!NativeLibraryLoader.loadFirstAvailable(NativeCryptoJni.classLoader(), results, NativeCryptoJni.platformLibName(), DYNAMIC_LIB_NAME_PREFIX, STATIC_LIB_NAME)) {
            NativeCryptoJni.logResults(results);
            NativeCryptoJni.throwBestError(results);
        }
    }

    private NativeCryptoJni() {
    }

    private static void logResults(List<NativeLibraryLoader.LoadResult> results) {
        for (NativeLibraryLoader.LoadResult result : results) {
            result.log();
        }
    }

    private static void throwBestError(List<NativeLibraryLoader.LoadResult> results) {
        Collections.sort(results, ErrorComparator.INSTANCE);
        Throwable bestError = results.get((int)0).error;
        for (NativeLibraryLoader.LoadResult result : results.subList(1, results.size())) {
            bestError.addSuppressed(result.error);
        }
        if (bestError instanceof Error) {
            throw (Error)bestError;
        }
        throw (Error)new UnsatisfiedLinkError(bestError.getMessage()).initCause(bestError);
    }

    private static ClassLoader classLoader() {
        return NativeCrypto.class.getClassLoader();
    }

    private static String platformLibName() {
        return "conscrypt_openjdk_jni-" + NativeCryptoJni.osName() + '-' + NativeCryptoJni.archName();
    }

    private static String osName() {
        return HostProperties.OS.getFileComponent();
    }

    private static String archName() {
        return HostProperties.ARCH.getFileComponent();
    }

    private static final class ErrorComparator
    implements Comparator<NativeLibraryLoader.LoadResult> {
        static final ErrorComparator INSTANCE = new ErrorComparator();

        private ErrorComparator() {
        }

        @Override
        public int compare(NativeLibraryLoader.LoadResult o1, NativeLibraryLoader.LoadResult o2) {
            int value2;
            Throwable e1 = o1.error;
            Throwable e2 = o2.error;
            int value1 = e1 instanceof UnsatisfiedLinkError ? 1 : 0;
            int n = value2 = e2 instanceof UnsatisfiedLinkError ? 1 : 0;
            if (value1 != value2) {
                return value2 - value1;
            }
            String m1 = e1.getMessage();
            String m22 = e2.getMessage();
            value1 = m1 != null && m1.contains("java.library.path") ? 0 : 1;
            value2 = m22 != null && m22.contains("java.library.path") ? 0 : 1;
            return value2 - value1;
        }
    }
}

