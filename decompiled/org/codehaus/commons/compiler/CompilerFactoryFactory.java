/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import org.codehaus.commons.compiler.ICompilerFactory;
import org.codehaus.commons.nullanalysis.Nullable;

public final class CompilerFactoryFactory {
    @Nullable
    private static ICompilerFactory defaultCompilerFactory;

    private CompilerFactoryFactory() {
    }

    @Deprecated
    public static ICompilerFactory getDefaultCompilerFactory() throws Exception {
        return CompilerFactoryFactory.getDefaultCompilerFactory(Thread.currentThread().getContextClassLoader());
    }

    public static ICompilerFactory getDefaultCompilerFactory(ClassLoader classLoader) throws Exception {
        Properties properties;
        if (defaultCompilerFactory != null) {
            return defaultCompilerFactory;
        }
        InputStream is = classLoader.getResourceAsStream("org.codehaus.commons.compiler.properties");
        if (is == null) {
            throw new ClassNotFoundException("No implementation of org.codehaus.commons.compiler could be loaded. Typically, you'd have  \"janino.jar\", or \"commons-compiler-jdk.jar\", or both on the classpath, and use the \"ClassLoader.getSystemClassLoader\" to load them.");
        }
        try {
            properties = new Properties();
            properties.load(is);
        }
        finally {
            is.close();
        }
        String compilerFactoryClassName = properties.getProperty("compilerFactory");
        defaultCompilerFactory = CompilerFactoryFactory.getCompilerFactory(compilerFactoryClassName, classLoader);
        return defaultCompilerFactory;
    }

    @Deprecated
    public static ICompilerFactory[] getAllCompilerFactories() throws Exception {
        return CompilerFactoryFactory.getAllCompilerFactories(Thread.currentThread().getContextClassLoader());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ICompilerFactory[] getAllCompilerFactories(ClassLoader classLoader) throws Exception {
        ArrayList<ICompilerFactory> factories = new ArrayList<ICompilerFactory>();
        Enumeration<URL> en = classLoader.getResources("org.codehaus.commons.compiler.properties");
        while (en.hasMoreElements()) {
            URL url2 = en.nextElement();
            Properties properties = new Properties();
            try (InputStream is = url2.openStream();){
                properties.load(is);
            }
            String compilerFactoryClassName = properties.getProperty("compilerFactory");
            if (compilerFactoryClassName == null) {
                throw new IllegalStateException(url2.toString() + " does not specify the 'compilerFactory' property");
            }
            factories.add(CompilerFactoryFactory.getCompilerFactory(compilerFactoryClassName, classLoader));
        }
        return factories.toArray(new ICompilerFactory[factories.size()]);
    }

    @Deprecated
    public static ICompilerFactory getCompilerFactory(String compilerFactoryClassName) throws Exception {
        return CompilerFactoryFactory.getCompilerFactory(compilerFactoryClassName, Thread.currentThread().getContextClassLoader());
    }

    public static ICompilerFactory getCompilerFactory(String compilerFactoryClassName, ClassLoader classLoader) throws Exception {
        return (ICompilerFactory)classLoader.loadClass(compilerFactoryClassName).newInstance();
    }

    public static String getSpecificationVersion() {
        return CompilerFactoryFactory.class.getPackage().getSpecificationVersion();
    }
}

