/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.commandline;

class LiquibaseLauncherSettings {
    private static final String LIQUIBASE_HOME_JVM_PROPERTY_NAME = "liquibase.home";
    private static final String LIQUIBASE_LAUNCHER_DEBUG_JVM_PROPERTY_NAME = "liquibase.launcher.debug";
    private static final String LIQUIBASE_LAUNCHER_PARENT_CLASSLOADER_JVM_PROPERTY_NAME = "liquibase.launcher.parent_classloader";

    LiquibaseLauncherSettings() {
    }

    static String getSetting(LiquibaseLauncherSetting setting) {
        String value = System.getProperty(setting.getJvmPropertyName());
        if (value != null) {
            return value;
        }
        return System.getenv(setting.name());
    }

    static enum LiquibaseLauncherSetting {
        LIQUIBASE_HOME("liquibase.home"),
        LIQUIBASE_LAUNCHER_DEBUG("liquibase.launcher.debug"),
        LIQUIBASE_LAUNCHER_PARENT_CLASSLOADER("liquibase.launcher.parent_classloader");

        private final String jvmPropertyName;

        private LiquibaseLauncherSetting(String jvmPropertyName) {
            this.jvmPropertyName = jvmPropertyName;
        }

        String getJvmPropertyName() {
            return this.jvmPropertyName;
        }
    }
}

