/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.commandline;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import liquibase.Scope;
import liquibase.integration.commandline.LiquibaseCommandLine;
import liquibase.integration.commandline.LiquibaseLauncherSettings;
import liquibase.util.StringUtil;

public class LiquibaseLauncher {
    private static final String LIQUIBASE_CORE_JAR_PATTERN = ".*?/liquibase-core([-0-9.])*.jar";
    private static final String LIQUIBASE_COMMERCIAL_JAR_PATTERN = ".*?/liquibase-commercial([-0-9.])*.jar";
    private static final String LIQUIBASE_CORE_MESSAGE = "Liquibase Core";
    private static final String LIQUIBASE_COMMERCIAL_MESSAGE = "Liquibase Commercial";
    private static final String DEPENDENCY_JAR_VERSION_PATTERN = "(.*?)-?[0-9.]*.jar";
    private static boolean debug = false;

    public static void main(String[] args2) throws Exception {
        String parentLoaderSetting;
        String debugSetting = LiquibaseLauncherSettings.getSetting(LiquibaseLauncherSettings.LiquibaseLauncherSetting.LIQUIBASE_LAUNCHER_DEBUG);
        if ("true".equals(debugSetting)) {
            debug = true;
            LiquibaseLauncher.debug("Debug mode enabled because either the JVM property 'liquibase.launcher.debug' or the environment variable 'LIQUIBASE_LAUNCHER_DEBUG' is set to " + debugSetting);
        }
        if ((parentLoaderSetting = LiquibaseLauncherSettings.getSetting(LiquibaseLauncherSettings.LiquibaseLauncherSetting.LIQUIBASE_LAUNCHER_PARENT_CLASSLOADER)) == null) {
            parentLoaderSetting = "system";
        }
        LiquibaseLauncher.debug("Liquibase launcher parent classloader is set to " + parentLoaderSetting);
        String liquibaseHomeEnv = LiquibaseLauncherSettings.getSetting(LiquibaseLauncherSettings.LiquibaseLauncherSetting.LIQUIBASE_HOME);
        LiquibaseLauncher.debug("Liquibase home: " + liquibaseHomeEnv);
        if (liquibaseHomeEnv == null || liquibaseHomeEnv.equals("")) {
            throw new IllegalArgumentException("Unable to find either 'liquibase.home' JVM property nor 'LIQUIBASE_HOME' environment variable");
        }
        File liquibaseHome = new File(liquibaseHomeEnv);
        List<URL> libUrls = LiquibaseLauncher.getLibUrls(liquibaseHome);
        LiquibaseLauncher.checkForDuplicatedJars(libUrls);
        if (debug) {
            LiquibaseLauncher.debug("Final Classpath:");
            for (URL url2 : libUrls) {
                LiquibaseLauncher.debug("  " + url2.toString());
            }
        }
        ClassLoader parentLoader = LiquibaseLauncher.getClassLoader(parentLoaderSetting);
        URLClassLoader classloader = new URLClassLoader(libUrls.toArray(new URL[0]), parentLoader);
        Thread.currentThread().setContextClassLoader(classloader);
        Class<?> cli = null;
        try {
            cli = classloader.loadClass(LiquibaseCommandLine.class.getName());
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(String.format("Unable to find Liquibase classes in the configured home: '%s'.", liquibaseHome));
        }
        cli.getMethod("main", String[].class).invoke(null, new Object[]{args2});
    }

    private static ClassLoader getClassLoader(String parentLoaderSetting) {
        if (parentLoaderSetting.equalsIgnoreCase("system")) {
            return ClassLoader.getSystemClassLoader().getParent();
        }
        if (parentLoaderSetting.equalsIgnoreCase("thread")) {
            return Thread.currentThread().getContextClassLoader();
        }
        throw new RuntimeException("Unknown liquibase launcher parent classloader value: " + parentLoaderSetting);
    }

    private static void checkForDuplicatedJars(List<URL> urls2) {
        List<String> duplicateCore = urls2.stream().map(URL::getFile).filter(file -> file.matches(LIQUIBASE_CORE_JAR_PATTERN)).collect(Collectors.toList());
        List<String> duplicateCommercial = urls2.stream().map(URL::getFile).filter(file -> file.matches(LIQUIBASE_COMMERCIAL_JAR_PATTERN)).collect(Collectors.toList());
        if (duplicateCore.size() > 1) {
            LiquibaseLauncher.buildDupsMessage(duplicateCore, LIQUIBASE_CORE_MESSAGE);
        }
        if (duplicateCommercial.size() > 1) {
            LiquibaseLauncher.buildDupsMessage(duplicateCommercial, LIQUIBASE_COMMERCIAL_MESSAGE);
        }
        LinkedHashMap<String, List<String>> duplicates = new LinkedHashMap<String, List<String>>();
        LiquibaseLauncher.findVersionedDuplicates(urls2, Pattern.compile(DEPENDENCY_JAR_VERSION_PATTERN), duplicates);
        LiquibaseLauncher.findExactDuplicates(urls2, duplicates);
        duplicates.forEach((key, value) -> {
            if (value.size() > 1) {
                LiquibaseLauncher.buildDupsMessage(value, key);
            }
        });
    }

    private static List<URL> getLibUrls(File liquibaseHome) throws MalformedURLException {
        File[] libDirs;
        ArrayList<URL> urls2 = new ArrayList<URL>();
        urls2.add(new File(liquibaseHome, "internal/lib/liquibase-core.jar").toURI().toURL());
        for (File libDirFile : libDirs = new File[]{new File("./liquibase_libs"), new File(liquibaseHome, "lib"), new File(liquibaseHome, "internal/lib")}) {
            LiquibaseLauncher.debug("Looking for libraries in " + libDirFile.getAbsolutePath());
            if (!libDirFile.exists()) {
                LiquibaseLauncher.debug("Skipping directory " + libDirFile.getAbsolutePath() + " because it does not exist");
                continue;
            }
            File[] files = libDirFile.listFiles();
            if (files == null) {
                LiquibaseLauncher.debug("Skipping directory " + libDirFile.getAbsolutePath() + " because it does not list files");
                continue;
            }
            for (File lib : files) {
                if (!lib.getName().toLowerCase(Locale.US).endsWith(".jar") || lib.getName().toLowerCase(Locale.US).equals("liquibase-core.jar")) continue;
                try {
                    urls2.add(lib.toURI().toURL());
                    LiquibaseLauncher.debug("Added " + lib.getAbsolutePath() + " to classpath");
                }
                catch (Exception e) {
                    LiquibaseLauncher.debug("Error adding " + lib.getAbsolutePath() + ":" + e.getMessage(), e);
                }
            }
            try {
                urls2.add(libDirFile.toURI().toURL());
                LiquibaseLauncher.debug("Added " + libDirFile.getAbsolutePath() + " to classpath");
            }
            catch (Exception e) {
                LiquibaseLauncher.debug("Error adding " + libDirFile.getAbsolutePath() + ":" + e.getMessage(), e);
            }
        }
        return urls2;
    }

    private static void findVersionedDuplicates(List<URL> urls2, Pattern versionedJarPattern, Map<String, List<String>> duplicates) {
        urls2.forEach(url2 -> {
            Matcher m4 = versionedJarPattern.matcher(new File(url2.getFile()).getName());
            if (m4.find()) {
                String key = m4.group(1);
                ArrayList<String> dupEntries = (ArrayList<String>)duplicates.get(key);
                if (dupEntries == null) {
                    dupEntries = new ArrayList<String>();
                }
                dupEntries.add(url2.getFile());
                duplicates.put(key, dupEntries);
            }
        });
    }

    private static void findExactDuplicates(List<URL> urls2, Map<String, List<String>> duplicates) {
        urls2.forEach(url2 -> {
            String key = new File(url2.getFile()).getName();
            if (!(url2.toString().endsWith("/") || url2.toString().endsWith("\\") || key.contains("liquibase-core") || key.contains("liquibase-commercial"))) {
                ArrayList<String> dupEntries = (ArrayList<String>)duplicates.get(key);
                if (dupEntries == null) {
                    dupEntries = new ArrayList<String>();
                }
                dupEntries.add(url2.getFile());
                duplicates.put(key, dupEntries);
            }
        });
    }

    private static void buildDupsMessage(List<String> duplicates, String title) {
        String jarString = StringUtil.join(duplicates, System.lineSeparator());
        String message = String.format("*** Duplicate %s JAR files ***%n%s", title, jarString);
        Scope.getCurrentScope().getUI().sendMessage(String.format("WARNING: %s", message));
    }

    private static void debug(String message) {
        if (debug) {
            LiquibaseLauncher.debug(message, null);
        }
    }

    private static void debug(String message, Throwable e) {
        if (debug) {
            System.err.println("[LIQUIBASE LAUNCHER DEBUG] " + message);
            if (e != null) {
                e.printStackTrace(System.err);
            }
        }
    }
}

