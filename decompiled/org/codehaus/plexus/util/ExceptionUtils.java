/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class ExceptionUtils {
    static final String WRAPPED_MARKER = " [wrapped] ";
    protected static String[] CAUSE_METHOD_NAMES = new String[]{"getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested"};

    protected ExceptionUtils() {
    }

    public static void addCauseMethodName(String methodName) {
        if (methodName != null && methodName.length() > 0) {
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(CAUSE_METHOD_NAMES));
            list.add(methodName);
            CAUSE_METHOD_NAMES = list.toArray(new String[0]);
        }
    }

    public static Throwable getCause(Throwable throwable) {
        return ExceptionUtils.getCause(throwable, CAUSE_METHOD_NAMES);
    }

    public static Throwable getCause(Throwable throwable, String[] methodNames) {
        Throwable cause = ExceptionUtils.getCauseUsingWellKnownTypes(throwable);
        if (cause == null) {
            String methodName;
            String[] stringArray = methodNames;
            int n = stringArray.length;
            for (int i = 0; i < n && (cause = ExceptionUtils.getCauseUsingMethodName(throwable, methodName = stringArray[i])) == null; ++i) {
            }
            if (cause == null) {
                cause = ExceptionUtils.getCauseUsingFieldName(throwable, "detail");
            }
        }
        return cause;
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause = ExceptionUtils.getCause(throwable);
        if (cause != null) {
            throwable = cause;
            while ((throwable = ExceptionUtils.getCause(throwable)) != null) {
                cause = throwable;
            }
        }
        return cause;
    }

    private static Throwable getCauseUsingWellKnownTypes(Throwable throwable) {
        if (throwable instanceof SQLException) {
            return ((SQLException)throwable).getNextException();
        }
        if (throwable instanceof InvocationTargetException) {
            return ((InvocationTargetException)throwable).getTargetException();
        }
        return null;
    }

    private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName) {
        Method method2 = null;
        try {
            method2 = throwable.getClass().getMethod(methodName, null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        if (method2 != null && Throwable.class.isAssignableFrom(method2.getReturnType())) {
            try {
                return (Throwable)method2.invoke((Object)throwable, new Object[0]);
            }
            catch (IllegalAccessException illegalAccessException) {
            }
            catch (IllegalArgumentException illegalArgumentException) {
            }
            catch (InvocationTargetException invocationTargetException) {
                // empty catch block
            }
        }
        return null;
    }

    private static Throwable getCauseUsingFieldName(Throwable throwable, String fieldName) {
        Field field2 = null;
        try {
            field2 = throwable.getClass().getField(fieldName);
        }
        catch (NoSuchFieldException noSuchFieldException) {
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        if (field2 != null && Throwable.class.isAssignableFrom(field2.getType())) {
            try {
                return (Throwable)field2.get(throwable);
            }
            catch (IllegalAccessException illegalAccessException) {
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        return null;
    }

    public static int getThrowableCount(Throwable throwable) {
        int count = 0;
        while (throwable != null) {
            ++count;
            throwable = ExceptionUtils.getCause(throwable);
        }
        return count;
    }

    public static Throwable[] getThrowables(Throwable throwable) {
        ArrayList<Throwable> list = new ArrayList<Throwable>();
        while (throwable != null) {
            list.add(throwable);
            throwable = ExceptionUtils.getCause(throwable);
        }
        return list.toArray(new Throwable[0]);
    }

    public static int indexOfThrowable(Throwable throwable, Class type) {
        return ExceptionUtils.indexOfThrowable(throwable, type, 0);
    }

    public static int indexOfThrowable(Throwable throwable, Class type, int fromIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("Throwable index out of range: " + fromIndex);
        }
        Throwable[] throwables = ExceptionUtils.getThrowables(throwable);
        if (fromIndex >= throwables.length) {
            throw new IndexOutOfBoundsException("Throwable index out of range: " + fromIndex);
        }
        for (int i = fromIndex; i < throwables.length; ++i) {
            if (!throwables[i].getClass().equals(type)) continue;
            return i;
        }
        return -1;
    }

    public static void printRootCauseStackTrace(Throwable t2, PrintStream stream) {
        String[] trace;
        for (String aTrace : trace = ExceptionUtils.getRootCauseStackTrace(t2)) {
            stream.println(aTrace);
        }
        stream.flush();
    }

    public static void printRootCauseStackTrace(Throwable t2) {
        ExceptionUtils.printRootCauseStackTrace(t2, System.err);
    }

    public static void printRootCauseStackTrace(Throwable t2, PrintWriter writer) {
        String[] trace;
        for (String aTrace : trace = ExceptionUtils.getRootCauseStackTrace(t2)) {
            writer.println(aTrace);
        }
        writer.flush();
    }

    public static String[] getRootCauseStackTrace(Throwable t2) {
        Throwable[] throwables = ExceptionUtils.getThrowables(t2);
        int count = throwables.length;
        ArrayList<String> frames = new ArrayList<String>();
        List<String> nextTrace = ExceptionUtils.getStackFrameList(throwables[count - 1]);
        int i = count;
        while (--i >= 0) {
            List<String> trace = nextTrace;
            if (i != 0) {
                nextTrace = ExceptionUtils.getStackFrameList(throwables[i - 1]);
                ExceptionUtils.removeCommonFrames(trace, nextTrace);
            }
            if (i == count - 1) {
                frames.add(throwables[i].toString());
            } else {
                frames.add(WRAPPED_MARKER + throwables[i].toString());
            }
            for (String aTrace : trace) {
                frames.add(aTrace);
            }
        }
        return frames.toArray(new String[0]);
    }

    private static void removeCommonFrames(List<String> causeFrames, List<String> wrapperFrames) {
        int causeFrameIndex = causeFrames.size() - 1;
        for (int wrapperFrameIndex = wrapperFrames.size() - 1; causeFrameIndex >= 0 && wrapperFrameIndex >= 0; --causeFrameIndex, --wrapperFrameIndex) {
            String wrapperFrame;
            String causeFrame = causeFrames.get(causeFrameIndex);
            if (!causeFrame.equals(wrapperFrame = wrapperFrames.get(wrapperFrameIndex))) continue;
            causeFrames.remove(causeFrameIndex);
        }
    }

    public static String getStackTrace(Throwable t2) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter((Writer)sw, true);
        t2.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static String getFullStackTrace(Throwable t2) {
        Throwable[] ts;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter((Writer)sw, true);
        for (Throwable t1 : ts = ExceptionUtils.getThrowables(t2)) {
            t1.printStackTrace(pw);
            if (ExceptionUtils.isNestedThrowable(t1)) break;
        }
        return sw.getBuffer().toString();
    }

    public static boolean isNestedThrowable(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        if (throwable instanceof SQLException) {
            return true;
        }
        if (throwable instanceof InvocationTargetException) {
            return true;
        }
        for (String CAUSE_METHOD_NAME : CAUSE_METHOD_NAMES) {
            try {
                Method method2 = throwable.getClass().getMethod(CAUSE_METHOD_NAME, null);
                if (method2 == null) continue;
                return true;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (SecurityException securityException) {
                // empty catch block
            }
        }
        try {
            Field field2 = throwable.getClass().getField("detail");
            if (field2 != null) {
                return true;
            }
        }
        catch (NoSuchFieldException noSuchFieldException) {
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        return false;
    }

    public static String[] getStackFrames(Throwable t2) {
        return ExceptionUtils.getStackFrames(ExceptionUtils.getStackTrace(t2));
    }

    static String[] getStackFrames(String stackTrace) {
        String linebreak = System.getProperty("line.separator");
        StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
        LinkedList<String> list = new LinkedList<String>();
        while (frames.hasMoreTokens()) {
            list.add(frames.nextToken());
        }
        return list.toArray(new String[0]);
    }

    static List<String> getStackFrameList(Throwable t2) {
        String stackTrace = ExceptionUtils.getStackTrace(t2);
        String linebreak = System.getProperty("line.separator");
        StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
        LinkedList<String> list = new LinkedList<String>();
        boolean traceStarted = false;
        while (frames.hasMoreTokens()) {
            String token = frames.nextToken();
            int at = token.indexOf("at");
            if (at != -1 && token.substring(0, at).trim().length() == 0) {
                traceStarted = true;
                list.add(token);
                continue;
            }
            if (!traceStarted) continue;
            break;
        }
        return list;
    }
}

