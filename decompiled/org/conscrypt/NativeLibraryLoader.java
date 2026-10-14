/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.conscrypt.HostProperties;
import org.conscrypt.NativeLibraryUtil;
import org.conscrypt.Platform;

final class NativeLibraryLoader {
    private static final Logger logger = Logger.getLogger(NativeLibraryLoader.class.getName());
    private static final String WORK_DIR_PROPERTY_NAME = "org.conscrypt.native.workdir";
    private static final String DELETE_LIB_PROPERTY_NAME = "org.conscrypt.native.deleteLibAfterLoading";
    private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
    private static final File WORKDIR;
    private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING;

    private static File getWorkDir() {
        String dirName = System.getProperty(WORK_DIR_PROPERTY_NAME);
        if (dirName == null) {
            return null;
        }
        File f = new File(dirName);
        if (!f.mkdirs() && !f.exists()) {
            NativeLibraryLoader.log("Unable to find or create working directory: {0}", dirName);
            return null;
        }
        try {
            f = f.getAbsoluteFile();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return f;
    }

    static boolean loadFirstAvailable(ClassLoader loader, List<LoadResult> results, String ... names) {
        for (String name : names) {
            if (!NativeLibraryLoader.load(name, loader, results)) continue;
            return true;
        }
        return false;
    }

    private static boolean load(String name, ClassLoader loader, List<LoadResult> results) {
        return NativeLibraryLoader.loadFromWorkdir(name, loader, results) || NativeLibraryLoader.loadLibrary(loader, name, false, results);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean loadFromWorkdir(String name, ClassLoader loader, List<LoadResult> results) {
        String libname = System.mapLibraryName(name);
        String path = NATIVE_RESOURCE_HOME + libname;
        URL url2 = loader.getResource(path);
        if (url2 == null && HostProperties.isOSX()) {
            url2 = path.endsWith(".jnilib") ? loader.getResource("META-INF/native/lib" + name + ".dynlib") : loader.getResource("META-INF/native/lib" + name + ".jnilib");
        }
        if (url2 == null) {
            return false;
        }
        int index = libname.lastIndexOf(46);
        String prefix = libname.substring(0, index);
        String suffix = libname.substring(index, libname.length());
        File tmpFile = null;
        try {
            tmpFile = Platform.createTempFile(prefix, suffix, WORKDIR);
            if (tmpFile.isFile() && tmpFile.canRead() && !Platform.canExecuteExecutable(tmpFile)) {
                throw new IOException(MessageFormat.format("{0} exists but cannot be executed even when execute permissions set; check volume for \"noexec\" flag; use -D{1}=[path] to set native working directory separately.", tmpFile.getPath(), WORK_DIR_PROPERTY_NAME));
            }
            NativeLibraryLoader.copyLibrary(url2, tmpFile);
            boolean bl = NativeLibraryLoader.loadLibrary(loader, tmpFile.getPath(), true, results);
            return bl;
        }
        catch (IOException e) {
            Throwable error = new UnsatisfiedLinkError(MessageFormat.format("Failed creating temp file ({0})", tmpFile)).initCause(e);
            results.add(LoadResult.newFailureResult(name, true, false, error));
            boolean bl = false;
            return bl;
        }
        finally {
            if (tmpFile != null) {
                boolean deleted = false;
                if (DELETE_NATIVE_LIB_AFTER_LOADING) {
                    deleted = tmpFile.delete();
                }
                if (!deleted) {
                    tmpFile.deleteOnExit();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void copyLibrary(URL classpathUrl, File tmpFile) throws IOException {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            int length;
            in = classpathUrl.openStream();
            out = new FileOutputStream(tmpFile);
            byte[] buffer = new byte[8192];
            while ((length = in.read(buffer)) > 0) {
                ((OutputStream)out).write(buffer, 0, length);
            }
            out.flush();
        }
        catch (Throwable throwable) {
            NativeLibraryLoader.closeQuietly(in);
            NativeLibraryLoader.closeQuietly(out);
            throw throwable;
        }
        NativeLibraryLoader.closeQuietly(in);
        NativeLibraryLoader.closeQuietly(out);
    }

    private static boolean loadLibrary(ClassLoader loader, String name, boolean absolute, List<LoadResult> results) {
        try {
            Class<?> newHelper = NativeLibraryLoader.tryToLoadClass(loader, NativeLibraryUtil.class);
            LoadResult result = NativeLibraryLoader.loadLibraryFromHelperClassloader(newHelper, name, absolute);
            results.add(result);
            if (result.loaded) {
                return true;
            }
        }
        catch (Exception newHelper) {
            // empty catch block
        }
        LoadResult result = NativeLibraryLoader.loadLibraryFromCurrentClassloader(name, absolute);
        results.add(result);
        return result.loaded;
    }

    private static LoadResult loadLibraryFromHelperClassloader(final Class<?> helper, final String name, final boolean absolute) {
        return AccessController.doPrivileged(new PrivilegedAction<LoadResult>(){

            @Override
            public LoadResult run() {
                try {
                    Method method2 = helper.getMethod("loadLibrary", String.class, Boolean.TYPE);
                    method2.setAccessible(true);
                    method2.invoke(null, name, absolute);
                    return LoadResult.newSuccessResult(name, absolute, true);
                }
                catch (InvocationTargetException e) {
                    return LoadResult.newFailureResult(name, absolute, true, e.getCause());
                }
                catch (Throwable e) {
                    return LoadResult.newFailureResult(name, absolute, true, e);
                }
            }
        });
    }

    private static LoadResult loadLibraryFromCurrentClassloader(String name, boolean absolute) {
        try {
            NativeLibraryUtil.loadLibrary(name, absolute);
            return LoadResult.newSuccessResult(name, absolute, false);
        }
        catch (Throwable e) {
            return LoadResult.newFailureResult(name, absolute, false, e);
        }
    }

    private static Class<?> tryToLoadClass(final ClassLoader loader, final Class<?> helper) throws ClassNotFoundException {
        try {
            return loader.loadClass(helper.getName());
        }
        catch (ClassNotFoundException e) {
            final byte[] classBinary = NativeLibraryLoader.classToByteArray(helper);
            return (Class)AccessController.doPrivileged(new PrivilegedAction<Class<?>>(){

                @Override
                public Class<?> run() {
                    try {
                        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                        defineClass.setAccessible(true);
                        return (Class)defineClass.invoke((Object)loader, helper.getName(), classBinary, 0, classBinary.length);
                    }
                    catch (Exception e) {
                        throw new IllegalStateException("Define class failed!", e);
                    }
                }
            });
        }
    }

    private static byte[] classToByteArray(Class<?> clazz) throws ClassNotFoundException {
        URL classUrl;
        String fileName = clazz.getName();
        int lastDot = fileName.lastIndexOf(46);
        if (lastDot > 0) {
            fileName = fileName.substring(lastDot + 1);
        }
        if ((classUrl = clazz.getResource(fileName + ".class")) == null) {
            throw new ClassNotFoundException(clazz.getName());
        }
        byte[] buf = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        InputStream in = null;
        try {
            int r22;
            in = classUrl.openStream();
            while ((r22 = in.read(buf)) != -1) {
                out.write(buf, 0, r22);
            }
            byte[] r22 = out.toByteArray();
            return r22;
        }
        catch (IOException ex) {
            throw new ClassNotFoundException(clazz.getName(), ex);
        }
        finally {
            NativeLibraryLoader.closeQuietly(in);
            NativeLibraryLoader.closeQuietly(out);
        }
    }

    private static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private NativeLibraryLoader() {
    }

    private static void log(String format, Object arg) {
        logger.log(Level.FINE, format, arg);
    }

    private static void log(String format, Object arg1, Object arg2) {
        logger.log(Level.FINE, format, new Object[]{arg1, arg2});
    }

    private static void log(String format, Object arg1, Object arg2, Throwable t2) {
        NativeLibraryLoader.debug(MessageFormat.format(format, arg1, arg2), t2);
    }

    private static void debug(String message, Throwable t2) {
        logger.log(Level.FINE, message, t2);
    }

    static {
        File workdir = NativeLibraryLoader.getWorkDir();
        if (workdir == null) {
            workdir = HostProperties.getTempDir();
        }
        WORKDIR = workdir;
        NativeLibraryLoader.log("-D{0}: {1}", WORK_DIR_PROPERTY_NAME, WORKDIR);
        DELETE_NATIVE_LIB_AFTER_LOADING = Boolean.valueOf(System.getProperty(DELETE_LIB_PROPERTY_NAME, "true"));
    }

    static final class LoadResult {
        final String name;
        final boolean absolute;
        final boolean loaded;
        final boolean usingHelperClassloader;
        final Throwable error;

        private static LoadResult newSuccessResult(String name, boolean absolute, boolean usingHelperClassloader) {
            return new LoadResult(name, absolute, true, usingHelperClassloader, null);
        }

        private static LoadResult newFailureResult(String name, boolean absolute, boolean usingHelperClassloader, Throwable error) {
            return new LoadResult(name, absolute, false, usingHelperClassloader, error);
        }

        private LoadResult(String name, boolean absolute, boolean loaded, boolean usingHelperClassloader, Throwable error) {
            this.name = name;
            this.absolute = absolute;
            this.loaded = loaded;
            this.usingHelperClassloader = usingHelperClassloader;
            this.error = error;
        }

        void log() {
            if (this.error != null) {
                NativeLibraryLoader.log("Unable to load the library {0} (using helper classloader={1})", this.name, this.usingHelperClassloader, this.error);
            } else {
                NativeLibraryLoader.log("Successfully loaded library {0}  (using helper classloader={1})", this.name, this.usingHelperClassloader);
            }
        }
    }
}

