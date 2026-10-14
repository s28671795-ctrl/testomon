/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IClass;
import org.codehaus.janino.IClassLoader;
import org.codehaus.janino.ReflectionIClass;

public class ClassLoaderIClassLoader
extends IClassLoader {
    private static final Logger LOGGER = Logger.getLogger(ClassLoaderIClassLoader.class.getName());
    private final ClassLoader classLoader;

    public ClassLoaderIClassLoader(ClassLoader classLoader) {
        super(null);
        this.classLoader = classLoader;
        super.postConstruct();
    }

    public ClassLoaderIClassLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    @Nullable
    protected IClass findIClass(String descriptor) throws ClassNotFoundException {
        Class<?> clazz;
        LOGGER.entering(null, "findIClass", descriptor);
        try {
            clazz = this.classLoader.loadClass(Descriptor.toClassName(descriptor));
        }
        catch (NoClassDefFoundError ncdfe) {
            if (ncdfe.getMessage().contains("wrong name")) {
                return null;
            }
            throw ncdfe;
        }
        catch (ClassNotFoundException e) {
            Throwable t2 = e.getCause();
            while (t2 instanceof ClassNotFoundException) {
                t2 = t2.getCause();
            }
            if (t2 == null) {
                return null;
            }
            throw e;
        }
        LOGGER.log(Level.FINE, "clazz={0}", clazz);
        ReflectionIClass result = new ReflectionIClass(clazz, this);
        this.defineIClass(result);
        return result;
    }
}

