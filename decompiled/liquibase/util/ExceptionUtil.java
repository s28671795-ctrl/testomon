/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

public class ExceptionUtil {
    public static Throwable findExceptionInCauseChain(Throwable exceptionToSearchIn, Class<?> desiredCause) {
        if (exceptionToSearchIn == null || exceptionToSearchIn.getCause() == null) {
            return null;
        }
        if (desiredCause.isAssignableFrom(exceptionToSearchIn.getCause().getClass())) {
            return exceptionToSearchIn;
        }
        return ExceptionUtil.findExceptionInCauseChain(exceptionToSearchIn.getCause(), desiredCause);
    }

    private ExceptionUtil() {
    }
}

