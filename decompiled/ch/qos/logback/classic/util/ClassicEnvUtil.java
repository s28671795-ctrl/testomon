/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.classic.util;

import ch.qos.logback.core.util.EnvUtil;
import java.lang.module.ModuleDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

public class ClassicEnvUtil {
    public static boolean isGroovyAvailable() {
        return EnvUtil.isClassAvailable(ClassicEnvUtil.class, "groovy.lang.Binding");
    }

    public static <T> List<T> loadFromServiceLoader(Class<T> c, ClassLoader classLoader) {
        ServiceLoader<T> loader = ServiceLoader.load(c, classLoader);
        ArrayList<T> listOfT = new ArrayList<T>();
        for (T t2 : loader) {
            listOfT.add(t2);
        }
        return listOfT;
    }

    public static String getVersionOfLogbackClassic() {
        String moduleVersion = ClassicEnvUtil.getVersionOfLogbackClassicByModule();
        if (moduleVersion != null) {
            return moduleVersion;
        }
        Package pkg = ClassicEnvUtil.class.getPackage();
        if (pkg == null) {
            return null;
        }
        return pkg.getImplementationVersion();
    }

    private static String getVersionOfLogbackClassicByModule() {
        Module module = ClassicEnvUtil.class.getModule();
        if (module == null) {
            return null;
        }
        ModuleDescriptor md = module.getDescriptor();
        if (md == null) {
            return null;
        }
        Optional<String> opt = md.rawVersion();
        return opt.orElse(null);
    }
}

