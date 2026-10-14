/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static String exceptionStackTraceAsString(Throwable t2) {
        StringWriter sw = new StringWriter();
        t2.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static <T extends Exception> void conditionallyReThrow(T e, boolean rethrow, Logger logger2, String m4, Level level) throws T {
        if (rethrow) {
            throw e;
        }
        logger2.log(level, m4, e);
    }
}

