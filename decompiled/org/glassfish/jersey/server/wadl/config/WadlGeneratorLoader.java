/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.server.wadl.WadlGenerator;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorJAXBGrammarGenerator;

class WadlGeneratorLoader {
    private static final Logger LOGGER = Logger.getLogger(WadlGeneratorLoader.class.getName());

    WadlGeneratorLoader() {
    }

    static WadlGenerator loadWadlGenerators(List<WadlGenerator> wadlGenerators) throws Exception {
        WadlGenerator wadlGenerator = new WadlGeneratorJAXBGrammarGenerator();
        if (wadlGenerators != null && !wadlGenerators.isEmpty()) {
            for (WadlGenerator generator : wadlGenerators) {
                generator.setWadlGeneratorDelegate(wadlGenerator);
                wadlGenerator = generator;
            }
        }
        wadlGenerator.init();
        return wadlGenerator;
    }

    static WadlGenerator loadWadlGeneratorDescriptions(InjectionManager injectionManager, WadlGeneratorDescription ... wadlGeneratorDescriptions) throws Exception {
        List<WadlGeneratorDescription> list = wadlGeneratorDescriptions != null ? Arrays.asList(wadlGeneratorDescriptions) : null;
        return WadlGeneratorLoader.loadWadlGeneratorDescriptions(injectionManager, list);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static WadlGenerator loadWadlGeneratorDescriptions(InjectionManager injectionManager, List<WadlGeneratorDescription> wadlGeneratorDescriptions) throws Exception {
        WadlGenerator wadlGenerator = new WadlGeneratorJAXBGrammarGenerator();
        CallbackList callbacks = new CallbackList();
        try {
            if (wadlGeneratorDescriptions != null && !wadlGeneratorDescriptions.isEmpty()) {
                for (WadlGeneratorDescription wadlGeneratorDescription : wadlGeneratorDescriptions) {
                    WadlGeneratorControl control = WadlGeneratorLoader.loadWadlGenerator(injectionManager, wadlGeneratorDescription, wadlGenerator);
                    wadlGenerator = control.wadlGenerator;
                    callbacks.add(control.callback);
                }
            }
            wadlGenerator.init();
        }
        finally {
            callbacks.callback();
        }
        return wadlGenerator;
    }

    private static WadlGeneratorControl loadWadlGenerator(InjectionManager injectionManager, WadlGeneratorDescription wadlGeneratorDescription, WadlGenerator wadlGeneratorDelegate) throws Exception {
        LOGGER.info("Loading wadlGenerator " + wadlGeneratorDescription.getGeneratorClass().getName());
        WadlGenerator generator = Injections.getOrCreate(injectionManager, wadlGeneratorDescription.getGeneratorClass());
        generator.setWadlGeneratorDelegate(wadlGeneratorDelegate);
        CallbackList callbacks = null;
        if (wadlGeneratorDescription.getProperties() != null && !wadlGeneratorDescription.getProperties().isEmpty()) {
            callbacks = new CallbackList();
            Properties wadlGeneratorProperties = wadlGeneratorDescription.getProperties();
            Class<?> osgiConfiguratorClass = wadlGeneratorDescription.getConfiguratorClass();
            for (Map.Entry<Object, Object> entry : wadlGeneratorProperties.entrySet()) {
                Callback callback = WadlGeneratorLoader.setProperty(generator, entry.getKey().toString(), entry.getValue(), osgiConfiguratorClass);
                callbacks.add(callback);
            }
        }
        return new WadlGeneratorControl(generator, callbacks);
    }

    private static Callback setProperty(Object generator, String propertyName, Object propertyValue, Class<?> osgiConfigClass) throws Exception {
        Callback result = null;
        String methodName = "set" + propertyName.substring(0, 1).toUpperCase(Locale.ROOT) + propertyName.substring(1);
        Method method2 = WadlGeneratorLoader.getMethodByName(methodName, generator.getClass());
        if (method2.getParameterTypes().length != 1) {
            throw new RuntimeException("Method " + methodName + " is no setter, it does not expect exactly one parameter, but " + method2.getParameterTypes().length);
        }
        Class<?> paramClazz = method2.getParameterTypes()[0];
        if (paramClazz.isAssignableFrom(propertyValue.getClass())) {
            method2.invoke(generator, propertyValue);
        } else if (File.class.equals(paramClazz) && propertyValue instanceof String) {
            LOGGER.warning("Configuring the " + method2.getDeclaringClass().getSimpleName() + " with the file based property " + propertyName + " is deprecated and will be removed in future versions of jersey! You should use the InputStream based property instead.");
            String filename = propertyValue.toString();
            if (filename.startsWith("classpath:")) {
                String strippedFilename = filename.substring("classpath:".length());
                URL resource = generator.getClass().getResource(strippedFilename);
                if (resource == null) {
                    throw new RuntimeException("The file '" + strippedFilename + "' does not exist in the classpath. It's loaded by the generator class, so if you use a relative filename it's relative to the generator class, otherwise you might want to load it via an absolute classpath reference like classpath:/somefile.xml");
                }
                File file = new File(resource.toURI());
                method2.invoke(generator, file);
            } else {
                method2.invoke(generator, new File(filename));
            }
        } else if (InputStream.class.equals(paramClazz) && propertyValue instanceof String) {
            InputStream is;
            final String resource = propertyValue.toString();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader == null) {
                loader = WadlGeneratorLoader.class.getClassLoader();
            }
            if ((is = ReflectionHelper.getResourceAsStream(loader, osgiConfigClass, resource)) == null) {
                String message = "The resource '" + resource + "' does not exist.";
                throw new RuntimeException(message);
            }
            result = new Callback(){

                @Override
                public void callback() {
                    try {
                        is.close();
                    }
                    catch (IOException e) {
                        LOGGER.log(Level.WARNING, "Could not close InputStream from resource " + resource, e);
                    }
                }
            };
            try {
                method2.invoke(generator, is);
            }
            catch (Exception e) {
                is.close();
                throw e;
            }
        } else {
            Constructor<?> paramTypeConstructor = paramClazz.getConstructor(propertyValue.getClass());
            if (paramTypeConstructor != null) {
                Object typedPropertyValue = paramTypeConstructor.newInstance(propertyValue);
                method2.invoke(generator, typedPropertyValue);
            } else {
                throw new RuntimeException("The property '" + propertyName + "' could not be set because the expected parameter is neither of type " + propertyValue.getClass() + " nor of any type that provides a constructor expecting a " + propertyValue.getClass() + ". The expected parameter is of type " + paramClazz.getName());
            }
        }
        return result;
    }

    private static Method getMethodByName(String methodName, Class<?> clazz) {
        for (Method method2 : clazz.getMethods()) {
            if (!method2.getName().equals(methodName)) continue;
            return method2;
        }
        throw new RuntimeException("Method '" + methodName + "' not found for class " + clazz.getName());
    }

    private static class CallbackList
    extends ArrayList<Callback>
    implements Callback {
        private static final long serialVersionUID = 1L;

        private CallbackList() {
        }

        @Override
        public void callback() {
            for (Callback callback : this) {
                callback.callback();
            }
        }

        @Override
        public boolean add(Callback e) {
            return e != null ? super.add(e) : false;
        }
    }

    private static interface Callback {
        public void callback();
    }

    private static class WadlGeneratorControl {
        WadlGenerator wadlGenerator;
        Callback callback;

        public WadlGeneratorControl(WadlGenerator wadlGenerator, Callback callback) {
            this.wadlGenerator = wadlGenerator;
            this.callback = callback;
        }
    }
}

