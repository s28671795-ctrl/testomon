/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

class TempFiles {
    private static final Logger LOGGER = Logger.getLogger(TempFiles.class.getName());
    private static final Class<?> CLASS_FILES;
    private static final Class<?> CLASS_PATH;
    private static final Class<?> CLASS_FILE_ATTRIBUTE;
    private static final Class<?> CLASS_FILE_ATTRIBUTES;
    private static final Method METHOD_FILE_TO_PATH;
    private static final Method METHOD_FILES_CREATE_TEMP_FILE;
    private static final Method METHOD_FILES_CREATE_TEMP_FILE_WITHPATH;
    private static final Method METHOD_PATH_TO_FILE;
    private static boolean useJdk6API;

    TempFiles() {
    }

    private static boolean isJdk6() {
        String javaVersion = System.getProperty("java.version");
        LOGGER.log(Level.FINEST, "Detected java version = {0}", javaVersion);
        return javaVersion.startsWith("1.6.");
    }

    private static Class<?> safeGetClass(String className) {
        if (useJdk6API) {
            return null;
        }
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Exception cought", e);
            LOGGER.log(Level.WARNING, "Class {0} not found. Temp files will be created using old java.io API.", className);
            useJdk6API = true;
            return null;
        }
    }

    private static Method safeGetMethod(Class<?> clazz, String methodName, Class<?> ... parameterTypes) {
        if (useJdk6API) {
            return null;
        }
        try {
            return clazz.getMethod(methodName, parameterTypes);
        }
        catch (NoSuchMethodException e) {
            LOGGER.log(Level.SEVERE, "Exception cought", e);
            LOGGER.log(Level.WARNING, "Method {0} not found. Temp files will be created using old java.io API.", methodName);
            useJdk6API = true;
            return null;
        }
    }

    static Object toPath(File f) throws InvocationTargetException, IllegalAccessException {
        return METHOD_FILE_TO_PATH.invoke((Object)f, new Object[0]);
    }

    static File toFile(Object path) throws InvocationTargetException, IllegalAccessException {
        return (File)METHOD_PATH_TO_FILE.invoke(path, new Object[0]);
    }

    static File createTempFile(String prefix, String suffix, File dir) throws IOException {
        if (useJdk6API) {
            LOGGER.log(Level.FINEST, "Jdk6 detected, temp file (prefix:{0}, suffix:{1}) being created using old java.io API.", new Object[]{prefix, suffix});
            return File.createTempFile(prefix, suffix, dir);
        }
        try {
            if (dir != null) {
                Object path = TempFiles.toPath(dir);
                LOGGER.log(Level.FINEST, "Temp file (path: {0}, prefix:{1}, suffix:{2}) being created using NIO API.", new Object[]{dir.getAbsolutePath(), prefix, suffix});
                return TempFiles.toFile(METHOD_FILES_CREATE_TEMP_FILE_WITHPATH.invoke(null, path, prefix, suffix, Array.newInstance(CLASS_FILE_ATTRIBUTE, 0)));
            }
            LOGGER.log(Level.FINEST, "Temp file (prefix:{0}, suffix:{1}) being created using NIO API.", new Object[]{prefix, suffix});
            return TempFiles.toFile(METHOD_FILES_CREATE_TEMP_FILE.invoke(null, prefix, suffix, Array.newInstance(CLASS_FILE_ATTRIBUTE, 0)));
        }
        catch (IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Exception caught", e);
            LOGGER.log(Level.WARNING, "Error invoking java.nio API, temp file (path: {0}, prefix:{1}, suffix:{2}) being created using old java.io API.", new Object[]{dir != null ? dir.getAbsolutePath() : null, prefix, suffix});
            return File.createTempFile(prefix, suffix, dir);
        }
        catch (InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, "Exception caught", e);
            LOGGER.log(Level.WARNING, "Error invoking java.nio API, temp file (path: {0}, prefix:{1}, suffix:{2}) being created using old java.io API.", new Object[]{dir != null ? dir.getAbsolutePath() : null, prefix, suffix});
            return File.createTempFile(prefix, suffix, dir);
        }
    }

    static {
        useJdk6API = TempFiles.isJdk6();
        CLASS_FILES = TempFiles.safeGetClass("java.nio.file.Files");
        CLASS_PATH = TempFiles.safeGetClass("java.nio.file.Path");
        CLASS_FILE_ATTRIBUTE = TempFiles.safeGetClass("java.nio.file.attribute.FileAttribute");
        CLASS_FILE_ATTRIBUTES = TempFiles.safeGetClass("[Ljava.nio.file.attribute.FileAttribute;");
        METHOD_FILE_TO_PATH = TempFiles.safeGetMethod(File.class, "toPath", new Class[0]);
        METHOD_FILES_CREATE_TEMP_FILE = TempFiles.safeGetMethod(CLASS_FILES, "createTempFile", String.class, String.class, CLASS_FILE_ATTRIBUTES);
        METHOD_FILES_CREATE_TEMP_FILE_WITHPATH = TempFiles.safeGetMethod(CLASS_FILES, "createTempFile", CLASS_PATH, String.class, String.class, CLASS_FILE_ATTRIBUTES);
        METHOD_PATH_TO_FILE = TempFiles.safeGetMethod(CLASS_PATH, "toFile", new Class[0]);
    }
}

