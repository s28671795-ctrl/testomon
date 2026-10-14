/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import java.math.BigInteger;

public final class IOExceptionSupport {
    private IOExceptionSupport() {
    }

    public static IOException create(String msg, Throwable cause) {
        IOException exception = new IOException(msg);
        exception.initCause(cause);
        return exception;
    }

    public static IOException create(String msg, Exception cause) {
        IOException exception = new IOException(msg);
        exception.initCause(cause);
        return exception;
    }

    public static IOException create(Throwable cause) {
        IOException exception = new IOException(cause.getMessage());
        exception.initCause(cause);
        return exception;
    }

    public static IOException create(Exception cause) {
        IOException exception = new IOException(cause.getMessage());
        exception.initCause(cause);
        return exception;
    }

    public static IOException createFrameSizeException(int size, long maxSize) {
        return new IOException("Frame size of " + IOExceptionSupport.toHumanReadableSizeString(size) + " larger than max allowed " + IOExceptionSupport.toHumanReadableSizeString(maxSize));
    }

    private static String toHumanReadableSizeString(int size) {
        return IOExceptionSupport.toHumanReadableSizeString(BigInteger.valueOf(size));
    }

    private static String toHumanReadableSizeString(long size) {
        return IOExceptionSupport.toHumanReadableSizeString(BigInteger.valueOf(size));
    }

    private static String toHumanReadableSizeString(BigInteger size) {
        BigInteger ONE_MB_BI;
        BigInteger ONE_KB_BI = BigInteger.valueOf(1024L);
        BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI));
        String displaySize = size.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_GB_BI)) + " GB" : (size.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_MB_BI)) + " MB" : (size.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0 ? String.valueOf(size.divide(ONE_KB_BI)) + " KB" : String.valueOf(size) + " bytes"));
        return displaySize;
    }
}

