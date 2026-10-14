/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import liquibase.logging.LogService;
import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogService;
import liquibase.logging.core.JavaLogger;
import liquibase.serializer.LiquibaseSerializable;

public class JavaLogService
extends AbstractLogService {
    private final Map<Class, JavaLogger> loggers = new HashMap<Class, JavaLogger>();
    private java.util.logging.Logger parent;

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public Logger getLog(Class clazz) {
        JavaLogger logger2 = this.loggers.get(clazz);
        if (logger2 == null) {
            java.util.logging.Logger utilLogger = java.util.logging.Logger.getLogger(this.getLogName(clazz));
            utilLogger.setUseParentHandlers(true);
            if (this.parent != null && !this.parent.getName().equals(utilLogger.getName())) {
                utilLogger.setParent(this.parent);
            }
            logger2 = new JavaLogger(utilLogger);
            this.loggers.put(clazz, logger2);
        }
        return logger2;
    }

    protected String getLogName(Class clazz) {
        String superclassLogName;
        if (clazz == null) {
            return "unknown";
        }
        if (clazz.getPackage() == null) {
            return clazz.getName();
        }
        String classPackageName = clazz.getPackage().getName();
        if (classPackageName.equals("liquibase")) {
            return "liquibase";
        }
        if (classPackageName.startsWith("liquibase.")) {
            return classPackageName.replaceFirst("(liquibase.\\w+)\\.?.*", "$1");
        }
        for (Class<?> iface : clazz.getInterfaces()) {
            String interfaceLog;
            if (iface.equals(LiquibaseSerializable.class) || (interfaceLog = this.getLogName(iface)).equals("liquibase")) continue;
            return interfaceLog;
        }
        Class superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class) && !(superclassLogName = this.getLogName(superclass)).equals("liquibase")) {
            return superclassLogName;
        }
        return "liquibase";
    }

    public java.util.logging.Logger getParent() {
        return this.parent;
    }

    public void setParent(java.util.logging.Logger parent) {
        this.parent = parent;
    }

    public Formatter getCustomFormatter() {
        return null;
    }

    public static void setFormatterOnHandler(LogService logService, Handler handler) {
        Formatter customFormatter;
        if (logService instanceof JavaLogService && handler != null && (customFormatter = ((JavaLogService)logService).getCustomFormatter()) != null) {
            handler.setFormatter(customFormatter);
        }
    }
}

