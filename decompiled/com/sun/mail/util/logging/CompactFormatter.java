/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.util.logging;

import com.sun.mail.util.logging.LogManagerProperties;
import com.sun.mail.util.logging.SeverityComparator;
import java.util.Collections;
import java.util.Date;
import java.util.Formattable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CompactFormatter
extends Formatter {
    private final String fmt;

    public CompactFormatter() {
        String p = this.getClass().getName();
        this.fmt = this.initFormat(p);
    }

    public CompactFormatter(String format) {
        String p = this.getClass().getName();
        this.fmt = format == null ? this.initFormat(p) : format;
    }

    @Override
    public String format(LogRecord record) {
        ResourceBundle rb = record.getResourceBundle();
        Locale l = rb == null ? null : rb.getLocale();
        String msg = this.formatMessage(record);
        String thrown = this.formatThrown(record);
        String err = this.formatError(record);
        Object[] params = new Object[]{new Date(record.getMillis()), this.formatSource(record), this.formatLoggerName(record), this.formatLevel(record), msg, thrown, new Alternate(msg, thrown), new Alternate(thrown, msg), record.getSequenceNumber(), this.formatThreadID(record), err, new Alternate(msg, err), new Alternate(err, msg), this.formatBackTrace(record), record.getResourceBundleName(), record.getMessage()};
        if (l == null) {
            return String.format(this.fmt, params);
        }
        return String.format(l, this.fmt, params);
    }

    @Override
    public String formatMessage(LogRecord record) {
        String msg = super.formatMessage(record);
        msg = CompactFormatter.replaceClassName(msg, record.getThrown());
        msg = CompactFormatter.replaceClassName(msg, record.getParameters());
        return msg;
    }

    public String formatMessage(Throwable t2) {
        return t2 != null ? CompactFormatter.replaceClassName(this.apply(t2).getMessage(), t2) : "";
    }

    public String formatLevel(LogRecord record) {
        return record.getLevel().getLocalizedName();
    }

    public String formatSource(LogRecord record) {
        String source2 = record.getSourceClassName();
        source2 = source2 != null ? (record.getSourceMethodName() != null ? CompactFormatter.simpleClassName(source2) + " " + record.getSourceMethodName() : CompactFormatter.simpleClassName(source2)) : CompactFormatter.simpleClassName(record.getLoggerName());
        return source2;
    }

    public String formatLoggerName(LogRecord record) {
        return CompactFormatter.simpleClassName(record.getLoggerName());
    }

    public Number formatThreadID(LogRecord record) {
        return (long)record.getThreadID() & 0xFFFFFFFFL;
    }

    public String formatThrown(LogRecord record) {
        String msg;
        Throwable t2 = record.getThrown();
        if (t2 != null) {
            String site = this.formatBackTrace(record);
            msg = this.formatToString(t2) + (CompactFormatter.isNullOrSpaces(site) ? "" : ' ' + site);
        } else {
            msg = "";
        }
        return msg;
    }

    public String formatError(LogRecord record) {
        Throwable t2 = record.getThrown();
        if (t2 != null) {
            return this.formatToString(t2);
        }
        return "";
    }

    private String formatToString(Throwable t2) {
        return CompactFormatter.simpleClassName(this.apply(t2).getClass()) + ": " + this.formatMessage(t2);
    }

    public String formatBackTrace(LogRecord record) {
        Throwable root;
        String site = "";
        Throwable t2 = record.getThrown();
        if (t2 != null && CompactFormatter.isNullOrSpaces(site = this.findAndFormat((root = this.apply(t2)).getStackTrace()))) {
            int limit = 0;
            for (Throwable c = t2; c != null && CompactFormatter.isNullOrSpaces(site = this.findAndFormat(c.getStackTrace())) && ++limit != 65536; c = c.getCause()) {
            }
        }
        return site;
    }

    private String findAndFormat(StackTraceElement[] trace) {
        String site = "";
        for (StackTraceElement s2 : trace) {
            if (this.ignore(s2)) continue;
            site = this.formatStackTraceElement(s2);
            break;
        }
        if (CompactFormatter.isNullOrSpaces(site)) {
            for (StackTraceElement s2 : trace) {
                if (this.defaultIgnore(s2)) continue;
                site = this.formatStackTraceElement(s2);
                break;
            }
        }
        return site;
    }

    private String formatStackTraceElement(StackTraceElement s2) {
        String v = CompactFormatter.simpleClassName(s2.getClassName());
        String result = v != null ? s2.toString().replace(s2.getClassName(), v) : s2.toString();
        v = CompactFormatter.simpleFileName(s2.getFileName());
        if (v != null && result.startsWith(v)) {
            result = result.replace(s2.getFileName(), "");
        }
        return result;
    }

    protected Throwable apply(Throwable t2) {
        return SeverityComparator.getInstance().apply(t2);
    }

    protected boolean ignore(StackTraceElement s2) {
        return this.isUnknown(s2) || this.defaultIgnore(s2);
    }

    protected String toAlternate(String s2) {
        return s2 != null ? s2.replaceAll("[\\x00-\\x1F\\x7F]+", "") : null;
    }

    private boolean defaultIgnore(StackTraceElement s2) {
        return this.isSynthetic(s2) || this.isStaticUtility(s2) || this.isReflection(s2);
    }

    private boolean isStaticUtility(StackTraceElement s2) {
        try {
            return LogManagerProperties.isStaticUtilityClass(s2.getClassName());
        }
        catch (RuntimeException runtimeException) {
        }
        catch (Exception exception) {
        }
        catch (LinkageError linkageError) {
            // empty catch block
        }
        String cn = s2.getClassName();
        return cn.endsWith("s") && !cn.endsWith("es") || cn.contains("Util") || cn.endsWith("Throwables");
    }

    private boolean isSynthetic(StackTraceElement s2) {
        return s2.getMethodName().indexOf(36) > -1;
    }

    private boolean isUnknown(StackTraceElement s2) {
        return s2.getLineNumber() < 0;
    }

    private boolean isReflection(StackTraceElement s2) {
        try {
            return LogManagerProperties.isReflectionClass(s2.getClassName());
        }
        catch (RuntimeException runtimeException) {
        }
        catch (Exception exception) {
        }
        catch (LinkageError linkageError) {
            // empty catch block
        }
        return s2.getClassName().startsWith("java.lang.reflect.") || s2.getClassName().startsWith("sun.reflect.");
    }

    private String initFormat(String p) {
        String v = LogManagerProperties.fromLogManager(p.concat(".format"));
        if (CompactFormatter.isNullOrSpaces(v)) {
            v = "%7$#.160s%n";
        }
        return v;
    }

    private static String replaceClassName(String msg, Throwable t2) {
        if (!CompactFormatter.isNullOrSpaces(msg)) {
            int limit = 0;
            for (Throwable c = t2; c != null; c = c.getCause()) {
                Class<?> k = c.getClass();
                msg = msg.replace(k.getName(), CompactFormatter.simpleClassName(k));
                if (++limit == 65536) break;
            }
        }
        return msg;
    }

    private static String replaceClassName(String msg, Object[] p) {
        if (!CompactFormatter.isNullOrSpaces(msg) && p != null) {
            for (Object o : p) {
                if (o == null) continue;
                Class<?> k = o.getClass();
                msg = msg.replace(k.getName(), CompactFormatter.simpleClassName(k));
            }
        }
        return msg;
    }

    private static String simpleClassName(Class<?> k) {
        try {
            return k.getSimpleName();
        }
        catch (InternalError internalError) {
            return CompactFormatter.simpleClassName(k.getName());
        }
    }

    private static String simpleClassName(String name) {
        if (name != null) {
            int index = name.lastIndexOf(46);
            name = index > -1 ? name.substring(index + 1) : name;
        }
        return name;
    }

    private static String simpleFileName(String name) {
        if (name != null) {
            int index = name.lastIndexOf(46);
            name = index > -1 ? name.substring(0, index) : name;
        }
        return name;
    }

    private static boolean isNullOrSpaces(String s2) {
        return s2 == null || s2.trim().length() == 0;
    }

    private class Alternate
    implements Formattable {
        private final String left;
        private final String right;

        Alternate(String left, String right) {
            this.left = String.valueOf(left);
            this.right = String.valueOf(right);
        }

        public void formatTo(java.util.Formatter formatter, int flags, int width, int precision) {
            int fence;
            String l = this.left;
            String r = this.right;
            if ((flags & 2) == 2) {
                l = l.toUpperCase(formatter.locale());
                r = r.toUpperCase(formatter.locale());
            }
            if ((flags & 4) == 4) {
                l = CompactFormatter.this.toAlternate(l);
                r = CompactFormatter.this.toAlternate(r);
            }
            if (precision <= 0) {
                precision = Integer.MAX_VALUE;
            }
            if ((fence = Math.min(l.length(), precision)) > precision >> 1) {
                fence = Math.max(fence - r.length(), fence >> 1);
            }
            if (fence > 0) {
                if (fence > l.length() && Character.isHighSurrogate(l.charAt(fence - 1))) {
                    --fence;
                }
                l = l.substring(0, fence);
            }
            r = r.substring(0, Math.min(precision - fence, r.length()));
            if (width > 0) {
                int half = width >> 1;
                if (l.length() < half) {
                    l = this.pad(flags, l, half);
                }
                if (r.length() < half) {
                    r = this.pad(flags, r, half);
                }
            }
            Object[] empty = Collections.emptySet().toArray();
            formatter.format(l, empty);
            if (l.length() != 0 && r.length() != 0) {
                formatter.format("|", empty);
            }
            formatter.format(r, empty);
        }

        private String pad(int flags, String s2, int length) {
            int padding = length - s2.length();
            StringBuilder b = new StringBuilder(length);
            if ((flags & 1) == 1) {
                for (int i = 0; i < padding; ++i) {
                    b.append(' ');
                }
                b.append(s2);
            } else {
                b.append(s2);
                for (int i = 0; i < padding; ++i) {
                    b.append(' ');
                }
            }
            return b.toString();
        }
    }
}

