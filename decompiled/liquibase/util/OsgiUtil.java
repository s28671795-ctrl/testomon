/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.List;
import java.util.stream.Collectors;
import liquibase.osgi.Activator;

public final class OsgiUtil {
    private OsgiUtil() {
    }

    public static <T> Class<T> loadClass(String className) throws ClassNotFoundException {
        List<Activator.LiquibaseBundle> liquibaseBundles = Activator.getLiquibaseBundles();
        for (Activator.LiquibaseBundle lb : liquibaseBundles) {
            try {
                Class clazz = lb.getBundle().loadClass(className);
                if (!OsgiUtil.isClassAllowed(lb, clazz)) {
                    throw new ClassNotFoundException("Class is not allowed to load, class:" + className + " bundles:" + liquibaseBundles.stream().map(i -> i.getBundle().getSymbolicName()).collect(Collectors.joining(",")));
                }
                return clazz;
            }
            catch (ClassNotFoundException classNotFoundException) {
            }
        }
        throw new ClassNotFoundException("Cannot find class:" + className + " bundles:" + liquibaseBundles.stream().map(i -> i.getBundle().getSymbolicName()).collect(Collectors.joining(",")));
    }

    private static boolean isClassAllowed(Activator.LiquibaseBundle liquibaseBundle, Class clazz) {
        if (liquibaseBundle.allowedAllPackages()) {
            return true;
        }
        for (String allowedPackage : liquibaseBundle.getAllowedPackages()) {
            String pkgName;
            Package pkg = clazz.getPackage();
            if (pkg == null || !allowedPackage.equals(pkgName = pkg.getName()) && !allowedPackage.startsWith(pkgName + ".")) continue;
            return true;
        }
        return false;
    }
}

