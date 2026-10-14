/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressFBWarnings(value={"MS_EXPOSE_REP"})
public enum MinioProperties {
    INSTANCE;

    private static final Logger LOGGER;
    private final AtomicReference<String> version = new AtomicReference<Object>(null);

    public String getVersion() {
        String result = this.version.get();
        if (result != null) {
            return result;
        }
        this.setVersion();
        return this.version.get();
    }

    private synchronized void setVersion() {
        if (this.version.get() != null) {
            return;
        }
        this.version.set("dev");
        ClassLoader classLoader = ((Object)((Object)this)).getClass().getClassLoader();
        if (classLoader == null) {
            return;
        }
        try {
            Enumeration<URL> resources = classLoader.getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                InputStream is = resources.nextElement().openStream();
                Throwable throwable = null;
                try {
                    Manifest manifest = new Manifest(is);
                    if (!"minio".equals(manifest.getMainAttributes().getValue("Implementation-Title"))) continue;
                    this.version.set(manifest.getMainAttributes().getValue("Implementation-Version"));
                    return;
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (is == null) continue;
                    if (throwable != null) {
                        try {
                            is.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    is.close();
                }
            }
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException occurred", e);
            this.version.set("unknown");
        }
    }

    public String getDefaultUserAgent() {
        return "MinIO (" + System.getProperty("os.name") + "; " + System.getProperty("os.arch") + ") minio-java/" + this.getVersion();
    }

    static {
        LOGGER = Logger.getLogger(MinioProperties.class.getName());
    }
}

