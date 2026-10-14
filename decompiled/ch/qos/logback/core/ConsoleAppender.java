/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core;

import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import ch.qos.logback.core.status.WarnStatus;
import ch.qos.logback.core.util.Loader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ConsoleAppender<E>
extends OutputStreamAppender<E> {
    protected ConsoleTarget target = ConsoleTarget.SystemOut;
    protected boolean withJansi = false;
    private static final String AnsiConsole_CLASS_NAME = "org.fusesource.jansi.AnsiConsole";
    private static final String JANSI2_OUT_METHOD_NAME = "out";
    private static final String JANSI2_ERR_METHOD_NAME = "err";
    private static final String WRAP_SYSTEM_OUT_METHOD_NAME = "wrapSystemOut";
    private static final String WRAP_SYSTEM_ERR_METHOD_NAME = "wrapSystemErr";
    private static final String SYSTEM_INSTALL_METHOD_NAME = "systemInstall";
    private static final Class<?>[] ARGUMENT_TYPES = new Class[]{PrintStream.class};
    private static final String CONSOLE_APPENDER_WARNING_URL = "https://logback.qos.ch/codes.html#slowConsole";

    public void setTarget(String value) {
        ConsoleTarget t2 = ConsoleTarget.findByName(value.trim());
        if (t2 == null) {
            this.targetWarn(value);
        } else {
            this.target = t2;
        }
    }

    public String getTarget() {
        return this.target.getName();
    }

    private void targetWarn(String val) {
        WarnStatus status = new WarnStatus("[" + val + "] should be one of " + Arrays.toString((Object[])ConsoleTarget.values()), this);
        status.add(new WarnStatus("Using previously set target, System.out by default.", this));
        this.addStatus(status);
    }

    @Override
    public void start() {
        this.addInfo("BEWARE: Writing to the console can be very slow. Avoid logging to the ");
        this.addInfo("console in production environments, especially in high volume systems.");
        this.addInfo("See also https://logback.qos.ch/codes.html#slowConsole");
        OutputStream targetStream = this.target.getStream();
        if (this.withJansi) {
            targetStream = this.wrapWithJansi(targetStream);
        }
        this.setOutputStream(targetStream);
        super.start();
    }

    private OutputStream wrapWithJansi(OutputStream targetStream) {
        try {
            this.addInfo("Enabling JANSI AnsiPrintStream for the console.");
            ClassLoader classLoader = Loader.getClassLoaderOfObject(this.context);
            Class<?> classObj = classLoader.loadClass(AnsiConsole_CLASS_NAME);
            Method systemInstallMethod = classObj.getMethod(SYSTEM_INSTALL_METHOD_NAME, new Class[0]);
            if (systemInstallMethod != null) {
                systemInstallMethod.invoke(null, new Object[0]);
            }
            String methodNameJansi2 = this.target == ConsoleTarget.SystemOut ? JANSI2_OUT_METHOD_NAME : JANSI2_ERR_METHOD_NAME;
            Optional<Method> optOutMethod = Arrays.stream(classObj.getMethods()).filter(m4 -> m4.getName().equals(methodNameJansi2)).filter(m4 -> m4.getParameters().length == 0).filter(m4 -> Modifier.isStatic(m4.getModifiers())).filter(m4 -> PrintStream.class.isAssignableFrom(m4.getReturnType())).findAny();
            if (optOutMethod.isPresent()) {
                Method outMethod = optOutMethod.orElseThrow(() -> new NoSuchElementException("No out/err method present"));
                return (PrintStream)outMethod.invoke(null, new Object[0]);
            }
            String methodName = this.target == ConsoleTarget.SystemOut ? WRAP_SYSTEM_OUT_METHOD_NAME : WRAP_SYSTEM_ERR_METHOD_NAME;
            Method method2 = classObj.getMethod(methodName, ARGUMENT_TYPES);
            return (OutputStream)method2.invoke(null, new PrintStream(targetStream));
        }
        catch (Exception e) {
            this.addWarn("Failed to create AnsiPrintStream. Falling back on the default stream.", e);
            return targetStream;
        }
    }

    public boolean isWithJansi() {
        return this.withJansi;
    }

    public void setWithJansi(boolean withJansi) {
        this.withJansi = withJansi;
    }
}

