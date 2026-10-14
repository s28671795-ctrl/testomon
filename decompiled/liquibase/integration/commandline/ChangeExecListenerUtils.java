/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Properties;
import liquibase.Scope;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.logging.Logger;
import liquibase.resource.ResourceAccessor;

public final class ChangeExecListenerUtils {
    private ChangeExecListenerUtils() {
    }

    public static ChangeExecListener getChangeExecListener(Database database, ResourceAccessor resourceAccessor, String changeExecListenerClass, String changeExecListenerPropertiesFile) throws Exception {
        ChangeExecListener changeExecListener = null;
        if (changeExecListenerClass != null) {
            Logger logger2 = Scope.getCurrentScope().getLog(ChangeExecListenerUtils.class);
            logger2.fine("Setting ChangeExecListener: " + changeExecListenerClass);
            ClassLoader classLoader = Scope.getCurrentScope().getClassLoader();
            Class<?> clazz = Class.forName(changeExecListenerClass, true, classLoader);
            Properties properties = ChangeExecListenerUtils.loadProperties(changeExecListenerPropertiesFile);
            Constructor<?> cons = ChangeExecListenerUtils.getConstructor(clazz, Database.class, Properties.class);
            if (cons != null) {
                logger2.fine("Create " + clazz.getSimpleName() + "(Database, Properties)");
                changeExecListener = (ChangeExecListener)cons.newInstance(database, properties);
            } else {
                cons = ChangeExecListenerUtils.getConstructor(clazz, Properties.class, Database.class);
                if (cons != null) {
                    logger2.fine("Create " + clazz.getSimpleName() + "(Properties, Database)");
                    changeExecListener = (ChangeExecListener)cons.newInstance(properties, database);
                } else {
                    cons = ChangeExecListenerUtils.getConstructor(clazz, Database.class);
                    if (cons != null) {
                        logger2.fine("Create " + clazz.getSimpleName() + "(Database)");
                        changeExecListener = (ChangeExecListener)cons.newInstance(database);
                    } else {
                        cons = ChangeExecListenerUtils.getConstructor(clazz, Properties.class);
                        if (cons != null) {
                            logger2.fine("Create " + clazz.getSimpleName() + "(Properties)");
                            changeExecListener = (ChangeExecListener)cons.newInstance(properties);
                        } else {
                            logger2.fine("Create " + clazz.getSimpleName() + "()");
                            changeExecListener = (ChangeExecListener)clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
                        }
                    }
                }
            }
        }
        return changeExecListener;
    }

    private static Constructor<?> getConstructor(Class<?> clazz, Class<?> ... params) {
        try {
            return clazz.getConstructor(params);
        }
        catch (Exception e) {
            return null;
        }
    }

    private static Properties loadProperties(String propertiesFile) throws IOException {
        if (propertiesFile != null) {
            File file = new File(propertiesFile);
            if (file.exists()) {
                Properties properties = new Properties();
                try (InputStream inputStream2 = Files.newInputStream(file.toPath(), new OpenOption[0]);){
                    properties.load(inputStream2);
                }
                return properties;
            }
            throw new FileNotFoundException(propertiesFile);
        }
        return null;
    }
}

