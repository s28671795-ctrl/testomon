/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.reflect.Proxy;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.util.ClassUnwrapper;
import org.apache.cxf.common.util.SpringClassUnwrapper;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;

public class ClassHelper {
    public static final String USE_DEFAULT_CLASS_HELPER = "org.apache.cxf.useDefaultClassHelpers";
    static final ClassHelper HELPER = new ClassHelper();
    static final ClassUnwrapper DEFAULT_UNWRAPPER = new DefaultClassUnwrapper(HELPER);
    static final ClassUnwrapper UNWRAPPER = ClassHelper.getClassUnwrapper(DEFAULT_UNWRAPPER);

    protected ClassHelper() {
    }

    private static ClassUnwrapper getClassUnwrapper(ClassUnwrapper defaultHelper) {
        boolean useSpring = true;
        String s2 = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.useSpringClassHelpers");
        if (!StringUtils.isEmpty(s2)) {
            boolean bl = useSpring = "1".equals(s2) || Boolean.parseBoolean(s2);
        }
        if (useSpring) {
            try {
                return new SpringClassUnwrapper();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return defaultHelper;
    }

    private Class<?> getRealClassInternal(Object o) {
        return this.getRealObjectInternal(o).getClass();
    }

    private Class<?> getRealClassFromClassInternal(Class<?> cls) {
        return cls;
    }

    private Object getRealObjectInternal(Object o) {
        return o instanceof Proxy ? Proxy.getInvocationHandler(o) : o;
    }

    public static Class<?> getRealClass(Object o) {
        return ClassHelper.getRealClass(null, o);
    }

    public static Class<?> getRealClassFromClass(Class<?> cls) {
        return ClassHelper.getRealClassFromClass(null, cls);
    }

    public static Class<?> getRealClassFromClass(Bus bus, Class<?> cls) {
        return ClassHelper.getContextClassUnwrapper(ClassHelper.getBus(bus)).getRealClassFromClass(cls);
    }

    public static Object getRealObject(Object o) {
        return ClassHelper.getContextClassUnwrapper(ClassHelper.getBus(null)).getRealObject(o);
    }

    public static Class<?> getRealClass(Bus bus, Object o) {
        return ClassHelper.getContextClassUnwrapper(ClassHelper.getBus(bus)).getRealClass(o);
    }

    private static ClassUnwrapper getContextClassUnwrapper(Bus bus) {
        if (bus != null && bus.getProperty(ClassUnwrapper.class.getName()) != null) {
            ClassUnwrapper unwrapper = (ClassUnwrapper)bus.getProperty(ClassUnwrapper.class.getName());
            return unwrapper;
        }
        return DEFAULT_UNWRAPPER == UNWRAPPER || ClassHelper.checkUseDefaultClassHelper(bus) ? DEFAULT_UNWRAPPER : UNWRAPPER;
    }

    private static Bus getBus(Bus bus) {
        return bus == null ? BusFactory.getThreadDefaultBus() : bus;
    }

    private static boolean checkUseDefaultClassHelper(Bus bus) {
        return bus != null && Boolean.TRUE.equals(bus.getProperty(USE_DEFAULT_CLASS_HELPER));
    }

    private static class DefaultClassUnwrapper
    implements ClassUnwrapper {
        private final ClassHelper helper;

        DefaultClassUnwrapper(ClassHelper helper) {
            this.helper = helper;
        }

        @Override
        public Class<?> getRealClassFromClass(Class<?> clazz) {
            return this.helper.getRealClassFromClassInternal(clazz);
        }

        @Override
        public Class<?> getRealClass(Object o) {
            return this.helper.getRealClassInternal(o);
        }

        @Override
        public Object getRealObject(Object o) {
            return this.helper.getRealObjectInternal(o);
        }
    }
}

