/*
 * Decompiled with CFR 0.152.
 */
package reactor.util;

import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.regex.Matcher;
import org.slf4j.LoggerFactory;
import reactor.util.Logger;
import reactor.util.annotation.Nullable;

public abstract class Loggers {
    public static final String FALLBACK_PROPERTY = "reactor.logging.fallback";
    private static Function<String, ? extends Logger> LOGGER_FACTORY;

    public static void resetLoggerFactory() {
        try {
            Loggers.useSl4jLoggers();
        }
        catch (Throwable t2) {
            if (Loggers.isFallbackToJdk()) {
                Loggers.useJdkLoggers();
            }
            Loggers.useConsoleLoggers();
        }
    }

    static boolean isFallbackToJdk() {
        return "JDK".equalsIgnoreCase(System.getProperty(FALLBACK_PROPERTY));
    }

    public static void useConsoleLoggers() {
        ConsoleLoggerFactory loggerFactory;
        String name = Loggers.class.getName();
        LOGGER_FACTORY = loggerFactory = new ConsoleLoggerFactory(false);
        ((Logger)loggerFactory.apply(name)).debug("Using Console logging");
    }

    public static void useVerboseConsoleLoggers() {
        ConsoleLoggerFactory loggerFactory;
        String name = Loggers.class.getName();
        LOGGER_FACTORY = loggerFactory = new ConsoleLoggerFactory(true);
        ((Logger)loggerFactory.apply(name)).debug("Using Verbose Console logging");
    }

    public static void useCustomLoggers(Function<String, ? extends Logger> loggerFactory) {
        String name = Loggers.class.getName();
        LOGGER_FACTORY = loggerFactory;
        loggerFactory.apply(name).debug("Using custom logging");
    }

    public static void useJdkLoggers() {
        JdkLoggerFactory loggerFactory;
        String name = Loggers.class.getName();
        LOGGER_FACTORY = loggerFactory = new JdkLoggerFactory();
        ((Logger)loggerFactory.apply(name)).debug("Using JDK logging framework");
    }

    public static void useSl4jLoggers() {
        Slf4JLoggerFactory loggerFactory;
        String name = Loggers.class.getName();
        LOGGER_FACTORY = loggerFactory = new Slf4JLoggerFactory();
        ((Logger)loggerFactory.apply(name)).debug("Using Slf4j logging framework");
    }

    public static Logger getLogger(String name) {
        return LOGGER_FACTORY.apply(name);
    }

    public static Logger getLogger(Class<?> cls) {
        return LOGGER_FACTORY.apply(cls.getName());
    }

    Loggers() {
    }

    static {
        Loggers.resetLoggerFactory();
    }

    static final class ConsoleLoggerFactory
    implements Function<String, Logger> {
        private static final Map<ConsoleLoggerKey, WeakReference<Logger>> consoleLoggers = new WeakHashMap<ConsoleLoggerKey, WeakReference<Logger>>();
        final boolean verbose;

        ConsoleLoggerFactory(boolean verbose) {
            this.verbose = verbose;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Logger apply(String name) {
            ConsoleLoggerKey key = new ConsoleLoggerKey(name, this.verbose);
            Map<ConsoleLoggerKey, WeakReference<Logger>> map = consoleLoggers;
            synchronized (map) {
                Logger cached;
                WeakReference<Logger> ref = consoleLoggers.get(key);
                Logger logger2 = cached = ref == null ? null : (Logger)ref.get();
                if (cached == null) {
                    cached = new ConsoleLogger(key);
                    consoleLoggers.put(key, new WeakReference<Logger>(cached));
                }
                return cached;
            }
        }
    }

    private static final class ConsoleLoggerKey {
        private final String name;
        private final boolean verbose;

        private ConsoleLoggerKey(String name, boolean verbose) {
            this.name = name;
            this.verbose = verbose;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ConsoleLoggerKey key = (ConsoleLoggerKey)o;
            return this.verbose == key.verbose && Objects.equals(this.name, key.name);
        }

        public int hashCode() {
            return Objects.hash(this.name, this.verbose);
        }
    }

    static final class ConsoleLogger
    implements Logger {
        private final ConsoleLoggerKey identifier;
        private final PrintStream err;
        private final PrintStream log;

        ConsoleLogger(ConsoleLoggerKey identifier, PrintStream log, PrintStream err) {
            this.identifier = identifier;
            this.log = log;
            this.err = err;
        }

        ConsoleLogger(String name, PrintStream log, PrintStream err, boolean verbose) {
            this(new ConsoleLoggerKey(name, verbose), log, err);
        }

        ConsoleLogger(ConsoleLoggerKey identifier) {
            this(identifier, System.out, System.err);
        }

        @Override
        public String getName() {
            return this.identifier.name;
        }

        @Nullable
        final String format(@Nullable String from, Object ... arguments) {
            if (from != null) {
                String computed = from;
                if (arguments != null && arguments.length != 0) {
                    for (Object argument : arguments) {
                        computed = computed.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(argument)));
                    }
                }
                return computed;
            }
            return null;
        }

        @Override
        public boolean isTraceEnabled() {
            return this.identifier.verbose;
        }

        @Override
        public synchronized void trace(String msg) {
            if (!this.identifier.verbose) {
                return;
            }
            this.log.format("[TRACE] (%s) %s\n", Thread.currentThread().getName(), msg);
        }

        @Override
        public synchronized void trace(String format, Object ... arguments) {
            if (!this.identifier.verbose) {
                return;
            }
            this.log.format("[TRACE] (%s) %s\n", Thread.currentThread().getName(), this.format(format, arguments));
        }

        @Override
        public synchronized void trace(String msg, Throwable t2) {
            if (!this.identifier.verbose) {
                return;
            }
            this.log.format("[TRACE] (%s) %s - %s\n", Thread.currentThread().getName(), msg, t2);
            t2.printStackTrace(this.log);
        }

        @Override
        public boolean isDebugEnabled() {
            return this.identifier.verbose;
        }

        @Override
        public synchronized void debug(String msg) {
            if (!this.identifier.verbose) {
                return;
            }
            this.log.format("[DEBUG] (%s) %s\n", Thread.currentThread().getName(), msg);
        }

        @Override
        public synchronized void debug(String format, Object ... arguments) {
            if (!this.identifier.verbose) {
                return;
            }
            this.log.format("[DEBUG] (%s) %s\n", Thread.currentThread().getName(), this.format(format, arguments));
        }

        @Override
        public synchronized void debug(String msg, Throwable t2) {
            if (!this.identifier.verbose) {
                return;
            }
            this.log.format("[DEBUG] (%s) %s - %s\n", Thread.currentThread().getName(), msg, t2);
            t2.printStackTrace(this.log);
        }

        @Override
        public boolean isInfoEnabled() {
            return true;
        }

        @Override
        public synchronized void info(String msg) {
            this.log.format("[ INFO] (%s) %s\n", Thread.currentThread().getName(), msg);
        }

        @Override
        public synchronized void info(String format, Object ... arguments) {
            this.log.format("[ INFO] (%s) %s\n", Thread.currentThread().getName(), this.format(format, arguments));
        }

        @Override
        public synchronized void info(String msg, Throwable t2) {
            this.log.format("[ INFO] (%s) %s - %s\n", Thread.currentThread().getName(), msg, t2);
            t2.printStackTrace(this.log);
        }

        @Override
        public boolean isWarnEnabled() {
            return true;
        }

        @Override
        public synchronized void warn(String msg) {
            this.err.format("[ WARN] (%s) %s\n", Thread.currentThread().getName(), msg);
        }

        @Override
        public synchronized void warn(String format, Object ... arguments) {
            this.err.format("[ WARN] (%s) %s\n", Thread.currentThread().getName(), this.format(format, arguments));
        }

        @Override
        public synchronized void warn(String msg, Throwable t2) {
            this.err.format("[ WARN] (%s) %s - %s\n", Thread.currentThread().getName(), msg, t2);
            t2.printStackTrace(this.err);
        }

        @Override
        public boolean isErrorEnabled() {
            return true;
        }

        @Override
        public synchronized void error(String msg) {
            this.err.format("[ERROR] (%s) %s\n", Thread.currentThread().getName(), msg);
        }

        @Override
        public synchronized void error(String format, Object ... arguments) {
            this.err.format("[ERROR] (%s) %s\n", Thread.currentThread().getName(), this.format(format, arguments));
        }

        @Override
        public synchronized void error(String msg, Throwable t2) {
            this.err.format("[ERROR] (%s) %s - %s\n", Thread.currentThread().getName(), msg, t2);
            t2.printStackTrace(this.err);
        }

        public String toString() {
            return "ConsoleLogger[name=" + this.getName() + ", verbose=" + this.identifier.verbose + "]";
        }
    }

    private static class JdkLoggerFactory
    implements Function<String, Logger> {
        private JdkLoggerFactory() {
        }

        @Override
        public Logger apply(String name) {
            return new JdkLogger(java.util.logging.Logger.getLogger(name));
        }
    }

    static final class JdkLogger
    implements Logger {
        private final java.util.logging.Logger logger;

        public JdkLogger(java.util.logging.Logger logger2) {
            this.logger = logger2;
        }

        @Override
        public String getName() {
            return this.logger.getName();
        }

        @Override
        public boolean isTraceEnabled() {
            return this.logger.isLoggable(Level.FINEST);
        }

        @Override
        public void trace(String msg) {
            this.logger.log(Level.FINEST, msg);
        }

        @Override
        public void trace(String format, Object ... arguments) {
            this.logger.log(Level.FINEST, this.format(format, arguments));
        }

        @Override
        public void trace(String msg, Throwable t2) {
            this.logger.log(Level.FINEST, msg, t2);
        }

        @Override
        public boolean isDebugEnabled() {
            return this.logger.isLoggable(Level.FINE);
        }

        @Override
        public void debug(String msg) {
            this.logger.log(Level.FINE, msg);
        }

        @Override
        public void debug(String format, Object ... arguments) {
            this.logger.log(Level.FINE, this.format(format, arguments));
        }

        @Override
        public void debug(String msg, Throwable t2) {
            this.logger.log(Level.FINE, msg, t2);
        }

        @Override
        public boolean isInfoEnabled() {
            return this.logger.isLoggable(Level.INFO);
        }

        @Override
        public void info(String msg) {
            this.logger.log(Level.INFO, msg);
        }

        @Override
        public void info(String format, Object ... arguments) {
            this.logger.log(Level.INFO, this.format(format, arguments));
        }

        @Override
        public void info(String msg, Throwable t2) {
            this.logger.log(Level.INFO, msg, t2);
        }

        @Override
        public boolean isWarnEnabled() {
            return this.logger.isLoggable(Level.WARNING);
        }

        @Override
        public void warn(String msg) {
            this.logger.log(Level.WARNING, msg);
        }

        @Override
        public void warn(String format, Object ... arguments) {
            this.logger.log(Level.WARNING, this.format(format, arguments));
        }

        @Override
        public void warn(String msg, Throwable t2) {
            this.logger.log(Level.WARNING, msg, t2);
        }

        @Override
        public boolean isErrorEnabled() {
            return this.logger.isLoggable(Level.SEVERE);
        }

        @Override
        public void error(String msg) {
            this.logger.log(Level.SEVERE, msg);
        }

        @Override
        public void error(String format, Object ... arguments) {
            this.logger.log(Level.SEVERE, this.format(format, arguments));
        }

        @Override
        public void error(String msg, Throwable t2) {
            this.logger.log(Level.SEVERE, msg, t2);
        }

        @Nullable
        final String format(@Nullable String from, Object ... arguments) {
            if (from != null) {
                String computed = from;
                if (arguments != null && arguments.length != 0) {
                    for (Object argument : arguments) {
                        computed = computed.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(argument)));
                    }
                }
                return computed;
            }
            return null;
        }
    }

    private static class Slf4JLogger
    implements Logger {
        private final org.slf4j.Logger logger;

        public Slf4JLogger(org.slf4j.Logger logger2) {
            this.logger = logger2;
        }

        @Override
        public String getName() {
            return this.logger.getName();
        }

        @Override
        public boolean isTraceEnabled() {
            return this.logger.isTraceEnabled();
        }

        @Override
        public void trace(String msg) {
            this.logger.trace(msg);
        }

        @Override
        public void trace(String format, Object ... arguments) {
            this.logger.trace(format, arguments);
        }

        @Override
        public void trace(String msg, Throwable t2) {
            this.logger.trace(msg, t2);
        }

        @Override
        public boolean isDebugEnabled() {
            return this.logger.isDebugEnabled();
        }

        @Override
        public void debug(String msg) {
            this.logger.debug(msg);
        }

        @Override
        public void debug(String format, Object ... arguments) {
            this.logger.debug(format, arguments);
        }

        @Override
        public void debug(String msg, Throwable t2) {
            this.logger.debug(msg, t2);
        }

        @Override
        public boolean isInfoEnabled() {
            return this.logger.isInfoEnabled();
        }

        @Override
        public void info(String msg) {
            this.logger.info(msg);
        }

        @Override
        public void info(String format, Object ... arguments) {
            this.logger.info(format, arguments);
        }

        @Override
        public void info(String msg, Throwable t2) {
            this.logger.info(msg, t2);
        }

        @Override
        public boolean isWarnEnabled() {
            return this.logger.isWarnEnabled();
        }

        @Override
        public void warn(String msg) {
            this.logger.warn(msg);
        }

        @Override
        public void warn(String format, Object ... arguments) {
            this.logger.warn(format, arguments);
        }

        @Override
        public void warn(String msg, Throwable t2) {
            this.logger.warn(msg, t2);
        }

        @Override
        public boolean isErrorEnabled() {
            return this.logger.isErrorEnabled();
        }

        @Override
        public void error(String msg) {
            this.logger.error(msg);
        }

        @Override
        public void error(String format, Object ... arguments) {
            this.logger.error(format, arguments);
        }

        @Override
        public void error(String msg, Throwable t2) {
            this.logger.error(msg, t2);
        }
    }

    private static class Slf4JLoggerFactory
    implements Function<String, Logger> {
        private Slf4JLoggerFactory() {
        }

        @Override
        public Logger apply(String name) {
            return new Slf4JLogger(LoggerFactory.getLogger(name));
        }
    }
}

